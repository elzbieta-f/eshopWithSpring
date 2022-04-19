package lt.bit.eshop.config;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lt.bit.eshop.dao.KrepselioDetalesDAO;
import lt.bit.eshop.dao.KrepselisDAO;
import lt.bit.eshop.dao.PrekeDAO;
import lt.bit.eshop.data.KrepselioDetales;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import org.springframework.transaction.annotation.Transactional;

@Configuration

public class SpringConfig {

    @Autowired
    private KrepselisDAO krepselisDAO;

    @Autowired
    private KrepselioDetalesDAO krepselioDetalesDAO;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean

    public AuthenticationSuccessHandler loggedIn() {
        AuthenticationSuccessHandler h;
        h = new AuthenticationSuccessHandler() {
            @Override
            @Transactional
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
                HttpSession session = request.getSession();
                Krepselis k = (Krepselis) session.getAttribute("krepselis");
                System.out.println(k);
                VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
                Vartotojas v = vd.getVartotojas();
                Krepselis vartotojoK = null;
                List<Krepselis> krepselisDB = krepselisDAO.getNebaigtasKrepselis(v);
                if (!krepselisDB.isEmpty()) {
                    vartotojoK = krepselisDB.get(0);
                }

                if (k != null) {
                    if (vartotojoK == null) {
                        vartotojoK = k;
                        vartotojoK.setVartotojasId(v);
                    } else {

                        List<KrepselioDetales> sessionCart = krepselioDetalesDAO.getDetalesByKrepselis(k);
                        List<KrepselioDetales> vartotojoCart = krepselioDetalesDAO.getDetalesByKrepselis(vartotojoK);
                        for (KrepselioDetales kd : sessionCart) {
                            Optional<KrepselioDetales> okd = krepselioDetalesDAO.findById(kd.getId());
                            if (okd.isEmpty()) {
                                continue;
                            }
                            KrepselioDetales kdDB = okd.get();
                            kdDB.setKrepselis(vartotojoK);
                            for (KrepselioDetales vkd : vartotojoCart) {
                                if (kd.getPreke().equals(vkd.getPreke())) {
                                    Integer galimasKiekis = 0;
                                    if (kd.getKiekis() + vkd.getKiekis() <= kd.getPreke().getKiekis()) {
                                        galimasKiekis = kd.getKiekis() + vkd.getKiekis();
                                    } else {
                                        galimasKiekis = kd.getPreke().getKiekis();
                                    }
                                    kdDB.setKiekis(galimasKiekis);
                                    krepselioDetalesDAO.delete(vkd);
                                }
                            }
                            krepselioDetalesDAO.flush();

                        }
                        krepselisDAO.delete(k);
                    }
                }

                session.setAttribute("krepselis", vartotojoK);
                  SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
                if (savedRequest == null) {
                    response.sendRedirect("./");
                } else {
                    response.sendRedirect(savedRequest.getRedirectUrl());
                }
            }
        };
        return h;
    }
}

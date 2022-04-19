package lt.bit.eshop.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.KrepselioDetalesDAO;
import lt.bit.eshop.dao.KrepselisDAO;
import lt.bit.eshop.dao.PrekeDAO;
import lt.bit.eshop.dao.VartotojasDAO;
import lt.bit.eshop.data.KrepselioDetales;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class VartotojasController {

    @Autowired
    VartotojasDAO vartotojasDAO;
    
    @Autowired
    private PrekeDAO prekeDAO;

    @Autowired
    private KrepselisDAO krepselisDAO;

    @Autowired
    private KrepselioDetalesDAO krepselioDetalesDAO;

    @Autowired
    private PasswordEncoder pe;

    @GetMapping(path = "register")
    public ModelAndView naujas() {
        ModelAndView mv = new ModelAndView("register");
        return mv;
    }

    @PostMapping(path = "register")
    @Transactional
    public String register(
            @RequestParam(value = "username") String vardas,
            @RequestParam(value = "password") String slaptazodis,
            @RequestParam(value = "password2") String slaptazodis2) {

        if (slaptazodis.equals(slaptazodis2)) {
            Vartotojas v = new Vartotojas();
            v.setVardas(vardas);
            v.setSlaptazodis(pe.encode(slaptazodis));
            vartotojasDAO.save(v);
            return "redirect:./login";
        } else {
            return "redirect:./login";
        }
    }

    @GetMapping(path = "account/change")
    public ModelAndView changeForm() {
        ModelAndView mv = new ModelAndView("change");
        return mv;
    }

    @PostMapping(path = "account/change")
    @Transactional
    public String changePassword(
            Authentication auth,
            @RequestParam(value = "password") String old,
            @RequestParam(value = "new") String naujas,
            @RequestParam(value = "new2") String naujas2) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        if (pe.matches(old, v.getSlaptazodis()) && naujas.equals(naujas2)) {
            v.setSlaptazodis(pe.encode(naujas));
            vartotojasDAO.save(v);
            return "redirect:../account?passChange=success";
        } else {
            return "redirect:../account?passChange=fail";
        }
    }

     @GetMapping(path = "account")
    public ModelAndView account(Authentication auth) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();        
        ModelAndView mv = new ModelAndView("account");
        List<Krepselis> krepseliaiDone =krepselisDAO.getPabaigtiKrepseliai(v);
        Map <Krepselis, BigDecimal> krepseliuSumos=new HashMap();
         for (Krepselis krepselis : krepseliaiDone) {
             krepseliuSumos.put(krepselis, krepselioDetalesDAO.getTotalSum(krepselis));
         }
        mv.addObject("vartotojas", v);
        mv.addObject("krepseliaiDone", krepseliaiDone);
        mv.addObject("krepseliuSumos", krepseliuSumos);
        return mv;
    }
    
@GetMapping(path = "account/showCart")
    public ModelAndView issamiau(
            Authentication auth,
            @RequestParam(value = "krepselioId") Integer krepselioId) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas(); 
        Optional<Krepselis> ok=krepselisDAO.findById(krepselioId);
         ModelAndView home = new ModelAndView("redirect:../");
         if (ok.isEmpty()){            
             return home;
        }
        Krepselis k=ok.get();
        if (!k.getVartotojasId().equals(v)){
            return home;
        }
        ModelAndView mv = new ModelAndView("showCart");
        List<KrepselioDetales> kd= krepselioDetalesDAO.getDetalesByKrepselis(k);
        BigDecimal krepselioSuma=krepselioDetalesDAO.getTotalSum(k);
        mv.addObject("krepselioDetales", kd);
        mv.addObject("krepselioSuma", krepselioSuma);
        mv.addObject("vartotojas", v);
        return mv;
    }
}

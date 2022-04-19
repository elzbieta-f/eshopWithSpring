package lt.bit.eshop.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.KrepselioDetalesDAO;
import lt.bit.eshop.dao.KrepselisDAO;
import lt.bit.eshop.dao.PrekeDAO;
import lt.bit.eshop.dao.RoleDAO;
import lt.bit.eshop.data.KrepselioDetales;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Preke;
import lt.bit.eshop.data.Role;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class StartController {

    @Autowired
    private PrekeDAO prekeDAO;

    @Autowired
    private KrepselisDAO krepselisDAO;

    @Autowired
    private KrepselioDetalesDAO krepselioDetalesDAO;

    @Autowired
    private RoleDAO roleDAO;
    
    @Autowired
    private HttpSession session;

    @GetMapping
    public ModelAndView index(
            Authentication auth,
            @RequestParam(value = "cart", required = false) String cart,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sortPrice", required = false) String sortPrice
    ) {
        Vartotojas v = null;
        Krepselis k = (Krepselis) session.getAttribute("krepselis");;
        if (auth != null) {
            VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
            v = vd.getVartotojas();
        }
        ModelAndView mv = new ModelAndView("index");
        List<Preke> list;
        if (filter != null && !filter.trim().equals("")) {
            filter = "%" + filter + "%";
            list = prekeDAO.getPrekes(filter);
        } else if (sortPrice != null && sortPrice.trim().equals("asc")) {
            list = prekeDAO.priceAsc();
        } else if (sortPrice != null && sortPrice.trim().equals("desc")) {
            list = prekeDAO.priceDesc();
        } else {
            list = prekeDAO.findAll();
        }
        
        mv.addObject("list", list);
        mv.addObject("vartotojas", v);
        Role admin=null;
        List<Role> or=roleDAO.byRoleName("Admin");
        if (!or.isEmpty()){
            admin=or.get(0);
        }
        mv.addObject("admin", admin);
        if (k != null) {
            Integer prekiuKiekis = krepselioDetalesDAO.getPrekiuKiekisKrepselyje(k);
            mv.addObject("prekiuKiekis", prekiuKiekis);
        }
        if (cart != null) {
            mv.addObject("cart", cart);
        }
        return mv;
    }

    @PostMapping(path = "toCart")
    @Transactional
    public String toCart(
            Authentication auth,
            @RequestParam("prekeId") Integer prekeId,
            //            @RequestParam(value = "krepselisId", required = false) Integer krepselisId,
            @RequestParam("kiekis") Integer kiekis) {
        Krepselis k = (Krepselis) session.getAttribute("krepselis");
        if (k == null) {
            k = new Krepselis();
            k.setSukurtas(new Date());
            krepselisDAO.save(k);
            session.setAttribute("krepselis", k);
        }
        if (auth != null) {
            VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
            Vartotojas v = vd.getVartotojas();
            if (k != null) {
                k.setVartotojasId(v);
            }
        }
        Optional<Preke> op = prekeDAO.findById(prekeId);
        if (op.isEmpty()) {
            return "redirect:./";
        }
        Preke p = op.get();
        if (kiekis <= 0) {
            return "redirect:./";
        }
        Integer galimasKiekis = 0;
        if (kiekis <= p.getKiekis()) {
            galimasKiekis = kiekis;
        } else {
            galimasKiekis = p.getKiekis();
        }
        KrepselioDetales kd = krepselioDetalesDAO.getDetalesByKrepselisIrPreke(k, p);
        if (kd != null) {
            kd.setKiekis(kd.getKiekis() + galimasKiekis);
        } else {
            kd = new KrepselioDetales();
            kd.setKiekis(galimasKiekis);
            kd.setKrepselis(k);
            kd.setPreke(p);
            krepselioDetalesDAO.save(kd);
        }

        return "redirect:./";
    }

    @GetMapping(path = "cart")
    public ModelAndView cart(Authentication auth) {
        ModelAndView mv = new ModelAndView("cart");
        Krepselis k = (Krepselis) session.getAttribute("krepselis");
        if (k != null) {
            List<KrepselioDetales> cart = krepselioDetalesDAO.getDetalesByKrepselis(k);
            BigDecimal total = krepselioDetalesDAO.getTotalSum(k);
            mv.addObject("cart", cart);
            mv.addObject("total", total);
        }

        return mv;
    }

    @PostMapping(path = "kiekis")
    @Transactional
    public String keistiKieki(
            Authentication auth,
            @RequestParam("kdId") Integer kdId,
            @RequestParam("kiekis") Integer kiekis) {
        Krepselis k = (Krepselis) session.getAttribute("krepselis");
        Optional<KrepselioDetales> okd = krepselioDetalesDAO.findById(kdId);
        if (okd.isEmpty()) {
            return "redirect:./cart";
        }
        KrepselioDetales kd = okd.get();
        if (!k.equals(kd.getKrepselis())) {
            return "redirect:./";
        }
        Preke p = kd.getPreke();
        Integer galimasKiekis = 0;
        if (kiekis <= 0) {
            krepselioDetalesDAO.delete(kd);
        } else if (kiekis <= p.getKiekis()) {
            galimasKiekis = kiekis;
        } else {
            galimasKiekis = p.getKiekis();
        }
        kd.setKiekis(galimasKiekis);
        return "redirect:./cart";
    }

    @GetMapping(path = "delete")
    @Transactional
    public String deleteKd(
            Authentication auth,
            @RequestParam("kdId") Integer kdId
    ) {
        Krepselis k = (Krepselis) session.getAttribute("krepselis");
        if (k == null) {
            return "redirect:./";
        }
        Optional<KrepselioDetales> okd = krepselioDetalesDAO.findById(kdId);
        if (okd.isEmpty()) {
            return "redirect:./cart";
        }
        KrepselioDetales kd = okd.get();
        if (!k.equals(kd.getKrepselis())) {
            return "redirect:./";
        }
        krepselioDetalesDAO.delete(kd);
        return "redirect:./cart";
    }

    @PostMapping(path = "buy")
    @Transactional
    public ModelAndView pirtki(
            Authentication auth,
            @RequestParam("krepselioId") Integer kId) {
        ModelAndView mv = new ModelAndView("redirect:./");
        Krepselis k = (Krepselis) session.getAttribute("krepselis");
        Optional<Krepselis> ok = krepselisDAO.findById(kId);
        if (ok.isEmpty()) {
            mv.addObject("cart", "Nepavyko pateikti užsakymo");
            return mv;
        }
        Krepselis kDB = ok.get();
        if (!k.equals(kDB)) {
            mv.addObject("cart", "Nepavyko pateikti užsakymo");
            return mv;
        }
        List<KrepselioDetales> list = krepselioDetalesDAO.getDetalesByKrepselis(kDB);
        for (KrepselioDetales kd : list) {
            Optional<Preke> op = prekeDAO.findById(kd.getPreke().getId());
            if (op.isEmpty()) {
                mv.addObject("cart", "Nepavyko pateikti užsakymo");
                return mv;
            }
            Preke p = op.get();
            p.setKiekis(p.getKiekis() - kd.getKiekis());
        }
        kDB.setIvykdytas(new Date());
        session.removeAttribute("krepselis");
        mv.addObject("cart", "Užsakymas pateiktas. Ačiū, kad perkate!");
        return mv;
    }

}

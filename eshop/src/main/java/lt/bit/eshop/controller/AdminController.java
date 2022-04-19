package lt.bit.eshop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.PrekeDAO;
import lt.bit.eshop.dao.VartotojasDAO;
import lt.bit.eshop.data.Preke;
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
@RequestMapping(path = "admin")
public class AdminController {

    @Autowired
    VartotojasDAO vartotojasDAO;

    @Autowired
    PrekeDAO prekeDAO;

    @GetMapping
    public ModelAndView prekiuValdymas(Authentication auth,
            @RequestParam(value = "prekeId", required = false) Integer prekeId,
            @RequestParam(value = "nauja", required = false) String nauja) {
        ModelAndView mv = new ModelAndView("prekes");
        List<Preke> list = prekeDAO.findAll();
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        if (prekeId != null) {
            Optional<Preke> op = prekeDAO.findById(prekeId);
            if (op.isPresent()) {
                Preke p = op.get();
                mv.addObject("preke", p);
            }
        }
        if (nauja != null) {
            mv.addObject("nauja", Boolean.TRUE);
        }
        mv.addObject("admin", admin);
        mv.addObject("list", list);
        return mv;
    }

    @GetMapping(path = "vartotojai")
    public ModelAndView vartotojaiList(
            Authentication auth,
            @RequestParam(value = "filter", required = false) String filter) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        List<Vartotojas> list;
        if (filter != null && !filter.trim().equals("")) {
            filter = "%" + filter + "%";
            list = vartotojasDAO.byVardasFilter(filter);
        } else {
            list = vartotojasDAO.findAll();
        }
        ModelAndView mv = new ModelAndView("vartotojai");
        mv.addObject("admin", admin);
        mv.addObject("vartotojai", list);
        return mv;
    }

    @GetMapping(path = "set")
    @Transactional
    public String setAdmin(Authentication auth,
            @RequestParam(value = "userId") Integer userId) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        Optional<Vartotojas> ov = vartotojasDAO.findById(userId);
        if (ov.isEmpty()) {
            return "redirect:../admin";
        }
        Vartotojas v = ov.get();
        if (v.equals(admin)) {
            return "redirect:../admin";
        }
        v.setAdmin(Boolean.TRUE);
        return "redirect:../admin";
    }

    @GetMapping(path = "remove")
    @Transactional
    public String removeAdmin(Authentication auth,
            @RequestParam(value = "userId") Integer userId) {
        Optional<Vartotojas> ov = vartotojasDAO.findById(userId);
        if (ov.isEmpty()) {
            return "redirect:../admin";
        }
        Vartotojas v = ov.get();
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        if (v.equals(admin)) {
            return "redirect:../admin";
        }
        v.setAdmin(Boolean.FALSE);
        return "redirect:../admin";
    }

    @GetMapping(path = "delete")
    @Transactional
    public String delete(Authentication auth,
            @RequestParam(value = "userId") Integer userId) {
        Optional<Vartotojas> ov = vartotojasDAO.findById(userId);
        if (ov.isEmpty()) {
            return "redirect:../admin";
        }
        Vartotojas v = ov.get();
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        if (v.equals(admin)) {
            return "redirect:../admin";
        }
        vartotojasDAO.delete(v);
        return "redirect:../admin";
    }

    @GetMapping(path = "deletePreke")
    @Transactional
    public String deletePreke(
            Authentication auth,
            @RequestParam(value = "prekeId") Integer prekeId) {
        Optional<Preke> op = prekeDAO.findById(prekeId);
        if (op.isEmpty()) {
            return "redirect:../admin";
        }
        Preke p = op.get();
        prekeDAO.delete(p);
        return "redirect:../admin";
    }

    @PostMapping
    @Transactional
    public String editPreke(
            Authentication auth,
            @RequestParam(value = "prekeId", required = false) Integer prekeId,
            @RequestParam(value = "pavadinimas") String pavadinimas,
            @RequestParam(value = "aprasymas", required = false) String aprasymas,
            @RequestParam(value = "kiekis", required = false) Integer kiekis,
            @RequestParam(value = "kaina") BigDecimal kaina) {

        Preke p = null;
        if (prekeId != null) {
            Optional<Preke> op = prekeDAO.findById(prekeId);
            if (op.isEmpty()) {
                return "redirect:./admin?edit=fail";
            }
            p = op.get();
        } else {
            p = new Preke();
        }
        p.setPavadinimas(pavadinimas);
        p.setAprasymas(aprasymas);
        if (kiekis != null) {
            p.setKiekis(kiekis);
        }
        p.setKaina(kaina);
        if (prekeId == null) {
            prekeDAO.save(p);
        }
        return "redirect:./admin?edit=ok";
    }

}

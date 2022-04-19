package lt.bit.eshop.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.KrepselioDetalesDAO;
import lt.bit.eshop.dao.KrepselisDAO;
import lt.bit.eshop.dao.PrekeDAO;
import lt.bit.eshop.dao.VartotojasDAO;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Preke;
import lt.bit.eshop.data.PrekiuStatistika;
import lt.bit.eshop.data.Role;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "stats")
public class StatsController {

    @Autowired
    private VartotojasDAO vartotojasDAO;

    @Autowired
    private KrepselisDAO krepselisDAO;

    @Autowired
    private KrepselioDetalesDAO krepselioDetalesDAO;

    @Autowired
    private PrekeDAO prekeDAO;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping
    public ModelAndView stats(Authentication auth) {
        ModelAndView mv = new ModelAndView("stats");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        mv.addObject("vartotojas", v);
        return mv;
    }

    @GetMapping(path = "carts")
    public ModelAndView showCarts(Authentication auth,
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "isCompleted", required = false) String isCompleted) {
        ModelAndView mv = new ModelAndView("carts");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        mv.addObject("vartotojas", v);
        List<Krepselis> carts = null;

        if (startDateStr != null && !startDateStr.equals("")) {
            try {
                Date startDate = sdf.parse(startDateStr);
                Date endDate = null;
                if (endDateStr != null) {
                    try {
                        endDate = sdf.parse(endDateStr);
                    } catch (Exception ex) {
                        endDate = new Date();
                    }
                }
                if (isCompleted != null && isCompleted.equals("on")) {
                    carts = krepselisDAO.getIvykdytiKrepseliaiPerLaikotarpi(startDate, endDate);
                } else {
                    carts = krepselisDAO.getSukurtiKrepseliaiPerLaikotarpi(startDate, endDate);
                }
            } catch (Exception ex) {
                carts = krepselisDAO.getVisiPabaigtiKrepseliai();
            }
        } else {
            if (isCompleted == null) {
                carts = krepselisDAO.findAll();
            } else if (isCompleted.equals("on")) {
                carts = krepselisDAO.getVisiPabaigtiKrepseliai();
            } else {
                carts = krepselisDAO.getVisiNepabaigtiKrepseliai();
            }
        }
        BigDecimal total = new BigDecimal(0);
        Map<Integer, BigDecimal> krepseliuSumos = new HashMap();
        for (Krepselis krepselis : carts) {
            BigDecimal krepselioTotal = krepselioDetalesDAO.getTotalSum(krepselis);
            if (krepselioTotal == null) {
                krepselioTotal = new BigDecimal(0);
            }
            krepseliuSumos.put(krepselis.getId(), krepselioTotal);
            total = total.add(krepselioTotal);
        }

        mv.addObject("total", total);
        mv.addObject("carts", carts);
        mv.addObject("krepseliuSumos", krepseliuSumos);
        return mv;
    }

    @GetMapping(path = "products")
    public ModelAndView showProducts(
            Authentication auth,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sortSum", required = false) String sortSum
    ) {
        ModelAndView mv = new ModelAndView("products");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        mv.addObject("vartotojas", v);
        List<PrekiuStatistika> products = null;
        if (filter != null && !filter.trim().equals("")) {
            filter = "%" + filter + "%";
            products = krepselioDetalesDAO.getPrekiuStatistikaFilter(filter);
        } else if (sort != null) {
            if ("desc".equals(sort)) {
                products = krepselioDetalesDAO.getStatsDescByKiekis();
            } else if ("asc".equals(sort)) {
                products = krepselioDetalesDAO.getStatsAscByKiekis();
            } else {
                products = krepselioDetalesDAO.getPrekiuStatistika();
            }
        } else if (sortSum != null) {
            if ("desc".equals(sortSum)) {
                products = krepselioDetalesDAO.getStatsDescBySuma();
            } else if ("asc".equals(sortSum)) {
                products = krepselioDetalesDAO.getStatsAscBySuma();
            } else {
                products = krepselioDetalesDAO.getPrekiuStatistika();
            }
        } else {
            products = krepselioDetalesDAO.getPrekiuStatistika();
        }
        mv.addObject("products", products);
        return mv;
    }
    
     @GetMapping(path = "sold")
    public ModelAndView showSoldProducts(
            Authentication auth,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "sortSum", required = false) String sortSum
    ) {
        ModelAndView mv = new ModelAndView("products");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        mv.addObject("vartotojas", v);
        List<PrekiuStatistika> products = null;
        if (filter != null && !filter.trim().equals("")) {
            filter = "%" + filter + "%";
            products = krepselioDetalesDAO.getPrekiuStatistikaFilterSold(filter);
        } else if (sort != null) {
            if ("desc".equals(sort)) {
                products = krepselioDetalesDAO.getStatsDescByKiekisSold();
            } else if ("asc".equals(sort)) {
                products = krepselioDetalesDAO.getStatsAscByKiekisSold();
            } else {
                products = krepselioDetalesDAO.getPrekiuStatistikaSold();
            }
        } else if (sortSum != null) {
            if ("desc".equals(sortSum)) {
                products = krepselioDetalesDAO.getStatsDescBySumaSold();
            } else if ("asc".equals(sortSum)) {
                products = krepselioDetalesDAO.getStatsAscBySumaSold();
            } else {
                products = krepselioDetalesDAO.getPrekiuStatistikaSold();
            }
        } else {
            products = krepselioDetalesDAO.getPrekiuStatistikaSold();
        }
        mv.addObject("soldProducts", products);
        return mv;
    }
}

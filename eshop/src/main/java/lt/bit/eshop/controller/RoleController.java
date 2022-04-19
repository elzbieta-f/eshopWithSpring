package lt.bit.eshop.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.RoleDAO;
import lt.bit.eshop.dao.VartotojasDAO;
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
@RequestMapping(path = "roles")
public class RoleController {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private VartotojasDAO vartotojasDAO;

    @GetMapping
    public ModelAndView rolesValdymas(
            Authentication auth,
            @RequestParam(value = "roleId", required = false) Integer roleId,
            @RequestParam(value = "nauja", required = false) String nauja,
            @RequestParam(value = "filter", required = false) String filter) {
        ModelAndView mv = new ModelAndView("roles");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas admin = vd.getVartotojas();
        mv.addObject("admin", admin);
        List<Role> roleList = null;
        if (filter != null && !filter.equals("")) {
            filter = "%" + filter + "%";
            roleList = roleDAO.roleFilter(filter);
        } else {
            roleList = roleDAO.findAll();
        }
        mv.addObject("roleList", roleList);
        if (nauja != null) {
            mv.addObject("nauja", Boolean.TRUE);
        }
        if (roleId != null) {
            Optional<Role> or = roleDAO.findById(roleId);
            if (or.isPresent()) {
                Role r = or.get();
                mv.addObject("role", r);
            }
        }
        return mv;
    }

    @GetMapping(path = "delete")
    @Transactional
    public String deleteRole(
            Authentication auth,
            @RequestParam(value = "roleId") Integer roleId) {
        Optional<Role> or = roleDAO.findById(roleId);
        if (or.isEmpty()) {
            return "redirect:../roles";
        }
        Role r = or.get();
        roleDAO.delete(r);
        return "redirect:../roles";
    }

    @PostMapping
    @Transactional
    public String editRole(
            Authentication auth,
            @RequestParam(value = "roleId", required = false) Integer roleId,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "desc", required = false) String desc
    ) {
        Role r = null;
        if (roleId != null) {
            Optional<Role> or = roleDAO.findById(roleId);
            if (or.isEmpty()) {
                return "redirect:./roles?edit=fail";
            }
            r = or.get();
        } else {
            r = new Role();
        }
        r.setRolesName(name);
        r.setDesc(desc);
        System.out.println(r);
        if (roleId == null) {
            roleDAO.save(r);
        }
        return "redirect:./roles?edit=ok";
    }

}

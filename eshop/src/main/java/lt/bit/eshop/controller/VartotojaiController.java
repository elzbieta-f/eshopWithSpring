package lt.bit.eshop.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lt.bit.eshop.config.VartotojasDetails;
import lt.bit.eshop.dao.RoleDAO;
import lt.bit.eshop.dao.VartotojasDAO;
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
@RequestMapping(path = "myRoles")
public class VartotojaiController {

    @Autowired
    private RoleDAO roleDAO;

    @Autowired
    private VartotojasDAO vartotojasDAO;

    @GetMapping
    public ModelAndView myRolesList(Authentication auth) {
        ModelAndView mv = new ModelAndView("myRoles");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas v = vd.getVartotojas();
        Role adminRole = null;
        List<Role> adminList = roleDAO.byRoleName("Admin");
        if (!adminList.isEmpty()) {
            adminRole = adminList.get(0);
        }
        Vartotojas admin = null;
        for (Role role : v.getRolesList()) {
            if (role.equals(adminRole)) {
                admin = v;
            }
        }
        Set<Role> roleList = v.getRolesList();
        if (admin!=null){
            mv.addObject("admin", admin);
        }       
        mv.addObject("vartotojas", v);
        mv.addObject("roleList", roleList);
        return mv;
    }

    @GetMapping(path = "vartotojai")
    public ModelAndView userList(
            Authentication auth,
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "naujas", required = false) String naujas) {
        ModelAndView mv = new ModelAndView("vartotojai");
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas grupesNarys = vd.getVartotojas();
        Role adminRole = null;
        List<Role> adminList = roleDAO.byRoleName("Admin");
        if (!adminList.isEmpty()) {
            adminRole = adminList.get(0);
        }
        Vartotojas admin = null;
        for (Role role : grupesNarys.getRolesList()) {
            if (role.equals(adminRole)) {
                admin = grupesNarys;
            }
        }
        Optional<Role> or = roleDAO.findById(roleId);
        if (or.isEmpty()) {
            ModelAndView neradoRoles = new ModelAndView("redirect:../");
            return neradoRoles;
        }
        Role r = or.get();
        Set<Vartotojas> set = r.getVartotojaiList();
        boolean belongs = false;
        for (Vartotojas vart : set) {
            if (vart.equals(grupesNarys)) {
                belongs = true;
            }
        }
        if (admin != null) {
            belongs = true;
        }
        if (!belongs) {
            ModelAndView nepriklausoRolei = new ModelAndView("redirect:../");
            return nepriklausoRolei;
        }

        List<Vartotojas> allOtherUsers = vartotojasDAO.notInRole(r);
        mv.addObject("allOtherUsers", allOtherUsers);
        mv.addObject("admin", admin);
        mv.addObject("vartotojas", grupesNarys);
        mv.addObject("role", r);
        mv.addObject("users", set);

        if (naujas != null) {
            mv.addObject("naujas", Boolean.TRUE);
        }
        return mv;
    }

    @GetMapping(path = "remove")
    @Transactional
    public String removeUserFromGroup(
            Authentication auth,
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas autorizuotasV = vd.getVartotojas();
        Role adminRole = null;
        List<Role> adminList = roleDAO.byRoleName("Admin");
        if (!adminList.isEmpty()) {
            adminRole = adminList.get(0);
        }
        Vartotojas admin = null;
        for (Role role : autorizuotasV.getRolesList()) {
            if (role.equals(adminRole)) {
                admin = autorizuotasV;
            }
        }
        Optional<Vartotojas> ov = vartotojasDAO.findById(userId);
        if (ov.isEmpty()) {
            return "redirect:../myRoles";
        }
        Vartotojas v = ov.get();
        if (admin == null && !v.equals(autorizuotasV)) {
            return "redirect:../myRoles";
        }
        Optional<Role> or = roleDAO.findById(roleId);
        if (or.isEmpty()) {
            return "redirect:../myRoles";
        }
        Role r = or.get();
        Set<Vartotojas> usersInGroup = r.getVartotojaiList();

        boolean belongs = false;
        for (Vartotojas vart : usersInGroup) {
            if (vart.equals(v)) {
                belongs = true;
            }
        }
        if (!belongs) {
            return "redirect:../myRoles/vartotojai?roleId=" + r.getId();
        }
        usersInGroup.remove(v);
        return "redirect:../myRoles/vartotojai?roleId=" + r.getId();
    }

    @PostMapping(path = "addUser")
    @Transactional
    public String addUserToGroup(
            Authentication auth,
            @RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        VartotojasDetails vd = (VartotojasDetails) auth.getPrincipal();
        Vartotojas autorizuotasV = vd.getVartotojas();
        Optional<Vartotojas> ov = vartotojasDAO.findById(userId);
        if (ov.isEmpty()) {
            return "redirect:../myRoles";
        }
        Vartotojas v = ov.get();
        Optional<Role> or = roleDAO.findById(roleId);
        if (or.isEmpty()) {
            return "redirect:../myRoles";
        }
        Role r = or.get();
        Set<Vartotojas> usersInGroup = r.getVartotojaiList();
        boolean autBelongs = false;
        for (Vartotojas vart : usersInGroup) {
            if (vart.equals(autorizuotasV)) {
                autBelongs = true;
            }
        }
        if (!autBelongs) {
            return "redirect:../myRoles";
        }

        boolean isUserInGroup = false;
        for (Vartotojas vart : usersInGroup) {
            if (vart.equals(v)) {
                isUserInGroup = true;
            }
        }
        if (isUserInGroup) {
           return "redirect:../myRoles";
        }
        usersInGroup.add(v);
        return "redirect:../myRoles/vartotojai?roleId=" + r.getId();
    }
}

package lt.bit.eshop.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lt.bit.eshop.dao.RoleDAO;
import lt.bit.eshop.data.Role;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class VartotojasDetails implements UserDetails {

    private final Vartotojas vartotojas;

    @Autowired
    private RoleDAO roleDAO;

    public VartotojasDetails(Vartotojas vartotojas) {
        if (vartotojas == null) {
            throw new NullPointerException("vartotojas must not be null");
        }
        this.vartotojas = vartotojas;
    }

    public Vartotojas getVartotojas() {
        return this.vartotojas;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> list = new HashSet();
        Set<Role> roles = vartotojas.getRolesList();
        for (Role role : roles) {
            list.add(new SimpleGrantedAuthority(role.getRolesName()));
        }
        if (this.vartotojas.getAdmin()) {
            
        }
        return list;
    }

    @Override
    public String getPassword() {
        return this.vartotojas.getSlaptazodis();
    }

    @Override
    public String getUsername() {
        return this.vartotojas.getVardas();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lt.bit.eshop.data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author elzbi
 */
@Entity
@Table(name = "vartotojai")
public class Vartotojas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "vardas")
    private String vardas;
    
    @Basic(optional = false)
    @Column(name = "slaptazodis")
    private String slaptazodis;
    
    @Basic(optional = false)
    @Column(name = "admin")
    private Boolean admin;
    
    @ManyToMany(mappedBy = "vartotojai")
    private Set<Role> roles;
    
   
    public Vartotojas() {
        this.admin=false;
         this.roles=new HashSet();
    }

    public Vartotojas(Integer id) {
        this.id = id;
    }

    public Vartotojas(Integer id, String vardas, String slaptazodis, Boolean admin) {
        this.id = id;
        this.vardas = vardas;
        this.slaptazodis = slaptazodis;
        this.admin = admin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVardas() {
        return vardas;
    }

    public void setVardas(String vardas) {
        this.vardas = vardas;
    }

    public String getSlaptazodis() {
        return slaptazodis;
    }

    public void setSlaptazodis(String slaptazodis) {
        this.slaptazodis = slaptazodis;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Set<Role> getRolesList() {
        return roles;
    }

    public void setRolesList(Set<Role> rolesList) {
        this.roles = rolesList;
    }
    
    public boolean hasRole(String roleName){
        if (roleName==null){
            return false;
        }
        boolean has=false;
        for (Role role : this.roles) {
            if (roleName.equals(role.getRolesName())){
                has=true;
                break;
            }
        }
        return has;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vartotojas)) {
            return false;
        }
        Vartotojas other = (Vartotojas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vartotojas{" + "id=" + id + ", vardas=" + vardas + ", slaptazodis=" + slaptazodis + ", admin=" + admin + '}';
    }

    
}

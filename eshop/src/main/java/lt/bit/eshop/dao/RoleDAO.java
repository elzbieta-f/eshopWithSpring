
package lt.bit.eshop.dao;


import java.util.List;
import java.util.Optional;
import lt.bit.eshop.data.Role;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface RoleDAO extends JpaRepository <Role, Integer> {
    @Query("select r from Role r where r.rolesName= :rolename")
    public List<Role> byRoleName(@Param("rolename") String roleName);
    
     @Query("select r from Role r where UPPER(r.rolesName) like UPPER(:filter) or  UPPER(r.descr) like UPPER(:filter)")
    public List<Role> roleFilter(@Param("filter") String filter);

}

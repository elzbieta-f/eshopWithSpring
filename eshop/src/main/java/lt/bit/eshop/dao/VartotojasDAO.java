
package lt.bit.eshop.dao;

import java.util.List;
import lt.bit.eshop.data.Preke;
import lt.bit.eshop.data.Role;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface VartotojasDAO extends JpaRepository <Vartotojas, Integer> {
    @Query("select v from Vartotojas v where v.vardas= :un")
    public List<Vartotojas> byVardas(@Param("un") String vardas);
    
    @Query("select v from Vartotojas v where v.vardas like :un")
    public List<Vartotojas> byVardasFilter(@Param("un") String vardas);
    
   @Query("select v from Vartotojas v where :role not member v.roles")
    public List<Vartotojas> notInRole(@Param("role") Role role);
    
}

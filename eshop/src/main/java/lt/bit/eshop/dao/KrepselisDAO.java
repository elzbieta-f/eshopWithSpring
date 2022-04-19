
package lt.bit.eshop.dao;

import java.util.Date;
import java.util.List;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface KrepselisDAO extends JpaRepository <Krepselis, Integer> {
    @Query("select k from Krepselis k where k.vartotojas= :vartotojas and k.ivykdytas is null")
    public List<Krepselis> getNebaigtasKrepselis(@Param("vartotojas") Vartotojas v);
    
    @Query("select k from Krepselis k where k.vartotojas= :vartotojas and k.ivykdytas is not null")
    public List<Krepselis> getPabaigtiKrepseliai(@Param("vartotojas") Vartotojas v);
    
    @Query("select k from Krepselis k where k.ivykdytas is not null")
    public List<Krepselis> getVisiPabaigtiKrepseliai();
    
    @Query("select k from Krepselis k where k.ivykdytas is null")
    public List<Krepselis> getVisiNepabaigtiKrepseliai();    
    
    @Query("SELECT k FROM Krepselis k where k.sukurtas> :startDate and k.sukurtas< :endDate and k.ivykdytas is null")
    public List<Krepselis> getSukurtiKrepseliaiPerLaikotarpi(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
    
    @Query("SELECT k FROM Krepselis k where k.ivykdytas> :startDate and k.ivykdytas< :endDate")
    public List<Krepselis> getIvykdytiKrepseliaiPerLaikotarpi(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

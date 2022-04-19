package lt.bit.eshop.dao;

import java.util.List;
import lt.bit.eshop.data.Preke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrekeDAO extends JpaRepository <Preke, Integer> {
    @Query("select p from Preke p where UPPER(p.pavadinimas) like UPPER(:filter) or UPPER(p.aprasymas) like UPPER(:filter)")
    public List<Preke> getPrekes(@Param("filter") String filter);
    
    @Query("select p from Preke p order by p.kaina asc")
    public List<Preke> priceAsc();
    
    @Query("select p from Preke p order by p.kaina desc")
    public List<Preke> priceDesc();
}

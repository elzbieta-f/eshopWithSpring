/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lt.bit.eshop.dao;

import java.math.BigDecimal;
import java.util.List;
import lt.bit.eshop.data.KrepselioDetales;
import lt.bit.eshop.data.Krepselis;
import lt.bit.eshop.data.Preke;
import lt.bit.eshop.data.PrekiuStatistika;
import lt.bit.eshop.data.Vartotojas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author elzbi
 */
public interface KrepselioDetalesDAO extends JpaRepository <KrepselioDetales, Integer> {
    @Query("SELECT SUM(k.kiekis) FROM KrepselioDetales k where k.krepselis= :krepselis")
    public Integer getPrekiuKiekisKrepselyje(@Param("krepselis") Krepselis k);
    
     @Query("SELECT k FROM KrepselioDetales k where k.krepselis= :krepselis")
    public List<KrepselioDetales> getDetalesByKrepselis(@Param("krepselis") Krepselis k);
    
     @Query("SELECT k FROM KrepselioDetales k where k.krepselis= :krepselis and k.preke= :preke")
    public KrepselioDetales getDetalesByKrepselisIrPreke(@Param("krepselis") Krepselis k, @Param("preke") Preke p);
    
    @Query("SELECT SUM(k.kiekis * k.preke.kaina) FROM KrepselioDetales k where k.krepselis= :krepselis")
    public BigDecimal getTotalSum(@Param("krepselis") Krepselis k);
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k group by k.preke")
    public List<PrekiuStatistika> getPrekiuStatistika();
       
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.preke.pavadinimas like :filter group by k.preke")
    public List<PrekiuStatistika> getPrekiuStatistikaFilter(@Param("filter") String filter);
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k group by k.preke order by kiekis desc")
    public List<PrekiuStatistika> getStatsDescByKiekis();
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k group by k.preke order by kiekis asc")
    public List<PrekiuStatistika> getStatsAscByKiekis();
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k group by k.preke order by suma desc")
    public List<PrekiuStatistika> getStatsDescBySuma();
    
     @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k group by k.preke order by suma asc")
    public List<PrekiuStatistika> getStatsAscBySuma();
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.krepselis.ivykdytas is not null group by k.preke")
    public List<PrekiuStatistika> getPrekiuStatistikaSold();
       
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.preke.pavadinimas like :filter and k.krepselis.ivykdytas is not null group by k.preke")
    public List<PrekiuStatistika> getPrekiuStatistikaFilterSold(@Param("filter") String filter);
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.krepselis.ivykdytas is not null group by k.preke order by kiekis desc")
    public List<PrekiuStatistika> getStatsDescByKiekisSold();
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.krepselis.ivykdytas is not null group by k.preke order by kiekis asc")
    public List<PrekiuStatistika> getStatsAscByKiekisSold();
    
    @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.krepselis.ivykdytas is not null group by k.preke order by suma desc")
    public List<PrekiuStatistika> getStatsDescBySumaSold();
    
     @Query("SELECT  new lt.bit.eshop.data.PrekiuStatistika(k.preke.id as prekesId, k.preke.pavadinimas as PrekesPavadinimas, SUM(k.kiekis) as kiekis, SUM(k.kiekis * k.preke.kaina) as suma) FROM KrepselioDetales k where k.krepselis.ivykdytas is not null group by k.preke order by suma asc")
    public List<PrekiuStatistika> getStatsAscBySumaSold();
//    @Query("SELECT k FROM KrepselioDetales k where k.preke= :preke")
//    public List<KrepselioDetales> getDetalesByPreke(@Param("preke") Preke p);
//    
//    @Query("SELECT SUM(k.kiekis * k.preke.kaina) FROM KrepselioDetales k where k.preke= :krepselis")
//    public BigDecimal getSumByPreke(@Param("krepselis") Krepselis k);
    
}

package lt.bit.eshop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

public class PrekiuStatistika implements Serializable {

    private Integer prekesId;

    private String prekesPavadinimas;

    private Long kiekis;

    private BigDecimal suma;

    public PrekiuStatistika() {
    }

    public PrekiuStatistika(Integer prekesId, String prekesPavadinimas, Long kiekis, BigDecimal suma) {
        this.prekesId = prekesId;
        this.prekesPavadinimas = prekesPavadinimas;
        this.kiekis = kiekis;
        this.suma = suma;
    }

    public Integer getPrekesId() {
        return prekesId;
    }

    public void setPrekesId(Integer prekesId) {
        this.prekesId = prekesId;
    }

    public String getPrekesPavadinimas() {
        return prekesPavadinimas;
    }

    public void setPrekesPavadinimas(String prekesPavadinimas) {
        this.prekesPavadinimas = prekesPavadinimas;
    }

    public Long getKiekis() {
        return kiekis;
    }

    public void setKiekis(Long kiekis) {
        this.kiekis = kiekis;
    }

    public BigDecimal getSuma() {
        return suma;
    }

    public void setSuma(BigDecimal suma) {
        this.suma = suma;
    }

    @Override
    public String toString() {
        return "PrekiuStatistika{" + "prekesId=" + prekesId + ", prekesPavadinimas=" + prekesPavadinimas + ", kiekis=" + kiekis + ", suma=" + suma + '}';
    }
    
    

}

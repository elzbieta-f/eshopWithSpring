/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lt.bit.eshop.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author elzbi
 */
@Entity
@Table(name = "prekes")
public class Preke implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "pavadinimas")
    private String pavadinimas;

    @Column(name = "aprasymas")
    private String aprasymas;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "kaina")
    private BigDecimal kaina;

    @Column(name = "paveiksl")
    private String paveiksl;

    @Basic(optional = false)
    @Column(name = "kiekis")
    private int kiekis;

    public Preke() {
    }

    public Preke(Integer id) {
        this.id = id;
    }

    public Preke(Integer id, String pavadinimas, BigDecimal kaina, int kiekis) {
        this.id = id;
        this.pavadinimas = pavadinimas;
        this.kaina = kaina;
        this.kiekis = kiekis;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPavadinimas() {
        return pavadinimas;
    }

    public void setPavadinimas(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }

    public String getAprasymas() {
        return aprasymas;
    }

    public void setAprasymas(String aprasymas) {
        this.aprasymas = aprasymas;
    }

    public BigDecimal getKaina() {
        return kaina;
    }

    public void setKaina(BigDecimal kaina) {
        this.kaina = kaina;
    }

    public String getPaveiksl() {
        return paveiksl;
    }

    public void setPaveiksl(String paveiksl) {
        this.paveiksl = paveiksl;
    }

    public int getKiekis() {
        return kiekis;
    }

    public void setKiekis(int kiekis) {
        this.kiekis = kiekis;
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
        if (!(object instanceof Preke)) {
            return false;
        }
        Preke other = (Preke) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Preke{" + "id=" + id + ", pavadinimas=" + pavadinimas + ", aprasymas=" + aprasymas + ", kaina=" + kaina + ", paveiksl=" + paveiksl + ", kiekis=" + kiekis + '}';
    }

}

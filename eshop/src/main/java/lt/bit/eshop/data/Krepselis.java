package lt.bit.eshop.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "krepselis")
public class Krepselis implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "ivykdytas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ivykdytas;
    
    @Basic(optional = false)
    @Column(name = "sukurtas")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sukurtas;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "krepselis")
    private List<KrepselioDetales> krepselioDetalesList;
    
    @JoinColumn(name = "vartotojas_id", referencedColumnName = "id")
    @ManyToOne
    private Vartotojas vartotojas;

    public Krepselis() {
    }

    public Krepselis(Integer id) {
        this.id = id;
    }

    public Krepselis(Integer id, Date sukurtas) {
        this.id = id;
        this.sukurtas = sukurtas;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getIvykdytas() {
        return ivykdytas;
    }

    public void setIvykdytas(Date ivykdytas) {
        this.ivykdytas = ivykdytas;
    }

    public Date getSukurtas() {
        return sukurtas;
    }

    public void setSukurtas(Date sukurtas) {
        this.sukurtas = sukurtas;
    }

    public List<KrepselioDetales> getKrepselioDetalesList() {
        return krepselioDetalesList;
    }

    public void setKrepselioDetalesList(List<KrepselioDetales> krepselioDetalesList) {
        this.krepselioDetalesList = krepselioDetalesList;
    }

    public Vartotojas getVartotojasId() {
        return vartotojas;
    }

    public void setVartotojasId(Vartotojas vartotojas) {
        this.vartotojas = vartotojas;
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
        if (!(object instanceof Krepselis)) {
            return false;
        }
        Krepselis other = (Krepselis) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Krepselis{" + "id=" + id + ", ivykdytas=" + ivykdytas + ", sukurtas=" + sukurtas +'}';
    }

    
    
}

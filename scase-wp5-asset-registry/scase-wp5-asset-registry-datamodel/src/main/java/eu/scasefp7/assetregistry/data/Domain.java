package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by missler on 12/05/15.
 */
@XmlRootElement
@Entity
@Table(name = "DOMAIN")
public class Domain extends BaseEntity{

    @Column(name= "NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<SubDomain> subdomains;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubDomain> getSubdomains() {
        return subdomains;
    }

    public void setSubdomains(List<SubDomain> subdomains) {
        this.subdomains = subdomains;
    }
}

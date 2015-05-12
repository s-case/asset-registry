package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by missler on 12/05/15.
 */
@XmlRootElement
@Entity
@Table(name="SUBDOMAIN")
public class SubDomain extends BaseEntity{

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    private Domain domain;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}

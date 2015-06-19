package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Entity representation of a domain as specified by Google Verticals.
 */
@XmlRootElement
@Entity
@Table(name = "DOMAIN")
public class Domain
        implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement
    @Version
    private Long version;

    @Column(name = "NAME")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "domain")
    private List<SubDomain> subdomains;

    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<SubDomain> getSubdomains()
    {
        return subdomains;
    }

    public void setSubdomains(List<SubDomain> subdomains)
    {
        this.subdomains = subdomains;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Domain domain = (Domain) o;

        if (id != null ? !id.equals(domain.id) : domain.id != null) {
            return false;
        }
        if (version != null ? !version.equals(domain.version) : domain.version != null) {
            return false;
        }
        if (name != null ? !name.equals(domain.name) : domain.name != null) {
            return false;
        }
        return !(subdomains != null ? !subdomains.equals(domain.subdomains) : domain.subdomains != null);

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (subdomains != null ? subdomains.hashCode() : 0);
        return result;
    }
}

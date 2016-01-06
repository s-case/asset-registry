package eu.scasefp7.assetregistry.data;

import java.io.Serializable;
import java.util.List;

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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<SubDomain> getSubdomains()
    {
        return this.subdomains;
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

        return new EqualsBuilder().append(this.id, domain.id).
                append(this.version, domain.version).
                append(this.name, domain.name).
                append(this.subdomains, domain.subdomains).
                isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.version)
                .append(this.name)
                .append(this.subdomains)
                .toHashCode();
    }
}

package eu.scasefp7.assetregistry.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * Entity representation of a subdomain as specified by Google Verticals
 */
@XmlRootElement
@Entity
@Table(name = "SUBDOMAIN")
public class SubDomain
        implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -7894913140324726507L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @XmlElement
    @Version
    private Long version;

    @Column(name = "NAME")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "domainId")
    @JsonBackReference
    private Domain domain;

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Domain getDomain()
    {
        return this.domain;
    }

    public void setDomain(Domain domain)
    {
        this.domain = domain;
    }

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubDomain subDomain = (SubDomain) o;

        if (this.id != null ? !this.id.equals(subDomain.id) : subDomain.id != null) {
            return false;
        }
        if (this.version != null ? !this.version.equals(subDomain.version) : subDomain.version != null) {
            return false;
        }
        if (this.name != null ? !this.name.equals(subDomain.name) : subDomain.name != null) {
            return false;
        }
        return !(this.domain != null ? !this.domain.equals(subDomain.domain) : subDomain.domain != null);

    }

    @Override
    public int hashCode()
    {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.version != null ? this.version.hashCode() : 0);
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.domain != null ? this.domain.hashCode() : 0);
        return result;
    }
}

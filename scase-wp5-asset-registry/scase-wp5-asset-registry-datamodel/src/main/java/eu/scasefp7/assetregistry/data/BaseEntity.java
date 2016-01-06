package eu.scasefp7.assetregistry.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * base entity.
 *
 * @author Robert Magnus
 */
@MappedSuperclass
public abstract class BaseEntity
        implements Serializable

{
    private static final long serialVersionUID = 4775486465844722741L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CREATEDBY")
    private String createdBy;

    @Column(name = "UPDATEDBY")
    private String updatedBy;

    @Column(name = "CREATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "UPDATED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    private Domain domain;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubDomain subDomain;

    @Column(name = "PRIVACYLEVEL")
    private PrivacyLevel privacyLevel;

    @XmlElement
    @Version
    private Long version;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public String getCreatedBy()
    {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy()
    {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy)
    {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt()
    {
        return this.createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return this.updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public Domain getDomain()
    {
        return this.domain;
    }

    public void setDomain(Domain domain)
    {
        this.domain = domain;
    }

    public SubDomain getSubDomain()
    {
        return this.subDomain;
    }

    public void setSubDomain(SubDomain subDomain)
    {
        this.subDomain = subDomain;
    }

    public PrivacyLevel getPrivacyLevel()
    {
        return this.privacyLevel;
    }

    public void setPrivacyLevel(PrivacyLevel privacyLevel)
    {
        this.privacyLevel = privacyLevel;
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

        BaseEntity that = (BaseEntity) o;

        return new EqualsBuilder().append(this.id, that.id).
                append(this.createdBy, that.createdBy).
                append(this.updatedBy, that.updatedBy).
                append(this.createdAt, that.createdAt).
                append(this.updatedAt, that.updatedAt).
                append(this.domain, that.domain).
                append(this.subDomain, that.subDomain).
                append(this.privacyLevel, that.privacyLevel).
                append(this.version, that.version).
                isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.createdBy)
                .append(this.updatedBy)
                .append(this.createdAt)
                .append(this.updatedAt)
                .append(this.domain)
                .append(this.subDomain)
                .append(this.privacyLevel)
                .append(this.version)
                .toHashCode();
    }

}

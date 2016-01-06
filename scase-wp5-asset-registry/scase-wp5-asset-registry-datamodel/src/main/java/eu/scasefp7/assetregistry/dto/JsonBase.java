package eu.scasefp7.assetregistry.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.scasefp7.assetregistry.data.PrivacyLevel;

/**
 * Created by missler on 03/06/15.
 */
public abstract class JsonBase
        implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = -4769527198114115162L;

    private Long id;

    private String createdBy;

    private String updatedBy;

    private Date createdAt;

    private Date updatedAt;

    private String domain;

    private String subDomain;

    private PrivacyLevel privacyLevel;

    @XmlElement
    private Long version;

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public String getDomain()
    {
        return this.domain;
    }

    public void setDomain(String domain)
    {
        this.domain = domain;
    }

    public String getSubDomain()
    {
        return this.subDomain;
    }

    public void setSubDomain(String subDomain)
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

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
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

        JsonBase jsonBase = (JsonBase) o;

        return new EqualsBuilder().append(this.id, jsonBase.id).
                append(this.createdBy, jsonBase.createdBy).
                append(this.updatedBy, jsonBase.updatedBy).
                append(this.createdAt, jsonBase.createdAt).
                append(this.updatedAt, jsonBase.updatedAt).
                append(this.domain, jsonBase.domain).
                append(this.subDomain, jsonBase.subDomain).
                append(this.privacyLevel, jsonBase.privacyLevel).
                append(this.version, jsonBase.version).
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

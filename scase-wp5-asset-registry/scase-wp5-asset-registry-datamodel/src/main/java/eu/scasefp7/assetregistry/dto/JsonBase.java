package eu.scasefp7.assetregistry.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

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

        if (this.id != null ? !this.id.equals(jsonBase.id) : jsonBase.id != null) {
            return false;
        }
        if (this.createdBy != null ? !this.createdBy.equals(jsonBase.createdBy) : jsonBase.createdBy != null) {
            return false;
        }
        if (this.updatedBy != null ? !this.updatedBy.equals(jsonBase.updatedBy) : jsonBase.updatedBy != null) {
            return false;
        }
        if (this.createdAt != null ? !this.createdAt.equals(jsonBase.createdAt) : jsonBase.createdAt != null) {
            return false;
        }
        if (this.updatedAt != null ? !this.updatedAt.equals(jsonBase.updatedAt) : jsonBase.updatedAt != null) {
            return false;
        }
        if (this.domain != null ? !this.domain.equals(jsonBase.domain) : jsonBase.domain != null) {
            return false;
        }
        if (this.subDomain != null ? !this.subDomain.equals(jsonBase.subDomain) : jsonBase.subDomain != null) {
            return false;
        }
        if (this.privacyLevel != jsonBase.privacyLevel) {
            return false;
        }
        return !(this.version != null ? !this.version.equals(jsonBase.version) : jsonBase.version != null);

    }

    @Override
    public int hashCode()
    {
        int result = this.id != null ? this.id.hashCode() : 0;
        result = 31 * result + (this.createdBy != null ? this.createdBy.hashCode() : 0);
        result = 31 * result + (this.updatedBy != null ? this.updatedBy.hashCode() : 0);
        result = 31 * result + (this.createdAt != null ? this.createdAt.hashCode() : 0);
        result = 31 * result + (this.updatedAt != null ? this.updatedAt.hashCode() : 0);
        result = 31 * result + (this.domain != null ? this.domain.hashCode() : 0);
        result = 31 * result + (this.subDomain != null ? this.subDomain.hashCode() : 0);
        result = 31 * result + (this.privacyLevel != null ? this.privacyLevel.hashCode() : 0);
        result = 31 * result + (this.version != null ? this.version.hashCode() : 0);
        return result;
    }
}

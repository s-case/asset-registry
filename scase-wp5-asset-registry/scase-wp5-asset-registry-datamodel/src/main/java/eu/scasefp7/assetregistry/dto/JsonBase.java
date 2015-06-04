package eu.scasefp7.assetregistry.dto;

import eu.scasefp7.assetregistry.data.PrivacyLevel;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by missler on 03/06/15.
 */
public abstract class JsonBase implements Serializable{

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }

    public PrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(PrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JsonBase jsonBase = (JsonBase) o;

        if (id != null ? !id.equals(jsonBase.id) : jsonBase.id != null) return false;
        if (createdBy != null ? !createdBy.equals(jsonBase.createdBy) : jsonBase.createdBy != null) return false;
        if (updatedBy != null ? !updatedBy.equals(jsonBase.updatedBy) : jsonBase.updatedBy != null) return false;
        if (createdAt != null ? !createdAt.equals(jsonBase.createdAt) : jsonBase.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(jsonBase.updatedAt) : jsonBase.updatedAt != null) return false;
        if (domain != null ? !domain.equals(jsonBase.domain) : jsonBase.domain != null) return false;
        if (subDomain != null ? !subDomain.equals(jsonBase.subDomain) : jsonBase.subDomain != null) return false;
        if (privacyLevel != jsonBase.privacyLevel) return false;
        return !(version != null ? !version.equals(jsonBase.version) : jsonBase.version != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0);
        result = 31 * result + (updatedBy != null ? updatedBy.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (subDomain != null ? subDomain.hashCode() : 0);
        result = 31 * result + (privacyLevel != null ? privacyLevel.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}

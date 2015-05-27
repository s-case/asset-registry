package eu.scasefp7.assetregistry.data;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entity representation of an artefact
 */
@XmlRootElement
@Entity
@Table(name = "ARTEFACT")
public class Artefact extends BaseEntity {

    private static final long serialVersionUID = -8329957242653476838L;

    @Column(name = "URI")
    private String uri;

    @Column(name = "GROUPID")
    private String groupId;

    @Column(name = "ARTEFACTNAME")
    private String name;

    @ElementCollection()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Long> dependencies = new ArrayList<Long>();

    @Column(name = "TYPE")
    private ArtefactType type;

    @Column(name = "DESCRIPTION")
    private String description;

    @ElementCollection()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> tags = new ArrayList<String>();

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Map<String, String> metadata = new HashMap<>();

    @OneToMany(cascade = javax.persistence.CascadeType.ALL)
    @Column(name = "PAYLOAD")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ArtefactPayload> payload = new ArrayList<ArtefactPayload>();

    public String getUri() {

        return uri;
    }

    public void setUri(String uri) {

        this.uri = uri;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getDependencies() {


        return dependencies;
    }

    public void setDependencies(List<Long> dependencies) {
        this.dependencies = dependencies;
    }

    public ArtefactType getType() {
        return type;
    }

    public void setType(ArtefactType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public List<ArtefactPayload> getPayload() {
        return payload;
    }

    public void setPayload(List<ArtefactPayload> payload) {
        this.payload = payload;
    }

    public void addPayload(ArtefactPayload artefactPayload) {
        if (null == payload) {
            payload = new ArrayList<ArtefactPayload>();
        }
        payload.add(artefactPayload);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Artefact artefact = (Artefact) o;

        if (uri != null ? !uri.equals(artefact.uri) : artefact.uri != null) return false;
        if (groupId != null ? !groupId.equals(artefact.groupId) : artefact.groupId != null) return false;
        if (name != null ? !name.equals(artefact.name) : artefact.name != null) return false;
        if (dependencies != null ? !dependencies.equals(artefact.dependencies) : artefact.dependencies != null)
            return false;
        if (type != artefact.type) return false;
        if (description != null ? !description.equals(artefact.description) : artefact.description != null)
            return false;
        if (tags != null ? !tags.equals(artefact.tags) : artefact.tags != null) return false;
        if (metadata != null ? !metadata.equals(artefact.metadata) : artefact.metadata != null) return false;
        return !(payload != null ? !payload.equals(artefact.payload) : artefact.payload != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        result = 31 * result + (groupId != null ? groupId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dependencies != null ? dependencies.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (metadata != null ? metadata.hashCode() : 0);
        result = 31 * result + (payload != null ? payload.hashCode() : 0);
        return result;
    }
}

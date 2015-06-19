package eu.scasefp7.assetregistry.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity representation of an artefact
 */
@XmlRootElement
@Entity
@Table(name = "ARTEFACT")
public class Artefact
        extends BaseEntity
{

    private static final long serialVersionUID = -8329957242653476838L;

    @Column(name = "PROJECTNAME")
    private String projectName;

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

    public String getProjectName()
    {
        return this.projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getUri()
    {

        return this.uri;
    }

    public void setUri(String uri)
    {

        this.uri = uri;
    }

    public String getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Long> getDependencies()
    {

        return this.dependencies;
    }

    public void setDependencies(List<Long> dependencies)
    {
        this.dependencies = dependencies;
    }

    public ArtefactType getType()
    {
        return this.type;
    }

    public void setType(ArtefactType type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getTags()
    {
        return this.tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    public Map<String, String> getMetadata()
    {
        return this.metadata;
    }

    public void setMetadata(Map<String, String> metadata)
    {
        this.metadata = metadata;
    }

    public List<ArtefactPayload> getPayload()
    {
        return this.payload;
    }

    public void setPayload(List<ArtefactPayload> payload)
    {
        this.payload = payload;
    }

    public void addPayload(ArtefactPayload artefactPayload)
    {
        if (null == this.payload) {
            this.payload = new ArrayList<ArtefactPayload>();
        }
        this.payload.add(artefactPayload);
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
        if (!super.equals(o)) {
            return false;
        }

        Artefact artefact = (Artefact) o;

        if (this.projectName != null ? !this.projectName.equals(artefact.projectName) : artefact.projectName != null) {
            return false;
        }
        if (this.uri != null ? !this.uri.equals(artefact.uri) : artefact.uri != null) {
            return false;
        }
        if (this.groupId != null ? !this.groupId.equals(artefact.groupId) : artefact.groupId != null) {
            return false;
        }
        if (this.name != null ? !this.name.equals(artefact.name) : artefact.name != null) {
            return false;
        }
        if (this.dependencies != null ? !this.dependencies.equals(artefact.dependencies)
                : artefact.dependencies != null) {
            return false;
        }
        if (this.type != artefact.type) {
            return false;
        }
        if (this.description != null ? !this.description.equals(artefact.description) : artefact.description != null) {
            return false;
        }
        if (this.tags != null ? !this.tags.equals(artefact.tags) : artefact.tags != null) {
            return false;
        }
        if (this.metadata != null ? !this.metadata.equals(artefact.metadata) : artefact.metadata != null) {
            return false;
        }
        return !(this.payload != null ? !this.payload.equals(artefact.payload) : artefact.payload != null);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (this.projectName != null ? this.projectName.hashCode() : 0);
        result = 31 * result + (this.uri != null ? this.uri.hashCode() : 0);
        result = 31 * result + (this.groupId != null ? this.groupId.hashCode() : 0);
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        result = 31 * result + (this.dependencies != null ? this.dependencies.hashCode() : 0);
        result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
        result = 31 * result + (this.description != null ? this.description.hashCode() : 0);
        result = 31 * result + (this.tags != null ? this.tags.hashCode() : 0);
        result = 31 * result + (this.metadata != null ? this.metadata.hashCode() : 0);
        result = 31 * result + (this.payload != null ? this.payload.hashCode() : 0);
        return result;
    }
}

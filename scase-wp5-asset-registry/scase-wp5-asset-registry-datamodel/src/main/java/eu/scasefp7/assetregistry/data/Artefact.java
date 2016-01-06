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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Entity representation of an artefact.
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

    /**
     * adds a payload for an artefact.
     * @param artefactPayload the payload.
     */
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

        return new EqualsBuilder().append(this.projectName, artefact.projectName).
                append(this.uri, artefact.uri).
                append(this.groupId, artefact.groupId).
                append(this.name, artefact.name).
                append(this.dependencies, artefact.dependencies).
                append(this.type, artefact.type).
                append(this.description, artefact.description).
                append(this.tags, artefact.tags).
                append(this.metadata, artefact.metadata).
                append(this.payload, artefact.payload).
                isEquals();


    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(this.projectName)
                .append(this.uri)
                .append(this.groupId)
                .append(this.name)
                .append(this.dependencies)
                .append(this.type)
                .append(this.description)
                .append(this.tags)
                .append(this.metadata)
                .append(this.payload)
                .toHashCode();
    }
}

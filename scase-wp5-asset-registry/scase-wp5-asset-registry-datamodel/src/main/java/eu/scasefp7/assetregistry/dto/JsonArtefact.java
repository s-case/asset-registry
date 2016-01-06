package eu.scasefp7.assetregistry.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.scasefp7.assetregistry.data.ArtefactType;

/**
 * Created by missler on 03/06/15.
 */
@XmlRootElement
public class JsonArtefact
        extends JsonBase
        implements Serializable
{

    private static final long serialVersionUID = -1932564949168434818L;

    private String projectName;

    private String uri;

    private String groupId;

    private String name;

    private List<Long> dependencies = new ArrayList<Long>();

    private ArtefactType type;

    private String description;

    private List<String> tags = new ArrayList<String>();

    private Map<String, String> metadata = new HashMap<>();

    private List<JsonArtefactPayload> payload = new ArrayList<JsonArtefactPayload>();

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

    public List<JsonArtefactPayload> getPayload()
    {
        return this.payload;
    }

    public void setPayload(List<JsonArtefactPayload> payload)
    {
        this.payload = payload;
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

        JsonArtefact that = (JsonArtefact) o;

        return new EqualsBuilder().append(this.projectName, that.projectName).
                append(this.uri, that.uri).
                append(this.groupId, that.groupId).
                append(this.name, that.name).
                append(this.dependencies, that.dependencies).
                append(this.type, that.type).
                append(this.description, that.description).
                append(this.tags, that.tags).
                append(this.metadata, that.metadata).
                append(this.payload, that.payload).
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

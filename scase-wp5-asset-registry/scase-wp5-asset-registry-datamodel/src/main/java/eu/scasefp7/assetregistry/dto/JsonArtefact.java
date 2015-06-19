package eu.scasefp7.assetregistry.dto;

import eu.scasefp7.assetregistry.data.ArtefactType;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return projectName;
    }

    public void setProjectName(String projectName)
    {
        this.projectName = projectName;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public List<Long> getDependencies()
    {
        return dependencies;
    }

    public void setDependencies(List<Long> dependencies)
    {
        this.dependencies = dependencies;
    }

    public ArtefactType getType()
    {
        return type;
    }

    public void setType(ArtefactType type)
    {
        this.type = type;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getTags()
    {
        return tags;
    }

    public void setTags(List<String> tags)
    {
        this.tags = tags;
    }

    public Map<String, String> getMetadata()
    {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata)
    {
        this.metadata = metadata;
    }

    public List<JsonArtefactPayload> getPayload()
    {
        return payload;
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

        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) {
            return false;
        }
        if (uri != null ? !uri.equals(that.uri) : that.uri != null) {
            return false;
        }
        if (groupId != null ? !groupId.equals(that.groupId) : that.groupId != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (dependencies != null ? !dependencies.equals(that.dependencies) : that.dependencies != null) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        if (description != null ? !description.equals(that.description) : that.description != null) {
            return false;
        }
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) {
            return false;
        }
        if (metadata != null ? !metadata.equals(that.metadata) : that.metadata != null) {
            return false;
        }
        return !(payload != null ? !payload.equals(that.payload) : that.payload != null);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
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

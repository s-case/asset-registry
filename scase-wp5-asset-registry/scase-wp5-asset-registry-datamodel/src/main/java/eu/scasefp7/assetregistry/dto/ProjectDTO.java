package eu.scasefp7.assetregistry.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Data transfer object of an artefact and it's score provided by ElasticSearch
 */
@XmlRootElement
public class ProjectDTO
        implements Serializable
{

    private static final long serialVersionUID = -9138018656434867472L;

    private JsonProject project;
    private float score;

    public JsonProject getProject()
    {
        return project;
    }

    public void setProject(JsonProject project)
    {
        this.project = project;
    }

    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
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

        ProjectDTO that = (ProjectDTO) o;

        if (Float.compare(that.score, score) != 0) {
            return false;
        }
        return !(project != null ? !project.equals(that.project) : that.project != null);

    }

    @Override
    public int hashCode()
    {
        int result = project != null ? project.hashCode() : 0;
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        return result;
    }
}

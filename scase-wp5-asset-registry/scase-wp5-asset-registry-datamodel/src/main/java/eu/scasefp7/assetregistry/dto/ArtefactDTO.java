package eu.scasefp7.assetregistry.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Data transfer object of an artefact and it's score provided by ElasticSearch
 */
@XmlRootElement
public class ArtefactDTO
        implements Serializable
{

    private static final long serialVersionUID = 1718431934579412614L;

    private JsonArtefact artefact;
    private float score;

    public JsonArtefact getArtefact()
    {
        return artefact;
    }

    public void setArtefact(JsonArtefact artefact)
    {
        this.artefact = artefact;
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

        ArtefactDTO that = (ArtefactDTO) o;

        if (Float.compare(that.score, score) != 0) {
            return false;
        }
        return !(artefact != null ? !artefact.equals(that.artefact) : that.artefact != null);

    }

    @Override
    public int hashCode()
    {
        int result = artefact != null ? artefact.hashCode() : 0;
        result = 31 * result + (score != +0.0f ? Float.floatToIntBits(score) : 0);
        return result;
    }
}

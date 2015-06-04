package eu.scasefp7.assetregistry.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by missler on 03/06/15.
 */
@XmlRootElement
public class JsonProject extends JsonBase implements Serializable {

    private static final long serialVersionUID = -6726764228129281382L;

    private String name;

    private List<JsonArtefact> artefacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JsonArtefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<JsonArtefact> artefacts) {
        this.artefacts = artefacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JsonProject that = (JsonProject) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(artefacts != null ? !artefacts.equals(that.artefacts) : that.artefacts != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (artefacts != null ? artefacts.hashCode() : 0);
        return result;
    }
}

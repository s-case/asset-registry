package eu.scasefp7.assetregistry.data;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representation of a project.
 */
@XmlRootElement
@Entity
@Table(name = "PROJECT")
public class Project extends BaseEntity {
    private static final long serialVersionUID = -6840914948412009544L;

    @Column(name = "PROJECTNAME", nullable = false)
    private String name;

    @Column(name = "PRIVACYLEVEL")
    private PrivacyLevel privacyLevel;


    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Artefact> artefacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PrivacyLevel getPrivacyLevel() {
        return privacyLevel;
    }

    public void setPrivacyLevel(PrivacyLevel privacyLevel) {
        this.privacyLevel = privacyLevel;
    }

    public List<Artefact> getArtefacts() {

        if(null==artefacts){
            artefacts = new ArrayList<Artefact>();
        }

        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Project project = (Project) o;

        if (name != null ? !name.equals(project.name) : project.name != null) return false;
        if (privacyLevel != project.privacyLevel) return false;
        return !(artefacts != null ? !artefacts.equals(project.artefacts) : project.artefacts != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (privacyLevel != null ? privacyLevel.hashCode() : 0);
        result = 31 * result + (artefacts != null ? artefacts.hashCode() : 0);
        return result;
    }
}

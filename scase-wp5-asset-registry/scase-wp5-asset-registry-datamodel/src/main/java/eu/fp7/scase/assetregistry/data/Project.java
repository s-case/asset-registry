package eu.fp7.scase.assetregistry.data;

import javax.persistence.*;
import java.util.List;

/**
 * entity class for project.
 * @author rmagnus
 *
 */
@Entity
@Table(name= "PROJECT")
public class Project extends BaseEntity 
{
    private static final long serialVersionUID = 1L;

    @Column(name="PROJECTNAME", nullable=false)
    private String name;

    @OneToMany
    @Column(nullable = true)
    private List<Artefact> artefacts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }
}

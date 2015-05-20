package eu.scasefp7.assetregistry.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * JPA entity representation of a project.
 * @author rmagnus
 */
@XmlRootElement
@Entity
@Table(name= "PROJECT")
public class Project extends BaseEntity 
{
    private static final long serialVersionUID = -6840914948412009544L;

    @Column(name="PROJECTNAME", nullable=false)
    private String name;

    @Column(name="PRIVACYLEVEL")
    private PrivacyLevel privacyLevel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Domain domain;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubDomain subDomain;

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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public SubDomain getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(SubDomain subDomain) {
        this.subDomain = subDomain;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }
}

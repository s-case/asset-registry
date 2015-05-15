package eu.scasefp7.assetregistry.data.testdata;

import de.akquinet.jbosscc.needle.db.testdata.AbstractTestdataBuilder;

import eu.scasefp7.assetregistry.data.PrivacyType;
import eu.scasefp7.assetregistry.data.Project;

import javax.persistence.EntityManager;
import java.util.Arrays;

/**
 *
 */
public class ProjectTestdataBuilder extends AbstractTestdataBuilder<Project> {

    public ProjectTestdataBuilder(final EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Project build() {
        final Project project = new Project();
        project.setName("Testproject");
        project.setPrivacyType(PrivacyType.PUBLIC);
        project.setArtefacts(Arrays.asList(new ArtefactTestdataBuilder(getEntityManager()).buildWithPayload()));
        return project;
    }
}

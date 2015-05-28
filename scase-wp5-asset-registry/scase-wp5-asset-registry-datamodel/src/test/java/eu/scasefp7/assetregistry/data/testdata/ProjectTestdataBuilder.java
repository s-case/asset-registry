package eu.scasefp7.assetregistry.data.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.akquinet.jbosscc.needle.db.testdata.AbstractTestdataBuilder;
import eu.scasefp7.assetregistry.data.PrivacyLevel;
import eu.scasefp7.assetregistry.data.Project;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Date;

/**
 *
 */
public class ProjectTestdataBuilder extends AbstractTestdataBuilder<Project> {

    public ProjectTestdataBuilder(final EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Project build() {
        final Project project = new Project();
        project.setName("testproject");
        project.setCreatedAt(new Date());
        project.setCreatedBy("user");
        project.setPrivacyLevel(PrivacyLevel.PUBLIC);
        project.setArtefacts(Arrays.asList(new ArtefactTestdataBuilder(getEntityManager()).buildWithPayload()));
        return project;
    }

    public String toJSON(Project project) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(project);
    }
}

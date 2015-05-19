package eu.scasefp7.assetregistry.service.db;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.DatabaseRule;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import eu.scasefp7.assetregistry.data.Project;
import org.junit.Rule;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by missler on 15/05/15.
 */
public class ProjectServiceImplTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @Rule
    public NeedleRule needleRule = new NeedleRule(databaseRule);

    @Inject
    private EntityManager entityManager;

    @ObjectUnderTest
    private ProjectDbServiceImpl service;

    public void testFindByName(){
      // Project project = new ProjectTestdataBuilder(entityManager).buildAndSave();

    }

}

package eu.scasefp7.assetregistry.service.db;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.junit.Rule;

import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.DatabaseRule;
import de.akquinet.jbosscc.needle.junit.NeedleRule;

/**
 * Created by missler on 15/05/15.
 */
public class ProjectServiceImplTest {

    @Rule
    public DatabaseRule databaseRule = new DatabaseRule();

    @Rule
    public NeedleRule needleRule = new NeedleRule(this.databaseRule);

    @Inject
    private EntityManager entityManager;

    @ObjectUnderTest
    private ProjectDbServiceImpl service;

    public void testFindByName(){
      // Project project = new ProjectTestdataBuilder(entityManager).buildAndSave();

    }

}

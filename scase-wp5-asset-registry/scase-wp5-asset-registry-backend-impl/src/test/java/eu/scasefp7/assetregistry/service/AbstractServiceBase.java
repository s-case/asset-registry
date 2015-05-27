package eu.scasefp7.assetregistry.service;

import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import de.akquinet.jbosscc.needle.annotation.InjectIntoMany;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import de.akquinet.jbosscc.needle.junit.DatabaseRule;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import de.akquinet.jbosscc.needle.mock.EasyMockProvider;
import eu.scasefp7.assetregistry.connector.ElasticSearchConnectorService;

/**
 * @author Alphonse Bendt
 */
public class AbstractServiceBase
{

    @Rule
    public ElasticsearchTestNode testNode = new ElasticsearchTestNode();

    @Rule
    public final DatabaseRule databaseRule = new DatabaseRule();

    @Rule
    public final NeedleRule needleRule = new NeedleRule(this.databaseRule);

    protected EasyMockProvider mockProvider = this.needleRule.getMockProvider();
    protected EmbeddedElasticsearchServer embeddedElasticsearchServer;

    @ObjectUnderTest
    @InjectIntoMany
    private ElasticSearchConnectorService connectorService;

    @Rule
    public final TestRule txWatcher = new TestWatcher() {
        @Override
        protected void finished(Description description)
        {
            EntityTransaction tx = AbstractServiceBase.this.databaseRule.getEntityManager().getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    };
    private boolean inTransaction;

    @Before
    public void setup()
    {
        this.embeddedElasticsearchServer = new EmbeddedElasticsearchServer();
        this.connectorService.init();

        this.mockProvider.resetAllToNice();

        startTransaction();
    }

    @After
    public void after()
    {
        rollbackTransaction();

        this.embeddedElasticsearchServer.shutdown();
    }

    protected void startTransaction()
    {

        if (this.inTransaction) {
            return;
        }

        this.inTransaction = true;

        this.databaseRule.getEntityManager().getTransaction().begin();
        this.databaseRule.getEntityManager().clear();
    }

    protected void commitTransaction()
    {
        if (!this.inTransaction) {
            return;
        }

        this.databaseRule.getEntityManager().getTransaction().commit();

        this.inTransaction = false;
    }

    protected void rollbackTransaction()
    {
        if (!this.inTransaction) {
            return;
        }

        this.databaseRule.getEntityManager().getTransaction().rollback();

        this.inTransaction = false;
    }

    protected void replayAll()
    {
        this.mockProvider.replayAll();
    }

    protected void verifyAll()
    {
        this.mockProvider.verifyAll();
    }
}

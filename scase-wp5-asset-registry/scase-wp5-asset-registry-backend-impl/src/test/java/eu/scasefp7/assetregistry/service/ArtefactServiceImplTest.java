package eu.scasefp7.assetregistry.service;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.InjectIntoMany;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.service.db.ArtefactDbServiceImpl;

public class ArtefactServiceImplTest
extends AbstractServiceBase
{

    @Rule
    public ElasticsearchTestNode testNode = new ElasticsearchTestNode();

    @ObjectUnderTest
    private ArtefactServiceImpl artefactServiceImpl;

    @ObjectUnderTest
    @InjectIntoMany
    private ArtefactDbServiceImpl dbService;


    private EmbeddedElasticsearchServer embeddedElasticsearchServer;

    @Before
    public void startEmbeddedElasticsearchServer()
    {
        this.embeddedElasticsearchServer = new EmbeddedElasticsearchServer();
    }

    @After
    public void shutdownEmbeddedElasticsearchServer()
    {
        this.embeddedElasticsearchServer.shutdown();
    }

    @Test
    public void testRun()
    {
        Artefact artefact = new Artefact();
        artefact.setName("name");
        Artefact create = this.artefactServiceImpl.create(artefact);

        assertThat(create).isNotNull();
        Artefact find = this.artefactServiceImpl.find(create.getId());

        assertThat(find).isNotNull();
    }

}

package eu.scasefp7.assetregistry.service;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import org.easymock.EasyMock;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import de.akquinet.jbosscc.needle.annotation.InjectIntoMany;
import de.akquinet.jbosscc.needle.annotation.ObjectUnderTest;
import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.service.db.ArtefactDbServiceImpl;
import eu.scasefp7.assetregistry.service.es.ArtefactEsServiceImpl;

public class ArtefactServiceImplTest
        extends AbstractServiceBase
{

    @ObjectUnderTest
    private ArtefactServiceImpl artefactServiceImpl;

    @ObjectUnderTest
    @InjectIntoMany
    private ArtefactDbServiceImpl dbService;

    @ObjectUnderTest
    @InjectIntoMany
    private ArtefactEsServiceImpl esService;

    @Test
    public void testFindById()
    {
        EasyMock.expect(this.connectorService.getClient()).andReturn(this.embeddedElasticsearchServer.getClient());

        this.mockProvider.replayAll();

        Artefact artefact = new Artefact();
        artefact.setName("name");
        Artefact create = this.artefactServiceImpl.create(artefact);

        assertThat(create).isNotNull();
        Artefact find = this.artefactServiceImpl.find(create.getId());

        assertThat(find).isNotNull();
        assertThat(find.getName()).isEqualTo("name");

        this.mockProvider.verifyAll();
    }

    @Test
    public void testRunNotExisting()
    {
        Artefact find = this.artefactServiceImpl.find(-1);

        assertThat(find).isNull();
    }

    @Test
    public void testFindByIndex() throws InterruptedException
    {
        EasyMock.expect(this.connectorService.getClient()).andReturn(this.embeddedElasticsearchServer.getClient());
        EasyMock.expect(this.connectorService.getClient()).andReturn(this.embeddedElasticsearchServer.getClient());

        this.mockProvider.replayAll();

        Artefact artefact = new Artefact();
        artefact.setName("dog");
        Artefact create = this.artefactServiceImpl.create(artefact);

        //warten auf Indizierung
        Thread.sleep(1000*2);

        assertThat(create).isNotNull();

        QueryBuilder qb = QueryBuilders.termQuery("name", "dog");

//        SearchResponse actionGet = this.connectorService.getClient()
//                .prepareSearch(ArtefactIndex.INDEX_NAME)
//                .setTypes(IndexType.TYPE_ARTEFACT)
//                .setQuery(qb)
//                .setFrom(0).
//                setSize(500).
//                setExplain(true).execute()
//                .actionGet();

         List<Artefact> find = this.artefactServiceImpl.find(qb.toString());

         assertThat(find).hasSize(1);
         assertThat(find.get(0).getName()).isEqualTo("dog");

        this.mockProvider.verifyAll();
    }

}

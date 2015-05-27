package eu.scasefp7.assetregistry.service;

import static org.fest.assertions.api.Assertions.assertThat;

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
    public void testRun()
    {
        Artefact artefact = new Artefact();
        artefact.setName("name");
        Artefact create = this.artefactServiceImpl.create(artefact);

        assertThat(create).isNotNull();
        Artefact find = this.artefactServiceImpl.find(create.getId());

        assertThat(find).isNotNull();
        assertThat(find.getName()).isEqualTo("name");
    }

    @Test
    public void testRunNotExisting()
    {
        Artefact find = this.artefactServiceImpl.find(-1);

        assertThat(find).isNull();
    }

}

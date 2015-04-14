package eu.scasefp7.assetregistry.data.testdata;

import java.util.Arrays;

import javax.persistence.EntityManager;

import de.akquinet.jbosscc.needle.db.testdata.AbstractTestdataBuilder;
import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.ArtefactType;

/**
 * Artefact test data builder
 */
public class ArtefactTestdataBuilder extends AbstractTestdataBuilder<Artefact>{

    public ArtefactTestdataBuilder(final EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Artefact build() {
        final Artefact artefact = new Artefact();
        artefact.setName("Test Artefact");
        artefact.setType(ArtefactType.TEXTUAL);
        artefact.setTags(Arrays.asList("Test", "Needle"));
        artefact.setDescription("Test Artefact Description");
        return artefact;
    }

    public Artefact buildWithPayload(){
        final Artefact artefact = build();
        artefact.addPayload(new ArtefactPayloadTestdataBuilder().build());
        artefact.addPayload((new ArtefactPayloadTestdataBuilder().build()));
        return artefact;
    }
}

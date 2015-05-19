package eu.scasefp7.assetregistry.data.testdata;

import de.akquinet.jbosscc.needle.db.testdata.AbstractTestdataBuilder;
import eu.scasefp7.assetregistry.data.ArtefactPayload;
import eu.scasefp7.assetregistry.data.PayloadFormat;
import eu.scasefp7.assetregistry.data.PayloadType;


/**
 *
 */
public class ArtefactPayloadTestdataBuilder extends AbstractTestdataBuilder<ArtefactPayload> {

    @Override
    public ArtefactPayload build() {
        final ArtefactPayload artefactPlayload = new ArtefactPayload();
        artefactPlayload.setType(PayloadType.BINARY);
        artefactPlayload.setFormat(PayloadFormat.IMAGE);
        artefactPlayload.setName("TestPayload Name");
        artefactPlayload.setPayload("TestPayload Content".getBytes());
        return artefactPlayload;
    }
}

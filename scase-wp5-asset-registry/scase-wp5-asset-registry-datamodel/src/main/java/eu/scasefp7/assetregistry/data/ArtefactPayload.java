package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * JPA entity representation of an artefact payload
 */
@Entity
@Table(name = "ARTEFACTPAYLOAD")
public class ArtefactPayload extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 542435835493989083L;

    @Column(name = "TYPE", nullable = false)
    private PayloadType type;

    @Column(name= "FORMAT", nullable = false)
    private PayloadFormat format;

    @Column(name = "PAYLOADNAME",nullable = false)
    private String name;

    @Lob
    @Column(name = "PAYLOAD")
    private byte[] payload;

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] playload) {
        this.payload = playload;
    }

    public PayloadType getType() {
        return type;
    }

    public void setType(PayloadType type) {
        this.type = type;
    }

    public PayloadFormat getFormat() {
        return format;
    }

    public void setFormat(PayloadFormat format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

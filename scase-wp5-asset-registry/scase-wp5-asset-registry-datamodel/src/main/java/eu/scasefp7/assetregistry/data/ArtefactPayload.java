package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Arrays;

/**
 * Entity representation of an artefact payload
 */
@Entity
@Table(name = "ARTEFACTPAYLOAD")
public class ArtefactPayload extends BaseEntity {

    private static final long serialVersionUID = 542435835493989083L;

    @Column(name = "TYPE", nullable = false)
    private PayloadType type;

    @Column(name = "FORMAT", nullable = false)
    private PayloadFormat format;

    @Column(name = "PAYLOADNAME", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ArtefactPayload that = (ArtefactPayload) o;

        if (type != that.type) return false;
        if (format != that.format) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return Arrays.equals(payload, that.payload);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (payload != null ? Arrays.hashCode(payload) : 0);
        return result;
    }
}

package eu.scasefp7.assetregistry.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Entity representation of an artefact payload.
 */
@Entity
@Table(name = "ARTEFACTPAYLOAD")
public class ArtefactPayload
        implements Serializable
{

    private static final long serialVersionUID = 542435835493989083L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TYPE", nullable = false)
    private PayloadType type;

    @Column(name = "FORMAT", nullable = false)
    private PayloadFormat format;

    @Column(name = "PAYLOADNAME", nullable = false)
    private String name;

    @Lob
    @Column(name = "PAYLOAD")
    private byte[] payload;

    @XmlElement
    @Version
    private Long version;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getVersion()
    {
        return version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public byte[] getPayload()
    {
        return payload.clone();
    }

    public void setPayload(byte[] playload)
    {
        this.payload = playload.clone();
    }

    public PayloadType getType()
    {
        return type;
    }

    public void setType(PayloadType type)
    {
        this.type = type;
    }

    public PayloadFormat getFormat()
    {
        return format;
    }

    public void setFormat(PayloadFormat format)
    {
        this.format = format;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ArtefactPayload payload1 = (ArtefactPayload) o;

        if (id != null ? !id.equals(payload1.id) : payload1.id != null) {
            return false;
        }
        if (type != payload1.type) {
            return false;
        }
        if (format != payload1.format) {
            return false;
        }
        if (name != null ? !name.equals(payload1.name) : payload1.name != null) {
            return false;
        }
        if (!Arrays.equals(payload, payload1.payload)) {
            return false;
        }
        return !(version != null ? !version.equals(payload1.version) : payload1.version != null);

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (payload != null ? Arrays.hashCode(payload) : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}

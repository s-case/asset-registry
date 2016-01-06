package eu.scasefp7.assetregistry.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getVersion()
    {
        return this.version;
    }

    public void setVersion(Long version)
    {
        this.version = version;
    }

    public byte[] getPayload()
    {
        return this.payload.clone();
    }

    public void setPayload(byte[] playload)
    {
        this.payload = playload.clone();
    }

    public PayloadType getType()
    {
        return this.type;
    }

    public void setType(PayloadType type)
    {
        this.type = type;
    }

    public PayloadFormat getFormat()
    {
        return this.format;
    }

    public void setFormat(PayloadFormat format)
    {
        this.format = format;
    }

    public String getName()
    {
        return this.name;
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

        ArtefactPayload payload = (ArtefactPayload) o;

        return new EqualsBuilder().append(this.id, payload.id).
                append(this.type, payload.type).
                append(this.format, payload.format).
                append(this.name, payload.name).
                append(this.payload, payload.payload).
                append(this.version, payload.version).
                isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.type)
                .append(this.format)
                .append(this.name)
                .append(this.payload)
                .append(this.version)
                .toHashCode();
    }
}

package eu.scasefp7.assetregistry.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import eu.scasefp7.assetregistry.data.PayloadFormat;
import eu.scasefp7.assetregistry.data.PayloadType;

/**
 * Created by missler on 03/06/15.
 */
public class JsonArtefactPayload
        implements Serializable
{

    private static final long serialVersionUID = 4412739544893279742L;

    private Long id;

    @XmlElement
    private Long version;

    private PayloadType type;

    private PayloadFormat format;

    private String name;

    private byte[] payload;

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

    public byte[] getPayload()
    {
        return this.payload.clone();
    }

    public void setPayload(byte[] payload)
    {
        this.payload = payload.clone();
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

        JsonArtefactPayload that = (JsonArtefactPayload) o;

        return new EqualsBuilder().append(this.id, that.id).
                append(this.version, that.version).
                append(this.type, that.type).
                append(this.format, that.format).
                append(this.name, that.name).
                append(this.payload, that.payload).
                isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder()
                .append(this.id)
                .append(this.version)
                .append(this.type)
                .append(this.format)
                .append(this.name)
                .append(this.payload)
                .toHashCode();
    }
}

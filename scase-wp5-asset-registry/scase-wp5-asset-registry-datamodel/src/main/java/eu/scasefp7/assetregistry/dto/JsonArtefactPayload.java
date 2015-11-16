package eu.scasefp7.assetregistry.dto;

import eu.scasefp7.assetregistry.data.PayloadFormat;
import eu.scasefp7.assetregistry.data.PayloadType;

import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Arrays;

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

    public byte[] getPayload()
    {
        return payload.clone();
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

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (version != null ? !version.equals(that.version) : that.version != null) {
            return false;
        }
        if (type != that.type) {
            return false;
        }
        if (format != that.format) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        return Arrays.equals(payload, that.payload);

    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (format != null ? format.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (payload != null ? Arrays.hashCode(payload) : 0);
        return result;
    }
}

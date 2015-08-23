package eu.scasefp7.assetregistry.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.ArtefactPayload;
import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.dto.JsonArtefactPayload;
import eu.scasefp7.assetregistry.index.ArtefactIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import eu.scasefp7.assetregistry.service.es.ArtefactEsService;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;

/**
 * Service implementation for Artefact related services to interact with the S-Case Asset Repository.
 */
@Stateless
@Local(ArtefactService.class)
public class ArtefactServiceImpl
        implements ArtefactService
{

    @EJB
    private ArtefactDbService dbService;

    @EJB
    private ArtefactEsService esService;

    @EJB
    private DomainDbService domainDbService;

    @Override
    public Artefact find(long id)
    {
        Artefact artefact = this.dbService.find(id);
        return artefact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ArtefactDTO> find(String query)
    {
        List<ArtefactDTO> artefacts = this.esService.find(query);
        return artefacts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ArtefactDTO> find(String query, String domain, String subdomain, String type){
        List<ArtefactDTO> artefacts = this.esService.find(query, domain, subdomain, type);
        return artefacts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artefact create(final Artefact artefact)
    {

        Artefact create;
        try {
            create = this.dbService.create(artefact);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Artefact.class, artefact.getName(), getRootCause(thrown));
        }

        try {
            this.esService.index(create);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Artefact.class, artefact.getName(), getRootCause(thrown));
        }

        return create;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artefact update(final Artefact artefact)
    {
        Artefact updated;
        try {
            updated = this.dbService.update(artefact);
            this.esService.update(updated);
        } catch (Throwable thrown) {
            throw new NotUpdatedException(Artefact.class, artefact.getId(), getRootCause(thrown));
        }
        return updated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(long id)
    {
        this.esService.delete(id, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        this.dbService.delete(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Artefact artefact)
    {
        this.esService.delete(artefact, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        this.dbService.delete(artefact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Artefact convertJsonToEntity(JsonArtefact jsonArtefact){
        Artefact artefact = new Artefact();

        artefact.setId(jsonArtefact.getId());
        artefact.setCreatedAt(jsonArtefact.getCreatedAt());
        artefact.setCreatedBy(jsonArtefact.getCreatedBy());
        artefact.setUpdatedAt(jsonArtefact.getUpdatedAt());
        artefact.setUpdatedBy(jsonArtefact.getUpdatedBy());
        artefact.setVersion(jsonArtefact.getVersion());

        if(null!=jsonArtefact.getDomain()) {
            Domain domain = domainDbService.findDomainByName(jsonArtefact.getDomain());
            artefact.setDomain(domain);
        }
        if(null!=jsonArtefact.getSubDomain()) {
            SubDomain subdomain = domainDbService.findSubDomainByName(jsonArtefact.getSubDomain());
            artefact.setSubDomain(subdomain);
        }

        artefact.setName(jsonArtefact.getName());
        artefact.setPrivacyLevel(jsonArtefact.getPrivacyLevel());
        artefact.setProjectName( jsonArtefact.getProjectName());
        artefact.setUri(jsonArtefact.getUri());
        artefact.setGroupId(jsonArtefact.getGroupId());
        artefact.setDependencies(jsonArtefact.getDependencies());
        artefact.setType(jsonArtefact.getType());
        artefact.setTags(jsonArtefact.getTags());
        artefact.setMetadata(jsonArtefact.getMetadata());
        artefact.setDescription(jsonArtefact.getDescription());

        for(JsonArtefactPayload jsonPayload : jsonArtefact.getPayload()){
            ArtefactPayload payload =  convertJsonToEntity(jsonPayload);
            artefact.getPayload().add(payload);
        }

        return artefact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonArtefact convertEntityToJson(Artefact artefact){
        JsonArtefact jsonArtefact = new JsonArtefact();
        jsonArtefact.setId(artefact.getId());
        jsonArtefact.setCreatedAt(artefact.getCreatedAt());
        jsonArtefact.setCreatedBy(artefact.getCreatedBy());
        jsonArtefact.setUpdatedAt(artefact.getUpdatedAt());
        jsonArtefact.setUpdatedBy(artefact.getUpdatedBy());
        jsonArtefact.setVersion(artefact.getVersion());

        if(null!=artefact.getDomain()) {
            jsonArtefact.setDomain(artefact.getDomain().getName());
        }
        if(null!=artefact.getSubDomain()) {
            jsonArtefact.setSubDomain(artefact.getSubDomain().getName());
        }

        jsonArtefact.setName(artefact.getName());
        jsonArtefact.setProjectName(artefact.getProjectName());
        jsonArtefact.setPrivacyLevel(artefact.getPrivacyLevel());
        jsonArtefact.setUri(artefact.getUri());
        jsonArtefact.setGroupId(artefact.getGroupId());
        jsonArtefact.setDependencies(artefact.getDependencies());
        jsonArtefact.setType(artefact.getType());
        jsonArtefact.setTags(artefact.getTags());
        jsonArtefact.setMetadata(artefact.getMetadata());
        jsonArtefact.setDescription(artefact.getDescription());

       for(ArtefactPayload payload : artefact.getPayload()){
         JsonArtefactPayload jsonArtefactPayload =  convertEntityToJson(payload);
           jsonArtefact.getPayload().add(jsonArtefactPayload);
       }

        return jsonArtefact;
    }

    /**
     * Private service to convert an {@link eu.scasefp7.assetregistry.data.ArtefactPayload ArtefactPayload}
     * into an {@link eu.scasefp7.assetregistry.dto.JsonArtefactPayload JsonArtefactPayload}.
     * @param payload {@link eu.scasefp7.assetregistry.data.ArtefactPayload ArtefactPayload}
     * @return {@link eu.scasefp7.assetregistry.dto.JsonArtefactPayload JsonArtefactPayload}
     */
    private JsonArtefactPayload convertEntityToJson(ArtefactPayload payload){
        JsonArtefactPayload jsonPayload = new JsonArtefactPayload();

        jsonPayload.setId(payload.getId());
        jsonPayload.setName(payload.getName());
        jsonPayload.setFormat(payload.getFormat());
        jsonPayload.setType(payload.getType());
        jsonPayload.setPayload(payload.getPayload());
        jsonPayload.setVersion(payload.getVersion());

        return jsonPayload;
    }

    /**
     * Private service to convert an {@link eu.scasefp7.assetregistry.dto.JsonArtefactPayload JsonArtefactPayload}
     * into an {@link eu.scasefp7.assetregistry.data.ArtefactPayload ArtefactPayload}.
     * @param jsonPayload {@link eu.scasefp7.assetregistry.dto.JsonArtefactPayload JsonArtefactPayload}
     * @return {@link eu.scasefp7.assetregistry.data.ArtefactPayload ArtefactPayload}
     */
    private ArtefactPayload convertJsonToEntity(JsonArtefactPayload jsonPayload){
        ArtefactPayload payload = new ArtefactPayload();

        payload.setId(jsonPayload.getId());
        payload.setName(jsonPayload.getName());
        payload.setFormat(jsonPayload.getFormat());
        payload.setType(jsonPayload.getType());
        payload.setPayload(jsonPayload.getPayload());
        payload.setVersion(jsonPayload.getVersion());

        return payload;

    }

    /**
     * Private service to discover the root cause of an exception thrown.
     * @param thrown - the thrown exception.
     * @return Throwable thrown - The root cause of the exception thrown.
     */
    private Throwable getRootCause(Throwable thrown){
        while(thrown.getCause()!=null){
            thrown = thrown.getCause();
        }
        return thrown;
    }
}

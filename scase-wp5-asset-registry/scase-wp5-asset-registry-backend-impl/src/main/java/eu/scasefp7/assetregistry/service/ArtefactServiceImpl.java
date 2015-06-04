package eu.scasefp7.assetregistry.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.ArtefactPayload;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.dto.JsonArtefactPayload;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.index.ArtefactIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import eu.scasefp7.assetregistry.service.es.ArtefactEsService;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;

/**
 * Created by missler on 16/03/15.
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

    @Override
    public List<ArtefactDTO> find(String query)
    {
        List<ArtefactDTO> artefacts = this.esService.find(query);
        return artefacts;
    }

    @Override
    public Artefact create(final Artefact artefact)
    {

        Artefact create;
        try {
            create = this.dbService.create(artefact);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Artefact.class, 0, thrown);
        }

        try {
            this.esService.index(create);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Artefact.class, artefact.getId(), thrown);
        }

        return create;
    }

    @Override
    public Artefact update(Artefact artefact)
    {
        try {
            artefact = this.dbService.update(artefact);
            this.esService.update(artefact);
        } catch (Throwable thrown) {
            throw new NotUpdatedException(Artefact.class, artefact.getId(), thrown);
        }
        return artefact;
    }

    @Override
    public void delete(long id)
    {
        this.esService.delete(id, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        this.dbService.delete(id);
    }

    @Override
    public void delete(Artefact artefact)
    {
        this.esService.delete(artefact, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        this.dbService.delete(artefact);
    }

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
            domainDbService.findDomainByName(jsonArtefact.getDomain());
        }
        if(null!=jsonArtefact.getSubDomain()) {
            domainDbService.findSubDomainByName(jsonArtefact.getSubDomain());
        }

        artefact.setName(jsonArtefact.getName());
        artefact.setPrivacyLevel(jsonArtefact.getPrivacyLevel());

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
}

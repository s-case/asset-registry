package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;
import eu.scasefp7.assetregistry.service.es.ArtefactEsService;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;
import eu.scasefp7.assetregistry.index.ArtefactIndex;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import java.util.List;

/**
 * Created by missler on 16/03/15.
 */
@Stateless
@Local(ArtefactService.class)
public class ArtefactServiceImpl implements ArtefactService {

    @EJB
    private ArtefactDbService dbService;

    @EJB
    private ArtefactEsService esService;

    @Override
    public Artefact find(long id){
        Artefact artefact = dbService.find(id);
        return artefact;
    }

    @Override
    public List<Artefact> find(String query){
        List<Artefact> artefacts = esService.find(query);
        return artefacts;
    }

    @Override
    public Artefact create(Artefact artefact){

        try {
            artefact = dbService.create(artefact);
            esService.index(artefact);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Artefact.class,artefact.getId(),thrown);
        }

        return artefact;
    }

    @Override
    public Artefact update(Artefact artefact){
        try{
            artefact = dbService.update(artefact);
            esService.update(artefact);
        }catch(Throwable thrown){
            throw new NotUpdatedException(Artefact.class,artefact.getId(),thrown);
        }
        return artefact;
    }

    @Override
    public void delete(long id){
        esService.delete(id, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        dbService.delete(id);
    }

    @Override
    public void delete(Artefact artefact){
        esService.delete(artefact, ArtefactIndex.INDEX_NAME, IndexType.TYPE_ARTEFACT);
        dbService.delete(artefact);
    }
}

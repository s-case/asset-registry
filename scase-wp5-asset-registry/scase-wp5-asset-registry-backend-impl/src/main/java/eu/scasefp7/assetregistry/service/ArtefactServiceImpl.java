package eu.scasefp7.assetregistry.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.dto.ArtefactDTO;
import eu.scasefp7.assetregistry.index.ArtefactIndex;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ArtefactDbService;
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
}

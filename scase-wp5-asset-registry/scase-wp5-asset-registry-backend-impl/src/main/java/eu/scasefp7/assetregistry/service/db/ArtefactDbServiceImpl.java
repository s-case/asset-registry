package eu.scasefp7.assetregistry.service.db;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.ArtefactPayload;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.service.exception.NotFoundException;

/**
 * Service to handle CRUD operations of artefacts.
 */
@Stateless
@Local(ArtefactDbService.class)
public class ArtefactDbServiceImpl extends BaseCrudDbServiceImpl<Artefact> implements ArtefactDbService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    Class<Artefact> getEntityClass() {
        return Artefact.class;
    }

    @Override
    public Artefact update(Artefact entity) {
        Artefact loaded = find(entity.getId());
        validateVersion(loaded, entity);
        loaded.setDependencies(entity.getDependencies());
        loaded.setMetadata(entity.getMetadata());
        loaded.setDescription(entity.getDescription());
        loaded.setGroupId(entity.getGroupId());
        loaded.setName(entity.getName());

        updatePayLoad(entity, loaded);

        loaded.setTags(entity.getTags());
        loaded.setType(entity.getType());
        loaded.setUri(entity.getUri());
        loaded.setUpdatedAt(new Date());

        return loaded;
    }

    private void updatePayLoad(Artefact entity, Artefact loaded)
    {
        if (loaded.getPayload() != null) {
            loaded.getPayload().clear();
        }

        if (entity.getPayload() != null) {
            if (loaded.getPayload() == null) {
                loaded.setPayload(new ArrayList<ArtefactPayload>());
            }

            for (ArtefactPayload element : entity.getPayload()) {
                loaded.getPayload().add(entityManager.find(ArtefactPayload.class, element.getId()));
            }
        }

    }

    @Override
    public void delete(final long entityId) {
        final Artefact e = find(entityId);
        if (e == null) {
            throw new NotFoundException(getEntityClass(), entityId);
        }
        
        String queryStr = "select p from Project p where name = :name";
        TypedQuery<Project> query = entityManager.createQuery(queryStr, Project.class);
        query.setParameter("name", e.getProjectName());
        Project project = query.getSingleResult();
        project.getArtefacts().remove(e);
        
        delete(e);
    }

    @Override
    protected EntityManager em() {
        return entityManager;
    }
}

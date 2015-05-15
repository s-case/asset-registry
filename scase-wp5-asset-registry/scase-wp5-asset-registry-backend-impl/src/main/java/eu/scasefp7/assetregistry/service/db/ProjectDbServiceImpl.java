package eu.scasefp7.assetregistry.service.db;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eu.scasefp7.assetregistry.data.Project;

import java.util.Date;

/**
 * service class for project.
 * @author rmagnus
 *
 */
@Stateless
@Local(ProjectDbService.class)
public class ProjectDbServiceImpl extends BaseCrudDbServiceImpl<Project> implements ProjectDbService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    Class<Project> getEntityClass() {
        return Project.class;
    }

    @Override
    public Project update(Project entity) {
        Project loaded = find(entity.getId());
        validateVersion(loaded, entity);
        loaded.setName(entity.getName());
        loaded.setPrivacyType(entity.getPrivacyType());
        loaded.setDomain(entity.getDomain());
        loaded.setSubDomain(entity.getSubDomain());
        loaded.setUpdatedAt(new Date());
        loaded.setArtefacts(entity.getArtefacts());

        return loaded;
    }

    @Override
    protected EntityManager em() {
        return entityManager;
    }

}

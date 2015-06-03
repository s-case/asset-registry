package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.ProjectDbService;
import eu.scasefp7.assetregistry.service.es.ProjectEsService;
import eu.scasefp7.assetregistry.service.exception.NameNotFoundException;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;
import eu.scasefp7.assetregistry.index.ProjectIndex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by missler on 17/03/15.
 */
@Stateless
@Local(ProjectService.class)
public class ProjectServiceImpl implements ProjectService {

    private final static Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @EJB
    private ProjectDbService dbService;

    @EJB
    private ProjectEsService esService;

    @Override
    public Project find(long id) {
        Project project = dbService.find(id);
        return project;
    }

    public Project findByName(String name){
        Project project = dbService.findByName(name);
        return project;
    }

    @Override
    public List<ProjectDTO> find(String query) {
        List<ProjectDTO> projects = esService.find(query);
        return projects;
    }

    @Override
    public Project create(Project project) {

        try {
            project = dbService.create(project);
            esService.index(project);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Project.class, project.getId(), thrown);
        }

        return project;
    }

    @Override
    public Project update(Project project) {
        try {
            project = dbService.update(project);
            esService.update(project);
        } catch (Throwable thrown) {
            throw new NotUpdatedException(Project.class, project.getId(), thrown);
        }
        return project;
    }

    @Override
    public void delete(long id) {
        esService.delete(id, ProjectIndex.INDEX_NAME, IndexType.TYPE_PROJECT);
        dbService.delete(id);
    }

    @Override
    public void delete(String name) {
        Project project = null;
        try {
            long id = Long.parseLong(name);
            project = dbService.find(id);
        } catch(NumberFormatException e){
            LOG.warn("Value " + name +" could not be parsed into a number. Trying to find the project by name.");
            project = dbService.findByName(name);
        }

        if (null != project) {
            this.delete(project.getId());
        } else {
            throw new NameNotFoundException(Project.class, name);
        }
    }

    @Override
    public void delete(Project project) {
        esService.delete(project, ProjectIndex.INDEX_NAME, IndexType.TYPE_PROJECT);
        dbService.delete(project);
    }
}

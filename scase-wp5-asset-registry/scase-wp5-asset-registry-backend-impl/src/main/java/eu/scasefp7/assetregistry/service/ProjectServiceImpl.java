package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.connector.ElasticSearchConnectorService;
import eu.scasefp7.assetregistry.service.db.ProjectDbService;
import eu.scasefp7.assetregistry.service.es.ProjectEsService;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;

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

    @EJB
    private ProjectDbService dbService;

    @EJB
    private ProjectEsService esService;

    @Override
    public Project find(long id){
        Project project = dbService.find(id);
        return project;
    }

    @Override
    public List<Project> find(String query){
        List<Project> projects = esService.find(query);
        return projects;
    }

    @Override
    public Project create(Project project){

        try {
            project = dbService.create(project);
            esService.index(project);
        } catch (Throwable thrown) {
            throw new NotCreatedException(Project.class,project.getId(),thrown);
        }

        return project;
    }

    @Override
    public Project update(Project project){
        try{
            project = dbService.update(project);
            esService.update(project);
        }catch(Throwable thrown){
            throw new NotUpdatedException(Project.class,project.getId(),thrown);
        }
        return project;
    }

    @Override
    public void delete(long id){
        esService.delete(id, ElasticSearchConnectorService.INDEX_PROJECTS,ElasticSearchConnectorService.TYPE_PROJECT);
        dbService.delete(id);
    }

    @Override
    public void delete(Project project){
        esService.delete(project, ElasticSearchConnectorService.INDEX_PROJECTS,ElasticSearchConnectorService.TYPE_PROJECT);
        dbService.delete(project);
    }
}

package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
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
    private DomainDbService domainDbService;

    @EJB
    private ArtefactService artefactService;

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
    public List<ProjectDTO> find(String query, String domain, String subdomain){
        List<ProjectDTO> projects = this.esService.find(query, domain, subdomain);
        return projects;
    }

    @Override
    public Project create(Project project) {
        Project created = null;
        try {
            created = dbService.create(project);
            esService.index(created);
        } catch (Throwable thrown) {
              throw new NotCreatedException(Project.class, project.getName(), getRootCause(thrown));
        }

        return project;
    }

    @Override
    public Project update(Project project) {
        try {
            project = dbService.update(project);
            esService.update(project);
        } catch (Throwable thrown) {
            throw new NotUpdatedException(Project.class, project.getId(), getRootCause(thrown));
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

    @Override
    public Project convertJsonToEntity(JsonProject jsonProject){
        Project project = new Project();

        project.setId(jsonProject.getId());
        project.setCreatedAt(jsonProject.getCreatedAt());
        project.setCreatedBy(jsonProject.getCreatedBy());
        project.setUpdatedAt(jsonProject.getUpdatedAt());
        project.setUpdatedBy(jsonProject.getUpdatedBy());
        project.setVersion(jsonProject.getVersion());

        if(null!=jsonProject.getDomain()) {
          Domain domain =  domainDbService.findDomainByName(jsonProject.getDomain());
            project.setDomain(domain);
        }
        if(null!=jsonProject.getSubDomain()) {
            SubDomain subdomain = domainDbService.findSubDomainByName(jsonProject.getSubDomain());
            project.setSubDomain(subdomain);
        }

        project.setName(jsonProject.getName());
        project.setPrivacyLevel(jsonProject.getPrivacyLevel());

        if(null!=jsonProject.getArtefacts()){
            List<JsonArtefact> jsonArtefacts = jsonProject.getArtefacts();
            for (JsonArtefact jsonArtefact : jsonArtefacts) {
               Artefact artefact = artefactService.convertJsonToEntity(jsonArtefact);
                project.getArtefacts().add(artefact);
            }
        }
        return project;
    }

    @Override
    public JsonProject convertEntityToJson(Project project){
        JsonProject jsonProject = new JsonProject();
        jsonProject.setId(project.getId());
        jsonProject.setCreatedAt(project.getCreatedAt());
        jsonProject.setCreatedBy(project.getCreatedBy());
        jsonProject.setUpdatedAt(project.getUpdatedAt());
        jsonProject.setUpdatedBy(project.getUpdatedBy());
        jsonProject.setVersion(project.getVersion());

        if(null!=project.getDomain()) {
            jsonProject.setDomain(project.getDomain().getName());
        }
        if(null!=project.getSubDomain()) {
            jsonProject.setSubDomain(project.getSubDomain().getName());
        }

        jsonProject.setName(project.getName());
        jsonProject.setPrivacyLevel(project.getPrivacyLevel());

        if(null!=project.getArtefacts()){
            List<Artefact> artefacts = project.getArtefacts();
            for (Artefact artefact : artefacts) {
                JsonArtefact jsonArtefact = artefactService.convertEntityToJson(artefact);
                jsonProject.getArtefacts().add(jsonArtefact);
            }
        }
        return jsonProject;
    }

    private Throwable getRootCause(Throwable thrown){
        while(thrown.getCause()!=null){
            thrown = thrown.getCause();
        }
        return thrown;
    }
}

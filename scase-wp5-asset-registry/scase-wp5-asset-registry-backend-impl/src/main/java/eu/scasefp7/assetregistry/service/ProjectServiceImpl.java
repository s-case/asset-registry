package eu.scasefp7.assetregistry.service;

import eu.scasefp7.assetregistry.data.Artefact;
import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.data.Project;
import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.dto.JsonArtefact;
import eu.scasefp7.assetregistry.dto.JsonProject;
import eu.scasefp7.assetregistry.dto.ProjectDTO;
import eu.scasefp7.assetregistry.index.IndexType;
import eu.scasefp7.assetregistry.index.ProjectIndex;
import eu.scasefp7.assetregistry.service.db.DomainDbService;
import eu.scasefp7.assetregistry.service.db.ProjectDbService;
import eu.scasefp7.assetregistry.service.es.ProjectEsService;
import eu.scasefp7.assetregistry.service.exception.NameNotFoundException;
import eu.scasefp7.assetregistry.service.exception.NotCreatedException;
import eu.scasefp7.assetregistry.service.exception.NotUpdatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Service implementation for Project related services to interact with the S-Case Asset Repository.
 */
@Stateless
@Local( ProjectService.class )
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOG = LoggerFactory.getLogger( ProjectServiceImpl.class );

    @EJB
    private ProjectDbService dbService;

    @EJB
    private DomainDbService domainDbService;

    @EJB
    private ArtefactService artefactService;

    @EJB
    private ProjectEsService esService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Project find( long id ) {
        Project project = this.dbService.find( id );
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project findByNameOrId( String nameOrId ) {

        Project project;
        try {
            long id = Long.parseLong( nameOrId );
            project = this.dbService.find( id );
        } catch ( NumberFormatException e ) {
            LOG.info( "Value " + nameOrId + " could not be parsed into a number. Trying to find the project by name." );
            project = this.dbService.findByName( nameOrId );
        }
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectDTO> find( String query ) {
        List<ProjectDTO> projects = this.esService.find( query );
        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ProjectDTO> find( String query, String domain, String subdomain ) {
        List<ProjectDTO> projects = this.esService.find( query, domain, subdomain );
        return projects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project create( final Project project ) {
        Project created;
        try {
            created = this.dbService.create( project );
            this.esService.index( created );
        } catch ( Exception thrown ) {
            throw new NotCreatedException( Project.class, project.getName(), getRootCause( thrown ) );
        }

        return created;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project update( final Project project ) {
        Project updated;
        try {
            updated = this.dbService.update( project );
            this.esService.update( updated );
        } catch ( Exception thrown ) {
            throw new NotUpdatedException( Project.class, project.getId(), getRootCause( thrown ) );
        }
        return updated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( long id ) {
        this.esService.delete( id, ProjectIndex.INDEX_NAME, IndexType.TYPE_PROJECT );
        this.dbService.delete( id );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete( String name ) {
        Project project = findByNameOrId( name );

        if ( null != project ) {
            this.delete( project );
        } else {
            throw new NameNotFoundException( Project.class, name );
        }
    }

    @Override
    public void delete( Project project ) {
        if ( null != project ) {
            this.delete( project.getId() );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Project convertJsonToEntity( JsonProject jsonProject ) {
        Project project = new Project();

        project.setId( jsonProject.getId() );
        project.setCreatedAt( jsonProject.getCreatedAt() );
        project.setCreatedBy( jsonProject.getCreatedBy() );
        project.setUpdatedAt( jsonProject.getUpdatedAt() );
        project.setUpdatedBy( jsonProject.getUpdatedBy() );
        project.setVersion( jsonProject.getVersion() );

        if ( null != jsonProject.getDomain() ) {
            Domain domain = this.domainDbService.findDomainByName( jsonProject.getDomain() );
            project.setDomain( domain );
        }
        if ( null != jsonProject.getSubDomain() ) {
            SubDomain subdomain = this.domainDbService.findSubDomainByName( jsonProject.getSubDomain() );
            project.setSubDomain( subdomain );
        }

        project.setName( jsonProject.getName() );
        project.setPrivacyLevel( jsonProject.getPrivacyLevel() );

        if ( null != jsonProject.getArtefacts() ) {
            List<JsonArtefact> jsonArtefacts = jsonProject.getArtefacts();
            for ( JsonArtefact jsonArtefact : jsonArtefacts ) {
                Artefact artefact = this.artefactService.convertJsonToEntity( jsonArtefact );
                project.getArtefacts().add( artefact );
            }
        }
        return project;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonProject convertEntityToJson( Project project ) {
        JsonProject jsonProject = new JsonProject();
        jsonProject.setId( project.getId() );
        jsonProject.setCreatedAt( project.getCreatedAt() );
        jsonProject.setCreatedBy( project.getCreatedBy() );
        jsonProject.setUpdatedAt( project.getUpdatedAt() );
        jsonProject.setUpdatedBy( project.getUpdatedBy() );
        jsonProject.setVersion( project.getVersion() );

        if ( null != project.getDomain() ) {
            jsonProject.setDomain( project.getDomain().getName() );
        }
        if ( null != project.getSubDomain() ) {
            jsonProject.setSubDomain( project.getSubDomain().getName() );
        }

        jsonProject.setName( project.getName() );
        jsonProject.setPrivacyLevel( project.getPrivacyLevel() );

        if ( null != project.getArtefacts() ) {
            List<Artefact> artefacts = project.getArtefacts();
            for ( Artefact artefact : artefacts ) {
                JsonArtefact jsonArtefact = this.artefactService.convertEntityToJson( artefact );
                jsonProject.getArtefacts().add( jsonArtefact );
            }
        }
        return jsonProject;
    }

    /**
     * Private service to discover the root cause of an exception thrown.
     *
     * @param thrown - the thrown exception.
     * @return Throwable thrown - The root cause of the exception thrown.
     */
    private Throwable getRootCause( final Throwable thrown ) {
        Throwable t = thrown;
        while ( t.getCause() != null ) {
            t = t.getCause();
        }
        return t;
    }
}

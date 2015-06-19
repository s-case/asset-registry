package eu.scasefp7.assetregistry.service.db;

import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.data.SubDomain;
import eu.scasefp7.assetregistry.service.exception.NameNotFoundException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Service  to retrieve core data of domains and subdomains available in the Asset Registry
 */
@Stateless
public class DomainDbServiceImpl implements DomainDbService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Domain findDomain(long domainId) {

      return em().find(Domain.class,domainId);
    }

    @Override
    public Domain findDomainByName(String name){
        TypedQuery<Domain> query = entityManager.createQuery("SELECT d FROM Domain d WHERE d.name = :name",Domain.class).setParameter("name",name);
        List<Domain> result = query.getResultList();
        if(result.isEmpty()){
            throw new NameNotFoundException(Domain.class,name);
        }
        return result.get(0);
    }

    @Override
    public SubDomain findSubDomain(long subdomainId) {
        return em().find(SubDomain.class,subdomainId);
    }

    @Override
    public SubDomain findSubDomainByName(String name){
        TypedQuery<SubDomain> query = entityManager.createQuery("SELECT s FROM SubDomain s WHERE s.name = :name",SubDomain.class).setParameter("name",name);
        List<SubDomain> result = query.getResultList();
        if(result.isEmpty()){
            throw new NameNotFoundException(SubDomain.class,name);
        }
        return result.get(0);
    }

    @Override
    public List<Domain> findAllDomains() {
        final CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<Domain> cq = cb.createQuery(Domain.class);
        cq.from(Domain.class);
        return em().createQuery(cq).getResultList();
    }

    @Override
    public List<SubDomain> findAllSubDomains() {
        final CriteriaBuilder cb = em().getCriteriaBuilder();
        CriteriaQuery<SubDomain> cq = cb.createQuery(SubDomain.class);
        cq.from(SubDomain.class);
        return em().createQuery(cq).getResultList();
    }


    private EntityManager em() {
        return entityManager;
    }
}

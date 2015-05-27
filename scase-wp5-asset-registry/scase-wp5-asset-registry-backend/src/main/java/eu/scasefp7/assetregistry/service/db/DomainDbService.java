package eu.scasefp7.assetregistry.service.db;

import eu.scasefp7.assetregistry.data.Domain;
import eu.scasefp7.assetregistry.data.SubDomain;

import java.util.List;

/**
 * Created by missler on 27/05/15.
 */

public interface DomainDbService {

    Domain findDomain(long domainId);

    SubDomain findSubDomain(long subdomainId);

    List<Domain> findAllDomains();

    List<SubDomain> findAllSubDomains();
}

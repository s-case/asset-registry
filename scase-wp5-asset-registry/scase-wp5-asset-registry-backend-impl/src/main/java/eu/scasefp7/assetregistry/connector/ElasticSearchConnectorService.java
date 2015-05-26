package eu.scasefp7.assetregistry.connector;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

@Singleton
@Startup
public class ElasticSearchConnectorService {

    private final static Logger LOG = LoggerFactory.getLogger(ElasticSearchConnectorService.class);

    private Client client;

    // instance of a json mapper
    private ObjectMapper mapper;

    @SuppressWarnings("resource")
    @PostConstruct
    public void init(){

        LOG.info( "Starting ElasticSearch Connector" );

        final String esServerNode = System.getProperty("es.node");

        client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(esServerNode, 9300));
    }

    @Produces
    public Client getClient() {

        return client;
    }

    @Produces
    public ObjectMapper getMapper(){

        return mapper;
    }

    @PreDestroy
    public void destroy(){

        client.close();
    }
}

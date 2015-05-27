package eu.scasefp7.assetregistry.connector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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

        String esServerNode = System.getProperty("es.node");

        String hostname = (esServerNode == null) ? "localhost" : esServerNode;

        this.client = new TransportClient()
                .addTransportAddress(new InetSocketTransportAddress(hostname, 9300));
    }

    @Produces
    public Client getClient() {

        return this.client;
    }

    @Produces
    public ObjectMapper getMapper(){

        return this.mapper;
    }

    @PreDestroy
    public void destroy(){

        this.client.close();
    }
}

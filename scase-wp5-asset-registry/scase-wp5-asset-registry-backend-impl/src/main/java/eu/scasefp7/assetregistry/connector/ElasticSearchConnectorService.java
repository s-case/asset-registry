package eu.scasefp7.assetregistry.connector;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Elastic Search Connector Service that will connect try to connect to an Elastic Search Node on start-up
 * of the application server.
 */
@Singleton
@Startup
public class ElasticSearchConnectorService {

    private static final Logger LOG = LoggerFactory.getLogger(ElasticSearchConnectorService.class);

    private Client client;

    @SuppressWarnings("resource")
    @PostConstruct
    public void init(){

        LOG.info( "Starting ElasticSearch Connector" );

        String esServerNode = System.getProperty("es.node");
        String esServerClusterName = System.getProperty("es.clustername");

        String hostname = (esServerNode == null) ? "localhost" : esServerNode;
        String clustername = (esServerClusterName == null) ? "elasticsearch" : esServerClusterName;
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", clustername).build();

        this.client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(hostname, 9300));
    }

    @Produces
    public Client getClient() {

        return this.client;
    }

    @PreDestroy
    public void destroy(){

        this.client.close();
    }
}

package eu.scasefp7.assetregistry.service;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;

public class EmbeddedElasticsearchServer
{

    private static final String DEFAULT_DATA_DIRECTORY = "target/elasticsearch-data";

    private final Node node;
    private final String dataDirectory;

    public EmbeddedElasticsearchServer() {
        this(DEFAULT_DATA_DIRECTORY);
    }

    public EmbeddedElasticsearchServer(String dataDirectory) {
        this.dataDirectory = dataDirectory;

        ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder()
                .put("http.enabled", "true")
                .put("http.host", "localhost")
                .put("path.data", dataDirectory);

        this.node = nodeBuilder().local(true).settings(elasticsearchSettings.build()).node();
    }

    public Client getClient()
    {
        return this.node.client();
    }

    public void shutdown()
    {
        this.node.close();
        deleteDataDirectory();
    }

    private void deleteDataDirectory()
    {
        try {
            FileUtils.deleteDirectory(new File(this.dataDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Could not delete data directory of embedded elasticsearch server", e);
        }
    }
}

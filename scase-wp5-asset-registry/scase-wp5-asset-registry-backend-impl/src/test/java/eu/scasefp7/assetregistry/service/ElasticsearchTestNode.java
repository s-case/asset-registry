package eu.scasefp7.assetregistry.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.junit.rules.ExternalResource;

public class ElasticsearchTestNode extends ExternalResource {

    private Node node;
    private Path dataDirectory;

    @Override
    protected void before() throws Throwable {
        try {
            this.dataDirectory = Files.createTempDirectory("es-test", new FileAttribute []{});
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        ImmutableSettings.Builder elasticsearchSettings = ImmutableSettings.settingsBuilder()
                .put("http.enabled", "true")
                .put("http.port", "9300")
                .put("path.data", this.dataDirectory.toString());

        this.node = NodeBuilder.nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node();
    }

    @Override
    protected void after() {
        this.node.close();
        try {
            FileUtils.deleteDirectory(this.dataDirectory.toFile());
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public Client getClient() {
        return this.node.client();
    }
}

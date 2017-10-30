package com.techolution.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by Dharmendra Pandit on 6/28/2017.
 */
@Configuration
@ConfigurationProperties(prefix = "swagger-ui")
public class SwaggerConfiguration {

    private List<GatewayConfiguration> configuration;

    public List<GatewayConfiguration> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(List<GatewayConfiguration> configuration) {
        this.configuration = configuration;
    }

    public static class GatewayConfiguration {
        private String applicationName;
        private String version;
        private String path;

        public String getApplicationName() {
            return applicationName;
        }

        public void setApplicationName(String applicationName) {
            this.applicationName = applicationName;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            final StringBuilder builder = new StringBuilder();
            builder.append("GateWayConfiguration [applicationName=");
            builder.append(applicationName);
            builder.append(", path=");
            builder.append(path);
            builder.append(", version=");
            builder.append(version);
            builder.append("]");
            return builder.toString();
        }
    }
}

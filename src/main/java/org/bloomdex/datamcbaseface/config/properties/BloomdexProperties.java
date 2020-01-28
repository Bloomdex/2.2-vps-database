package org.bloomdex.datamcbaseface.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "bloomdex.server")
@Configuration
public class BloomdexProperties {

    private String keyStoreFileName;

    private String keyStorePassword;

    private int port;

    //region Getters and Setters

    public String getKeyStoreFileName() {
        return keyStoreFileName;
    }

    public void setKeyStoreFileName(String keyStoreFileName) {
        this.keyStoreFileName = keyStoreFileName;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public void setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    //endregion
}

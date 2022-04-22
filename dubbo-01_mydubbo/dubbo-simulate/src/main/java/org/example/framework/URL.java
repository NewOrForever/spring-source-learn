package org.example.framework;

import java.io.Serializable;

public class URL implements Serializable {
    private static final long serialVersionUID = 3841286229072600529L;
    private String hostname;
    private Integer port;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}

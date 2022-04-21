package org.example.framework;

import java.io.Serializable;

public class URL implements Serializable {
    private static final long serialVersionUID = 3841286229072600529L;
    private String hostname;
    private Integer post;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPost() {
        return post;
    }

    public void setPost(Integer post) {
        this.post = post;
    }
}

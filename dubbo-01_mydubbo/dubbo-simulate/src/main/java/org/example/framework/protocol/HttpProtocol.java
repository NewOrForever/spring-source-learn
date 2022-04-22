package org.example.framework.protocol;

import org.example.framework.Invocation;
import org.example.framework.Protocol;
import org.example.framework.URL;

public class HttpProtocol implements Protocol {
    @Override
    public void start(URL url) {
        new HttpServer().start(url.getHostname(), url.getPort());
    }

    public String send(URL url, Invocation invocation){
        HttpClient httpClient = new HttpClient();
        return httpClient.send(url.getHostname(), url.getPort(), invocation);
    }
}

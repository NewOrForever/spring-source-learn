package org.example.framework.protocol;

import org.example.framework.Invocation;
import org.example.framework.URL;

public class HttpProtocol {
    public String send(URL url, Invocation invocation){
        HttpClient httpClient = new HttpClient();
        return httpClient.send(url.getHostname(), url.getPost(), invocation);
    }
}

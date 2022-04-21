package org.example.framework.protocol;

import org.apache.commons.io.IOUtils;
import org.example.framework.Invocation;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public String send(String hostname, Integer port, Invocation invocation) {
        try {
//            var request = HttpRequest.newBuilder()
//                    .uri(new URI("http", null, hostname, port, "/", null, null))
//                    .POST(HttpRequest.BodyPublishers.ofString(JSONObject.toJSONString(invocation)))
//                    .build();
//            var client = java.net.http.HttpClient.newHttpClient();
//
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            String result = response.body();

            // JDK11之前用
            URL url = new URL("http", hostname, port, "/");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStream outputStream = httpURLConnection.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            oos.writeObject(invocation);
            oos.flush();
            oos.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            String result = IOUtils.toString(inputStream);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

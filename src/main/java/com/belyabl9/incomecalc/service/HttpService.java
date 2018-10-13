package com.belyabl9.incomecalc.service;

import lombok.NonNull;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
public class HttpService {

    public String get(@NonNull URI uri) throws Exception {
        return get(uri, Collections.emptyList());
    }

    public String get(@NonNull URI uri, @NonNull List<Header> headers) throws Exception {
        HttpClient client = new DefaultHttpClient();

        HttpGet request = new HttpGet(uri);
        if (!headers.isEmpty()) {
            request.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        HttpResponse response = client.execute(request);

        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }
    
}

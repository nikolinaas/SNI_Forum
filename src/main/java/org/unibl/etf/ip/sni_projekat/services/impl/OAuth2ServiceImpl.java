package org.unibl.etf.ip.sni_projekat.services.impl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.services.OAuth2Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    @Value("${getUserInfoURI}")
    private String accessTokenUri;
    @Override
    public User oauth2login(String token) {


        try {
            URI uri = new URIBuilder(accessTokenUri).addParameter("id_token",token).build();

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));

            HttpResponse response = client.execute(postRequest);

            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            System.out.println(responseBody);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}

package org.unibl.etf.ip.sni_projekat.services.impl;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import net.minidev.json.JSONObject;
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
import org.unibl.etf.ip.sni_projekat.model.Role;
import org.unibl.etf.ip.sni_projekat.model.User;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.OAuth2Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class OAuth2ServiceImpl implements OAuth2Service {

    @Value("${getUserInfoURI}")
    private String accessTokenUri;
    @PersistenceContext
    private EntityManager entityManager;

    private final UserEntityRepository userEntityRepository;

    public OAuth2ServiceImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public User oauth2login(String token) {


        try {
            URI uri = new URIBuilder(accessTokenUri).addParameter("id_token", token).build();

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));

            HttpResponse response = client.execute(postRequest);

            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            JsonObject data = new Gson().fromJson(responseBody, JsonObject.class);
            JsonElement email = data.get("email");
            JsonElement username = data.get("name");
            JsonElement firstName = data.get("given_name");
            JsonElement surname = data.get("family_name");
            System.out.println(data);
UserEntity userEntity = null;

            if(userEntityRepository.existsByEmail(email.toString())){

                userEntity = userEntityRepository.findByEmail(email.toString());


            }else{
                userEntity = new UserEntity();
                userEntity.setActivated(true);
                userEntity.setPassword(null);
                userEntity.setUsername(username.toString());
                userEntity.setRole(Role.USER);
                userEntity.setName(firstName.toString());
                userEntity.setSurname(surname.toString());
                userEntity.setEmail(email.toString());
                userEntity.setId(null);
                userEntity = userEntityRepository.saveAndFlush(userEntity);
                entityManager.refresh(userEntity);
            }





        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}

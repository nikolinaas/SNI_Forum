package org.unibl.etf.ip.sni_projekat.services.impl;


import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.ip.sni_projekat.exceptions.WrongCredentialsException;
import org.unibl.etf.ip.sni_projekat.model.*;
import org.unibl.etf.ip.sni_projekat.model.entities.UserEntity;
import org.unibl.etf.ip.sni_projekat.repositories.UserEntityRepository;
import org.unibl.etf.ip.sni_projekat.services.AuthenticationService;
import org.unibl.etf.ip.sni_projekat.services.EmailService;
import org.unibl.etf.ip.sni_projekat.services.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {


    @PersistenceContext
    private EntityManager entityManager;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final ModelMapper mapper;

    private final EmailService emailService;

    @Value("${getUserInfoURI}")
    private String accessTokenUri;

    private final UserEntityRepository userEntityRepository;

    @Value("900000")//oduzeti nulu jednu poslije
    private String tokenExpirationDate;

    @Value("${authorization.token.secret}")
    private String tokenSecert;

    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserService userService, ModelMapper mapper, EmailService emailService, UserEntityRepository userEntityRepository) {


        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.mapper = mapper;
        this.emailService = emailService;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserEntity login(LoginRequest request) {
        UserEntity userEntity=null;
        try{

        JWTUser user = null;
        Authentication authentication = null;

            user = mapper.map(userService.findByUsername(request.getUsername()), JWTUser.class);



        try {
            System.out.println(request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, request.getPassword(), user.getAuthorities()));


            //  user.setToken(generateJWT(user));
        } catch (Exception ex) {

            ex.printStackTrace();
        }

         userEntity = userEntityRepository.getById(user.getId());
        if (!userEntity.getActivated()) {
            userEntity = null;
        } else {
            int length = 24;
            boolean useLetters = true;
            boolean useNumbers = true;
            String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
            System.out.println(generatedString);
            try {
                emailService.sendVerificationEmail(userEntity.getEmail(), generatedString, userEntity.getName());
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            userEntity.setCode(generatedString);
        }
        }catch (Exception ex){
            throw new WrongCredentialsException();
        }
        System.out.println(userEntity);
        return userEntity;
    }


    @Override
    public void register(UserRequest request) {

        UserEntity user = mapper.map(request, UserEntity.class);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActivated(false);
        user.setRole(Role.USER);
        user = userEntityRepository.saveAndFlush(user);
        entityManager.refresh(user);

    }


    @Override
    public User oauth2login(String token) {

        UserEntity userEntity = null;
        try {
            URI uri = new URIBuilder(accessTokenUri).addParameter("id_token", token).build();

            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));

            HttpResponse response = client.execute(postRequest);

            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            JsonObject data = new Gson().fromJson(responseBody, JsonObject.class);
            String email = data.get("email").getAsString();
            String username = data.get("name").getAsString();
            String firstName = data.get("given_name").getAsString();
            String surname = data.get("family_name").getAsString();
            System.out.println(data);


            if(userEntityRepository.existsByEmail(email)){
                userEntity = userEntityRepository.findByEmail(email);
                if(!userEntity.getActivated()){
                    userEntity=null;
                }else{
                    int length = 24;
                    boolean useLetters = true;
                    boolean useNumbers = true;
                    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
                    System.out.println(generatedString);
                    try {
                        emailService.sendVerificationEmail(userEntity.getEmail(), generatedString, userEntity.getName());
                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                    userEntity.setCode(generatedString);
                }



            }else{
                userEntity = new UserEntity();
                userEntity.setActivated(false);
                userEntity.setUsername(username);
                userEntity.setRole(Role.USER);
                userEntity.setName(firstName);
                userEntity.setSurname(surname);
                userEntity.setEmail(email);
                userEntity.setId(null);
                userEntity = userEntityRepository.saveAndFlush(userEntity);
                entityManager.refresh(userEntity);
            }







        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return mapper.map(userEntity,User.class);
    }



@Override
    public JWTUser verifyCode(Integer id, String code) {
        UserEntity user = userEntityRepository.getById(id);
        JWTUser res = null;
        Authentication authentication = null;
        res = mapper.map(userService.findByUsername(user.getUsername()), JWTUser.class);
        res.setToken(generateJWT(res));
        System.out.println(user.getCode());
        if (user.getCode().equals(code)) {
            return res;
        } else return null;
    }


    private String generateJWT(JWTUser user) {
        System.out.println(user.getRole().toString());
        return Jwts.builder().setId(user.getId().toString()).setSubject(user.getUsername()).claim("role", user.getRole().toString()).setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationDate))).signWith(SignatureAlgorithm.HS512, tokenSecert).compact();
    }
}

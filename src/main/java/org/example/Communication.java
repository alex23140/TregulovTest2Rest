package org.example;

import org.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class Communication {

    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "http://91.241.64.178:7081/api/users";
    private String cookie;

    public List<User> getAllUsers() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        cookie = forEntity.getHeaders().getFirst("Set-Cookie");
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        List<User> allUsers = responseEntity.getBody();
        return allUsers;
    }

    public void saveUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", cookie);
        HttpEntity requestEntity = new HttpEntity(user, requestHeaders);
        ResponseEntity response = restTemplate.exchange(URL, HttpMethod.POST, requestEntity, String.class);
        System.out.print(response.getBody());
    }

    public void updateUser(User user1) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", cookie);
        HttpEntity requestEntity = new HttpEntity(user1, requestHeaders);
        ResponseEntity response = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, String.class);
        System.out.print(response.getBody());
    }

    public void delete(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Cookie", cookie);
        User updateUser = new User(3L, "Thomas", "Shelby", (byte) 21);
        HttpEntity requestEntity = new HttpEntity(updateUser, requestHeaders);
        ResponseEntity responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, requestEntity, String.class);
        System.out.print(responseEntity.getBody());
    }

}

package org.example;

import org.example.entities.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Communication {
    private RestTemplate restTemplate;

    private final String URL = "http://94.198.50.185:7081/api/users";
    private List<String> cookie;


    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> getAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        System.out.println("Response: " + response.toString() + "\n");
        System.out.println("Set-Cookie: " + response.getHeaders().getFirst("Set-Cookie") + "\n");
        cookie = response.getHeaders().get("Set-Cookie");
        return response;
    }

    public ResponseEntity<String> saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", cookie.stream().collect(Collectors.joining(";")));
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<User> entity = new HttpEntity<>(user,headers);
        return restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
    }

    public String updateUser(long id, User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", cookie.stream().collect(Collectors.joining(";")));
        HttpEntity<User> entity = new HttpEntity<User>(user,headers);
        return restTemplate.exchange(
                URL+id, HttpMethod.PUT, entity, String.class).getBody();
    }

    public String deleteUser(long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Cookie", cookie.stream().collect(Collectors.joining(";")));
        HttpEntity<User> entity = new HttpEntity<User>(headers);

        return restTemplate.exchange(
                "http://localhost:8080/products/"+id, HttpMethod.DELETE, entity, String.class).getBody();
    }
}

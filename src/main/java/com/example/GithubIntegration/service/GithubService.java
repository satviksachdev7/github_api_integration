package com.example.GithubIntegration.service;

import com.example.GithubIntegration.model.GithubRepo;
import com.example.GithubIntegration.model.GithubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class GithubService {
    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public GithubService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public GithubUser fetchGithubUser(String username) {
        String url = "https://api.github.com/users/" + username;
        return restTemplate.getForObject(url, GithubUser.class);
    }

    public List<GithubRepo> fetchGithubRepos(String username) {
        String url = "https://api.github.com/users/" + username + "/repos";
        GithubRepo[] repos = restTemplate.getForObject(url, GithubRepo[].class);
        if (repos == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(repos);
    }

}


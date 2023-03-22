package com.example.GithubIntegration.controller;

import com.example.GithubIntegration.model.GithubRepo;
import com.example.GithubIntegration.model.GithubUser;
import com.example.GithubIntegration.service.GitHubUserResponse;
import com.example.GithubIntegration.service.GithubService;
import com.example.GithubIntegration.service.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
public class GithubController {
    private final GithubService githubService;
    private final RedisService redisService;

    public GithubController(GithubService githubService, RedisService redisService) {
        this.githubService = githubService;
        this.redisService = redisService;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<GitHubUserResponse> getUserInfo(@PathVariable("username") String username) {
        String userCacheKey = "user:" + username;
        String reposCacheKey = "repos:" + username;

        GithubUser githubUser = redisService.getCacheValue(userCacheKey, GithubUser.class);
        List<GithubRepo> githubRepos = redisService.getCacheValue(reposCacheKey, List.class);

        if (githubUser == null) {
            githubUser = githubService.fetchGithubUser(username);
            redisService.setCacheValue(userCacheKey, githubUser, 1, TimeUnit.HOURS);
        }

        if (githubRepos == null) {
            githubRepos = githubService.fetchGithubRepos(username);
            redisService.setCacheValue(reposCacheKey, githubRepos, 1, TimeUnit.HOURS);
        }

        GitHubUserResponse response = new GitHubUserResponse(githubUser, githubRepos);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleHttpClientErrorException(HttpClientErrorException e) {
        String errorMessage = "Error fetching data from GitHub for the requested user. Please check the username and try again.";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}

package com.example.GithubIntegration.service;

import com.example.GithubIntegration.model.GithubRepo;
import com.example.GithubIntegration.model.GithubUser;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GitHubUserResponse {
    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("geo_location")
    private String geoLocation;
    @JsonProperty("email")
    private String email;
    @JsonProperty("url")
    private String url;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("repositories")
    private List<GithubRepo> repositories;

    public GitHubUserResponse(GithubUser user, List<GithubRepo> repos) {
        this.userName = user.getLogin();
        this.displayName = user.getName();
        this.avatar = user.getAvatar_url();
        this.geoLocation = user.getLocation();
        this.email = user.getEmail();
        this.url = user.getHtml_url();
        this.createdAt = user.getCreatedAt();
        this.repositories = repos;
    }

}

package com.example.GithubIntegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepo {
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}

package com.example.GithubIntegration;

import com.example.GithubIntegration.model.GithubRepo;
import com.example.GithubIntegration.model.GithubUser;
import com.example.GithubIntegration.service.GithubService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@SpringBootTest
public class GithubServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private GithubService githubService;

    @Before
    public void setup() {

        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        githubService = new GithubService(restTemplateBuilder);
    }

    @Test
    public void testFetchGithubUserSuccess() {
        // Setup
        String username = "octocat";
        String url = "https://api.github.com/users/" + username;
        GithubUser githubUser = new GithubUser("octocat", "The Octocat", "https://avatars.githubusercontent.com/u/583231?v=4", "San Francisco", null, "https://github.com/octocat", "2011-01-25T18:44:36Z");

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.getForObject(url, GithubUser.class)).thenReturn(githubUser);

        RestTemplateBuilder mockRestTemplateBuilder = mock(RestTemplateBuilder.class);
        when(mockRestTemplateBuilder.build()).thenReturn(mockRestTemplate);

        GithubService githubService = new GithubService(mockRestTemplateBuilder);


        // Execute
        GithubUser result = githubService.fetchGithubUser(username);

        // Verify
        assertEquals(githubUser, result);
    }

    @Test
    public void testFetchGithubUserFailure() {
        // Setup
        String username = "invalid_username";
        String url = "https://api.github.com/users/" + username;

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.getForObject(url, GithubUser.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "Not Found"));

        RestTemplateBuilder mockRestTemplateBuilder = mock(RestTemplateBuilder.class);
        when(mockRestTemplateBuilder.build()).thenReturn(mockRestTemplate);

        GithubService githubService = new GithubService(mockRestTemplateBuilder);

        // Execute and verify
        assertThrows(HttpClientErrorException.class, () -> {
            githubService.fetchGithubUser(username);
        });
    }

    @Test
    public void testFetchGithubReposSuccess() {
        // Setup
        String username = "octocat";
        String url = "https://api.github.com/users/" + username + "/repos";
        GithubRepo[] repos = new GithubRepo[] {
                new GithubRepo("boysenberry-repo-1", "https://github.com/octocat/boysenberry-repo-1"),
                new GithubRepo("git-consortium", "https://github.com/octocat/git-consortium")
        };
        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.getForObject(url, GithubRepo[].class)).thenReturn(repos);

        RestTemplateBuilder mockRestTemplateBuilder = mock(RestTemplateBuilder.class);
        when(mockRestTemplateBuilder.build()).thenReturn(mockRestTemplate);

        GithubService githubService = new GithubService(mockRestTemplateBuilder);

        // Execute
        List<GithubRepo> result = githubService.fetchGithubRepos(username);

        // Verify
        assertEquals(Arrays.asList(repos), result);
    }

    @Test
    public void testFetchGithubReposFailure() {
        // Setup
        String username = "invalid_username";
        String url = "https://api.github.com/users/" + username + "/repos";

        RestTemplate mockRestTemplate = mock(RestTemplate.class);
        when(mockRestTemplate.getForObject(url, GithubRepo[].class)).thenReturn(null);

        RestTemplateBuilder mockRestTemplateBuilder = mock(RestTemplateBuilder.class);
        when(mockRestTemplateBuilder.build()).thenReturn(mockRestTemplate);

        GithubService githubService = new GithubService(mockRestTemplateBuilder);


        // Execute
        List<GithubRepo> result = githubService.fetchGithubRepos(username);

        // Verify
        assertEquals(Collections.emptyList(), result);
    }
}

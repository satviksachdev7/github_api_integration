# GitHub User Info

GitHub User Info is a RESTful web service that provides user and repository information for a given 
GitHub username. The application uses the GitHub API to fetch user and repository data, and 
Redis as a caching mechanism to improve performance.

## Architecture

The application is built using Spring Boot and follows a layered architecture:

1. **Controller Layer**: Handles incoming HTTP requests and maps them to appropriate service methods.
2. **Service Layer**: Contains business logic and calls the GitHub API to fetch user and repository data.
3. **Data Access Layer**: Stores and retrieves user and repository information in the cache using Redis.

### Breakdown

    GithubController: Contains the endpoints for fetching a user's GitHub information and repositories.
    GithubService: Handles fetching the user's GitHub information and repositories from the GitHub API.
    RedisService: Handles caching and retrieval of data from Redis.
    GithubUser: POJO that represents a GitHub user's information.
    GithubRepo: POJO that represents a GitHub repository.
    GitHubUserResponse: POJO that represents the response returned by the AP

## Technology Choices
1. **Spring Boot**: We have used Spring Boot, a popular Java-based framework, to create a scalable and easy-to-maintain RESTful web service.
2. **GitHub REST API**: We use GitHub REST API to fetch user information and repositories. No authentication is required for this as we are only accessing public information.
3. **Redis**: Redis is used as an in-memory data store to cache the GitHub user information and repositories, reducing the number of calls to the GitHub REST API and improving response time.
4. **Lombok**: Project Lombok is a Java library that helps reduce boilerplate code in Java classes. We use it to generate getters, setters, and constructors automatically.
5. **RestTemplate**: Used to call the GitHub REST API

## Prerequisites

- Java 11 or higher
- Maven 3.6.3 or higher
- Redis 6.0.9 or higher

## Running the Project

1. Clone the repository to your local machine.
2. Open a terminal and navigate to the project root directory.
3. Start Redis server by running the command:\
``redis-server``
4. Setup the project:\
``mvn clean install``
5. Start the Spring Boot application by running the command:\
``./mvnw spring-boot:run``

## Usage
### Get GItHub User Info
Send a GET request to the following endpoint to retrieve a GitHub user's information:

``GET http://localhost:8080/api/user/{username}``

Replace {username} with the GitHub username of the user you want to retrieve information for.

### Example Request: 
``GET http://localhost:8080/api/user/octocat``

### Example Response:
``{
"user_name": "octocat",
"display_name": "The Octocat",
"avatar": "https://avatars3.githubusercontent.com/u/583231?v=4",
"geo_location": "San Francisco",
"email": null,
"url": "https://github.com/octocat ",
"created_at": "2011-01-25 18:44:36",
"repos": [
{
"name": "boysenberry-repo-1",
"url": "https://github.com/octocat/boysenberry-repo-1"
},
{
"name": "git-consortium",
"url": "https://github.com/octocat/git-consortium"
},
{
"name": "hello-worId",
"url": "https://github.com/octocat/hello-worId"
},
{
"name": "Hello-World",
"url": "https://github.com/octocat/Hello-World"
},
{
"name": "linguist",
"url": "https://github.com/octocat/linguist"
},
{
"name": "octocat.github.io",
"url": "https://github.com/octocat/octocat.github.io"
},
{
"name": "Spoon-Knife",
"url": "https://github.com/octocat/Spoon-Knife"
},
{
"name": "test-repo1",
"url": "https://github.com/octocat/test-repo1"
}
]
}
``
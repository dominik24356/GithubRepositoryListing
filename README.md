# GitHub Repository Listing

An API application for retrieving information about GitHub repositories.

## Technologies Utilized
- Spring Boot 3
- Java 21
- RESTful API
- Integration with the GitHub API

## Endpoints
- **GET /api/repository/{owner-login}**: Retrieve all repositories for a given GitHub user.
  - Parameters:
    - {owner-login}: The login/username of the GitHub user.
  - Response:
    - Status Code: 200 OK
    - Body: List of GitHub repositories including repository name, owner login, and branches with their last commit SHA.
  - Error Handling:
    - 404 Not Found: If the user does not exist.
    - 500 Internal Server Error: If there is an error while communicating with the GitHub API.

## Exception Handling
- `EntityNotFoundException`: Thrown when the requested user or repository is not found.
- `GithubException`: Thrown when there is an error while communicating with the GitHub API.

## Dependencies
- Spring Boot Starter Web
- Spring Boot Starter Test
- Jakarta Persistence API
- Junit

## Testing
- Unit tests are provided for `GithubBranchService`, `GithubRepositoryController`, and `GithubRepositoryService`.

## License
- This project is licensed under the [MIT License](LICENSE).

---


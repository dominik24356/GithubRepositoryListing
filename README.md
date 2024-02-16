# GitHub Repository Listing

An API application for retrieving information about GitHub repositories.

## Technologies Utilized
- Spring Boot 3
- Java 21
- RESTful API
- Integration with the GitHub API
- Maven

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

## How to Run
To run the program in an IDE environment such as IntelliJ IDEA, follow these steps:
1. Clone the repository to your local machine.
2. Open the project in IntelliJ IDEA.
3. Ensure that you have Java 21 installed and configured in your IDE.
4. Build the project to resolve dependencies.
5. Run the `Application` class to start the Spring Boot application.

## Testing with Postman
To test the functionality using Postman, follow these steps:
1. Ensure that the application is running locally or on a server accessible to Postman.
2. Open Postman.
3. Set the request type to `GET`.
4. Enter the URL endpoint: `http://localhost:8080/api/repository/{owner-login}`.
5. Replace `{owner-login}` with the GitHub username you want to retrieve repositories for.
6. Send the request and observe the response.

## License
- This project is licensed under the [MIT License](LICENSE).

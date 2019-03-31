# spring-boot-microservices
Rebuild REST APIs using Microservice Archtecture. 

## Architectures Desgin and Technologies
<pre>
1, Registry Server (Eureka Server): Eureka Naming Server will register all services.
2, Gateway  (Zuul): Gateway will validate JWT token and forward all valid incoming requests based on urls. If not token, will deny request. If 
   calling for authenticate, request will forward to Authentication Server.
3, Authentication Server (Eureka client): Validate User by check username and password in Database or using Oauth2 (Social Login).
    Using Java Keystores to store Private and Public key pair. JWT is created and signed with Private key. Expose an endpoint to ask for Public key.
3, Config Server: Centralized place for properties. Geting property files from Github repository.
4, 
</pre>


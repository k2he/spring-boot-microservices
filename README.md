# spring-boot-microservices
Rebuild REST APIs using Microservice Archtecture. 

## Architectures Desgin and Technologies
<pre>
1, <b>Registry Server (Eureka Server)</b>: Eureka Naming Server will register all services.

2, <b>Gateway  (Zuul)</b>: Gateway will validate JWT token and forward all valid incoming requests based on urls. If 
   not token, will deny request. If calling for authenticate, request will forward to Authentication Server.
   
3, <b>Authentication Server (Eureka client)</b>: Validate User by check username and password in Database or using 
   Oauth2 (Social Login).
   Using Java Keystores to store Private and Public key pair. JWT is created and signed with Private key. Expose 
   an endpoint to get Public key.
    
3, <b>Config Server</b>: Centralized place for properties. Geting property files from Github repository.

4, <b>Project Service (Eureka clients)</b>: REST API endpoints to perform Project related operations (JPA) 

5, <b>Contact Service (Eureka clients)</b>: REST API endpoints to perform Contact related operations (JPA)

6, <b>Test Service (Eureka clients)</b>: This is for test purpose. REST API endpoints for test purpose. 
   To test <b>Retry</b>, <b>Circuit Breaker</b>
   
7, <b>service-libs</b> package: Lib for share code used by other services. 
   If code changed, need run: mvn clean install
</pre>


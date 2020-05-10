# spring-boot-microservices
Build REST APIs using Microservice Archtecture. Desgin diagram as below: 
<img src="https://github.com/k2he/spring-boot-microservices/blob/master/architecture_design.JPG">

## Architectures Desgin and Technologies
<pre>
1, <b>Registry Server (Eureka Server)</b>: Eureka Naming Server will register all services.

2, <b>Gateway  (Zuul)</b>: Gateway will validate JWT token and forward all valid incoming requests based on urls. If 
   not token, will deny request. If calling for authenticate, request will forward to Authentication Server.
   
3, <b>Authentication Server (Eureka client)</b>: Validate User by check username and password in Database or using 
   Oauth2 (Social Login).
   Using Java Keystores to store Private and Public key pair. JWT is created and signed with Private key. Expose 
   an endpoint to get Public key.
    
3, <b>Config Server Eureka clients</b>: Centralized place for properties. Geting property files from Github repository.

4, <b>Project Service (Eureka clients)</b>: REST API endpoints to perform Project related operations (JPA) 

5, <b>Contact Service (Eureka clients)</b>: REST API endpoints to perform Contact related operations (JPA)

6, <b>Test Service (Eureka clients)</b>: This is for test purpose. REST API endpoints for test purpose. 
   To test <b>Retry</b>, <b>Circuit Breaker</b>
   
7, <b>Sleuth and Zipkin Server</b>: Call trace is sending to Zipking Server, it's help us to trace calls and debug.

8, <b>service-libs</b> package: Lib for share code used by other services. 
   If code changed, need run: mvn clean install
</pre>

## Security Design and workflow
<pre>
Using JWT token (included Expiration time and User role) to validate User access. First time when user trying to 
login, redirect to Authentication Server.

<b>"Authentication Server Design"</b>
1) If user choose to login using username and password, then validate again User information stored in Database 
   along with User role. 
2) If user choose to login using social login, redirect to Social Login page, if user grant access (email and 
   profile). System will automatically create a new account using Email.

On success login, JWT token will generated containing User roles, and sign with Private Key from Key Store, 
and return as login response. Also and there is a REST endpoint to access Public Key.

<b>"Public & Private Key"</b>
Public and Private key are stored in Key Store to prevent outside getting private key.

<b>"Each Individual Services"</b>
Call to Each individule Service also need be validated using JWT token to improve security on individual 
service level. 
Each Service will fetch Public key from Auth Server and then validate incoming JWT token. If validation 
fail, request get rejected. If success, resoure will be returned.

<b>"Backend Internationalization (i18n)"</b>
MessageService in service-lib shared by all service to handle backend internationalization.

<b>"Error Handling"</b>
ApplicationException -- Business Logic Error
InvalidRequestException -- Invalid Request Error
ResourceNotFoundException -- Resource Not Found Error
SystemException -- Any System Exception, not longer be able to process further.
ValidationException -- Bean validation failed.
ServiceExceptionHandler -- @ControllerAdvice intercept all Exception and Convert to below format. 

eg: 
ResourceNotFoundException.java will return below error message:
{
    "status": {
        "serverStatusCode": "404",
        "severity": "Error",
        "additionalStatus": [
            {
                "statusCode": null,
                "serverStatusCode": "demo-project-err-1002",
                "severity": "Error",
                "statusDesc": "Project Not Found."
            }
        ]
    }
}
</pre>

## Deployment
Each service contains a Dockerfile, and docker images are on docker hub:

https://cloud.docker.com/repository/list

It can be deployed to Cloud provide using Kubernetes or Cloud Provider have it's own tool for easy deployment. 
For instance:
AWS we can use ECS to deploy docker into EC2 instances and do auto-scaling. 

## TODO List
<pre>
1, Implement CQRS & Event Sourcing using Kafka or Axon framework.

2, Add Centralized logging (ELK Stack): Need check out options and add centralized logging mechanism.

3, Since Kubernete has it's component similar to Registry server and Config Server, need check out what's the best
   way to integrate Spring boot with Kubernete.
   
3, Need check more on best way to handle failure and Delay how to tell Kubernete when to re-create a Pod.
</pre>


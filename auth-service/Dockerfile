FROM openjdk:8-jdk-alpine

LABEL maintainer="kai.he517@hotmail.com"

VOLUME /tmp

#Add this in order to run .sh files
RUN apk add --no-cache bash

#If you want to include this wait-for-it.sh, then uncomment below two lines. (I use volumn in docker-compose to copy over, so no need it here)
#COPY wait-for-it.sh /wait-for-it.sh
#RUN chmod +x /wait-for-it.sh

# The application's jar file
ARG JAR_FILE=target/springboot-microservice-auth-service-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file 
ENTRYPOINT ["java", "-jar", "/app.jar"]
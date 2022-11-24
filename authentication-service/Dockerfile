FROM openjdk
WORKDIR usr/src
ENV MYSQL_USERNAME=root
ENV MYSQL_PASSWORD=Vishnu@1
ADD ./target/authentication-service-0.0.1-SNAPSHOT.jar /usr/src/authentication-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","authentication-service-0.0.1-SNAPSHOT.jar"]
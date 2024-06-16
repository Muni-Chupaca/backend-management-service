FROM eclipse-temurin:21

COPY ./target/api-service-0.0.1-SNAPSHOT.jar /

RUN chmod +x /api-service-0.0.1-SNAPSHOT.jar
RUN sh -c 'touch api-service-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","/api-service-0.0.1-SNAPSHOT.jar"]


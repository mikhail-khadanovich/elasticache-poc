FROM openjdk:11-jre-slim
VOLUME /tmp
COPY target/elasticache-poc-0.0.1-SNAPSHOT.jar ./
ENTRYPOINT ["java","-jar","./elasticache-poc-0.0.1-SNAPSHOT.jar"]
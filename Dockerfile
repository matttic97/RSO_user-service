FROM adoptopenjdk:15-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/user-api-1.0-SNAPSHOT.jar /app

EXPOSE 8080

CMD ["java", "-jar", "user-api-1.0-SNAPSHOT.jar"]
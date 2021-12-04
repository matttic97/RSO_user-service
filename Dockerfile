FROM adoptopenjdk/openjdk11

RUN mkdir -p /software

ADD target/userService.jar /software/userService.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /software/userService.jar
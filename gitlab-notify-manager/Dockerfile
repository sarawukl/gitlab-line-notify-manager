FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/gitlablinemgr-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.4.0/wait /wait
RUN chmod +x /wait
## Launch the wait tool and then your application
CMD /wait && java -Djava.security.egd=file:/dev/./urandom -jar /app.jar

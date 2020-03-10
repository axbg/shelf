FROM devayansarkar/maven-node-openjdk
EXPOSE 8080

# Front-end build
WORKDIR /front
COPY front .
RUN cp .env .env.prod && npm install && npm run build

# Back-end build
WORKDIR /back
COPY back .
RUN mkdir -p ./src/main/resources/static && mv ../front/dist/* ./src/main/resources/static && mvn -DskipTests package

#Run app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "./target/shelf-0.0.1-SNAPSHOT.jar"]
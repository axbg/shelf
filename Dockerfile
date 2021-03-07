FROM axbg/node-maven
EXPOSE 8080

# Front-end build
WORKDIR /front
COPY front .
RUN cp .env .env.prod

# Disable SSL that causes error on arm/v7
RUN npm config set registry http://registry.npmjs.org/
RUN npm install && npm run build

# Back-end build
WORKDIR /back
COPY back .
RUN mkdir -p ./src/main/resources/static && mv ../front/dist/* ./src/main/resources/static 
RUN mvn -DskipTests package

#Run app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "./target/shelf-0.0.1-SNAPSHOT.jar"]
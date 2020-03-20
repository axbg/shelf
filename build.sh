# Build front-end
cd front
npm install
npm run build

# Copy static resources
cd ..
mkdir -p back/src/main/resources/static
cp -R front/dist/* ./back/src/main/resources/static

# Build back-end
cd back
mvn package

cp target/shelf-0.0.1-SNAPSHOT.jar ..
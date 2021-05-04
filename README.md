# shelf
#### A progressive web-app & Chrome extension to bookmark interesting sites on the go

##### Development deployment
* [Create a Google Cloud Project and configure Web Application credentials](https://support.google.com/cloud/answer/6158849?hl=en)
* Back
    * Install >=Java11
    * Install >=Maven3.6.0
    * Configure a MySQL server
        * Fastest: spin a MySQL Docker container
    * Configure application.properties and create a database
        * The default values for database connection:
            * empty root password
            * database name: shelf
        * You should also change the default values shelf.app.jwt.secret, shelf.app.jwt.expiration
        * Change the value of shelf.app.google.id with your google client id
    * cd back
    * ```mvn package```
    * ```java -jar target/shelf-0.0.1-SNAPSHOT.jar```
* Front
    * Install Nodejs
    * cd front
    * In front/.env
      * Set VUE_APP_GOOGLE_CLIENT_ID equal to your google client id 
        * should be the same as above
      * Set VUE_APP_BASE_URL equal to http://BACK_URL:PORT/api
        * ex: http://localhost:8080/api
    * ```npm install```
    * ```npm run serve```
* Extension
    * Create Chrome App credentials in your GCP Projects
    * Create a chrome developer project and load the 'extension' directory
    * Open extension/manifest.json and fill:
        * key: the public key provided in the [chrome developer console](https://chrome.google.com/webstore/developer/dashboard) in the provided format (don't remove new lines)
        * client_id: the same client id used in the back-end
    * Open extension/js/utils.js and fill:
         * baseUrl: the url under which your back-end application is deployed
         * frontUrl: the url under which your front-end application is deployed (in production it should be the same with baseUrl)
    * Navigate to chrome://extensions
    * Enable developer mode
    * Load unpacked
    * Select the *extension* directory

##### Production deployment
* Manually
  * Really really manually
    * Front
        * Repeat the same steps untill ```npm install``` (included)
        * ```npm run build```
        * Copy the content produced in the dist/ directory to back/src/main/resources/
    * Back
        * The same steps as in development deployment
  * After you complete the required credentials mentioned above you can run the ```bash.sh``` script that will produce a runnable jar in the root of the project
* Using Docker
  * Prepare a MySQL database
  * Fill docker.env with suitable values
    * JWT_EXPIRATION is expressed in miliseconds
  * ```docker run -d -p 8080:8080 --env-file ./env --name shelf axbg/shelf```

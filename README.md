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
        * Change the value of shelf.app.google.id with the Web Application client id from your GCP Project 
    * cd back
    * ```mvn package```
    * ```java -jar target/shelf-0.0.1-SNAPSHOT.jar```
* Front
    * Install Nodejs
    * cd front
    * Modify Vue.prototype.$googleClientId in front/src/main.js with the same client_id used for back-end
    * ```npm install```
    * ```npm run serve```
* Extension
    * Create Chrome App credentials in your GCP Projects
    * Create a chrome developer project and load the 'extension' directory
    * Open extension/manifest.json and fill:
        * key: the public key provided in the [chrome developer console](https://chrome.google.com/webstore/developer/dashboard) *more information* box
        * client_id: the same client id used in the back-end
    * Navigate to chrome://extensions
    * Enable developer mode
    * Load unpacked
    * Select the *extension* directory

##### Production deployment
* Front
    * Repeat the same steps untill ```npm install``` (included)
    * ```node prepare-manifest.js```
    * Copy the content produced in the dist/ directory to back/src/main/resources/
* Back
    * The same steps as in development deployment

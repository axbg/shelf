const getAuthCookie = function (domain, cookieName) {
    return new Promise((resolve, reject) => {
        chrome.cookies.get({ url: domain, name: cookieName }, function (cookie) {
            if (cookie) {
                resolve(cookie);
            } else {
                resolve(null);
            }
        });
    });
};

const getCurrentUrl = async function () {

};

const login = async function () {
    //call login endpoint 
    //update the X-AUTH token for the domain
    return true;
};

const checkCurrentUrl = async function () {
    //get currentUrl
    //call checkCurrentUrl endpoint
    //return the result 
    return true;
};

const addItem = async function () {

};

const removeItem = async function () {

}
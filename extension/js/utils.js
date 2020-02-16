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
    return new Promise((resolve, reject) => {
        chrome.tabs.query({active: true, lastFocusedWindow: true}, function(tabs) {
            resolve({url: tabs[0].url, favicon: tabs[0].favIconUrl, title: tabs[0].title});
        });
    });
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
const baseUrl = "https://shelf.axbg.tech";

// in production it should be the same as baseUrl
const frontUrl = baseUrl;

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
        chrome.tabs.query({ active: true, lastFocusedWindow: true }, function (tabs) {
            if (tabs[0].url && tabs[0].favIconUrl && tabs[0].title) {
                resolve({ url: tabs[0].url, photo: tabs[0].favIconUrl, title: tabs[0].title });
            } else {
                resolve(null)
            }
        });
    });
};

const login = async function (idToken) {
    const result = await fetch(baseUrl + "/api/user/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({ gToken: idToken })
    });

    return result.status === 200 ? true : false;
};

const checkCurrentUrl = async function () {
    const currentUrl = await getCurrentUrl();

    if (currentUrl) {
        const response = await fetch(baseUrl + "/api/item/check?url=" + currentUrl.url, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            }
        });

        return response.status === 200 ? true :
            (response.status === 404) ? false : null;
    } else {
        alert("Page is invalid");
        window.close();
    }
};

const addItem = async function () {
    const currentUrl = await getCurrentUrl();
    const response = await fetch(baseUrl + "/api/item/", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(currentUrl)
    });

    return response.status === 201 ? true : false;
};

const removeItem = async function () {
    const currentUrl = await getCurrentUrl();
    const response = await fetch(baseUrl + "/api/item?url=" + currentUrl.url, {
        method: "DELETE",
        credentials: "include",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ url: currentUrl.url })
    });

    return response.status === 200 ? true : false;
};
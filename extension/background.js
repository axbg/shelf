chrome.tabs.onUpdated.addListener(async function (tabId, changeInfo, tab) {
    if (changeInfo.status === "complete") {
        const cookie = await getAuthCookie(frontUrl);
        if (cookie) {
            const result = await checkCurrentUrl(false);
            if (result) {
                changeIcon(tabId, true);
            }
        }
    }
});

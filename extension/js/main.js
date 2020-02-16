window.onload = async function () {
    const cookie = await getAuthCookie("http://google.com", "CONSENT");

    if (cookie) {
        const result = await executeCheckCurrentUrl();
        if (result) {
            return;
        }
    }

    handleLoginProcess();
}
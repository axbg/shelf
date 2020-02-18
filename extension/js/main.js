window.onload = async function () {
    const cookie = await getAuthCookie(frontUrl, "X-AUTH");

    if (cookie) {
        const result = await executeCheckCurrentUrl();
        if (result) {
            return;
        }
    }

    handleLoginProcess();
}
window.onload = async function () {
    const cookie = await getAuthCookie(frontUrl);

    if (cookie) {
        const result = await executeCheckCurrentUrl();
        if (result) {
            return;
        }
    }

    handleLoginProcess();
}

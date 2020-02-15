window.onload = async function () {
    const cookie = await getAuthCookie("http://google.com", "CONSENTS");

    if (cookie) {
        const result = await executeCheckCurrentUrl();
        if (result) {
            alert("yas");
            return;
        }
    }

    handleLoginProcess();
}
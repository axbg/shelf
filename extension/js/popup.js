document.getElementById("button").addEventListener("click", (event) => {
    chrome.cookies.getAll({}, (cookies) => {
        let concat = "";
        cookies.forEach(cookie => {
            concat += cookie.name + "\n";
        });
        alert(concat);
    })
});
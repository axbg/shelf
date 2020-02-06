const toggleLoader = function(state){
    if(state) {
        document.getElementById("loader").style.display = "block";
        document.getElementById("button").style.display = "none";
    } else {
        document.getElementById("loader").style.display = "none";
        document.getElementById("button").style.display = "block";
    }
}

document.getElementById("button").addEventListener("click", (event) => {
    toggleLoader(true);
    chrome.cookies.getAll({}, (cookies) => {
        let concat = "";
        cookies.forEach(cookie => {
            concat += cookie.name + "\n";
        });
        setTimeout(() => {
            toggleLoader(false);
        }, 1000);
    })
});
document.getElementById("add").addEventListener("click", function () {
    handleAddItem();
});

document.getElementById("remove").addEventListener("click", function () {
    handleRemoveItem();
});

const toggleLoader = function (state) {
    if (state) {
        document.getElementById("loader").style.display = "block";
    } else {
        document.getElementById("loader").style.display = "none";
    }
}

const toggleAddButton = function (state) {
    const add = document.getElementById("add");
    const remove = document.getElementById("remove");
    if (state) {
        add.style.display = "block";
        remove.style.display = "none";
    } else {
        add.style.display = "none";
        remove.style.display = "block";
    }
}

const toggleCheckResult = function (state) {
    const checkResult = document.getElementById("check-result");
    if (state) {
        checkResult.textContent = "✔";
        toggleAddButton(false);
    } else {
        checkResult.textContent = "✖";
        toggleAddButton(true);
    }

    checkResult.style.display = "block";
};

const hideResult = function () {
    document.getElementById("check-result").style.display = "none";
    document.getElementById("add").style.display = "none";
    document.getElementById("remove").style.display = "none";
}

const bindInteractiveLogin = function () {
    const loginButton = document.getElementById("login");
    toggleLoader(false);
    loginButton.style.display = "block";
    loginButton.addEventListener("click", async function () {
        await getIdTokenInteractively(true);
        window.close();
    });
};

const executeCheckCurrentUrl = async function () {
    let result = false;
    const checkResult = await checkCurrentUrl();

    switch (checkResult) {
        case null:
            result = false;
            break;
        case true:
            result = true;
            toggleLoader(false);
            toggleCheckResult(true);
            break;
        case false:
            result = true;
            toggleLoader(false);
            toggleCheckResult(false);
            break;
    }

    return result;
};

const loginAndCheckCurrentUrl = async function (idToken) {
    await login(idToken);
    await executeCheckCurrentUrl();
    return true;
};

const handleLoginProcess = async function () {
    const idToken = await getIdTokenInteractively(false);
    if (idToken) {
        loginAndCheckCurrentUrl(idToken);
    } else {
        bindInteractiveLogin();
    }
};

const handleAddItem = async function () {
    hideResult();
    toggleLoader(true);

    const result = await addItem();

    toggleLoader(false);
    if (result) {
        toggleCheckResult(true);
    } else {
        toggleCheckResult(false);
    }
};

const handleRemoveItem = async function () {
    hideResult();
    toggleLoader(true);

    const result = await removeItem();

    toggleLoader(false);
    if (result) {
        toggleCheckResult(false);
    } else {
        toggleCheckResult(true);
    }
};
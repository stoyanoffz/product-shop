const email = document.getElementById("email");
const confirmEmail = document.getElementById("confirmEmail");
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirmPassword");
const secretAnswer = document.getElementById("secretAnswer");
const theForm = document.getElementById("theForm");


/**
 * This method creates small element.
 *
 * @param innerText is the text for the element.
 * @param id is the element's Id.
 * @param parentId is the element's parent Id.
 */
function createSmallElement(innerText, id, parentId) {
    const small = document.createElement("small");
    small.innerText = innerText;
    small.className = "form-text text-danger";
    small.id = id;
    document.getElementById(parentId).appendChild(small);
}

/**
 * This method checks if input and confirmInput has the same value and if they do it destroys the small element.
 * However if they have different values it checks
 * if there is an existing small element and if it does not it creates one.
 *
 * @param input is the original input e.g. Email Address.
 * @param confirmInput is the field that checks if inputs are the same e.g. Confirm Email Address.
 * @param innerText is the text for the element.
 * @param id is the element's Id.
 * @param parentId is the element's parent Id.
 */
function displaySmallElement(input, confirmInput, innerText, id, parentId) {
    if (input.value !== confirmInput.value) {
        if (document.getElementById(id) === null) {
            createSmallElement(innerText, id, parentId);
        }
    } else if (document.getElementById(id)) {
        document.getElementById(id).remove();
    }
}

confirmEmail.addEventListener("change", () => {
    displaySmallElement(email, confirmEmail,
                                    "Emails don't match!", "confirmEmailError", "confirmEmailDiv")
});

const validatePassword = function() {
    const hasUpperCase = /[A-Z]/.test(password.value);
    const hasNumbers = /\d+/.test(password.value);
    const hasNonAlphas = /\W/.test(password.value);

    if (password.value.length >= 8 && hasUpperCase && hasNumbers && hasNonAlphas) {
        if (document.getElementById("passwordError")) {
            document.getElementById("passwordError").remove();
        }
    } else if (document.getElementById("passwordError") === null) {
        createSmallElement("Password doesn't satisfy the conditions!",
                    "passwordError", "passwordDiv");
    }
};
password.addEventListener("change", validatePassword);

confirmPassword.addEventListener("change", () => {
    displaySmallElement(password, confirmPassword,
                        "Passwords don't match!", "confirmPasswordError", "confirmPasswordDiv");
});

const validateSecretAnswer = function() {
    if (secretAnswer.value === password.value) {
        if (document.getElementById("secretAnswerError") === null) {
            createSmallElement("Secret answer cannot be the same as your password!",
                    "secretAnswerError", "secretAnswerDiv");
        }
    } else if (document.getElementById("secretAnswerError")) {
        document.getElementById("secretAnswerError").remove();
    }
};
secretAnswer.addEventListener("change", validateSecretAnswer);

theForm.addEventListener("submit", (event) => {
    if (email.value !== confirmEmail.value) {
        event.preventDefault();
        displaySmallElement(email, confirmEmail,
                    "Emails don't match!", "confirmEmailError", "confirmEmailDiv");
        return false;
    }

    if (password.value.length < 8) {
        event.preventDefault();
        validatePassword;
        return false;
    }

    if (password.value !== confirmPassword.value) {
        event.preventDefault();
        displaySmallElement(password, confirmPassword,
                    "Passwords don't match!", "confirmPasswordError", "confirmPasswordDiv");
        return false;
    }

    if (password.value === secretAnswer.value) {
        event.preventDefault();
        validateSecretAnswer;
        return false;
    }
});

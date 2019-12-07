const email = document.getElementById("email");
const theForm = document.getElementById("theForm");
const button = document.getElementById("submitButton");
const buttonDiv = document.getElementById("buttonDiv");
const httpRequest = new XMLHttpRequest();

const CONSTANTS = {
    id : "registrationMessage",
    parentId : "emailDiv",
    successMessage : "Confirmation email was sent.",
    smallElement : "small",
    smallClassSuccess : "form-text text-success",
    smallClassError : "form-text text-danger",
    buttonElement : "button",
    buttonClass : "btn btn-primary",
    buttonInnerText : "Resend code",
    buttonType : "submit",
    inputElement : "input",
    inputId : "tokenId",
    inputType : "hidden",
    invalidEmailError : "Please, provide valid email address.",
    httpPostMethod : "POST",
    registerEndPoint : "/api/users/register",
    resendEndPoint : "/api/users/register/resendcode",
    httpRequestHeader : "Content-Type",
    httpHeaderValue : "application/json; charset=UTF-8"
}

const createSmallElement = (innerText, textType, id, parentId) => {
    const small = document.createElement(CONSTANTS.smallElement);
    small.innerText = innerText;
    small.className = textType;
    small.id = id;
    document.getElementById(parentId).appendChild(small);
}

const displaySmallElement = (innerText, textType, id, parentId) => {
    if (document.getElementById(id) === null) {
        createSmallElement(innerText, textType, id, parentId);
    } else if (document.getElementById(id)) {
        document.getElementById(id).remove();
        createSmallElement(innerText, textType, id, parentId);
    }
}

const createResendCodeButton = () => {
    const resendButton = document.createElement(CONSTANTS.buttonElement);
    resendButton.type = CONSTANTS.buttonType;
    resendButton.innerText = CONSTANTS.buttonInnerText;
    resendButton.className = CONSTANTS.buttonClass;
    button.remove();
    buttonDiv.appendChild(resendButton);
}

const createHiddenInput = (value) => {
    const input = document.createElement(CONSTANTS.inputElement);
    input.id = CONSTANTS.inputId;
    input.type = CONSTANTS.inputType;
    input.value = value;
    buttonDiv.appendChild(input);
}

const hasHiddenInput = () => {
    const input = document.getElementById(CONSTANTS.inputId);

    if (input !== null) {
        return true;
    }

    return false;
}

const alertContents = () => {
    if (httpRequest.readyState === XMLHttpRequest.DONE) {
        if (httpRequest.status === 200) {
            displaySmallElement(CONSTANTS.successMessage,
                                CONSTANTS.smallClassSuccess,
                                CONSTANTS.id,
                                CONSTANTS.parentId);
            createResendCodeButton();
            createHiddenInput(httpRequest.response);
        } else {
            displaySmallElement(httpRequest.responseText,
                                CONSTANTS.smallClassError,
                                CONSTANTS.id,
                                CONSTANTS.parentId);
        }
    }
}

theForm.addEventListener("submit", (event) => {
    const emailValue = email.value;
    event.preventDefault();

    if (!/\S+@\S+\.\S+/.test(emailValue)) {
        displaySmallElement(CONSTANTS.invalidEmailError,
                            CONSTANTS.smallClassError,
                            CONSTANTS.id,
                            CONSTANTS.parentId);
    } else if (hasHiddenInput) {

    }else {
        httpRequest.onreadystatechange = alertContents;
        httpRequest.open(CONSTANTS.httpPostMethod, CONSTANTS.registerEndPoint, true);
        httpRequest.setRequestHeader(CONSTANTS.httpRequestHeader, CONSTANTS.httpHeaderValue);
        httpRequest.send(JSON.stringify({"username" : emailValue}));
    }

    return false;
});


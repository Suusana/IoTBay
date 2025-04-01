function checkValidity(event) {
    let valid = true;
    // check password
    var pwd = document.getElementById("password").value;
    var confirmPwd = document.getElementById("ConfirmPassword").value;

    if (pwd !== confirmPwd) {
        alert("The passwords don't match!");
        event.preventDefault()
        valid=false;
    }

    //check email
    const email_pattern= /\.[a-z]{1,}$/ // after . dot, there should be a letter that indicates domain
    const email = document.getElementById("email");
    if(!email_pattern.test(email.value)){
        alert("missing domain e.g .com .edu");
        event.preventDefault()
        valid=false;
    }
    return valid;

    //check phoneNumber
    const phoneNumber = document.getElementById("phoneNumber")
    if(phoneNumber.value.length !== 10){
        alert("phone number should be 10 digit");
        event.preventDefault()
        valid=false;
    }
    const pattern= /^0\d{9}/; // ^0 : start with 0, /d{9} : 9 digit
    if(!pattern.test(phoneNumber.value)){
        alert("phone number should start with 0 and without \"-\" dash");
        event.preventDefault()
        valid=false;
    }
    //check postcode
    const postalcode = document.getElementById("postalCode");
    console.log(postalcode.value.length)
    if(postalcode.value.length !== 4){
        alert("Wrong postalcode! Postalcode should be 4 digit.");
        event.preventDefault()
        valid=false;
    }


}

document.addEventListener("submit",checkValidity)

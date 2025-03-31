function checkValidity() {

    // check password
    var pwd = document.getElementById("password").value;
    var confirmPwd = document.getElementById("ComfirmPassword").value;

    if (pwd !== confirmPwd) {
        alert("The passwords don't match!");
        return false;
    }
    return true;
}
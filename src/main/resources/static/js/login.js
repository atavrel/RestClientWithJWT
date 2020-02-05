$(document).ready(function () {
    let token = $('#token').val();
    let email = $('#email').val();
    let roles = $('#roles').val();

    if (token !== "") {
        window.localStorage.setItem("token", token);
        window.localStorage.setItem("email", email);
        window.localStorage.setItem("roles", roles);
        if (localStorage.getItem('roles').indexOf('ADMIN') !== -1) {
            window.location.replace("/admin")
        } else if (localStorage.getItem('roles').indexOf('MANAGER') !== -1) {
            window.location.replace("/manager")
        } else if (localStorage.getItem('roles').indexOf('USER') !== -1) {
            window.location.replace("/home")
        }
    } else {
        if (localStorage.getItem('roles').indexOf('ADMIN') !== -1) {
            window.location.replace("/admin")
        } else if (localStorage.getItem('roles').indexOf('MANAGER') !== -1) {
            window.location.replace("/manager")
        } else if (localStorage.getItem('roles').indexOf('USER') !== -1) {
            window.location.replace("/home")
        }
    }
});
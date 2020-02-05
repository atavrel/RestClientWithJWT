addUserForm = $("#addUserForm");

document.getElementById('submitAddUser').onclick = function (e) {
    e.preventDefault();
    if (validateForm()) {
        const email = $("#email").val();
        $.ajax({
            type: "GET",
            url: 'http://localhost:8075/api/registration/users/email/' + email,
            dataType: "json",
            success: function (response) {
                if (response === true) {
                    ValidationMessage.text("User with such email is already registered");
                    validationMessageBlock.removeClass("alert-success").addClass("alert-danger").show();
                    $("#email").val('');
                } else {
                    $.ajax({
                        method: "POST",
                        url: "http://localhost:8075/api/registration/users",
                        dataType: "json",
                        data: JSON.stringify(formToJSON(addUserForm)),
                        contentType: "application/json; charset=utf-8",
                        success: function () {
                            window.location.replace("/?success");
                        },
                    })
                }
            }
        })
    }
};

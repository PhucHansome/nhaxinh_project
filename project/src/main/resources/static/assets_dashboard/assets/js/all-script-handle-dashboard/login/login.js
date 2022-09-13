
    let btn_sign_in = $('#btn_sign_in');
    btn_sign_in.on("click", () => {
    let username = $('#usernameLo').val();
    let password = $('#passwordLo').val();

    let user = {
    username: username,
    password: password
}

    $.ajax({
    headers: {
    "accept": "application/json",
    "content-type": "application/json"
},
    type: "POST",
    url: "http://localhost:8092/api/auth/login",
    data: JSON.stringify(user)
}).done((data) => {
    App.IziToast.showSuccessAlert("Đăng nhập thành công!!");
    App.catchIdLogin(data.id);
    setTimeout(function () {
    window.location.href = "/home-dashboard";
}, 750);
}).fail((jqXHR) => {
    App.IziToast.showErrorAlert("Đăng nhập thất bại!")
    setTimeout(function () {
    if (jqXHR.responseJSON) {
    $.each(jqXHR.responseJSON, (key, item) => {
    App.IziToast.showErrorAlert(item) ;
})
}
}, 500);

})
})

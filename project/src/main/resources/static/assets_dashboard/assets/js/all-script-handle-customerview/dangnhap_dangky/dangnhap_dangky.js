let page = {
    url: {
        CreateUser: App.BASE_URL_USER + "/register",
        LoginUser: App.BASE_URL_USER + "/login",
        ForgotAPassword: App.BASE_URL_USER_FORGOTAPASSWORD + "/forgot-a-password/",
        getUser: App.BASE_URL_USER_FORGOTAPASSWORD + "/username/"
    },
    element: {},
    loadData: {},
    commands: {},
    dialogs: {
        element: {},
        loadData: {},
        commands: {},
        close: {},
        alertDanger: {},
        inputError: {}
    }
}
page.element.emailReg = $("#reg_email");
page.element.passwordReg = $("#passwordReg")
page.element.passwordCon = $("#passwordCon");
page.element.passwordLo = $("#passwordLo");
page.element.emailLo = $("#username");

page.element.buttonRegister = $(".btn-register");
page.element.buttonLogin = $(".woocommerce-form-login__submit")
page.element.contentLogin = $(".woocommerce")
page.element.contentForgotAPassWord = $(".woocommerce_forgot_Pass")
page.element.btnSendMailCheckPassWord = $(".btn-submit-send-email")

let user = new User();
let role = new Role();


page.dialogs.commands.creatUser = () => {
    page.element.buttonRegister.on("click", function () {
        if (page.element.passwordReg.val() !== page.element.passwordCon.val()) {
            App.IziToast.showErrorAlert("Mật khẩu và nhập lại mật khẩu không giống nhau!")
            return;
        }

        delete user.id;
        user.username = page.element.emailReg.val();
        user.password = page.element.passwordReg.val();
        role.id = 2;
        user.role = role;
        $.ajax({
            headers: {
                "accept": "application/json",
                "content-type": "application/json"
            },
            type: "POST",
            url: page.url.CreateUser,
            data: JSON.stringify(user)
        }).then((data) => {
            console.log(data);
            page.element.emailLo.val(page.element.emailReg.val())
            page.element.passwordLo.val(page.element.passwordReg.val())
            App.IziToast.showSuccessAlert("Bạn đã tạo tài khoản thành công")
            $(".ErrorInCreateUser").addClass("d-none")
        }).fail((jqXHR) => {
            App.IziToast.showErrorAlert("Đăng nhập thất bại!")
            $(".ErrorInCreateUser").removeClass("d-none")
            $(".ErrorInCreateUser").html("")
            if (jqXHR.responseJSON) {
                $.each(jqXHR.responseJSON, (key, item) => {

                    let str = `<ul>
                                    <li>${item}</li>
                                    </ul>
                                `
                    $(".ErrorInCreateUser").append(str)
                })
            }
        })
    })
}

page.dialogs.commands.login = () => {
    page.element.buttonLogin.on("click", function () {
        user.username = page.element.emailLo.val();
        user.password = page.element.passwordLo.val();
        $.ajax({
            headers: {
                "accept": "application/json",
                "content-type": "application/json"
            },
            type: "POST",
            url: page.url.LoginUser,
            data: JSON.stringify(user)
        }).then((data) => {
            App.IziToast.showSuccessAlert("Bạn đã đăng nhập thành công")
            $(".ErrorInLogin").addClass("d-none")
            setTimeout(function () {
                window.location.href = "/";
            }, 750);
        }).fail((jqXHR) => {
            App.IziToast.showErrorAlert("Đăng nhập thất bại!")
            $(".ErrorInLogin").removeClass("d-none")
            $(".ErrorInLogin").html("")
            $.each(jqXHR.responseJSON, (key, item) => {
                if (item === 401) {
                    App.IziToast.showErrorAlert("Mật khẩu hoặc tài khoản không tồn tại!")
                    $(".ErrorInLogin").addClass("d-none")
                    return;
                }
                let str = `<ul>
                                    <li>${item}</li>
                                    </ul>
                                `
                $(".ErrorInLogin").append(str)
            })


        })
    })
}

page.commands.changePageForgotAPassword = () => {
    page.element.contentLogin.addClass("d-none")
    page.element.contentForgotAPassWord.removeClass("d-none")
}

page.commands.getUserByUserName = () => {
    return $.ajax({
        type: "GET",
        url: page.url.getUser + $("#user_login").val(),
    }).done((data) => {
        user = data
    }).fail((jqXHR) => {
        console.log(jqXHR)
        App.SweetAlert.showErrorAlert("Email không tồn tại vui lòng nhập lại!")
        $(".temploadding").html("")
    })
}

page.commands.sendMailCheckPassWord = () => {
    page.element.btnSendMailCheckPassWord.on("click", () => {
        page.commands.getUserByUserName().then(() => {
            $(".temploadding").html("")
            let str = `
                                <div class="loading">Loading&#8230;</div>

                                `
            $(".temploadding").append(str)
            $.ajax({
                headers: {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                type: "PUT",
                url: page.url.ForgotAPassword,
                data: JSON.stringify(user)
            }).done((data) => {
                $(".woocommerce_review_after_Changed").removeClass("d-none")
                page.element.contentForgotAPassWord.addClass("d-none");
                $(".temploadding").html("")

            }).fail((jqXHR) => {
                console.log(jqXHR)
                App.SweetAlert.showErrorAlert("Email không đúng vui lòng nhập lại!")
                $(".temploadding").html("")

            })
        })
    })
}

page.initializeControlEvent = () => {
    page.dialogs.commands.creatUser();
    page.dialogs.commands.login();
    page.commands.sendMailCheckPassWord()
}

$(() => {
    page.initializeControlEvent()
})
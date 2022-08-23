class App {

    static DOMAIN = location.origin;

    static BASE_URL_USER = this.DOMAIN + "/api/auth"


    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }
    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                // icon: '', // icon class

                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topRight',
                message: m,
            });
        }

        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                // icon: '', // icon class
                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topRight',
                message: m,
            });
        }
    }
}

class Role{
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class User {
    constructor(id ,username,password,role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}



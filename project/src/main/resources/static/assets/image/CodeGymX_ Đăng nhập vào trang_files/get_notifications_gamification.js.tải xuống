let notificotions = [];

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function wait_then_get_notifications() {
    notificotions = [];
    fetchNotifications();
    if (notificotions.length == 0) {
        setTimeout(function () {
            fetchNotifications();
        }, 5000);
    }
}

let buttons = document.querySelectorAll(".togglecompletion button");

for (let i = 0; i < buttons.length; i++) {
    buttons[i].addEventListener("click", wait_then_get_notifications);
}

function fetchNotifications() {
    let student_email = getCookie("student_email");
    if (student_email) {
        let url = 'https://andy.codegym.vn/api/notifications/application/james/student/' + student_email;
        fetch(url).then(response => {
            if (response.status !== 200) {
                console.log('Looks like there was a problem. Status Code: ' +
                    response.status);
                return;
            }
            // Examine the text in the response
            response.json().then(function (data) {
                notificotions = data.notifications;
                for (let i = 0; i < data.notifications.length; i++) {
                    notifications(data.notifications[i].content);
                }
            });
        });
    }
}
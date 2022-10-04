
let page = {
    url: {
        CreateColor: App.BASE_URL_COLOR +"/create",
        DeleteColor: App.BASE_URL_COLOR + "/delete-color/"

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
page.element.nameColor = $("#nameColor");
page.element.btncreateColor = $("#createColor");
page.element.btnDeleteColor = $(".btnDeleteColor");
page.element.idColor = $("#idColor");

let productColor = new ProductColor();

let colorId = null;

page.element.btncreateColor.on('click', () => {
    $('#showError').html("")
    productColor.color = page.element.nameColor.val();
    delete productColor.id;

    $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "POST",
        "url": page.url.CreateColor,
        "data": JSON.stringify(productColor)
    }).done((data) => {
        productColor.color = page.element.nameColor.val("");

        Swal.fire({
            icon: 'success',
            title: "Thêm mới màu sắc thành công",
            position: 'top-end',
            showConfirmButton: false,
            timer: 1500
        })
        setTimeout(function () {
            window.location.href = "/color-product-dashboard";
        }, 1000);
    }).fail((jqXHR) => {
        $('#showError').removeClass('d-none').addClass('show');
        if (jqXHR.responseJSON.message) {
            let msg = jqXHR.responseJSON.message;

    let str =               `<ul>
                                <li><label id="message-error" class="error" for="message">${msg}</label></li>
                            </ul>
                            `;

            $('#showError').append(str)
        }
        else if (jqXHR.responseJSON) {
            $.each(jqXHR.responseJSON, (key, item) => {
                let str = `    <ul>
                                    <li> <label id="${key}-error" class="error" for="${key}">${item}</label></li>
                              </ul>
                             `;

                $("#" + key).addClass("error");

                $('#showError').append(str)

            })
        }
})
})

$("#createFormColor").on("hidden.bs.modal", function () {
    $(" #createFormColor #showError")[0].reset();
});

    function handleCloseCreate() {

        $("#createFormColor").on("hidden.bs.modal", () => {
            $('#createFormColor #showError').empty().removeClass('show').addClass('d-none');
        })
    }
handleCloseCreate();

page.commands.handleShowConfirmDeleteColor = () => {
    page.element.btnDeleteColor.on('click', function () {
        let  id = $(this).data("id");
        App.SweetAlert.showConfirmDeleteColor()
            .then((result) => {
                if (result.isConfirmed) {

                    $.ajax({
                        "type": "DELETE",
                        url: page.url.DeleteColor + id,
                    })
                        .done((data) => {
                            console.log(data)
                            Swal.fire({
                                icon: 'success',
                                title: "Xóa màu sắc sản phẩm Thành Công! Bạn sẽ trở về danh sách màu sắc sản phẩm sau 1s",
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(function () {
                                window.location.href = "/color-product-dashboard";
                            }, 1000);
                        })
                        .fail((e) => {
                            console.log(e)
                            App.IziToast.showErrorAlert('Xóa màu sắc sản phẩm  thất bại');
                        });
                }
            })
    })
}
$(function () {

    page.commands.handleShowConfirmDeleteColor();


})

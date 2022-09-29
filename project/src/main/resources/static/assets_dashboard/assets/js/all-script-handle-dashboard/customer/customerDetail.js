let page = {
    url: {
        getAllCustomerInfo: App.BASE_URL_CUSTOMERINFO,
        getCustomerEdit: App.BASE_URL_CUSTOMERINFO + "/edit",
        getAllProvinces: App.BASE_URL_PROVINCE + "/",
        getAllDistricts: App.BASE_URL_PROVINCE + "/district/",
        DeleteCustomer: App.BASE_URL_CUSTOMERINFO + "/delete-soft-customer/"


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
let customerInfo = new CustomerInfo();
let locationRegion = new LocationRegion();
let user = new User();
let customerInfoId = null;


page.element.fullNameUp = $("#fullNameUp");
page.element.phoneUp = $("#phoneUp");
page.element.provinceUp = $("#provinceUp");
page.element.provinceText = $("#provinceUp :selected");
page.element.districtUp = $("#districtUp");
page.element.districText = $("#districtUp :selected");
page.element.addressUp = $("#addressUp");
page.element.idCustomer = $("#idCustomer")
page.element.render_update_finish_row = $("#render_update_finish_row")
page.element.pareData = $(".information_clear");


page.element.btnUpdateCustomer = $(".btnUpdate");
page.element.btnDelete = $(".btn-delete");
page.element.comfirmUpdateCustomer = $("#btnUpdateUser");

let row_render = jQuery.validator.format($.trim(page.element.render_update_finish_row.val().toString()));
page.commands.addRowLine = () => {
    page.element.pareData.append($(row_render(customerInfo.fullName, customerInfo.phone, customerInfo.userName, customerInfo.locationRegion.provinceName, customerInfo.locationRegion.districtName, customerInfo.locationRegion.address)));
}


page.dialogs.commands.getCustomerInfoById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.getAllCustomerInfo + "/" + id
    }).done((data) => {
        customerInfo = data;
    })
}

page.commands.goModalUpdateCustomer = () => {
    page.element.btnUpdateCustomer.on('click', function () {
        page.dialogs.commands.getCustomerInfoById(page.element.idCustomer.val()).then(() => {
            page.element.fullNameUp.val(customerInfo.fullName)
            page.element.phoneUp.val(customerInfo.phone)
            page.element.provinceUp.val(customerInfo.locationRegion.provinceId)
            page.element.addressUp.val(customerInfo.locationRegion.address)
            page.dialogs.commands.drawDistricts(customerInfo.locationRegion.provinceId).then(function () {
                page.element.districtUp.val(customerInfo.locationRegion.districtId);
            })
            $('#modalUpdateUser').modal('show')

        })
    })

}
page.dialogs.commands.doUpdateCustomer = () => {
    page.element.comfirmUpdateCustomer.on('click', function () {
        locationRegion.provinceId = page.element.provinceUp.val();
        locationRegion.provinceName = $("#provinceUp :selected").text();
        locationRegion.districtId = page.element.districtUp.val();
        locationRegion.districtName = $("#districtUp :selected").text();
        locationRegion.address = page.element.addressUp.val();
        customerInfo.id = page.element.idCustomer.val();
        customerInfo.locationRegion = locationRegion;
        customerInfo.fullName = page.element.fullNameUp.val();
        customerInfo.phone = page.element.phoneUp.val();
        $.ajax({
            headers: {
                accept: "application/json",
                "content-type": "application/json"
            },
            type: "PUT",
            url: page.url.getCustomerEdit,
            data: JSON.stringify(customerInfo)
        }).done((data) => {
            customerInfo = data;
            locationRegion = customerInfo.locationRegion
            customerInfo.fullName = page.element.fullNameUp.val("");
            customerInfo.phone = page.element.phoneUp.val("");
            console.log(data)
            App.IziToast.showSuccessAlert("Cập nhật khách hàng thành công");
            $('#modalUpdateUser').modal('hide');
            page.dialogs.commands.drawDetailCustomerUpdate(customerInfo.id);
            page.commands.goModalUpdateCustomer();
        }).fail((jqXHR) => {

            console.log(jqXHR)
            $(' .modal-alert-danger').html('').removeClass('hide').addClass('show');

            if (jqXHR.responseJSON.message) {
                let msg = jqXHR.responseJSON.message;

                let str = `<label id="message-error" class="error" for="message">${msg}</label>`;

                $('#modalUpdateUser .modal-alert-danger').append(str);
            } else if (jqXHR.responseJSON) {
                $.each(jqXHR.responseJSON, (key, item) => {
                    let str = `<label id="${key}-error" class="error" for="${key}">${item}</label>`;
                    $("#" + key).addClass("error");

                    $('#modalUpdateUser .modal-alert-danger').append(str);
                })
            }
        })
        // App.IziToast.showErrorAlert("Cập nhật khách hàng thất bại ");
    })
}
$("#modalUpdateUser").on("hidden.bs.modal", function () {
    $("#frmUpdateUser")[0].reset();
});

function handleCloseModalUpdate() {
    $("#modalUpdateUser").on("hidden.bs.modal", () => {
        $('#modalUpdateUser .modal-alert-danger').empty().removeClass('show').addClass('hide');
    })
}

handleCloseModalUpdate();
page.dialogs.commands.drawDetailCustomerUpdate = (customerInfoId) => {
    page.dialogs.commands.getCustomerInfoById(customerInfoId).then(() => {
        page.element.pareData.html("");
        page.commands.addRowLine();
        page.commands.handleShowConfirmDelete();
        page.commands.goModalUpdateCustomer();

    })
}

page.dialogs.commands.drawProvinces = () => {
    return $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "GET",
        "url": page.url.getAllProvinces
    })
        .done((data) => {
            console.log(data)
            page.element.provinceUp.html("")
            $.each(data.results, (i, item) => {
                console.log(item)
                let str = `<option value="${item.province_id}">${item.province_name}</option>`;
                page.element.provinceUp.append(str);
            })
        })
        .fail((jqxHR) => {
            console.log(jqxHR);
        })
}

page.dialogs.commands.drawDistricts = (provinceId) => {
    return $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "GET",
        "url": page.url.getAllDistricts + provinceId
    }).done((data) => {
        if (data.results.length === 0) {
            App.IziToast.showErrorAlert("Loading of districts is fail");
        } else {
            page.element.districtUp.html("");
            $.each(data.results, (i, item) => {
                let str = `<option  class="form-control" value="${item.district_id}">${item.district_name}</option>`;

                page.element.districtUp.append(str)
            })
        }
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}

page.element.provinceUp.on('change', function () {
    page.dialogs.commands.drawDistricts(page.element.provinceUp.val())
})

page.commands.handleShowConfirmDelete = () => {
    page.element.btnDelete.on('click', function () {
        App.SweetAlert.showConfirmDeleteUser()
            .then((result) => {
                if (result.isConfirmed) {

                    $.ajax({
                        "headers": {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        "type": "DELETE",
                        "url": page.url.DeleteCustomer + page.element.idCustomer.val(),
                        "data": JSON.stringify(customerInfo)
                    })
                        .done(() => {
                            App.IziToast.showSuccessAlert("Xóa khách hàng Thành Công! Bạn sẽ trở về danh sách khách hàng sau 3s")
                            setTimeout(function () {
                                window.location.href = "/user-dashboard";
                            }, 3000);
                        })
                        .fail(() => {
                            App.IziToast.showErrorAlert('Xóa khách hàng thất bại');
                        });
                }
            })
    })
}
$(function () {
    page.commands.goModalUpdateCustomer();
    page.dialogs.commands.drawProvinces().then(() => {
        page.dialogs.commands.drawDistricts(page.element.provinceUp.val());
    })
    page.commands.handleShowConfirmDelete();
    page.dialogs.commands.doUpdateCustomer();


})



    let page = {
    url: {
    CreateCustomerInfo: App.BASE_URL_CUSTOMERINFO +"/create",
    getAllCustomerInfo: App.BASE_URL_CUSTOMERINFO,
    getAllProvinces: App.BASE_URL_PROVINCE + "/",
    getAllDistricts: App.BASE_URL_PROVINCE + "/district/"


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

    page.dialogs.element.fullNameCre = $("#fullNameCre");
    page.dialogs.element.phoneCre = $("#phoneCre");
    page.dialogs.element.emailCre = $("#emailCre");
    page.dialogs.element.provinceCre = $("#provinceCre");
    page.dialogs.element.provinceText = $("#provinceCre :selected");
    page.dialogs.element.districtCre = $("#districtCre");
    page.dialogs.element.districText = $("#districtCre :selected");
    page.dialogs.element.addressCre = $("#addressCre");

    let customerInfo = new CustomerInfo();
    let locationRegion = new LocationRegion();
    let user = new User();
    let customerInfoId = null;

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
    page.dialogs.element.provinceCre.html("")
    $.each(data.results, (i, item) => {
    console.log(item)
    let str = `<option value="${item.province_id}">${item.province_name}</option>`;
    page.dialogs.element.provinceCre.append(str);
})
})
    .fail((jqxHR) => {
    console.log(jqxHR);
})
}

    page.dialogs.commands.drawDistricts = (provinceId) => {
    $.ajax({
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
            page.dialogs.element.districtCre.html("");
            $.each(data.results, (i, item) => {
                let str = `<option  class="form-control" value="${item.district_id}">${item.district_name}</option>`;

                page.dialogs.element.districtCre.append(str)
            })
        }
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}

    page.dialogs.element.provinceCre.on('change', function () {
    page.dialogs.commands.drawDistricts(page.dialogs.element.provinceCre.val())
})

    $("#createCustomer").on('click', () => {
    $('#showError').html("")

    locationRegion.provinceId = page.dialogs.element.provinceCre.val();
    locationRegion.provinceName = $("#provinceCre :selected").text();
    locationRegion.districtId = page.dialogs.element.districtCre.val();
    locationRegion.districtName = $("#districtCre :selected").text();
    locationRegion.address = page.dialogs.element.addressCre.val();

    delete customerInfo.id;
    customerInfo.fullName = page.dialogs.element.fullNameCre.val();
    customerInfo.phone = page.dialogs.element.phoneCre.val();
    customerInfo.userName = page.dialogs.element.emailCre.val();
    customerInfo.locationRegion = locationRegion;
    customerInfo.debt = 0;
    $.ajax({
    "headers": {
    "accept": "application/json",
    "content-type": "application/json"
},
    "type": "POST",
    "url": page.url.CreateCustomerInfo,
    "data": JSON.stringify(customerInfo)
}).done((data) => {
    page.dialogs.element.provinceCre.val(92);
    page.dialogs.element.addressCre.val("");
    page.dialogs.element.fullNameCre.val("");
    page.dialogs.element.phoneCre.val("");
    page.dialogs.element.emailCre.val("");

    App.IziToast.showSuccessAlert("Thêm mới user thành công");
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
    let str =               `<ul>
                                <li> <label id="${key}-error" class="error" for="${key}">${item}</label></li>
                              </ul>
                             `;

    $("#" + key).addClass("error");

    $('#showError').append(str)
})
}
    console.log(jqXHR)
})
})
    $("#createFormCustomer").on("hidden.bs.modal", function () {
    $(" #createFormCustomer #showError")[0].reset();
});
    function handleCloseCreate() {

    $("#createFormCustomer").on("hidden.bs.modal", () => {
        $('#createFormCustomer #showError').empty().removeClass('show').addClass('d-none');
    })
}
    handleCloseCreate();
    $(function () {

    page.dialogs.commands.drawProvinces().then(() => {
        page.dialogs.commands.drawDistricts(page.dialogs.element.provinceCre.val());
    })
})


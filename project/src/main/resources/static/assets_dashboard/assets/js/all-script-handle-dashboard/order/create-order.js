let page = {
    url: {
        GetAllCustomerInfo: App.BASE_URL_CUSTOMERINFO,
        getAllProvinces: App.BASE_URL_PROVINCE + "/",
        getAllDistricts: App.BASE_URL_PROVINCE + "/district/",
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
page.element.phoneUpToOrder = $("#phoneUpToOrder");
page.element.provinceUpToOrder = $("#provinceUpToOrder");
page.element.provinceTextToOrder = $("#provinceUpToOrder :selected");
page.element.districtUpToOrder = $("#districtUpToOrder");
page.element.districTextToOrder = $("#districtUpToOrder :selected");
page.element.addressUpToOrder = $("#addressUpToOrder");
page.element.idCustomerToOrder = $("#idCustomer")
page.element.btnUpdateLocation = $("#UpdateLocation")

let order = new Order();
let orderDetail = new OrderDetail();
let customerInfo = new CustomerInfo();
let locationRegion = new LocationRegion();
let user = new User();
let customerInfoId = null;


page.element.AllCustomer = $(".AllCustomerInfor")

page.commands.drawCustomer = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetAllCustomerInfo
    }).done((data) => {
        $(".AllCustomerInfor").html("")
        $(".AllCustomerInfor").select2()
        $(".AllCustomerInfor").append(`<option value="null">Hãy chọn khách hàng</option>`);
        $.each(data, (i, item) => {
            customerInfo = item
            let str = `
            <option value="${customerInfo.id}">${customerInfo.fullName}</option>  
            `
            $(".AllCustomerInfor").append(str);
        })
        page.commands.SelectedCustomer();
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Không thể lấy được dữ liệu người dùng")
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
            page.element.provinceUpToOrder.html("")
            $.each(data.results, (i, item) => {
                let str = `<option value="${item.province_id}">${item.province_name}</option>`;
                page.element.provinceUpToOrder.append(str);
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
        page.element.districtUpToOrder.html("");
        $.each(data.results, (i, item) => {
            let str = `<option  class="form-control" value="${item.district_id}">${item.district_name}</option>`;
            page.element.districtUpToOrder.append(str)
        })
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}
page.element.provinceUpToOrder.on('change', function () {
    page.dialogs.commands.drawDistricts(page.element.provinceUpToOrder.val())
})


page.commands.getCustomerInfoById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetAllCustomerInfo + "/" + id
    }).done((data) => {
        customerInfo = data;
    })
}


page.commands.SelectedCustomer = () => {
    $(".AllCustomerInfor").on("change", function () {

        page.commands.getCustomerInfoById($(".AllCustomerInfor").val()).then(() => {
            $(".SelectOneCustomer").html("")
            let str = `
                <a href="/api/customerInfo/edit/${customerInfo.id}"> 
                    <span style="    color: #0088FF;
                                    font-size: 1.125rem;
                                    font-weight: bold;">${customerInfo.fullName}  </span> 
                    <span style="font-weight: bold; color: black;
                                    font-size: 1.125rem;"> - ${customerInfo.phone}</span>
                </a>
                <a style="color: black; font-size: 1.125rem" href="#" onclick="page.commands.removeCustomerSelected()"><i class="fa fa-times-circle"></i></a>   
            `
            $(".SelectOneCustomer").append(str);

            $(".AddressDelivery").html("")
            let str1 = `
            <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ giao hàng <span id="UpdateLocation"> <a  href="#" onclick="page.commands.updateLocaRegion('${customerInfo.id}')">Thay đổi</a></span></p>
            <p >${customerInfo.phone}</p>
            <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            `
            $(".AddressDelivery").append(str1);
            $(".billingAddress").html("")
            let str2 = `
            <div class="col-6">
                <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ Nhận hóa đơn</p>
                <p >${customerInfo.phone}</p>
                <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            </div>
            <div class="col-6">
                <p style="border: 1px dashed #00000061;
                          margin: 40px 0px 40px 0px;
                          padding: 20px 20px 20px 20px;
                          text-align: center;">tổng chi tiêu: <span style="color: red; font-weight: bold;">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(customerInfo.debt)}</span>
                </p>
            </div>
            `
            $(".billingAddress").append(str2)
            // page.commands.updateLocaRegion(customerInfo.id);
        })
    })
}

page.commands.removeCustomerSelected = () => {
    $(".SelectOneCustomer").html("")
    let str = `
    <select class="form-control AllCustomerInfor"></select>
    `
    $(".SelectOneCustomer").append(str);
    page.commands.drawCustomer()

}

page.commands.updateLocaRegion = (id) => {
    console.log(id)
    page.commands.getCustomerInfoById(id).then(() => {
        console.log(customerInfo)
        $("#phoneUpToOrder").val(customerInfo.phone)
        $("#provinceUpToOrder").val(customerInfo.locationRegion.provinceId)
        $("#addressUpToOrder").val(customerInfo.locationRegion.address)
        page.dialogs.commands.drawDistricts(customerInfo.locationRegion.provinceId).then(function () {
            $("#districtUpToOrder").val(customerInfo.locationRegion.districtId);
        })
        $('#modalUpdateLocation').modal('show')
    })
}

$(function () {
    page.dialogs.commands.drawProvinces().then(() => {
        page.dialogs.commands.drawDistricts(page.element.provinceUpToOrder.val());
    })
    page.commands.updateLocaRegion();
})

page.initializeControlEvent = () => {
    page.commands.drawCustomer();

}

$(() => {
    page.initializeControlEvent()
})
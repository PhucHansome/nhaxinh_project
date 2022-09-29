let page = {
    url: {
        GetAllCustomerInfo: App.BASE_URL_CUSTOMERINFO
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
let order = new Order();
let orderDetail = new OrderDetail();
let customerInfo = new CustomerInfo();
let locationRegion = new LocationRegion();

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
            let str1 =`
            <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ giao hàng <span> <a href="#" onclick="page.commands.updateLocationRegion()">Thay đổi</a></span></p>
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
        })
    })
}

page.commands.removeCustomerSelected = () => {
    $(".SelectOneCustomer").html("")
    let str =`
    <select class="form-control AllCustomerInfor"></select>
    `
    $(".SelectOneCustomer").append(str);
    page.commands.drawCustomer()
}

page.commands.updateLocationRegion = () => {

}

page.initializeControlEvent = () => {
    page.commands.drawCustomer();
}

$(() => {
    page.initializeControlEvent()
})
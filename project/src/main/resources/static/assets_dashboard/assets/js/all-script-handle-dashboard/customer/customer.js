

    let page = {
    url: {
    CreateCustomerInfo: App.BASE_URL_CUSTOMERINFO,
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

    page.element.btnGoCreateCustomer = $(".btn-go-add-customer");

    let customerInfo = new CustomerInfo();
    let locationRegion = new LocationRegion();
    let user = new User();
    let customerInfoId = null;

    page.dialogs.commands.goCreateCustomer = () => {
    page.element.btnGoCreateCustomer.on("click", function (){
        window.location.href = "/create-user-dashboard";
    })
}

    page.dialogs.commands.getCustomerInfoById = (id) => {
        return $.ajax({
            "method": "GET",
            "url": page.url.getAllCustomerInfo + "/" + id
        }).done((data) => {
            customerInfo = data;
            console.log(data)
        })
    }


    $(function (){
    page.dialogs.commands.goCreateCustomer();

})

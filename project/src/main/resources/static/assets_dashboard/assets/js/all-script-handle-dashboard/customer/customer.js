

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
    page.dialogs.commands.handleActive = () =>{
        $("#btn-active-block").on("click",() =>{
            console.log("vô đây chưa");
                let id =$(this).data("id")
            console.log(id)
                page.dialogs.commands.getCustomerInfoById(id).then(() =>{
                    let currentRow = $('#tr_' + customerInfo.id);
                    let updateRow = `
                     <tr id="tr_${customerInfo.id}">
                        <td></td>
                        <td>${customerInfo.phone}</td>
                        <td>${customerInfo.fullName}</td>
                        <td>${customerInfo.userName}</td>
                        <td>
                        <button class="btn btn-outline-danger" type="button" data-id="${customerInfo.id}" id="btn-block"><i class="fas fa-lock"></i></button>
                        </td>                 
                    </tr>
                     `
                    App.IziToast.showSuccessAlert("Tài khoản đã bị block");
                    currentRow.replaceWith(updateRow);
                    page.dialogs.commands.handleBlock()

                })
        })
    }

    page.dialogs.commands.handleBlock =() =>{
        $("#btn-block").on("click",() => {
            let id = $(this).data("id");
            console.log(id)
            page.dialogs.commands.getCustomerInfoById(id).then(() => {

                let currentRow1 = $('#tr_' + customerInfo.id);
                let updateRow1 = `

                     <tr id="tr_${customerInfo.id}">
                        <td></td>
                        <td>${customerInfo.phone}</td>
                        <td>${customerInfo.fullName}</td>
                        <td>${customerInfo.userName}</td>
                        <td>
                            <button data-id="${customerInfo.id}" class="btn btn-outline-success" type="button"  id="btn-active-block"></button>
                            
                        </td>
                    </tr>

            `
                App.IziToast.showSuccessAlert("Tài khoản đã mở khóa");
                currentRow1.replaceWith(updateRow1);

                page.dialogs.commands.handleActive();
            })
        })
    }

    $(function (){
    page.dialogs.commands.goCreateCustomer();
        page.dialogs.commands.handleActive();
        page.dialogs.commands.handleBlock();
})

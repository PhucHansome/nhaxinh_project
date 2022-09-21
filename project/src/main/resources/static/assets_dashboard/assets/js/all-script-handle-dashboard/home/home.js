let page = {
    url: {
        GetOrderDetail: App.BASE_URL_ORDER + "/order-detail/findAll/",
        GetOrderDetailBySTT: App.BASE_URL_ORDER + "/order-detail/status/",
        GetOrder: App.BASE_URL_ORDER + "/order/getOrder/",
        GetOrderForTop5: App.BASE_URL_ORDER + "/order-top-5/",
        PutOrderDetailCheckOut: App.BASE_URL_ORDER + "/order-detail/checkout/",
        PutOrderDetailCancel: App.BASE_URL_ORDER + "/order-detail/cancel/",
        GetOrderByQuantityMax: App.BASE_URL_ORDER + "/order-max",
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


let product = new Product()

let tempRowCustomer = $.validator.format($.trim($('#tempRowCustomer').val().toString()));

function addRowCustomer() {
    $('#tbListCustomers tbody').prepend($(tempRowCustomer(product.id, product.productTitle, product.statusOrder, order.quantity)));
}



page.commands.getAllorder = () => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetOrderDetail,
    }).done((data) => {
        console.log(data);
        page.commands.getOrderDetailShowAlert()
    }).fail((e) => {
        console.log(e)
    })
}

page.commands.getOrderDetailShowAlert = () => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetOrderDetailBySTT,
    }).done((data) => {
        if (data.length === 0) {
            return
        }
        $(".alertshow").html("")
        let str = `
            <div class="alert alert-danger fixed-bottom" role="alert">
  Bạn đang có ${data.length} Đơn hàng Chưa xử lý <a href="/order-dashboard" class="alert-link">Nhấn vào đây</a> để vào trang order.
</div>
            `
        $(".alertshow").append(str)
    })
}

page.element.top5Product = () =>{
    $.ajax({
        "method": "GET",
        "url": page.url.GetOrderForTop5,
    }).done((data) => {
        console.log(data)
        $.each(data, (i, item) =>{
            order = item;

        })
    })
}


$(() => {
    page.commands.getAllorder()
    page.element.top5Product()
})



let page = {
    url: {
        GetOrderDetail: App.BASE_URL_ORDER + "/order-detail/",
        GetOrder: App.BASE_URL_ORDER + "/order/getOrder/",
        PutOrderDetailCheckOut: App.BASE_URL_ORDER + "/order-detail/checkout/",
        PutOrderDetailCancel: App.BASE_URL_ORDER + "/order-detail/cancel/",
        PutOrderDetailDelivery: App.BASE_URL_ORDER + "/order-detail/delivery/",
        PutOrderDetailSuccessDelivery: App.BASE_URL_ORDER + "/order-detail/success-delivery/"
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

page.element.information_Item = $(".information_Item");
page.element.information_customer = $(".information_customer")
page.element.idOrder = $("#idOrder")
page.element.str1_order = $("#str1_order")
page.element.str2_order = $("#str2_order")
page.element.str3_order = $("#str3_order")
page.element.str4_order = $("#str4_order")
page.dialogs.element.line_cart_item = $(".line_cart_item")
page.dialogs.element.btnCheckout = $(".btn-checkout")


let rowstr1_render = jQuery.validator.format($.trim(page.element.str1_order.val().toString()));

let rowstr2_render = jQuery.validator.format($.trim(page.element.str2_order.val().toString()));

let rowstr3_render = jQuery.validator.format($.trim(page.element.str3_order.val().toString()));

let rowstr4_render = jQuery.validator.format($.trim(page.element.str4_order.val().toString()));

page.commands.addStr1 = () => {
    page.element.information_Item.append($(rowstr1_render(orderDetail.statusOrderDetail, orderDetail.id)));
}

page.commands.addStr2 = () => {
    page.element.information_customer.append($(rowstr2_render(
        orderDetail.fullName, orderDetail.userName, orderDetail.phone, orderDetail.address,
        orderDetail.districtName, orderDetail.provinceName
    )));
}

page.commands.addStr3 = () => {
    $(".line_cart_item").append($(rowstr3_render(
        order.productImage, order.productCode, order.productTitle,
         new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(order.grandTotal)
    )));
}

page.commands.addStr4 = (sum) => {
    $(".total_Bill").append($(rowstr4_render(new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(sum)
    )));
}

page.commands.getAllOrderById = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetOrderDetail + page.element.idOrder.text(),
    }).done((data) => {
        orderDetail = data;
        page.element.information_Item.html("")
        page.commands.addStr1();
        page.commands.GetOrderByOrderDetailId(orderDetail);
        if (orderDetail.statusOrderDetail === "Đơn hàng đã duyệt" || orderDetail.statusOrderDetail === "Đã Hủy đơn hàng" || orderDetail.statusOrderDetail === "Đã giao hàng thành công" ) {
            $(".btnUpdateOrder").html("");
            $(".btn_AllAction").html("")
        }

        if (orderDetail.statusOrderDetail === "Đơn hàng đã duyệt"){
            $(".btn_AllAction").html("")
            let str =`
             <div class="col-6">
                <button class="btn btn-danger btn-Cancel"  data-id="${orderDetail.id}">Hủy đơn hàng</button>
            </div>
            <div class="col-6">
                <button data-id="${orderDetail.id}" class="btn btn-instagram btn-delivery float-lg-right">Đang giao hàng</button>
            </div>
            `
            $(".btn_AllAction").append(str);
        }

        if (orderDetail.statusOrderDetail === "Đang giao hàng"){
            $(".btn_AllAction").html("")
            let str =`
             <div class="col-6">
                <button class="btn btn-danger btn-Cancel"  data-id="${orderDetail.id}">Hủy đơn hàng</button>
            </div>
            <div class="col-6">
                <button data-id="${orderDetail.id}" class="btn btn-github btn-successful-delivery float-lg-right">Đã giao hàng thành công!</button>
            </div>
            `
            $(".btn_AllAction").append(str);
        }

        page.commands.printPage(orderDetail.id)
    }).fail((e) => {
        App.SweetAlert.showErrorAlert("Bạn đã Lấy toàn bộ đơn hàng Thất bại")

    })
}

page.commands.GetOrderByOrderDetailId = (orderDetail) => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetOrder + orderDetail.id,
    }).done((data) => {
        console.log(data);
        $(".line_cart_item").html("")
        console.log(data)
        let sum = 0
        $.each(data, (i, item) => {
            order = item;
            if (order.statusOrder === "Đang chờ duyệt" || order.statusOrder === "Đơn hàng đã duyệt" || order.statusOrder === "Đã Hủy đơn hàng" || order.statusOrder === "Đang giao hàng" || order.statusOrder === "Đã giao hàng thành công") {
                sum += order.grandTotal;
                page.commands.addStr3()
            }
            if (i === 0) {
                page.element.information_customer.html("")
                page.commands.addStr2()
                page.commands.handleCheckout(order.customerInfo.userName);
                page.commands.handleCancelOrder(order.customerInfo.userName);
                page.commands.handleDelivery(order.customerInfo.userName);
                page.commands.handleSuccessDelivery(order.customerInfo.userName);
            }
        })
        $(".total_Bill").html("")
        page.commands.addStr4(sum)
    }).fail(() => {
        App.SweetAlert.showErrorAlert("Bạn đã Lấy đơn hàng Thất bại")
    })
}

page.commands.getOrderDetailById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetOrderDetail + id
    }).done((data) => {
        console.log(data);
        orderDetail = data;
    }).fail((e) => {
        console.log(e)
    })
}

page.commands.handleDelivery = (userName) => {
    $(".btn-delivery").on("click", function () {
        Swal.fire({
            title: 'Bạn có muốn đổi trạng thái sang "Đang giao hàng" không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đúng, Tôi muốn đổi trạng thái!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id");
                page.commands.getOrderDetailById(id).then(() => {
                    $.ajax({
                        headers: {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        type: "PUT",
                        url: page.url.PutOrderDetailDelivery + userName,
                        data: JSON.stringify(orderDetail)
                    }).done((data) => {
                        App.SweetAlert.showSuccessAlert("Bạn đã đổi trạng thái Đang giao hàng")
                        page.commands.getAllOrderById();
                    }).fail(() => {
                        App.SweetAlert.showErrorAlert("Bạn đã đổi trạng thái Thất bại")
                    })
                })
            }
        })

    })
}

page.commands.handleSuccessDelivery = (userName) => {
    $(".btn-successful-delivery").on("click", function () {
        Swal.fire({
            title: 'Bạn có muốn đổi trạng thái sang "Đã giao hàng thành công" không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đúng, Tôi muốn duyệt đơn này!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id");
                page.commands.getOrderDetailById(id).then(() => {
                    $.ajax({
                        headers: {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        type: "PUT",
                        url: page.url.PutOrderDetailSuccessDelivery + userName,
                        data: JSON.stringify(orderDetail)
                    }).done((data) => {
                        App.SweetAlert.showSuccessAlert("Bạn đã đổi trạng thái Đã giao hàng thành công")
                        page.commands.getAllOrderById();
                    }).fail(() => {
                        App.SweetAlert.showErrorAlert("Bạn đã đổi trạng thái Thất bại")
                    })
                })
            }
        })

    })
}

page.commands.handleCheckout = (userName) => {
    $(".btn-checkout").on("click", function () {
        Swal.fire({
            title: 'Bạn có muốn duyệt đơn hàng này không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đúng, Tôi muốn duyệt đơn này!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id");
                page.commands.getOrderDetailById(id).then(() => {
                    $.ajax({
                        headers: {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        type: "PUT",
                        url: page.url.PutOrderDetailCheckOut + userName,
                        data: JSON.stringify(orderDetail)
                    }).done((data) => {
                        App.SweetAlert.showSuccessAlert("Bạn đã Duyệt đơn hàng thành công")
                        page.commands.getAllOrderById();
                    }).fail(() => {
                        App.SweetAlert.showErrorAlert("Bạn đã Duyệt đơn hàng Thất bại")
                    })
                })
            }
        })

    })
}

page.commands.handleCancelOrder = (userName) => {
    $(".btn-Cancel").on("click", function () {
        Swal.fire({
            title: 'Bạn có muốn Hủy đơn hàng này không?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đúng, Tôi muốn Hủy đơn này!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id");
                page.commands.getOrderDetailById(id).then(() => {
                    $.ajax({
                        headers: {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        type: "PUT",
                        url: page.url.PutOrderDetailCancel + userName,
                        data: JSON.stringify(orderDetail)
                    }).done((data) => {
                        App.SweetAlert.showSuccessAlert("Bạn đã Hủy đơn hàng thành công!")
                        console.log(data);
                        page.commands.getAllOrderById();
                    }).fail(() => {
                        App.SweetAlert.showErrorAlert("Bạn đã Hủy đơn hàng thất bại!")
                    })
                })
            }
        })
    })
}

page.commands.printPage = (id) => {
    $(".btn-print").on("click", function () {
        window.print("http://localhost:8092/order-dashboard/detail/" + id);
    })
}


$(function () {
    page.commands.getAllOrderById()
})


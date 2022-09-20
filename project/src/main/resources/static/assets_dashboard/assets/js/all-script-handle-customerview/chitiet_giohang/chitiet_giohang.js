let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/username/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetCustomer: App.BASE_URL_CUSTOMERINFO + "/username/" + $("#EmailLogin").text(),
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        PostProduct: App.BASE_URL_PRODUCT,
        PostCartItem: App.BASE_URL_CARTITEM,
        PostCart: App.BASE_URL_CART + "/create",
        PostOrder: App.BASE_URL_ORDER,
        DeleteCartItem: App.BASE_URL_CARTITEM
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
let carItem = new CartItems();
let cart = new Cart
let customerInfo = new CustomerInfo();
let locationRegion = new LocationRegion();
let user = new User();
let order = new Order();

page.element.userNameLogin = $("#EmailLogin")
page.element.cart_product = $("#cart_product_view")
page.element.cart_cartBotLine = $("#product-grid-1457246667")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.total_OrderSumary = $("#total_OrderSumary")
page.element.line_item_orderSumary = $("#line_item_orderSumary")
page.element.orderSummary = $(".woocommerce-checkout-review-order-table tbody")
page.element.resuilt_search = $(".resuilt_search")

page.element.InputQuerySearch = $("#querySearch")
page.element.select_provinceCUS = $("#provinceCUS")
page.element.select_provinceCUS_selected = $("#provinceCUS :selected")
page.element.select_districtCus = $("#districtCus")
page.element.select_districtCus_selected = $("#districtCus :selected")
page.element.fullNameCus = $("#fullNameCus")
page.element.phoneCus = $("#phoneCus")
page.element.emailCus = $("#emailCus")
page.element.addressCus = $("#addressCus")
page.element.contentCart = $('#contentCart')
page.element.btnKeepBuyIng = $(".btn-keep-buying")
page.element.btnTurnBackAccount = $(".btn-turn-back-account");
page.element.frmBuyProduct = $("#frmBuyProduct")


page.element.btnShowCart = $("#cart_")
page.element.chua_cart = $(".chua_cart")

page.element.btnOrder = $(".btn-order")

let cart_item_product = jQuery.validator.format($.trim(page.element.CaritemProduct.val().toString()));

let total_allCartItem = jQuery.validator.format($.trim(page.element.total_OrderSumary.val().toString()));

let line_item_order = jQuery.validator.format($.trim(page.element.line_item_orderSumary.val().toString()));

page.commands.addCartRow = () => {
    page.element.container_cartItem.prepend($(cart_item_product(carItem.product.image, carItem.product.title, carItem.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price), carItem.id, carItem.product.slug)));
}
page.commands.lineOrderSummary = () => {
    page.element.orderSummary.prepend($(line_item_order(carItem.product.image, carItem.product.title, carItem.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price))));
}

page.commands.totalCartItem = (sum, data) => {
    page.element.orderSummary.prepend($(total_allCartItem(new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(sum), data)));
}


page.dialogs.commands.drawProvinces = () => {
    return $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "GET",
        "url": "https://vapi.vnappmob.com/api/province/"
    })
        .done((data) => {
            page.element.select_provinceCUS.html("")
            $.each(data.results, (i, item) => {
                let str = `<option value="${item.province_id}">${item.province_name}</option>`;
                page.element.select_provinceCUS.append(str);
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
        "url": "https://vapi.vnappmob.com/api/province/district/" + provinceId
    }).done((data) => {
        if (data.results.length === 0) {
            App.IziToast.showErrorAlert("Loading of districts is fail");
        } else {
            page.element.select_districtCus.html("");
            $.each(data.results, (i, item) => {
                let str = `<option  class="form-control" value="${item.district_id}">${item.district_name}</option>`;

                page.element.select_districtCus.append(str)
            })
        }
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}

page.element.select_provinceCUS.on('change', function () {
    page.dialogs.commands.drawDistricts(page.element.select_provinceCUS.val())
})

page.commands.getProductById = (id) => {
    return $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        "method": "GET",
        "url": page.url.GetProduct + "/" + id
    }).done((data) => {
        console.log(data);
        product = data;
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Xin vui lòng đăng nhập để thực hiện chức năng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.showIconCart = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetCartItems,
    }).done((data) => {
        $("#cart_").html("")
        let icon = `
                    <span class="image-icon header-cart-icon" id="dataLabelIcon" data-icon-label="${data.length}"><img class="cart-img-icon" alt="Cart" src="/assets/HomePageFile/ic-shopping.png"></span>
                    `
        $("#cart_").prepend(icon)
    })
}

page.commands.handleShowCart = () => {
    page.element.btnShowCart.on("click", function () {
        $.ajax({
            "type": "GET",
            "url": page.url.GetCartItems,
        }).done((data) => {
            $("#seeGrandTotal").html("")
            page.element.container_cartItem.html("")
            $(".showCart").css({'display': 'block'})
            page.commands.handleCloseCart()
            $(".woocommerce-mini-cart__buttons").removeClass("d-none")
            let sum = 0
            if (data.length === 0) {
                page.element.container_cartItem.prepend(`<h1 style="align-content: center">Giỏ Hàng Trống</h1>`)
                $(".woocommerce-mini-cart__buttons").addClass("d-none")
            }
            $.each(data, (i, item) => {
                carItem = item
                sum += carItem.grandTotal;
                page.commands.addCartRow()
            })
            let str = `
                       <p class="woocommerce-mini-cart__total total"     >
                       <strong>Thành tiền:</strong> <span class="woocommerce-Price-amount amount"><bdi>${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(sum)}</bdi></span></p>`
            $("#seeGrandTotal").prepend(str)
            page.commands.showIconCart()
            page.commands.handleShowCart();
        }).fail(() => {
            page.element.container_cartItem.prepend("<h1>Giỏ Hàng Trống</h1>")
        })
    })
}

page.commands.deleteCartById = (id) => {
    $.ajax({
        "method": "DELETE",
        "url": page.url.DeleteCartItem + "/" + id
    }).done(() => {
        App.IziToast.showSuccessAlert("Xóa sản phẩm thành công")
        $.ajax({
            "type": "GET",
            "url": page.url.GetCartItems,
        }).done((data) => {
            page.loadData.getOrderSummary();
            $("#seeGrandTotal").html("")
            page.element.container_cartItem.html("")
            $(".showCart").css({'display': 'block'})
            page.commands.handleCloseCart()
            $(".woocommerce-mini-cart__buttons").removeClass("d-none")
            let sum = 0
            if (data.length === 0) {
                page.element.container_cartItem.prepend(`<h1 style="align-content: center">Giỏ Hàng Trống</h1>`)
                $(".woocommerce-mini-cart__buttons").addClass("d-none")
            }
            $.each(data, (i, item) => {
                carItem = item
                sum += carItem.grandTotal;
                page.commands.addCartRow()
            })
            let str = `
                       <p class="woocommerce-mini-cart__total total"     >
                       <strong>Thành tiền:</strong> <span class="woocommerce-Price-amount amount"><bdi>${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(sum)}</bdi></span></p>`
            $("#seeGrandTotal").prepend(str)
            page.commands.showIconCart()
            page.commands.handleShowCart();
        }).fail(() => {
        })
    })
}

page.commands.handleCloseCart = () => {
    $(".mfp-close").on("click", () => {
        $(".showCart").css({'display': 'none'});
    })
}

page.commands.handleOrder = () => {
    page.element.btnOrder.on("click", () => {
        page.element.frmBuyProduct.submit();
    })
}

page.commands.handleAfterClickOrder = () => {
    page.commands.getUserByEmail().then(() => {
        if ($("#payment_method_cod")[0].checked) {
            if ($("#terms")[0].checked) {
                delete locationRegion.id;
                locationRegion.provinceId = $("#provinceCUS").val();
                locationRegion.provinceName = $("#provinceCUS :selected").text();
                locationRegion.districtId = $("#districtCus").val();
                locationRegion.districtName = $("#districtCus :selected").text();
                locationRegion.address = page.element.addressCus.val();

                delete customerInfo.id;
                customerInfo.userName = page.element.userNameLogin.text();
                customerInfo.fullName = page.element.fullNameCus.val();
                customerInfo.phone = page.element.phoneCus.val();
                customerInfo.debt = 0;
                customerInfo.locationRegion = locationRegion;

                delete cart.id;
                cart.content = page.element.contentCart.val();
                cart.user = user
                cart.customerInfo = customerInfo
                $(".temploadding").html("")
                let str = `
           <div class="loading">Loading&#8230;</div>

            `
                $(".temploadding").append(str)
                $.ajax({
                    "headers": {
                        "accept": "application/json",
                        "content-type": "application/json"
                    },
                    "type": "POST",
                    "url": page.url.PostCart,
                    "data": JSON.stringify(cart)
                }).done((data) => {
                    console.log(data);
                    cart = data;
                    page.commands.createOrder(cart);
                }).fail((e) => {
                    $(".temploadding").html("")
                })
            } else {
                App.IziToast.showErrorAlert("Bạn chưa Đồng ý với điều khoản của chúng tôi")
                return;
            }

        } else {
            App.IziToast.showErrorAlert("Bạn chưa chọn Phương thức thanh toán")
            return;
        }
    })
}

page.commands.createOrder = (cart) => {
    delete order.id;
    order.description = cart.content;
    order.grandTotal = 0;
    order.quantity = 0;
    order.productCode = "null";
    order.productImage = "null";
    order.productTitle = "null";
    order.customerInfo = cart.customerInfo;
    order.statusOrder = "Đang chờ duyệt";
    order.orderDetail = new OrderDetail();
    $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "POST",
        "url": page.url.PostOrder,
        "data": JSON.stringify(order)
    }).done((dataOrder) => {
        $(".temploadding").html("")
        $("#frm_leave_chitiet_giohang").modal("show")
    }).fail((jqXHR) => {
        console.log(jqXHR);
        App.IziToast.showErrorAlert("Tạo đơn hàng thất bại!");
        $(".temploadding").html("")
        if (jqXHR.responseJSON) {
            $.each(jqXHR.responseJSON, (key, item) => {
                App.IziToast.showErrorAlert(item);
            })

        }
    })
}

page.loadData.getOrderSummary = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetCartItems,
    }).done((data) => {
        if (data.length === 0) {
            setTimeout(function () {
                App.IziToast.showErrorAlert("giỏ hàng trống! trở về trang giỏ hàng sau 1s")
                setTimeout(function () {
                    App.IziToast.showSuccessAlert("Trở về trang giỏ hàng");
                    window.location.href = "/cart";
                }, 1000);
            }, 1000);
        }
        page.element.emailCus.val(page.element.userNameLogin.text())
        page.element.orderSummary.html("")
        let sum = 0
        $.each(data, (i, item) => {
            carItem = item;
            page.commands.lineOrderSummary();
            sum += carItem.grandTotal
        })
        page.commands.totalCartItem(sum, data.length)
    }).fail(() => {

    })
}

page.commands.getUserByEmail = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetUser
    }).done((data) => {
        user = data;
    })
}

page.commands.getCustomerInfoByEmail = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetCustomer
    }).done((data) => {
        customerInfo = data;
        page.element.select_provinceCUS.val(customerInfo.locationRegion.provinceId)
        page.element.fullNameCus.val(customerInfo.fullName);
        page.element.phoneCus.val(customerInfo.phone);
        page.element.addressCus.val(customerInfo.locationRegion.address);
        page.dialogs.commands.drawDistricts(page.element.select_provinceCUS.val()).then(() => {
            page.element.select_districtCus.val(customerInfo.locationRegion.districtId)
        })

    })
}

page.commands.blurInputsearch = () => {
    page.element.InputQuerySearch.blur(function () {
        setTimeout(function () {
            page.element.resuilt_search.addClass("d-none")
        }, 150);
    })

}

page.commands.searchFunction = () => {
    page.element.InputQuerySearch.on("input", function () {
        let search = page.element.InputQuerySearch.val()
        $.ajax({
            "method": "GET",
            "url": page.url.GetResuiltSearch + "/" + search
        }).done((data) => {
            page.element.resuilt_search.removeClass("d-none")
            page.element.resuilt_search.html("")
            $.each(data, (i, item) => {
                if (i > 3) {
                    return;
                }
                tag = item
                let str = `
                             <div class="autocomplete-suggestion" data-index="${i}"><a href="/detail/${tag.product.slug}" ><img width="30px" height="30px"
                                    class="search-image"
                                    src="${tag.product.image}">
                                <div class="search-name"><strong style="font-size: 12px;">${tag.product.title}</strong><span style="font-size: 12px;"> ${tag.product.code}</span>
                                </div>
                                <span class="search-price"><ins><span style="font-size: 12px;"
                                        class="woocommerce-Price-amount amount"><bdi>${new Intl.NumberFormat('vi-VN', {
                    style: 'currency',
                    currency: 'VND'
                }).format(tag.product.price)}
                                        </bdi></span></ins></span></span>
                                </a></div>
                    `;
                page.element.resuilt_search.prepend(str);
            })
            page.commands.blurInputsearch()
        }).fail((jqXHR) => {
            if (jqXHR.responseJSON) {
                $.each(jqXHR.responseJSON, (key, item) => {
                    page.element.resuilt_search.html("")
                    page.element.resuilt_search.removeClass("d-none")
                    page.element.resuilt_search.append(`<div class="autocomplete-suggestion" data-index="${i}"><div class="search-name"><span>${item}</span> </div>`)
                    page.commands.blurInputsearch()
                })
            }
        })
    })
}

page.commands.handleGoSearch = () => {
    $(".btn-search").on("click", function () {
        window.location.href = "/search/page=1?option=1&choicePrice=0&Categories=null&Color=null&query=" + page.element.InputQuerySearch.val();
    })
}

page.commands.getCustomerByUserName = () => {
    $.ajax({
        type: "GET",
        url: page.url.GetCustomer,
    }).done((data) => {
        customerInfo = data;
        $("#fullNameScreen").text(customerInfo.fullName)
    }).fail((e) => {
        $("#fullNameScreen").text($("#EmailLogin").text())
    })
}

page.element.btnTurnBackAccount.on("click", () => {
    window.location.href = "/account";
})


page.element.btnKeepBuyIng.on("click", () => {
    window.location.href = "/";
})

page.element.frmBuyProduct.validate({
    "rules": {
        "fullNameCus": {
            required: true,
            minlength: 5,
        },
        "phoneCus": {
            required: true,
            number: true,
            minlength: 9,
        },
        "emailCus": {
            required: true,
            email: true,
            minlength: 3,
            maxlength: 50,
        },
        "billing_address_1": {
            required: true,
            minlength: 6,
        },
    },
    "messages": {
        "fullNameCus": {
            required: "Vui Lòng Nhập Họ và tên!",
            minlength: $.validator.format("Họ và tên tối thiểu {0} ký tự!"),
        },
        "phoneCus": {
            required: "Vui Lòng Nhập Số điện thoại!",
            minlength: $.validator.format("Số điện thoại tối thiểu {0} ký tự!"),
        },
        "emailCus": {
            required: "vui lòng nhập Email!",
            email: "Vui lòng nhập đúng định dạng Email (VD: phucnguyen@gmail.com)!",
            minlength: $.validator.format("Email tối thiểu {0} ký tự!"),
            maxlength: $.validator.format("Email tối đa {0} ký tự"),
        },
        "billing_address_1": {
            required: "Vui Lòng Nhập Địa chỉ!",
            minlength: $.validator.format("Họ và tên tối thiểu {0} ký tự!"),
        }
    },
    errorLabelContainer: "#frmBuyProduct .input.error",
    submitHandler: function () {
        page.commands.handleAfterClickOrder();
    }
})


page.initializeControlEvent = () => {
    page.dialogs.commands.drawDistricts(page.element.select_provinceCUS.val());
    page.commands.handleShowCart()
    page.commands.showIconCart()
    page.loadData.getOrderSummary();
    page.commands.getCustomerInfoByEmail();
    page.commands.handleOrder();
    page.commands.searchFunction()
    page.commands.handleGoSearch();
    page.commands.getCustomerByUserName()
    if ($("#EmailLogin").text() === "Đăng nhập") {
        $(".btn-order").addClass("d-none");
    }
}

$(() => {
    page.dialogs.commands.drawProvinces().then(() => {
        page.initializeControlEvent()
    })

})
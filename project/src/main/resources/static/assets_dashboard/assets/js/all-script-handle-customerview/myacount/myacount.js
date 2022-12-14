let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/username/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetCustomer: App.BASE_URL_CUSTOMERINFO + "/username/" + $("#EmailLogin").text(),
        GetOrder: App.BASE_URL_ORDER + "/" + $("#EmailLogin").text(),
        GetOrderDetailByUsername: App.BASE_URL_ORDER + "/order-detail/user-name/",
        GetOrderDetailById: App.BASE_URL_ORDER + "/order-detail/",
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        GetCustomers: App.BASE_URL_CUSTOMERINFO,
        PutCustomer: App.BASE_URL_CUSTOMERINFO + "/edit",
        PostCustomer: App.BASE_URL_CUSTOMERINFO + "/create",
        PutUser: App.BASE_URL_USER_ABC + "/change-password",
        PutOrderDetailCanel: App.BASE_URL_ORDER + "/order-detail/cancel/" + $("#EmailLogin").text(),
        PutOrderDetailSuccessDeliveryy: App.BASE_URL_ORDER + "/order-detail/success-delivery/" + $("#EmailLogin").text(),
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
let locationRegion = new LocationRegion();
let customerInfo = new CustomerInfo();
let user = new User();
let order = new Order();
let orderDetail = new OrderDetail();

page.element.cart_product = $("#cart_product_view")
page.element.cart_cartBotLine = $("#product-grid-1457246667")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.userNameLogin = $("#EmailLogin")
page.element.valueInput = $("#valueInput")
page.element.valueQuantity = $("#quantity_62fc4b3185e6c")
page.element.select_provinceCUS = $("#provinceCUS")
page.element.select_districtCus = $("#districtCus")
page.element.fullNameCus = $("#fullNameCus")
page.element.phoneCus = $("#phoneCus")
page.element.emailCus = $("#emailCus")
page.element.addressCus = $("#addressCus")
page.element.idCustomer = $("#idCustomer");
page.element.debtCustomer = $("#debtCustomer")
page.element.passWordOld = $("#passWordOld")
page.element.newPassword = $("#newPassword")
page.element.newPasswordCon = $("#newPasswordCon")
page.element.listOrder = $(".listOrder tbody")
page.element.totail_spending = $(".total_spending")
page.element.resuilt_search = $(".resuilt_search")
page.element.frmUpdateCustomer = $("#frmUpdateCustomer")

page.element.InputQuerySearch = $("#querySearch")

page.element.container_infomation = $(".information")
page.element.pagePassword = $(".changePassword")
page.element.pageOrder = $(".order")


page.element.btnSavePasword = $(".btn-Save-PassWord")
page.element.btnChangePagePassword = $(".btn-get-page-password")
page.element.btnchangPageInfomation = $(".btn-change-page-infomation")
page.element.btnGetPageOrder = $(".btn-get-page-order")
page.element.btnUpdate = $(".btn-update")
page.element.btnShowCart = $("#cart_")
page.element.chua_cart = $(".chua_cart")
page.element.main_photo = $(".main_photo")
page.element.each_image = $(".each_image")
page.element.single_add_to_cart_button = $(".btn-add-to-cart")
page.element.btnBuy = $(".btn-buy")
page.element.listAllOrder = $(".eachOrder")
page.element.listWattingOrder = $(".eachOrderWatting")
page.element.listCancelOrder = $(".eachOrderCancel")
page.element.listApplyOrder = $(".eachOrderApply")
page.element.listPageDelivery = $(".eachOrderDelivery")
page.element.listPageSuccessDelivery = $(".eachOrderSuccessDelivery")
page.element.pageSuccessDelivery = $(".all-success-delivery-my-acount")
page.element.pageDelivery = $(".all-delivery-my-acount")

page.dialogs.element.btnAllOrder = $(".all-order-my-acount")
page.dialogs.element.btnWattingOrder = $(".all-waiting-my-acount")
page.dialogs.element.btnApplyOrder = $(".all-apply-my-acount")
page.dialogs.element.btnCancelOrder = $(".all-Cancelled-my-acount")

let cart_item_product = jQuery.validator.format($.trim(page.element.CaritemProduct.val().toString()));

page.commands.addCartRow = () => {
    page.element.container_cartItem.prepend($(cart_item_product(carItem.product.image, carItem.product.title, carItem.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price), carItem.id, carItem.product.slug)));
}

page.commands.handleGodetailInTagA = (id) => {
    window.location.href = "/detail/" + id;
}

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
        App.IziToast.showErrorAlert("Xin vui l??ng ????ng nh???p ????? th???c hi???n ch???c n??ng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.getProductByCode = (code) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProduct + "/product/" + code
    }).done((data) => {
        product = data;
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Xin vui l??ng ????ng nh???p ????? th???c hi???n ch???c n??ng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.getUserByUsername = () => {
    return $.ajax({
        "type": "GET",
        "url": page.url.GetUser,
    }).done((data) => {
        user = data;
    })

}

page.commands.getOrderDetalById = (id) => {
    return $.ajax({
        "type": "GET",
        "url": page.url.GetOrderDetailById + id,
    }).done((data) => {
        orderDetail = data;
    })
}

page.commands.getCustomerInfoByEmail = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetCustomer
    }).done((data) => {
        let customerInfo = data;
        page.element.idCustomer.val(customerInfo.id)
        page.element.select_provinceCUS.val(customerInfo.locationRegion.provinceId)
        page.element.fullNameCus.val(customerInfo.fullName);
        page.element.emailCus.val($("#EmailLogin").text())
        page.element.phoneCus.val(customerInfo.phone);
        page.element.debtCustomer.val(customerInfo.debt)
        page.element.addressCus.val(customerInfo.locationRegion.address);
        page.dialogs.commands.drawDistricts(page.element.select_provinceCUS.val()).then(() => {
            page.element.select_districtCus.val(customerInfo.locationRegion.districtId)
        })


    })
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

page.commands.deleteCartById = (id) => {
    $.ajax({
        "method": "DELETE",
        "url": page.url.DeleteCartItem + "/" + id
    }).done(() => {
        App.IziToast.showSuccessAlert("X??a s???n ph???m th??nh c??ng")
        $.ajax({
            "type": "GET",
            "url": page.url.GetCartItems,
        }).done((data) => {
            $("#seeGrandTotal").html("")
            page.element.container_cartItem.html("")
            $(".showCart").css({'display': 'block'})
            page.commands.handleCloseCart()
            let sum = 0
            if (data.length === 0) {
                page.element.container_cartItem.prepend(`<h1 style="align-content: center">Gi??? H??ng Tr???ng</h1>`)
                $(".woocommerce-mini-cart__buttons").html("")
            }
            $.each(data, (i, item) => {
                carItem = item
                sum += carItem.grandTotal;
                page.commands.addCartRow()
            })
            let str = `
                       <p class="woocommerce-mini-cart__total total"     >
                       <strong>Th??nh ti???n:</strong> <span class="woocommerce-Price-amount amount"><bdi>${new Intl.NumberFormat('vi-VN', {
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

page.commands.handleShowCart = () => {
    page.element.btnShowCart.on("click", function () {
        $.ajax({
            "type": "GET",
            "url": page.url.GetCartItems,
        }).done((data) => {
            $("#seeGrandTotal").html("")
            page.element.container_cartItem.html("")
            $(".showCart").css({'display': 'block'})
            $(".woocommerce-mini-cart__buttons").removeClass("d-none")
            page.commands.handleCloseCart()
            let sum = 0
            console.log(data)
            if (data.length === 0) {
                page.element.container_cartItem.prepend(`<h1 style="align-content: center">Gi??? H??ng Tr???ng</h1>`)
                $(".woocommerce-mini-cart__buttons").html("")
                $(".woocommerce-mini-cart__buttons").addClass("d-none")
            }
            $.each(data, (i, item) => {
                carItem = item
                sum += carItem.grandTotal;
                page.commands.addCartRow()
            })
            let str = `
                       <p class="woocommerce-mini-cart__total total"     >
                       <strong>Th??nh ti???n:</strong> <span class="woocommerce-Price-amount amount"><bdi>${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(sum)}</bdi></span></p>`
            $("#seeGrandTotal").prepend(str)
            page.commands.showIconCart()
            page.commands.handleShowCart();
        }).fail(() => {
            page.element.container_cartItem.prepend("<h1>Gi??? H??ng Tr???ng</h1>")
        })
    })
}

page.commands.handleCloseCart = () => {
    $(".mfp-close").on("click", () => {
        $(".showCart").css({'display': 'none'});
    })
}

page.commands.handleUpdateCustomer = () => {
    page.element.btnUpdate.on("click", () => {
        page.element.frmUpdateCustomer.submit();
    })
}

page.element.afterClickUpdateInformation = () => {
    delete locationRegion.id;
    locationRegion.provinceId = $("#provinceCUS").val();
    locationRegion.provinceName = $("#provinceCUS :selected").text();
    locationRegion.districtId = $("#districtCus").val();
    locationRegion.districtName = $("#districtCus :selected").text();
    locationRegion.address = page.element.addressCus.val();
    if  (page.element.userNameLogin.text() !==  page.element.emailCus.val() ){
        App.IziToast.showErrorAlert("Email kh??ng ????ng!")
        page.element.emailCus.val($("#EmailLogin").text())
        return;
    }
    if (page.element.idCustomer.val() === "" ){
        customerInfo.id = page.element.idCustomer.val();
        customerInfo.userName = page.element.userNameLogin.text();
        customerInfo.fullName = page.element.fullNameCus.val();
        customerInfo.debt = 0
        customerInfo.phone = page.element.phoneCus.val();
        customerInfo.locationRegion = locationRegion;
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "POST",
            "url": page.url.PostCustomer,
            "data": JSON.stringify(customerInfo)
        }).done((data) => {
            App.IziToast.showSuccessAlert("B???n ???? C???p Nh???t th??nh c??ng")
        }).fail((e) => {
            App.IziToast.showErrorAlert("B???n ???? C???p Nh???t Th???t B???i")
        })

        return;
    }
    customerInfo.id = page.element.idCustomer.val();
    customerInfo.userName = page.element.userNameLogin.text();
    customerInfo.fullName = page.element.fullNameCus.val();
    customerInfo.debt = page.element.debtCustomer.val();
    customerInfo.phone = page.element.phoneCus.val();
    customerInfo.locationRegion = locationRegion;
    $.ajax({
        "headers": {
            "accept": "application/json",
            "content-type": "application/json"
        },
        "type": "PUT",
        "url": page.url.PutCustomer,
        "data": JSON.stringify(customerInfo)
    }).done((data) => {
        App.IziToast.showSuccessAlert("B???n ???? C???p Nh???t th??nh c??ng")
    }).fail((e) => {
        App.IziToast.showErrorAlert("B???n ???? C???p Nh???t Th???t B???i")
    })

}

page.commands.handleChangPagePassword = () => {
    page.element.btnChangePagePassword.on("click", () => {
        page.element.container_infomation.addClass("d-none")
        page.element.pageOrder.addClass("d-none")
        page.element.pagePassword.removeClass("d-none")
        page.commands.handleChangPageInformation();
        page.commands.handleChangPageOrder()
        page.commands.handleSavePassword()
    })
}

page.commands.handleChangPageInformation = () => {
    page.element.btnchangPageInfomation.on("click", () => {
        page.element.pagePassword.addClass("d-none")
        page.element.pageOrder.addClass("d-none")
        page.element.container_infomation.removeClass("d-none")
        page.commands.handleChangPagePassword();
        page.commands.handleChangPageOrder()
        page.commands.handleUpdateCustomer();
    })
}

page.commands.handleChangPageOrder = () => {
    page.element.btnGetPageOrder.on("click", () => {
        page.element.pagePassword.addClass("d-none")
        page.element.container_infomation.addClass("d-none")
        page.element.pageOrder.removeClass("d-none")
        page.commands.handleChangPagePassword();
        page.commands.handleChangPageInformation();
        page.commands.handleUpdateCustomer();
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageApplyOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.handleChangPageSuccessDeliveryOrder()
        page.commands.handleChangPageDeliveryOrder()
        page.commands.changeStatusOrderCancel()
        page.commands.changeStatusOrderSuccessDelivery()
    })
}

page.commands.handleChangPageAllOrder = () => {
    page.dialogs.element.btnAllOrder.on("click", () => {
        page.dialogs.element.btnAllOrder.css({'border-bottom': '2px solid black', 'box-shadow': '-0px 2px 2px 0px'});
        page.dialogs.element.btnWattingOrder.removeAttr("style");
        page.dialogs.element.btnApplyOrder.removeAttr("style");
        page.dialogs.element.btnCancelOrder.removeAttr("style");
        page.element.pageDelivery.removeAttr("style")
        page.element.pageSuccessDelivery.removeAttr("style");
        page.element.listWattingOrder.addClass("d-none")
        page.element.listCancelOrder.addClass("d-none")
        page.element.listApplyOrder.addClass("d-none")
        page.element.listAllOrder.removeClass("d-none")
        page.element.listPageDelivery.addClass("d-none");
        page.element.listPageSuccessDelivery.addClass("d-none");
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageApplyOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.handleChangPageSuccessDeliveryOrder()
        page.commands.handleChangPageDeliveryOrder()
        page.commands.changeStatusOrderCancel()
        page.commands.changeStatusOrderSuccessDelivery()
    })
}

page.commands.handleChangPageWattingOrder = () => {
    page.dialogs.element.btnWattingOrder.on("click", () => {
        page.dialogs.element.btnWattingOrder.css({
            'border-bottom': '2px solid black',
            'box-shadow': '-0px 2px 2px 0px'
        });
        page.dialogs.element.btnAllOrder.removeAttr("style");
        page.dialogs.element.btnApplyOrder.removeAttr("style");
        page.dialogs.element.btnCancelOrder.removeAttr("style");
        page.element.pageDelivery.removeAttr("style")
        page.element.pageSuccessDelivery.removeAttr("style");
        page.element.listCancelOrder.addClass("d-none")
        page.element.listAllOrder.addClass("d-none")
        page.element.listApplyOrder.addClass("d-none")
        page.element.listWattingOrder.removeClass("d-none")
        page.element.listPageDelivery.addClass("d-none");
        page.element.listPageSuccessDelivery.addClass("d-none");
        page.commands.handleChangPageAllOrder()
        page.commands.handleChangPageApplyOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.handleChangPageSuccessDeliveryOrder()
        page.commands.handleChangPageDeliveryOrder()
        page.commands.changeStatusOrderCancel()
    })
}

page.commands.handleChangPageApplyOrder = () => {
    page.dialogs.element.btnApplyOrder.on("click", () => {
        page.dialogs.element.btnApplyOrder.css({'border-bottom': '2px solid black', 'box-shadow': '-0px 2px 2px 0px'});
        page.dialogs.element.btnAllOrder.removeAttr("style");
        page.dialogs.element.btnWattingOrder.removeAttr("style");
        page.dialogs.element.btnCancelOrder.removeAttr("style");
        page.element.pageDelivery.removeAttr("style")
        page.element.pageSuccessDelivery.removeAttr("style");
        page.element.listAllOrder.addClass("d-none")
        page.element.listWattingOrder.addClass("d-none")
        page.element.listCancelOrder.addClass("d-none")
        page.element.listApplyOrder.removeClass("d-none")
        page.element.listPageDelivery.addClass("d-none");
        page.element.listPageSuccessDelivery.addClass("d-none");
        page.commands.handleChangPageAllOrder()
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.handleChangPageSuccessDeliveryOrder()
        page.commands.handleChangPageDeliveryOrder()
    })
}

page.commands.handleChangPageCancelledOrder = () => {
    page.dialogs.element.btnCancelOrder.on("click", () => {
        page.dialogs.element.btnCancelOrder.css({'border-bottom': '2px solid black', 'box-shadow': '-0px 2px 2px 0px'});
        page.dialogs.element.btnAllOrder.removeAttr("style");
        page.dialogs.element.btnWattingOrder.removeAttr("style");
        page.dialogs.element.btnApplyOrder.removeAttr("style");
        page.element.pageDelivery.removeAttr("style")
        page.element.pageSuccessDelivery.removeAttr("style");
        page.element.listAllOrder.addClass("d-none")
        page.element.listWattingOrder.addClass("d-none")
        page.element.listApplyOrder.addClass("d-none")
        page.element.listCancelOrder.removeClass("d-none")
        page.element.listPageDelivery.addClass("d-none");
        page.element.listPageSuccessDelivery.addClass("d-none");
        page.commands.handleChangPageAllOrder()
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageApplyOrder()
        page.commands.handleChangPageSuccessDeliveryOrder()
        page.commands.handleChangPageDeliveryOrder()
    })
}

page.commands.handleChangPageDeliveryOrder = () => {
    page.element.pageDelivery.on("click", () => {
        page.element.pageDelivery.css({'border-bottom': '2px solid black', 'box-shadow': '-0px 2px 2px 0px'});
        page.dialogs.element.btnAllOrder.removeAttr("style");
        page.dialogs.element.btnWattingOrder.removeAttr("style");
        page.dialogs.element.btnCancelOrder.removeAttr("style");
        page.dialogs.element.btnApplyOrder.removeAttr("style");
        page.element.pageSuccessDelivery.removeAttr("style");
        page.element.listApplyOrder.addClass("d-none")
        page.element.listAllOrder.addClass("d-none")
        page.element.listWattingOrder.addClass("d-none")
        page.element.listCancelOrder.addClass("d-none")
        page.element.listPageSuccessDelivery.addClass("d-none");
        page.element.listPageDelivery.removeClass("d-none")
        page.commands.handleChangPageAllOrder()
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.changeStatusOrderSuccessDelivery()
    })
}

page.commands.handleChangPageSuccessDeliveryOrder = () => {
    page.element.pageSuccessDelivery.on("click", () => {
        page.element.pageSuccessDelivery.css({'border-bottom': '2px solid black', 'box-shadow': '-0px 2px 2px 0px'});
        page.dialogs.element.btnAllOrder.removeAttr("style");
        page.dialogs.element.btnWattingOrder.removeAttr("style");
        page.dialogs.element.btnCancelOrder.removeAttr("style");
        page.dialogs.element.btnApplyOrder.removeAttr("style");
        page.element.pageDelivery.removeAttr("style");
        page.element.listApplyOrder.addClass("d-none")
        page.element.listAllOrder.addClass("d-none")
        page.element.listWattingOrder.addClass("d-none")
        page.element.listCancelOrder.addClass("d-none")
        page.element.listPageDelivery.addClass("d-none");
        page.element.listPageSuccessDelivery.removeClass("d-none")
        page.commands.handleChangPageAllOrder()
        page.commands.handleChangPageWattingOrder()
        page.commands.handleChangPageCancelledOrder()
        page.commands.handleChangPageDeliveryOrder()

    })
}

page.commands.handleSavePassword = () => {
    page.element.btnSavePasword.on("click", function () {
        page.commands.getUserByUsername().then(() => {
            if (page.element.newPasswordCon.val() !== page.element.newPassword.val()) {
                App.IziToast.showErrorAlert("M???t kh???u kh??ng gi???ng nhau")
                return;
            }
            page.element.btnSavePasword.off()
            user.password = page.element.newPassword.val();
            $.ajax({
                "headers": {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                "type": "PUT",
                "url": page.url.PutUser,
                "data": JSON.stringify(user)
            }).done((data) => {
                App.IziToast.showSuccessAlert("B???n ???? Thay ?????i m???t kh???u th??nh c??ng")
                page.element.newPasswordCon.val("");
                page.element.newPassword.val("");
            }).fail((e) => {
                console.log(e);
            })
        })
    })
}

page.commands.blurInputsearch = () => {
    page.element.InputQuerySearch.blur(function () {
        console.log("vao day")
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
        url: page.url.GetCustomers + "/username/" + $("#EmailLogin").text()
    }).done((data) => {
        customerInfo = data;
        $("#fullNameScreen").text(customerInfo.fullName)
    }).fail((e) => {
        $("#fullNameScreen").text($("#EmailLogin").text())
    })
}

page.commands.changeStatusOrderCancel = () => {
    $(".cancel-orderr").on("click", function () {
        Swal.fire({
            title: 'B???n c?? mu???n H???y ????n kh??ng?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '????ng, T??i mu???n H???y!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id")
                page.commands.getOrderDetalById(id).then(function () {
                    console.log(orderDetail);
                    $.ajax({
                        "headers": {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        "type": "PUT",
                        "url": page.url.PutOrderDetailCanel,
                        "data": JSON.stringify(orderDetail)
                    }).done((data) => {
                        console.log(data)
                        orderDetail = data;
                        App.SweetAlert.showSuccessAlert("B???n ???? h???y ????n th??nh c??ng")
                        setTimeout(function () {
                            window.location.href = "/account";
                        }, 1500);

                    }).fail((e) => {
                        console.log(e)
                    })
                })
            }
        })
    })
}

page.commands.changeStatusOrderSuccessDelivery = () => {
    $(".success-orderr").on("click", function () {
        Swal.fire({
            title: 'B???n ???? nh???n ???????c ????n h??ng ph???i kh??ng?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '????ng, T??i ???? nh???n ???????c h??ng!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                let id = $(this).data("id")
                page.commands.getOrderDetalById(id).then(function () {
                    console.log(orderDetail);
                    $.ajax({
                        "headers": {
                            "accept": "application/json",
                            "content-type": "application/json"
                        },
                        "type": "PUT",
                        "url": page.url.PutOrderDetailSuccessDeliveryy,
                        "data": JSON.stringify(orderDetail)
                    }).done((data) => {
                        console.log(data)
                        orderDetail = data;
                        App.SweetAlert.showSuccessAlert("B???n ???? Nh???n h??ng th??nh c??ng!")
                        setTimeout(function () {
                            window.location.href = "/account";
                        }, 1500);
                    }).fail((e) => {
                        console.log(e)
                    })
                })
            }
        })

    })
}

page.element.frmUpdateCustomer.validate({
    "rules": {
        "billing_first_name": {
            required: true,
            minlength: 5,
        },
        "emailCus": {
            required: true,
            email: true,
            minlength: 9,
        },
        "billing_phone": {
            required: true,
            number: true,
            minlength: 3,
            maxlength: 50,
        },
        "billing_address_1": {
            required: true,
            minlength: 6,
        },
    },
    "messages": {
        "billing_first_name": {
            required: "Vui L??ng Nh???p H??? v?? t??n!",
            minlength: $.validator.format("H??? v?? t??n t???i thi???u {0} k?? t???!"),
        },

        "emailCus": {
            required: "vui l??ng nh???p Email!",
            email: "Vui l??ng nh???p ????ng ?????nh d???ng Email (VD: phucnguyen@gmail.com)!",
            minlength: $.validator.format("Email t???i thi???u {0} k?? t???!"),
            maxlength: $.validator.format("Email t???i ??a {0} k?? t???"),
        },

        "billing_phone": {
            required: "Vui L??ng Nh???p S??? ??i???n tho???i!",
            minlength: $.validator.format("S??? ??i???n tho???i t???i thi???u {0} k?? t???!"),
        },

        "billing_address_1": {
            required: "Vui L??ng Nh???p ?????a ch???!",
            minlength: $.validator.format("?????a ch??? t???i thi???u {0} k?? t???!"),
        }
    },
    errorLabelContainer: "#frmBuyProduct .input.error",
    submitHandler: function () {
        page.element.afterClickUpdateInformation();
    }
})

page.initializeControlEvent = () => {
    page.dialogs.commands.drawDistricts(page.element.select_provinceCUS.val());
    page.commands.handleShowCart();
    page.commands.showIconCart();
    page.commands.getCustomerByUserName();
    page.commands.getCustomerInfoByEmail();
    page.commands.handleUpdateCustomer();
    page.commands.handleChangPageInformation();
    page.commands.handleChangPagePassword();
    page.commands.getUserByUsername();
    page.commands.handleChangPageOrder();
    page.commands.searchFunction()
    page.commands.handleGoSearch()
    page.element.emailCus.val($("#EmailLogin").text())
}

$(() => {
    page.dialogs.commands.drawProvinces().then(() => {
        page.initializeControlEvent()
    })
})
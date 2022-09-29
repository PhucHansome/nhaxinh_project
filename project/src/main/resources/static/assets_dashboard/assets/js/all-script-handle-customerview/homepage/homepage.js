let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        GetCustomer: App.BASE_URL_CUSTOMERINFO,
        PostProduct: App.BASE_URL_PRODUCT,
        PostCartItem: App.BASE_URL_CARTITEM,
        PostCart: App.BASE_URL_CART,
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
let customerInfo = new CustomerInfo()

page.element.cart_product = $("#cart_product_view")
page.element.cart_cartBotLine = $("#product-grid-1457246667")
page.element.imagePare = $("#imagePare")
page.element.idProduct = $("#idProduct")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.resuilt_search = $(".resuilt_search")
page.element.btnCancelPopUp = $(".btn-cancel-popup")

page.element.popUp = $(".popup_stock_wraper")
page.element.InputQuerySearch = $("#querySearch")
page.element.btnShowCart = $("#cart_")
page.element.chua_cart = $(".chua_cart")

let cart_product = jQuery.validator.format($.trim(page.element.cart_product.val().toString()));
let cart_item_product = jQuery.validator.format($.trim(page.element.CaritemProduct.val().toString()));

page.commands.addCartBotLine = () => {
    page.element.cart_cartBotLine.append($(cart_product(product.image, product.title, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(product.price), product.id, product.slug)));
}

page.commands.addCartRow = () => {
    page.element.container_cartItem.prepend($(cart_item_product(carItem.product.image, carItem.product.title, carItem.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price), carItem.id, carItem.product.slug)));
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
        App.IziToast.showErrorAlert("Xin vui lòng đăng nhập để thực hiện chức năng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.getUserByEmail = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetUser
    }).done((data) => {
        console.log(data);
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

page.commands.handleCreateCart = () => {
    $(".them_vao_cart").on("click", function () {
        let id = $(this).data("id");
        page.commands.getProductById(id).then(() => {
            if ($("#EmailLogin").text() === "Đăng nhập") {
                App.IziToast.showErrorAlert("Phiền bạn đăng nhập để thực hiện chức năng này")
                return;
            }
            if (product.quantity < 0) {
                App.IziToast.showErrorAlert("Sản phẩm này hiện tại đã hết hàng!");
                return;
            }
            delete carItem.id;
            carItem.userName = $("#EmailLogin").text();
            carItem.product = product;
            carItem.price = product.price;
            carItem.quantity = 1;
            carItem.grandTotal = product.price;
            $.ajax({
                "headers": {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                "async": false,
                "type": "POST",
                "url": page.url.PostCartItem,
                "data": JSON.stringify(carItem)
            }).done((data) => {
                App.IziToast.showSuccessAlert("Đã thêm vào giỏ hàng thành công")
                page.commands.showIconCart();
            }).fail((jqXHR) => {
                console.log(jqXHR);
                if (jqXHR.responseJSON) {
                    $.each(jqXHR.responseJSON, (key, item) => {
                        App.IziToast.showErrorAlert(item);
                    })
                }
            })
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

page.dialogs.loadData.getAllProduct = () => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetProduct + "/product-status-active"
    }).done((data) => {
        console.log(data)
        $.each(data, (i, item) => {
            if (i > 7) {
                return
            }
            product = item;
            page.commands.addCartBotLine();
            if (product.status === "Đã Hết Hàng") {
                $(".btn-add-cart").html("");
            }
            // console.log(product.quantity)
            if (Number(product.quantity) < 1) {
                $(".btn-add-cart").html("");
            }
        })
        page.commands.handleCreateCart()
        page.commands.showIconCart();
        page.commands.handleGoDetailProduct();
        page.commands.searchFunction();
        page.commands.handleGoSearch();
    }).fail((e) => {
        console.log(e)
    })
}

page.commands.handleGoDetailProduct = () => {
    $(".view_Product").on("click", function () {
        let id = $(this).data("id");
        page.commands.getProductById(id).then(() => {
            window.location.href = "/detail/" + product.slug;
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

page.commands.getCustomerByUserName = () => {
    $.ajax({
        type: "GET",
        url: page.url.GetCustomer + "/username/" + $("#EmailLogin").text()
    }).done((data) => {
        customerInfo = data;
        $("#fullNameScreen").text(customerInfo.fullName)
    }).fail((e) => {
        $("#fullNameScreen").text($("#EmailLogin").text())
    })
}

page.commands.handleGoSearch = () => {
    $(".btn-search").on("click", function () {
        window.location.href = "/search/page=1?option=1&choicePrice=0&Categories=null&Color=null&query=" + page.element.InputQuerySearch.val();
    })
}

page.commands.cancelPopUp = () => {
    page.element.btnCancelPopUp.on("click", () => {
        $(".popup_stock_wraper").removeClass("active");
    })
}

page.initializeControlEvent = () => {
    page.dialogs.loadData.getAllProduct();
    page.commands.handleShowCart()
    page.commands.searchFunction();
    page.commands.handleGoSearch();
    page.commands.getCustomerByUserName()
    setTimeout(() => {
        page.element.popUp.addClass("active")
    }, 500)
    page.commands.cancelPopUp()
}

$(() => {
    page.initializeControlEvent()
})
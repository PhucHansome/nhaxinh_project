let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetCartItemsId: App.BASE_URL_CARTITEM +"/id",
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        GetCustomer: App.BASE_URL_CUSTOMERINFO,
        PostProduct: App.BASE_URL_PRODUCT,
        PostCartItem: App.BASE_URL_CARTITEM,
        PostCart: App.BASE_URL_CART,
        PutCartReduce: App.BASE_URL_CARTITEM + "/reduce",
        PutCartIncreasing: App.BASE_URL_CARTITEM + "/increasing",
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

page.element.cart_product = $("#cart_product_view")
page.element.cart_cartBotLine = $("#product-grid-1457246667")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.tableListCartItem = $(".woocommerce-cart-form__contents tbody")
page.element.list_total_All = $(".total_All tbody")
page.element.cart_product_line = $("#cart_product_line")
page.element.cart_product_total = $("#cart_product_total")
page.element.resuilt_search = $(".resuilt_search")

page.element.InputQuerySearch = $("#querySearch")
page.element.btnShowCart = $("#cart_")
page.element.chua_cart = $(".chua_cart")

let cart_item_product = jQuery.validator.format($.trim(page.element.CaritemProduct.val().toString()));
let cart_item_product_inline = jQuery.validator.format($.trim(page.element.cart_product_line.val().toString()));
let cart_item_product_inline_total = jQuery.validator.format($.trim(page.element.cart_product_total.val().toString()));

page.commands.totalCartProduct = (sum) => {
    page.element.list_total_All.prepend($(cart_item_product_inline_total(new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(sum))))
}

page.commands.showCartInline = () => {
    page.element.tableListCartItem.prepend($(cart_item_product_inline(carItem.product.image, carItem.product.title, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price), carItem.quantity, carItem.id)));
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

page.commands.getCartItemById = (id) => {
    return $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        "method": "GET",
        "url": page.url.GetCartItemsId + "/" + id
    }).done((data) => {
        console.log(data);
        carItem = data;
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

page.commands.deleteInline = (id) => {
    $.ajax({
        "method": "DELETE",
        "url": page.url.DeleteCartItem + "/" + id
    }).done(() => {
        App.IziToast.showSuccessAlert("Xóa sản phẩm thành công")
        page.loadData.getAllItemOrder()
    })
}

page.commands.handleCloseCart = () => {
    $(".mfp-close").on("click", () => {
        $(".showCart").css({'display': 'none'});
    })
}

page.commands.handleReduceCartItem = (id) => {
    page.commands.getCartItemById(id).then(() => {
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "PUT",
            "url": page.url.PutCartReduce,
            "data": JSON.stringify(carItem)
        }).done((data) => {
            App.IziToast.showSuccessAlert("Giảm số lượng thành công")
            page.loadData.getAllItemOrder()
        }).fail((jqXHR) => {
            console.log(jqXHR);
            if (jqXHR.responseJSON) {
                $.each(jqXHR.responseJSON, (key, item) => {
                    App.IziToast.showErrorAlert(item) ;
                })

            }
        })
    })
}

page.commands.handleIncreasingCartItem = (id) => {
    page.commands.getCartItemById(id).then(() => {
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "PUT",
            "url": page.url.PutCartIncreasing,
            "data": JSON.stringify(carItem)
        }).done((data) => {
            App.IziToast.showSuccessAlert("Tăng số lượng thành công")
            page.loadData.getAllItemOrder()
        }).fail((jqXHR) => {
            console.log(jqXHR);
            if (jqXHR.responseJSON) {
                $.each(jqXHR.responseJSON, (key, item) => {
                    App.IziToast.showErrorAlert(item) ;
                })

            }
        })
    })
}

page.loadData.getAllItemOrder = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetCartItems,
    }).done((data) => {
        page.element.list_total_All.html("")
        page.element.tableListCartItem.html("")
        $(".total_count").text(data.length);
        if (data.length === 0) {
            page.element.tableListCartItem.prepend(`<h1 style="align-content: center">Giỏ Hàng Trống</h1>`)
        }
        let sum = 0
        $.each(data, (i, item) => {
            carItem = item;
            sum += carItem.grandTotal
            page.commands.showCartInline()
        })
        page.commands.showIconCart()
        page.commands.totalCartProduct(sum)
    }).fail((jqXHR) => {
        console.log(jqXHR);
        if (jqXHR.responseJSON) {
            $.each(jqXHR.responseJSON, (key, item) => {
                App.IziToast.showErrorAlert(item) ;
            })

        }
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
    $(".btn-search").on("click", function (){
        window.location.href = "/search/page=1?option=1&choicePrice=0&Categories=null&Color=null&query=" + page.element.InputQuerySearch.val();
    })
}

page.commands.getCustomerByUserName = ()=>{
    $.ajax({
        type : "GET",
        url: page.url.GetCustomer + "/username/" + $("#EmailLogin").text()
    }).done((data)=>{
        customerInfo = data;
        $("#fullNameScreen").text(customerInfo.fullName)
    }).fail((e)=>{
        $("#fullNameScreen").text($("#EmailLogin").text())
    })
}

page.initializeControlEvent = () => {
    page.commands.handleShowCart()
    page.commands.showIconCart()
    page.loadData.getAllItemOrder()
    page.commands.searchFunction()
    page.commands.handleGoSearch()
    page.commands.getCustomerByUserName();
}

$( () => {
    page.initializeControlEvent()
})
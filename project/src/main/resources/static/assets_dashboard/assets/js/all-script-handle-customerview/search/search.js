let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetProductMedia: App.BASE_URL_PRODUCT + "/productmedia",
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        GetProductColor: App.BASE_URL_PRODUCTCOLOR,
        GetCategory: App.BASE_URL_CATEGORY,
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
let productMedia = new ProductMedia();
let category = new Category();
let productColor = new ProductColor()
// let product = new Product();

page.element.cart_product = $("#cart_product_view")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.idProduct = $("#productId")
page.element.valueInput = $("#valueInput")
page.element.valueQuantity = $("#quantity_62fc4b3185e6c")
page.element.resuilt_search = $(".resuilt_search")
page.element.Query_Resuilt_inUrl = $("#Query_Resuilt")
page.element.resuilt_product_Seach_In_query = $(".product_Seach_In_query")
page.element.container_line_resuilt_search = $("#resuilt_query_search")
page.dialogs.element.optionSelect1 = $("#selectOptionPrice_time")
page.dialogs.element.radioSelect = $("input:radio[name=debt-amount]")
page.element.allResuitSearch = $(".all-resuit-product-search")
page.element.selectColor = $("#ColorSelect")
page.element.selectCategories = $("#selectOptionCategories ")
page.element.selectCateogries_value = $("#selectOptionCategories_Value")
page.element.selectColor_value = $("#ColorSelect_Value")

page.element.InputQuerySearch = $("#querySearch")
page.element.btnShowCart = $("#cart_")
page.element.chua_cart = $(".chua_cart")
page.element.main_photo = $(".main_photo")
page.element.each_image = $(".each_image")
page.element.single_add_to_cart_button = $(".btn-add-to-cart")
page.element.btnBuy = $(".btn-buy")


let cart_item_product = jQuery.validator.format($.trim(page.element.CaritemProduct.val().toString()));

let resuilt_line_product = jQuery.validator.format($.trim(page.element.container_line_resuilt_search.val().toString()));

page.commands.addCartRow = () => {
    page.element.container_cartItem.prepend($(cart_item_product(carItem.product.image, carItem.product.title, carItem.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(carItem.price), carItem.id, carItem.product.slug)));
}

page.commands.addCartBotLine = () => {
    page.element.resuilt_product_Seach_In_query.append($(resuilt_line_product(product.image, product.title, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(product.price), product.id, product.slug)));
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
        App.IziToast.showSuccessAlert("Xóa sản phẩm thành công")
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
            let sum = 0
            console.log(data)
            if (data.length === 0) {
                page.element.container_cartItem.prepend(`<h1 style="align-content: center">Giỏ Hàng Trống</h1>`)
                $(".woocommerce-mini-cart__buttons").html("")
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

page.commands.handleCloseCart = () => {
    $(".mfp-close").on("click", () => {
        $(".showCart").css({'display': 'none'});
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


page.commands.handleCreateCart = () => {
    $(".them_vao_cart").on("click", function () {
        let id = $(this).data("id");
        page.commands.getProductById(id).then(() => {
            if ($("#EmailLogin").text() === "Đăng nhập") {
                App.IziToast.showErrorAlert("Phiền bạn đăng nhập để thực hiện chức năng này")
                return;
            }
            if ((Number(product.quantity) - 1) < 0) {
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
                page.commands.handleGoSearch();
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

page.commands.handleGoSearch = () => {
    $(".btn-search").on("click", function () {
        window.location.href = "/search/page=1?option=1&choicePrice=0&Categories=null&Color=null&query=" + page.element.InputQuerySearch.val();
    })
}

page.commands.handleApply = () => {
    $(".apply-filters").on("click", function () {
        if (page.dialogs.element.radioSelect.filter('[value=5]').is(":checked")) {
            window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=5" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
            return
        }
        if (page.dialogs.element.radioSelect.filter('[value=4]').is(":checked")) {
            window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=4" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
            return
        }
        if (page.dialogs.element.radioSelect.filter('[value=3]').is(":checked")) {
            window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=3" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
            return
        }
        if (page.dialogs.element.radioSelect.filter('[value=2]').is(":checked")) {
            window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=2" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
            return
        }
        if (page.dialogs.element.radioSelect.filter('[value=1]').is(":checked")) {
            window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=1" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
            return
        }
        window.location.href = "/search/page=1?option=" + page.dialogs.element.optionSelect1.val() + "&choicePrice=0" + "&Categories=" + page.element.selectCategories.val() + "&Color=" + page.element.selectColor.val() + "&query=" + page.element.Query_Resuilt_inUrl.text()
        return
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

page.commands.loadChoicePrice = () => {
    if ($("#choicePrice").val() === '1') {
        page.dialogs.element.radioSelect.filter('[value=1]').prop('checked', true);
        return;
    }
    if ($("#choicePrice").val() === '2') {
        page.dialogs.element.radioSelect.filter('[value=2]').prop('checked', true);
        return;
    }
    if ($("#choicePrice").val() === '3') {
        page.dialogs.element.radioSelect.filter('[value=3]').prop('checked', true);
        return;
    }
    if ($("#choicePrice").val() === '4') {
        page.dialogs.element.radioSelect.filter('[value=4]').prop('checked', true);
        return;
    }
    if ($("#choicePrice").val() === '5') {
        page.dialogs.element.radioSelect.filter('[value=5]').prop('checked', true);
        return

    }
}

page.loadData.drawSelectCategory = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetCategory
    }).done((data) => {
        console.log(data)
        page.element.selectCategories.html("")
        $.each(data, (i, item) => {
            category = item;
            let str = `
                    <option class="filter-item" value="${category.name}">${category.name}</option>
                    `
            page.element.selectCategories.append(str)
        });
        page.element.selectCategories.val(page.element.selectCateogries_value.val())
    }).fail((e) => {
        console.log(e);
    })
}

page.loadData.drawSelectColor = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProductColor
    }).done((data) => {
        console.log(data)
        page.element.selectColor.html("")
        $.each(data, (i, item) => {
            productColor = item;
            let str = `
                    <option class="filter-item" value="${productColor.color}">${productColor.color}</option>
                    `
            page.element.selectColor.append(str)
        });
        page.element.selectColor.val(page.element.selectColor_value.val())
    }).fail((e) => {
        console.log(e);
    })
}


page.initializeControlEvent = () => {
    page.commands.handleShowCart()
    page.commands.showIconCart()
    page.commands.searchFunction()
    page.commands.handleGoDetailProduct()
    page.commands.handleCreateCart();
    page.commands.handleGoSearch();
    page.commands.getCustomerByUserName()
    page.commands.handleApply()
    page.loadData.drawSelectCategory()
    page.loadData.drawSelectColor();
    page.dialogs.element.optionSelect1.val($("#optionSelect1").val())
    page.commands.loadChoicePrice();
}

$(() => {
    page.initializeControlEvent()
})
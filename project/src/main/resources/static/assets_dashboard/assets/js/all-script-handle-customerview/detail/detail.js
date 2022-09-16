let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetUser: App.BASE_URL_USER_ABC + "/" + $("#EmailLogin").text(),
        GetCartItems: App.BASE_URL_CARTITEM + "/" + $("#EmailLogin").text(),
        GetProductMedia: App.BASE_URL_PRODUCT + "/productmedia",
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
let productMedia = new ProductMedia();
let tag = new Tag();

page.element.cart_product = $("#cart_product_view")
page.element.cart_cartBotLine = $("#product-grid-1457246667")
page.element.userNameLo = $("#header-account-title");
page.element.CaritemProduct = $("#CaritemProduct");
page.element.container_cartItem = $("#seeCartItems")
page.element.idProduct = $("#productId")
page.element.valueInput = $("#valueInput")
page.element.valueQuantity = $("#quantity_62fc4b3185e6c")
page.element.resuilt_search = $(".resuilt_search")
page.element.container_line_resuilt_search = $("#resuilt_query_search")
page.element.resuilt_product_Seach_In_query = $(".product_Seach_In_query")
page.element.SameProduct = $("#SameProduct")

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
    page.element.resuilt_product_Seach_In_query.append($(resuilt_line_product(tag.product.image, tag.product.title, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(tag.product.price), tag.product.id, tag.product.slug)));
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

page.commands.getProductMediaByProductId = () => {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        "method": "GET",
        "url": page.url.GetProductMedia + "/" + $("#productId").val()
    }).done((data) => {
        $(".image_big-view").html("")
        let sum = 0
        $.each(data, (i, item) => {
            productMedia = item;
            if (i === data.length - 1) {
                let str1 = `
                        <div class="col first is-nav-selected is-selected"
                                     style="position: absolute; left: ${sum}%;">
                                    <a>
                                        <img src="${productMedia.fileUrl}"
                                             alt="" width="300" height=""
                                             class="attachment-woocommerce_thumbnail"> </a>
                                </div>
                        `
                page.element.each_image.append(str1)


                let str2 = `
                         <div data-thumb="https://nhaxinh.com/wp-content/uploads/2021/10/ban-an-roma-100x100.jpg"
                         class="woocommerce-product-gallery__image slide first is-selected"
                         style="position: absolute; left: 0%;">
                         <a src="${productMedia.fileUrl}">
                            <img
                            width="500" height="333"
                            src="${productMedia.fileUrl}"
                            class="wp-post-image skip-lazy lazy-load-active">
                            </a>
                            </div>
                        `
                $(".image_big-view").append(str2)
                return;
            }
            sum += 100;
            let str = `
                                <div class="col" aria-hidden="true"
                                     style="position: absolute; left: 95.82%;"><a><img
                                        src="${productMedia.fileUrl}"
                                        data-src="${productMedia.fileUrl}"
                                        alt="" width="300" height=""
                                        class="attachment-woocommerce_thumbnail lazy-load-active"></a>
                                </div>
                    `;
            page.element.each_image.append(str)
            let str3 = `
                         <div data-thumb="https://nhaxinh.com/wp-content/uploads/2021/10/ban-an-roma-100x100.jpg"
                         class="woocommerce-product-gallery__image slide first is-selected"
                         style="position: absolute; left: ${sum}%;">
                         <a src="${productMedia.fileUrl}">
                            <img
                            width="500" height="333"
                            src="${productMedia.fileUrl}"
                            class="wp-post-image skip-lazy lazy-load-active">
                            </a>
                            </div>
                        `
            $(".image_big-view").append(str3)
        })
    }).fail((e) => {
        console.log(e)
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

page.commands.handleCloseCart = () => {
    $(".mfp-close").on("click", () => {
        $(".showCart").css({'display': 'none'});
    })
}

page.commands.handleReduce = () => {
    if (Number(page.element.valueQuantity.val()) < 1) {
        App.IziToast.showErrorAlert("số lượng không được nhỏ hơn 0!")
        return;
    }
    page.element.valueQuantity.val(Number(page.element.valueQuantity.val()) - 1);
    if (Number(page.element.valueQuantity.val()) === 0) {
        App.IziToast.showErrorAlert("số lượng không được nhỏ hơn 0!")
        page.element.valueQuantity.val(1)
        return;
    }
}

page.commands.handlePlus = () => {
    if (page.element.valueQuantity.val() > 4) {
        App.IziToast.showErrorAlert("Số lượng không được nhỏ hơn 5!")
        page.element.valueQuantity.val(5)
        return;
    }
    page.element.valueQuantity.val(Number(page.element.valueQuantity.val()) + 1);
}

page.commands.handleAddToCart = () => {
    page.element.single_add_to_cart_button.on("click", () => {
        if ($("#EmailLogin").text() === "Đăng nhập") {
            App.IziToast.showErrorAlert("Phiền bạn đăng nhập để thực hiện chức năng này")
            return;
        }

        page.commands.getProductById(page.element.idProduct.val()).then(() => {
            if ((Number(product.quantity) - Number(page.element.valueQuantity.val())) < 0) {
                App.IziToast.showErrorAlert("Số lượng sản phầm này hiện tại không đủ!");
                return;
            }
            delete carItem.id;
            carItem.product = product;
            carItem.price = product.price;
            carItem.quantity = page.element.valueQuantity.val();
            if (Number(carItem.quantity) === 0) {
                App.IziToast.showErrorAlert("Số lượng sản phẩm không đủ để đặt hàng")
                return;
            }
            let pattern = /\d/;
            if (pattern.test(carItem.quantity) === false) {
                App.IziToast.showErrorAlert("đây không phải số")
                return;
            }
            carItem.grandTotal = Number(carItem.quantity) * Number(product.price);
            carItem.userName = $("#EmailLogin").text();

            $.ajax({
                "headers": {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                "async": false,
                "type": "POST",
                "url": page.url.PostCartItem + "/creat-cart-in-detail",
                "data": JSON.stringify(carItem)
            }).done((data) => {
                console.log(data)
                App.IziToast.showSuccessAlert("Thêm vào giỏ hàng thành công")
                page.commands.showIconCart();

            }).fail((e) => {
                App.IziToast.showErrorAlert("Xin vui lòng đăng nhập để thực hiện chức năng")
                setTimeout(function () {
                    window.location.href = "/login"
                }, 500);
            })
        })
    })
}

page.commands.handleBuy = () => {
    page.element.btnBuy.on("click", () => {
        page.commands.getProductById(page.element.idProduct.val()).then(() => {
            if ($("#EmailLogin").text() === "Đăng nhập") {
                App.IziToast.showErrorAlert("Phiền bạn đăng nhập để thực hiện chức năng này")
                return;
            }
            if ((Number(product.quantity) - Number(page.element.valueQuantity.val())) < 0) {
                App.IziToast.showErrorAlert("Số lượng sản phầm này hiện tại không đủ!");
                return;
            }
            delete carItem.id;
            carItem.product = product;
            carItem.price = product.price;
            carItem.quantity = page.element.valueQuantity.val();
            if (Number(carItem.quantity) === 0) {
                App.IziToast.showErrorAlert("Số lượng sản phẩm không đủ để mua hàng!")
                return;
            }
            let pattern = /\d/;
            if (pattern.test(carItem.quantity) === false) {
                App.IziToast.showErrorAlert("đây không phải số")
                return;
            }
            carItem.grandTotal = Number(carItem.quantity) * Number(product.price);
            console.log(carItem.grandTotal)
            carItem.userName = $("#EmailLogin").text();
            $.ajax({
                "headers": {
                    "accept": "application/json",
                    "content-type": "application/json"
                },
                "async": false,
                "type": "POST",
                "url": page.url.PostCartItem + "/creat-cart-in-detail",
                "data": JSON.stringify(carItem)
            }).done((data) => {
                console.log(data)
                page.commands.showIconCart();
                setTimeout(function () {
                    window.location.href = "/cart"
                }, 100);
            }).fail((e) => {
                App.IziToast.showErrorAlert("Xin vui lòng đăng nhập để thực hiện chức năng")
                setTimeout(function () {
                    window.location.href = "/login"
                }, 500);
            })
        })
    })
}

page.element.valueQuantity.on("blur", () => {
    if (page.element.valueQuantity.val() < 2) {
        App.IziToast.showErrorAlert("số lượng không được nhỏ hơn 0!")
        page.element.valueQuantity.val(1)
        return;
    }
    if (page.element.valueQuantity.val() > 4) {
        App.IziToast.showErrorAlert("Số lượng không được nhỏ hơn 5!")
        page.element.valueQuantity.val(5)
        return;
    }
})

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
        window.location.href = "page=1?option=1&choicePrice=0&Categories=null&Color=null&query=" + page.element.InputQuerySearch.val();
    })
}

page.commands.getSearchResuilt = () => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetResuiltSearch + "/" + page.element.SameProduct.text()
    }).done((data) => {
        page.element.resuilt_product_Seach_In_query.html("")
        $.each(data, (i, item) => {
            if (i > 3) {
                return
            }
            tag = item
            page.commands.addCartBotLine()
        })
        page.commands.handleGoDetailProduct()
        page.commands.handleCreateCart()
        page.commands.handleGoSearch();
    }).fail((e) => {
        console.log(e)
    })
}

page.commands.getProductBySlug = (slug) => {
    return $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        "method": "GET",
        "url": page.url.GetProduct + "/product/slug/" + slug
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

page.commands.btnClick = () => {
    $("#btn-view-stock-modal").on("click", () => {
        $(".popup_stock_wraper").addClass("active");
        $("#close").on("click", () => {
            $(".popup_stock_wraper").removeClass("active");
        })
    })
}

page.initializeControlEvent = () => {
    page.commands.handleShowCart()
    page.commands.showIconCart()
    page.commands.getProductMediaByProductId();
    page.commands.handleBuy()
    page.commands.searchFunction()
    page.commands.handleGoSearch()
    page.commands.getSearchResuilt();
    page.commands.handleAddToCart();
    page.commands.getCustomerByUserName()
    page.commands.btnClick();
}

$(() => {
    page.initializeControlEvent()
})
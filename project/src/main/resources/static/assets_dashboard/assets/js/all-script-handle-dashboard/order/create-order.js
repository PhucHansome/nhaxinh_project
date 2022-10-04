let page = {
    url: {
        GetResuiltSearch: App.BASE_URL_PRODUCT + "/product/search",
        GetProduct: App.BASE_URL_PRODUCT,
        GetAllCustomerInfo: App.BASE_URL_CUSTOMERINFO,
        GetCartItems: App.BASE_URL_CARTITEM,
        GetCart: App.BASE_URL_CART + "/",
        DeleteCartItem: App.BASE_URL_CARTITEM,
        PutCustomer: App.BASE_URL_CUSTOMERINFO + "/edit",
        PostCartItem: App.BASE_URL_CARTITEM + "/createCartAndCartItem",
        PostOrder: App.BASE_URL_ORDER + "/create-order-dashboard",
        PutCartItem: App.BASE_URL_CARTITEM

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
let product = new Product();
let tag = new Tag();
let cartItems = new CartItems();
let cart = new Cart();

page.element.AllCustomer = $(".AllCustomerInfor")

page.commands.drawCustomer = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetAllCustomerInfo
    }).done((data) => {
        $(".AllCustomerInfor").html("")
        $(".AllCustomerInfor").select2()
        $(".AllCustomerInfor").append(`<option value="null">Hãy chọn khách hàng</option>`);
        $.each(data, (i, item) => {
            customerInfo = item
            let str = `
            <option value="${customerInfo.id}">${customerInfo.fullName}</option>  
            `
            $(".AllCustomerInfor").append(str);
        })
        page.commands.SelectedCustomer();
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Không thể lấy được dữ liệu người dùng")
    })
}

page.commands.getCustomerInfoById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetAllCustomerInfo + "/" + id
    }).done((data) => {
        customerInfo = data;
    })
}

page.commands.getProductById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProduct + "/" + id
    }).done((data) => {
        console.log(data);
        product = data;
    }).fail((e) => {
        console.log(e)
    })
}

const getCartItemById = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetCartItems + "/id/" + id
    }).done((data) => {
        cartItems = data;
    }).fail((e) => {
        console.log(e)
    })
}

const getCartByCustomerId = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetCart + id
    }).done((data) => {
        cart = data;
    }).fail((e) => {
        console.log(e)
    })
}

const getAllCartItem = (userName) => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetCartItems + '/' + userName
    }).done((data) => {
        $("#listCartitems tbody").html("")
        $("#listCartitems thead").removeClass("d-none")
        $(".btnCreateOrder").removeClass("d-none")
        let quanity = 0
        let sum = 0
        if (data.length === 0) {
            $("#listCartitems tbody").html("")
            $("#listCartitems thead").addClass("d-none")
            $(".btnCreateOrder").addClass("d-none")
        }
        $.each(data, (i, item) => {
            cartItems = item;
            let str = `
            <tr>
                <td  class="text-center align-middle">${i + 1}</td>
                <td  class="text-center align-middle"><img width="50px" height="50px" src="${cartItems.product.image}" alt=""></td>
                <td ><a href="/detail-product-dashboard/${cartItems.product.id}">
                    <div class="row" style="padding: 0px 0px 0px 10px;"><span style="font-size: 15px;
                                                  font-weight: 500;">${cartItems.product.title}</span></div>
                    <div class="row" style="padding: 0px 0px 0px 10px;"><span style="  color: #0000007d;
                                                    font-size: 12px;
                                                    padding: 5px 0px 0px 0px;">${cartItems.product.code}</span></div>
                     </a>
                </td>
                <td class="text-center align-middle"><input style="    width: 20%;
                                                                        border: none;
                                                                        border-bottom: 1px solid black;" type="number" id="inputQuantity_${cartItems.id}" onchange="handleChangeInputItem(${cartItems.id})" value="${cartItems.quantity}"></td>
                <td  class="text-center align-middle"> <span style="font-weight: 500;
                                                font-size: 14px;">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(cartItems.product.price)}</span></td>
                <td  class="text-center align-middle"><span style="font-weight: 500;
                                                font-size: 14px;">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(cartItems.grandTotal)}</span></td>
                <td  class="text-center align-middle"><i style="cursor: pointer" onclick="deleteCartItems(${cartItems.id})" class="fa fa-times-circle"></i></td>
            </tr> 
            `
            $("#listCartitems tbody").append(str)
            quanity += cartItems.quantity;
            sum += cartItems.grandTotal
        })
        $(".all-bill").html("")
        let str2 = `
             <div class="col-12 ">
                         <div class="row" style="   font-weight: 500;
                                    font-size: 16px;
                                    padding: 12px 0px 12px 0px;">
                <div class="col-6">
                    <span style="">Tổng tiền(<span style="color: blue;"> ${quanity} </span> Sản phẩm):</span>
                </div>
                <div class="col-6">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(sum)}</div>
                </div>
            </div>
            <div class="col-12">
            <div class="row" style="   font-weight: 500;
                                    font-size: 16px;    
                                    padding: 12px 0px 12px 0px;">
                <div class="col-6"><span>Khách hàng phải trả:</span>
                </div>
                <div class="col-6">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(sum)}</div></div>
            </div>
            <div class="col-12">
            <div class="row" style="font-weight: 500;
                                    font-size: 16px;   
                                     padding: 12px 0px 12px 0px;">
                <div class="col-6">
                    <span>Tổng tiền toàn bộ đơn hàng:</span>
                </div>
                <div class="col-6">${new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(sum)}</div>
                </div>
            </div>
            `
        $(".all-bill").append(str2)

        handleCreateOrder();
    }).fail((e) => {
        console.log(e)

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
            $("#districtUp").html("");
            $.each(data.results, (i, item) => {
                let str = `<option  class="form-control" value="${item.district_id}">${item.district_name}</option>`;

                $("#districtUp").append(str)
            })
        }
    }).fail((jqXHR) => {
        console.log(jqXHR);
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

            $("#provinceUp").html("")
            $.each(data.results, (i, item) => {
                let str = `<option value="${item.province_id}">${item.province_name}</option>`;

                $("#provinceUp").append(str);
            })
        })
        .fail((jqxHR) => {
            console.log(jqxHR);
        })
}

const updateLocationRegion = () => {
    $("#modalUpdateUser").modal("show")
}

$("#productSearchOutSide").on("blur", function () {
    setTimeout(function () {
        $("#listSearchProduct tbody").addClass("d-none");

    }, 200)
})

$("#productSearchOutSide").on("input", function () {
    let search = $("#productSearchOutSide").val()
    $.ajax({
        "method": "GET",
        "url": page.url.GetResuiltSearch + "/" + search,
    }).done((data) => {
        $("#listSearchProduct tbody").html("")
        $("#listSearchProduct tbody").removeClass("d-none")
        $("#listSearchProduct tbody").css({
            'position': 'absolute',
            'z-index': '1',
            'background': '#ebebeb',
            'width': '46%',
            'padding': '10px 20px 10px 20px'
        })
        if (data.length === 0) {
            $("#listSearchProduct tbody").append(`<h3>Không tìm thấy sản phẩm</h3>`)
        }
        $.each(data, (i, item) => {
            tag = item;
            product = tag.product
            if (i > 3) return;
            let str = `
              <tr id="tr_${product.id}" style="cursor: pointer">
                <td  class="text-center align-middle"><img width="50px" height="50px" src="${tag.product.image}" alt=""></td>
                <td>
                    <div class="row"><span style="font-size: 15px;
                                                  font-weight: 500;">
                                    ${tag.product.title}</span></div>
                    <div class="row"><span style="  color: #0000007d;
                                                    font-size: 12px;
                                                    padding: 5px 0px 0px 0px;">${tag.product.code}</span></div>
                </td >
                <td  class="text-center align-middle">
                <div class="row"><span style="font-weight: 500;
                                                font-size: 14px;"> ${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(tag.product.price)}</span></div>
                  <div class="row" style="color: #0000007d;">Tồn: <span style="color: blue;
                                                                               font-weight: 500;"> ${tag.product.quantity} </span> | có thể bán <span style="color: blue;
                                                                                                                                                            font-weight: 500;"> ${tag.product.quantity} </span></div>
                  </td>
            </tr>
                `
            $("#listSearchProduct tbody").append(str)

        });
        choiceProduct()
    })
})

page.commands.SelectedCustomer = () => {
    $(".AllCustomerInfor").on("change", function () {
        page.commands.getCustomerInfoById($(".AllCustomerInfor").val()).then(() => {
            $(".SelectOneCustomer").html("")
            $('#productSearchOutSide').prop('readonly', false);
            $('#productSearchOutSide').removeProp('readonly');
            let str = `
                <a href="/api/customerInfo/edit/${customerInfo.id}"> 
                    <span style="    color: #0088FF;
                                    font-size: 1.125rem;
                                    font-weight: bold;">${customerInfo.fullName}  </span> 
                    <span style="font-weight: bold; color: black;
                                    font-size: 1.125rem;"> - ${customerInfo.phone}</span>
                </a>
                <a style="color: black; font-size: 1.125rem" href="#" onclick="page.commands.removeCustomerSelected()"><i class="fa fa-times-circle"></i></a>   
            `
            $(".SelectOneCustomer").append(str);

            $(".AddressDelivery").html("")
            let str1 = `
            <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ giao hàng <span> <a href="#" onclick="updateLocationRegion()">Thay đổi</a></span></p>
            <p >${customerInfo.phone}</p>
            <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            `
            $(".AddressDelivery").append(str1);
            $(".billingAddress").html("")
            let str2 = `
            <div class="col-6">
                <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ Nhận hóa đơn</p>
                <p >${customerInfo.phone}</p>
                <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            </div>
            <div class="col-6">
                <p style="border: 1px dashed #00000061;
                          margin: 40px 0px 40px 0px;
                          padding: 20px 20px 20px 20px;
                          text-align: center;">Tổng chi tiêu: <span style="color: red; font-weight: bold;">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(customerInfo.debt)}</span>
                </p>
            </div>
            <input type="hidden" id="idCustomer" value="${customerInfo.id}">
            <input type="hidden" id="userCustomer" value="${customerInfo.userName}">
            `
            $(".billingAddress").append(str2)
            $("#provinceUp").val(customerInfo.locationRegion.provinceId)
            $("#phoneUp").val(customerInfo.phone)
            $("#addressUp").val(customerInfo.locationRegion.address)
            page.dialogs.commands.drawDistricts($("#provinceUp").val()).then(() => {
                $("#districtId").val(customerInfo.locationRegion.districtId);

            })
            handleUpdateCustomer(customerInfo);
            getAllCartItem(customerInfo.userName)
        })
    })
}

page.commands.removeCustomerSelected = () => {
    $(".SelectOneCustomer").html("")
    let str = `
    <select class="form-control AllCustomerInfor"></select>
    `
    $(".SelectOneCustomer").append(str);
    $('#productSearchOutSide').prop('readonly', true);
    $('#productSearchOutSide').removeProp('readonly');
    $(".AddressDelivery").html("")
    $(".AddressDelivery").append(`<div class="no-data-notice"><img  width="270px " src="https://deo.shopeemobile.com/shopee/shopee-pcmall-live-sg//assets/a60759ad1dabe909c46a817ecbf71878.png" alt=""></div>`)
    $(".billingAddress").html("")
    getAllCartItem()
    page.commands.drawCustomer()
}

const handleUpdateCustomer = (customerInfo) => {
    $("#btnUpdateUser").on("click", () => {
        customerInfo.phone = $("#phoneUp").val();
        customerInfo.locationRegion.address = $("#addressUp").val();
        customerInfo.locationRegion.provinceName = $("#provinceUp :selected").text();
        customerInfo.locationRegion.provinceId = $("#provinceUp").val();
        customerInfo.locationRegion.districtName = $("#districtUp :selected").text();
        customerInfo.locationRegion.districtId = $("#districtUp").val();
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "PUT",
            "url": page.url.PutCustomer,
            "data": JSON.stringify(customerInfo)
        }).done((data) => {
            console.log(data)
            App.IziToast.showSuccessAlert("Bạn đã đổi thông tin thành công!")
            $("#modalUpdateUser").modal("hide");
            customerInfo = data;
            $(".SelectOneCustomer").html("")
            let str = `
                <a href="/api/customerInfo/edit/${customerInfo.id}"> 
                    <span style="    color: #0088FF;
                                    font-size: 1.125rem;
                                    font-weight: bold;">${customerInfo.fullName}  </span> 
                    <span style="font-weight: bold; color: black;
                                    font-size: 1.125rem;"> - ${customerInfo.phone}</span>
                </a>
                <a style="color: black; font-size: 1.125rem" href="#" onclick="page.commands.removeCustomerSelected()"><i class="fa fa-times-circle"></i></a>   
            `
            $(".SelectOneCustomer").append(str);

            $(".AddressDelivery").html("")
            let str1 = `
            <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ giao hàng <span> <a href="#" onclick="updateLocationRegion()">Thay đổi</a></span></p>
            <p >${customerInfo.phone}</p>
            <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            `
            $(".AddressDelivery").append(str1);
            $(".billingAddress").html("")
            let str2 = `
            <div class="col-6">
                <p style="font-size: 1.125rem;  font-weight: bold;">Địa chỉ Nhận hóa đơn</p>
                <p >${customerInfo.phone}</p>
                <p ><span>${customerInfo.locationRegion.address}</span><span>, ${customerInfo.locationRegion.districtName}</span><span>, ${customerInfo.locationRegion.provinceName}</span></p>
            </div>
            <div class="col-6">
                <p style="border: 1px dashed #00000061;
                          margin: 40px 0px 40px 0px;
                          padding: 20px 20px 20px 20px;
                          text-align: center;">tổng chi tiêu: <span style="color: red; font-weight: bold;">${new Intl.NumberFormat('vi-VN', {
                style: 'currency',
                currency: 'VND'
            }).format(customerInfo.debt)}</span>
                </p>
            </div>
           
            `
            $(".billingAddress").append(str2)

        }).fail((e) => {
            console.log(e);
            App.IziToast.showErrorAlert("Bạn đã đổi thông tin Thất bại!")
        })
    })
}

const choiceProduct = () => {
    $("#listSearchProduct tbody tr").on('click', function () {
        let productId = $(this).attr('id').replace('tr_', '');
        page.commands.getProductById(productId).then(function () {
            page.commands.getCustomerInfoById($("#idCustomer").val()).then(function () {
                delete cartItems.id;
                cartItems.product = product;
                cartItems.price = product.price;
                cartItems.quantity = 1;
                cartItems.grandTotal = product.price;
                cartItems.userName = customerInfo.userName;
                $.ajax({
                    "headers": {
                        "accept": "application/json",
                        "content-type": "application/json"
                    },
                    "type": "POST",
                    "url": page.url.PostCartItem,
                    "data": JSON.stringify(cartItems)
                }).done((data) => {
                    console.log(data)
                    $("#listSearchProduct tbody").addClass("d-none");
                    console.log(data.userName)
                    $("#productSearchOutSide").val("")
                    $(".btn-create-Order").off();
                    getAllCartItem(data.userName)

                }).fail((e) => {
                    console.log(e)
                })
            })
        })
    })

}

const deleteCartItems = (id) => {
    Swal.fire({
        title: 'Bạn có muốn xóa sản phẩm này không?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Đúng, Tôi muốn xóa sản phẩm này!'
    }).then((resuilt) => {
        if (resuilt.isConfirmed) {
            $.ajax({
                "method": "DELETE",
                "url": page.url.DeleteCartItem + "/" + id
            }).done((data) => {
                getAllCartItem($("#userCustomer").val())
                App.IziToast.showSuccessAlert("Bạn đã xóa thành công!")
            })
        }
    })
}

const handleChangeInputItem = (id) => {
    getCartItemById(id).then(() => {
        if ($("#inputQuantity_" + cartItems.id).val() <= 0) {
            App.IziToast.showErrorAlert("số lượng sản phẩm phải lớn hơn 0!")
            $("#inputQuantity_" + cartItems.id).val(cartItems.quantity)
            return
        }
        let pattern = /\d/;
        if (pattern.test($("#inputQuantity_" + cartItems.id).val()) === false) {
            App.IziToast.showErrorAlert("đây không phải số")
            $("#inputQuantity_" + cartItems.id).val(cartItems.quantity)
            return;
        }
        cartItems.quantity = $("#inputQuantity_" + cartItems.id).val();
        cartItems.grandTotal = Number(cartItems.price) * Number(cartItems.quantity);
        $.ajax({
            "headers": {
                "accept": "application/json",
                "content-type": "application/json"
            },
            "type": "PUT",
            "url": page.url.PutCartItem + "/input-change",
            "data": JSON.stringify(cartItems)
        }).done((data) => {
            getAllCartItem($("#userCustomer").val())
            App.IziToast.showSuccessAlert("Thay đổi giá tiên thành công")
        }).fail((e) => {
            console.log(e)
        })
    })
}

const handleCreateOrder = () => {
    $(".btn-create-Order").on("click", function () {
        getCartByCustomerId($("#idCustomer").val()).then(() => {
            delete order.id;
            order.description = $("#description_order").val();
            order.grandTotal = 0;
            order.quantity = 0;
            order.productCode = "null";
            order.productImage = "null";
            order.productTitle = "null";
            order.customerInfo = cart.customerInfo;
            order.statusOrder = "Đang giao hàng";
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
                App.IziToast.showSuccessAlert("Bạn đã tạo đơn hàng thành công")
                $(".btn-create-Order").off();
                getAllCartItem($("#userCustomer").val())
            }).fail((e) => {
                console.log(e)
            })
        })
    })
}

page.initializeControlEvent = () => {
    page.commands.drawCustomer();
    page.dialogs.commands.drawProvinces()
    $("#provinceUp").on('change', function () {
        page.dialogs.commands.drawDistricts($("#provinceUp").val())
    })
    $("#listCartitems thead").addClass("d-none")
    $(".btnCreateOrder").addClass("d-none")
}

$(() => {
    page.initializeControlEvent()
})
let page = {
    url: {
        GetProduct: App.BASE_URL_PRODUCT,
        GetCategory: App.BASE_URL_CATEGORY,
        GetProductColor: App.BASE_URL_PRODUCTCOLOR,
        GetTag: App.BASE_URL_TAG,
        GetProductMedia: App.BASE_URL_PRODUCT + "/product-media",
        DeleteProduct: App.BASE_URL_PRODUCT + "/delete-soft-product/",
        PutProduct: App.BASE_URL_PRODUCT + "/put",
        PutTag: App.BASE_URL_TAG + "/id"
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

let productColor = new ProductColor();
let productMedia = new ProductMedia()
let category = new Category();
let product = new Product();
let tag = new Tag();
let images = [];
let formData;
images = []

page.element.idProduct = $("#idProduct")
page.element.titleProductUp = $("#titleProductUp")
page.element.codeProductUp = $("#codeProductUp")
page.element.quantityProduct = $("#quantityProductUp")
page.element.priceProductUp = $("#priceProductUp")
page.element.sizeUp = $("#sizeUp")
page.element.tagUp = $("#tagUp")
page.element.productStatus = $("#productStatus")
page.element.categoryUp = $("#categoryUp")
page.element.productColorUp = $("#productColorUp")
page.element.descriptionProduct = $("#descriptionProduct")
page.element.materialUp = $("#materialUp")
page.element.render_update_finish_row1 = $("#render_update_finish_row1")
page.element.slugProduct = $("#slugUP")
page.element.frmUpdateUser = $("#frmUpdateUser")

page.element.pareData = $(".information_clear")
page.element.btnDelete = $(".btn-delete")
page.element.btnUpdateShow = $(".btn-update-show")
page.element.btnSave = $("#btnUpdateProduct")


let row1_render = jQuery.validator.format($.trim(page.element.render_update_finish_row1.val().toString()));

page.commands.addRowLine1 = () => {
    page.element.pareData.append($(row1_render(product.title, product.image, product.code, product.category.name, product.quantity, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(product.price), product.size, product.productColor.color, product.material, product.status, product.tag, product.description)));
}

page.commands.formatNumber = () => {

    $(document).on('input', '#priceProductUp', function (e) {
        $(this).val(App.formatNumberSpace($(this).val()));
    })
    $('input#priceProductUp').on("keypress", function (e) {
        let charCode = (e.which) ? e.which : e.keyCode

        if (String.fromCharCode(charCode).match(/[^0-9]/g))
            return false;
    });
}

page.dialogs.loadData.drawListCategory = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetCategory
    }).done((data) => {
        page.element.categoryUp.html('');
        $.each(data, (i, item) => {
            let str = `
                            <option value="${item.id}">${item.name}</option>
                           `;
            page.element.categoryUp.append(str);
        })
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}

page.dialogs.loadData.drawListProductColor = () => {
    $.ajax({
        "type": "GET",
        "url": page.url.GetProductColor
    }).done((data) => {
        page.element.productColorUp.html('');
        $.each(data, (i, item) => {
            let str = `
                           <option class="form-control" value="${item.id}">${item.color}</option>
                          `;
            page.element.productColorUp.append(str);
        })
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
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

page.commands.getTagByProductId = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProduct + "/product/tag/" + id
    }).done((data) => {
        console.log(data);
        tag = data;
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Xin vui l??ng ????ng nh???p ????? th???c hi???n ch???c n??ng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.handleDeleteProduct = () => {
    page.element.btnDelete.on("click", function () {
        Swal.fire({
            title: 'B???n c?? mu???n x??a s???n ph???m  n??y kh??ng?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '????ng, X??a n??!'
        }).then((resuilt) => {
            if (resuilt.isConfirmed) {
                $.ajax({
                    type: "DELETE",
                    url: page.url.DeleteProduct + page.element.idProduct.val(),
                }).done(() => {
                    App.SweetAlert.showSuccessAlert("B???n ???? x??a s???n ph???m th??nh c??ng! B???n s??? tr??? v??? danh s??ch s???n ph???m sau 3s")

                    setTimeout(function () {
                        window.location.href = "/product-dashboard";
                    }, 3000);
                }).fail(() => {
                    App.SweetAlert.showErrorAlert("B???n ???? x??a s???n ph???m Th???t b???i!")
                })
            }
        })
    })
}

page.commands.handleShowProductUpdate = () => {
    console.log("handleShowProductUpdate")
    page.element.btnUpdateShow.on("click", function () {
        console.log('click')
        page.commands.getProductById(page.element.idProduct.val()).then(() => {
            $("#modalupdate-product").modal("show");
            page.element.titleProductUp.val(product.title)
            page.element.codeProductUp.val(product.code)
            page.element.quantityProduct.val(product.quantity)
            page.element.priceProductUp.val(product.price)
            page.element.sizeUp.val(product.size)
            page.element.materialUp.val(product.material)
            page.element.productStatus.val(product.status)
            page.element.categoryUp.val(product.category.id)
            page.element.productColorUp.val(product.productColor.id)
            page.element.descriptionProduct.val(product.description)
            page.element.slugProduct.val(product.slug)
            page.commands.getTagByProductId(product.id).then(() => {
                page.element.tagUp.val(tag.name);
            })
        })
    })
}

page.commands.handleUpdateProduct = () => {
    page.element.btnSave.on('click', function () {
        page.element.frmUpdateUser.submit()
    })
}

page.commands.afterValidationUpdateProduct = () => {
    formData.append("id", page.element.idProduct.val());
    formData.append("code", page.element.codeProductUp.val());
    formData.append("title", page.element.titleProductUp.val());
    formData.append("price", page.element.priceProductUp.val());
    formData.append("quantity", page.element.quantityProduct.val());
    formData.append("status", $("#productStatus :selected").text());
    formData.append("description", page.element.descriptionProduct.val());
    formData.append("slug", page.element.slugProduct.val());
    formData.append("size", page.element.sizeUp.val());
    formData.append("material", page.element.materialUp.val());
    formData.append("image", "null");
    $(".temploadding").html("")
    let str = `
           <div class="loading">Loading&#8230;</div>

            `
    $(".temploadding").append(str)
    $("#modalupdate-product").modal("hide");
    $.ajax({
        type: "PUT",
        contentType: false,
        cache: false,
        processData: false,
        url: page.url.PutProduct + "/" + page.element.categoryUp.val() + "/" + page.element.productColorUp.val() + "/" + page.element.tagUp.val(),
        data: formData
    }).done((data) => {
        $(".temploadding").html("")
        formData.delete("id")
        formData.delete("code");
        formData.delete("title");
        formData.delete("price");
        formData.delete("quantity");
        formData.delete("status");
        formData.delete("description");
        formData.delete("slug");
        formData.delete("size");
        formData.delete("material");
        formData.delete("image");
        page.element.btnSave.off()
        page.element.priceProductUp.val("")
        page.element.titleProductUp.val("")
        page.element.quantityProduct.val("")
        page.element.codeProductUp.val("")
        page.element.sizeUp.val("")
        page.element.categoryUp.val(1)
        page.element.productColorUp.val(1)
        page.element.productStatus.val("")
        page.element.materialUp.val("")
        page.element.descriptionProduct.val("")
        page.element.tagUp.val("")
        formData.delete("files")
        App.SweetAlert.showSuccessAlert("B???n ???? C???p nh???t S???n Ph???m Th??nh c??ng")
        page.commands.reloadPageFinishUpdate(product.id);
        images = []
        document.getElementById('container_').innerHTML = page.commands.image_show()
    }).fail(() => {
        $(".temploadding").html("")
        App.IziToast.showErrorAlert("B???n ???? t???o s???n ph???m th???t b???i")
        formData.delete("files")
        formData.delete("code");
        formData.delete("title");
        formData.delete("price");
        formData.delete("quantity");
        formData.delete("status");
        formData.delete("description");
        formData.delete("slug");
        formData.delete("size");
        formData.delete("material");
        formData.delete("image");
        $("#modalupdate-product").modal("show");
    })
}

page.commands.image_select = () => {
    page.element.btnSave.off()
    images = []
    formData = new FormData()
    let fileInput = $("#image")[0].files;
    if (fileInput.length > 4) {
        App.IziToast.showErrorAlert("ch??? ???????c upload t???i ??a 4 t???m h??nh")
        return
    }
    for (let k = 0; k < fileInput.length; k++) {
        formData.append("files", fileInput[k]);
    }
    for (let i = 0; i < fileInput.length; i++) {
        images.push({
            "name": fileInput[i].name,
            "url": URL.createObjectURL(fileInput[i]),
            "file": fileInput[i],
        });
    }
    page.commands.handleUpdateProduct();
    document.getElementById('container_').innerHTML = page.commands.image_show()
}

page.commands.image_show = () => {
    var image = "";
    images.forEach((i) => {
        image += `
                       <div class="col-3 ">
                           <div class="image_container justify-content-center position-relative">
                            <img style="height: 110px;width: 150px;"  src="` + i.url + `"  alt="" />
                             <span class="position-absolute" onclick="page.commands.delete_image(` + images.indexOf(i) + `)">&times;</span>
                          </div>
                      </div>
                    `;
    })
    return image;
}

page.commands.delete_image = (e) => {
    images.splice(e, 1)
    let arrFile = formData.getAll("files")
    arrFile.splice(e, 1)
    formData.delete("files")
    for (let i = 0; i < arrFile.length; i++) {
        formData.append("files", arrFile[i])
    }
    document.getElementById("container_").innerHTML = page.commands.image_show();
}

page.commands.reloadPageFinishUpdate = (productId) => {
    page.commands.getProductById(productId).then(() => {
        page.commands.getTagByProductId(page.element.idProduct.val()).then(() => {
            page.element.pareData.html("")
            product.tag = tag.name
            page.commands.addRowLine1();
            page.commands.getProductMediaByProduct(page.element.idProduct.val()).then(() => {
                $(".imageUpdate").html("")
                $.each(productMedia, (i, item) => {
                    let str2 = `
                                    <div class="col-3 text-center align-middle " >
                                        <img width="100px" style="padding-top: 15px;"
                                             src="${item.fileUrl}" alt="">
                                    </div>
                        `
                    $(".imageUpdate").append(str2);
                })
                page.commands.handleDeleteProduct();
                page.commands.handleShowProductUpdate();
            })
        })
    })
}

page.commands.getProductMediaByProduct = (id) => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProductMedia + "/" + id
    }).done((data) => {
        console.log(data);
        productMedia = data;
    }).fail((e) => {
        console.log(e)
        App.IziToast.showErrorAlert("Xin vui l??ng ????ng nh???p ????? th???c hi???n ch???c n??ng")
        setTimeout(function () {
            window.location.href = "/login"
        }, 500);
    })
}

page.commands.ChangeToSlug = () => {

    var title, slug;

    //L???y text t??? th??? input title
    title = page.element.titleProductUp.val();

    //?????i ch??? hoa th??nh ch??? th?????ng
    slug = title.toLowerCase();

    //?????i k?? t??? c?? d???u th??nh kh??ng d???u
    slug = slug.replace(/??|??|???|???|??|??|???|???|???|???|???|??|???|???|???|???|???/gi, 'a');
    slug = slug.replace(/??|??|???|???|???|??|???|???|???|???|???/gi, 'e');
    slug = slug.replace(/i|??|??|???|??|???/gi, 'i');
    slug = slug.replace(/??|??|???|??|???|??|???|???|???|???|???|??|???|???|???|???|???/gi, 'o');
    slug = slug.replace(/??|??|???|??|???|??|???|???|???|???|???/gi, 'u');
    slug = slug.replace(/??|???|???|???|???/gi, 'y');
    slug = slug.replace(/??/gi, 'd');
    //X??a c??c k?? t??? ?????t bi???t
    slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
    //?????i kho???ng tr???ng th??nh k?? t??? g???ch ngang
    slug = slug.replace(/ /gi, "-");
    //?????i nhi???u k?? t??? g???ch ngang li??n ti???p th??nh 1 k?? t??? g???ch ngang
    //Ph??ng tr?????ng h???p ng?????i nh???p v??o qu?? nhi???u k?? t??? tr???ng
    slug = slug.replace(/\-\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-/gi, '-');
    slug = slug.replace(/\-\-/gi, '-');
    //X??a c??c k?? t??? g???ch ngang ??? ?????u v?? cu???i
    slug = '@' + slug + '@';
    slug = slug.replace(/\@\-|\-\@|\@/gi, '');
    //In slug ra textbox c?? id ???slug???
    page.element.slugProduct.val(slug)
}

page.element.frmUpdateUser.validate({
    "rules": {
        "titleProductUp": {
            required: true,
            minlength: 5,
        },
        "quantityProductUp": {
            required: true,
            number: true,
            maxlength: 4,
        },
        "priceProductUp": {
            required: true,
            // number: true,
            // max: 1000000000,
            // min: 50000,
        },
        "LetterUp": {
            required: true,
            minlength: 2,
        },

        "codeProductUp": {
            required: true,
            number: true,
            minlength: 8,
        },
        "sizeUp": {
            required: true,
            minlength: 6,
        },
        "materialUp": {
            required: true,
            minlength: 6,
        },
        "tagUp": {
            required: true,
            minlength: 6,
        },
        "descriptionProduct": {
            required: true,
            minlength: 6,
        },
    },
    "messages": {
        "titleProductUp": {
            required: "Vui L??ng Nh???p Title s???n ph???m!",
            minlength: $.validator.format(" Title s???n ph???m t???i thi???u {0} k?? t???!"),
        },
        "quantityProductUp": {
            required: "Vui L??ng Nh???p s??? l?????ng s???n ph???m!",
            maxlength: $.validator.format(" s??? l?????ng s???n ph???m t???i ??a {0} s???n ph???m!"),
        },
        "priceProductUp": {
            required: "Vui L??ng Nh???p gi?? ti???n s???n ph???m!",
            // number: true,
            // max: $.validator.format(" Gi?? ti???n s???n ph???m t???i ??a {0} vn??"),
            // min: $.validator.format(" Gi?? ti???n s???n ph???m t???i thi???u {0} vn??"),
        },
        "LetterUp": {
            required: "Vui L??ng Nh???p K?? t??? ?????u SKU (Y??u c???u ch??? hoa)!",
            minlength: 2,
        },

        "codeProductUp": {
            required: "Vui L??ng Nh???p K?? t??? sau SKU (Y??u c???u ch??? s???)!",
            minlength: $.validator.format("Sau m?? SKU l?? 8 ch??? s???"),
        },
        "sizeUp": {
            required: "Vui L??ng Nh???p k??ch th?????c s???n ph???m!",
            minlength: $.validator.format("t???i thi???u {0} k?? t???"),
        },
        "materialUp": {
            required: "Vui L??ng Nh???p V???t li???u s???n ph???m",
            minlength: $.validator.format("t???i thi???u {0} k?? t???"),

        },
        "tagUp": {
            required: "Vui L??ng Nh???p Tag s???n ph???m",
            minlength: $.validator.format("t???i thi???u {0} k?? t???"),
        },
        "descriptionProduct": {
            required: "Vui L??ng Nh???p M?? t??? s???n ph???m",
            minlength: $.validator.format("t???i thi???u {0} k?? t???"),
        },
    },
    errorLabelContainer: "frmUpdateUser .input.error",
    submitHandler: function () {
        page.commands.afterValidationUpdateProduct();
    }
})

page.initializeControlEvent = () =>{
    page.commands.handleDeleteProduct();
    page.commands.handleShowProductUpdate();
    page.commands.formatNumber();
    page.dialogs.loadData.drawListCategory();
    page.dialogs.loadData.drawListProductColor();
}

$(() =>{
    page.initializeControlEvent();
})
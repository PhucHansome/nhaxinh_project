let page = {
    url: {
        CreateProduct: App.BASE_URL_PRODUCT,
        GetProduct: App.BASE_URL_PRODUCT,
        GetCategory: App.BASE_URL_CATEGORY,
        GetProductColor: App.BASE_URL_PRODUCTCOLOR,
        PostProduct: App.BASE_URL_PRODUCT,
        PostTag: App.BASE_URL_TAG + "/id"
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
let category = new Category();
let product = new Product();
let tag = new Tag();
let images = [];
let formData;

page.element.priceProduct = $("#priceProduct")
page.element.productName = $("#productName")
page.element.quantityProduct = $("#quantityProduct")
page.element.codeProduct = $("#codeProduct")
page.element.productSize = $("#productSize")
page.element.category = $("#category_")
page.element.categoryText = $("#category_ :selected")
page.element.colorProduct = $("#colorProduct")
page.element.colorProductText = $("#colorProduct :selected")
page.element.productStatus = $("#productStatus")
page.element.materialProduct = $("#materialProduct")
page.element.descriptionProduct = $("#descriptionProduct")
page.element.nameImage = $("#name_image")
page.element.tag = $("#TagProduct")
page.element.slugProduct = $("#slugProduct")
page.element.LetterUpCase = $("#LetterUp")

page.element.btnSave = $(".btn-Save")

page.element.listProductColor = $(".productColorList")
page.element.ErrorCreate = $(".ErrorCreate")
page.element.frmCreateProduct = $("#frmCreateProduct")

page.commands.formatNumber = () => {

    $(document).on('input', '#priceProduct', function (e) {
        $(this).val(App.formatNumberSpace($(this).val()));
    })
    $('input#priceProduct').on("keypress", function (e) {
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
        page.element.category.html('');
        $.each(data, (i, item) => {
            let str = `
                            <option value="${item.id}">${item.name}</option>
                           `;
            page.element.category.append(str);
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
        page.element.listProductColor.html('');
        $.each(data, (i, item) => {
            let str = `
                           <option class="form-control" value="${item.id}">${item.color}</option>
                          `;
            page.element.listProductColor.append(str);
        })
    }).fail((jqXHR) => {
        console.log(jqXHR);
    })
}

page.commands.handleCreateProduct = () => {
    page.element.btnSave.on('click', function () {
        page.element.frmCreateProduct.submit();
    })
}

page.commands.afterValidationCreateProduct = () => {
    formData.append("code", page.element.LetterUpCase.val() + "-31*" + page.element.codeProduct.val());
    formData.append("title", page.element.productName.val());
    formData.append("price", page.element.priceProduct.val());
    formData.append("quantity", page.element.quantityProduct.val());
    formData.append("status", "Đang chờ");
    formData.append("description", page.element.descriptionProduct.val());
    formData.append("slug", page.element.slugProduct.val());
    formData.append("size", page.element.productSize.val());
    formData.append("material", page.element.materialProduct.val());
    formData.append("image", "null");
    let str = `
              <div class="loading">Loading&#8230;</div>
            `
    $(".mla").html(str)
    $.ajax({
        type: "POST",
        contentType: false,
        cache: false,
        processData: false,
        url: page.url.PostProduct + "/" + page.element.category.val() + "/" + page.element.colorProduct.val() + "/" + page.element.tag.val(),
        data: formData
    }).done((data) => {
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
        $(".ErrorCreate").addClass("d-none")
        $(".mla").html("")
        page.element.btnSave.off()
        page.element.priceProduct.val("")
        page.element.productName.val("")
        page.element.quantityProduct.val("")
        page.element.codeProduct.val("")
        page.element.productSize.val("")
        page.element.LetterUpCase.val("")
        page.element.category.val(1)
        page.element.colorProduct.val(1)
        page.element.productStatus.val("")
        page.element.materialProduct.val("")
        page.element.descriptionProduct.val("")
        page.element.tag.val("")
        formData.delete("files")
        App.IziToast.showSuccessAlert("Bạn Đã Tạo Sản Phẩm Thành công")
        images = []
        document.getElementById('container_').innerHTML = page.commands.image_show()
    }).fail((jqXHR) => {
        $(".mla").html("")
        App.IziToast.showErrorAlert("Bạn đã tạo sản phẩm thất bại")
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
        $(".ErrorCreate").removeClass("d-none")
        $(".ErrorCreate").html("")
        if (jqXHR.responseJSON) {
            console.log(jqXHR)
            $.each(jqXHR.responseJSON, (key, item) => {

                let str = `<ul>
                                    <li>${item}</li>
                                    </ul>
                                `
                $(".ErrorCreate").append(str)
            })
        }
    })
}

page.commands.image_select = () => {
    images = []
    // page.element.btnSave.off()
    formData = new FormData()
    let fileInput = $("#image")[0].files;
    if (fileInput.length > 4) {
        App.IziToast.showErrorAlert("chỉ được upload tối đa 4 tấm hình")
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
    page.commands.handleCreateProduct();
    document.getElementById('container_').innerHTML = page.commands.image_show()
}

page.commands.image_show = () => {
    var image = "";
    images.forEach((i) => {
        image += `
                       <div class="col-3 " style="margin-right: 10px" >
                           <div class="image_container d-flex justify-content-center position-relative">
                            <img style="height: 110px;width: 150px;"  src="` + i.url + `"  alt="" />
                             <span class="position-absolute" onclick="page.commands.delete_image(` + images.indexOf(i) + `)"><i class="fas fa-times"></i></span>
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

page.commands.ChangeToSlug = () => {

    var title, slug;

    //Lấy text từ thẻ input title
    title = page.element.productName.val();

    //Đổi chữ hoa thành chữ thường
    slug = title.toLowerCase();

    //Đổi ký tự có dấu thành không dấu
    slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, 'a');
    slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, 'e');
    slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, 'i');
    slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, 'o');
    slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, 'u');
    slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, 'y');
    slug = slug.replace(/đ/gi, 'd');
    //Xóa các ký tự đặt biệt
    slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
    //Đổi khoảng trắng thành ký tự gạch ngang
    slug = slug.replace(/ /gi, "-");
    //Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
    //Phòng trường hợp người nhập vào quá nhiều ký tự trắng
    slug = slug.replace(/\-\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-/gi, '-');
    slug = slug.replace(/\-\-/gi, '-');
    //Xóa các ký tự gạch ngang ở đầu và cuối
    slug = '@' + slug + '@';
    slug = slug.replace(/\@\-|\-\@|\@/gi, '');
    //In slug ra textbox có id “slug”
    page.element.slugProduct.val(slug)
}

page.element.frmCreateProduct.validate({
    "rules": {
        "productName": {
            required: true,
            minlength: 5,
        },
        "quantityProduct": {
            required: true,
            number: true,
            maxlength: 4,
        },
        "priceProduct": {
            required: true,
            number: true,
            max: 1000000000,
            min: 50000,
        },
        "LetterUp": {
            required: true,
            minlength: 2,
        },

        "codeProduct": {
            required: true,
            number: true,
            minlength: 8,
        },
        "productSize": {
            required: true,
            minlength: 6,
        },
        "materialProduct": {
            required: true,
            minlength: 6,
        },
        "TagProduct": {
            required: true,
            minlength: 6,
        },
        "descriptionProduct": {
            required: true,
            minlength: 6,
        },
    },
    "messages": {
        "productName": {
            required: "Vui Lòng Nhập Title sản phẩm!",
            minlength: $.validator.format(" Title sản phẩm tối thiểu {0} ký tự!"),
        },
        "quantityProduct": {
            required: "Vui Lòng Nhập số lượng sản phẩm!",
            maxlength: $.validator.format(" số lượng sản phẩm tối đa {0} sản phẩm!"),
        },
        "priceProduct": {
            required: "Vui Lòng Nhập giá tiền sản phẩm!",
            number: true,
            max: $.validator.format(" Giá tiền sản phẩm tối đa {0} vnđ"),
            min: $.validator.format(" Giá tiền sản phẩm tối thiểu {0} vnđ"),
        },
        "LetterUp": {
            required: "Vui Lòng Nhập Ký tự đầu SKU (Yêu cầu chữ hoa)!",
            minlength: 2,
        },

        "codeProduct": {
            required: "Vui Lòng Nhập Ký tự sau SKU (Yêu cầu chữ số)!",
            minlength: $.validator.format("Sau mã SKU là 8 chữ số"),
        },
        "productSize": {
            required: "Vui Lòng Nhập kích thước sản phẩm!",
            minlength: $.validator.format("tối thiểu {0} kí tự"),
        },
        "materialProduct": {
            required: "Vui Lòng Nhập Vật liệu sản phẩm",
            minlength: $.validator.format("tối thiểu {0} kí tự"),

        },
        "TagProduct": {
            required: "Vui Lòng Nhập Tag sản phẩm",
            minlength: $.validator.format("tối thiểu {0} kí tự"),
        },
        "descriptionProduct": {
            required: "Vui Lòng Nhập Mô tả sản phẩm",
            minlength: $.validator.format("tối thiểu {0} kí tự"),
        },
    },
    errorLabelContainer: "#frmCreateProduct .input.error",
    submitHandler: function () {
        page.commands.afterValidationCreateProduct();
    }
})

page.initializeControlEvent = () => {
    page.dialogs.loadData.drawListCategory();
    page.dialogs.loadData.drawListProductColor();
    page.commands.formatNumber();
}

$(()=> {
    page.initializeControlEvent()
})
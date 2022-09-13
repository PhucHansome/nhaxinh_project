let page = {
    url: {
        CreateProduct: App.BASE_URL_PRODUCT,
        GetProduct: App.BASE_URL_PRODUCT

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
page.element.frmListProduct = $("#tableListProducts tbody");
page.element.tempProduct = $("#tempProduct");
page.element.btnGoCreateProduct = $(".btn-go-add-product");
page.element.btnGoCreateCategory = $(".btnGoCategory");
page.element.btnGoCreateColor = $(".btnGoColor");

let product = new Product();
let category = new Category();
let productColor = new ProductColor();
let tag = new Tag();

let productId = null;

let tempProduct = jQuery.validator.format($.trim(page.element.tempProduct.val().toString()));

page.commands.addRow = () => {
    page.element.frmListProduct.append($(tempProduct(product.id, product.image, product.title, product.category.name, new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND'
    }).format(product.price), product.status, product.createdAt)));
}

page.dialogs.commands.getProductById = (id) => {
    $.ajax({
        "method": "GET",
        "url": page.url.GetProduct + "/" + id
    }).done((data) => {
        let product = data;
    })
}

page.dialogs.commands.getAllProduct = () => {
    return $.ajax({
        "method": "GET",
        "url": page.url.GetProduct
    }).done((data) => {

        console.log(data)
        $.each(data, (i, item) => {
            product = item;
            page.commands.addRow();
            console.log(data)
        });
    }).fail((e) => {
        console.log(e);
    })

}

page.dialogs.commands.goDetailProduct = (productId) => {
    window.location.href = "/detail-product-dashboard/" + productId;
}

page.dialogs.commands.goCreateProduct = () => {
    page.element.btnGoCreateProduct.on("click", function () {
        window.location.href = "/create-product-dashboard";
    })
}
page.dialogs.commands.goCreateCategory = () => {
    page.element.btnGoCreateCategory.on("click", function () {
        window.location.href = "/category-product-dashboard";
    })
}
page.dialogs.commands.goCreateColor = () => {
    page.element.btnGoCreateColor.on("click", function () {
        window.location.href = "/color-product-dashboard";
    })
}


$(function () {
    // page.dialogs.commands.getAllProduct();
    page.dialogs.commands.goCreateProduct();
    page.dialogs.commands.goCreateCategory();
    page.dialogs.commands.goCreateColor();
    // new LazyLoad({});
})

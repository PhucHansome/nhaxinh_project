    let page = {
    url: {
    CreateCategory: App.BASE_URL_CATEGORY +"/create",
    DeleteCategory: App.BASE_URL_CATEGORY + "/delete-category/"

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
    page.element.nameCategory = $("#nameCategory");
    page.element.codeCategory = $("#codeCategory");
    page.element.btncreateCategory = $("#createCategory");
    page.element.btnDeleteCategory = $(".btnDeleteCategory");
    page.element.idCategory = $("#idCategory");

    let category = new Category();

    let categoryId = null;

    page.element.btncreateCategory.on('click', () => {
    category.name = page.element.nameCategory.val();
    category.code = page.element.codeCategory.val();
    delete category.id;

    $.ajax({
    "headers": {
    "accept": "application/json",
    "content-type": "application/json"
},
    "type": "POST",
    "url": page.url.CreateCategory,
    "data": JSON.stringify(category)
}).done((data) => {
    category.name = page.element.nameCategory.val("");
    category.code = page.element.codeCategory.val("");

    Swal.fire({
    icon: 'success',
    title: "Thêm mới Category thành công",
    position: 'top-end',
    showConfirmButton: false,
    timer: 1500
})
    setTimeout(function () {
    window.location.href = "/category-product-dashboard";
}, 1000);
}).fail((jqXHR) => {
    App.IziToast.showErrorAlert("Thêm mới Category thất bại")
    console.log(jqXHR)
})
})
    page.commands.handleShowConfirmDelete = () => {
    page.element.btnDeleteCategory.on('click', function () {
        let  id = $(this).data("id");
        App.SweetAlert.showConfirmDeleteCategory()
            .then((result) => {
                if (result.isConfirmed) {

                    $.ajax({
                        "type": "DELETE",
                        url: page.url.DeleteCategory + id,
                    })
                        .done((data) => {
                            console.log(data)
                            Swal.fire({
                                icon: 'success',
                                title: "Xóa Loại sản phẩm Thành Công! Bạn sẽ trở về danh sách loại sản phẩm sau 1s",
                                position: 'top-end',
                                showConfirmButton: false,
                                timer: 1500
                            })
                            setTimeout(function () {
                                window.location.href = "/category-product-dashboard";
                            }, 1000);
                        })
                        .fail((e) => {
                            console.log(e)
                            App.IziToast.showErrorAlert('Xóa Loại sản phẩm  thất bại');
                        });
                }
            })
    })
}
    $(function () {

    page.commands.handleShowConfirmDelete();


})

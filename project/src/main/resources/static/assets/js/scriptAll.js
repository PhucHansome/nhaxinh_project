class App {

    static DOMAIN = location.origin;

    static BASE_URL_USER = this.DOMAIN + "/api/auth";
    static BASE_URL_USER_FORGOTAPASSWORD = this.DOMAIN + "/api/user";
    static BASE_URL_CUSTOMERINFO = this.DOMAIN +"/api/customerInfo";
    static BASE_URL_PROVINCE =  "https://vapi.vnappmob.com/api/province";
    static BASE_URL_PRODUCT = this.DOMAIN + "/api/products";
    static BASE_URL_CATEGORY = this.DOMAIN + "/api/category";
    static BASE_URL_COLOR = this.DOMAIN + "/api/colorProduct";
    static BASE_URL_PRODUCTCOLOR = this.DOMAIN + "/api/products/product-color"
    static BASE_URL_TAG = this.DOMAIN + "/api/tag"
    static BASE_URL_CART = this.DOMAIN + "/api/cart"
    static BASE_URL_CARTITEM = this.DOMAIN + "/api/cart-item"
    static BASE_URL_USER_ABC = this.DOMAIN + "/api/user"
    static BASE_URL_ORDER = this.DOMAIN + "/api/order"
    static BASE_URL_STATISTICS = this.DOMAIN + "/api/statistics"
    static BASE_URL_CLOUD_IMAGE = "https://res.cloudinary.com/apt-inventory/image/upload";
    static BASE_URL_CLOUD_VIDEO = "https://res.cloudinary.com/apt-inventory/video/upload";
    static BASE_SCALE_IMAGE = "c_limit,w_150,h_100,q_100";

    static SweetAlert = class {
        
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1000
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
                timer: 1000
            })
        }
        static showConfirmDeleteUser() {
            return Swal.fire({
                title: 'Bạn có muốn xoá khách hàng này?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có, Xoá ngay!',
                cancelButtonText: 'Huỷ'
            });
        }
        static showConfirmDeleteCategory() {
            return Swal.fire({
                title: 'Bạn có muốn xoá loại sản phẩm này?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có, Xoá ngay!',
                cancelButtonText: 'Huỷ'
            });
        }
        static showConfirmDeleteColor() {
            return Swal.fire({
                title: 'Bạn có muốn xoá màu sắc sản phẩm này?',
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Có, Xoá ngay!',
                cancelButtonText: 'Huỷ'
            });
        }
    }
    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                // icon: '', // icon class

                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topLeft',
                message: m,
            });
        }

        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                // icon: '', // icon class
                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topLeft',
                message: m,
            });
        }
    }

    static formatNumber() {
        $(".num-space").number(true, 0, ',', ' ');
        $(".num-point").number(true, 0, ',', '.');
        $(".num-comma").number(true, 0, ',', ',');
    }

    static formatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "").replace(/\B(?=(\d{3})+(?!\d))/g, " ");
    }

    static removeFormatNumberSpace(x) {
        if (x == null) {
            return x;
        }
        return x.toString().replace(/ /g, "")
    }

    static formatTooltip() {
        $('[data-toggle="tooltip"]').tooltip();
    }


}


class Role{
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class User {
    constructor(id ,username,password,role,status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
    }

}

class LocationRegion {
    constructor(id, provinceId, provinceName, districtId, districtName, address) {
        this.id = id;
        this.provinceId = provinceId;
        this.provinceName = provinceName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.address = address;
    }
}
class CustomerInfo {
    constructor(id,userName,fullName,phone,debt,locationRegion) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.phone = phone;
        this.debt = debt;
        this.locationRegion = locationRegion;

    }
}
class Category{
    constructor(id , name,code) {
        this.id = id;
        this.name = name ;
        this.code = code;
    }
}

class ProductColor {
    constructor(id, color) {
        this.id = id;
        this.color = color;
    }
}


class Product{
    constructor(id ,title ,code ,price ,quantity ,status ,description ,slug ,size ,material ,image ,category ,productColor ,tag ,createdAt ,fileId ,fileName ,fileFolder ,fileType ,fileUrl) {
    this.id = id;
    this.title = title;
    this.code = code
    this.price = price;
    this.quantity = quantity;
    this.status = status;
    this.description = description;
    this.slug = slug;
    this.size = size;
    this.material = material;
    this.image = image;
    this.category = category;
    this.productColor = productColor;
    this.tag = tag;
    this.createdAt = createdAt;
    this.fileId = fileId;
    this.fileName = fileName;
    this.fileFolder = fileFolder;
    this.fileType = fileType;
    this.fileUrl = fileUrl;
    }
}

class Tag {
    constructor(id, name, product) {
        this.id = id;
        this.name = name;
        this.product = product;
    }

}

class CartItems {
    constructor(id, product, price, quantity,grandTotal,userName ) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.grandTotal = grandTotal;
        this.userName = userName;
    }
}

class Cart {
    constructor(id, content, user, customerInfo) {
        this.id = id;
        this.content = content;
        this.user = user
        this.customerInfo = customerInfo
    }

}

class OrderDetail {
    constructor(id, order, statusOrderDetail,createdAt, grandTotal,fullName,userName,phone,address,districtName,provinceName ) {
        this.id = id;
        this.order = order;
        this.statusOrderDetail = statusOrderDetail;
        this.createdAt = createdAt;
        this.grandTotal = grandTotal;
        this.fullName = fullName;
        this.userName = userName;
        this.phone = phone;
        this.address = address;
        this.districtName = districtName;
        this.provinceName = provinceName;
    }
}

class Order {
    constructor(id ,description ,grandTotal ,quantity ,productCode ,productImage,productTitle ,customerInfo,createdAt, statusOrder,orderDetail ) {
        this.id = id;
        this.description = description;
        this.grandTotal = grandTotal;
        this.quantity = quantity;
        this.productCode = productCode;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.customerInfo = customerInfo;
        this.createdAt = createdAt;
        this.statusOrder = statusOrder;
        this.orderDetail = orderDetail;
    }
}

class ProductMedia{
    constructor(id, fileUrl) {
        this.id = id;
        this.fileUrl = fileUrl;
    }
}


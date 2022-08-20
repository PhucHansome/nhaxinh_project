class Appp {

        static catchIdLogin(id){
            let a = id;
            return a;
        }

    static SweetAlert = class {
        static showSuccessAlert(t) {
            Swal.fire({
                icon: 'success',
                title: t,
                position: 'top-end',
                showConfirmButton: false,
                timer: 1500
            })
        }

        static showErrorAlert(t) {
            Swal.fire({
                icon: 'error',
                title: 'Warning',
                text: t,
            })
        }
    }
    static IziToast = class  {
        static showErrorAlert(m) {
            iziToast.error({
                title: 'Error',
                // icon: '', // icon class

                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topRight',
                message: m,
            });
        }

        static showSuccessAlert(m) {
            iziToast.success({
                title: 'Success',
                // icon: '', // icon class
                transitionIn: 'flipInX',
                transitionOut: 'flip',
                position: 'topRight',
                message: m,
            });
        }
    }
}

class Role{
    constructor(id, code) {
        this.id = id;
        this.code = code;
    }
}

class User {
    constructor(id ,username,password,role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

class Category{
    constructor(id, name) {
        this.id = id;
        this.name = name;
    }
}

class Product{
    constructor(id, productCode, productName, category, quantityProduct, productDescription, priceProduct, image, createdAt,updatedAt){
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.quantityProduct = quantityProduct;
        this.productDescription = productDescription;
        this.priceProduct = priceProduct;
        this.image = image;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

class CustomerInfo{
    constructor(id ,fullName ,phone ,birthDate ,address) {
        this.id = id;
        this.fullName = fullName;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
    }

}


class Order{
    constructor(id, shippedDate,user, customerInfo, orderItem) {
        this.id = id;
        this.shippedDate = shippedDate;
        this.user = user;
        this.orderItem = orderItem;
        this.customerInfo = customerInfo;
    }

}

class OrderItem {
    constructor(id, product, quantityOrdered, priceEach, grandTotal, user) {
        this.id = id;
        this.product = product;
        this.quantityOrdered = quantityOrdered;
        this.priceEach = priceEach;
        this.grandTotal = grandTotal;
        this.user = user;
    }
}


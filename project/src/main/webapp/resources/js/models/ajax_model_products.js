var productUrl = "product/";

function getAllProduct() {
    return ajaxGet(`${productUrl}find-all`);
}
var categoryUrl = "category/";

function getAllCategory() {
    return ajaxGet(categoryUrl+"/find-all");
}

function deleteCategoryApi(data) {
    return ajaxDelete(`${categoryUrl}delete?id=${data.id}`, data);
}
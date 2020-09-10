let textTen, btnSave, selectSearchCategory, selectSearchSapXep,
    textSearchTen, tableData, listCategory, btnAdd, btnRefact, indexCategory;
    textTen = $("#text-ten");
    btnSave = $("#btn-luu-lai");
    btnConfirmDelete = $("#btn-xac-nhan-xoa");
    selectSearchCategory = $("#select-search-danh-muc");
    selectSearchSapXep = $("#select-search-sap-xep");
    textSearchTen = $("#text-search-ten");
    btnAdd = $("#btn-them");
    btnRefact = $(".btn-sua");
    listCategory = [];

$(async function() {
    tableData = $("tbody");

    await getAllCategory().then(rs => {
        listCategory = rs.message == "success" ? rs.data : [];
    }).catch(err => {
        console.log(err);
    });
    viewAllCategory();
    comfirmDelete();
})

function viewAllCategory() {
    let  view = "<tr><td colspan='3'><strong class='text-center'>Không có danh mục nào</strong></td></tr>";
    if(listCategory && listCategory.length >= 0){
        view = listCategory.map((data, index)=>{
            return `  <tr data-index = "${index}">
                            <th scope="row">${index + 1}</th>
                            <td>${viewField(data.name)}</td>
                            <td class="text-center">
                                <button type="button" class="btn btn-warning mt-1 btn-sua" ><i class="fas fa-pencil-alt"></i> Sửa</button>
                                <button type="button" class="btn btn-danger mt-1 btn-xoa" ><i class="fas fa-trash-alt"></i> Xóa</button>
                            </td>
                        </tr>`
        }).join("");
    }
    tableData.html(view);
    deleteCategory();
}

function deleteCategory() {
    $(".btn-xoa").click(function () {
        indexCategory = $(this).parents("tr").attr("data-index");
        $("#modalDelete").modal("show");
    });
}

function comfirmDelete() {
    $("#btn-xac-nhan-xoa").click(function () {
        // let categoryId = listCategory[indexCategory].id;
        deleteCategoryApi(listCategory[indexCategory]).then(rs=>{
            console.log(rs);
        }).catch(err=>{
            console.log(err);
        });
        listCategory = listCategory.filter((data, index) => {
            return index != indexCategory;
        });
        viewAllCategory();
        $("#modalDelete").modal("hide");

    });
}
let selectSearchDanhMuc, selectSearchSapXep, numberSearchGia, numberSearchDaBan,
    dateSearchNgayTao, selectSearchConHang, tableDuLieu, btnTimKiem, textSearchTen,
    textTen, selectDanhMuc, numberGia, numberDaBan, numberBaoHanh, numberKhuyenMai,
    fileAnh, dateNgayTao, textAreaGioiThieu, textAreaThongso, checkBoxHetHang, btnLuuLai,
    btnXacNhanXoa, listProduct, indexProduct, elementProduct,btnThem;


$(async function () {
    selectSearchDanhMuc = $("#select-search-danh-muc");
    selectSearchSapXep = $("#select-search-sap-xep");
    numberSearchGia = $("#number-search-gia");
    numberSearchDaBan = $("#number-search-da-ban");
    dateSearchNgayTao = $("#date-search-ngay-tao");
    selectSearchConHang = $("#select-search-con-hang");
    tableDuLieu = $("tbody");
    btnTimKiem = $("#btn-tim-kiem");
    textSearchTen = $("#text-search-ten");
    textTen = $("#text-ten");
    selectDanhMuc = $("#select-danh-muc");
    numberGia = $("#number-gia");
    numberDaBan = $("#number-da-ban");
    numberBaoHanh = $("#number-bao-hanh");
    numberKhuyenMai = $("#number-khuyen-mai");
    fileAnh = $("#file-anh");
    dateNgayTao = $("#date-ngay-tao");
    textAreaGioiThieu = $("#textarea-gioi-thieu");
    textAreaThongso = $("#textarea-thong-so");
    checkBoxHetHang = $("#checkbox-het-hang");
    btnLuuLai = $("#btn-luu-lai");
    btnXacNhanXoa = $("#btn-xac-nhan-xoa");
    btnThem = $("#btn-them");
    listProduct = [];

    await getAllProduct().then(rs =>{
        listProduct = rs.message == "success" ? rs.data : [];
    }).catch(err =>{
        console.log(err);
    })
    viewAllProduct();
    addProduct();
})

function viewAllProduct() {
    let  view = "<tr><td colspan='8'><strong>Không có sản phẩm nào</strong></td></tr>";
    if(listProduct && listProduct.length > 0){
        view = listProduct.map((data,index) => {
            return ` <tr data-index="${index}">
                            <th scope="row">${index + 1}</th>
                            <td><img src="${data.image}"
                                     alt="" width="80px"></td>
                            <td>${viewField(data.name)}</td>
                            <td>${viewField(data.price)}</td>
                            <td>${viewField(data.bought)}</td>
                            <td>${viewField(data.createDate)}</td>
                            <td class="text-center">${data.soldOut == true ? `<span class="badge badge-danger">Hết hàng</span>`  :  `<span class="badge badge-success">Còn hàng</span>`}</td>
                            <td>
                                <button type="button" class="btn btn-warning sua-san-pham" ><i class="fas fa-pen"></i>
                                    Sửa</button>
                                <button type="button" class="btn btn-danger xoa-san-pham" ><i class="fas fa-trash-alt"></i>
                                    Xóa</button>
                            </td>
                        </tr>`
        }).join("");
    }
    tableDuLieu.html(view);
    deleteProduct();
    confirmDelete();
    saveProduct();
    updateProduct();
}

function deleteProduct() {
    $(".xoa-san-pham").click(function () {
        indexProduct = $(this).parents("tr").attr("data-index");
        $("#modalDelete").modal("show");
    })
}

function confirmDelete() {
    btnXacNhanXoa.click(function () {
        let idProduct = listProduct[indexProduct - 0].id;
        listProduct = listProduct.filter((data, index)=>{
            return index != indexProduct;
        });

        viewAllProduct();
        $("#modalDelete").modal("hide");
    })
}


function updateProduct() {
    $(".sua-san-pham").click(function () {
        indexProduct = $(this).parents("tr").attr("data-index");
        elementProduct = listProduct[indexProduct];
        textTen.val(elementProduct.name);
        selectDanhMuc.val(elementProduct.categoryId);
        numberGia.val(elementProduct.price);
        numberDaBan.val(elementProduct.bought);
        numberBaoHanh.val(elementProduct.guarantee);
        numberKhuyenMai.val(elementProduct.promotion);
        dateNgayTao.val(elementProduct.createDate);
        textAreaGioiThieu.val(elementProduct.introduction);
        textAreaThongso.val(elementProduct.specification);
        elementProduct.soldOut == true ? checkBoxHetHang.prop("checked","true") : checkBoxHetHang.prop("checked","false");
        $("#modalUpdate").modal("show");
    })
}

function checkData(selector, textError) {
    let val = $(selector).val();
    let check = false;
    val.length > 0 ? check = true : viewError(selector,textError);
    return {val, check};
}

function saveProduct() {
    btnLuuLai.click(function () {
        let {val:valTen, check:checkTen} = checkData(textTen, "Định dạng tên chưa đúng"),
            valDanhMuc = selectDanhMuc.val(),
            {val:valGia, check:checkGia}= checkData(numberGia, "Giá bán phải là số"),
            {val:valDaBan, check:checkDaBan}= checkData(numberDaBan, "Nhập số lượng đã bán"),
            {val:valBaoHanh, check:checkBaoHanh}= checkData(numberBaoHanh, "Nhập thời gian bảo hành"),
            {val:valKhuyenMai, check:checkKhuyenMai}= checkData(numberKhuyenMai, "Nhập phần trăm khuyến mãi"),
            valGioiThieu = textAreaGioiThieu.val(),
            valThongSo = textAreaThongso.val(),
            valHetHang = checkBoxHetHang.is(":checked");
        if(checkTen && checkGia && checkDaBan && checkBaoHanh && checkKhuyenMai){
            //nếu element product là null sẽ tương ứng với thêm
            let checkAction = false;  //true là sửa, false là xóa
            if(elementProduct){
                checkAction  = true;
            }else{
                elementProduct = {};
            }
            elementProduct.name = valTen;
            elementProduct.categoryId = valDanhMuc;
            elementProduct.price = valGia;
            elementProduct.bought = valDaBan;
            elementProduct.guarantee = valBaoHanh;
            elementProduct.promotion = valKhuyenMai;
            elementProduct.introduction = valGioiThieu;
            elementProduct.specification = valThongSo;
            elementProduct.soldOut = valHetHang;
            //    call api sua san pham va truyen vao elementProduct hien tai
            //    khi api tre ve ket qua thanh cong thi gan doi tuong vua tra ve vao list voi id tuong ung
            if(checkAction){
                listProduct[indexProduct] = elementProduct;
            }else{
                listProduct.push(elementProduct);
            }
            listProduct[indexProduct] = elementProduct;
            //thêm: thêm mới vào mảng, tác vụ còn lại là dùng chung
            viewAllProduct();
            $("#modalUpdate").modal("hide");
        }
    });
}

function addProduct() {
    btnThem.click(function () {
        elementProduct = null;
        $("#modalUpdate").modal("show");
    })
}
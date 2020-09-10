<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript"  src = "resources/js/models/ajax_model_category.js" ></script>
<script type="text/javascript"  src = "resources/js/pages/ajax_page_category.js"></script>
<main>
    <!-- Modal -->
    <div class="modal fade" id="modalUpdate" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="zzz">Chi tiết danh mục sản phẩm</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12">
                                <div class="form-group">
                                    <label>Tên danh mục</label>
                                    <input type="text" class="form-control is-invalid" id="text-ten"  placeholder="Nhập tên danh mục...">
                                    <div class="invalid-feedback">
                                        Please choose a username.
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                    <button type="button" class="btn btn-success" id="btn-luu-lai">Lưu</button>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true" >
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="exampleModalLabel">Xác nhận thao tác</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-12">
                                Bạn có chắc chắn xóa danh mục này không?
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
                    <button type="button" class="btn btn-danger" id="btn-xac-nhan-xoa" >Xóa</button>
                </div>
            </div>
        </div>
    </div>
    <div class="title-page mt-4">
        <div class="container">
            <div class="row">
                <div class="col-12 text-center">
                    <h1>Quản Lý Danh Mục Sản Phẩm</h1>
                </div>
                <div class="col-12">
                    <hr>
                </div>
            </div>
        </div>
    </div>
    <div class="tool-page">
        <div class="container">
            <div class="row">
                <div class="col-md-8">
                    <button type="button" class="btn btn-primary" id="btn-them"><i class="fas fa-plus"></i> Thêm danh mục sản phẩm</button>
                </div>
                <div class="col-md-2 mt-1">
                    <select class="form-control" id="select-search-danh-muc">
                        <option>Loại sản phẩm</option>
                        <option>Iphone</option>
                        <option>Dây điện</option>
                    </select>
                </div>
                <div class="col-md-2 mt-1">
                    <select class="form-control" id="select-sarch-sap-xep">
                        <option>Sắp xếp</option>
                        <option>A->Z</option>
                        <option>Z->A</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div class="table-data">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <div class="table-reponsive">
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Tên Danh Mục</th>
                                    <th scope="col">Hành Động</th>
                                </tr>
                                <tr>
                                    <th scope="row"></th>
                                    <td><input type="text" id="text-search-ten" class="form-control"></td>
                                    <td class="text-center">
                                        <button type="button" class="btn btn-primary" id="btn-tim-kiem"><i class="fas fa-search"></i> Tìm kiếm</button>
                                    </td>
                                </tr>
                            </thead>
                            <tbody >
                                    <!--ajax-->
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</main>

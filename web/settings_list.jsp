<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Settings List</title>
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css">
        <link rel="stylesheet" href="resources/css/sticky_footer.css"/>
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
    </head>
    <body data-open-modal="${openModal}">
        <div class="flex-column">
            <div class="content">
                <div class="container">
                    <h1 class="display-3 fw-bold text-center my-3">Settings List</h1>
                    
                    <c:if test="${not empty sessionScope.deleteSettingsMess}">
                        <div class="alert ${sessionScope.deleteSettingsMess.contains('failed') ? 'alert-danger' : 'alert-success'} alert-dismissible fade show" role="alert">
                            ${sessionScope.deleteSettingsMess}
                        </div>
                        <c:remove var="deleteSettingsMess" scope="session" />
                    </c:if>

                    <c:if test="${not empty sessionScope.statusMes}">
                        <div class="alert ${sessionScope.statusMes.contains('failed') ? 'alert-danger' : 'alert-success'} alert-dismissible fade show" role="alert">
                            ${sessionScope.statusMes}
                        </div>
                        <c:remove var="statusMes" scope="session" />
                    </c:if>


                    <h2 class="fw-bold mt-3">Settings Filters</h2>
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <div class="d-flex">
                            <div id="settingsFilter" class="dropdown me-3">
                                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="settingsFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                    <c:choose>
                                        <c:when test="${empty param.type}">
                                            Types
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="selectedTypeName" value="" />
                                            <c:forEach var="type" items="${typeList}">
                                                <c:if test="${type.settingTypeId == param.type}">
                                                    <c:set var="selectedTypeName" value="${type.settingTypeName}" />
                                                </c:if>
                                            </c:forEach>
                                            ${selectedTypeName}
                                        </c:otherwise>
                                    </c:choose>
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="settingsFilterButton">
                                    <li>
                                        <a class="dropdown-item filter-button ${empty param.type ? 'active' : ''}" href="settings-list">
                                            All Types
                                        </a>
                                    </li>
                                    <c:forEach var="type" items="${typeList}">
                                        <li>
                                            <a class="dropdown-item filter-button ${param.type == type.settingTypeId ? 'active' : ''}" href="?type=${type.settingTypeId}${not empty param.status ? '&status='.concat(param.status) : ''}">
                                                ${type.settingTypeName}
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>

                            <div id="statusFilter" class="dropdown me-3">
                                <button class="btn btn-outline-primary dropdown-toggle" type="button" id="statusFilterButton" data-bs-toggle="dropdown" aria-expanded="false">
                                    ${empty param.status ? 'Status' : (param.status == 'true' ? 'Active' : 'Inactive')}
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="statusFilterButton">
                                    <li>
                                        <a class="dropdown-item filter-button ${empty param.status ? 'active' : ''}" href="settings-list${not empty param.type ? '?type='.concat(param.type) : ''}">
                                            All Statuses
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item filter-button ${param.status == 'true' ? 'active' : ''}" href="?status=true${not empty param.type ? '&type='.concat(param.type) : ''}">
                                            Active
                                        </a>
                                    </li>
                                    <li>
                                        <a class="dropdown-item filter-button ${param.status == 'false' ? 'active' : ''}" href="?status=false${not empty param.type ? '&type='.concat(param.type) : ''}">
                                            Inactive
                                        </a>
                                    </li>
                                </ul>
                            </div>

                            <a class="btn btn-primary" href="settings-list">Clear Filters</a>
                        </div>

                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#settingsModal">
                            Add new Settings
                        </button>
                    </div>

                    <div class="modal fade" id="settingsModal">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title fw-bold fs-2">Add new Settings</h4>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>

                                <!-- Modal body -->
                                <div class="modal-body">
                                    <form action = "new-settings" method="POST">
                                        <label for="Name" class="fw-medium mt-2">Name(*):</label>
                                        <input value="${settingName}" pattern=".*\S+.*" name="settingName" class="form-control w-100 mt-1 border" type="text">
                                        <div class="invalid-feedback">Settings Name cannot be empty</div>

                                        <label for="Type" class="fw-medium mt-3">Type:</label>
                                        <select name="settingType" class="form-control w-100 mt-1 border">
                                            <c:forEach items="${typeList}" var="t">
                                                <option <c:if test = "${t.settingTypeId == settingType}">
                                                        selected
                                                    </c:if> value="${t.settingTypeId}">${t.settingTypeName}</option>
                                            </c:forEach>
                                        </select>

                                        <label for="Description" class="fw-medium mt-3">Description(*):</label>
                                        <input value="${settingDes}" name="des" class="form-control w-100 mt-1 mb-3 border" type="text">
                                        <div class="invalid-feedback mb-3">Description cannot be empty</div>

                                        <label for="Status" class="fw-medium">Is Active?</label>
                                        <input name="status" class="form-check-input mt-1" type="checkbox" value="true"
                                               <c:if test = "${settingStatus == true}">
                                                   checked
                                               </c:if>>
                                        </div>

                                        <c:if test="${not empty sessionScope.successMessage}">
                                            <div class="d-flex justify-content-center w-100 mb-3">
                                                <div class="alert alert-success w-75 text-center">
                                                    ${sessionScope.successMessage}
                                                </div>
                                            </div>
                                            <c:remove var="successMessage" scope="session" />
                                        </c:if>

                                        <c:if test="${not empty sessionScope.error}">
                                            <div class="d-flex justify-content-center w-100 mb-3">
                                                <div class="alert alert-danger w-75 text-center">
                                                    ${sessionScope.error}
                                                </div>
                                            </div>
                                            <c:remove var="error" scope="session" />
                                        </c:if>

                                        <!-- Modal footer -->
                                        <div class="modal-footer">
                                            <button type="submit" class="btn btn-primary" onclick="confirmAddSettings(event)">Submit</button>
                                            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">Close</button>
                                        </div>
                                    </form>
                                </div>
                            </div>

                        </div>
                        <table id="settingsList" class="table table-bordered table-hover">
                            <thead class="thead-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>Type</th>
                                    <th>Order</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="s" items="${settingsList}">
                                    <tr>
                                        <td>${s.settingId}</td>
                                        <td><a href="setting-detail?id=${s.settingId}">${s.value}</a></td>
                                        <td>${s.typeName}</td>
                                        <td>${s.order}</td>
                                        <td class="${s.status ? 'text-success fw-medium' : 'text-danger fw-medium'}">${s.status ? 'Active' : 'Inactive'}</td>
                                        <td>
                                            <a href="setting-detail?id=${s.settingId}&edit=true"><i class="fas fa-edit fa-lg"></i></a>
                                            <a href="delete-settings?id=${s.settingId}" onclick="confirmDeleteSettings(event)"><i class="fa-solid fa-trash fa-lg"></i></a>
                                                <c:if test = "${s.status == false}">
                                                <a href="settings-status?id=${s.settingId}" onclick="confirmActive(event)"><i class="fas fa-toggle-off fa-lg"></i></a>
                                                </c:if>

                                            <c:if test = "${s.status == true}">
                                                <a href="settings-status?id=${s.settingId}" onclick="confirmDeactive(event)"><i class="fas fa-toggle-on fa-lg"></i></a>
                                                </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>                              
            <script src="vendors/datatables/datatables.min.js"></script>
            <script src="resources/js/settings_list.js"></script>
            <%@include file="footer.jsp"%>
    </body>
</html>
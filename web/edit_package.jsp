<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Edit Package</title>
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
        <script src="vendors/select2/js/select2.min.js"></script>
        <<link rel="stylesheet" href="resources/css/edit_dimension.css"/>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <c:if test="${not empty sessionScope.mess}">
                    <div class="alert alert-success">${mess}</div>
                    <c:remove var="mess" scope="session"/>
                </c:if>
                <div class="col-6">
                    <c:choose>
                        <c:when test = "${service eq 'viewPackage'}">
                            <c:choose>
                                <c:when test = "${account.roleId == 1}">
                                    <h1 class="display-3 fw-bold text-center mb-3">Edit Package</h1>
                                </c:when>

                                <c:otherwise>
                                    <h1 class="display-3 fw-bold text-center mb-3">Package Detail</h1>
                                    <small id = "warnMess">* You can only view the information *</small>
                                </c:otherwise>
                            </c:choose>
                            <form action="edit-package" method="POST">

                                <input type="hidden" value="${pack.packageId}" name="pId">
                                <input type="hidden" value="viewPackage" name="service">


                                <div class="my-3" id="currentSub">
                                    <label for="subjectName" class="form-label">Subject *</label>
                                    <div class="input-group">
                                        <select name="subjectId" class="form-control" id="subjectList" aria-describedby="dimensionListHelp">
                                            <option value="" selected disabled>Choose a subject...</option>
                                            <c:forEach items="${subjectList}" var="s">
                                                <option <c:if test = "${account.roleId == 3}">disabled</c:if> value = ${s.subjectId}
                                                                                              <c:if test = "${s.subjectId == pack.subjectId}">selected</c:if> >
                                                    ${s.subjectName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="my-3">
                                    <label for="packageName" class="form-label">Package name *</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="text" class="form-control" required name="packageName"
                                                                                 value="${pack.packageName}">
                                </div>

                                <div class="my-3">
                                    <label for="duration" class="form-label">Duration(months) *</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="number" class="form-control" required name="duration"
                                                                                 value="${pack.duration}">
                                </div>

                                <div class="my-3">
                                    <label for="duration" class="form-label">Status (Read-only)</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="text" class="form-control" disabled name="status"
                                                                                 value="${pack.status == 1 ? 'Active' : 'Inactive'}">
                                </div>


                                <div class="my-3">
                                    <label for="price" class="form-label">List price *</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="number" class="form-control" required name="listP"
                                                                                 value="${pack.listPrice}">
                                </div>

                                <div class="my-3">
                                    <label for="price" class="form-label">Sell price *</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="number" class="form-control" required name="sellP"
                                                                                 value="${pack.salePrice}">
                                </div>

                                <div class="my-3">
                                    <label for="packageDescription" class="form-label">Description *</label>
                                    <input <c:if test = "${account.roleId == 3}">disabled</c:if> type="text" class="form-control" required name="packageDescription"
                                                                                 value="${pack.description}">
                                </div>

                                <c:choose>
                                    <c:when test = "${account.roleId == 1}">
                                        <button type="submit" class="btn btn-primary" onclick="confirmUpdatePackage(event)">Submit</button>
                                        <button type="button" class="btn btn-danger" onclick="window.location.href = 'package-list'">Cancel</button>
                                    </c:when>

                                    <c:otherwise>
                                        <button type="button" class="btn btn-secondary" onclick="window.location.href = 'package-list'">Back to package list</button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                            <br>
                        </c:when>
                        <c:otherwise>
                            <h1 class="display-3 fw-bold text-center mb-3">Edit Package</h1>
                            <form action="edit-package" method="POST">
                                <div class="my-3">
                                    <label for="subject" class="form-label">Subject (Read-only):</label>
                                    <input type="text" class="form-control" required name="subjectName" readonly 
                                           value="${subject.subjectName}">
                                </div>

                                <input type="hidden" value="${pack.packageId}" name="pId">
                                <input type="hidden" value="editSubjectPack" name="service">
                                <input type="hidden" value="${subject.subjectId}" name="subjectId">

                                <div class="my-3">
                                    <label for="packageName" class="form-label">Package name:</label>
                                    <input type="text" class="form-control" required name="packageName"
                                           value="${pack.packageName}">
                                </div>

                                <div class="my-3">
                                    <label for="duration" class="form-label">Duration(month):</label>
                                    <input type="number" class="form-control" required name="duration"
                                           value="${pack.duration}">
                                </div>

                                <div class="my-3">
                                    <label for="price" class="form-label">List price:</label>
                                    <input type="number" class="form-control" required name="listP"
                                           value="${pack.listPrice}">
                                </div>

                                <div class="my-3">
                                    <label for="price" class="form-label">Sell price:</label>
                                    <input type="number" class="form-control" required name="sellP"
                                           value="${pack.salePrice}">
                                </div>

                                <div class="my-3">
                                    <label for="packageDescription" class="form-label">Description:</label>
                                    <input type="text" class="form-control" required name="packageDescription"
                                           value="${pack.description}">
                                </div>

                                <button type="submit" class="btn btn-primary" onclick="confirmUpdatePackage(event)">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'subject-detail-control?subjectId=${subject.subjectId}'">Cancel</button>
                            </form>
                            <br>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
        <script src="resources/js/subject_detail_control.js"></script>
        <script src="resources/js/edit_package.js"></script>
    </body>
</html>
<%@include file="footer.jsp"%>

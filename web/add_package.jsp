<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Add new Dimension</title>
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
        <script src="vendors/select2/js/select2.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-6">
                    <c:if test="${not empty sessionScope.mess}">
                        <div class="alert alert-success">${mess}</div>
                        <c:remove var="mess" scope="session"/>
                    </c:if>
                    <h1 class="display-3 fw-bold text-center mb-3">Add new Package</h1>

                    <c:choose>
                        <c:when test = "${service eq 'addGeneral'}">
                            <small>Note: The package added is inactive by default</small>
                            <form action="add-package" method="POST">
                                <div class="my-3" id="currentSub">
                                    <label for="subjectName" class="form-label">Subject *</label>
                                    <div class="input-group">
                                        <select name="subjectId" class="form-control" id="subjectList" aria-describedby="dimensionListHelp">
                                            <option value="" selected disabled>Choose a subject...</option>
                                            <c:forEach items="${subjectList}" var="s">
                                                <option value = ${s.subjectId}
                                                        <c:if test = "${s.subjectId == pack.subjectId}">selected</c:if> >
                                                    ${s.subjectName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <input type="hidden" value="${pack.packageId}" name="pId">
                                <input type="hidden" value="addGeneral" name="service">

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

                                <button type="submit" class="btn btn-primary"  onclick="confirmAddPackage(event)">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'package-list'">Cancel</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="add-package" method="POST">
                                <div class="my-3">
                                    <label for="subject" class="form-label">Subject (Read-only):</label>
                                    <input type="text" class="form-control" required name="subjectName" readonly 
                                           value="${subject.subjectName}">
                                </div>

                                <input type="hidden" value="${pack.packageId}" name="pId">
                                <input type="hidden" value="${subject.subjectId}" name="subjectId">
                                <input type="hidden" value="addPackSubject" name="service">

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

                                <button type="submit" class="btn btn-primary"  onclick="confirmAddPackage(event)">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'subject-detail-control?subjectId=${subject.subjectId}'">Cancel</button>
                            </form>
                            <br>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <script src="resources/js/subject_detail_control.js"></script>
        <script src="resources/js/add_package.js"></script>
    </body>
</html>
<%@include file="footer.jsp"%>

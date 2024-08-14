<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Edit Dimension</title>
        <link rel="stylesheet" href="resources/css/edit_dimension.css">
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
        <script src="vendors/select2/js/select2.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-6">
                    <h1 class="display-3 fw-bold text-center mb-3">Edit Dimension</h1>


                    <c:choose>
                        <c:when test="${service eq 'dimName'}">

                            <div class="my-3" id="currentSub">
                                <div class="input-group">
                                    <select name="dimensionList" class="form-control" id="dimensionList" aria-describedby="dimensionListHelp">
                                        <option value="" selected disabled>List of existing dimensions...</option>
                                        <c:forEach items="${dimList}" var="s">
                                            <option disabled>
                                                ${s.dimensionName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <small id="dimensionListHelp" class="form-text text-muted">This field is view-only.</small>
                            </div>  

                            <form action="dimension-edit" method="POST" id="dimensionForm" onsubmit="confirmEditDimName(event)">
                                <div class="my-3">
                                    <label for="dimensionName" class="form-label">Dimension Name(*):</label>
                                    <input type="text" class="form-control" required name="dimensionName" id="dimensionName" value="${dim.dimensionName}" placeholder="Enter dimension name"
                                           pattern=".*\S+.*" oninput="validateInputDimName(this)">
                                    <small id="dimensionNameHelp" class="form-text text-muted">Please provide a unique name for the dimension</small>
                                    <div class="invalid-feedback">Dimension Name cannot be empty</div>
                                </div>

                                <div class="my-3">
                                    <label for="description" class="form-label">Description(*):</label>
                                    <textarea class="form-control" required name="nameDescription" id="nameDescription" placeholder="Enter description"
                                              pattern=".*\S+.*" oninput="validateInputDimName(this)">${dim.description}</textarea>
                                    <div class="invalid-feedback">Description cannot be empty</div>
                                </div>

                                <input type="hidden" value = "dimName" name = "service">
                                <input type="hidden" value = "${dim.dimensionId}" name = "dimensionId">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'dimension-control'">Cancel</button>
                            </form>
                        </c:when>

                        <c:when test="${service eq 'dimType'}">

                            <div class="my-3" id="currentSub">
                                <div class="input-group">
                                    <select name="dimensionList" class="form-control" id="dimensionList" aria-describedby="dimensionListHelp">
                                        <option value="" selected disabled>List of existing dimension types...</option>
                                        <c:forEach items="${typeList}" var="s">
                                            <option disabled>
                                                ${s.dimensionTypeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <small id="dimensionListHelp" class="form-text text-muted">This field is view-only.</small>
                            </div>  

                            <form action="dimension-edit" method="POST" id="dimensionForm" onsubmit="confirmEditDimName(event)">
                                <div class="my-3">
                                    <label for="dimensionName" class="form-label">Dimension Type(*):</label>
                                    <input type="text" class="form-control" required name="dimensionType" id="dimensionName" value="${type.dimensionTypeName}" placeholder="Enter dimension name"
                                           pattern=".*\S+.*" oninput="validateInputDimName(this)">
                                    <small id="dimensionNameHelp" class="form-text text-muted">Please provide a unique type for the dimension</small>
                                    <div class="invalid-feedback">Dimension Type cannot be empty</div>
                                </div>

                                <div class="my-3">
                                    <label for="description" class="form-label">Description(*):</label>
                                    <textarea class="form-control" required name="typeDescription" id="nameDescription" placeholder="Enter description"
                                              pattern=".*\S+.*" oninput="validateInputDimName(this)">${type.description}</textarea>
                                    <div class="invalid-feedback">Description cannot be empty</div>
                                </div>

                                <input type="hidden" value = "dimType" name = "service">
                                <input type="hidden" value = "${type.dimensionTypeId}" name = "typeId">
                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'dimension-type'">Cancel</button>
                            </form>
                        </c:when>

                        <c:otherwise>
                            <form action="edit-dimension" method="POST">
                                <div class="my-3">
                                    <label for="subject" class="form-label">Subject (Read-only):</label>
                                    <input type="text" class="form-control" required name="subjectName" readonly 
                                           value="${subject.subjectName}">
                                </div>

                                <input type="hidden" value="${dimension.dimensionId}" name="sId">
                                <input type="hidden" value="${subject.subjectId}" name="subjectId">
                                <input type="hidden" value="${identify.id}" name="dId">

                                <input type="hidden" name="oldDimensionId" value="${dimension.dimensionId}">
                                <input type="hidden" name="oldDimensionType" value="${type.dimensionTypeId}">

                                <div class="my-3">
                                    <label for="type" class="form-label">Type:</label>
                                    <select name = "dimType" class = "form-control">
                                        <c:forEach items = "${typeList}" var = "t">
                                            <option value = "${t.dimensionTypeId}"
                                                    <c:if test = "${t.dimensionTypeId == type.dimensionTypeId}">selected</c:if>>
                                                ${t.dimensionTypeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="my-3">
                                    <label for="dimension" class="form-label">Dimension:</label>
                                    <select name = "dimensionName" class = "form-control">
                                        <c:forEach items = "${dimList}" var = "d">
                                            <option value = "${d.dimensionId}"
                                                    <c:if test = "${d.dimensionId == dimension.dimensionId}">selected</c:if>>
                                                ${d.dimensionName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>


                                <button type="submit" class="btn btn-primary" onclick="confirmUpdateDimension(event)">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'subject-detail-control?subjectId=${subject.subjectId}'">Cancel</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <br>
                    <c:if test="${not empty sessionScope.mess}">
                        <p style="color:green;font-size: 20px">${sessionScope.mess}</p>
                        <c:remove var="mess" scope="session"/>
                    </c:if>

                    <c:if test="${not empty sessionScope.failmess}">
                        <p style="color:red;font-size: 20px">${sessionScope.failmess}</p>
                        <c:remove var="failmess" scope="session"/>
                    </c:if>
                </div>
            </div>
        </div>
        <script src="resources/js/subject_detail_control.js"></script>
        <script src="resources/js/edit_dimension.js"></script>
    </body>
</html>
<%@include file="footer.jsp"%>

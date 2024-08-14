<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Add new Dimension</title>
        <link rel = stylesheet href="resources/css/add_dimension.css">
        <link rel="stylesheet" href="vendors/select2/css/select2.min.css"/>
        <script src="vendors/select2/js/select2.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-6">

                    <c:if test="${not empty sessionScope.addSuccess}">
                        <div class="alert alert-success">${addSuccess}</div>
                        <c:remove var="addSuccess" scope="session"/>
                    </c:if>

                    <c:if test="${not empty sessionScope.addFail}">
                        <div class="alert alert-danger">${addFail}</div>
                        <c:remove var="addFail" scope="session"/>
                    </c:if>

                    <c:choose>
                        <c:when test = "${service eq 'dimName'}">
                            <h4 class="display-5 fw-bold text-center mb-3">Add new Dimension</h4>    
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

                            <form action="new-dimension" method="POST" id="dimensionForm" onsubmit="confirmAddDimName(event)">
                                <div class="my-3">
                                    <label for="dimensionName" class="form-label">Dimension Name(*):</label>
                                    <input type="text" class="form-control" required name="dimensionName" id="dimensionName" value="${dimName}" placeholder="Enter dimension name"
                                           pattern=".*\S+.*" oninput="validateInputDimName(this)">
                                    <small id="dimensionNameHelp" class="form-text text-muted">Please provide a unique name for the dimension</small>
                                    <div class="invalid-feedback">Dimension Name cannot be empty</div>
                                </div>

                                <div class="my-3">
                                    <label for="description" class="form-label">Description(*):</label>
                                    <textarea class="form-control" required name="nameDescription" id="nameDescription" placeholder="Enter description"
                                              pattern=".*\S+.*" oninput="validateInputDimName(this)">${desc}</textarea>
                                    <div class="invalid-feedback">Description cannot be empty</div>
                                </div>

                                <input type="hidden" value = "dimName" name = "service">
                                <input type="hidden" value = "${dim.dimensionId}" name = "dimensionId">

                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'dimension-control'">Cancel</button>
                            </form>
                        </c:when>

                        <c:when test = "${service eq 'dimType'}">
                            <h4 class="display-5 fw-bold text-center mb-3">Add new Dimension Type</h4>    
                            <div class="my-3" id="currentSub">
                                <div class="input-group">
                                    <select name="dimensionList" class="form-control" id="dimensionList" aria-describedby="dimensionListHelp">
                                        <option value="" selected disabled>List of existing dimensions...</option>
                                        <c:forEach items="${typeList}" var="s">
                                            <option disabled>
                                                ${s.dimensionTypeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <small id="dimensionListHelp" class="form-text text-muted">This field is view-only.</small>
                            </div>  

                            <form action="new-dimension" method="POST" id="dimensionForm" onsubmit="confirmAddDimName(event)">
                                <div class="my-3">
                                    <label for="dimensionName" class="form-label">Dimension Name(*):</label>
                                    <input type="text" class="form-control" required name="typeName" id="dimensionName" value="${dimType}" placeholder="Enter dimension name"
                                           pattern=".*\S+.*" oninput="validateInputDimName(this)">
                                    <small id="dimensionNameHelp" class="form-text text-muted">Please provide a unique type name</small>
                                    <div class="invalid-feedback">Dimension Name cannot be empty</div>
                                </div>

                                <div class="my-3">
                                    <label for="description" class="form-label">Description(*):</label>
                                    <textarea class="form-control" required name="typeDescription" id="nameDescription" placeholder="Enter description"
                                              pattern=".*\S+.*" oninput="validateInputDimName(this)">${desc}</textarea>
                                    <div class="invalid-feedback">Description cannot be empty</div>
                                </div>

                                <input type="hidden" value = "dimType" name = "service">
                                <input type="hidden" value = "${dim.dimensionId}" name = "dimensionId">

                                <button type="submit" class="btn btn-primary">Submit</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'dimension-type'">Cancel</button>
                            </form>
                        </c:when>    

                        <c:otherwise>

                            <h1 class="display-3 fw-bold text-center mb-3">Add new Dimension</h1>
                            <form action="add-dimension" method="POST">
                                <div class="my-3">
                                    <label for="subject" class="form-label">Subject (Read-only):</label>
                                    <input type="text" class="form-control" required name="subjectName" readonly 
                                           value="${subject.subjectName}">
                                </div>
                                <input type="hidden" value="${identify.id}" name="dId">
                                <input type="hidden" value="${dimension.dimensionId}" name="sId">
                                <input type="hidden" value="${subject.subjectId}" name="subjectId">
                                <div class="my-3">
                                    <label for="dimension" class="form-label">Dimension Type:</label>
                                    <select name = "dimType" class = "form-control">
                                        <c:forEach items = "${typeList}" var = "t">
                                            <option value = "${t.dimensionTypeId}">
                                                ${t.dimensionTypeName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="my-3">
                                    <label for="type" class="form-label" >Dimension Name:</label>
                                    <select name = "dimensionName" class = "form-control">
                                        <c:forEach items = "${dimList}" var = "d">
                                            <option value = "${d.dimensionId}">
                                                ${d.dimensionName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <button type="submit" class="btn btn-primary"  onclick="confirmAddDimension(event)">Add</button>
                                <button type="button" class="btn btn-danger" onclick="window.location.href = 'subject-detail-control?subjectId=${subject.subjectId}'">Cancel</button>
                            </form>
                        </c:otherwise>
                    </c:choose>
                    <br>
                </div>
            </div>
        </div>
        <script src="resources/js/subject_detail_control.js"></script>
        <script src="resources/js/add_dimension.js"></script>
    </body>
</html>
<%@include file="footer.jsp"%>

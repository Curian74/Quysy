<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Subject Details</title>
        <link rel="stylesheet" href="resources/css/subject_detail_control.css">
    </head>
    <body>

        <c:choose>
            <c:when test = "${user.roleId == 3 || user.roleId == 1}">
                <div class="container">
                    <div class="card shadow">
                        <div class="card-header">
                            <h1>Subject Details</h1>
                        </div>
                        <div class="card-body">
                            <c:if test="${not empty deleteDimSuccess}">
                                <div class="alert alert-success">${deleteDimSuccess}</div>
                            </c:if>
                            <c:if test="${not empty deleteDimFail}">
                                <div class="alert alert-danger">${deleteDimFail}</div>
                            </c:if>

                            <c:if test="${not empty activationSuccess}">
                                <div class="alert alert-success">${activationSuccess}</div>
                            </c:if>
                            <c:if test="${not empty activationFail}">
                                <div class="alert alert-danger">${activationSuccess}</div>
                            </c:if>

                            <c:if test="${not empty sessionScope.updateMessage}">
                                <div class="alert ${sessionScope.updateMessage.contains('failed') ? 'alert-danger' : 'alert-success'} alert-dismissible fade show" role="alert">
                                    ${sessionScope.updateMessage}
                                </div>
                                <c:remove var="updateMessage" scope="session" />
                            </c:if>

                            <div class="tabs">
                                <div class="tab active">Overview</div>
                                <div class="tab">Dimension</div>
                                <div class="tab">Price Package</div>
                            </div>
                            <form action="subject-detail-control" method="POST" enctype="multipart/form-data">
                                <div class="tab-content">
                                    <div class="form-row">
                                        <div class="form-column-left">
                                            <c:if test="${user.roleId == 1}">
                                                <c:if test="${subject.expertId == 0 || empty subject.expertId}">
                                                    <h3 id="exp-mes">No expert for this subject yet</h3>
                                                </c:if>
                                            </c:if>
                                            <div class="form-group" id="s-name">
                                                <label for="subjectName">Subject Name</label>
                                                <input type="text" id="subjectName" required name="subjectName" class="form-control"
                                                       value="${subject.subjectName}">
                                            </div>
                                            <div class="form-group">
                                                <label for="category">Category</label>
                                                <select id="category" name="category" class="form-control">
                                                    <c:forEach items="${cat}" var="d">
                                                        <option required value="${d.categoryId}" <c:if test="${d.categoryId == subject.categoryId}">selected</c:if>>
                                                            ${d.categoryName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="checkForm">
                                                <input type="checkbox" id="featuredSubject" name="featuredSubject" value="true" class="form-check-input"
                                                       <c:if test="${subject.isFeatured == true}">
                                                           checked
                                                       </c:if>>
                                                <label for="featuredSubject" class="form-check-label">Featured Subject</label>
                                                <c:if test = "${user.roleId == 1}">
                                                    <span id="status-bl">
                                                        <label for="status" class="ml-3">Status</label>
                                                        <select id="status" name="status">
                                                            <option value="true" <c:if test="${subject.status == true}">selected</c:if>>Published</option>
                                                            <option value="false" <c:if test="${subject.status == false}">selected</c:if>>Unpublished</option>
                                                            </select>
                                                        </span>
                                                </c:if>
                                            </div>
                                            <c:if test = "${user.roleId == 1}">
                                                <div id="owner">
                                                    <label for="status" class="ml-3">Change Owner:</label>
                                                    <select id="expert-list" class ="form-control mt-1" name="owner">
                                                        <c:forEach items="${expertList}" var="e">
                                                            <option value="${e.userId}" <c:if test="${e.userId == subject.expertId}">selected</c:if>>
                                                                ${e.fullname}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </c:if>
                                            <div class="form-group" id="des">
                                                <label for="description">Tag line</label>
                                                <textarea required id="description" name="tagLine" class="form-control">${subject.tagLine}</textarea>
                                            </div>
                                            <input type="hidden" name="service" value="general">
                                            <input type="hidden" name="sId" value="${subject.subjectId}">
                                            <div class="form-group" id="des">
                                                <label for="description">Brief Info</label>
                                                <textarea required id="description" name="brief" class="form-control">${subject.briefInfo}</textarea>
                                            </div>
                                            <div class="form-group" id="des">
                                                <label for="description">Description</label>
                                                <textarea required id="description" name="description" class="form-control">${subject.description}</textarea>
                                            </div>
                                            <c:if test="${subject.expertId != 0 && not empty subject.expertId}">
                                                <h3 id="exp-mes">Assigned Expert:</h3>
                                                <div class="expert-section text-center">
                                                    <div class="profile-picture mx-auto">
                                                        <img src="${expert.avatar}">
                                                    </div>
                                                    <div class="expert-info">
                                                        <div class="expert-name" id="expertName">
                                                            <p>${expert.fullname}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </div>
                                        <div class="form-column-right">
                                            <div class="subject-image">
                                                <h4 id="thumb">Subject Thumbnail</h4>
                                                <img src="${subject.thumbnail}" alt="Subject Image" id="subjectImage">
                                                <input type="file" id="img" name="img" accept="image/*" onchange="previewImage(event)">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="buttons">
                                        <button type="submit" class="btn btn-primary" onclick="confirmUpdateGeneral(event)">Submit</button>
                                        <button type="button" class="btn btn-secondary" onclick="window.location.href = 'subject-control'">Back</button>
                                    </div>
                            </form>
                        </div>

                        <div class="tab-content" style="display:none;">
                            <div class="form-row">
                                <div class="dimension-table">
                                    <div class="table-header">
                                        <h2>All Dimensions</h2>
                                        <a href="add-dimension?subjectId=${subject.subjectId}" class="add-new">Add New</a>
                                    </div>
                                    <c:choose>
                                        <c:when test="${empty dList}">
                                            <p>Nothing to show here...</p>
                                        </c:when>
                                        <c:otherwise>
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Type</th>
                                                        <th>Dimension</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${dList}" var="d" varStatus="loop">
                                                        <tr>
                                                            <td>${loop.index + 1}</td>
                                                            <td>${d.dimensionType}</td>
                                                            <td>${d.dimensionName}</td>
                                                            <td>
                                                                <a class="under-warn" href="edit-dimension?dimensionId=${d.dimensionId}&dimensionType=${d.dimensionTypeId}&subjectId=${subject.subjectId}&dId=${d.id}">Edit</a>
                                                                <a class="under-warn" href="delete-dimension?dimensionId=${d.dimensionId}&dimensionType=${d.dimensionTypeId}&subjectId=${subject.subjectId}&dId=${d.id}" onclick="confirmDeleteDimension(event)">Delete</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>


                        <div class="tab-content" style="display:none;">
                            <div class="form-row">
                                <div class="price-package-table">
                                    <div class="table-header">
                                        <h2>All Price Packages</h2>
                                        <c:if test="${user.roleId == 1}">
                                            <a class="under-warn" href="add-package?&subjectId=${subject.subjectId}&service=addPackSubject" class="add-new">Add New</a>
                                        </c:if>
                                    </div>
                                    <c:choose>
                                        <c:when test="${empty packS}">
                                            <p>Nothing to show here...</p>
                                        </c:when>
                                        <c:otherwise>
                                            <table>
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Package</th>
                                                        <th>Duration</th>
                                                        <th>List Price</th>
                                                        <th>Sale Price</th>
                                                        <th>Status</th>
                                                            <c:if test = "${user.roleId == 1}">
                                                            <th>Actions</th>
                                                            </c:if>
                                                        <th style="text-align:center">View Subject Lesson</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${packS}" var="d" varStatus="loop">
                                                        <tr>
                                                            <td>${loop.index + 1}</td>
                                                            <c:choose>
                                                                <c:when test="${user.roleId == 1}">
                                                                    <td>
                                                                        <a class="under-warn" href="edit-package?packageId=${d.packageId}&subjectId=${subject.subjectId}&service=editSubjectPack">
                                                                            ${d.packageName}
                                                                        </a>
                                                                    </td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>${d.packageName}</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <td>${d.duration} month(s)</td>
                                                            <td>${d.listPrice} VND</td>
                                                            <td>${d.salePrice} VND</td>
                                                            <c:choose>
                                                                <c:when test="${d.status == 1}">
                                                                    <td>Activated</td>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <td>Inactivated</td>
                                                                </c:otherwise>
                                                            </c:choose>
                                                            <c:if test="${user.roleId == 1}">
                                                                <td>
                                                                    <a class="under-warn" href="edit-package?packageId=${d.packageId}&subjectId=${subject.subjectId}&service=editSubjectPack">Edit</a>

                                                                    <c:if test="${d.status == 0}">
                                                                        <a class="under-warn" href="activation?packageId=${d.packageId}&subjectId=${subject.subjectId}&service=active"
                                                                           onclick="confirmActivatePackage(event)">
                                                                            Activate
                                                                        </a>
                                                                    </c:if>
                                                                    <c:if test="${d.status == 1}">
                                                                        <a class="under-warn" href="activation?packageId=${d.packageId}&subjectId=${subject.subjectId}&service=deactive"
                                                                           onclick="confirmDeactivatePackage(event)">
                                                                            Deactivate
                                                                        </a>
                                                                    </c:if>
                                                                </c:if>
                                                            </td>
                                                            <td style="text-align:center">
                                                                <c:if test="${user.roleId == 1 || user.roleId == 3}">
                                                                    <a href="subject-lesson?packageId=${d.packageId}&subjectId=${subject.subjectId}">Subject lesson for ${subject.subjectName}</a>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>


                        <!-- Edit Dimension Modal -->
                        <div class="modal" id="editDimensionModal" tabindex="-1" role="dialog">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Edit Dimension</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <form id="editDimensionForm">
                                            <div class="form-group">
                                                <label for="dimensionType">Type</label>
                                                <input type="text" class="form-control" id="dimensionType" name="dimensionType">
                                            </div>
                                            <div class="form-group">
                                                <label for="dimensionName">Dimension</label>
                                                <input type="text" class="form-control" id="dimensionName" name="dimensionName">
                                            </div>
                                            <input type="hidden" id="dimensionId" name="dimensionId">
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary" onclick="submitEditDimension()">Save changes</button>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </c:when>

    </c:choose>

    <%@ include file="footer.jsp" %>
    <script src="resources/js/subject_detail_control.js"></script>
</body>
</html>

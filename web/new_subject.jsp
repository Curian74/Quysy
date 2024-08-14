<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Quysy | Add new Dimension</title>
        <link rel="stylesheet" href="resources/css/new_subject.css">
        <link rel="stylesheet" href="vendors/fontawesome/css/all.css">
        <script src="vendors/jquery/jquery-3.7.1.min.js"></script>
        <link href="vendors/select2/css/select2.min.css" rel="stylesheet" />
        <script src="vendors/select2/js/select2.min.js"></script>
    </head>
    <body>
        <div class="container">
            <h1 class="display-5 fw-bold text-center mb-3">New Subject</h1>
            <c:if test="${not empty addSuccess}">
                <div class="alert alert-success">${addSuccess} <a href="subject-detail-control?subjectId=${target}">View Detail</a></div>
                <c:remove var="addSuccess" scope="session"/>
            </c:if>

            <c:if test="${not empty addFail}">
                <div class="alert alert-danger">${addFail}</div>
                <c:remove var="addFail" scope="session"/>
            </c:if>

            <div class="my-3" id="currentSub">
                <div class="input-group">
                    <select name="subjectList" class="form-control" id="subjectList" >
                        <option value="" selected disabled>List of existing subjects...</option>
                        <c:forEach items="${sList}" var="s">
                            <option disabled>
                                ${s.subjectName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <p class ="notice">This field is view only</p>
            </div>    

            <div class="row">
                <div class="col-md-8">
                    <form action="new-subject" method="POST" enctype="multipart/form-data">
                        <div class="my-3">
                            <label for="subject" class="form-label">Subject Name(*):</label>
                            <input id="name" type="text" class="form-control" required name="subject" pattern=".*\S+.*" oninput="validateInput(this)" value="${subject}">
                            <div class="invalid-feedback">Subject Name cannot be empty</div>
                        </div>

                        <div class="my-3">  
                            <label for="category" class="form-label">Category(*):</label>
                            <div class="input-group">
                                <select name="cat" class="form-control" id="categorySelect">
                                    <option value="" selected disabled>Select a category</option> <!-- Placeholder option -->
                                    <c:forEach items="${catList}" var="c">
                                        <option value="${c.categoryId}" <c:if test="${cat eq c.categoryId}">selected</c:if>>
                                            ${c.categoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="my-3">
                            <label for="expert" class="form-label">Owner(*):</label>
                            <div class="input-group">
                                <select name="owner" class="form-control" id="ownerSelect" required>
                                    <option value="" selected disabled>Select an owner</option> <!-- Placeholder option -->
                                    <c:forEach items="${expertList}" var="e">
                                        <option value="${e.userId}" <c:if test="${owner eq e.userId}">selected</c:if>>
                                            ${e.fullname}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <input type="checkbox" id="featuredSubject" name="featuredSubject" value="true" class="form-check-input" <c:if test="${featuredSubject == 'true'}">checked</c:if>>
                        <label for="featuredSubject" class="form-check-label">is Featured ?</label>

                        <div class="my-3" id="des">
                            <label for="tag">Tag line(*):</label>
                            <input type="text" class="form-control" required name="tag" pattern=".*\S+.*" oninput="validateInput(this)" value="${tag}">
                            <div class="invalid-feedback">Tag line cannot be empty</div>
                        </div>

                        <div class="my-3" id="des">
                            <label for="description">Brief Info(*):</label>
                            <input type="text" class="form-control" required name="brief" pattern=".*\S+.*" oninput="validateInput(this)" value="${brief}">
                            <div class="invalid-feedback">Brief Info cannot be empty</div>
                        </div>

                        <div class="my-3">
                            <label for="subject" class="form-label">Description(*):</label>
                            <textarea required id="description" name="description" class="form-control" oninput="validateInput(this)">${description}</textarea>           
                            <div class="invalid-feedback">Description cannot be empty</div>
                        </div>
                        <div class="buttons">
                            <button type="submit" class="btn btn-primary" onclick="confirmAddSubject(event)">Submit</button>
                            <button type="button" class="btn btn-secondary" onclick="window.location.href = 'subject-control'">Back</button>
                        </div>
                        <br>
                        </div>

                        <div class="col-md-4" id="thumb">
                            <h2>Subject Thumbnail</h2>
                            <input type="file" id="thumbnail" name="thumb" accept="image/*" onchange="previewImage(event)">
                            <br>
                            <img id="imagePreview" src="#" alt="Preview" style="display: none; max-width: 100%; height: auto;">
                        </div>

                    </form>
                </div>
            </div> 
            <script src="resources/js/new_subject.js"></script>
            <%@include file="footer.jsp"%>
    </body>
</html>

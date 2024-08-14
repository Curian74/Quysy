<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/style.css">
        <script>
            function confirmChange() {
                return confirm("Are you sure you want to save changes?");
            }
        </script>
    </head>
    <body>
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <c:choose>
            <c:when test="${sessionUser.roleId == 4}">
                <div class="container">
                    <form action="slider-details" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">  
                        <h3 style="text-align: center; font-style: italic;">Slider details for ${slider.title}</h3><br>
                        <input type="hidden" name="id" value="${slider.sliderId}">
                        <input type="hidden" id="editMode" name="editMode" value="true">
                        <input type="hidden" name="existingThumbnail" value="${slider.sliderThumbnail}"> 
                        <div style="text-align: center;">
                            <img id="thumbnailPreview" src="${slider.sliderThumbnail}" alt="Thumbnail Preview" width="300" height="180"><br>
                            <input type="file" name="thumbnail" id="thumbnail" accept="image/*" onchange="previewImage()" ${slider.sliderThumbnail} >
                        </div>
                        <h6>Slider ID: ${slider.sliderId}</h6>

                        <div class="form-group">
                            <label for="title">Title:</label>
                            <input type="text" name="title" id="title" value="${slider.title}" required disabled>
                        </div>

                        <div class="form-group">
                            <label for="backlink">Backlink:</label>
                            <input type="text" name="backlink" id="backlink" value="${slider.backlink}" required disabled>
                        </div>

                        <div class="form-group">
                            <label for="status">Status:</label>
                            <select name="status" id="status" disabled>
                                <option value="true" ${slider.status ? 'selected' : ''}>Show</option>
                                <option value="false" ${slider.status ? '' : 'selected'}>Hide</option>
                            </select>
                        </div>

                        <div class="form-group">
                            <label for="notes">Notes:</label>
                            <textarea name="notes" id="notes" disabled>${slider.notes}</textarea>
                        </div>

                        <div class="form-group">
                            <button type="button" class="btn btn-secondary" onclick="window.location.href = 'sliders-list'">Back</button>
                            <button type="button" id="editButton" class="btn btn-secondary" onclick="toggleEditMode(true)">Edit</button>
                            <button type="button" id="cancelButton" class="btn btn-secondary" onclick="toggleEditMode(false)" style="display:none;">Cancel</button>
                            <button type="submit" id="submitButton" class="btn btn-secondary" style="display:none;" onclick="return confirmChange();">Submit</button>
                            <div style="text-align: center">
                                <c:if test="${not empty sessionScope.updateMessage}">
                                    <div class="alert ${sessionScope.updateMessage.contains('failed') ? 'alert-danger' : 'alert-success'} alert-dismissible fade show p-3" role="alert" style="max-width: 400px; margin: auto;">
                                        ${sessionScope.updateMessage}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                    </div>
                                    <c:remove var="updateMessage" scope="session"/>
                                </c:if>
                            </div>
                        </div>
                    </form>
                </div>
                <script src="resources/js/slider.js"></script>
            </c:when>
            <c:otherwise>
                <div class="container">
                    <h1 style="font-size: 64px; color: red; margin-top: 140px; margin-bottom: 136px;">You have no permission to visit this page</h1>
                </div>                    
                <script src="resources/js/permission_return.js"></script>
            </c:otherwise>
        </c:choose>
    </body>
</html>
<%@include file="footer.jsp" %>

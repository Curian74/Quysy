<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="resources/css/style.css">
        <title>Setting Details</title>
    </head>
    <body>        
        <div class="container">
            <form action="setting-detail" method="post">  
                <h3 style="text-align: center; font-style: italic;">Settings details for: ${setting.value}</h3><br>
                <input type="hidden" name="settingId" value="${setting.settingId}">
                <h6>Setting ID: ${setting.settingId}</h6>
                <div class="form-group">
                    <label for="name">Settings Name:</label>
                    <input type="text" name="name" id="name" value="${setting.value}" required ${not edit ? 'disabled' : ''}>
                </div>
                <div class="form-group">
                    <label for="type">Settings Type:</label>
                    <select name="settingTypeId" id="type" ${not edit ? 'disabled' : ''}>
                        <c:forEach items="${typeList}" var="t">
                            <option value="${t.settingTypeId}" ${t.settingTypeId eq setting.settingTypeId ? 'selected' : ''}>
                                ${t.settingTypeName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="order">Settings Order:</label>
                    <input type="number" name="order" id="order" value="${setting.order}" required ${not edit ? 'disabled' : ''}>
                </div>
                <div class="form-group">
                    <label for="status">Status:</label>
                    <select name="status" id="status" ${not edit ? 'disabled' : ''}>
                        <option value="true" ${setting.status ? 'selected' : ''}>Active</option>
                        <option value="false" ${not setting.status ? 'selected' : ''}>Inactive</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description">Settings Description:</label>
                    <textarea name="description" id="description" ${not edit ? 'disabled' : ''}>${setting.description}</textarea>
                </div>
                <div class="form-group">
                    <c:choose>
                        <c:when test="${not edit}">
                            <button type="button" onclick="window.location.href = 'setting-detail?id=${setting.settingId}&edit=true'">Edit</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit">Save</button>
                            <button type="button" onclick="window.location.href = 'setting-detail?id=${setting.settingId}'">Cancel</button>
                        </c:otherwise>
                    </c:choose>
                    <button type="button" onclick="window.location.href = 'settings-list'">Back</button>

                </div>
            </form>
            <c:if test="${not empty message}">
                <div class="alert ${messageType == 'success' ? 'alert-success' : 'alert-danger'}" role="alert">
                    ${message}
                </div>
            </c:if>
        </div>
    </body>
</html>
<%@ include file="footer.jsp" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Registration List</title>
        <link rel="stylesheet" href="vendors/bootstrap/bootstrap.min.css">
        <link rel="stylesheet" href="resources/css/registration_list.css">
        <link rel="stylesheet" href="vendors/datatables/datatables.min.css"/>
    </head>
    <body>
        <div class="container">
            <div class="top-bar">
                <h1 class="text-center fw-bold mb-4">Registration List</h1>
            </div>
            <form id="searchForm" action="registration-list" method="get" class="mb-4 search-form text-center">
                <input type="text" hidden id="subject" name="subject">
                <input type="text" hidden id="email" name="email">

                <div class="filter">
                    <label for="status">Status</label>
                    <select class="form-control" id="status" name="status" onchange="autoSubmit()">
                        <option value="">Status</option>
                        <c:forEach var="status" items="${statusList}">
                            <option value="${status}" <c:if test="${param.status == status}">selected</c:if>>${status}</option>
                        </c:forEach>
                    </select>

                    <label for="registrationTimeFrom">From:</label>

                    <input class="form-control" type="date" id="registrationTimeFrom" name="registrationTimeFrom" value="${param.registrationTimeFrom}">

                    <label for="registrationTimeTo">To:</label>
                    <input class="form-control" type="date" id="registrationTimeTo" name="registrationTimeTo" value="${param.registrationTimeTo}">
                    <button type="submit" onclick="trimAndSubmit()" class="btn btn-primary">Filter</button>   
                    <button type="button" onclick="clearFilters()" class="btn btn-secondary">Clear</button>
                </div>
            </form>
            <p class="sort-instruction">(*) Click on column header to sort</p>
            <table id="registrationList" class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Email</th>
                        <th>Registration Time</th>
                        <th>Subject</th>
                        <th>Package</th>
                        <th>Cost</th>
                        <th class="status-column">Status</th>
                        <th>Valid From</th>
                        <th>Valid To</th>
                        <th>Updated</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reg" items="${registrations}">
                        <tr>
                            <td><a href="registration-details?registrationId=${reg.registrationId}">${reg.registrationId}</a></td>
                            <td>${reg.email}</td>
                            <td>${reg.registrationDate}</td>
                            <td>${reg.subjectName}</td>
                            <td>${reg.packageName}</td>
                            <td>${reg.totalCost}</td>
                            <td class="<c:choose>
                                    <c:when test="${reg.status == 'Submitted'}">text-info fw-bold</c:when>
                                    <c:when test="${reg.status == 'Cancelled'}">text-danger fw-bold</c:when>
                                    <c:when test="${reg.status == 'Paid'}">text-warning fw-bold</c:when>
                                    <c:when test="${reg.status == 'Successful'}">text-success fw-bold</c:when>
                                </c:choose>">${reg.status}</td>
                            <td>${reg.validFrom}</td>
                            <td>${reg.validTo}</td>
                            <td>${lastUpdatedByMap[reg.lastUpdatedBy]}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <script src="vendors/datatables/datatables.min.js"></script>
        <script src="resources/js/registration_list.js"></script>
    </body>
</html>
<%@include file="footer.jsp"%>

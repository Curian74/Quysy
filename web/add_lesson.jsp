<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="Model.Package"%>
<%@page import="Model.Lesson"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="header.jsp" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Add New Lesson</title>
        <link rel="stylesheet" href="resources/css/add_lesson.css">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="ckeditor/ckeditor.js"></script>
        <script>
            $(document).ready(function () {
                function toggleFields() {
                    var lessonType = $('input[name="lessonTypeId"]:checked').val();
                    if (lessonType == '1') {
                        $('#youtubeLinkContainer, #htmlContentContainer, #quizContainer').addClass('hidden').find('input, select, textarea').prop('disabled', true);
                    } else if (lessonType == '2') {
                        $('#youtubeLinkContainer, #htmlContentContainer').removeClass('hidden').find('input, select, textarea').prop('disabled', false);
                        $('#quizContainer').addClass('hidden').find('input, select, textarea').prop('disabled', true);
                    } else if (lessonType == '3') {
                        $('#youtubeLinkContainer').addClass('hidden').find('input, select, textarea').prop('disabled', true);
                        $('#htmlContentContainer, #quizContainer').removeClass('hidden').find('input, select, textarea').prop('disabled', false);
                    }
                }

                function updateOrderField() {
                    var lessonType = $('input[name="lessonTypeId"]:checked').val();
                    var $orderField = $('#lessonOrder');

                    if (lessonType == '1') {
                        var nextOrder = ${latestOrder + 1};
                        $orderField.replaceWith('<input type="text" id="lessonOrder" name="lessonOrder" value="' + nextOrder + '" readonly>');
                    } else {
                        var options = '<select id="lessonOrder" name="lessonOrder">';
            <c:forEach items="${existingOrders}" var="order">
                        options += '<option value="${order}">${order}</option>';
            </c:forEach>
                        options += '</select>';
                        $orderField.replaceWith(options);
                    }
                }

                function updateTopicOptions() {
                    var lessonType = $('input[name="lessonTypeId"]:checked').val();
                    var $topicSelect = $('#topicId');
                    $topicSelect.empty();

                    if (lessonType === '1') {
            <c:forEach var="topic" items="${lessonTopics}">
                <c:if test="${topic.lessonTopicId == 1 || topic.lessonTopicId == 2}">
                        $topicSelect.append($('<option>', {
                            value: ${topic.lessonTopicId},
                            text: '${topic.lessonTopicName}'
                        }));
                </c:if>
            </c:forEach>
                    } else if (lessonType === '2') {
            <c:forEach var="topic" items="${lessonTopics}">
                <c:if test="${topic.lessonTopicId == 1 || topic.lessonTopicId == 3}">
                        $topicSelect.append($('<option>', {
                            value: ${topic.lessonTopicId},
                            text: '${topic.lessonTopicName}'
                        }));
                </c:if>
            </c:forEach>
                    } else if (lessonType === '3') {
            <c:forEach var="topic" items="${lessonTopics}">
                <c:if test="${topic.lessonTopicId == 3 || topic.lessonTopicId == 4}">
                        $topicSelect.append($('<option>', {
                            value: ${topic.lessonTopicId},
                            text: '${topic.lessonTopicName}'
                        }));
                </c:if>
            </c:forEach>
                    }
                }

                $('input[name="lessonTypeId"]').change(function () {
                    toggleFields();
                    updateOrderField();
                    updateTopicOptions();
                });

                function handleSearchInput(inputId, selectId) {
                    $(inputId).on('input', function () {
                        var filter = $(this).val().toLowerCase().trim();
                        if (filter) {
                            $(selectId).show();
                        } else {
                            $(selectId).hide();
                        }
                        $(selectId + ' option').each(function () {
                            var text = $(this).text().toLowerCase();
                            $(this).toggle(text.indexOf(filter) > -1);
                        });
                    });

                    $(inputId).on('click', function (e) {
                        $(selectId).show();
                        e.stopPropagation();
                    });

                    $(selectId).on('change', function () {
                        $(inputId).val($(selectId + ' option:selected').text());
                        $(selectId).hide();
                    });
                }

                handleSearchInput('#packageSearch', '#packageId');
                handleSearchInput('#quizSearch', '#quizId');
                if (${sessionScope.account.roleId != 3}) {
                    handleSearchInput('#userSearch', '#userId');
                }

                $(document).on('click', function (e) {
                    if (!$(e.target).closest('.search-container').length) {
                        $('#packageId, #quizId, #userId').hide();
                    }
                });

                $('#btnPreview').click(function () {
                    var youtubeLink = $('#youtubeLink').val().trim();
                    if (youtubeLink) {
                        var videoId = youtubeLink.split('v=')[1];
                        var ampersandPosition = videoId.indexOf('&');
                        if (ampersandPosition != -1) {
                            videoId = videoId.substring(0, ampersandPosition);
                        }
                        var embedUrl = 'https://www.youtube.com/embed/' + videoId;
                        var iframe = '<iframe src="' + embedUrl + '" frameborder="0" allowfullscreen></iframe>';
                        $('#youtubePreview').html(iframe).show();
                    }
                });

                CKEDITOR.replace('htmlContent', {
                    filebrowserUploadUrl: '${pageContext.request.contextPath}/new-lesson',
                    filebrowserUploadMethod: 'form'
                });

                toggleFields();
                updateOrderField();
                updateTopicOptions();
            });

            function clearForm() {
                $('#lessonName').val('');
                $('#packageSearch').val('');
                $('#packageId').val('');
                $('input[name="lessonTypeId"]').prop('checked', false);
                $('#youtubeLink').val('');
                $('#youtubePreview').empty().hide();
                $('#htmlContent').val('');
                $('#topicId').val('');
                $('#quizSearch').val('');
                $('#quizId').val('');
            }

            function openModal() {
                document.getElementById("myModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("myModal").style.display = "none";
            }

            window.onclick = function (event) {
                if (event.target == document.getElementById("myModal")) {
                    document.getElementById("myModal").style.display = "none";
                }
            }

            function confirmClear() {
                return confirm("Are you sure you want to clear all filled field?");
            }

            function confirmAddnew() {
                return confirm("Are you sure you want to add new lesson with the current filled fields");
            }

            function confirmReturn() {
                return confirm("Are you sure you want to return to subject lesson: ${subject.subjectName}");
            }

            document.getElementById('lessonForm').addEventListener('submit', function (e) {
                var lessonName = document.getElementById('lessonName').value;
                var lessonOrder = document.getElementById('lessonOrder').value;
                var lessonType = document.querySelector('input[name="lessonTypeId"]:checked');
                var htmlContent = CKEDITOR.instances.htmlContent.getData();
                var youtubeLink = document.getElementById('youtubeLink').value;

                if (lessonName.trim() === '') {
                    e.preventDefault();
                } else if (lessonName.charAt(0) === ' ') {
                    e.preventDefault();
                }

                if (isNaN(lessonOrder) || parseInt(lessonOrder) <= 0) {
                    e.preventDefault();
                }

                if (lessonType && lessonType.value === '2') {
                    if (htmlContent.trim() === '') {
                        e.preventDefault();
                    }
                    if (youtubeLink.trim() === '') {
                        e.preventDefault();
                    }
                }
            });
            var successMessage = "${requestScope.successMessage}";
            if (successMessage) {
                alert(successMessage);
            }

            var errorMessage = "${requestScope.errorMessage}";
            if (errorMessage) {
                alert(errorMessage);
            }
        </script>

    </head>
    <body>
        <c:set var="u" value="${requestScope.user}"/>
        <c:set var="sessionUser" value="${sessionScope.account}"/>
        <div class="form-container">
            <h2>New Lesson for ${subject.subjectName}</h2>
            <c:if test="${not empty successMessage}">
                <div class="alert alert-success" role="alert">
                    ${successMessage}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger" role="alert">
                    ${errorMessage}
                </div>
            </c:if>

            <form action="new-lesson" method="post">
                <input type="hidden" name="packageId" value="${param.packageId}">
                <input type="hidden" name="subjectId" value="${param.subjectId}">
                <div class="col-12">
                    <div class="form-group">
                        <label>Lesson Type:</label>
                        <div class="radio-group">
                            <c:forEach var="type" items="${lessonTypes}">
                                <label>
                                    <input type="radio" name="lessonTypeId" value="${type.lessonTypeId}">
                                    ${type.lessonTypeName}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="form-group row d-flex align-items-center">
                        <div class="col-md-7">
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <div class="col-md-9">
                                        <label for="lessonName">Lesson Name:</label>
                                        <input type="text" id="lessonName" name="lessonName" required>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="lessonOrder">Lesson Order:</label>
                                        <input type="text" id="lessonOrder" name="lessonOrder" class="form-control" required>
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <!-- Package Selection -->
                                    <div class="col-md-6">
                                        <label for="packageId">Package:</label>
                                        <div class="search-container">
                                            <input type="text" id="packageSearch" placeholder="Search package..." class="form-control mb-2">
                                            <select id="packageId" name="packageId" size="${packages.size()}" class="form-control" required>
                                                <c:forEach var="pkg" items="${packages}">
                                                    <option value="${pkg.packageId}">${pkg.packageName}</option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>
                                    <!-- Lesson Order Input -->

                                    <div class="col-md-6">
                                        <label for="userId">Expert:</label>
                                        <div class="search-container">
                                            <c:choose>
                                                <c:when test="${sessionScope.account.roleId == 3}">
                                                    <input type="text" id="userSearch" class="form-control mb-2" value="${sessionScope.account.fullname}" readonly>
                                                    <input type="hidden" id="userId" name="userId" value="${sessionScope.account.userId}">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="text" id="userSearch" placeholder="Search expert..." class="form-control mb-2">
                                                    <select id="userId" name="userId" size="${users.size()}" class="form-control">
                                                        <c:forEach var="user" items="${users}">
                                                            <option value="${user.userId}">${user.fullname}</option>
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>

                                </div>
                            </div>


                            <div class="form-group hidden" id="youtubeLinkContainer">
                                <label for="youtubeLink">YouTube Link:</label>
                                <div class="d-flex">
                                    <input type="url" id="youtubeLink" name="youtubeLink" class="form-control">
                                    <button type="button" id="btnPreview" class="btn-preview">Preview</button>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <div class="col-md-4">
                                        <label for="topicId">Topic:</label>
                                        <select id="topicId" name="topicId" required>
                                            <c:forEach var="topic" items="${lessonTopics}">
                                                <option value="${topic.lessonTopicId}">${topic.lessonTopicName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="status">Status:</label>
                                        <select id="status" name="status" required>
                                            <option value="true">Active</option>
                                            <option value="false">Inactive</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4" id="quizContainer">
                                        <label for="quizId">Quiz:</label>
                                        <div class="search-container">
                                            <input type="text" id="quizSearch" placeholder="Search quiz..." class="form-control mb-2">
                                            <select id="quizId" name="quizId" size="${quizList.size()}" class="form-control" required>
                                                <c:forEach var="quiz" items="${quizList}">
                                                    <option value="${quiz.quizId}">${quiz.quizName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div id="youtubePreview" class="youtube-preview"></div>
                        </div>  
                    </div>

                    <div class="form-group hidden" id="htmlContentContainer">
                        <label for="htmlContent">HTML Content:</label>
                        <textarea id="htmlContent" name="htmlContent" rows="5" cols="50"></textarea>
                    </div>
                    <a href="subject-lesson?packageId=${param.packageId}&subjectId=${param.subjectId}" class="btn btn-secondary" onclick="return confirmReturn();">Back</a>                    
                    <button type="button" onclick="if (confirmClear())
                                clearForm();" class="btn btn-secondary ml-2">Clear</button>
                    <button type="button" onclick="openModal();" class="btn btn-info ml-2" style="margin-left: 70%">Note</button>
                    <input type="submit" value="Add Lesson" class="btn btn-success" onclick="return confirmAddnew();">
                </div>               
            </form>
        </div>

        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h3 style="text-align: center">Notes on Adding a New Lesson</h3><br>
                <ul>
                    <li>All fields marked with * are required.</li>
                    <li>You can only choose existing Packages, Quizzes, and Experts.</li>
                    <li>Each lesson type has different form fields:</li>
                    <ul>
                        <li>Subject Topic: Only requires basic information</li>
                        <li>Video: Requires a valid YouTube link</li>
                        <li>Quiz: Requires selecting an existing quiz</li>
                    </ul>
                    <li>Ensure the YouTube link is valid before submitting.</li>
                    <li>HTML content should be properly formatted.</li>
                </ul>
            </div>
        </div>


    </body>
</html>
<%@include file="footer.jsp" %>

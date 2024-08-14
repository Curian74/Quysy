<%@page import="java.util.List"%>
<%@page import="Model.Lesson"%>
<%@page import="Model.LessonTopic"%>
<%@page import="Model.LessonType"%>
<%@page import="Model.Package"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lesson Details</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="ckeditor/ckeditor.js"></script>
        <link rel="stylesheet" href="resources/css/lesson_details.css">
        <script>
            $(document).ready(function () {
                function loadYouTubeVideo() {
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
                }
                if ($('input[name="lessonTypeId"]:checked').val() == '2') {
                    loadYouTubeVideo();
                }
                $('#btnPreview').click(loadYouTubeVideo);

                function toggleFields() {
                    var lessonType = $('input[name="lessonTypeId"]:checked').val();
                    if (lessonType == '1') {
                        $('#youtubeLinkContainer, #youtubePreview, #quizContainer, #htmlContentContainer').addClass('hidden');
                        $('#youtubeLinkContainer, #youtubePreview, #quizContainer').find('input, select, textarea').prop('disabled', true);
                        if (CKEDITOR.instances.htmlContent) {
                            CKEDITOR.instances.htmlContent.setReadOnly(true);
                        }
                    } else if (lessonType == '2') {
                        $('#youtubeLinkContainer, #youtubePreview, #htmlContentContainer').removeClass('hidden');
                        $('#youtubeLinkContainer, #youtubePreview, #htmlContentContainer').find('input, select, textarea').prop('disabled', false);
                        $('#quizContainer').addClass('hidden').find('input, select, textarea').prop('disabled', true);
                        if (CKEDITOR.instances.htmlContent) {
                            CKEDITOR.instances.htmlContent.setReadOnly(false);
                        }
                    } else if (lessonType == '3') {
                        $('#youtubeLinkContainer, #youtubePreview').addClass('hidden').find('input, select, textarea').prop('disabled', true);
                        $('#quizContainer, #htmlContentContainer').removeClass('hidden').find('input, select, textarea').prop('disabled', false);
                        if (CKEDITOR.instances.htmlContent) {
                            CKEDITOR.instances.htmlContent.setReadOnly(false);
                        }
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
                        options += '<option value="${order}" ${lesson.lessonOrder == order ? "selected" : ""}>${order}</option>';
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

                function previewYouTubeLink() {
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
                }

                $('input[name="lessonTypeId"]').change(function () {
                    toggleFields();
                    updateOrderField();
                    updateTopicOptions();
                    if (CKEDITOR.instances.htmlContent) {
                        CKEDITOR.instances.htmlContent.updateElement();
                    }
                });

                $('#packageSearch, #quizSearch, #userSearch').on('input', function () {
                    var filter = $(this).val().toLowerCase().trim();
                    var targetId = this.id.replace('Search', 'Id');
                    if (filter) {
                        $('#' + targetId).show();
                    } else {
                        $('#' + targetId).hide();
                    }
                    $('#' + targetId + ' option').each(function () {
                        var text = $(this).text().toLowerCase();
                        $(this).toggle(text.indexOf(filter) > -1);
                    });
                });
                toggleFields();
                updateOrderField();
                updateTopicOptions();
                $('#packageSearch, #quizSearch, #userSearch').on('click', function (e) {
                    var targetId = this.id.replace('Search', 'Id');
                    $('#' + targetId).show();
                    e.stopPropagation();
                });

                $(document).on('click', function (e) {
                    if (!$(e.target).closest('.search-container').length) {
                        $('#packageId, #quizId, #userId').hide();
                    }
                });

                $('#packageId, #quizId, #userId').on('change', function () {
                    var searchId = this.id.replace('Id', 'Search');
                    $('#' + searchId).val($('#' + this.id + ' option:selected').text());
                    $(this).hide();
                });

                $('#btnPreview').click(previewYouTubeLink);
                $('#userId').on('change', function () {
                    var selectedText = $('#userId option:selected').text().trim();
                    $('#userSearch').val(selectedText);
                    $('#userId').hide();
                });
                $('#packageSearch, #quizSearch, #userSearch').on('focus', function (e) {
                    e.preventDefault();
                    var scrollPosition = window.pageYOffset;
                    window.scrollTo(0, scrollPosition);
                });
                $(document).on('click', function (e) {
                    if (!$(e.target).closest('.search-container').length) {
                        $('.search-container select').hide();
                    }
                });
                CKEDITOR.replace('htmlContent', {
                    filebrowserUploadUrl: '${pageContext.request.contextPath}/lesson-details',
                    filebrowserUploadMethod: 'form'
                });

                toggleFields();

                var currentYoutubeLink = '${lesson.youtubeLink}';
                if (currentYoutubeLink) {
                    var videoId = currentYoutubeLink.split('v=')[1];
                    var ampersandPosition = videoId.indexOf('&');
                    if (ampersandPosition != -1) {
                        videoId = videoId.substring(0, ampersandPosition);
                    }
                    var embedUrl = 'https://www.youtube.com/embed/' + videoId;
                    var iframe = '<iframe src="' + embedUrl + '" frameborder="0" allowfullscreen></iframe>';
                    $('#youtubePreview').html(iframe).show();
                }

                var currentExpertId = '${lesson.updatedBy}';
                var currentExpertName = '${lesson.updatedByName}';
                $('#userId').val(currentExpertId);
                $('#userSearch').val(currentExpertName);
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

            function confirmUpdate() {
                return confirm("Are you sure you want to update this lesson");
            }
            function confirmReturn() {
                return confirm("Are you sure you want to return to subject lesson: ${subject.subjectName}");
            }
            function openSameOrderModal() {
                document.getElementById("sameOrderModal").style.display = "block";
            }

            function closeSameOrderModal() {
                document.getElementById("sameOrderModal").style.display = "none";
            }

            window.onclick = function (event) {
                var modal = document.getElementById("sameOrderModal");
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
            function openPreviewModal() {
                var modal = document.getElementById("previewModal");
                var lessonName = document.getElementById("lessonName").value;
                var lessonTypeId = document.querySelector('input[name="lessonTypeId"]:checked').value;
                var lessonType = document.querySelector('input[name="lessonTypeId"]:checked').nextSibling.textContent.trim();
                var lessonTopic = document.getElementById("topicId").options[document.getElementById("topicId").selectedIndex].text;
                var youtubeLink = document.getElementById("youtubeLink").value;
                var htmlContent = CKEDITOR.instances.htmlContent.getData();
                var lastUpdatedBy = document.getElementById("userSearch").value;

                document.getElementById("previewLessonName").textContent = lessonName;
                document.getElementById("previewLessonType").textContent = lessonType;
                document.getElementById("previewLessonTopic").textContent = lessonTopic;
                document.getElementById("previewLastUpdatedBy").textContent = lastUpdatedBy;

                var videoContainer = document.getElementById("previewVideoContainer");
                var htmlContainer = document.getElementById("previewHtmlContainer");
                var quizContainer = document.getElementById("previewQuizContainer");

                // Hide both containers initially
                videoContainer.style.display = "none";
                htmlContainer.style.display = "none";

                if (lessonTypeId === "1") {
                } else if (lessonTypeId === "2") {
                    // Video: Show the video
                    videoContainer.style.display = "block";
                    var videoId = youtubeLink.split('v=')[1];
                    var ampersandPosition = videoId.indexOf('&');
                    if (ampersandPosition != -1) {
                        videoId = videoId.substring(0, ampersandPosition);
                    }
                    var embedUrl = 'https://www.youtube.com/embed/' + videoId;

                    document.getElementById("previewHtmlContent").innerHTML = htmlContent;
                    document.getElementById("previewVideo").innerHTML = '<iframe width="560" height="315" src="' + embedUrl + '" frameborder="0" allowfullscreen></iframe>';
                    if (htmlContent.trim() !== "") {
                        htmlContainer.style.display = "block";
                        document.getElementById("previewHtmlContent").innerHTML = htmlContent;
                    }
                } else if (lessonTypeId === "3") {
                    // Quiz: Show HTML content if available
                    if (htmlContent.trim() !== "") {
                        htmlContainer.style.display = "block";
                        document.getElementById("previewHtmlContent").innerHTML = htmlContent;
                        var quizId = document.getElementById("quizId").value;
                        document.getElementById("previewQuizLink").href = "quiz-details?quizId=" + quizId;
                        document.getElementById("previewQuizLink").textContent = "Go to Quiz";
                    }
                }

                modal.style.display = "block";
            }

            function closePreviewModal() {
                document.getElementById("previewModal").style.display = "none";
            }

            window.onclick = function (event) {
                var modal = document.getElementById("previewModal");
                if (event.target == modal) {
                    modal.style.display = "none";
                }
            }
            document.getElementById('lessonForm').addEventListener('submit', function (e) {
                var lessonName = document.getElementById('lessonName').value.trim();
                var lessonType = document.querySelector('input[name="lessonTypeId"]:checked').value;
                var youtubeLink = document.getElementById('youtubeLink').value.trim();
                var htmlContent = CKEDITOR.instances.htmlContent.getData().trim();

                if (lessonName === '') {
                    e.preventDefault();
                    alert('Lesson name cannot be empty or contain only spaces');
                    return;
                }

                if (lessonName !== lessonName.trim()) {
                    e.preventDefault();
                    alert('Lesson name cannot have spaces before the first letter');
                    return;
                }

                if (lessonType === '2') {
                    if (youtubeLink === '') {
                        e.preventDefault();
                        alert('YouTube link cannot be empty for video lessons');
                        return;
                    }

                    if (htmlContent === '') {
                        e.preventDefault();
                        alert('HTML content cannot be empty for video lessons');
                        return;
                    }
                }
            });
            function updateOrderField() {
                var lessonType = $('input[name="lessonTypeId"]:checked').val();
                var $orderField = $('#lessonOrder');

                if (lessonType == '1') {
                    $orderField.replaceWith('<input type="text" id="lessonOrder" name="lessonOrder" value="${lesson.lessonOrder}" readonly>');
                } else {
                    var options = '<select id="lessonOrder" name="lessonOrder">';
            <c:forEach items="${existingOrders}" var="order">
                    options += '<option value="${order}" ${lesson.lessonOrder == order ? "selected" : ""}>${order}</option>';
            </c:forEach>
                    options += '</select>';
                    $orderField.replaceWith(options);
                }
            }
        </script>
    </head>
    <body>
        <div class="form-container">
            <h2>Lesson Details for Lesson: <em>${lesson.lessonName}</em></h2>
            <h4>Lesson ID: ${lesson.lessonId}</h4>
            <c:if test="${not empty sessionScope.successMessage}">
                <div class="alert alert-success" role="alert">
                    ${sessionScope.successMessage}
                </div>
                <% session.removeAttribute("successMessage"); %>
            </c:if>
            <% if (request.getAttribute("errorMessage") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("errorMessage") %>
            </div>
            <% } %>
            <form action="lesson-details" method="post">
                <input type="hidden" name="lessonId" value="${lesson.lessonId}">
                <input type="hidden" name="subjectId" value="${lesson.subjectId}">
                <div class="col-12">
                    <div class="form-group row d-flex align-items-center">
                        <div class="col-md-7">
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <div class="col-md-9">
                                        <label for="lessonName">Lesson Name:</label>
                                        <input type="text" id="lessonName" name="lessonName" value="${lesson.lessonName}" required>
                                    </div>
                                    <div class="col-md-3">
                                        <label for="lessonOrder">Lesson Order:</label>
                                        <input type="text" id="lessonOrder" name="lessonOrder" value="${lesson.lessonOrder}" required class="form-control">
                                    </div>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <!-- Package Selection -->
                                    <div class="col-md-6">
                                        <label for="packageId">Package:</label>
                                        <div class="search-container">
                                            <input type="text" id="packageSearch" placeholder="Search package..." value="<c:forEach var='pkg' items='${packages}'><c:if test='${pkg.packageId == lesson.packageId}'>${pkg.packageName}</c:if></c:forEach>" class="form-control mb-2">
                                            <select id="packageId" name="packageId" size="${packages.size()}" required class="form-control">
                                                <c:forEach var="pkg" items="${packages}">
                                                    <option value="${pkg.packageId}" ${pkg.packageId == lesson.packageId ? 'selected' : ''}>${pkg.packageName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>

                                    </div>
                                    <div class="col-md-6">
                                        <label for="userId">Updated by</label>
                                        <div class="search-container">
                                            <c:choose>
                                                <c:when test="${sessionScope.account.roleId == 3}">
                                                    <input type="text" id="userSearch" class="form-control mb-2" value="${sessionScope.account.fullname}"  readonly>
                                                    <input type="hidden" id="userId" name="userId" value="${sessionScope.account.userId}">
                                                </c:when>
                                                <c:otherwise>
                                                    <input type="text" id="userSearch" placeholder="Search expert..." class="form-control mb-2" value="${lesson.updatedByName}">
                                                    <select id="userId" name="userId" size="${users.size()}" class="form-control">
                                                        <c:forEach var="user" items="${users}">
                                                            <option value="${user.userId}" ${user.userId == lesson.updatedBy ? 'selected' : ''}>
                                                                ${user.fullname}
                                                            </option>                                                        
                                                        </c:forEach>
                                                    </select>
                                                </c:otherwise>
                                            </c:choose>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <label>Lesson Type:</label>
                                <div class="d-flex align-items-center">
                                    <div class="radio-group">
                                        <c:forEach var="type" items="${lessonTypes}">
                                            <label class="mr-3">
                                                <input type="radio" name="lessonTypeId" value="${type.lessonTypeId}" ${type.lessonTypeId == lesson.lessonTypeId ? 'checked' : ''}>
                                                ${type.lessonTypeName}
                                            </label>
                                        </c:forEach>
                                    </div>
                                    <c:if test="${lesson.lessonTypeId == 1}">
                                        <button type="button" class="btn btn-info btn-sm ml-3" onclick="openSameOrderModal()">View Same Order Lessons</button>
                                    </c:if>
                                </div>
                            </div>

                            <div class="form-group hidden" id="youtubeLinkContainer">
                                <label for="youtubeLink">YouTube Link:</label>
                                <div class="d-flex">
                                    <input type="url" id="youtubeLink" name="youtubeLink" value="${lesson.youtubeLink}" class="form-control">
                                    <button type="button" id="btnPreview" class="btn-preview">Preview</button>
                                </div>
                            </div>
                            <div class="col-12">
                                <div class="form-group row d-flex align-items-center">
                                    <div class="col-md-4">
                                        <label for="topicId">Topic:</label>
                                        <select id="topicId" name="topicId" required>
                                            <c:forEach var="topic" items="${lessonTopics}">
                                                <option value="${topic.lessonTopicId}" ${topic.lessonTopicId == lesson.topicId ? 'selected' : ''}>
                                                    ${topic.lessonTopicName}
                                                </option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-4">
                                        <label for="status">Status:</label>
                                        <select id="status" name="status" required>
                                            <option value="true" ${lesson.status ? 'selected' : ''}>Active</option>
                                            <option value="false" ${!lesson.status ? 'selected' : ''}>Inactive</option>
                                        </select>
                                    </div>
                                    <div class="col-md-4" id="quizContainer">
                                        <label for="quizId">Quiz:</label>
                                        <div class="search-container">
                                            <input type="text" id="quizSearch" placeholder="Search quiz..." class="form-control mb-2" value="${lesson.quizName}">
                                            <select id="quizId" name="quizId" size="${quizList.size()}" class="form-control" required>                                              
                                                <c:forEach var="quiz" items="${quizList}" >
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

                    <div class="form-group" id="htmlContentContainer">
                        <label for="htmlContent">Content:</label>
                        <textarea id="htmlContent" name="htmlContent" rows="10" cols="80">${lesson.htmlContent}</textarea>
                    </div>
                    <div class="form-group submit-button">
                        <a href="subject-lesson?subjectId=${lesson.subjectId}&packageId=${lesson.packageId}" class="btn btn-secondary" onclick="return confirmReturn();">Back to Lessons</a>
                        <a href="lesson-details?lessonId=${lesson.lessonId}" class="btn btn-outline-secondary" onclick="return confirmClear();">Clear</a>
                        <button type="button" class="btn btn-info" onclick="openPreviewModal()">Preview</button>

                        <input type="submit" style="margin-left: 60%;" class="btn btn-primary" onclick="return confirmUpdate();"     value="Update" >
                    </div>
                </div>
            </form>

            <c:if test="${not empty errorMessage}">
                <p style="color: red;">${errorMessage}</p>
            </c:if>
        </div>
        <div id="sameOrderModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeSameOrderModal()">&times;</span>
                <h3>Lessons with the Same Order</h3>
                <table id="sameOrderTable">
                    <thead>
                        <tr>
                            <th>Lesson ID</th>
                            <th>Lesson Name</th>
                            <th>Lesson Type</th>
                            <th>Lesson Topic</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="sameOrderLesson" items="${sameOrderLessons}">
                            <tr>
                                <td>${sameOrderLesson.lessonId}</td>
                                <td>${sameOrderLesson.lessonName}</td>
                                <td>${sameOrderLesson.lessonTypeName}</td>
                                <td>${sameOrderLesson.topicName}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h3 style="text-align: center">Notes on Updating a Lesson</h3><br>
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
        <div id="previewModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closePreviewModal()">&times;</span>
                <h2  style="text-align: center" id="previewLessonName"></h2>
                <p style="text-align: center"><strong>Lesson Type:</strong> <span id="previewLessonType"></span></p>
                <p style="text-align: center"><strong>Lesson Topic:</strong> <span id="previewLessonTopic"></span></p>
                <div id="previewVideoContainer" style="display: none;">
                    <h3 style="text-align: center">Video Content:</h3>
                    <div style="text-align: center" id="previewVideo"></div>
                </div>
                <div id="previewQuizContainer" style="display: none;">
                    <h3>Quiz:</h3>
                    <a id="previewQuizLink" href="#" target="_blank">Go to Quiz</a>
                </div>
                <div id="previewHtmlContainer" style="display: none;">
                    <h3>Content:</h3>
                    <div id="previewHtmlContent"></div>
                </div>
                <br>
                <p style="position: absolute; bottom: 10px; right: 10px;"><strong>Last Updated By:</strong> <span id="previewLastUpdatedBy"></span></p>
            </div>
        </div>
    </body>
</html>
<%@ include file="footer.jsp" %>

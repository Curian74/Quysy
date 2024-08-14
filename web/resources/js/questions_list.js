$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var selectedSubjectId = urlParams.get("subjectId");
    var selectedLessonId = urlParams.get("lessonId");
    var selectedDimensionId = urlParams.get("dimensionId");
    var selectedLevelId = urlParams.get("levelId");
    var currentPage = urlParams.get("page");

    // Function to update filter URL and apply filters
    function updateFilterUrl() {
        var url = "question-list";
        var queryParams = [];

        if (selectedSubjectId !== null) {
            queryParams.push("subjectId=" + selectedSubjectId);
        }
        if (selectedLessonId !== null) {
            queryParams.push("lessonId=" + selectedLessonId);
        }
        if (selectedDimensionId !== null) {
            queryParams.push("dimensionId=" + selectedDimensionId);
        }
        if (selectedLevelId !== null) {
            queryParams.push("levelId=" + selectedLevelId);
        }
        if (currentPage !== null) {
            queryParams.push("page=" + currentPage);
        }

        // Build the final URL with query parameters
        if (queryParams.length > 0) {
            url += "?" + queryParams.join("&");
        }

        console.log("Filter Updating URL:", url);
        window.location.href = url;
    }

    // Event handlers for subject, lesson, dimension, and level filters
    $(".filter-button").click(function (e) {
        e.preventDefault();
        var filterType = $(this).data("filter-type");
        var filterId = $(this).data("filter-id");

        if (filterType === 'subject') {
            selectedSubjectId = filterId;
        } else if (filterType === 'lesson') {
            selectedLessonId = filterId;
        } else if (filterType === 'dimension') {
            selectedDimensionId = filterId;
        } else if (filterType === 'level') {
            selectedLevelId = filterId;
        }

        updateFilterUrl();
    });

    // Select the current filters in the dropdowns
    if (selectedSubjectId) {
        $('#subjectFilterButton').text($('.dropdown-item[data-subject-id="' + selectedSubjectId + '"]').text());
    }
    if (selectedLessonId) {
        $('#lessonFilterButton').text($('.dropdown-item[data-lesson-id="' + selectedLessonId + '"]').text());
    }
    if (selectedDimensionId) {
        $('#dimensionFilterButton').text($('.dropdown-item[data-dimension-id="' + selectedDimensionId + '"]').text());
    }
    if (selectedLevelId) {
        $('#levelFilterButton').text($('.dropdown-item[data-level-id="' + selectedLevelId + '"]').text());
    }

    // Function
    function toggleQuestionStatus(questionId, status) {
        var url = "toggle-question-status";
        var queryParams = [];

        if (questionId !== null) {
            queryParams.push("questionId=" + questionId);
        }
        if (selectedSubjectId !== null) {
            queryParams.push("subjectId=" + selectedSubjectId);
        }
        if (selectedLessonId !== null) {
            queryParams.push("lessonId=" + selectedLessonId);
        }
        if (selectedDimensionId !== null) {
            queryParams.push("dimensionId=" + selectedDimensionId);
        }
        if (selectedLevelId !== null) {
            queryParams.push("levelId=" + selectedLevelId);
        }
        if (currentPage !== null) {
            queryParams.push("page=" + currentPage);
        }
        if (status !== null) {
            queryParams.push("status=" + status);
        }

        // Build the final URL with query parameters
        if (queryParams.length > 0) {
            url += "?" + queryParams.join("&");
        }

        console.log("Toggle Status Updating URL:", url);
        window.location.href = url;
    }

    $(".toggle-status-button").click(function (e) {
        e.preventDefault();
        var questionId = $(this).data("question-id");
        var status = !$(this).data("status");

        currentPage = table.page();

        toggleQuestionStatus(questionId, status);
    });

    // Initialize DataTable with row grouping by subject and disable ordering on action column
    var table = $('#questionList').DataTable({
        rowGroup: {
            dataSrc: 2 // Grouping the DataTable row by subject (3rd column - index 2)
        },
        columnDefs: [{
                orderable: false,
                targets: 9 // Disable ordering on the action column (10th column - index 9)
            }],
        pageLength: 10,
        displayStart: currentPage * 10
    });

});
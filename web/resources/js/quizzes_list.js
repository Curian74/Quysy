$(document).ready(function () {
    $('#subjectList').select2({
    });
    $('#quizzes').select2({
    });
    $('#creatorList').select2({
    });
});
function clearFilters() {
    // Reset all select elements
    $('#quizzes').val(null).trigger('change');
    $('#subjectList').val(null).trigger('change');
    $('#creatorList').val(null).trigger('change');

    // Uncheck all checkboxes
    $('input[type=radio]').prop('checked', false);

    // Clear any text inputs if present
    $('input[type=text]').val('');
}
$('#quizList').DataTable();
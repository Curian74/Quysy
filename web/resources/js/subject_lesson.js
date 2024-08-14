
$(document).ready(function () {
    $('#lessonFilter').select2({
        placeholder: 'Select a lesson',
        allowClear: true,
        matcher: matchCustom
    });

    $('#lessonFilter').on('change', function () {
        var selectedOrder = $(this).val();
        var packageId = '${param.packageId}';
        window.location.href = 'subject-lesson?packageId=' + packageId + '&subjectId=${subject.subjectId}&filterOrder=' + selectedOrder + '&page=1';
    });


    function matchCustom(params, data) {
        if ($.trim(params.term) == '') {
            return data;
        }

        if (typeof data.text == 'undefined') {
            return null;
        }

        var searchTerm = $.trim(params.term).toLowerCase();
        var text = data.text.toLowerCase();

        if (text.includes(searchTerm)) {
            var modifiedData = $.extend({}, data, true);
            return modifiedData;
        }

        return null;
    }
});
function confirmChange() {
    return confirm("Are you sure you want to save changes?");
}
function selectCategory(categoryId, categoryName) {
    document.getElementById('categories').value = categoryId;
    document.getElementById('categoryDropdown').textContent = categoryName;
    document.getElementById('subjectForm').submit();
}
function selectStatus(statusValue, statusText) {
    document.getElementById('status').value = statusValue;
    document.getElementById('statusDropdown').textContent = statusText;
    document.getElementById('subjectForm').submit();
}
function selectFeatured(value, text) {
    document.getElementById('featuredDropdown').textContent = text;
    document.getElementById('featured').value = value;
    document.getElementById('subjectForm').submit();
}
function selectOrder(value, text) {
    document.getElementById('orderByDropdown').textContent = text;

    document.getElementById('orderBy').value = value;
    document.getElementById('subjectForm').submit();
}
document.addEventListener('DOMContentLoaded', function () {
    var selectedOption = document.querySelector('#categories option:checked');
    if (selectedOption) {
        document.getElementById('categoryDropdown').textContent = selectedOption.textContent;
    }
    var selectedStatusOption = document.querySelector('#status option:checked');
    if (selectedStatusOption) {
        document.getElementById('statusDropdown').textContent = selectedStatusOption.textContent;
    }
    var selectedOption = document.querySelector('#featured option:checked');
    if (selectedOption) {
        document.getElementById('featuredDropdown').textContent = selectedOption.textContent;
    }
    var selectedOrderBy = document.querySelector('#orderBy option:checked');
    if (selectedOrderBy) {
        document.getElementById('orderByDropdown').textContent = selectedOrderBy.textContent;
    }
});
document.addEventListener('DOMContentLoaded', function () {
    // Check if we need to show the modal
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('showModal')) {
        $('#registerModal').modal('show');
    }
});
function selectCategory(categoryId, categoryName) {
    document.getElementById('categories').value = categoryId;
    document.getElementById('categoryDropdown').textContent = categoryName;
    document.getElementById('subjectForm').submit();
}
// Set the dropdown button text on page load based on the selected category
document.addEventListener('DOMContentLoaded', function () {
    var selectedOption = document.querySelector('#categories option:checked');
    if (selectedOption) {
        document.getElementById('categoryDropdown').textContent = selectedOption.textContent;
    }
});
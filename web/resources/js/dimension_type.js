function selectSort(sortValue, sortText) {
    const form = document.getElementById('searchSortForm');
    document.getElementById('sortBy').value = sortValue;
    form.submit();
}

document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('clearSearch').addEventListener('click', function() {
        document.getElementById('searchName').value = '';
        document.getElementById('searchSortForm').submit();
    });
});


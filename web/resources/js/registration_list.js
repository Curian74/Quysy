function autoSubmit() {
    trimInput();
    document.getElementById("searchForm").submit();
}

function trimAndSubmit() {
    trimInput();
    document.getElementById("searchForm").submit();
}

function trimInput() {
    var searchInput = document.getElementById("subject");
    var searchInput1 = document.getElementById("email");
    searchInput.value = searchInput.value.trim().replace(/\s+/g, ' ');
    searchInput1.value = searchInput.value.trim().replace(/\s+/g, ' ');
}

function clearFilters() {
    document.getElementById('status').selectedIndex = 0;
    document.getElementById('registrationTimeFrom').value = '';
    document.getElementById('registrationTimeTo').value = '';
    document.getElementById('searchForm').submit();
}

$('#registrationList').DataTable();

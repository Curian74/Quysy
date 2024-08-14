function autoSubmit() {
    trimInput();
    document.getElementById("searchForm").submit();
}

function trimAndSubmit() {
    trimInput();
    document.getElementById("searchForm").submit();
}

function trimInput() {
    var searchInput = document.getElementById("searchInput");
    searchInput.value = searchInput.value.trim().replace(/\s+/g, ' ');
}

$('#userList').DataTable();
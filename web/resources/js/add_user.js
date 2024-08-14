function confirmAdd() {
    return confirm("Are you sure you want to add this user?");
}

function confirmRedirect() {
    setTimeout(function () {
        window.location.href = "user-list";
    }, 1000);
}

document.addEventListener("DOMContentLoaded", function () {
    const successMessage = document.querySelector(".alert-success");
    if (successMessage) {
        successMessage.addEventListener("click", confirmRedirect);
    }
});

function removePlaceholder() {
    var selectElement = document.getElementById('role_id');
    if (selectElement.value === "") {
        selectElement.options[0].disabled = true;
    }
}
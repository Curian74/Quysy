$(document).ready(function () {
    var urlParams = new URLSearchParams(window.location.search);
    var selectedCategoryId = urlParams.get("categoryId");

    // Event handler for category filter
    $(".filter-button").click(function (e) {
        e.preventDefault();
        selectedCategoryId = $(this).data("category-id");
        var url = "my-registrations";

        if (selectedCategoryId) {
            url += "?categoryId=" + selectedCategoryId;
        }

        console.log("Filter Updating URL: ", url);
        window.location.href = url;
    });

    // Select the current filter in the dropdown
    if (selectedCategoryId) {
        $('#categoryFilterButton').text($('.dropdown-item[data-category-id="' + selectedCategoryId + '"]').text());
    }

    // Event handler to cancel registration
    $(".toggle-status-button").click(function (e) {
        e.preventDefault();
        var url = "toggle-registration-status?registrationId=" + $(this).data("registration-id");

        currentPage = table.page();

        console.log("Toggle Status Updating URL: ", url);
        window.location.href = url;
    });

    // Handle edit links click
    $(document).on('click', '#edit-link', function (e) {
        e.preventDefault();
        const registrationId = $(this).data('registration-id');
        console.log('Clicked registration ID:', registrationId);
        fetchRegistrationData(registrationId);
    });

    // Show modal if URL param 'showModal' exists
    const myRegistrationUrlParams = new URLSearchParams(window.location.search);
    if (myRegistrationUrlParams.has('showModal')) {
        $('#editRegistrationModal').modal('show');
    }

    function fetchRegistrationData(registrationId) {
        $.ajax({
            url: 'display-edit-registration',
            method: 'POST',
            data: { registrationId: registrationId },
            success: function (data) {
                console.log(data);
                $('#registrationId').val(data.registrationId);
                $('#fullname').val(data.fullName);
                $('#email').val(data.email);
                $('#mobile').val(data.mobile);
                $('#gender').val(data.gender);
                $('#note').val(data.note);
                populatePackages(data.subjectId);
                $('#editRegistrationModal').modal('show');
            },
            error: function (xhr, status, error) {
                console.error('AJAX Error: ' + status + error);
                $('#editError').html('<p>An error occurred</p>');
            }
        });
    }

    function populatePackages(subjectId) {
        $.ajax({
            url: 'display-edit-registration',
            method: 'GET',
            data: { subjectId: subjectId },
            success: function (packages) {
                console.log(packages);
                const packageContainer = $('#package');
                packageContainer.empty();
                packages.forEach(pkg => {
                    const packageRadio = $('<input>', {
                        type: 'radio',
                        name: 'packageId',
                        value: pkg.id,
                        required: true
                    });
                    const packageLabel = $('<label>').text(pkg.name);
                    packageContainer.append(packageRadio).append(packageLabel);
                });
            },
            error: function (xhr, status, error) {
                console.error('AJAX Error: ' + status + error);
                $('#editError').html('<p>An error occurred while fetching packages</p>');
            }
        });
    }

    // Initialize DataTables
    var table = $('#registrationList').DataTable();

    // Custom search box functionality
    $('#searchBox').on('keyup change', function () {
        $('#registrationList').DataTable().search($(this).val()).draw();
    });
});
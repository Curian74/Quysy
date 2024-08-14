$(document).ready(function () {
    $("#subject").change(function () {
        var subjectId = $(this).val();
        $.ajax({
            url: 'display-add-registration',
            type: 'POST',
            data: {subjectId: subjectId},
            success: function (data) {
                var packageSelect = $("#package");
                packageSelect.empty();
                $.each(data, function (key, value) {
                    packageSelect.append('<option value="' + value.packageId + '">' + value.packageName + '</option>');
                });

                // Trigger change event to load the prices for the first package
                packageSelect.change();
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error: " + status + error);
            }
        });
    });

    $("#package").change(function () {
        var packageId = $(this).val();
        $.ajax({
            url: 'display-add-registration',
            type: 'POST',
            data: {packageId: packageId},
            success: function (data) {
                // Update prices
                $("#listPrice").val(data.listPrice * 1000 + " VND");
                $("#salePrice").val(data.salePrice * 1000 + " VND");
            },
            error: function (xhr, status, error) {
                console.error("AJAX Error: " + status + error);
            }
        });
    });
});

function confirmChanges() {
    return confirm("Are you sure you want to save changes?");
}
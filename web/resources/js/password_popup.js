function showChangePasswordPopup() {
    document.getElementById('changePassword').style.display = 'flex';
}

function closeChangePasswordPopup() {
    document.getElementById('changePassword').style.display = 'none';
    // Clear the message and form when closing the popup
    $('#changePasswordMessage').text('');
    $('#changePasswordForm')[0].reset();
}

$(document).ready(function () {
    // Handle change password form submission
    $('#changePasswordForm').submit(function (event) {
        event.preventDefault();
        var formData = $(this).serialize();
        $.ajax({
            type: 'POST',
            url: 'change-password',
            data: formData,
            dataType: 'json',
            success: function (response) {
                if (response.success) {
                    $('#changePasswordMessage').text(response.message).css('color', 'green');
                    $('#changePasswordForm')[0].reset();
                } else {
                    $('#changePasswordMessage').text(response.message).css('color', 'red');
                }
            },
            error: function () {
                $('#changePasswordMessage').text('Error occurred while processing your request.').css('color', 'red');
            }
        });
    });

    $('.popup-overlay .close-button').click(function () {
        closeChangePasswordPopup();
    });
});
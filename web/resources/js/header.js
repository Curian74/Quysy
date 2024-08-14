// Switch between modals
$('#switchToRegister').click(function () {
    $('#loginModal').modal('hide');
    $('#registerAccountModal').modal('show');
});

$('#switchToLogin').click(function () {
    $('#registerAccountModal').modal('hide');
    $('#loginModal').modal('show');
});

// Check for confirmation message
$(document).ready(function () {
    const headerUrlParams = new URLSearchParams(window.location.search);
    const showLoginModal = headerUrlParams.get('showLoginModal');
    const headerConfirmed = headerUrlParams.get('confirmed');

    if (showLoginModal === 'true') {
        if (headerConfirmed === 'true') {
            $('#loginMessage').text('Your email has been confirmed successfully!');
            $('#loginMessage').show();
        } else if (headerConfirmed === 'false') {
            $('#loginError').text('Email confirmation failed. Please try again.');
            $('#loginError').show();
        }

        $('#loginModal').modal('show');
    }
    $('#profileModal').on('show.bs.modal', function () {
        var modal = $(this);
        modal.find('#profileContent').html('<div class="text-center my-3">Loading...</div>');
        $.ajax({
            url: 'profile',
            method: 'GET',
            success: function (data) {
                modal.find('#profileContent').html(data);
            },
            error: function () {
                modal.find('#profileContent').html('<div class="alert alert-danger">Failed to load profile content. Please try again.</div>');
            }
        });
    });
    $('#loginForm').on('submit', function (event) {
        event.preventDefault();

        const xhr = new XMLHttpRequest();
        xhr.open('POST', this.action, true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.onload = function () {
            const response = xhr.responseText;
            $('#loginError').hide();
            $('#loginMessage').hide();
            if (response.includes('error')) {
                $('#loginError').text(response.split(':')[1].trim());
                $('#loginError').show();
            } else {
                window.location.href = "home";
            }
        };
        xhr.send(new URLSearchParams(new FormData(this)).toString());
    });
});
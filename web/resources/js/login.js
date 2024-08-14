document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', this.action, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        const response = xhr.responseText;
        document.getElementById('loginError').style.display = 'none';
        document.getElementById('loginMessage').style.display = 'none';
        if (response.includes('error')) {
            document.getElementById('loginError').textContent = response.split(':')[1].trim();
            document.getElementById('loginError').style.display = 'block';
        } else {
            window.location.href = "home";
        }
    };
    xhr.send(new URLSearchParams(new FormData(this)).toString());
});

// Check for confirmation message
const loginUrlParams = new URLSearchParams(window.location.search);
const loginConfirmed = loginUrlParams.get('confirmed');
if (loginConfirmed === 'true') {
    document.getElementById('loginMessage').textContent = 'Your email has been confirmed successfully!';
    document.getElementById('loginMessage').style.display = 'block';
} else if (loginConfirmed === 'false') {
    document.getElementById('loginError').textContent = 'Email confirmation failed. Please try again.';
    document.getElementById('loginError').style.display = 'block';
}
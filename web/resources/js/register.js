document.getElementById('registerForm').addEventListener('submit', function (event) {
    event.preventDefault();

    const xhr = new XMLHttpRequest();
    xhr.open('POST', this.action, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        const response = xhr.responseText;
        document.getElementById('registerError').style.display = 'none';
        document.getElementById('registerMessage').style.display = 'none';
        if (response.includes('error')) {
            document.getElementById('registerError').textContent = response.split(':')[1].trim();
            document.getElementById('registerError').style.display = 'block';
        } else {
            document.getElementById('registerMessage').textContent = response.split(':')[1].trim();
            document.getElementById('registerMessage').style.display = 'block';
        }
    };
    xhr.send(new URLSearchParams(new FormData(this)).toString());
});
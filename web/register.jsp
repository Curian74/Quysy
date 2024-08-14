<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="vendors/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <form id="registerForm" action="user-register" method="post">
                <h1 class="mb-4 fw-bold text-center">Register</h1>
                <div id="registerError" class="alert alert-danger" style="display:none;"></div>
                <div id="registerMessage" class="alert alert-success" style="display:none;"></div>
                <div class="mb-3">
                    <label for="emailRegister" class="form-label">Email</label>
                    <input type="email" id="emailRegister" class="form-control mt-1" name="email" placeholder="Email address" required>
                </div>
                <div class="mb-3">
                    <label for="fullnameRegister" class="form-label">Full Name</label>
                    <input type="text" id="fullnameRegister" class="form-control mt-1" name="fullname" placeholder="Full Name" required>
                </div>
                <div class="mb-3">
                    <label for="genderRegister" class="form-label">Gender</label>
                    <select id="genderRegister" class="form-control mt-1" name="gender" required>
                        <option value="true">Male</option>
                        <option value="false">Female</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="mobileRegister" class="form-label">Mobile</label>
                    <input type="text" id="mobileRegister" class="form-control mt-1" name="mobile" placeholder="Mobile" required>
                </div>
                <button type="submit" class="btn btn-lg btn-primary w-100 mt-2">Register</button>
            </form>
        </div>
        <script src="resources/js/register.js"></script>
    </body>
</html>

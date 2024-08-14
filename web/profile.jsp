<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>User Profile</title>
        <link href="resources/css/profile.css" rel="stylesheet"/>
        <link href="resources/css/password_popup.css" rel="stylesheet"/>  
    </head>
    <body>
        <div class="profile-section">
            <h2>Account Setting</h2><br>
            <button onclick="showEditPopup()">Profile</button><br>
            <br><button onclick="showChangePasswordPopup()">Change Password</button>
        </div>

        <!-- Edit profile pop-up -->
        <div class="popup-overlay" id="editProfile">
            <div class="popup-content">
                <span class="close-button" onclick="closeEditPopup()">&times;</span>
                <h2>User Profile</h2>
                <form action="edit-profile" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
                    <div class="profile-section">
                        <label for="avatar" id="avatarLabel">
                            <div class="avatar-container">
                                <img class="avatar" id="avatarPreviewEdit" src="${user.avatar}" alt="Avatar">
                            </div>
                            <input type="file" id="avatar" name="avatar" accept="image/*" style="display: none;" onchange="previewImage(this);" disabled>
                        </label>
                    </div>
                    <label for="fullname">Full Name:</label>
                    <input type="text" id="fullname" name="fullname" value="${user.fullname}" readonly><br>
                    <label for="gender">Gender:</label>
                    <div id="gender-display">${user.gender ? 'Male' : 'Female'}</div>
                    <div id="gender-options" class="gender-container" style="display: none;">
                        <div class="gender-option">
                            <input type="radio" id="male" name="gender" value="male" ${user.gender ? 'checked' : ''} disabled>
                            <label for="male">Male</label>
                        </div>
                        <div class="gender-option">
                            <input type="radio" id="female" name="gender" value="female" ${!user.gender ? 'checked' : ''} disabled>
                            <label for="female">Female</label>
                        </div>
                    </div>
                    <label for="email">Email (View Only):</label>
                    <input type="email" id="email" name="email" value="${user.email}" readonly><br>
                    <label for="mobile">Mobile:</label>
                    <input type="text" id="mobile" name="mobile" value="${user.mobile}" readonly><br>
                    <input type="hidden" id="userId" name="userId" value="${user.userId}">
                    <div>
                        <button type="button" id="editButton" onclick="toggleEdit()">Edit</button>
                        <button type="submit" id="saveButton" style="display: none;">Save</button>
                        <button type="button" id="cancelEditButton" onclick="cancelEdit()" style="display: none;">Cancel Edit</button>
                        <button type="button" id="clearButton" onclick="clearChanges()" style="display: none;">Clear Changes</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Change password pop-up -->
        <div class="popup-overlay" id="changePassword">
            <div class="popup-content">
                <span class="close-button" onclick="closeChangePasswordPopup()">&times;</span>     
                <h2>Change Password</h2>
                <div id="changePasswordMessage"></div>
                <!-- Form to change password -->
                <form id="changePasswordForm">
                    <label for="oldPassword">Old Password:</label>
                    <input value="${curPass}" type="password" name="oldPassword" required><br>

                    <label for="newPassword">New Password:</label>
                    <input value="${newPass}" type="password" name="newPassword" required><br>

                    <c:if test="${sessionScope.sucMessage == null && message == null}">
                        <p style="font-size:13px; font-style: italic">Password length must be at least 8 characters</p>
                    </c:if>

                    <label for="confirmPassword">Confirm Password:</label>
                    <input value="${cfPass}" type="password" name="confirmPassword" required><br>

                    <input type="submit" value="Save">
                </form>
            </div>
        </div>

        <script src ="resources/js/profile.js"></script>

        <script src ="resources/js/password_popup.js"></script>

    </body>
</html>
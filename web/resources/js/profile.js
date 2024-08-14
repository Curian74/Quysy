let originalFormData = {};

function toggleEdit() {
    var form = document.querySelector('#editProfile form');
    var inputs = form.querySelectorAll('input:not([type="hidden"])');
    var editButton = document.getElementById('editButton');
    var saveButton = document.getElementById('saveButton');
    var cancelEditButton = document.getElementById('cancelEditButton');
    var clearButton = document.getElementById('clearButton');
    var genderDisplay = document.getElementById('gender-display');
    var genderOptions = document.getElementById('gender-options');

    if (editButton.textContent === 'Edit') {
        // Switch to edit mode
        inputs.forEach(function (input) {
            if (input.id !== 'email') {
                input.removeAttribute('readonly');
                input.removeAttribute('disabled');
                originalFormData[input.name] = input.value;
            }
        });
        editButton.style.display = 'none';
        saveButton.style.display = 'inline-block';
        cancelEditButton.style.display = 'inline-block';
        clearButton.style.display = 'inline-block';
        genderDisplay.style.display = 'none';
        genderOptions.style.display = 'block';
        originalFormData['gender'] = document.querySelector('input[name="gender"]:checked').value;
        originalFormData['avatar'] = document.getElementById('avatarPreviewEdit').src;
    }
}

function cancelEdit() {
    resetForm();
    toggleEditMode(false);
}

function clearChanges() {
    var form = document.querySelector('#editProfile form');
    var inputs = form.querySelectorAll('input:not([type="hidden"]):not([readonly]):not([disabled])');
    inputs.forEach(function (input) {
        if (input.type === 'file') {
            input.value = '';
        } else if (input.type === 'radio') {
            input.checked = input.value === originalFormData['gender'];
        } else {
            input.value = originalFormData[input.name] || '';
        }
    });
    document.getElementById('avatarPreviewEdit').src = originalFormData['avatar'];
}

function resetForm() {
    var form = document.querySelector('#editProfile form');
    form.reset();
    document.getElementById('gender-display').textContent = document.getElementById('male').checked ? 'Male' : 'Female';
}

function toggleEditMode(isEditing) {
    var form = document.querySelector('#editProfile form');
    var inputs = form.querySelectorAll('input:not([type="hidden"])');
    var editButton = document.getElementById('editButton');
    var saveButton = document.getElementById('saveButton');
    var cancelEditButton = document.getElementById('cancelEditButton');
    var clearButton = document.getElementById('clearButton');
    var genderDisplay = document.getElementById('gender-display');
    var genderOptions = document.getElementById('gender-options');

    inputs.forEach(function (input) {
        if (input.id !== 'email') {
            input.readOnly = !isEditing;
            input.disabled = !isEditing;
        }
    });

    editButton.style.display = isEditing ? 'none' : 'inline-block';
    saveButton.style.display = isEditing ? 'inline-block' : 'none';
    cancelEditButton.style.display = isEditing ? 'inline-block' : 'none';
    clearButton.style.display = isEditing ? 'inline-block' : 'none';
    genderDisplay.style.display = isEditing ? 'none' : 'block';
    genderOptions.style.display = isEditing ? 'block' : 'none';
}

function closeEditPopup() {
    document.getElementById('editProfile').style.display = 'none';
    cancelEdit();
}
function showEditPopup() {
    document.getElementById('editProfile').style.display = 'block';
}

function previewImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            document.getElementById('avatarPreviewEdit').src = e.target.result;
        };

        reader.readAsDataURL(input.files[0]);
    }
}

document.addEventListener('DOMContentLoaded', function () {
    document.querySelectorAll('.popup-overlay').forEach(function (overlay) {
        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) {
                overlay.style.display = 'none';
            }
        });
    });
});
function validateForm() {
    const fullname = document.getElementById('fullname').value;
    const mobile = document.getElementById('mobile').value;

    // Check if fields are empty, contain only spaces, or have leading/trailing spaces
    if (!fullname.trim() || !mobile.trim() || fullname !== fullname.trim() || mobile !== mobile.trim()) {
        alert('Fields cannot be empty, contain only spaces, or have leading/trailing spaces.');
        return false;
    }

    // Check if fullname contains numbers or symbols
    if (!/^[a-zA-Z]+(\s[a-zA-Z]+)*$/.test(fullname)) {
        alert('Full name should contain only letters and single spaces between words.');
        return false;
    }

    // Check if mobile contains non-numeric characters
    if (!/^\d+$/.test(mobile)) {
        alert('Mobile number should contain only digits.');
        return false;
    }

    return true;
}
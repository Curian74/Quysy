function openListPopup() {
    document.getElementById("addSliderPopup").style.display = "block";
}
function closeListPopup() {
    document.getElementById("addSliderPopup").style.display = "none";
}
function cancelForm() {
    // Reset form fields
    document.getElementById("thumbnailPreview").src = "${slider.sliderThumbnail}";
    document.getElementById("thumbnail").value = "";
    document.getElementById("title").value = "";
    document.getElementById("backlink").value = "";
    document.getElementById("status").value = "true"; // Defaulting to 'Show'
    document.getElementById("notes").value = "";
    closeListPopup();
}

function previewImage() {
    var file = document.getElementById('thumbnail').files[0];
    var reader = new FileReader();
    reader.onloadend = function () {
        document.getElementById('thumbnailPreview').src = reader.result;
    };
    if (file) {
        reader.readAsDataURL(file);
        document.getElementById('thumbnailFileName').innerText = file.name;
    } else {
        document.getElementById('thumbnailPreview').src = "";
        document.getElementById('thumbnailFileName').innerText = 'No file selected';
    }
}

function toggleEditMode(enable) {
    const formElements = document.querySelectorAll('.form-group input, .form-group textarea, .form-group select');

    formElements.forEach(element => {
        element.disabled = !enable;
        if (element.type !== 'file') {
            element.readOnly = !enable;
        }
    });

    document.getElementById('editButton').style.display = enable ? 'none' : 'inline-block';
    document.getElementById('cancelButton').style.display = enable ? 'inline-block' : 'none';
    document.getElementById('submitButton').style.display = enable ? 'inline-block' : 'none';

    if (!enable) {
        resetForm();
    }
}
window.onload = function () {
    toggleEditMode(false);
};
function selectStatus(statusValue, statusText) {
    document.getElementById('status').value = statusValue;
    document.getElementById('statusDropdown').textContent = statusText;
    document.getElementById('slidersForm').submit();
}

document.addEventListener('DOMContentLoaded', function () {
    var selectedStatusOption = document.querySelector('#status option:checked');
    if (selectedStatusOption) {
        document.getElementById('statusDropdown').textContent = selectedStatusOption.textContent;
    }
});
function validateForm() {
    var title = document.getElementById('title').value;
    var backlink = document.getElementById('backlink').value;
    var notes = document.getElementById('notes').value;
    var thumbnail = document.getElementById('thumbnail');
    var isEditMode = document.getElementById('editMode') !== null;

    if (title.trimStart() !== title || backlink.trimStart() !== backlink || notes.trimStart() !== notes) {
        alert('Fields cannot start with spaces.');
        return false;
    }
    
    if (title.trim() === '' || backlink.trim() === '') {
        alert('Title and backlink are required and cannot be empty.');
        return false;
    }
    
    // Check for image only in add new mode
    if (!isEditMode && thumbnail && thumbnail.files.length === 0) {
        alert('Please select an image.');
        return false;
    }
    
    return true;
}
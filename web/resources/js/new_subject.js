$(document).ready(function () {
    $('#categorySelect').select2({
        placeholder: 'Select a category'
    });

    $('#ownerSelect').select2({
        placeholder: 'Select the owner'
    });

    $('#subjectList').select2({
    });


});



function validateInput(input) {
    var isValid = input.value.trim().length > 0;
    if (isValid) {
        input.classList.remove('is-invalid');
    } else {
        input.classList.add('is-invalid');
    }
}

function confirmAddSubject(event) {
    // Get the form elements
    const subjectName = document.getElementById('name');
    const category = document.getElementById('categorySelect');
    const tagLine = document.querySelector('input[name="tag"]');
    const brief = document.querySelector('input[name="brief"]');
    const description = document.querySelector('textarea[name="description"]');
    const owner = document.getElementById('ownerSelect');
    const thumb = document.getElementById('thumbnail');

    // Check if any required field is empty
    if (!subjectName.value.trim() || !category.value.trim() || !tagLine.value.trim() || !brief.value.trim() || !description.value.trim() || !owner.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
        return;
    }   
    if(!thumb.value.trim())   {
        alert('Please upload an image');
        event.preventDefault(); // Prevent form submission
        return;
    }

    else {
        // Show confirmation alert if all fields are filled
        if (!confirm("Are you sure you want to add the subject?")) {
            event.preventDefault();
        }
    }
}


function previewImage(event) {

    const file = event.target.files[0];
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

    if (!allowedTypes.includes(file.type)) {
        alert('Please upload a valid image file (JPEG, PNG, GIF)');
        event.target.value = '';
        return;
    }
    var input = event.target;
    var preview = document.getElementById('imagePreview');

    var reader = new FileReader();
    reader.onload = function () {
        preview.src = reader.result;
        preview.style.display = 'block'; // Show image preview
    };
    reader.readAsDataURL(input.files[0]);
}


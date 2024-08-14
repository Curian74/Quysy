document.addEventListener('DOMContentLoaded', function () {
    const tabs = document.querySelectorAll('.tab');
    const tabContents = document.querySelectorAll('.tab-content');

    tabs.forEach((tab, index) => {
        tab.addEventListener('click', function () {
            tabs.forEach(t => t.classList.remove('active'));
            tabContents.forEach(tc => tc.style.display = 'none');

            tab.classList.add('active');
            tabContents[index].style.display = 'block';
        });
    });

    // Initialize the first tab as active
    tabs[0].classList.add('active');
    tabContents[0].style.display = 'block';
});


function previewImage(event) {
    const file = event.target.files[0];
    const allowedTypes = ['image/jpeg', 'image/png', 'image/gif'];

    if (!allowedTypes.includes(file.type)) {
        alert('Please upload a valid image file (JPEG, PNG, GIF)');
        event.target.value = '';
        return;
    }

    const reader = new FileReader();
    reader.onload = function () {
        const output = document.getElementById('subjectImage');
        output.src = reader.result;
    };
    reader.readAsDataURL(file);
}

function confirmDeleteDimension(event) {
    if (!confirm("Are you sure you want to delete this dimension?")) {
        event.preventDefault();
    }
}

function confirmUpdateDimension(event) {
    // Get the form elements
    const dimensionName = document.querySelector('select[name="dimensionName"]');
    const dimType = document.querySelector('select[name="dimType"]');

    // Check if any required field is empty
    if (!dimensionName.value.trim() || !dimType.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
    } else {
        // Show confirmation alert if all fields are filled
        if (!confirm("Are you sure you want to update the dimension?")) {
            event.preventDefault();
        }
    }
}

function confirmAddPackage(event) {
    // Get the form elements
    const packageName = document.querySelector('input[name="packageName"]');
    const duration = document.querySelector('input[name="duration"]');
    const listPrice = document.querySelector('input[name="listP"]');
    const sellPrice = document.querySelector('input[name="sellP"]');

    // Check if any required field is empty or has invalid value
    if (!packageName.value.trim() || !duration.value.trim() || !listPrice.value.trim() || !sellPrice.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
    } else if (parseFloat(listPrice.value) <= 0 || parseFloat(sellPrice.value) <= 0) {
        alert('Prices must be positive and non-zero.');
        event.preventDefault(); // Prevent form submission
    } else if (parseInt(duration.value) <= 0) {
        alert('Duration value must be greater than zero');
        event.preventDefault(); // Prevent form submission
    } else {
        // Show confirmation alert if all fields are filled and valid
        if (!confirm("Are you sure you want to update the package?")) {
            event.preventDefault();
        }
    }
}

function confirmUpdatePackage(event) {
    // Get the form elements
    const packageName = document.querySelector('input[name="packageName"]');
    const duration = document.querySelector('input[name="duration"]');
    const listPrice = document.querySelector('input[name="listP"]');
    const sellPrice = document.querySelector('input[name="sellP"]');

    // Check if any required field is empty or has invalid value
    if (!packageName.value.trim() || !duration.value.trim() || !listPrice.value.trim() || !sellPrice.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
    } else if (parseFloat(listPrice.value) <= 0 || parseFloat(sellPrice.value) <= 0) {
        alert('Prices must be positive and non-zero.');
        event.preventDefault(); // Prevent form submission
    } else if (parseInt(duration.value) <= 0) {
        alert('Duration value must be greater than zero');
        event.preventDefault(); // Prevent form submission
    } else {
        // Show confirmation alert if all fields are filled and valid
        if (!confirm("Are you sure you want to update the package?")) {
            event.preventDefault();
        }
    }
}
function confirmAddDimension(event) {
    if (confirm("Are you sure you want to add this dimension?")) {
        document.getElementById("addDimensionForm").submit();
    } else {
        event.preventDefault(); // Prevent default action (form submission)
    }
}


function confirmUpdateGeneral(event) {
    // Get the form elements
    const subjectName = document.getElementById('subjectName');
    const category = document.getElementById('category');
    const tagLine = document.querySelector('textarea[name="tagLine"]');
    const brief = document.querySelector('textarea[name="brief"]');
    const description = document.querySelector('textarea[name="description"]');

    // Check if any required field is empty
    if (!subjectName.value.trim() || !category.value.trim() || !tagLine.value.trim() || !brief.value.trim() || !description.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
    } else {
        // Show confirmation alert if all fields are filled
        if (!confirm("Are you sure you want to update the subject information?")) {
            event.preventDefault();
        }
    }
}

function confirmActivatePackage(event) {
    if (!confirm("Are you sure you want to activate this package?")) {
        event.preventDefault();
    }
}

function confirmDeactivatePackage(event) {
    if (!confirm("Are you sure you want to deactivate this package?")) {
        event.preventDefault();
    }
}




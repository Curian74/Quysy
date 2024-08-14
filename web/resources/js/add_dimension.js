$(document).ready(function () {

    $('#dimensionList').select2({
    });

});

function validateInputDimName(input) {
    var isValid = input.value.trim().length > 0;
    var helpText = document.getElementById("dimensionNameHelp");
    var desText = document.getElementById("descriptionHelp");
    
    if (isValid) {
        input.classList.remove('is-invalid');
        helpText.style.display = 'block'; 
        desText.style.display = 'block'; 
    } else {
        input.classList.add('is-invalid');
        helpText.style.display = 'none'; 
        desText.style.display = 'none'; 
    }
}

function confirmAddDimName(event) {
    // Get the form elements
    const dimensionName = document.getElementById('dimensionName');
    const description = document.getElementById('nameDescription');

    // Check if any required field is empty
    if (!dimensionName.value.trim() || !description.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); // Prevent form submission
    } else {
        // Show confirmation alert if all fields are filled
        if (!confirm("Are you sure you want to add the dimension?")) {
            event.preventDefault(); // Prevent form submission
        }
    }
}


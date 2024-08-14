$('#settingsList').DataTable();


document.addEventListener('DOMContentLoaded', function () {
    // Check if there's a flag to open the modal
    var openModal = document.body.dataset.openModal === 'true';

    if (openModal) {
        var settingsModal = new bootstrap.Modal(document.getElementById('settingsModal'));
        settingsModal.show();
    }

    // Clear the success message after it's been shown
    var successAlert = document.querySelector('.alert-success');
    if (successAlert) {
        setTimeout(function () {
            successAlert.style.display = 'none';
        }, 5000); // Hide after 5 seconds
    }
});

document.querySelector('input[name="settingName"]').addEventListener('input', function () {
    validateInput(this);
});
document.querySelector('input[name="des"]').addEventListener('input', function () {
    validateInput(this);
});

function confirmDeleteSettings(event) {
    if (!confirm("Are you sure you want to delete this settings?")) {
        event.preventDefault();
    }
}

function confirmActive(event) {
    if (!confirm("Are you sure you want to activate this settings?")) {
        event.preventDefault();
    }
}

function confirmDeactive(event) {
    if (!confirm("Are you sure you want to deactive this settings?")) {
        event.preventDefault();
    }
}

function confirmAddSettings(event) {
    const name = document.querySelector('input[name="settingName"]');
    const description = document.querySelector('input[name="des"]');

    if (!name.value.trim() || !description.value.trim()) {
        alert('Please fill in all required fields.');
        event.preventDefault(); 
        return;
    }

    if (!confirm("Are you sure you want to add the settings?")) {
        event.preventDefault();
    }
}
function showTab(tabName) {
    // Remove 'active' class from all tabs and tab content
    var tabs = document.querySelectorAll('.tabs .tab');
    var contents = document.querySelectorAll('.content > div');

    tabs.forEach(function (tab) {
        tab.classList.remove('active');
    });

    contents.forEach(function (content) {
        content.classList.remove('active');
    });

    // Add 'active' class to the clicked tab and corresponding content
    document.querySelector('.tabs .tab[onclick="showTab(\'' + tabName + '\')"]').classList.add('active');
    document.getElementById(tabName).classList.add('active');
}

// Initializing the event listeners for buttons
document.addEventListener('DOMContentLoaded', (event) => {
    document.querySelectorAll('.btn-add').forEach(function (button) {
        button.addEventListener('click', function () {
            this.style.display = 'none';
            this.nextElementSibling.style.display = 'inline-block';
        });
    });

    document.querySelectorAll('.btn-remove').forEach(function (button) {
        button.addEventListener('click', function () {
            this.style.display = 'none';
            this.previousElementSibling.style.display = 'inline-block';
        });
    });

    $('#subjectList').select2();
});

document.addEventListener('DOMContentLoaded', (event) => {
    const overviewForm = document.querySelector('#overview form');
    const settingsForm = document.querySelector('#setting form');

    overviewForm.addEventListener('submit', (e) => {
        if (!validateOverviewForm()) {
            e.preventDefault();
        }
    });

    settingsForm.addEventListener('submit', (e) => {
        if (!validateSettingsForm()) {
            e.preventDefault();
        }
    });

    function validateOverviewForm() {
        const quizName = document.getElementById('name').value;
        const subjectName = document.getElementById('subjectList').value;
        const level = document.querySelector('input[name="level"]:checked');
        const duration = document.getElementById('duration').value;

        if (!quizName || !subjectName || !level || !duration) {
            alert('Please fill in all required fields in the Overview and Settings form.');
            return false;
        }
        return true;
    }

    function validateSettingsForm() {
        const totalQuestions = document.getElementById('totalQuestions').value;
        const basicQuestions = document.getElementById('basicQuestions').value;
        const intermediateQuestions = document.getElementById('intermediateQuestions').value;
        const advancedQuestions = document.getElementById('advancedQuestions').value;

        if (!totalQuestions || !basicQuestions || !intermediateQuestions || !advancedQuestions) {
            alert('Please fill in all required fields in the Overview and Settings form.');
            return false;
        }
        return true;
    }
});
function validateQuestions() {
    const totalQuestions = parseInt(document.getElementById('totalQuestions').value);
    const basicQuestions = parseInt(document.getElementById('basicQuestions').value) || 0;
    const intermediateQuestions = parseInt(document.getElementById('intermediateQuestions').value) || 0;
    const advancedQuestions = parseInt(document.getElementById('advancedQuestions').value) || 0;

    const sumOfQuestions = basicQuestions + intermediateQuestions + advancedQuestions;

    if (sumOfQuestions !== totalQuestions) {
        alert('The sum of Basic, Intermediate, and Advanced questions must equal the total number of questions.');
        return false;
    }
    return true;
}

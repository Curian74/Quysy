//Naviage pages
function navigate(questionIndex) {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set('questionIndex', questionIndex);
    window.location.search = urlParams.toString();
}

//display time
window.onload = function() {
    const quizTypeId = parseInt(document.getElementById('quizTypeId').value, 10);
    const durationElement = document.getElementById('quizDuration');
    const timerElement = document.getElementById('timer');
    let duration = parseInt(durationElement.value, 10) * 60; // Convert minutes to seconds

    function formatTime(seconds) {
        let minutes = Math.floor(seconds / 60);
        let remainingSeconds = seconds % 60;
        remainingSeconds = remainingSeconds < 10 ? '0' + remainingSeconds : remainingSeconds;
        return minutes + ":" + remainingSeconds;
    }

    if (quizTypeId === 2) {
        // Countdown timer
        timerElement.innerHTML = formatTime(duration); // Initialize timer display
        let countdownInterval = setInterval(function() {
            duration--;
            timerElement.innerHTML = formatTime(duration);
            if (duration < 0) {
                clearInterval(countdownInterval);
                // Time's up logic here
                alert('Time is up!');
            }
        }, 1000);
    } else if (quizTypeId === 1) {
        // Stopwatch timer
        let seconds = 0;
        let minutes = 0;
        timerElement.innerHTML = "00:00"; // Initialize timer display
        let stopwatchInterval = setInterval(function() {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }
            let displaySeconds = seconds < 10 ? '0' + seconds : seconds;
            let displayMinutes = minutes < 10 ? '0' + minutes : minutes;
            timerElement.innerHTML = displayMinutes + ":" + displaySeconds;
        }, 1000);
    }
};

document.querySelector('.explain-btn').addEventListener('click', function () {
        document.querySelector('.popup').style.display = 'block';
        document.querySelector('.overlay').style.display = 'block';
    });

    document.querySelector('.close-btn').addEventListener('click', function () {
        document.querySelector('.popup').style.display = 'none';
        document.querySelector('.overlay').style.display = 'none';
    });

    document.querySelector('.overlay').addEventListener('click', function () {
        document.querySelector('.popup').style.display = 'none';
        document.querySelector('.overlay').style.display = 'none';
    });

//display correct Answer (ABCD)
document.addEventListener('DOMContentLoaded', function() {
    const peekButton = document.getElementById('peek');
    const correctAnswerElement = document.getElementById('correctAnswer');
    const correctAnswerSpan = document.querySelector('.correct-answer[data-correct-answer]');
    const overlay = document.querySelector('.overlay');
    const popup = document.querySelector('.popup');
    const closeButton = document.querySelector('.close-btn');

    if (peekButton && correctAnswerElement && correctAnswerSpan) {
        peekButton.addEventListener('click', function() {
            const correctAnswer = correctAnswerSpan.getAttribute('data-correct-answer');
            correctAnswerElement.textContent = correctAnswer;
            overlay.style.display = 'block';
            popup.style.display = 'block';
        });
    }

    if (closeButton) {
        closeButton.addEventListener('click', function() {
            overlay.style.display = 'none';
            popup.style.display = 'none';
        });
    }
});


document.querySelector('.review-btn').addEventListener('click', function () {
        document.querySelector('.popup-review').style.display = 'block';
        document.querySelector('.overlay-review').style.display = 'block';
    });

    document.querySelector('.close-btn-review').addEventListener('click', function () {
        document.querySelector('.popup-review').style.display = 'none';
        document.querySelector('.overlay-review').style.display = 'none';
    });

    document.querySelector('.overlay-review').addEventListener('click', function () {
        document.querySelector('.popup-review').style.display = 'none';
        document.querySelector('.overlay-review').style.display = 'none';
    });






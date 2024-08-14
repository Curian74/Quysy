document.addEventListener('DOMContentLoaded', function () {
    const explanationButton = document.querySelector('.explain-btn');
    const reviewButton = document.querySelector('.review-btn');
    const closeButton = document.querySelector('.close-btn');
    const closeReviewButton = document.querySelector('.close-btn-review');
    const overlay = document.querySelector('.overlay');
    const overlayReview = document.querySelector('.overlay-review');
    const popup = document.querySelector('.popup');
    const popupReview = document.querySelector('.popup-review');

    if (explanationButton) {
        explanationButton.addEventListener('click', function () {
            overlay.style.display = 'block';
            popup.style.display = 'block';
        });
    }

    if (reviewButton) {
        reviewButton.addEventListener('click', function () {
            overlayReview.style.display = 'block';
            popupReview.style.display = 'block';
        });
    }

    if (closeButton) {
        closeButton.addEventListener('click', function () {
            overlay.style.display = 'none';
            popup.style.display = 'none';
        });
    }

    if (closeReviewButton) {
        closeReviewButton.addEventListener('click', function () {
            overlayReview.style.display = 'none';
            popupReview.style.display = 'none';
        });
    }

    // Prevent overlay from closing the popup when clicking inside the popup content
    overlayReview.addEventListener('click', function (event) {
        if (event.target === overlayReview) {
            overlayReview.style.display = 'none';
            popupReview.style.display = 'none';
        }
    });

    popupReview.addEventListener('click', function (event) {
        event.stopPropagation();
    });
});

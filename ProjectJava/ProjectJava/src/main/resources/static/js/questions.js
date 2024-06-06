document.addEventListener("DOMContentLoaded", function() {
    let questions = []; // Store the list of questions
    let currentIndex = 0; // Current question index

    function fetchQuestionsAndStartQuiz() {
        // Replace the URL with your backend endpoint to fetch questions
        fetch('https://localhost:443/questions')
            .then(response => response.json())
            .then(data => {
            questions = data; // Store the received questions
            displayQuestion(currentIndex); // Start displaying questions
        })
            .catch(error => console.error('Error fetching questions:', error));
    }

    function displayQuestion(index) {
        const questionContainer = document.getElementById('questionContainer');
        questionContainer.innerHTML = `<p>${questions[index].text}</p>`;

        const answersContainer = document.getElementById('answersContainer');
        answersContainer.innerHTML = '';

        questions[index].answers.forEach(answer => {
            const label = document.createElement('label');
            label.innerHTML = `
                <input type="radio" name="answer" value="${answer.value}" required>
                ${answer.text}
            `;
            answersContainer.appendChild(label);
        });

        // Update navigation buttons based on the current question index
        updateNavigationButtons();
    }

    function updateNavigationButtons() {
        const prevButton = document.getElementById('prevButton');
        const nextButton = document.getElementById('nextButton');

        prevButton.disabled = currentIndex === 0; // Disable previous button on first question
        nextButton.disabled = currentIndex === questions.length - 1; // Disable next button on last question
    }

    function handlePrevious() {
        if (currentIndex > 0) {
            currentIndex--;
            displayQuestion(currentIndex);
        }
    }

    function handleNext() {
        if (currentIndex < questions.length - 1) {
            currentIndex++;
            displayQuestion(currentIndex);
        } else {
            // Redirect to the final question page or perform any other action
            window.location.href = 'final-question.html';
        }
    }

    function handleSubmit(event) {
        event.preventDefault(); // Prevent default form submission
        // Handle form submission
    }

    document.getElementById('questionForm').addEventListener('submit', handleSubmit);
    document.getElementById('prevButton').addEventListener('click', handlePrevious);
    document.getElementById('nextButton').addEventListener('click', handleNext);

    // Call the function to fetch questions and start the quiz
    fetchQuestionsAndStartQuiz();
});

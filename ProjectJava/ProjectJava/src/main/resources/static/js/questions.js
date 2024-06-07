document.addEventListener("DOMContentLoaded", function() {
    let currentIndex = 0;
    let quizId = 0;
    let selectedAnswers = {};
    let questions = [];

    function fetchQuestionsAndStartQuiz() {
        const urlParams = new URLSearchParams(window.location.search);
        quizId = urlParams.get('quizId');
        fetchQuestions(function() {
            displayQuestion(currentIndex);
        });
    }

    function fetchQuestions(callback) {
        fetch(`/quizzes/${quizId}/questions`)
            .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch questions');
            }
            return response.json();
        })
            .then(data => {
            questions = data;
            let count = 0;
            questions.forEach(question => {
                fetch(`/questions/${question.questionId}/answers`)
                    .then(response => {
                    if (!response.ok) {
                        throw new Error('Failed to fetch answers for question: ' + question.questionId);
                    }
                    return response.json();
                })
                    .then(answers => {
                    question.answers = answers;
                    count++;
                    if (count === questions.length) {
                        callback();
                    }
                })
                    .catch(error => console.error('Error fetching answers:', error));
            });
        })
            .catch(error => {
            console.error('Error fetching questions:', error);
        });
    }

    function displayQuestion(index) {
        const questionContainer = document.getElementById('questionContainer');
        const answersContainer = document.getElementById('answersContainer');

        if (index < 0 || index >= questions.length) {
            console.error('Invalid question index');
            return;
        }

        const currentQuestion = questions[index];

        if (!currentQuestion) {
            console.error('Current question is undefined');
            return;
        }

        questionContainer.textContent = currentQuestion.text;

        answersContainer.innerHTML = '';

        currentQuestion.answers.forEach(answer => {
            const label = document.createElement('label');
            const input = document.createElement('input');
            input.type = 'radio';
            input.name = 'answer';
            input.value = answer.text;

            if (selectedAnswers[currentQuestion.questionId] === answer.text) {
                input.checked = true;
            }

            input.required = true;
            label.appendChild(input);
            label.appendChild(document.createTextNode(answer.text));
            answersContainer.appendChild(label);
        });

        const previousButton = document.getElementById('previousButton');
        const nextButton = document.getElementById('nextButton');
        const finishButton = document.getElementById('finishButton');

        previousButton.disabled = index === 0;
        nextButton.disabled = index === questions.length - 1;

        if (index === questions.length - 1) {
            nextButton.textContent = 'Finish';
            finishButton.style.display = 'block';
        } else {
            nextButton.textContent = 'Next';
            finishButton.style.display = 'none';
        }
    }

    function handlePrevious() {
        currentIndex--;
        displayQuestion(currentIndex);
    }

    function handleNext() {
        // Save selected answer to session storage
        const selectedAnswer = document.querySelector('input[name="answer"]:checked').value;
        selectedAnswers[questions[currentIndex].questionId] = selectedAnswer;
        sessionStorage.selectedAnswers = JSON.stringify(selectedAnswers);

        currentIndex++;

        if (currentIndex === questions.length) {
            window.location.href = '/pages/scoreboard';
        } else {
            displayQuestion(currentIndex);
        }
    }


    const previousButton = document.getElementById('previousButton');
    const nextButton = document.getElementById('nextButton');

    previousButton.addEventListener('click', handlePrevious);
    nextButton.addEventListener('click', handleNext);

    fetchQuestionsAndStartQuiz();
});


function startQuiz() {
    const domainSelect = document.getElementById('domain');
    const selectedOption = domainSelect.options[domainSelect.selectedIndex];
    const selectedDomainId = selectedOption.value;
    console.log(selectedOption);
    if (selectedDomainId) {
        createQuiz(selectedDomainId, 10);
    } else {
        console.error('No domain selected');
    }
}

function createQuiz(domainId, numberOfQuestions) {
    fetch(`/quizzes?domainId=${domainId}&numberOfQuestions=${numberOfQuestions}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            fetchQuestionsAndStartQuiz(data.quizId);
            window.location.href = `question?quizId=${data.quizId}`;
    })
        .catch(error => console.error('Error creating quiz:', error));
}



document.addEventListener('DOMContentLoaded', function() {
    fetchDomains();
});

function fetchDomains() {
    const domainSelect = document.getElementById('domain');
    fetch('/domains')
        .then(response => response.json())
        .then(domains => {
        console.log('Domains:', domains);
        domains.forEach(domain => {
            const option = document.createElement('option');
            option.value = domain.domainId;
            option.textContent = domain.name;
            domainSelect.appendChild(option);
        });
    })
        .catch(error => console.error('Error fetching domains:', error));
}

function fetchQuestionsAndStartQuiz(quizId) {
    fetchQuestions(quizId)
        .then(questions => {
        if (questions.length > 0) {
            window.location.href = `question?quizId=${quizId}&questionIndex=0`;
        } else {
            console.error('No questions found for quiz:', quizId);
        }
    })
        .catch(error => console.error('Error fetching questions:', error));
}

function fetchQuestions(quizId) {
    return fetch(`/quizzes/${quizId}/questions`)
        .then(response => {
        if (!response.ok) {
            throw new Error('Failed to fetch questions');
        }
        return response.json();
    })
        .catch(error => {
        console.error('Error fetching questions:', error);
        return [];
    });
}


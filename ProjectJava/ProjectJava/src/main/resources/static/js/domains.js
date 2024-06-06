document.addEventListener("DOMContentLoaded", function() {
    const domainSelect = document.getElementById('domain');

    function startQuiz() {
        const domainSelect = document.getElementById('domain');
        const selectedDomainId = domainSelect.value;
        if (selectedDomainId) {
            createQuiz(selectedDomainId, 10); // can be adjustec
        } else {
            console.error('No domain selected');
        }
    }

    function createQuiz(domainId, numberOfQuestions) {
        fetch('/quizzes', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                domainId: domainId,
                numberOfQuestions: numberOfQuestions
            })
        })
            .then(response => response.json())
            .then(data => {
            window.location.href = `questions.html?quizId=${data.quizId}`;
        })
            .catch(error => console.error('Error creating quiz:', error));
    }


    function fetchDomains() {
        fetch('/domains')
            .then(response => response.json())
            .then(domains => {
            domains.forEach(domain => {
                const option = document.createElement('option');
                option.value = domain.domainId;
                option.textContent = domain.name;
                domainSelect.appendChild(option);
            });
        })
            .catch(error => console.error('Error fetching domains:', error));
    }

    fetchDomains();

    document.getElementById('domainForm').addEventListener('submit', function(event) {
        event.preventDefault();
        const selectedDomainId = domainSelect.value;
        if (selectedDomainId) {
            window.location.href = `question_page.html?domainId=${selectedDomainId}`;
        } else {
            console.error('No domain selected');
        }
    });
});
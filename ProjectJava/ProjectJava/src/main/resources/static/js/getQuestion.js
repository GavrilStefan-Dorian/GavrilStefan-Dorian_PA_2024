document.addEventListener("DOMContentLoaded", function() {
    function fetchQuestionAndUpdatePage(pageId) {
        fetch(`https://localhost:443/questions/${pageId}`)
            .then(response => response.json())
            .then(data => {
                const questionContainer = document.getElementById('questionContainer');
                questionContainer.innerHTML = `<p>${data.text}</p>`;

                const answersContainer = document.getElementById('answersContainer');
                answersContainer.innerHTML = '';

                fetch(`https://localhost:443/questions/${pageId}/answers`)
                    .then(response => response.json())
                    .then(options => {
                        if (options && Array.isArray(options)) { // Change here
                            options.forEach(option => {
                                const label = document.createElement('label');
                                label.innerHTML = `
                                    <input type="radio" name="answer" value="${option.text}" required>
                                    ${option.text}
                                `;
                                answersContainer.appendChild(label);
                            });
                        } else {
                            console.error('Options are missing or not in the expected format');
                        }
                    })
                    .catch(error => console.error('Error fetching answer options:', error));
            })
            .catch(error => console.error('Error fetching question:', error));
    }

    function getCurrentPageIndex() {
        const pageIndex = sessionStorage.getItem('currentPageIndex');
        return pageIndex ? parseInt(pageIndex) : 1;
    }

    function setCurrentPageIndex(index) {
        sessionStorage.setItem('currentPageIndex', index);
    }

    function handleSubmit(event) {
        event.preventDefault(); // Prevent default form submission
        const currentPageIndex = getCurrentPageIndex();
        const nextPageIndex = parseInt(currentPageIndex) + 1;
        setCurrentPageIndex(nextPageIndex);
        window.location.reload();
    }

    document.getElementById('questionForm').addEventListener('submit', handleSubmit);

    // Call the function when the page loads
    window.onload = function() {
        const currentPageIndex = getCurrentPageIndex();
        fetchQuestionAndUpdatePage(currentPageIndex);
    };
});

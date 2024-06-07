document.addEventListener("DOMContentLoaded", function() {
    const players = ['Player1', 'Player2', 'Player3', 'Player4', 'Player5'];

    function displayPlayers() {
        const playersList = document.getElementById('players');

        playersList.innerHTML = '';

        players.forEach(player => {
            const li = document.createElement('li');
            li.textContent = player;
            playersList.appendChild(li);
        });
    }

    function redirectToQuestionPage() {
        window.location.href = "question.html";
    }

    displayPlayers();

    const connectButton = document.getElementById('connectButton');
    connectButton.addEventListener('click', redirectToQuestionPage);
});

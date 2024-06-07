function calculateScore(selectedAnswers) {
    let score = 0;
    selectedAnswers.forEach((selectedAnswer, questionId) => {
        const correctAnswer = questions.find(question => question.questionId === questionId).answers.find(answer => answer.isCorrect);
        if (correctAnswer && correctAnswer.text === selectedAnswer) {
            score++;
        }
    });
    return score;
}

//
//const players = [
//    { username: 'User1', score: 100 },
//    { username: 'User2', score: 90 },
//    { username: 'User3', score: 85 },
//    { username: 'User4', score: 75 },
//    { username: 'User5', score: 65 }
//];
//
//function populateLeaderboard(players) {
//    const leaderboardBody = document.getElementById('leaderboard-body');
//
//    leaderboardBody.innerHTML = '';
//
//    players.sort((a, b) => b.score - a.score);
//
//    players.forEach((player, index) => {
//        const row = document.createElement('tr');
//
//        const rankCell = document.createElement('td');
//        rankCell.textContent = index + 1;
//        row.appendChild(rankCell);
//
//        const usernameCell = document.createElement('td');
//        usernameCell.textContent = player.username;
//        row.appendChild(usernameCell);
//
//        const scoreCell = document.createElement('td');
//        scoreCell.textContent = player.score;
//        row.appendChild(scoreCell);
//
//        leaderboardBody.appendChild(row);
//    });
//}


function populateLeaderboard(currentUser) {
    const leaderboardBody = document.getElementById('leaderboard-body');
    leaderboardBody.innerHTML = '';

    const row = document.createElement('tr');

    const rankCell = document.createElement('td');
    rankCell.textContent = 1;
    row.appendChild(rankCell);

    const usernameCell = document.createElement('td');
    usernameCell.textContent = currentUser.username;
    row.appendChild(usernameCell);

    const scoreCell = document.createElement('td');
    scoreCell.textContent = currentUser.score;
    row.appendChild(scoreCell);

    leaderboardBody.appendChild(row);
}
document.addEventListener("DOMContentLoaded", function() {
    const selectedAnswers = JSON.parse(sessionStorage.selectedAnswers || '{}');
    const score = calculateScore(selectedAnswers);
    const currentUser = { username: 'Your Username', score: score };
    populateLeaderboard(currentUser);
});
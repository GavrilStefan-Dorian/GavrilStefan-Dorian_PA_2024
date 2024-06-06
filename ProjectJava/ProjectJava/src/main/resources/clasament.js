const players = [
    { username: 'User1', score: 100 },
    { username: 'User2', score: 90 },
    { username: 'User3', score: 85 },
    { username: 'User4', score: 75 },
    { username: 'User5', score: 65 }
];

function populateLeaderboard(players) {
    const leaderboardBody = document.getElementById('leaderboard-body');
    
    leaderboardBody.innerHTML = '';

    players.sort((a, b) => b.score - a.score);

    players.forEach((player, index) => {
        const row = document.createElement('tr');
        
        const rankCell = document.createElement('td');
        rankCell.textContent = index + 1;
        row.appendChild(rankCell);

        const usernameCell = document.createElement('td');
        usernameCell.textContent = player.username;
        row.appendChild(usernameCell);

        const scoreCell = document.createElement('td');
        scoreCell.textContent = player.score;
        row.appendChild(scoreCell);

        leaderboardBody.appendChild(row);
    });
}

document.addEventListener('DOMContentLoaded', () => populateLeaderboard(players));

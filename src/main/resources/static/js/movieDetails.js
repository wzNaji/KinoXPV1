document.addEventListener('DOMContentLoaded', () => {
    const movieId = window.location.pathname.split("/")[2];  // Get movie ID from URL

    fetch(`/api/movies/${movieId}`)  // Fetch the specific movie details from the API
        .then(response => {
            if (!response.ok) {
                throw new Error('Movie not found');
            }
            return response.json();
        })
        .then(movie => {
            // Display the movie details dynamically
            const movieDetailsDiv = document.getElementById('movie-details');
            movieDetailsDiv.innerHTML = `
                <h2>${movie.title}</h2>
                <p>Genre: ${movie.genre}</p>
                <p>Duration: ${movie.duration} minutes</p>
                <p>Age Limit: ${movie.ageLimit}+</p>
            `;
        })
        .catch(error => {
            document.getElementById('movie-details').innerHTML = `<p>Error: ${error.message}</p>`;
        });
});

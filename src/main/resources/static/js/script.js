let allMovies = [];  // Global variabel for at gemme all movies

// Fetch movies from the backend
fetch('/api/movies')
    .then(response => {
        if (response.status === 204) {
            return [];  // no content returnerer tomt array for at undgå undefined error
        }
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(movies => {
        allMovies = movies;  // Gemmer movies i global array
        displayMovies(allMovies);  // Initial display
    })
    .catch(error => {
        document.getElementById("movies-container").innerHTML = `<p>Error: ${error.message}</p>`;
    });

// Function to display movies
function displayMovies(movies) {
    const container = document.getElementById("movies-container");
    container.innerHTML = "";  // Clear

    if (movies.length === 0) {
        container.innerHTML = "<p>No movies available.</p>";
    } else {
        movies.forEach(movie => {
            let movieElement = `
                <div class="movie">
                    <h2>${movie.title}</h2>
                    <p>Genre: ${movie.genre}</p>
                    <p>Duration: ${movie.duration} minutes</p>
                    <p>Age Limit: ${movie.ageLimit}+</p>
                </div>
            `;
            container.innerHTML += movieElement;
        });
    }
}

// Function: sort movies ud fra valgte kriterier. Se cases for valgmulighederne.
function sortMovies() {
    const sortOption = document.getElementById("sort-options").value;

    let sortedMovies = [...allMovies];  // Create a copy of the array

    switch (sortOption) {
        case 'title-asc':
            sortedMovies.sort((a, b) => a.title.localeCompare(b.title));
            break;
        case 'title-desc':
            sortedMovies.sort((a, b) => b.title.localeCompare(a.title));
            break;
        case 'age-asc':
            sortedMovies.sort((a, b) => a.ageLimit - b.ageLimit);
            break;
        case 'age-desc':
            sortedMovies.sort((a, b) => b.ageLimit - a.ageLimit);
            break;
    }

    displayMovies(sortedMovies);  // Display de sorterede movies
}

// Function: filter movies via search bar input
function filterMovies() {
    const searchTerm = document.getElementById("search-bar").value.toLowerCase();

    const filteredMovies = allMovies.filter(movie =>
        movie.title.toLowerCase().includes(searchTerm)
    );

    displayMovies(filteredMovies);  // Display the filtered movies
}

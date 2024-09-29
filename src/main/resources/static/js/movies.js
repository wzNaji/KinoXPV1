let allMovies = [];  // Global variable to store all movies
let uniqueGenres = [];  // Global variable to store unique genres

// Fetch movies from the backend
fetch('/api/movies')
    .then(response => {
        if (response.status === 204) {
            return [];  // Return empty array for no content
        }
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(movies => {
        allMovies = movies;  // Store movies in global array
        populateGenreDropdown(allMovies);  // Populate the genre filter dropdown
        displayMovies(allMovies);  // Initial display
    })
    .catch(error => {
        document.getElementById("movies-container").innerHTML = `<p>Error: ${error.message}</p>`;
    });

// Function to populate the genre filter dropdown
function populateGenreDropdown(movies) {
    const genreSet = new Set();  // Use a Set to store unique genres

    movies.forEach(movie => {
        genreSet.add(movie.genre);  // Add each movie's genre to the set
    });

    uniqueGenres = Array.from(genreSet);  // Convert the Set to an array
    const genreDropdown = document.getElementById("genre-filter");

    // Add options to the genre dropdown dynamically
    uniqueGenres.forEach(genre => {
        const option = document.createElement("option");
        option.value = genre;
        option.textContent = genre;
        genreDropdown.appendChild(option);
    });
}

// Function to display movies
function displayMovies(movies) {
    const container = document.getElementById("movies-container");
    container.innerHTML = "";  // Clear previous content

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

// Function to sort movies based on selected criteria
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

    displayMovies(sortedMovies);  // Display the sorted movies
}

// Function to filter movies by search input
function filterMovies() {
    const searchTerm = document.getElementById("search-bar").value.toLowerCase();

    const filteredMovies = allMovies.filter(movie =>
        movie.title.toLowerCase().includes(searchTerm)
    );

    displayMovies(filteredMovies);  // Display the filtered movies
}

// Function to filter movies by selected genre
function filterMoviesByGenre() {
    const selectedGenre = document.getElementById("genre-filter").value;

    // If 'all' is selected, show all movies
    if (selectedGenre === "all") {
        displayMovies(allMovies);
    } else {
        const filteredMovies = allMovies.filter(movie => movie.genre === selectedGenre);
        displayMovies(filteredMovies);
    }
}

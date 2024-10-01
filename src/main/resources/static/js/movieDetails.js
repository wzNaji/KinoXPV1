document.addEventListener('DOMContentLoaded', () => {
    const movieId = window.location.pathname.split("/")[2];  // Get movie ID from URL

    // Show loading indicators
    document.getElementById('movie-details').innerHTML = "<p>Loading movie details...</p>";
    document.getElementById('seating-chart').innerHTML = "<p>Loading seating chart...</p>";

    // Fetch the specific movie details from the API
    fetch(`/api/movies/${movieId}`)
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
                <p>Theater type: ${movie.theaterType}</p>
            `;
        })
        .catch(error => {
            document.getElementById('movie-details').innerHTML = `<p>Error: ${error.message}</p>`;
        });

    // Fetch the seating chart information from the API
    fetch(`/api/movies/${movieId}/seats`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Could not fetch seat data');
            }
            return response.json();
        })
        .then(seats => displaySeats(seats))
        .catch(error => {
            document.getElementById('seating-chart').innerHTML = `<p>Error: ${error.message}</p>`;
            console.error('Error fetching seats:', error);
        });

    // Function to display the seating chart
    function displaySeats(seats) {
        const seatingChart = document.getElementById('seating-chart');
        seatingChart.innerHTML = '';  // Clear previous chart

        seats.forEach(seat => {
            const seatElement = document.createElement('div');
            seatElement.classList.add('seat');
            seatElement.dataset.seatId = seat.seatId;  // Use seat ID from backend

            // Set seat status (reserved or available)
            if (seat.reserved) {
                seatElement.classList.add('reserved');  // Reserved seat
            } else {
                seatElement.addEventListener('click', () => toggleSeatSelection(seatElement));  // Allow selection for available seats
            }

            seatingChart.appendChild(seatElement);
        });
    }

    // Toggle seat selection
    function toggleSeatSelection(seatElement) {
        seatElement.classList.toggle('selected');  // Toggle selection class
        updateReserveButton();  // Update the reserve button state
    }

    // Enable/disable reserve button based on seat selection
    function updateReserveButton() {
        const selectedSeats = document.querySelectorAll('.seat.selected');
        const reserveButton = document.getElementById('reserve-seats');
        reserveButton.disabled = selectedSeats.length === 0;  // Disable if no seats are selected
    }

    // Reserve selected seats
    document.getElementById('reserve-seats').addEventListener('click', () => {
        const selectedSeats = document.querySelectorAll('.seat.selected');
        const seatIds = Array.from(selectedSeats).map(seat => seat.dataset.seatId);  // Get selected seat IDs

        fetch(`/api/movies/${movieId}/reserve`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(seatIds)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Reservation failed');
                }
                return response.text();  // Expect a success message
            })
            .then(message => {
                document.getElementById('reservation-status').innerText = message;  // Show success message
                displaySeats(movieId);  // Refresh the seating chart
            })
            .catch(error => {
                document.getElementById('reservation-status').innerText = 'Reservation failed. Please try again.';
                console.error('Error reserving seats:', error);
            });
    });
});

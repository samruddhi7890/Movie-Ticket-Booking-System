import java.util.*;

class Movie {
    String name;
    String showTime;
    int totalSeats;
    int availableSeats;
    String[] seatTypes = { "Regular", "Premium", "VIP" };
    double[] seatPrices = { 150.0, 250.0, 400.0 };

    // Constructor to initialize movie details
    Movie(String name, String showTime, int totalSeats) {
        this.name = name;
        this.showTime = showTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    double getSeatPrice(String seatType) {
        for (int i = 0; i < seatTypes.length; i++) {
            if (seatTypes[i].equalsIgnoreCase(seatType)) {
                return seatPrices[i];
            }
        }
        return -1; // Invalid seat type
    }

    void displayMovieDetails() {
        System.out.println("Movie: " + name + " | Show Time: " + showTime + " | Available Seats: " + availableSeats);
    }
}

class BookingSystem {
    ArrayList<Movie> movies;
    ArrayList<String> waitingList;

    BookingSystem() {
        movies = new ArrayList<>();
        waitingList = new ArrayList<>();
    }

    void addMovie(Movie movie) {
        movies.add(movie);
    }

    void bookTicket(String movieName, String seatType, int numTickets) {
        for (Movie movie : movies) {
            if (movie.name.equalsIgnoreCase(movieName)) {
                double pricePerSeat = movie.getSeatPrice(seatType);
                if (pricePerSeat == -1) {
                    System.out.println("Invalid seat type.");
                    return;
                }
                if (movie.availableSeats >= numTickets) {
                    double price = pricePerSeat * numTickets;

                    if (numTickets > 5) {
                        price *= 0.9; // 10% discount
                    }

                    movie.availableSeats -= numTickets;
                    System.out.println("Booking Confirmed: " + numTickets + " " + seatType + " seats for " + movieName);
                    System.out.println("Total Price: Rs. " + price);
                } else {
                    System.out.println("Seats unavailable. Adding to waiting list.");
                    waitingList.add(movieName + " - " + numTickets + " tickets");
                }
                return;
            }
        }
        System.out.println("Movie not found.");
    }

    void bookTicket(String movieName, String showTime, String seatType, int numTickets) {
        for (Movie movie : movies) {
            if (movie.name.equals(movieName) && movie.showTime.equals(showTime)) {
                bookTicket(movieName, seatType, numTickets);
                return;
            }
        }
        System.out.println("Movie with specified showtime not found.");
    }

    void cancelTicket(String movieName, int numTickets) {
        for (Movie movie : movies) {
            if (movie.name.equals(movieName)) {
                movie.availableSeats += numTickets;
                System.out.println(
                        "Cancellation Successful: " + numTickets + " tickets for " + movieName + " are now available.");
                return;
            }
        }
        System.out.println("Movie not found.");
    }

    void displayMovies() {
        for (Movie movie : movies) {
            movie.displayMovieDetails();
        }
    }
}

public class movieTicketBooking {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingSystem system = new BookingSystem();

        System.out.print("Enter number of movies to add: ");
        int numMovies = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numMovies; i++) {
            System.out.print("Enter movie name: ");
            String name = scanner.nextLine();
            System.out.print("Enter show time: ");
            String showTime = scanner.nextLine();
            System.out.print("Enter total seats available: ");
            int totalSeats = scanner.nextInt();
            scanner.nextLine();
            system.addMovie(new Movie(name, showTime, totalSeats));
        }

        while (true) {
            System.out.println("\n1. Display Movies\n2. Book Ticket\n3. Cancel Ticket\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    system.displayMovies();
                    break;
                case 2:
                    System.out.print("Enter movie name: ");
                    String movieName = scanner.nextLine();
                    System.out.print("Enter seat type (Regular/Premium/VIP): ");
                    String seatType = scanner.nextLine();
                    System.out.print("Enter number of tickets: ");
                    int numTickets = scanner.nextInt();
                    scanner.nextLine();
                    system.bookTicket(movieName, seatType, numTickets);
                    break;
                case 3:
                    System.out.print("Enter movie name: ");
                    movieName = scanner.nextLine();
                    System.out.print("Enter number of tickets to cancel: ");
                    numTickets = scanner.nextInt();
                    scanner.nextLine();
                    system.cancelTicket(movieName, numTickets);
                    break;
                case 4:
                    System.out.println("Exiting system. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

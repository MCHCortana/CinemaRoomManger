package cinema;

import java.util.Scanner;

public class Cinema {

    public static int menuCall(Scanner scanner) {

        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return scanner.nextInt();
    }


    public static void cinemaVisualisation(int rows, int seatsInRow, String[][] cinemaLayout) {
        System.out.println("Cinema:");

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seatsInRow; j++) {
                System.out.print(cinemaLayout[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void ticketSelection(Scanner scanner,
                                       int rows,
                                       int seatsInRoom,
                                       int[] chosenSeat,
                                       String[][] cinemaLayout) {

        boolean choosingTicket = true;

        while (choosingTicket) {
            System.out.println("Enter a row number:");
            chosenSeat[0] = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            chosenSeat[1] = scanner.nextInt();

            if (chosenSeat[0] > rows || chosenSeat[1] > seatsInRoom / rows) {
                System.out.println("Wrong input!");
            } else if (cinemaLayout[chosenSeat[0]][chosenSeat[1]].equals("B")) {
                System.out.println("That ticket has already been purchased!");
            } else {
                choosingTicket = false;
            }
        }
    }

    public static void buyTicket(int seatsInRoom,
                                 int rows,
                                 int[] chosenSeat,
                                 String[][] cinemaLayout,
                                 int[] purchasedTickets) {

        int ticketPrice = 0;

        if (seatsInRoom <= 60) {
            ticketPrice = 10;
            purchasedTickets[1] += ticketPrice;

            System.out.println("Ticket price: $" + ticketPrice);

        } else {

            int seatPrice = 8;
            int seatPriceHigh = 10;
            if (rows / 2 >= chosenSeat[0]) {
                seatPrice = seatPriceHigh;
            }

            ticketPrice = seatPrice;

            purchasedTickets[1] += ticketPrice;

            System.out.println("Ticket price: $" + ticketPrice);
        }
        cinemaLayout[chosenSeat[0]][chosenSeat[1]] = "B";
    }

    public static void statistics(int rows,
                                  int seatsInRoom,
                                  int[] purchasedTickets,
                                  String[][] cinemaLayout) {
        purchasedTickets[0] = 0;

        int totalIncome = 0;

        if (seatsInRoom <= 60) {
            totalIncome = seatsInRoom * 10;
        } else {
            totalIncome = seatsInRoom / 2 * 10 + seatsInRoom / 2 * 8;
        }

        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seatsInRoom / rows; j++) {
                if (cinemaLayout[i][j].equals("B")) {
                    purchasedTickets[0]++;
                }
            }
        }
        float percentBought = ((float) purchasedTickets[0] / seatsInRoom) * 100;
        String percentBoughtFormatted = String.format("%.2f", percentBought);



        System.out.printf("Number of purchased tickets: %d%n", purchasedTickets[0]);
        System.out.println("Percentage: " + percentBoughtFormatted + "%");
        System.out.printf("Current income: $%d%", purchasedTickets[1]);
        System.out.printf("Total income: $%d%n", totalIncome);
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsInRow = scanner.nextInt();

        //int seatInRow = 0;
        //int seatNum = 0;

        int[] chosenSeat = {0, 0};

        int seatsInRoom = rows * seatsInRow;

        int[] purchasedTickets = {0, 0};

        String[][] cinemaLayout = new String[rows + 1][seatsInRow + 1];
        for (int i = 0; i <= rows; i++) {
            for (int j = 0; j <= seatsInRow; j++) {
                if (i == 0) {
                    cinemaLayout[i][j] = Integer.toString(j);
                } else if (j == 0) {
                    cinemaLayout[i][j] = Integer.toString(i);
                } else {
                    cinemaLayout[i][j] = "S";
                }
                cinemaLayout[0][0] = " ";
            }
        }

        boolean running = true;
        while (running) {
            switch (menuCall(scanner)) {
                case 1:
                    cinemaVisualisation(rows, seatsInRow, cinemaLayout);
                    System.out.println();
                    break;
                case 2:
                    ticketSelection(scanner,
                            rows,
                            seatsInRoom,
                            chosenSeat,
                            cinemaLayout);
                    buyTicket(seatsInRoom,
                            rows,
                            chosenSeat,
                            cinemaLayout,
                            purchasedTickets);
                    System.out.println();
                    break;
                case 3:
                    statistics(rows, seatsInRoom, purchasedTickets, cinemaLayout);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Wrong input!");
            }
        }
        scanner.close();

    }
}
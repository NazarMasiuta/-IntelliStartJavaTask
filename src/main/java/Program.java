import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Program {
    //Supplementary methods:
    public static void println(Object line) {
        System.out.println(line);
    }
    public static void print(Object line) {
        System.out.print(line);
    }
    //here has to be an error method

    public static void mainMenu(Products[] p, Customers[] c) {
        Scanner input = new Scanner(System.in);
        println("You are in the main menu. What would you like to do:\n" +
                "Log in: press 1.\n" +
                "Register: press 2.\n" +
                "Display all products: press 3.\n" +
                "Display all customers: press 4.\n" +
                "See all customers who purchased a product: press 5.\n" +
                "See all products purchased by a customer: press 6.\n" +
                "Delete a customer: press 7.\n" +
                "Delete a product press 8.\n" +
                "Add a product: press 9.");
        int mainMenuChoice = 0;
        try {
            mainMenuChoice = input.nextInt();
        } catch (InputMismatchException exception) {
            println("Wrong input format or no such option! Try again.");
            mainMenu(p, c);
        }

        switch (mainMenuChoice) {

            case 1 -> clientLogIn(p, c);
            case 2 -> customerRegistration(p, c);
            case 3 -> displayAllProducts(p, c);
            case 4 -> displayAllCustomers(p, c);
            case 5 -> displayBuyersOfAProduct(p, c);
            case 6 -> displayProductsOfACustomer(p, c);
            case 7 -> deleteAClient(p, c);
            case 8 -> deleteAProduct(p, c);
            case 9 -> addAProduct(p, c);
            default -> {
                println("Wrong input format or no such option! Try again.");
                mainMenu(p, c);
            }
        }
    }

    public static void backToMainMenu(Products[] p, Customers[] c) {
        Scanner input = new Scanner(System.in);
        println("\nEnter \"M\" to go back to the Main Menu.");
        String exitInput = "";
        while(!(exitInput.equals("m")||exitInput.equals("M"))) {
            exitInput = input.nextLine();
        }
        mainMenu(p, c);
    }

    public static void clientLogIn(Products[] p, Customers[] c) {
        Scanner input = new Scanner(System.in);
        print("Please,enter your customer ID to log in: ");
        int inputID1 = 0;
        try {
            inputID1 = input.nextInt();
        } catch (InputMismatchException exception) {
            println("Wrong id format! Try again.");
            clientLogIn(p, c);
        }
        if(inputID1 <= 1000 || inputID1 >= 10000) {
            println("Wrong id format! Try again.");
            clientLogIn(p, c);
        }

        int customerIndex = 0;
        boolean id1Recognized = false;
        for(int i = 0; i < c.length; ++i) {
            if(inputID1 == c[i].id) {
                id1Recognized = true;
                customerIndex = i;
                break;
            }
        }
        if(!id1Recognized) {
            println("No client with such id found! Try again.");
            clientLogIn(p, c);
        }
        println("Hello, " + c[customerIndex].firstName + " " + c[customerIndex].lastName + "." +
                " Your balance is " + c[customerIndex].money + ".\n" +
                "To go back to the Main Menu enter \"M\" to make a purchase press \"P\"");
        boolean choiceMade = false;
        while (!choiceMade) {
            String choice = input.nextLine();
            if(choice.equals("p")||choice.equals("P")) {
                purchase(p, c, customerIndex);
            }
            if(choice.equals("m")||choice.equals("M")) {
                backToMainMenu(p, c);
            }
        }

    }

    public static void purchase(Products[] p, Customers[] c, int customerIndex) {
        Scanner input = new Scanner(System.in);
        print("Enter the product's ID: ");
        int inputID2 = 0;
        try {
            inputID2 = input.nextShort();
        } catch (InputMismatchException exception) {
            println("Wrong id format! Try again.");
        }
        if (inputID2 <= 1000 || inputID2 >= 10000) {
            println("Wrong id format! Try again.");
        }
        int productIndex = 0;
        boolean id2Recogized = false;
        for(int i = 0; i < p.length; ++i) {
            if(inputID2 == p[i].id) {
                productIndex = i;
                id2Recogized = true;
            }
        }

        if(!id2Recogized) {
            println("No product with such an ID! Try again.");
            purchase(p, c, customerIndex);
        }
        if(c[customerIndex].money >= p[productIndex].price) {
            c[customerIndex].money -= p[productIndex].price;
            if(c[customerIndex].purchases.isBlank()) {
                c[customerIndex].purchases = p[productIndex].name;
            }
            else {
                c[customerIndex].purchases += " " + p[productIndex].name;
            }
            if(p[productIndex].buyers.isBlank()) {
                p[productIndex].buyers += c[customerIndex].id;
            }
            else {
                p[productIndex].buyers += " " + c[customerIndex].id;
            }
            println("You successfully purchased " + p[productIndex].name +
                    ". Your balance now is " + c[customerIndex].money);
            backToMainMenu(p, c);
        }

        else {
            println("Not enough money to buy this product!");
            purchase(p, c, customerIndex);
        }
    }
    public static void customerRegistration(Products[]p, Customers[] c) {
        Scanner input = new Scanner(System.in);
        print("New customer registration\nEnter your first name: ");
        String firstName = input.next();
        print("Enter your second name: ");
        String lastName = input.next();
        boolean inputAcceptable = false;
        double money = 0;
        while(!inputAcceptable) {
            print("Enter the amount of money you are now willing to put into your account: ");
            money = input.nextDouble();
            if(money >= 0)
                inputAcceptable = true;
            else
                println("You can only put 0 or more into your account! Try again!");
        }
        Random random = new Random();
        short id = (short) random.nextInt(1000,10000); //add a check for already existing id.

        Customers[] buffer = new Customers[c.length + 1];
        for(int i = 0; i < c.length; ++i) {
            buffer[i] = c[i];
        }
        buffer[c.length] = new Customers(firstName, lastName, id, money);
        c = buffer;

        println(firstName + " " + lastName +
                ", you were successfully registered! Your customer ID is " + id);
        backToMainMenu(p, c);
    }
    public static void displayAllProducts(Products[] p,Customers[] c) {
        Scanner input = new Scanner(System.in);
        println("\nList of all products offered: ");
        if(p.length == 0)
            println("No products in the system yet.");
        else
            for(int i = 0; i < p.length; ++i)
                println(p[i].name + "(ID: " + p[i].id + ")");

        backToMainMenu(p, c);
    }
    public static void displayAllCustomers(Products[] p,Customers[] c) {
        Scanner input = new Scanner(System.in);
        println("The following customers are registered:");
        for(int i = 0; i < c.length; ++i) {
            println(c[i].firstName + " " + c[i].lastName + " (ID: " + c[i].id + ")");
        }
        backToMainMenu(p, c);
    }
    public static void displayBuyersOfAProduct(Products[] p,Customers[] c) {
        Scanner input = new Scanner(System.in);
        print("Please, enter the ID of the product: ");
        int inputID = 0;
        try {
            inputID = input.nextInt();
        } catch (InputMismatchException exception) {
            println("Wrong id format! Try again.");
            displayBuyersOfAProduct(p, c);
        }
        if(inputID <= 1000 || inputID >= 10000) {
            println("Wrong product id format! Try again.");
            displayBuyersOfAProduct(p, c);
        }

        int productIndex = 0;
        boolean idRecognized = false;
        for(int i = 0; i < p.length; ++i) {
            if(inputID == p[i].id) {
                idRecognized = true;
                productIndex = i;
                break;
            }
        }
        if(!idRecognized) {
            println("No product with such id found! Try again.");
            displayBuyersOfAProduct(p, c);
        }
        if(p[productIndex].buyers.isBlank())
            println("No one has bought this product yet.");
        else
            println("Here is a list of people (by their ID) who bought" + p[productIndex].name +
                    "(ID: " + inputID + "): " + p[productIndex].buyers);
        backToMainMenu(p, c);
    }
    public static void displayProductsOfACustomer(Products[] p,Customers[] c) {
        Scanner input = new Scanner(System.in);
        print("Please, enter the ID of the customer: ");
        int inputID = 0;
        try {
            inputID = input.nextInt();
        } catch (InputMismatchException exception) {
            println("Wrong id format! Try again.");
            displayProductsOfACustomer(p, c);
        }
        if(inputID <= 1000 || inputID >= 10000) {
            println("Wrong product id format! Try again.");
            displayProductsOfACustomer(p, c);
        }

        int customerIndex = 0;
        boolean idRecognized = false;
        for(int i = 0; i < c.length; ++i) {
            if(inputID == c[i].id) {
                idRecognized = true;
                customerIndex = i;
                break;
            }
        }
        if(!idRecognized) {
            println("No customer with such id found! Try again.");
            displayProductsOfACustomer(p, c);
        }
        if(c[customerIndex].purchases.isBlank())
            println("This customer has not purchased anything yet.");
        else
            println("Here is a list of products that this customer bought: " + c[customerIndex].purchases);
        backToMainMenu(p, c);
    }
    public static void deleteAClient(Products[] p,Customers[] c) {
        backToMainMenu(p, c);
    }
    public static void deleteAProduct(Products[] p,Customers[] c) {
        backToMainMenu(p, c);
    }
    public static void addAProduct(Products[] p,Customers[] c) {
        Scanner input = new Scanner(System.in);
        print("Enter the name of the product you want to add: ");
        String name = input.next();
        double price = 0;
        boolean inputAcceptable = false;
        while(!inputAcceptable) {
            print("Enter the price for the product: ");
            price = input.nextDouble();
            if(price >= 0)
                inputAcceptable = true;
            else
                println("You can set price only to more than 0! Try again!");
        }
        Random random = new Random();
        int id = random.nextInt(1000,10000); //add a check
        Products[] buffer = new Products[p.length + 1];
        for(int i = 0; i < p.length; ++i) {
            buffer[i] = p[i];
        }
        buffer[p.length] = new Products(name, id, price);
        p = buffer;
        println("Product " + name + "(ID: " + id + ") created successfully!");
        backToMainMenu(p, c);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Products[] p = new Products[0];
        Customers[] c = new Customers[0];
        mainMenu(p, c);
    }
}

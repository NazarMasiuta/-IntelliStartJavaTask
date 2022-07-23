public class Customers {
    Customers(String firstName, String lastName, int id, double money) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.money = money;
    }
    public void displayPurchases() {
        System.out.print(firstName + " " + lastName + " has purchased the following: ");
        System.out.println(purchases);
    }
    String purchases = "";
    String firstName;
    String lastName;
    int id;
    double money;
}

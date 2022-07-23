public class Products {
    Products (String name, int id, double price) { //maybe use byte for id (to save memory)
        this.name = name;
        this.id = id;
        this.price = price;
    }

    public void displayBuyers() {
        System.out.print("The following clients has purchased this product: ");
        System.out.println(buyers);
    }
    String name;
    int id;
    double price;
    String buyers = "";
}

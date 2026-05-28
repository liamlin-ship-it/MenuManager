public class MenuItem {
    private final String id;
    private final String name;
    private final double price;

    public MenuItem(String id, String name, double price){
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return price;
    }

    public String getDetails(){
        // %-5s means a left-aligned string with 5 spaces
        return String.format("%-5s %-20s %-8.2f", id, name, price);
    }
}

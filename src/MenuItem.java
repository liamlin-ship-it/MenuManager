public class MenuItem {
    private final String id;
    private final String name;
    private final int price;

    public MenuItem(String id, String name, int price){
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

    public int getPrice(){
        return price;
    }

    public String getDetails(){
        // %-5s means a left-aligned string with 5 spaces
        return String.format("%-5s %-20s %-8d", id, name, price);
    }
}

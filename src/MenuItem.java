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
        // Check the length of the item name.
        // If it is less than 6 characters,
        // append an extra tab to ensure proper alignment.
        String tab = name.length() < 6 ? "\t\t" : "\t";
        return id + "  " + name + tab + "$" + price;
    }
}

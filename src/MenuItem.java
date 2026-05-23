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
        // 判斷中文名稱長度。如果字數小於6個字，
        // 就多補一個 Tab 確保對齊
        String tab = name.length() < 6 ? "\t\t" : "\t";
        return id + "  " + name + tab + "$" + price;
    }
}

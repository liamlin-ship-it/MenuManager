import java.util.ArrayList;

public class Order {
    private final ArrayList<MenuItem> items;

    public Order(){
        this.items = new ArrayList<>();
    }

    // Add the item to the order
    public void addItem(MenuItem item){
        items.add(item);
        System.out.println("Added: " + item.getName());
    }

    public void removeItem(int index){
        // Ensure the provided index is greater than or equal to 0,
        // and less than the current size of the list.
        if (index >= 0 && index < items.size()){
            MenuItem removed = items.remove(index);
            System.out.println("Removed: " + removed.getName());
        }
    }

    // Calculate the total price
    public int calculateTotal(){
        int total = 0;
        for (MenuItem item : items){
            // Retrieve the price from each MenuItem and add it to the total
            total += item.getPrice();
        }
        return total;
    }

    public ArrayList<MenuItem> getItems(){
        return items;
    }

    // Call this when checkout is complete, preparing to serve the next customer
    public void clear(){
        items.clear();
    }
}

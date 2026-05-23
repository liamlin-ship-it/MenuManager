import java.util.ArrayList;

public class Order {
    private final ArrayList<MenuItem> items;

    public Order(){
        this.items = new ArrayList<>();
    }

    // 將餐點加入購物車
    public void addItem(MenuItem item){
        items.add(item);
        System.out.println("已加入: " + item.getName());
    }

    public void removeItem(int index){
        // 確保傳入的索引值大於等於 0，且小於清單目前的總長度
        if (index >= 0 && index < items.size()){
            MenuItem removed = items.remove(index);
            System.out.println("已移除: " + removed.getName());
        }
    }

    // 計算總金額
    public int calculateTotal(){
        int total = 0;
        for (MenuItem item : items){
            // 從 MenuItem 裡面拿每一個的價錢來加總
            total += item.getPrice();
        }
        return total;
    }

    public ArrayList<MenuItem> getItems(){
        return items;
    }

    // 結帳完成，準備服務下一位客人時呼叫
    public void clear(){
        items.clear();
    }
}

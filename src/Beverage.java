public class Beverage extends MenuItem {
    private String sugarLevel;
    private String iceLevel;

    public Beverage(String id, String name, int price){
        super(id, name, price);
        this.sugarLevel = "  　 甜度";
        this.iceLevel = "冰塊";
    }

    public void setCustomization(String sugar, String ice){
        this.sugarLevel = sugar;
        this.iceLevel = ice;
    }

    @Override
    public String getDetails(){
        // 先取之前已經排版好的編號、名稱跟價格，
        // 再加上甜度跟冰塊的字串
        return super.getDetails() + String.format("%s, %s", sugarLevel, iceLevel);
    }
}

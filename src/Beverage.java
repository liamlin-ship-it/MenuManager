public class Beverage extends MenuItem {
    private String sugarLevel;
    private String iceLevel;

    public Beverage(String id, String name, int price){
        super(id, name, price);
        this.sugarLevel = "Regular Sugar";
        this.iceLevel = "Regular Ice";
    }

    public void setCustomization(String sugar, String ice){
        this.sugarLevel = sugar;
        this.iceLevel = ice;
    }

    @Override
    public String getDetails(){
        // Retrieve the pre-formatted ID, name, and price from the parent class,
        // then append the sugar and ice level strings.
        return super.getDetails() + String.format("%s, %s", sugarLevel, iceLevel);
    }
}

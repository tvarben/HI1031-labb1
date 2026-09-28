package se.kth.davr.labb1.Model.Enteties;

public class OrderedItem {
    private int id;
    private int orderId;
    private int itemId;
    private int quantity;
    private int unitPrice;

    public OrderedItem(int orderId, int itemId, int quantity, int unitPrice){
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.unitPrice = unitPrice ;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int calculateTotalPrice(){
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return "OrderedItem: " +
                "[id:" + getId() +
                " orderId: " + getOrderId() +
                " itemId: " + getItemId() +
                " quantity: " + getQuantity() +
                " unitPrice: " + getUnitPrice() +
                "]";    }
}

package se.kth.davr.labb1.Model.Enteties;

public class Item {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public Item(int id, String name, int price, int quantity){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {return id;}

    public String getName() {return name;}

    public int getPrice() {
        return price;
    }

    public int getQuantity() {return quantity;}

    public void setName(String name) {this.name = name;}

    public void setPrice(int price) {this.price = price;}

    public void setQuantity(int quantity) {this.quantity = quantity;}

    @Override
    public String toString() {
        return "Item: " +
                "[id:" + getId() +
                " name: " + getName() +
                " price: " + getPrice() +
                " quantity: " + getQuantity() +
                "]";
    }
}
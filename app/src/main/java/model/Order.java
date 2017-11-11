package model;

/**
 * Created by Hytham on 11/6/2017.
 */

public class Order {
    String userName , itemName;

    public Order(){

    }
    public Order(String userName , String itemName){
        this.userName = userName;
        this.itemName = itemName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}

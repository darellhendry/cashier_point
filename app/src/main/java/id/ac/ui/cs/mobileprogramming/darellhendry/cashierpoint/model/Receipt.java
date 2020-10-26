package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.model;

import java.util.List;

public class Receipt {
    private String id;
    private String customerEmail;
    private List<Item> items;
    private int cash;
    private String date;

    public Receipt(String id, String customerEmail, List<Item> items, int cash, String date) {
        this.id = id;
        this.customerEmail = customerEmail;
        this.items = items;
        this.cash = cash;
        this.date = date;
    }

    public Receipt() {
    }

    public String getId() {
        return id;
    }

    public int getCash() {
        return cash;
    }

    public String getDate() {
        return date;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getPrice() {
        int result = 0;
        for (Item item: items) {
            result += item.getPrice();
        }
        return result;
    }
}

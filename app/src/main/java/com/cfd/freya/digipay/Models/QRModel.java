package com.cfd.freya.digipay.Models;

import java.util.ArrayList;

/**
 * Created by FREYA on 11-02-2017.
 */

public class QRModel {
    public String storeName;
    public String billId;
    public String userName;
    public String date;
    public ArrayList<Item> item;
    public String grandTotal;

    public String getStoreName() {
        return storeName;
    }

    public String getBillId() {
        return billId;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<Item> getItemArrayList() {
        return item;
    }

    public String getGrandTotal() {
        return grandTotal;
    }
}

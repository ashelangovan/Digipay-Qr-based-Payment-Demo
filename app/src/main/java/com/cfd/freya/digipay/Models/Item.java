package com.cfd.freya.digipay.Models;

/**
 * Created by FREYA on 11-02-2017.
 */

public class Item {
    public String itName,itQuantity,itPrice,itSubtotal;

    public String getItName() {
        return itName;
    }

    public void setItName(String itName) {
        this.itName = itName;
    }

    public String getItQuanitity() {
        return itQuantity;
    }

    public void setItQuantity(String itQuanitity) {
        this.itQuantity = itQuanitity;
    }

    public String getItPrice() {
        return itPrice;
    }

    public void setItPrice(String itPrice) {
        this.itPrice = itPrice;
    }

    public String getItSubTotal() {
        return itSubtotal;
    }

    public void setItSubTotal(String itSubTotal) {
        this.itSubtotal = itSubtotal;
    }

}

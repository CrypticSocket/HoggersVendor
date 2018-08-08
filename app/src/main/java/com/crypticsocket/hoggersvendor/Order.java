package com.crypticsocket.hoggersvendor;

import android.content.ClipData;
import android.widget.ImageView;

/**
 * Created by saket on 22-Dec-17.
 */

public class Order {
    String username, itemname;

    public Order ()
    {

    }

    public Order(String username, String itemname) {
        this.username = username;
        this.itemname = itemname;
    }

    public String getUsername() {
        return username;
    }

    public String getItemname() {
        return itemname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }
}

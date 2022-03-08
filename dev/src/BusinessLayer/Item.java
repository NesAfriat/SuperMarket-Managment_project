package BusinessLayer;

import DataLayer.DataController;

import java.util.Date;

public class Item {
    private final Integer item_id;             //item id
    private final Integer product_id;          //product id
    private String location;                   //item location in store
    private final Date supplied_date;          //the date which the item was supplied
    private Date expiration_date;               //item expiration date and time of defected date
//    private Double selling_price;               //item price tag in store

    public Item(Integer item_id, Integer product_id, String location, Date supplied_date, Date expiration_date) {
        this.item_id = item_id;
        this.product_id = product_id;
        this.location = location;
        this.supplied_date = supplied_date;
        this.expiration_date = expiration_date;
//        this.selling_price = selling_price;
    }

    public Integer getItem_id() {
        return item_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
        update(this);// update item location in db
    }

    public Date getSupplied_date() {
        return supplied_date;
    }


    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
        update(this);
    }


    @Override
    public String toString() {
        return "{" +
                "item_id=" + item_id +
                ", product_id=" + product_id +
                ", location=" + location +
                ", supplied_date=" + supplied_date +
                ", expiration_date=" + expiration_date +
                '}';
    }


    //==================================================
    //DATA Actions:
    private void update(Item item) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if(!dc.update(this)){
            System.out.println("failed to update new Item to the database with the keys: gpID= "+item.getProduct_id()+" iID= "+item.getItem_id());
        }
    }
}


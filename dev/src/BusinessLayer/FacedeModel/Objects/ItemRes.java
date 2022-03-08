package BusinessLayer.FacedeModel.Objects;

import BusinessLayer.Item;

import static BusinessLayer.FacedeModel.inventModel.getDate;

public class ItemRes {
    public int item_id;
    public int product_id;
    public String location;
    public String supplied_date;
    public String creation_date;
    public String expiration_date;


    public ItemRes(Item item) {
        this.item_id = item.getItem_id();
        this.product_id = item.getProduct_id();
        this.location = item.getLocation();
        this.supplied_date = getDate(item.getSupplied_date());
        this.expiration_date = getDate(item.getExpiration_date());
    }

    @Override
    public String toString() {
        return "{" +
                "item id=" + item_id +
                ", product id=" + product_id +
                ", location=" + location +
                ", supplied date=" + supplied_date +
                ", creation date=" + creation_date +
                ", expiration date=" + expiration_date +
                '}'+'\n';
    }
}

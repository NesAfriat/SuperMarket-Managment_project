package BusinessLayer.FacedeModel.Objects;

import BusinessLayer.GeneralProduct;

import java.util.LinkedList;

public class ProductRes {
    public int product_id;
    public String product_name;
    public String manufacturer_name;
    public int amount_store;
    public int amount_storage;
    public int min_amount;
    public double selling_price;
    public LinkedList<Integer> items;

    public ProductRes(GeneralProduct product, Double discount) {
        this.product_id = product.getProduct_id();
        this.product_name = product.getProduct_name();
        this.manufacturer_name = product.getManufacturer_name();
        this.amount_store = product.getAmount_store();
        this.amount_storage = product.getAmount_storage();
        this.min_amount = product.getMin_amount();
        this.selling_price = product.getSelling_price() * (1 - discount);
        this.items = product.getItems();
    }

    @Override
    public String toString() {
        return "{" +
                "product id=" + product_id +
                ", product name=" + product_name +
                ", manufacturer name=" + manufacturer_name +
                ", amount store=" + amount_store +
                ", amount storage=" + amount_storage +
                ", min amount=" + min_amount +
                ", selling price= " + selling_price +
                ", items=" + items +
                '}'+'\n';
    }
}

package BusinessLayer;


import DataLayer.DataController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Date;


public class GeneralProduct {
    private final Integer product_id;                           //product id
    private String product_name;                                //product name
    private String manufacturer_name;                               //producer name
    private Integer amount_store;                               //product's amount in store
    private Integer amount_storage;                             //product's amount in storage
    private Integer min_amount;                                 //product's min amount required
    private Double selling_price;
    private LinkedList<Item> items;                             //product's items in stock
    private Integer item_id;
    private HashMap<Integer, ProductSupplier> HashOfSupplierProducts;

    public GeneralProduct(String product_name, Integer product_id, String manufacturer_name, Integer min_amount, Double selling_price, ProductSupplier productSupplier) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.manufacturer_name = manufacturer_name;
        this.amount_store = 0;
        this.amount_storage = 0;
        this.min_amount = min_amount;
        HashOfSupplierProducts = new HashMap<>();
        this.selling_price = selling_price;
        this.items = new LinkedList<>();
        this.item_id = 0;
        HashOfSupplierProducts.put(productSupplier.getCatalogID(), productSupplier);
    }

    //constructor for DAL
    public GeneralProduct(int gpID, String gpName, String gpManuName, int amountStore, int amountStorage, int minAmount, double sellingPrice) {
        this.product_id = gpID;
        this.product_name = gpName;
        this.manufacturer_name = gpManuName;
        this.amount_store = amountStore;
        this.amount_storage = amountStorage;
        this.min_amount = minAmount;
        this.selling_price = sellingPrice;

        this.item_id = 0;
        this.items = new LinkedList<>();
        this.HashOfSupplierProducts = new HashMap<>();
    }




    public HashMap<Integer, ProductSupplier> getProductSuppliers() {
        return HashOfSupplierProducts;
    }

    //add new product supplier,product supplier can have multiple product suppliers, it must have at least one product supplier
    public void AddSupplierProduct(ProductSupplier productSupplier) {
        if (CheckIfSupplierProductExist(productSupplier.getCatalogID())) {
            throw new IllegalArgumentException("the product is already exsist in the system ");
        }
        // if ok we want to add the product to the system
        HashOfSupplierProducts.put(productSupplier.getCatalogID(), productSupplier);
    }

    public boolean CheckIfSupplierProductExist(int catalog_id) {
        return HashOfSupplierProducts.containsKey(catalog_id);
    }

    public void RemoveSupplierProduct(Integer Catalofid) {
        if (!isSupplierProductExist(Catalofid)) {
            throw new IllegalArgumentException("the supplie product doues not exsist");
        }
        removeSPPersistence(HashOfSupplierProducts.remove(Catalofid));

    }


    public boolean isSupplierProductExist(int Catalogid) {
        return HashOfSupplierProducts.containsKey(Catalogid);
    }


    public String getProduct_name() {
        return product_name;
    }

    public HashMap<Integer, ProductSupplier> getHashOfSupplierProducts() {
        return HashOfSupplierProducts;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public String getManufacturer_name() {
        return manufacturer_name;
    }

    public Integer getAmount_store() {
        return amount_store;
    }

    public void addAmount_store(Integer quantity) {
        this.amount_store = this.amount_store + quantity;
    }

    public Integer getAmount_storage() {
        return amount_storage;
    }

    public void addAmount_storage(Integer quantity) {
        this.amount_storage = this.amount_storage + quantity;
    }

    public Integer getTotal_amount() {
        return amount_store + amount_storage;
    }


    public Integer getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(Integer min_amount) {
        this.min_amount = min_amount;
        update(this); //update gp min amount in db
    }

    public Double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(Double selling_price) {
        this.selling_price = selling_price;
        update(this);
    }


    public LinkedList<Integer> getItems() {
        LinkedList<Integer> items = new LinkedList<>();
        for (Item item : this.items) {
            items.add(item.getItem_id());
        }
        return items;
    }

    /**
     * @param quantity
     * @param location
     * @param supplied_date
     * @param expiration_date
     * @return
     */
    public LinkedList<Integer> addItems(Integer quantity, String location, Date supplied_date, Date expiration_date) {
        LinkedList<Integer> itemsAdded = new LinkedList<>();
        for (int i = 0; i < quantity; i++) {
            item_id++;
            Item item = new Item(item_id, product_id, location, supplied_date, expiration_date);
            addItemToGP(item);
            items.add(item);
            itemsAdded.add(item.getItem_id());

        }
        if (location.equals("storage")) {
            addAmount_storage(quantity);
        } else {
            addAmount_store(quantity);
        }
        update(this); //update gp's amount storage \ store
        return itemsAdded;
    }

    public boolean isSupplierProducHashEmpty() {
        return HashOfSupplierProducts.keySet().size() == 0;
    }

    /**
     * get the defected items from the product to this date(today) from a specific stock
     *
     * @return
     */
    public LinkedList<Item> getDefects() {
        LinkedList<Item> defects = new LinkedList<>();
        LinkedList<Item> new_items = new LinkedList<>();
        int amount_storage = 0;
        int amount_store = 0;
        for (Item item : items) {
            if (item.getExpiration_date().before(new Date())) {
                defects.add(item);
                if (item.getLocation().equals("storage")) {
                    amount_storage++;
                } else {
                    amount_store++;
                }
            } else {
                new_items.add(item);
            }
        }
        items = new_items;
        removeDefectedItem(defects);
        addDefectedItem(defects);
        addAmount_store(-1 * amount_store);
        addAmount_storage(-1 * amount_storage);
        update(this); //update amounts in the db
        return defects;
    }


    /**
     * @param item_id
     */
    public Item setItem_defected(Integer item_id) throws Exception {
        Item item = getItem(item_id);
        item.setExpiration_date(new Date());
        items.remove(item);
        removeItemPersistence(item);
        LinkedList<Item> toRemove = new LinkedList<>();
        toRemove.add(item);
        addDefectedItem(toRemove);
        if(item.getLocation().equals("storage")){
            amount_storage--;
        }else{
            amount_store--;
        }
        update(this); //update amounts
        return item;
    }

    /**
     * @param item_id
     */
    public void setItem_location(Integer item_id, String new_location) throws Exception {
        Item item = getItem(item_id);
        if (item.getLocation().startsWith("storage")) {
            if (new_location.startsWith("store")) {
                addAmount_store(1);
                addAmount_storage(-1);
            }
        } else if (item.getLocation().startsWith("store")) {
            if (new_location.startsWith("storage")) {
                addAmount_store(-1);
                addAmount_storage(1);
            }
        }
        update(this); //update gp storage/store amount in db
        item.setLocation(new_location);
    }

    private Item getItem(Integer item_id) throws Exception {
        Item item = null;
        for (Item i : items) {
            if (i.getItem_id().equals(item_id)) {
                item = i;
                break;
            }
        }
        if (item == null)
            throw new Exception("item does not exist");
        return item;
    }


    @Override
    public String toString() {
        return "{" +
                "product_name=" + product_name +
                ", product_id=" + product_id +
                ", producer_name=" + manufacturer_name +
                ", amount_store=" + amount_store +
                ", amount_storage=" + amount_storage +
                ", min_amount=" + min_amount +
                ", items=" + items +
                '}';
    }


    public void removeItem(Integer item_id) throws Exception {
        Item item = getItem(item_id);
        removeItemPersistence(item);
        items.remove(item);
        if(item.getLocation().equals("storage")){
            amount_storage--;
        }else{
            amount_store--;
        }
        update(this); //update amounts
    }

    public LinkedList<Item> get_items() {
        return items;
    }

    //======================================================================
//DATA Functions:
    private void addItemToGP(Item item) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insertItem(item)) {
            System.out.println("failed to insert new item to the database with the keys: gpID= " + item.getProduct_id() + " iID= " + item.getItem_id());
        }
        im.addItem(item);
    }

    private void update(GeneralProduct prod) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.update(this)) {
            System.out.println("failed to update new General Product to the database with the keys: gpID= " + prod.getProduct_id());
        }
    }
    private void addDefectedItem(LinkedList<Item> defects) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        for(Item item: defects){
            dc.insertDefected(item);
            im.addDefectedItem(item);
        }
    }
    private void removeDefectedItem(LinkedList<Item> defects) {
        for(Item item: defects){
            removeItemPersistence(item);
        }
    }

    private void removeItemPersistence(Item toRemove) {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        im.removeItem(toRemove.getItem_id(), toRemove.getProduct_id());
        dc.delete(toRemove);
    }

    //for Dal - load from DB
    public void addItem(Item toAdd) {
        items.add(toAdd);
        if(item_id<toAdd.getItem_id()){
            item_id = toAdd.getItem_id();
        }
    }
    //for Dal - load from DB
    public void addSupplierProduct(ProductSupplier productSupplier) {
        HashOfSupplierProducts.put(productSupplier.getCatalogID(), productSupplier);
    }


    public void removeItems() {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        for(Item item: items) {
            im.removeItem(this.product_id, item.getItem_id());
            dc.delete(item);
        }
    }
    private void removeSPPersistence(ProductSupplier toRemove) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        im.removerProductSupplier(toRemove);
        dc.deleteProductSupplier(toRemove,toRemove.getSuplierID());
    }
}

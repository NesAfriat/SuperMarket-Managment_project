package BusinessLayer;


import DataLayer.DataController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;


public class Stock {
    private LinkedList<Item> defects;                                   //all defect items
    private ProductManager productManager;
    private boolean loadDefected;

    public Stock() {
        this.defects = new LinkedList<>();
        this.productManager = new ProductManager();
        loadDefected = false;
    }


    @Override
    public String toString() {
        return "Stock{" +
                ", defects=" + defects +
                '}';
    }


    /**
     * add a new category to the stock
     *
     * @param category
     */
    public void addCategory(String category) throws Exception {
        this.productManager.addCategory(category);
    }

    /**
     * add items of a specific product to the stock
     *
     * @param product_id
     * @param quantity
     * @param location
     * @param supplied_date
     * @param expiration_date
     */
    public LinkedList<Integer> addItems(Integer product_id, Integer quantity, String location, Date supplied_date, Date expiration_date) throws Exception {
        return this.productManager.addItems(product_id, quantity, location, supplied_date, expiration_date);
    }

    /**
     * update the location of a specific product
     *
     * @param item_id
     * @param product_id
     * @param new_location
     */
    public void update_location(Integer item_id, Integer product_id, String new_location) throws Exception {
        this.productManager.update_location(item_id, product_id, new_location);
    }

    /**
     * first it get all the defected items of each product, and then return all defected items
     *
     * @return
     */
    public LinkedList<Item> get_defected_items() {
        if (!loadDefected) {
            loadAllDefected();
            loadDefected = true;
        }
        defects.addAll(this.productManager.getDefects());
        return defects;
    }


    public void remove_product(Integer product_id) throws Exception {
        this.productManager.remove_product(product_id);
    }

    public void remove_category(String cat_name) throws Exception {
        this.productManager.remove_category(cat_name);
    }

    public void set_father(String cat_name, String cat_father_name) throws Exception {
        this.productManager.set_father(cat_name, cat_father_name);
    }

    public LinkedList<GeneralProduct> get_category_products(String cat_name) throws Exception {
        return this.productManager.get_category_products(cat_name);
    }


    public GeneralProduct get_product(Integer product_id) throws Exception {
        return this.productManager.get_product(product_id);
    }

    public void clear_defected() {
        clearDefects();
        defects.clear();
    }


    public void removeItem(Integer product_id, Integer item_id) throws Exception {
        this.productManager.removeItem(product_id, item_id);
    }

    public void update_product_min_amount(Integer product_id, Integer min_amount) throws Exception {
        this.productManager.update_product_min_amount(product_id, min_amount);
    }


    //get_missing_products_with_amounts
    public LinkedList<GeneralProduct> get_missing_products() {
        return this.productManager.get_missing_products();
    }

    public HashMap<Integer, Integer> get_missing_products_with_amounts() {
        return this.productManager.get_missing_products_with_amounts();
    }

    public HashMap<GeneralProduct, Integer> get_missing_General_products_with_amounts() {
        return this.productManager.get_missing_General_products_with_amounts();
    }


    public void set_item_defected(Integer product_id, Integer item_id) throws Exception {
        defects.addLast(this.productManager.set_item_defected(product_id, item_id));
    }

    public LinkedList<String> get_product_categories(GeneralProduct product) throws Exception {
        return this.productManager.get_product_categories(product);
    }


    public LinkedList<String> get_all_products() {
        return this.productManager.get_all_products();
    }

    public LinkedList<String> get_all_categories() {
        return this.productManager.get_all_categories();
    }

    public void update_product_selling_price(int product_id, double price) throws Exception {
        this.productManager.update_product_selling_price(product_id, price);
    }


    ///////////////////////TEST FUNCTIONS/////////////////////////


    public boolean getDefectedItem_Test(int item_id) {
        for (Item i : defects)
            if (i.getItem_id() == item_id)
                return true;
        return false;
    }

    public LinkedList<Item> get_product_items(Integer product_id) throws Exception {
        return this.productManager.get_product_items(product_id);
    }

    //for inside use
    public boolean check_category_exist(String cat_name) {
        return this.productManager.check_category_exist(cat_name);
    }


    public boolean check_product_exist(String prod_name) {
        return this.productManager.check_product_exist(prod_name);
    }

    //======================================================================
//DATA Functions:
    private void add_to_data(Category category) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insertCategory(category)) {
            System.out.println("failed to insert new Category to the database with the name + " + category.getCategory_name());
        }
        im.addCategory(category);
    }

    private void loadAllDefected() {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        LinkedList<Item> defList = dc.loadAllDefected();
        for (Item item : defList) {
            im.addItem(item);
            boolean flag = false;
            for(Item i: defects){
                if (i.getItem_id().equals(item.getItem_id())) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                defects.add(item);
        }
    }
    public ProductManager getPM()
    {
        return  this.productManager;
    }
    private void clearDefects() {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        im.removeAllDefects();
        dc.removeAllDefects();
    }

    public void receiveLastShipment() {
        productManager.receiveLastShipment();
    }
}

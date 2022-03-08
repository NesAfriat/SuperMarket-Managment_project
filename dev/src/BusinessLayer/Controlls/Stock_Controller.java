package BusinessLayer.Controlls;




import BusinessLayer.GeneralProduct;
import BusinessLayer.Item;
import BusinessLayer.Stock;
import BusinessLayer.ProductManager;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Stock_Controller {
    private Stock stock;      //holds all stock ever created
    private static Stock_Controller st_C = null;

    private Stock_Controller() {
        this.stock = new Stock();
    }



    public static Stock_Controller getInstance() {
        if (st_C == null)
            st_C = new Stock_Controller();
        return st_C;
    }

    public Stock getStock()
    {
        if(st_C!=null)
        return stock;
        else
            return null;
    }
    private void check_valid_number(Number[] arr) throws Exception {
        for (Number number : arr) {
            if (number.doubleValue() < 0) {
                throw new Exception("negative number is not allowed");
            }
        }
    }

    private void check_valid_Dates(Date supplied, Date exp) throws Exception {
        Date now = new Date();
        if (supplied.after(now))
            throw new Exception("supplied date / create date can't be after today");
        if (exp.before(now))
            throw new Exception("expiration date can't be before today");
    }

    private void check_valid_string(String[] arr) throws Exception {
        for (String str : arr) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_location(String location) throws Exception {
        if (!location.equals("storage")) {
            if (location.startsWith("store_")) {
                String tmp = location.substring(6);
                String[] arr = tmp.split("_");
                if (arr.length == 2) {
                    if (Integer.parseInt(arr[0]) < 0)
                        throw new Exception("store location number is invalid");
                    if (!isLetter(arr[1]))
                        throw new Exception("store location letter is invalid");
                } else {
                    throw new Exception("not a valid store location");
                }
            } else {
                throw new Exception("not a valid location");
            }
        }
    }

    private boolean isLetter(String str) {
        if (str.length() > 1)
            return false;
        char ch = str.charAt(0);
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }



    public LinkedList<Integer> addItems(Integer product_id, Integer quantity, String location, Date
            supplied_date, Date expiration_date) throws Exception {
        check_valid_string(new String[]{location});
        check_valid_location(location);
        check_valid_number(new Number[]{product_id, quantity});
        check_valid_Dates(supplied_date, expiration_date);
        return stock.addItems(product_id, quantity, location, supplied_date, expiration_date);
    }

    public void update_location(Integer item_id, Integer product_id, String new_location) throws Exception {
        check_valid_string(new String[]{new_location});
        check_valid_location(new_location);
        check_valid_number(new Number[]{item_id, product_id});
        stock.update_location(item_id, product_id, new_location);
    }

    public void createNewCategory(String cat_name) throws Exception {
        check_valid_string(new String[]{cat_name});
        stock.addCategory(cat_name);
    }

    public void remove_product_from_stock(Integer product_id) throws Exception {
        check_valid_number(new Number[]{product_id});
        stock.remove_product(product_id);
    }

    public void remove_category_from_stock(String cat_name) throws Exception {
        check_valid_string(new String[]{cat_name});
        stock.remove_category(cat_name);
    }

    public void set_father(String cat_name, String cat_father_name) throws Exception {
        check_valid_string(new String[]{cat_name, cat_father_name});
        stock.set_father(cat_name, cat_father_name);
    }

    public GeneralProduct get_product_info(Integer product_id) throws Exception {
        check_valid_number(new Number[]{product_id});
        return stock.get_product(product_id);
    }

    public void clear_stock_defected() throws Exception {
        stock.clear_defected();
    }



    public void removeItems(Integer product_id, Integer item_id) throws Exception {
        check_valid_number(new Number[]{product_id, item_id});
        stock.removeItem(product_id, item_id);
    }

    public void update_product_min_amount(Integer product_id, Integer min_amount) throws Exception {
        check_valid_number(new Number[]{product_id, min_amount});
        stock.update_product_min_amount(product_id, min_amount);
    }

    //for inside use
    public LinkedList<GeneralProduct> get_missing_products() {
        return stock.get_missing_products();
    }

    public HashMap<Integer, Integer> get_missing_product_with_amount() {
        return stock.get_missing_products_with_amounts();
    }

    public HashMap<GeneralProduct, Integer> get_missing_General_products_with_amounts() {
    return stock.get_missing_General_products_with_amounts();
}
    //for inside use
    public LinkedList<GeneralProduct> get_category_products(String cat_name) throws Exception {
        return stock.get_category_products(cat_name);
    }

    //for inside use
    public LinkedList<Item> get_defected_items() throws Exception {
        return stock.get_defected_items();
    }

    public void set_item_defected(Integer product_id, Integer item_id) throws Exception {

        check_valid_number(new Number[]{product_id, item_id});
        stock.set_item_defected(product_id, item_id);
    }


    //for inside use
    public LinkedList<String> get_product_categories(GeneralProduct product) throws Exception {
        return stock.get_product_categories(product);
    }

    public LinkedList<String> get_all_products() throws Exception {
        return stock.get_all_products();
    }

    public LinkedList<String> get_all_categories() throws Exception {
        return stock.get_all_categories();
    }

    public void update_product_selling_price(int product_id, double price) throws Exception {
        check_valid_number(new Number[]{product_id, price});
        stock.update_product_selling_price(product_id, price);
    }

    public LinkedList<Item> get_product_items(Integer product_id) throws Exception {
        check_valid_number(new Number[]{product_id});
        return stock.get_product_items(product_id);
    }

    public void check_categories_exist(LinkedList<String> affected_category) throws Exception {
        boolean flag=false;
        for(String cat_name: affected_category)
            flag = flag | stock.check_category_exist(cat_name);

        if(!flag)
            throw new Exception("category does not exist");
    }


    public void check_product_exist(LinkedList<String> affected_products) throws Exception {
        boolean flag=false;
        for(String prod_name: affected_products)
            flag = flag | stock.check_product_exist(prod_name);
        if(!flag)
            throw new Exception("product does not exist");
    }

    public void receiveLastShipment() {
        stock.receiveLastShipment();
    }
}




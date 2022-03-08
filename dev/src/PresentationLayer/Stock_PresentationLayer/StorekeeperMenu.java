package PresentationLayer.Stock_PresentationLayer;

import BusinessLayer.FacedeModel.Objects.Response;
import BusinessLayer.FacedeModel.facade;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class StorekeeperMenu {
    private facade Facade;
    private BufferedReader reader;
    private Response res;

    int inputi;
    String inputs;

    public StorekeeperMenu(facade Facade, BufferedReader reader){
        this.Facade = Facade;
        this.reader = reader;
    }

    public void run() throws IOException {
        System.out.println("Hello, and welcome to the 'Super-Li's Stock' interface");
        boolean flag = true;
        while (flag) {
            System.out.println("Please choose an action:");
            System.out.println("0. Exit\n1. Stocks \n2. Sales \n3. Reports \n ");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 0: {
                    flag = false;
                    break;
                    //should terminate the program
                }
                case 1: {
                    stocks_window();
                    break;
                }
                case 2: {
                    sales_window();
                    break;
                }
                case 3: {
                    report_window();
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }

    private boolean check_action(Response res) {
        if (res.getErrorOccurred()) {
            System.out.println(res.getErrorMsg());
            System.out.println("Action FAILED!");
            return false;
        } else System.out.println("Action SUCCESS!");
        return true;
    }

    public void stocks_window() throws IOException {
        boolean flag = true;
        while (flag) {
            System.out.println("What action would u like to do?");
            System.out.println("1. go back");
            System.out.println("2. show all products");
            System.out.println("3. show all categories");
            System.out.println("4. clear stock defected items");
            System.out.println("5. add new category");
            System.out.println("6. remove category");
            System.out.println("7. print product info");
            System.out.println("8. update category's father");
            System.out.println("9. update min amount of a product");
            System.out.println("10. update selling price");
            System.out.println("11. add new items");
            System.out.println("12. remove item");
            System.out.println("13. show product items info");
            System.out.println("14. update item location");
            System.out.println("15. set item defected");
            System.out.println("16. Receive lst shipment");

            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 1: {
                    flag = false;
                    break;
                }
                case 2: {
                    //print products
                    res = Facade.show_all_products();
                    if (check_action(res))
                        System.out.println("All products:\n"+res.print());
                    break;
                }
                case 3: {
                    //print categories
                    res = Facade.show_all_categories();
                    if (check_action(res))
                        System.out.println("All categories:\n"+res.print());
                    break;
                }
                case 4: {
                    //clear defected
                    res = Facade.clear_stock_defected();
                    check_action(res);
                    break;
                }
                case 5: {
                    //create category
                    System.out.println("please type the category name: ");
                    inputs = reader.readLine().trim().toLowerCase();
                    res = Facade.create_category(inputs);
                    check_action(res);
                    break;
                }
                case 6: {
                    //remove category
                    System.out.println("please enter the category name: ");
                    String cat_name = reader.readLine().trim().toLowerCase();
                    res = Facade.remove_category_from_stock(cat_name);
                    check_action(res);
                    break;
                }
                case 8: {
                    //update category father
                    System.out.println("please type the category name u want to set a father: ");
                    String cat_name = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the father category name: ");
                    String father_name = reader.readLine().trim().toLowerCase();
                    res = Facade.set_father(cat_name, father_name);
                    check_action(res);
                    break;
                }


                case 7: {
                    //product info
                    System.out.println("please enter the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    res = Facade.get_product_info(product_id);
                    if (check_action(res)) {
                        System.out.println(res.print());
                    }
                    break;
                }


                case 9: {
                    //update min amount
                    System.out.println("please type the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please enter a new min amount: ");
                    int amount = Integer.parseInt(reader.readLine());
                    res = Facade.update_product_min_amount(product_id, amount);
                    check_action(res);
                    break;
                }
                case 10: {
                    //update selling price
                    System.out.println("please type the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please enter a new selling price: ");
                    double price = Double.parseDouble(reader.readLine());
                    res = Facade.update_product_selling_price(product_id, price);
                    check_action(res);
                    break;
                }
                case 11: {
                    //add items to product
                    System.out.println("please type the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please type the quantity: ");
                    int quantity = Integer.parseInt(reader.readLine());
                    System.out.println("please type the location which the items are stored (<storage>\\<store_number_letter>): ");
                    String loc = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the supplied date (YYYY-MM-DD)");
                    String sup_date = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the expiration date (YYYY-MM-DD)");
                    String exp_date = reader.readLine().trim().toLowerCase();
                    res = Facade.add_product_items(product_id, quantity, loc, sup_date, exp_date);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 12: {
                    //remove item
                    System.out.println("please type the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please type the item id: ");
                    int item_id = Integer.parseInt(reader.readLine());
                    res = Facade.remove_product_items(product_id, item_id);
                    check_action(res);
                    break;
                }
                case 13:{
                    //get product items info
                    System.out.println("please enter the product id");
                    inputi = Integer.parseInt(reader.readLine());
                    res = Facade.get_product_items(inputi);
                    if (check_action(res)) {
                        System.out.println(res.print());
                    }
                    break;
                }
                case 14: {
                    //update item location
                    System.out.println("please type product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please type the item id: ");
                    int item_id = Integer.parseInt(reader.readLine());
                    System.out.println("please type new location for the item (<storage>\\<store_number_letter>): ");
                    String loc = reader.readLine().trim().toLowerCase();
                    res = Facade.update_location(item_id, product_id, loc);
                    check_action(res);
                    break;
                }
                case 15: {
                    //set item defected
                    System.out.println("please type the product id: ");
                    int product_id = Integer.parseInt(reader.readLine());
                    System.out.println("please type the item id: ");
                    int item_id = Integer.parseInt(reader.readLine());
                    res = Facade.set_item_defected(product_id, item_id);
                    check_action(res);
                    break;
                }
                case 16: {
                    res = Facade.receiveLastShipment();
                    check_action(res);
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }

    public void sales_window() throws IOException {

        boolean flag = true;
        while (flag) {
            System.out.println("What action would u like to do?");
            System.out.println("0. go back");
            System.out.println("1. add a new category sale");
            System.out.println("2. add new products sale");
            System.out.println("3. update all sales");
            System.out.println("4. remove sale");
            System.out.println("5. inspect all sales");
            System.out.println("6. get info of a category sale");
            System.out.println("7. get info of a product sale");
            System.out.println("8. update sale description");
            System.out.println("9. update sale discount");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    //add sale-category
                    System.out.println("please type the discount of the sale: ");
                    double disc = Double.parseDouble(reader.readLine());
                    System.out.println("please type the description of the sale: ");
                    String desc = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the start date: (YYYY-MM-DD)");
                    String start_date = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the end date: (YYYY-MM-DD)");
                    String end_date = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the names of the categories affected by the sale: (type q for exit)");
                    inputs = reader.readLine().trim().toLowerCase();
                    LinkedList<String> cat = new LinkedList<>();
                    while (!inputs.equals("q")) {
                        cat.add(inputs);
                        inputs = reader.readLine().trim().toLowerCase();
                    }
                    res = Facade.add_sale_by_category(disc, desc, start_date, end_date, cat);
                    check_action(res);
                    break;
                }
                case 2: {
                    //add sale-products
                    System.out.println("please type the discount of the sale: ");
                    double disc = Double.parseDouble(reader.readLine());
                    System.out.println("please type the description of the sale: ");
                    String desc = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the start date: (YYYY-MM-DD)");
                    String start_date = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the end date: (YYYY-MM-DD)");
                    String end_date = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the names of the products affected by the sale: (type q for exit)");
                    inputs = reader.readLine().trim().toLowerCase();
                    LinkedList<String> prod = new LinkedList<>();
                    while (!inputs.equals("q")) {
                        prod.add(inputs);
                        inputs = reader.readLine().trim().toLowerCase();
                    }
                    res = Facade.addSaleByProduct(disc, desc, start_date, end_date, prod);
                    check_action(res);
                    break;
                }
                case 3: {
                    //update all sales
                    res = Facade.updateSales();
                    check_action(res);
                    break;
                }
                case 4: {
                    //remove sale
                    System.out.println("please type the sale id you want to remove: ");
                    inputi = Integer.parseInt(reader.readLine());
                    res = Facade.removeSale(inputi);
                    check_action(res);
                    break;
                }
                case 5: {
                    // inspect all sales
                    res = Facade.all_sales();
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 6: {
                    // get info of a specific sale-category
                    System.out.println("please type category name: ");
                    inputs = reader.readLine().trim().toLowerCase();
                    res = Facade.get_sale_by_category_name(inputs);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 7: {
                    // get info of a specific sale-product
                    System.out.println("please type product name: ");
                    inputs = reader.readLine().trim().toLowerCase();
                    res = Facade.get_sale_by_product_name(inputs);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 8: {
                    //update description
                    System.out.println("please type the sale id: ");
                    inputi = Integer.parseInt(reader.readLine());
                    System.out.println("please enter a new description: ");
                    String desc = reader.readLine().trim().toLowerCase();
                    res = Facade.update_sale_description(inputi, desc);
                    check_action(res);
                    break;
                }
                case 9: {
                    //update discount
                    System.out.println("please type the sale id: ");
                    inputi = Integer.parseInt(reader.readLine());
                    System.out.println("please enter a new discount: ");
                    double disc = Double.parseDouble(reader.readLine());
                    res = Facade.update_sale_discount(inputi, disc);
                    check_action(res);
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }

    public void report_window() throws IOException {

        boolean flag = true;
        while (flag) {
            System.out.println("What action would u like to do?");
            System.out.println("0. go back");
            System.out.println("1. create a new report");
            System.out.println("2. get report by id");
            System.out.println("3. get report by subject and date");
            System.out.println("4. show all reports");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            switch (inputi) {
                case 0: {
                    flag = false;
                    break;
                }
                case 1: {
                    //create report
                    System.out.println("please type the subject of the report: (Stock, Missing, Defects)");
                    String sub = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the time range: (d-Daily, w-Weekly, m-Monthly)");
                    String time = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the names of the categories: (type q for exit)");
                    inputs = reader.readLine().trim().toLowerCase();
                    LinkedList<String> cat = new LinkedList<>();
                    while (!inputs.equals("q")) {
                        cat.add(inputs);
                        inputs = reader.readLine().trim().toLowerCase();
                    }
                    res = Facade.createReport(sub, time, cat);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 2: {
                    //get report by id
                    System.out.println("please type the report id");
                    inputi = Integer.parseInt(reader.readLine());
                    res = Facade.get_report_by_id(inputi);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 3: {
                    //get report by sub and date
                    System.out.println("please type the subject(Missing, Defects, Stock");
                    inputs = reader.readLine().trim().toLowerCase();
                    System.out.println("please type the date (YYYY-MM-DD)");
                    String date = reader.readLine().trim().toLowerCase();
                    res = Facade.get_reports_id(inputs, date);
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                case 4: {
                    //show all report
                    res = Facade.show_all_reports();
                    if (check_action(res))
                        System.out.println(res.print());
                    break;
                }
                default: {
                    System.out.println("not valid input, please try again");
                }
            }
        }
    }
}


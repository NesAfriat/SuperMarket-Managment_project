package BusinessLayer.FacedeModel;

import BusinessLayer.Controlls.Reports_Controller;
import BusinessLayer.Controlls.Sales_Controller;
import BusinessLayer.Controlls.Stock_Controller;
import BusinessLayer.FacedeModel.Objects.*;
import BusinessLayer.GeneralProduct;
import BusinessLayer.Item;
import BusinessLayer.Reports.Report;
import BusinessLayer.Sales.Sale;
import BusinessLayer.ProductManager;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class inventModel {
    private Stock_Controller stock_controller;
    private Sales_Controller sales_controller;
    private Reports_Controller reports_controller;

    public inventModel() {
        this.stock_controller = Stock_Controller.getInstance();
        this.sales_controller = Sales_Controller.getInstance();
        this.reports_controller = Reports_Controller.getInstance();
    }

    public Stock_Controller getStockC() {
        return stock_controller;
    }

    public Sales_Controller getSalesC() {
        return sales_controller;
    }

    public static Date getDate(String date) throws ParseException {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }

    public static String getDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

//=============================================STOCK=================================================

    /**
     * create a new category
     *
     * @param cat_name
     * @return
     */
    public Response create_category(String cat_name) {
        Response res;
        try {
            stock_controller.createNewCategory(cat_name);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }


    /**
     * remove a category from the stock
     *
     * @param cat_name
     * @return
     */
    public Response remove_category_from_stock(String cat_name) {
        Response res;
        try {
            stock_controller.remove_category_from_stock(cat_name);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * set a father to a category
     *
     * @param cat_name
     * @param cat_father_name
     * @return
     */
    public Response set_father(String cat_name, String cat_father_name) {
        Response res;
        try {
            stock_controller.set_father(cat_name, cat_father_name);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * get a specific product (return its info with items)
     *
     * @param product_id
     * @return
     */
    public Response get_product_info(Integer product_id) {
        ResponseT<ProductRes> res;
        try {
            ProductRes productRes;
            GeneralProduct product = stock_controller.get_product_info(product_id);
            double max_disc = get_Max_disc_for_product(product);
            productRes = new ProductRes(product, max_disc/100);
            res = new ResponseT<>(productRes);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * for inside use
     * return the max discount for a produce between sales on his categories and his product sale
     * @param product
     * @return
     * @throws Exception
     */
    private double get_Max_disc_for_product(GeneralProduct product) throws Exception {
        return Math.max(sales_controller.getDiscountByProduct(product.getProduct_name()), sales_controller.getDiscountByCategory(stock_controller.get_product_categories(product)));
    }

    /**
     * clear the defected items of a stock
     *
     * @return
     */
    public Response clear_stock_defected() {
        Response res;
        try {
            stock_controller.clear_stock_defected();
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }



    /**
     * update an item location in stock
     *
     * @param item_id
     * @param new_location
     * @return
     */
    public Response update_location(Integer item_id, Integer product_id, String new_location) {
        Response res;
        try {
            stock_controller.update_location(item_id, product_id, new_location);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * add new items of a product
     *
     * @param product_id
     * @param quantity
     * @param location
     * @param supplied_date
     * @param expiration_date
     * @return
     */
    public Response add_product_items(Integer product_id, Integer quantity, String location, String supplied_date, String expiration_date) {
        ResponseT<LinkedList<Integer>> res;
        try {
            LinkedList<Integer> itemsIds = stock_controller.addItems(product_id, quantity, location, getDate(supplied_date), getDate(expiration_date));
            res = new ResponseT<>(itemsIds);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * remove an item from the stock
     *
     * @param product_id
     * @param item_id
     * @return
     */
    public Response remove_product_items(Integer product_id, Integer item_id) {
        Response res;
        try {
            stock_controller.removeItems(product_id, item_id);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * update a product min amount required in stock
     *
     * @param product_id
     * @param min_amount
     * @return
     */
    public Response update_product_min_amount(Integer product_id, Integer min_amount) {
        Response res;
        try {
            stock_controller.update_product_min_amount(product_id, min_amount);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * set item defected
     *
     * @param product_id
     * @param item_id
     * @return
     */
    public Response set_item_defected(Integer product_id, Integer item_id) {
        Response res;
        try {
            stock_controller.set_item_defected(product_id, item_id);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * return list of all products id's and names
     *
     * @return
     */
    public Response show_all_products() {
        ResponseT<LinkedList<String>> res;
        try {
            LinkedList<String> names = stock_controller.get_all_products();
            res = new ResponseT<>(names);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return list of all categories names
     *
     * @return
     */
    public Response show_all_categories() {
        ResponseT<LinkedList<String>> res;
        try {
            LinkedList<String> names = stock_controller.get_all_categories();
            res = new ResponseT<>(names);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * update product selling price
     *
     * @param product_id
     * @param price
     * @return
     */
    public Response update_product_selling_price(int product_id, double price) {
        Response res;
        try {
            stock_controller.update_product_selling_price(product_id, price);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }
    public Response get_product_items(Integer product_id) {
        ResponseT<LinkedList<ItemRes>> res;
        try {
            LinkedList<Item> items = stock_controller.get_product_items(product_id);
            LinkedList<ItemRes> itemRes = new LinkedList<>();
            for (Item item : items) {
                itemRes.addLast(new ItemRes(item));
            }
            res = new ResponseT<>(itemRes);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }
//=============================================SALES=================================================

    /**
     * add new category-sale
     *
     * @param discount
     * @param desc
     * @param start_date
     * @param end_date
     * @param affected_category
     * @return
     */
    public Response add_sale_by_category(Double discount, String desc, String start_date, String end_date, LinkedList<String> affected_category) {
        Response res;
        try {
            stock_controller.check_categories_exist(affected_category);
            sales_controller.createSaleByCategory(discount, desc, getDate(start_date), getDate(end_date), affected_category);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * add new product-sale
     *
     * @param discount
     * @param desc
     * @param start_date
     * @param end_date
     * @param affected_Products
     * @return
     */
    public Response addSaleByProduct(Double discount, String desc, String start_date, String end_date, LinkedList<String> affected_Products) {
        Response res;
        try {
            stock_controller.check_product_exist(affected_Products);
            sales_controller.createSaleByProduct(discount, desc, getDate(start_date), getDate(end_date), affected_Products);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * update all sales
     *
     * @return
     */
    public Response updateSales() {
        Response res;
        try {
            sales_controller.updateSales();
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * delete a sale
     *
     * @param sale_id
     * @return
     */
    public Response removeSale(int sale_id) {
        Response res;
        try {
            sales_controller.removeSale(sale_id);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * return all available sales
     *
     * @return
     */
    public Response all_sales() {
        ResponseT<LinkedList<SaleRes>> res;
        try {
            LinkedList<SaleRes> salesByCategory = new LinkedList<>();
            LinkedList<Sale> returnedSales = sales_controller.allSales();
            for (Sale sl : returnedSales) {
                salesByCategory.add(new SaleRes(sl));
            }
            res = new ResponseT<>(salesByCategory);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return all sales which available for specific category
     *
     * @param cat_name
     * @return
     */
    public Response get_sale_by_category_name(String cat_name) {
        ResponseT<LinkedList<SaleRes>> res;
        try {
            LinkedList<SaleRes> salesByCategory = new LinkedList<>();
            LinkedList<Sale> returnedSales = sales_controller.getSalesByCategory(cat_name);
            for (Sale sl : returnedSales) {
                salesByCategory.add(new SaleRes(sl));
            }
            res = new ResponseT<>(salesByCategory);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return all sales available for a specific product
     *
     * @param product
     * @return
     */
    public Response get_sale_by_product_name(String product) {
        ResponseT<LinkedList<SaleRes>> res;
        try {
            LinkedList<SaleRes> salesByProd = new LinkedList<>();
            LinkedList<Sale> returnedSales = sales_controller.getSalesByProduct(product);
            for (Sale sl : returnedSales) {
                salesByProd.add(new SaleRes(sl));
            }
            res = new ResponseT<>(salesByProd);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * update sale description
     *
     * @param sale_id
     * @param desc
     * @return
     */
    public Response update_sale_description(int sale_id, String desc) {
        Response res;
        try {
            sales_controller.update_sale_description(sale_id, desc);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

    /**
     * update sale discount percentage
     *
     * @param sale_id
     * @param disc
     * @return
     */
    public Response update_sale_discount(int sale_id, double disc) {
        Response res;
        try {
            sales_controller.update_sale_discount(sale_id, disc);
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }

//=============================================REPORTS=================================================

    /**
     * create a new report
     *
     * @param subject
     * @param timeRange
     * @param categories
     * @return
     */
    public Response createReport(String subject, String timeRange, LinkedList<String> categories) {
        ResponseT<ReportRes> res;
        try {
            stock_controller.check_categories_exist(categories);
            Report report = reports_controller.createReport(subject, timeRange, categories);
            ReportRes reportRes = new ReportRes(report);
            res = new ResponseT<>(reportRes);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return info of a specific report
     *
     * @param report_id
     * @return
     */
    public Response get_report_by_id(Integer report_id) {
        ResponseT<ReportRes> res;
        try {
            Report report = reports_controller.getReportById(report_id);
            ReportRes reportRes = new ReportRes(report);
            res = new ResponseT<>(reportRes);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return all reports created at {date} from the {subject}
     *
     * @param subject
     * @param date
     * @return
     */
    public Response get_reports_id(String subject, String date) {
        ResponseT<LinkedList<Integer>> res;
        try {
            LinkedList<Integer> ids = reports_controller.getReportId(subject, getDate(date));
            res = new ResponseT<>(ids);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }

    /**
     * return all report's ids,subject and dates
     *
     * @return
     */
    public Response show_all_reports() {
        LinkedList<ReportRes> repRes= new LinkedList<>();
        ResponseT<LinkedList<ReportRes>> res;
        try {
            LinkedList<Report> reports = reports_controller.get_all_reports();
            for(Report r: reports)
                 repRes.add(new ReportRes(r));
            res = new ResponseT<>(repRes);
        } catch (Exception e) {
            res = new ResponseT<>(e.getMessage());
        }
        return res;
    }



    //=============================================INTEGRATION=================================================

    //for suppliers use!!!
    private HashMap<Integer, Integer> get_missing_products_for_order() throws Exception {
        return stock_controller.get_missing_product_with_amount();
    }

    public Response receiveLastShipment() {
        Response res;
        try {
            stock_controller.receiveLastShipment();
            res = new Response();
        } catch (Exception e) {
            res = new Response(e.getMessage());
        }
        return res;
    }
}





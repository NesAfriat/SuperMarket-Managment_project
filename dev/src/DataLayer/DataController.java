package DataLayer;

import BusinessLayer.*;
import BusinessLayer.Reports.Report;
import BusinessLayer.OrderBuissness.Order;
import BusinessLayer.Sales.Sale;
import BusinessLayer.SupplierBuissness.Contact;
import BusinessLayer.SupplierBuissness.Supplier;
import DataLayer.Mappers.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class DataController {
    private static DataController instance = null;
    private AffectedProductsMapper apMapper;
    private AffectedCategoriesMapper acMapper;
    private AgreementDeliveryDaysMapper addMapper;
    private AgreementProductDiscMapper apdMapper;
    private AgreementsMapper agreementsMapper;
    private CategoriesMapper categoriesMapper;
    private DefectsItemsMapper defectsItemsMapper;
    private GeneralProductMapper generalProductMapper;
    private ItemMapper itemMapper;
    private OrderProductsMapper orderProductsMapper;
    private OrdersMapper ordersMapper;
    private Reports_CategoriesMapper rcMapper;
    private ReportsMapper reportsMapper;
    private SalesMapper salesMapper;
    private SuppliersMapper suppliersMapper;
    private SuppliersContactsMapper suppliersContactsMapper;
    private SuppliersProductsMapper suppliersProductsMapper;
    private ArrivedShipmentMapper arrivedShipmentMapper;


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

    public static DataController getInstance() {
        if (instance == null) {
            instance = new DataController();
        }
        return instance;
    }

    private DataController() {
        generalProductMapper = new GeneralProductMapper();
        suppliersMapper = new SuppliersMapper();
        categoriesMapper = new CategoriesMapper();
        salesMapper = new SalesMapper();
        reportsMapper = new ReportsMapper();
        agreementsMapper = new AgreementsMapper(); //needs suppliers
        suppliersContactsMapper = new SuppliersContactsMapper(); //needs suppliers
        ordersMapper = new OrdersMapper(); //needs supplier
        addMapper = new AgreementDeliveryDaysMapper(); //needs agreement
        itemMapper = new ItemMapper(); //needs gp
        defectsItemsMapper = new DefectsItemsMapper(); //needs gp
        apMapper = new AffectedProductsMapper(); //needs sales+gp
        acMapper = new AffectedCategoriesMapper(); //needs sales+categories
        rcMapper = new Reports_CategoriesMapper(); //needs report+categories
        suppliersProductsMapper = new SuppliersProductsMapper(); //needs supplier+gp
        apdMapper = new AgreementProductDiscMapper(); //needs supplier+supProd
        orderProductsMapper = new OrderProductsMapper(); //needs order+supProd
        arrivedShipmentMapper = new ArrivedShipmentMapper();

        reportsMapper.setRCM(rcMapper);
        salesMapper.setAcm(acMapper);
        salesMapper.setApm(apMapper);
    }

    //================================================================================
    //Item Actions:
    //If we want to retrieve an item which was not in the business
    public Item getItem(int product_id, int item_id) {
        Item ip = itemMapper.getItem(product_id, item_id);
        return ip;
    }

    //If we want to make entire new record of an item
    public boolean insertItem(Item obj) {
        return itemMapper.insertItem(obj);
    }

    public boolean update(Item obj) {
        return itemMapper.update(obj);
    }

    public boolean delete(Item obj) {
        return itemMapper.delete(obj);
    }

    //================================================================================
    //GeneralProduct Actions:
    //If we want to retrieve an product which was not in the business
    //load all the gp's objects
    public GeneralProduct getGP(int product_id) {
        GeneralProduct gp = generalProductMapper.getGeneralProduct(product_id);
        itemMapper.addItemToProduct(gp); //add gp items
//        suppliersProductsMapper.addPStoProduct(gp); //add gp ps
        return gp;
    }

    public String getGPCategory(GeneralProduct gp) {
        return generalProductMapper.getGPCategory(gp);
    }

    public void updateGPCategoryDAL(GeneralProduct gp, String catName) {
        generalProductMapper.updateGPCategoryDAL(gp, catName);
    }

    //If we want to make entire new record of an gp
    public boolean insertGP(GeneralProduct obj, String catName) {
        return generalProductMapper.insertProduct(obj, catName);
    }

    public boolean CheckGPInputExist(String product_name, String manufacturer_name) {
        return generalProductMapper.checkNamesExist(product_name, manufacturer_name);
    }

    public boolean checkPrductExist(Integer gpID) {
        return generalProductMapper.checkProductExist(gpID);
    }

    public boolean checkPrductExist(String gpName) {
        return generalProductMapper.checkProductExist(gpName);
    }

    public boolean update(GeneralProduct obj) {
        return generalProductMapper.update(obj);
    }

    public boolean delete(GeneralProduct obj) {
        return generalProductMapper.delete(obj);
    }

    public LinkedList<GeneralProduct> loadAllGeneralProducts() {
        LinkedList<GeneralProduct> gps = generalProductMapper.loadAllProducts();
        for (GeneralProduct gp : gps) {
            itemMapper.addItemToProduct(gp); //add gp items
//            suppliersProductsMapper.addPStoProduct(gp); //add gp ps
        }
        return gps;
    }

    public LinkedList<GeneralProduct> get_category_products_DAL(String cat_name) {
        LinkedList<GeneralProduct> gps = generalProductMapper.loadProductsByCategory(cat_name);
        for (GeneralProduct gp : gps) {
            itemMapper.addItemToProduct(gp); //add gp items
//            suppliersProductsMapper.addPStoProduct(gp); //add gp ps
        }
        return gps;
    }
    //================
    //Categories

    public Category getCategory(String cat_name) {
        Category cat = categoriesMapper.getCategory(cat_name);
        return cat;
    }

    public boolean insertCategory(Category category) {
        return categoriesMapper.insertCategory(category);
    }

    public boolean update(Category obj) {
        return categoriesMapper.update(obj);
    }

    public boolean delete(Category obj) {
        return categoriesMapper.delete(obj);
    }

    public boolean setFather(Category cat, Category father_cat) {

        return categoriesMapper.setFather(cat, father_cat);
    }


    public LinkedList<Category> loadAllCategoreis() {
        return categoriesMapper.loadAllCategories();
    }

    public String getFatherCategory(Category cat) {
        return categoriesMapper.getFatherCategory(cat);
    }

    public LinkedList<String> getChildrenCategories(Category cat) {
        return categoriesMapper.getChildrenCategories(cat);
    }

    //=============================
    //reports

    public int getMaxRepID() {
        return reportsMapper.getMaxReportID();
    }

    public Report getReport(int rID) {
        Report report = reportsMapper.getReport(rID);
        return report;
    }

    public boolean insertReport(Report report) {
        return reportsMapper.insert(report);
    }

    public boolean update(Report report) {
        return reportsMapper.update(report);
    }

    public boolean delete(Report report) {
        return reportsMapper.delete(report);
    }

    public LinkedList<Integer> getReportsIDs(String sub, String date) {
        return reportsMapper.getIDs(sub, date);
    }

    public LinkedList<Report> loadAllReports() {
        return reportsMapper.loadAllReports();
    }

    //================================================================================
    //Suppliers Actions:
    //If we want to retrive an suplpier which was not in the business
    public Supplier getSupplier(int supplier_id) {
        Supplier sup = suppliersMapper.getSupplier(supplier_id);
        if(sup!=null){
            suppliersContactsMapper.addAllContactsToSupplier(sup);
            sup.setContactIdCounter(suppliersContactsMapper.getBigestId(supplier_id)+1);
        }
        return sup;
    }


    public List<Supplier> getAllSupplier() {
        List<Supplier> sup_list = suppliersMapper.getALLSupplier();
        for (Supplier s : sup_list
        ) {
            suppliersContactsMapper.addAllContactsToSupplier(s);
        }
        return sup_list;
    }


    //If we want to make entire new record of an item
    public boolean insertSupplier(Supplier sup) {
        return suppliersMapper.insertSupplier(sup);
    }

    public boolean update(Supplier obj) {
        return suppliersMapper.update(obj);
    }

    public boolean delete(Supplier obj) {
        return suppliersMapper.delete(obj);
    }

    //================================================================================
    //Contacs Actions:
    //If we want to retrive an contact which was not in the business
    public Contact getContact(int sup_id, int con_id) {
        Contact cntc = suppliersContactsMapper.getContact(sup_id, con_id);
        return cntc;
    }

    //If we want to make entire new record of an item
    public boolean insertContact(Contact obj, int sup_id) {
        return suppliersContactsMapper.insertContact(obj, sup_id);
    }

    public boolean update(Contact con, int sup_id) {
        return suppliersContactsMapper.update(con, sup_id);
    }

    public boolean delete(Contact con, int sup_id) {
        return suppliersContactsMapper.delete(con, sup_id);
    }

    //================================================================================
    //ProductSuppliers Actions:
    //If we want to retrive an PS which was not in the business
    public ProductSupplier getPS(int sup_id, int catID) {
        ProductSupplier ps = suppliersProductsMapper.getProductSupplier(sup_id, catID);
        return ps;
    }

    //If we want to make entire new record of an item
    public boolean insertPS(ProductSupplier obj, int sup_id) {
        return suppliersProductsMapper.insertProduct(obj, sup_id);
    }

    public boolean updateProductSupplier(ProductSupplier con, int sup_id) {
        return suppliersProductsMapper.update(con, sup_id);
    }

    public boolean deleteProductSupplier(ProductSupplier con, int sup_id) {
        return suppliersProductsMapper.delete(con, sup_id);
    }

    //================================================================================
    //Orders Actions:
    //If we want to retrive an Order which was not in the business

    public Order getOrder(int sup_id, int oID) {
        Order o = ordersMapper.getOrder(sup_id, oID);
        orderProductsMapper.addProductsToOrder(o);// this function add all product to the order
        return o;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = ordersMapper.getAllOrders();
        for (Order o : orderList
        ) {
            orderProductsMapper.addProductsToOrder(o);
        }
        return orderList;
    }

    //If we want to make entire new record of an item
    public boolean insetOrder(Order o) {
        return ordersMapper.insertOrder(o);
    }

    public boolean updateOrder(Order o) {
        return ordersMapper.update(o);
    }

    public boolean deleteOrder(Order o) {
        boolean b= ordersMapper.delete(o);
        if(b){
            for (int catalogId:o.getProductQuantity().keySet()
            ) {
                boolean t=orderProductsMapper.delete(o.GetId(),catalogId);
                if(!t) return false;
            }
            return true;
        }
        return false;
    }

    public boolean addAgreementDeliveryDaysAgreement(int SupID, int Day) {
        return addMapper.addAgreementDeliveryDaysAgreement(SupID, Day);
    }

    public boolean RemoveAgreementDeliveryDays(int SupID, int Day) {
        return addMapper.RemoveAgreementDeliveryDays(SupID, Day);
    }


    public boolean addQuantityDiscAgreement(int SupId, int catalogId, int quantity, double Price) {
        return apdMapper.addQuantityDiscAgreement(SupId, catalogId, quantity, Price);
    }

    public boolean RemoveQuantityDiscAgreement(int SupId, int catalogId, int quantity) {
        return apdMapper.RemoveQuantityDiscAgreement(SupId, catalogId, quantity);

    }


    public boolean UpdateQuantityDiscAgreement(int SupId, int catalogId, int quantity, int price) {
        return apdMapper.UpdateQuantityDiscAgreement(SupId, catalogId, quantity, price);
    }

    //================================================================================
    //Agreement Actions:
    //If we want to retrive an Order which was not in the business
    public Agreement getAgreement(int sup_id) {
        Agreement agr = agreementsMapper.getAgreement(sup_id);
        suppliersProductsMapper.addPStoAgreement(agr); //SupplierProducts
        apdMapper.addQuantityDiscAgreement(agr); //DiscByQuantity
        addMapper.addDaysDelivery(agr); //DeliveryDays

        return agr;
    }

    public List<Agreement> getAllAgrements() {
        List<Agreement> agreementList = agreementsMapper.getAllAgreement();
        for (Agreement agr : agreementList
        ) {
            suppliersProductsMapper.addPStoAgreement(agr); //SupplierProducts
            apdMapper.addQuantityDiscAgreement(agr); //DiscByQuantity
            addMapper.addDaysDelivery(agr); //DeliveryDays
        }
        return agreementList;
    }
    public int get_gpId_from_Suppliers(int catalogID, int supID) {
        return suppliersProductsMapper.get_gpId(catalogID, supID); //SupplierProducts
    }
    //If we want to make entire new record of an item
    public boolean insertAgreement(Agreement a) {
        return agreementsMapper.insertAgreement(a);
    }

    public boolean updateAgreemnent(Agreement a) {
        return agreementsMapper.update(a);
    }

    public boolean deleteAreementFronData(Agreement a) {
        return agreementsMapper.delete(a);
    }


    //===================================order!!!!!!!=============================================
//productQuantity

    public boolean updateProductInOrder(int orderId, int catalogID, int quantity) {
        return orderProductsMapper.update(orderId, catalogID, quantity);
    }

    //removeProduct
    public boolean removeProductFromORder(int orderId, int catalogID) {
        return orderProductsMapper.delete(orderId, catalogID);
    }

    //
    public boolean insetProduct(int orderId, int catalogID, int quantity) {
        return orderProductsMapper.insetProduct(orderId, catalogID, quantity);
    }

    //================================================================================


    //Item Actions:
    //If we want to retrive an item which was not in the business
    public Item getDefectedItem(int product_id, int item_id) {
        Item ip = defectsItemsMapper.getDefectedItem(product_id, item_id);
        return ip;
    }

    //If we want to make entire new record of an item
    public boolean insertDefected(Item obj) {
        return defectsItemsMapper.insertDefectedItem(obj);
    }

    public boolean updateDefected(Item obj) {
        return defectsItemsMapper.update(obj);
    }

    public boolean deleteDefected(Item obj) {
        return defectsItemsMapper.delete(obj);
    }

    public LinkedList<Item> loadAllDefected() {
        return defectsItemsMapper.loadAllDefected();
    }

    public void removeAllDefects() {
        defectsItemsMapper.removeAllDefects();
    }

    //================================================================================
    //Sales Actions:
    public LinkedList<Sale> getSaleByProduct(String product) {
        return salesMapper.getSaleByProduct(product);
    }

    public LinkedList<Sale> getSaleByCategory(String category) {
        return salesMapper.getSaleByCategory(category);
    }

    public Sale getSaleByID(int sale_id) {
        return salesMapper.getSaleByID(sale_id);
    }

    //If we want to make entire new record of an item
    public boolean insertSaleByProduct(Sale s) {
        return salesMapper.insertSaleByProduct(s);
    }

    public boolean insertSaleByCategory(Sale s) {
        return salesMapper.insertSaleByCategory(s);
    }

    public boolean update(Sale s) {
        return salesMapper.update(s);
    }

    public boolean delete(Sale s) {
        return salesMapper.delete(s);
    }

    public LinkedList<Sale> loadAllSales() {
        return salesMapper.loadAllSales();
    }


    public void changeGPCategory(LinkedList<GeneralProduct> products, Category father) {
        for (GeneralProduct prod : products) {
            generalProductMapper.setGPCategory(prod, father.getCategory_name());
        }
    }

    public Integer getMaxSalesID() {
        return salesMapper.getMaxSaleID();
    }
    public int getmaxgpid(){
        return suppliersProductsMapper.getMaxPGcounterNumber();
    }

    public int getMaxGPID() {
        return generalProductMapper.getMaxGPID();
    }
    public int getTheBigestIDforTheCounterinContacts(int Supid){
        return suppliersContactsMapper.getBigestId(Supid);
    }

    public int getOrderBigestId(){
        int order=orderProductsMapper.getOrderIdCounterBigest();
        return order;
    }

    //stock-shipment functions


    public HashMap<Integer, Integer> getLastShipment() {
        HashMap<Integer, Integer> supply = arrivedShipmentMapper.getLastShipment();
        arrivedShipmentMapper.cleanShipment();
        return supply;
    }
    public void insertArrivedShipment(int gpID, int quantity) {
        try{
            arrivedShipmentMapper.insert(gpID, quantity);
        }catch(SQLException e){
            arrivedShipmentMapper.addQuantity(gpID,quantity);
        }
    }
}

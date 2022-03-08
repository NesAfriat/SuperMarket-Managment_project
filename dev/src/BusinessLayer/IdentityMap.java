package BusinessLayer;


import BusinessLayer.Reports.Report;
import BusinessLayer.OrderBuissness.Order;
import BusinessLayer.Sales.Sale;
import BusinessLayer.SupplierBuissness.Contact;
import BusinessLayer.SupplierBuissness.Supplier;


import java.util.LinkedList;

public class IdentityMap {
    private static IdentityMap instance = null;
    private LinkedList<Item> itemList;
    private LinkedList<Item> defctedItemsList;
    private LinkedList<GeneralProduct> generalProductList;
    private LinkedList<Category> categoryList;
    private LinkedList<Supplier> suppliersList;
    private LinkedList<Report> reportsList;
    private LinkedList<Contact> contactsList;
    private LinkedList<ProductSupplier> productSuppliersList;
    private LinkedList<Order> ordersList;
    private LinkedList<Agreement> agreementsList;
    private LinkedList<Sale> salesList;


    public static IdentityMap getInstance() {
        if (instance == null) {
            instance = new IdentityMap();
        }
        return instance;
    }

    private IdentityMap() {
        itemList = new LinkedList<>();
        categoryList = new LinkedList<>();
        suppliersList = new LinkedList<>();
        generalProductList = new LinkedList<>();
        reportsList = new LinkedList<>();
        contactsList = new LinkedList<>();
        productSuppliersList = new LinkedList<>();
        ordersList = new LinkedList<>();
        agreementsList = new LinkedList<>();
        defctedItemsList = new LinkedList<>();
        salesList = new LinkedList<>();
    }

    //================================================================================
    //add an item to the identityMap
    public void addItem(Item item) {
        itemList.add(item);
    }

    //if this function return null - go to the db
    public Item getItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : itemList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
        }
        return output;
    }

    public Item removeItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : itemList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id) {
                output = item;
                itemList.remove(item);
                return output;
            }
        }
        return output;
    }

    //================================================================================
    //function which retrieve the info from the database
    public void addGeneralProduct(GeneralProduct product) {
        boolean flag;
        flag = false;
        for (GeneralProduct gp : generalProductList) {
            if (product.getProduct_id().equals(gp.getProduct_id())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            generalProductList.add(product);
        }
        for (Item i : product.get_items()) {
            flag = false;
            for (Item item : itemList) {
                if (i.getItem_id().equals(item.getItem_id()) && i.getProduct_id().equals(item.getProduct_id())) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                itemList.add(i);
            }
        }
        for (ProductSupplier ps : product.getHashOfSupplierProducts().values()) {
            flag = false;
            for (ProductSupplier prod : productSuppliersList) {
                if (ps.getId() == prod.getId() && ps.getCatalogID() == prod.getCatalogID()) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                productSuppliersList.add(ps);
            }
        }
    }

    //if this function return null - go to the db
    public GeneralProduct getGeneralProduct(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod : generalProductList) {
            if (prod.getProduct_id() == gp_id)
                output = prod;
        }
        return output;
    }

    public GeneralProduct removeGeneralProd(int gp_id) {
        GeneralProduct output = null;
        for (GeneralProduct prod : generalProductList) {
            if (prod.getProduct_id() == gp_id) {
                output = prod;
                generalProductList.remove(prod);
            }
        }

        return output;
    }

    ////Categories//////

    public void addCategory(Category category) {
        boolean isIN = false;
        for (Category c : categoryList) {
            if (category.getCategory_name().equals(c.getCategory_name()))
                isIN = true;
        }
        if (!isIN)
            categoryList.add(category);
    }

    //if this function return null - go to the db
    public Category getCategory(String cat) {
        Category output = null;
        for (Category c : categoryList) {
            if (c.getCategory_name().equals(cat))
                output = c;
        }
        return output;
    }

    public Category removeCategory(Category category) {
        Category output = null;
        for (Category cat : categoryList) {
            if (cat.getCategory_name().equals(category.getCategory_name())) {
                output = cat;
                categoryList.remove(cat);
            }
        }
        return output;
    }

    //==============================================
    /////Reports//////
    public void addReport(Report report) {
        boolean isIN = false;
        for (Report r : reportsList)
        { if (r.getReportID() == report.getReportID())
                isIN = true;
       }
        if (!isIN)
            reportsList.add(report);
    }

    //if this function return null - go to the db
    public Report getReport(int reportID) {
        Report output = null;
        for (Report r : reportsList) {
            if (r.getReportID() == reportID)
                output = r;
        }
        return output;
    }

    public Report removeReport(int reportID) {
        Report output = null;
        for (Report r : reportsList) {
            if (r.getReportID() == reportID) {
                output = r;
                reportsList.remove(r);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addSupplier(Supplier sup) {
        suppliersList.add(sup);
    }

    //if this function return null - go to the db
    public Supplier getSupplier(int supplier_id) {
        Supplier output = null;
        for (Supplier sup : suppliersList) {
            if (sup.getId() == supplier_id)
                output = sup;

        }
        return output;
    }

    public Supplier removeSupplier(int supplier_id) {
        Supplier output = null;
        for (Supplier sup : suppliersList) {
            if (sup.getId() == supplier_id) {
                output = sup;
                suppliersList.remove(sup);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addContact(Contact con) {
        contactsList.add(con);
    }


    //if this function return null - go to the db
    public Contact getContact(int con_id) {
        Contact output = null;
        for (Contact con : contactsList) {
            if (con.getId() == con_id)
                output = con;
        }
        return output;
    }

    public Contact removeContact(int con_id) {
        Contact output = null;
        for (Contact con : contactsList) {
            if (con.getId() == con_id) {
                output = con;
                contactsList.remove(con);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addPS(ProductSupplier ps) {
        productSuppliersList.add(ps);
    }
    //================================================================================
    //add an Order to the identityMap
    public void addOrder(Order o) {
        ordersList.add(o);
    }


    //if this function return null - go to the db
    public Order getOrder(int oID) {
        Order output = null;
        for (Order o : ordersList) {
            if (o.GetId() == oID)
                output = o;
        }
        return output;
    }

    public Order removeOrder(int oID) {
        Order output = null;
        for (Order o : ordersList) {
            if (o.GetId() == oID) {
                output = o;
                ordersList.remove(o);
                return o;
            }
        }
        return output;
    }

    //================================================================================
    //add an Agreement to the identityMap
    public void addAgreement(Agreement a) {
        agreementsList.add(a);
    }


    //if this function return null - go to the db
    public Agreement getAgreement(int supID) {
        Agreement output = null;
        for (Agreement a : agreementsList) {
            if (a.getSupplierID() == supID)
                output = a;
        }
        return output;
    }


    public Agreement removeAgreement(int supID) {
        Agreement output = null;
        for (Agreement a : agreementsList) {
            if (a.getSupplierID() == supID) {
                output = a;
                agreementsList.remove(a);
            }
        }
        return output;
    }

    //================================================================================
    //add an item to the identityMap
    public void addDefectedItem(Item item) {
        defctedItemsList.add(item);
    }

    //if this function return null - go to the db
    public Item getDefectedItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : defctedItemsList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
        }
        return output;
    }

    public Item removeDefectedItem(int item_id, int gp_id) {
        Item output = null;
        for (Item item : defctedItemsList) {
            if (item.getItem_id() == item_id && item.getProduct_id() == gp_id)
                output = item;
            defctedItemsList.remove(item);
        }
        return output;
    }

    public void removeAllDefects() {
        defctedItemsList = new LinkedList<>();
    }

    //================================================================================
    //Sales
    //==============================================
    /////sales//////
    public boolean addSale(Sale sale) {
        boolean added = false;
        for (Sale s : salesList) {
            if (s.getSale_id().equals(sale.getSale_id()))
                return false;
        }
        salesList.add(sale);
        return true;
    }

    //if this function return null - go to the db
    public Sale getSale(int saleID) {
        Sale output = null;
        for (Sale s : salesList) {
            if (s.getSale_id() == saleID)
                output = s;
        }
        return output;
    }

    public boolean removeSale(Sale sale) {
        boolean output = false;
        output = salesList.remove(sale);
        return output;
    }

    //================================================productSupplier================================================
    public boolean removerProductSupplier(ProductSupplier ps) {
        for (ProductSupplier p : productSuppliersList
        ) {
            if (p == ps) {
                productSuppliersList.remove(ps);
                return true;
            }
        }
        return false;
    }


}

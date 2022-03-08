package BusinessLayer.OrderBuissness;

import BusinessLayer.IAgreement;
import BusinessLayer.IdentityMap;
import DataLayer.DataController;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//    private HashMap<Integer, HashMap<Integer, Integer>> DiscountByProductQuantity;//- DiscountByProductQuantity hashMap<CatalogID:int, hashMap<quantitiy :int , newPrice:int>>
public class Order {
    private int id;
    private int SupplierID;
    private HashMap<Integer, Integer> productQuantity; //product quantity
    private LocalDate dateTime;
    private double TotalPayment = 0;
    private boolean Constant;
    private Integer constsntordersDays;

    //constructor for DAL
    public Order(int orderID, int sup, String date, double pay, int con,int dayOfOrder) {
       //TODO: need to edit this constructor
        this.id = orderID;
        productQuantity=new HashMap<>();
        this.SupplierID = sup;
//        this.productQuantity=products;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateTime = LocalDate.parse(date, formatter);; //TODO need to take from inventModel the static function
//        this.TotalPayment=CalculateTotalPayment(products,agreement);
        this.TotalPayment = pay;
        this.Constant = con == 1;
        constsntordersDays=dayOfOrder;

//        this.constsntordersDays=constsntordersDays;
    }

public int getDayOfOrder(){
        return constsntordersDays;
}
    public boolean isConstant() {
        return Constant;
    }

    public Order(int id, int SupplierID, HashMap<Integer, Integer> products, IAgreement agreement, boolean constant, Integer constsntordersDays) {
        this.id = id;
        this.SupplierID = SupplierID;
        this.productQuantity = products;

        this.dateTime = LocalDate.now();
        this.TotalPayment = CalculateTotalPayment(products, agreement);
        this.Constant = constant;
        this.constsntordersDays = constsntordersDays;
        for (int pr:products.keySet()
             ) {
            add_ProductToOrder(this.id, pr,products.get(pr));
        }
    }
    public LocalDate getDateTime(){return dateTime; }
    public int getSupplierID() {
        return SupplierID;
    }
    public double getTotalPayment() {
        return TotalPayment;
    }

    public int isConstant_int() {
        if (isConstant()) return 1;
        else return 0;
    }


    public HashMap<Integer, Integer> getProductQuantity() {
        return productQuantity;
    }

    public void AddPrudactToOrder(Integer productCatalogID, int quantity, IAgreement agreement) {
        if (checkIfProductIsAlreadyExist(productCatalogID)) {
            throw new IllegalArgumentException("the product is already exist in the Order");
        }
        productQuantity.put(productCatalogID, quantity);
        this.TotalPayment = CalculateTotalPayment(this.productQuantity, agreement);
        add_ProductToOrder(this.id, productCatalogID,quantity);// data
    }


    public boolean checkIfProductIsAlreadyExist(Integer ProductCtalogID) {
        return productQuantity.containsKey(ProductCtalogID);
    }


    public void RemovePrudactFromOrder(int CatalogID, IAgreement agreement) {
        if (isConstant() && isOneDayFromOrder()) {
            throw new IllegalArgumentException("the order is constant and it less then one day to the order");

        }
        if (!checkIfProductIsAlreadyExist(CatalogID)) {
            throw new IllegalArgumentException("the product is not in the order you cannot deleate it");
        }
        System.out.println("CatalogID::::" + CatalogID);
        productQuantity.remove(CatalogID);
        this.TotalPayment = CalculateTotalPayment(this.productQuantity, agreement);
        removeProduct(this.id, CatalogID); //data
        update(this);


    }


    public void EditProductQuantity(int CatalogID, int quantity, IAgreement agreement) {
        if (!checkIfProductIsAlreadyExist(CatalogID)) {
            throw new IllegalArgumentException("this Product dose not exist in this order");
        }
        if (isConstant() && isOneDayFromOrder()) {
            throw new IllegalArgumentException("the order is constant and it less then one day to the order");

        }
        productQuantity.replace(CatalogID, quantity);
        TotalPayment=CalculateTotalPayment(this.productQuantity, agreement);
        updateProductQuantity(this.id, CatalogID,quantity);//data
        update(this);
    }

    public double GetTotalPayment() {
        return TotalPayment;
    }

    public LocalDate GetDateTime() {
        return dateTime;
    }

    public int GetId() {
        return id;
    }

    public int GetProductQuantity(int CatalogID) {
        return productQuantity.get(CatalogID);
    }


    public void ReCalculateTotalPayment(IAgreement agreement) {
        this.TotalPayment = CalculateTotalPayment(productQuantity, agreement);
    }

    public double CalculateTotalPayment(HashMap<Integer, Integer> productQuantity, IAgreement agreement) {
        double TotalPayment = 0;
        for (Integer key : productQuantity.keySet()) {
            Integer value = productQuantity.get(key);
            double newprice = CheckAvailableDiscount(key, value, agreement);
            if (newprice != -1) {
                TotalPayment = TotalPayment + newprice * productQuantity.get(key);

            } else {
                TotalPayment = TotalPayment + ((agreement.getProducts().get(key)).getPrice()) * value;
            }
        }
        //calculate the total payment with the extra discount
        if (agreement.getExtraDiscount() == 0) {
            return TotalPayment;
        } else {
            return TotalPayment - (TotalPayment * ((agreement.getExtraDiscount()) / 100.0));
        }
    }


    //chek if discount is avalible
    private double CheckAvailableDiscount(Integer CatalogID, Integer quantity, IAgreement agreement) {
        double newprice = -1;
        HashMap<Integer, Double> Temp = (agreement.getDiscountByProductQuantity()).get(CatalogID);
        if (Temp != null && Temp.size() != 0) {
            for (Integer key : Temp.keySet()) {
                if (quantity >= Temp.get(key)) {
                    if (newprice == -1) {
                        newprice = Temp.get(key);
                    } else {
                        newprice = Math.min(Temp.get(key), newprice);
                    }
                }
            }
        }
        return newprice;
    }

    public boolean isOneDayFromOrder() {
        Date date = convertToDateViaSqlDate(LocalDate.now());
        int daytoday = getDayNumberOld(date);

        if (constsntordersDays - daytoday == 1 || constsntordersDays - daytoday == 0) {
            return true;
        }

        return false;
    }

    public Date convertToDateViaSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static int getDayNumberOld(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }



////////////////////////////////DATA Functions////////////////////////////////

    private void add_to_data(Order order) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insetOrder(order)) {
            System.out.println("failed to insert Order to the database with the keys");
        }
        im.addOrder(order);
    }

    private void update(Order order) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.updateOrder(order)) {
            System.out.println("failed to update  Order  to the database with the keys");
        }
    }




    private void updateProductQuantity(int orderId,int catalogID,int quantity) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.updateProductInOrder(orderId,catalogID,quantity)) {
            System.out.println("failed to update Product Order  to the database ");
        }
    }

    //        removeProduct(this.id, CatalogID);
    private void removeProduct(int orderId,int catalogID) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.removeProductFromORder(orderId,catalogID)) {
            System.out.println("failed to remove Product Order  to the database ");
        }

    }
    //insertProduct
    private void add_ProductToOrder(int orderId,int catalogID,int quantity) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insetProduct(orderId,catalogID,quantity)) {
            System.out.println("failed to insert product to the database with the keys");
        }
    }

    //forDAL WHEN WE DO GET
    public void insertProductToOrderForDal(int CatalogId,int Quantity){
        if(!productQuantity.containsKey(CatalogId)){
            productQuantity.put(CatalogId,Quantity);
        }

    }


}

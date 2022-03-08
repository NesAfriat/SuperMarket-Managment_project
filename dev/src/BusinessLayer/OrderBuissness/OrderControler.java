package BusinessLayer.OrderBuissness;

import BusinessLayer.AgreementManager;
import BusinessLayer.IAgreement;
import BusinessLayer.IdentityMap;
import BusinessLayer.ProductSupplier;
import BusinessLayer.Transport_BusinessLayer.Transport_Integration;
import DataLayer.DataController;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderControler {
    //private static OrderControler single_instance = null;
   // HashMap<Integer, List<Integer>> ProductsOrderedFromSupplier ;//+ ProductsOrderedFromSupplier : HashMap<supplierID:int , List<CatalogID:int>>
    private HashMap<Integer, Order> Orders ;//- Orders: hashMap<OrderID: int, order: Order>
    private AgreementManager agreementManager;
    private int idOrderCounter;

    boolean isAllOrdersUploudFromData=false;

//    private HashMap<Integer, HashMap<Integer, Integer>> DiscountByProductQuantity;//- DiscountByProductQuantity hashMap<CatalogID:int, hashMap<quantitiy :int , newPrice:int>>
    public List<Order> getAllOrders(){
        loadAllOrders();
        return new ArrayList<Order>(Orders.values());
    }
    public OrderControler(AgreementManager agreementManager){
        DataController dc = DataController.getInstance();
        // ProductsOrderedFromSupplier=new HashMap<>();
        Orders=new HashMap<>();
        this.agreementManager=agreementManager;
        idOrderCounter=dc.getOrderBigestId()+1;
    }

    public void AddOrder(int SupId, HashMap<Integer,Integer> productQuantity, boolean isConstant, Integer constantorderdayfromdelivery, Transport_Integration transport_integration) throws Exception {
        if(!CheckIfSupplierExist(SupId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        //now check if all the products exist in the agreement of the supplier
        if(!checkIfAllItemsExistInTheSupplierAgreement(SupId, new ArrayList<Integer>(productQuantity.keySet()))){
            throw new IllegalArgumentException("not all product exist in the Agreement with the supplier");
        }
        if(isConstant){
            Order order = new Order(idOrderCounter, SupId, productQuantity, agreementManager.GetAgreement(SupId),true,constantorderdayfromdelivery);
            Orders.put(idOrderCounter, order);
            add_Order_to_the_data(order);
            idOrderCounter++;

            String date= transport_integration.addTransportFromSupplierConstant(order.GetId(),SupId,productQuantity);
            if (date==null) {
                removeOrderFromTheData(order);
                throw new IllegalArgumentException("There is no driver available to transport this order, please try again later");
            }
        }
        else {
            Order order = new Order(idOrderCounter, SupId, productQuantity, agreementManager.GetAgreement(SupId),false,constantorderdayfromdelivery);
            Orders.put(idOrderCounter, order);
            add_Order_to_the_data(order);
            idOrderCounter++;


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LL yyyy");
            String formattedString = order.getDateTime().format(formatter);
            String date_string_format=(formattedString.substring(0,2)+"/"+formattedString.substring(3,5)+"/"+formattedString.substring(6,10));
            String date= transport_integration.addTransportFromSupplier(order.GetId(),SupId,productQuantity,date_string_format);
            if (date==null) {
                removeOrderFromTheData(order);
                throw new IllegalArgumentException("There is no driver available to transport order id: "+idOrderCounter+", please try again later");
            }
        }

    }


    public void create_order_Due_to_lack( HashMap<Integer, HashMap<Integer, Integer>> cheapest_supplier_products_by_quantity,Integer constantorderdayfromdelivery,Transport_Integration transport_integration) throws Exception {
        for (Integer SupId : cheapest_supplier_products_by_quantity.keySet()) {
            AddOrder(SupId,cheapest_supplier_products_by_quantity.get(SupId),false,constantorderdayfromdelivery,transport_integration);
        }
    }


    // check if supplier exist in the agreements
    private boolean CheckIfSupplierExist(int SupId){
        return agreementManager.isSupplierExist(SupId);
    }
    private boolean CheckIfSupplierProvidesThePruduct(int SupId,int productId){
        return (agreementManager.GetAgreement(SupId)).isProductExist(productId);
    }

    // check if items exist in the supplier agrement
    private boolean checkIfAllItemsExistInTheSupplierAgreement(int SupId,List<Integer> productsCtalogId){
        HashMap<Integer, ProductSupplier> AgreemntProduct =agreementManager.GetAgreement(SupId).getProducts();
        for (Integer CatalogId:productsCtalogId
             ) {
            if(!AgreemntProduct.containsKey(CatalogId)){
                return false;
            }
        }
        return true;
    }

    //ok with THE DATA
    public void RemoveOrder(int OrderID,int SupId)
    {
        if(!isExsist(OrderID,SupId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        Order order=Orders.get(OrderID);
        Orders.remove(OrderID);
        removeOrderFromTheData(order);
    }

    //it ok withe the data
    public Order GetOrder(int OrderID,int SupId)
    {
        if(!Orders.containsKey(OrderID)){
            Order order=getOrderFromTheData(OrderID,SupId);
            Orders.put(OrderID,order);
            if(order==null){
            throw new IllegalArgumentException("the order is not in the system");
        }
            return order;

        }
        return Orders.get(OrderID);
    }

    //Ok with the DATA
    //after we remoce supliers we shold remove all constant orders of this supplier
    public void RemoveAllSupllierConstOrders(int SupiD){
        for (Order order:Orders.values()
             ) {
           if(order.isConstant() &&order.getSupplierID()==SupiD)
           {
               Orders.remove(order.GetId());
               removeOrderFromTheData(order);
           }
        }
    }


    private IAgreement GetAgreement(int supplierID)
    {
        return agreementManager.GetAgreement(supplierID);
    }


    public void addProductToOrder(Integer product,Integer Quantity,Integer OrderID,int supId){
        if(!CheckIfSupplierExist(supId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        if(!isExsist(OrderID,supId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        //you canot chacg orders that no constant!!!
        if(!Orders.get(OrderID).isConstant()){
            throw new IllegalArgumentException("the Order is not Constant you cannot change order tht done already");
        }
        if(!CheckIfSupplierProvidesThePruduct(supId,product)){
            throw new IllegalArgumentException("the supplier dose not Supply this product");
        }

        Orders.get(OrderID).AddPrudactToOrder(product,Quantity,agreementManager.GetAgreement(supId));
    }
    public void changeProductQuantityFromOrder(Integer product,Integer Quantity,Integer OrderID,int supId){
        if(!CheckIfSupplierExist(supId)){
            throw new IllegalArgumentException("the supplier is not exist in system");
        }
        if(!isExsist(OrderID,supId)){
            throw new IllegalArgumentException("the order is not in the system");
        }
        //you canot chacg orders that no constant!!!
        if(!Orders.get(OrderID).isConstant()){
            throw new IllegalArgumentException("the Order is not Constant you cannot change order tht done already");
        }
        if(!CheckIfSupplierProvidesThePruduct(supId,product)){
            throw new IllegalArgumentException("the supplier dose not Supply this product");
        }
        //if(!(Orders.get(OrderID)).checkIfProductIsAlreadyExist(product)){
         //   throw new IllegalArgumentException("this Product dose not exist in this order");
       // }
       // public void EditProductQuantity(int CatalogID,int quantity,IAgreement agreement)
        Orders.get(OrderID).EditProductQuantity(product,Quantity,agreementManager.GetAgreement(supId));
    }

    public void ReCalculateTotalPayment (int SupId)
    {
        for (Order order:Orders.values())
        {
            order.ReCalculateTotalPayment(agreementManager.GetAgreement(SupId));
        }
    }



    public Boolean isExsist(int OrderId,int SupId){
        loadAllOrders();
        if(Orders.containsKey(OrderId)){
            return true;
        }
        return false;
    }


    public void RemovePrudactFromOrders (int SupId, int CatalogID)
    {
        for (Order order:Orders.values())
        {
            if (order.isConstant()&&(order.getSupplierID()==SupId)&&order.checkIfProductIsAlreadyExist(CatalogID)){
            order.RemovePrudactFromOrder(CatalogID,agreementManager.GetAgreement(SupId));
             }
        }
    }

    //================================================DATA===================================



    //insertProduct
    private Order getOrderFromTheData(int orderId,int SupId) {
        IdentityMap im = IdentityMap.getInstance();
        Order order =im.getOrder(orderId);
        if(order!=null){
            return order;
        }
        else {
            DataController dc = DataController.getInstance();
            order = dc.getOrder(SupId, orderId);
            if (order == null) {
                return null; }

            im.addOrder(order);
            return order;
        }
    }




    private void removeOrderFromTheData(Order order){
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        im.removeOrder(order.GetId());
        boolean isOk=dc.deleteOrder(order);
        if(!isOk){
        throw new IllegalArgumentException("canot remove from the datat order that not exsist");
}
    }




    //ADD ORDER
    private void add_Order_to_the_data(Order order) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.insetOrder(order)) {
            System.out.println("failed to insert Order to the database with the keys");
        }
        im.addOrder(order);
    }




    private boolean loadAllOrders(){
        if(isAllOrdersUploudFromData){
            return true;
        }
        DataController dc = DataController.getInstance();
        List<Order> orderList=dc.getAllOrders();
        IdentityMap im = IdentityMap.getInstance();
        for (Order o:orderList
             ) {
            if(!Orders.containsKey(o.GetId())){
                im.addOrder(o);
                Orders.put(o.GetId(),o);
            }
        }
        isAllOrdersUploudFromData=true;
        return true;

    }

}

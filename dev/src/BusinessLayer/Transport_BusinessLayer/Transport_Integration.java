package BusinessLayer.Transport_BusinessLayer;

import java.util.HashMap;

public interface Transport_Integration {

    //date format "DD/MM/YYYY"
    //return string - Date of
    //hashmap <Integer,Integer> - general product id, amount
    public String addTransportFromSupplier(int orderID,int supplierId, HashMap<Integer,Integer> productAndAmount, String date) throws Exception;

    //takeCurrent Date And Time
    public String addTransportFromSupplierConstant(int orderID, int supplierId, HashMap<Integer,Integer> productAndAmount) throws Exception;

}

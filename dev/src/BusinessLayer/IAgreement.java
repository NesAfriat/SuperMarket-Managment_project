package BusinessLayer;

import java.util.HashMap;
import java.util.List;

public interface IAgreement {

    public ProductSupplier AddPrudact(double Price, int CatalogID, int idProductCounter, String name);
    public void RemovePrudact(int CatalogID);
    public void AddDiscountByProduct(int CatalogID, int quantity, double newPrice);
    public void SetExtraDiscount(int ExtarDiscount);
    public void setProductPrice(int CatalogId, double ExtraDiscount);
    public ProductSupplier GetPrudact(int CatalogID);
    public HashMap<Integer, ProductSupplier> getProducts();
    public int getNumOfDaysFromOrder();
    public List<Integer> getDaysOfDelivery();
    public DeliveryMode getDeliveryMode();
    public int getSupplierID();
    public double getExtraDiscount();
    public HashMap<Integer, HashMap<Integer, Double>> getDiscountByProductQuantity();
    public boolean isProductExist(int CatalogId);
    public void removeDiscountQuantity(int CatalogId, int Quantiti);
    public void SetDeliveryMode(DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder);
    public double Calculate_cost(ProductSupplier productSupplier, int quantity);
    public void RemovAllProducts();


}

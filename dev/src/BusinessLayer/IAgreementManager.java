package BusinessLayer;

import java.util.List;

public interface IAgreementManager {

    public ProductSupplier getProduct(int supplierID, int CatalogId);

    public List<ProductSupplier> getAllProductsOfSupplier(int SupplierId);

    public List<ProductSupplier> getAllProductsInTheSystem();

    public Agreement getAgreement(int SupplierID);

    //public Agreement AddAgreement(Agreement agreement);

    public void AddNewAgreement(IAgreement agreement);

    public void AddProduct(int SupplierId, double Price, int CatalogID);



    }

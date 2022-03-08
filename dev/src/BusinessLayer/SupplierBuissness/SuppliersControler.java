package BusinessLayer.SupplierBuissness;

import BusinessLayer.*;
import DataLayer.DataController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class SuppliersControler implements ISupplierControler {
    private static SuppliersControler single_instance = null;
    private HashMap<Integer, ISupplier> Suppliers;
    private AgreementManager agreementManager;
    private int SupplierIdCounter;
    boolean isLoadAllSuppliers = false;


    public SuppliersControler(AgreementManager agreementManager) {
        Suppliers = new HashMap<>();
        this.agreementManager = agreementManager;
        SupplierIdCounter = 0;
    }
    //private function
    //private void AddNewAgreement(IAgreement agreement) {
    //   agreementManager.AddNewAgreement(agreement);

    // }
    //HashMap<Integer, HashMap<Integer, Integer>> cheapest_supplier_products_by_quantity;// hashMap<SupllierID:int, hashMap<CatalogID :int , quantitiy:int>>
    public HashMap<Integer, HashMap<Integer, Integer>> get_cheapest_supplier(HashMap<GeneralProduct, Integer> lackMap) {
        return agreementManager.get_cheapest_supplier2(lackMap);
    }


    private void AddNewAgreement(int id, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery) {
        agreementManager.AddNewAgreement(id, deliveryMode, daysOfDelivery, NumOfDaysFromDelivery);

    }


    public void removeProductFromSupplier(int CatalogId, int SupID) throws Exception {

        agreementManager.removeProduct(SupID, CatalogId);
    }

    public void setProductPrice(int SupId, int CatalogID, double price) {
        agreementManager.GetAgreement(SupId).setProductPrice(CatalogID, price);
    }

    public void addNewDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy, double newPrice) {
        agreementManager.GetAgreement(SupId).AddDiscountByProduct(CatalogID, Quantitiy, newPrice);
    }

    public void RemovDiscountByQuantitiyToProduct(int SupId, int CatalogID, int Quantitiy) {
        agreementManager.GetAgreement(SupId).removeDiscountQuantity(CatalogID, Quantitiy);
    }

    public void setExtraDiscountToSupplier(int SupId, int ExtraDiscount) {
        (agreementManager.GetAgreement(SupId)).SetExtraDiscount(ExtraDiscount);
    }

    public IAgreement getAgreement(int SupId) {
        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        return (agreementManager.GetAgreement(SupId));
    }

    @Override
    public void SetPayment(paymentMethods paymentMethods, int SupplierId) {
        if (!isSupplierExist(SupplierId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        ISupplier supplier = Suppliers.get(SupplierId);
        supplier.setPayment(paymentMethods);
        updateSupplierFromTheData((Supplier)getSupplier(SupplierId));

    }

    //@Override
    public void addNewProductToAgreement(int SupplierId, double Price, int CatalogID, String manfucator, String name, String category, int pid, boolean isexist) throws Exception {
        if (!isSupplierExist(SupplierId)) {
            throw new IllegalArgumentException("the Supplier doues not exsist");
        }
        if (manfucator == null || manfucator == "") {
            throw new IllegalArgumentException("the manfucator input is incorrect");
        }
        if (name == null || name == "") {
            throw new IllegalArgumentException("the name input is incorrect");
        }
        agreementManager.AddProduct(SupplierId, Price, CatalogID, name, manfucator, category, pid, isexist);
    }


    @Override
    public void addNewSupplier(int id, String name, String bankAccount, paymentMethods paymentMethods, DeliveryMode deliveryMode, List<Integer> daysOfDelivery, int NumOfDaysFromDelivery, String contactName, String contactEmail, String phoneNumber) {
        if (isSupplierExist(id)) {
            throw new IllegalArgumentException("the supplier does exist already");
        }
        if (contactEmail == null || contactName == null || phoneNumber == null) {
            throw new IllegalArgumentException("one of the given values is null");
        }
        if (contactEmail == "" || contactName == "" || phoneNumber == "") {
            throw new IllegalArgumentException("one of the given values is empty");
        }
        if (!validatePhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("the phone number form is incorect");
        }
        if (!isValidEmail(contactEmail)) {
            throw new IllegalArgumentException("the Email is not valid");
        }
        Supplier supplier = new Supplier(id, name, paymentMethods, bankAccount, SupplierIdCounter, contactName, phoneNumber, contactEmail);
        Suppliers.put(id, supplier);
        SupplierIdCounter++;
        AddNewAgreement(id, deliveryMode, daysOfDelivery, NumOfDaysFromDelivery);
        addSupllierToTheData(supplier);
    }

    @Override
    public void RemoveSupplier(int SupId) {

        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        Supplier supplier=(Supplier)getSupplier(SupId);
        agreementManager.GetAgreement(SupId).RemovAllProducts();
        agreementManager.RemoveAgreement(SupId);
        supplier.removeAllContacts();
        removeSupplierFromTheData(supplier);
        Suppliers.remove(supplier.getId());

    }


    @Override
    public ISupplier getSupplier(int SupId) {
        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        return Suppliers.get(SupId);
    }

    @Override
    public List<ISupplier> getAllSuppliers() {
        loadAllSuppliersFromData();
        return new ArrayList<ISupplier>(Suppliers.values());
    }

    @Override
    public void setBankAccount(int SupId, String BankAccount) {
        if (BankAccount == null) {
            throw new IllegalArgumentException("the Bank Acount is null");
        }
        if (BankAccount == "") {
            throw new IllegalArgumentException("the Bank Acount value is empty");
        }
        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        Suppliers.get(SupId).setBankAccount(BankAccount);
        updateSupplierFromTheData((Supplier)getSupplier(SupId));
    }

    @Override
    public void SetDeliveryMode(int SupId, DeliveryMode deliveryMods, List<Integer> daysOfDelivery, int numOfDaysFromOrder) {
        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        (agreementManager.GetAgreement(SupId)).SetDeliveryMode(deliveryMods, daysOfDelivery, numOfDaysFromOrder);
    }

    @Override
    public void addNewContact(int SupId, String contactName, String contactEmail, String phoneNumber) {

        if (contactEmail == null || contactName == null || phoneNumber == null) {
            throw new IllegalArgumentException("one of the given values is null");
        }
        if (contactEmail.equals("") || contactName.equals("") || phoneNumber.equals("")) {
            throw new IllegalArgumentException("one of the given values is empty");
        }
        if (!isValidUsername(contactName)) {
            throw new IllegalArgumentException("Illegal contact Name");
        }
        if (!validatePhoneNumber(phoneNumber)) {
            throw new IllegalArgumentException("the phone number form is incorect");
        }
        if (!isSupplierExist(SupId)) {
            throw new IllegalArgumentException("the supplier does not exist");
        }
        if (!isValidEmail(contactEmail)) {
            throw new IllegalArgumentException("the Email is not valid");

        }
        Suppliers.get(SupId).addNewContact(contactName, phoneNumber, contactEmail);
    }

    @Override
    public void removeContact(int SupId, int ContactId) {
        if (!isSupplierExist(SupId)) {

            getSupplier(SupId);
        }

        Suppliers.get(SupId).removeContact(ContactId);
    }

    // return true if the supplier exist in the suppliers hash map
    public boolean isSupplierExist(int supplierId) {
      if(Suppliers.containsKey(supplierId)){
          return true;
      }
      else {
          Supplier s=getSupplierFromData(supplierId);
          if (s==null){
              return false;
          }

          Suppliers.put(supplierId,s);
          return true;
      }
    }


    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.matches("\\d{10}"))
            return true;
        else if (phoneNumber.matches("\\d{9}")) {
            return true;
        }
        return false;
    }

    private boolean isValidUsername(String name) {
        return name.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$");
    }



//===============================================dal=================================

    public Supplier getSupplierFromData(int SupId) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        Supplier supplier = im.getSupplier(SupId);
        if (supplier != null) {
            return supplier;
        }
        supplier = dc.getSupplier(SupId);
        if (supplier != null)
        {  im.addSupplier(supplier);
            return supplier;
        }
        return null;
    }


    public boolean loadAllSuppliersFromData() {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (isLoadAllSuppliers) {
            return true;
        } else {
            List<Supplier> supplierList = dc.getAllSupplier();
            if (supplierList == null) {
                return false;
            }
            for (Supplier supllier : supplierList
            ) {
                if (!isSupplierExist(supllier.getId())) {
                    im.addSupplier(supllier);
                    Suppliers.put(supllier.getId(), supllier);
                }
            }
        }
        return true;
    }

    public void addSupllierToTheData(Supplier supplier) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        im.addSupplier(supplier);
        dc.insertSupplier(supplier);
    }

    public void removeSupplierFromTheData(Supplier supplier) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        im.removeSupplier(supplier.getId());
        dc.delete(supplier);
    }

    public void updateSupplierFromTheData(Supplier supplier) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        dc.update(supplier);
    }
}
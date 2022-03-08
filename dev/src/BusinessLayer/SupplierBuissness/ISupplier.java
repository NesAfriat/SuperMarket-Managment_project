package BusinessLayer.SupplierBuissness;

import BusinessLayer.paymentMethods;

import java.util.List;

public interface ISupplier {

    //this function add new contact to the supplier list
    public void addNewContact(String contactName, String phoneNumber, String Email);

    //this function remove contact from supplier contact list, if the contact do not exist return false
    public void removeContact(int contactID);

   // public void setPayment(paymentMethods payment);

    public String getBankAccount();

    public void setBankAccount(String bankAccount);

    public void setSupplierName(String supplierName);

    public List<Contact> getListOfContacts();

    public String getSupplierName();

    public void setPayment(paymentMethods payment);


    public paymentMethods getPayment();

    public int getId();

    public Contact getContact(int ContactId);
    }


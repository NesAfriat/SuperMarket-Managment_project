package BusinessLayer.SupplierBuissness;

import BusinessLayer.IdentityMap;
import BusinessLayer.paymentMethods;
import DataLayer.DataController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Supplier implements ISupplier {
    //the payment methods in the system

    private int ContactIdCounter; // increment then insert new contact. start with def value - 0
    private int id;
    private String SupplierName;
    private String BankAccount ;
    private paymentMethods payment;
    private List<Contact> ListOfContacts;
    private int SupplierIdCounter;






    public Supplier(int id, String SupplierName, paymentMethods payment, String BankAccount, int SupplierIdCounter , String contactName, String contactEmail, String phoneNumber)
    {
       this.ContactIdCounter=0;
       this.ListOfContacts  = new ArrayList<Contact>();
       Contact contact=new Contact(ContactIdCounter,contactName,contactEmail,phoneNumber,id);
       ListOfContacts.add(contact);
       addContactToSupplierInTheData(contact,id);
       ContactIdCounter++;
       this.id=id;
       this.SupplierName=SupplierName;
       this.payment=payment;
       this.BankAccount=BankAccount;
       this.SupplierIdCounter=SupplierIdCounter;
    }

    //Constructor for DAL
    public Supplier(int supID, String supName, paymentMethods payment, String bank) {
        this.ContactIdCounter=getTheBigestIDforTheCounterinContacts(supID)+1;
        this.ListOfContacts  = new ArrayList<>();
        //TODO contacts will be added later
//        ListOfContacts.add(new Contact(ContactIdCounter,contactName,contactEmail,phoneNumber));
//        ContactIdCounter++;
        this.id=supID;
        this.SupplierName=supName;
        this.payment=payment; //TODO need to change
        this.BankAccount=bank;
    }

    @Override
    public void addNewContact( String contactName, String phoneNumber, String Email) {
        Contact newContact=new Contact(ContactIdCounter,contactName,phoneNumber,Email,id);
        ListOfContacts.add(newContact);
        addContactToSupplierInTheData(newContact,id);
        incContactIdCounter();
    }


    @Override
    public void removeContact(int contactID) {

        for (Contact contact:ListOfContacts) {
            if(contact.getId()==contactID){
                ListOfContacts.remove(contact);
                RemoveContactToSupplierInTheData(contact,id);
                return;//ככה יוצאים מפונקציה? אחרת זה יזרוק לנו שגיאה בלי סיבה
            }
        }
        //the contact is not exist
        throw new IllegalArgumentException("the contact is not exist");
    }

    public Contact getContact(int ContactId){
        for (Contact contact:ListOfContacts
             ) {
            if(contact.getId()==ContactId){
                return contact;
            }
        }
        //
        throw new IllegalArgumentException("the contact is not exist");
    }

    // this function inc the Contact id counter when we insert anew contact to whe list
    public void incContactIdCounter() {
        this.ContactIdCounter++;
    }

    // this function dec the Contact id counter

    public void decContactIdCounter() {
        this.ContactIdCounter--;
    }


    public void setContactIdCounter(int contactIdCounter) {
        ContactIdCounter = contactIdCounter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        UpdateSupplierInTheData(this);
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
        UpdateSupplierInTheData(this);

    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
        UpdateSupplierInTheData(this);

    }

    public paymentMethods getPayment() {
        return payment;
    }

    public void setPayment(paymentMethods payment) {
       this.payment = payment;
        UpdateSupplierInTheData(this);

    }

    public List<Contact> getListOfContacts() {
        return ListOfContacts;
    }

//    public void setListOfContacts(List<Contact> listOfContacts) {
//        ListOfContacts = listOfContacts;
//
//    }
//


///=====================================================data=======================================

public void UpdateSupplierInTheData(Supplier supplier){
    IdentityMap im = IdentityMap.getInstance();
    DataController dc = DataController.getInstance();
    if(!dc.update(supplier)){
        System.out.println("faild");
    }
}


    public void addContactToSupplierInTheData(Contact contact,int SupId){
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if(!dc.insertContact(contact,SupId)){
            System.out.println("faild");
        }
    }


    public void removeAllContacts(){
        List<Integer> list=new LinkedList<>();
        for(int i=0;i<ListOfContacts.size();i++){
            list.add(ListOfContacts.get(i).getId());
        }
        for (Integer id:list
        ) {
            removeContact(id);
        }
    }

    public void RemoveContactToSupplierInTheData(Contact contact,int SupId){
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if(!dc.delete(contact,SupId)){
            System.out.println("faild");
        }
    }

    //add contact to the supplier from dal when we do get tu supplier
    public void addContactFromDal(Contact c){
        IdentityMap im = IdentityMap.getInstance();
        //CHECK IF ALREADY EXSIST
        boolean isContactExsist=false;
        for (Contact con:ListOfContacts
             ) {
            if(con.getId()==c.getId()){
                isContactExsist=true;
            }
        }
        if(!isContactExsist) {
            im.addContact(c);
            ListOfContacts.add(c);
        }
    }


    private int getTheBigestIDforTheCounterinContacts(int supId){
        DataController dc = DataController.getInstance();
        int x=dc.getTheBigestIDforTheCounterinContacts(supId);
        return x;

    }





}

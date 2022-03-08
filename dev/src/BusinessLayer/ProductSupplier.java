package BusinessLayer;


import DataLayer.DataController;

public class ProductSupplier {
    private double Price;
    private int CatalogID;
    private int id;
    private String name;
    private int SuplierID;


    public ProductSupplier(double Price, int CatalogID, int id,String name,int SupId){
        this.Price=Price;
        this.CatalogID=CatalogID;
        this.id=id;
        this.name=name;
        this.SuplierID=SupId;
    }
    public String getName(){return name;}

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
        update(this);
    }

    public int getCatalogID() {
        return CatalogID;
    }

    public void setCatalogID(int catalogID) {
        CatalogID = catalogID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        update(this);

    }

    public int getSuplierID(){
        return SuplierID;
    }


    //======================================================================
//DATA Functions:


    private void update(ProductSupplier productSupplier) {
        IdentityMap im = IdentityMap.getInstance();
        DataController dc = DataController.getInstance();
        if (!dc.updateProductSupplier(productSupplier,SuplierID)) {
            System.out.println("failed to update ProductSupplier to the database");
        }
    }



}

package BusinessLayer.Transport_BusinessLayer.Shops;

public class Product {



    private int id;

    public Product(int id){

        this.id=id;

    }



    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


    public String toString(){

        return  "Product ID: "+id +"\n";

    }
}

package BusinessLayer.FacedeModel.Objects;

public class productSupplierResponse {
    public final double price;
    public final int CatalogID;
    public final int id;
    public final String name;


    public productSupplierResponse(double price, int catalogID, int id, String name) {
        this.price = price;
        CatalogID = catalogID;
        this.id = id;
        this.name = name;
    }
}

package BusinessLayer.Transport_BusinessLayer.Shops;

public class ProductArea {
    private Product p;
    private Area a;

    public ProductArea(Product p, Area a) {
        this.p = p;
        this.a = a;
    }

    public String getA() {
        return a.name();
    }

    public int getId() {
        return p.getId();
    }
}

package BusinessLayer.Transport_BusinessLayer.Shops;

public abstract class Site {



    protected int id;




    public Site(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

package BusinessLayer.Transport_BusinessLayer.Drives;

public class Driver {
    private String name;
    private int id;
    private License license;

    public Driver(String name, int id, License license) {
        this.name = name;
        this.id = id;
        this.license = license;
    }

    public String toString() {
        return "Driver id: " + id + ", " + name + ", " + license.name() + "\n ";
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public License getLicense() {
        return license;
    }


}

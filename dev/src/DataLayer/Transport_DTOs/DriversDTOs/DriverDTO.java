package DataLayer.Transport_DTOs.DriversDTOs;

import BusinessLayer.Transport_BusinessLayer.Drives.License;

public class DriverDTO {
    private final String name;
    private final int id;
    private final License license;

    public DriverDTO(String name, int id, License license) {
        this.name = name;
        this.id = id;
        this.license = license;
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

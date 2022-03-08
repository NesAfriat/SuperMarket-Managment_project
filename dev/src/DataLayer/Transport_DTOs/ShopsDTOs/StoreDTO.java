package DataLayer.Transport_DTOs.ShopsDTOs;

import BusinessLayer.Transport_BusinessLayer.Shops.Area;

public class StoreDTO {
    private final String name;
    private final int id;
    private final String phoneNumber;
    private final String contact;
    private final Area StoreArea;

    public StoreDTO(String name, int id, String phoneNumber, String contact, Area storeArea) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.contact = contact;
        StoreArea = storeArea;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getContact() {
        return contact;
    }

    public Area getStoreArea() {
        return StoreArea;
    }
}

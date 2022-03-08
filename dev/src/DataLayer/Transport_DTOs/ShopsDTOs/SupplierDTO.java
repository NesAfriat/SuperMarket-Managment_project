package DataLayer.Transport_DTOs.ShopsDTOs;

import BusinessLayer.Transport_BusinessLayer.Shops.Area;

import java.util.List;

public class SupplierDTO {
    private final String name;
    private final int id;
    private final String phoneNumber;
    private final String contact;
    private final Area StoreArea;
    private final List<ProductDTO> productsServed;

    public SupplierDTO(String name, int id, String phoneNumber, String contact, Area storeArea, List<ProductDTO> productsServed) {
        this.name = name;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.contact = contact;
        StoreArea = storeArea;
        this.productsServed = productsServed;
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

    public List<ProductDTO> getProductsServed() {
        return productsServed;
    }
}

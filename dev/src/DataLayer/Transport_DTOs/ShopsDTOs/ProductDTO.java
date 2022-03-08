package DataLayer.Transport_DTOs.ShopsDTOs;

import java.util.List;

public class ProductDTO {
    private final String name;
    private final int id;
    private final List<SupplierDTO> suppList;


    public ProductDTO(String name, int id, List<SupplierDTO> suppList){
        this.name=name;
        this.id=id;
        this.suppList=suppList;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<SupplierDTO> getSuppList() {
        return suppList;
    }
}

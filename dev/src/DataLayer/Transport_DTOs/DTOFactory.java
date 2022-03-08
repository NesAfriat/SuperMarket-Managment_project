package DataLayer.Transport_DTOs;

import DataLayer.Transport_DTOs.DocumentsDTOs.TransportDocDTO;
import DataLayer.Transport_DTOs.DriversDTOs.DriverDTO;
import DataLayer.Transport_DTOs.DriversDTOs.TruckDTO;
import DataLayer.Transport_DTOs.DriversDTOs.TruckTypeDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.ProductDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.StoreDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.SupplierDTO;
import BusinessLayer.Transport_BusinessLayer.Document.TransportDoc;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Drives.Driver;
import BusinessLayer.Transport_BusinessLayer.Drives.Truck;
import BusinessLayer.Transport_BusinessLayer.Drives.TruckType;
import BusinessLayer.Transport_BusinessLayer.Shops.Product;
import BusinessLayer.Transport_BusinessLayer.Shops.Store;
import BusinessLayer.Transport_BusinessLayer.Shops.Supplier;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class  DTOFactory  {

    //DriverDTOs
    /*
    public static DriverDTO createDriverDTO(Driver driver){
        return new DriverDTO(driver.getName(), driver.getId(), driver.getLicense());
    }
    public static TruckDTO createTruckDTO(Truck truck){
        return new TruckDTO(truck.getName(), truck.getLicensePlate(), createTruckTypeDTO(truck.getTruckType()));
    }
    private static TruckTypeDTO createTruckTypeDTO(TruckType type){
        return new TruckTypeDTO(type.getName(), type.getMaxWeight(), type.getFreeWeight(), type.CopyOfLicenses());
    }
    //ShopsDTOs
    public static StoreDTO createStoreDTO(Store store){
        return new StoreDTO(store.getName(), store.getId(), store.getPhoneNumber(), store.getContact(), store.getArea());
    }
    public static SupplierDTO createSupplierDTO(Supplier supplier){
        return new SupplierDTO(supplier.getName(), supplier.getId(), supplier.getPhoneNumber(), supplier.getContact(),
                supplier.getArea(),createSallowListProductDTO(supplier.getProductsServed()));
    }
    private static List<ProductDTO> createSallowListProductDTO(List<Product> product){//only primitive fields are copyed
        return product.stream().map(x->new ProductDTO(x.getName(), x.getId(), new LinkedList<>())).collect(Collectors.toList());
    }
    public static ProductDTO createProductDTO(Product product){
        return new ProductDTO(product.getName(), product.getId(),createSallowListSupplierDTO(product.getSuppList()) );
    }
    private static List<SupplierDTO> createSallowListSupplierDTO(List<Supplier> suppliers){//only primitive fields are copyed
        return suppliers.stream().map(x->new SupplierDTO(x.getName(),x.getId(),x.getPhoneNumber(),x.getContact(),x.getArea(),
                new LinkedList<>())).collect(Collectors.toList());
    }

    public static TransportDocDTO createTransportDocDTO(TransportDoc doc) throws Exception {
        return new TransportDocDTO(doc.getId(),doc.getTransDate(),doc.getLeftOrigin(),createTruckDTO(doc.getTruck()),
                createDriverDTO(doc.getDriver()),createStoreDTO(doc.getOrigin()),HashMapToListSupplier(doc.getDestinationSupplier()),
                HashMapToListStore(doc.getDestinationStore()),doc.getArea(), doc.getTruckWeightDep(), ProductsAmountStore(doc.getProductList()),
                createTransportDocDTO(doc.upDates));
    }



    private static List<Triple<ProductDTO, Integer, StoreDTO>> ProductsAmountStore(List<Triple<Product, Integer, Store>> productList){
       return productList.stream().map(x->
               new Triple<ProductDTO, Integer, StoreDTO>(createProductDTO(x.getFirst()),x.getSecond().intValue(),createStoreDTO(x.getThird()))
               ).collect(Collectors.toList()) ;
    }
    private static List<Tuple<Integer,StoreDTO>> HashMapToListStore(HashMap<Integer, Store> destinationStore){
        return destinationStore.keySet().stream().map(x->
            new Tuple<>(x.intValue(),createStoreDTO(destinationStore.get(x)))
        ).collect(Collectors.toList());
    }
    private static List<Tuple<Integer,SupplierDTO>> HashMapToListSupplier(HashMap<Integer, Supplier> destinationSupplier){
        return destinationSupplier.keySet().stream().map(x->
                new Tuple<>(x.intValue(),createSupplierDTO((destinationSupplier.get(x))))
        ).collect(Collectors.toList());
    }

     */

}

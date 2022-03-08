package DataLayer.Transport_DTOs.DocumentsDTOs;

import DataLayer.Transport_DTOs.DriversDTOs.DriverDTO;
import DataLayer.Transport_DTOs.DriversDTOs.TruckDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.ProductDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.StoreDTO;
import DataLayer.Transport_DTOs.ShopsDTOs.SupplierDTO;
import BusinessLayer.Transport_BusinessLayer.Document.Triple;
import BusinessLayer.Transport_BusinessLayer.Shops.Area;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;

import java.util.Date;
import java.util.List;

public class TransportDocDTO {
    final int id;
    final Date TransDate;
    final Date LeftOrigin;
    final TruckDTO truck;
    final DriverDTO driver;
    final StoreDTO origin;
    // store,int


    final List<Tuple<Integer,SupplierDTO>> destinationSupplier;
    final List<Tuple<Integer,StoreDTO>> destinationStore;
    final Area area;
    final double truckWeightDep;
    //Product,int
    final List<Triple<ProductDTO, Integer, StoreDTO>> productList;

    final public TransportDocDTO upDates;



    public TransportDocDTO(int id, Date transDate, Date leftOrigin, TruckDTO truck, DriverDTO driver, StoreDTO origin, List<Tuple<Integer, SupplierDTO>> destinationSupplier, List<Tuple<Integer, StoreDTO>> destinationStore, Area area, double truckWeightDep, List<Triple<ProductDTO, Integer, StoreDTO>> productList, TransportDocDTO upDates) {
        this.id = id;
        TransDate = transDate;
        LeftOrigin = leftOrigin;
        this.truck = truck;
        this.driver = driver;
        this.origin = origin;
        this.destinationSupplier = destinationSupplier;
        this.destinationStore = destinationStore;
        this.area = area;
        this.truckWeightDep = truckWeightDep;
        this.productList = productList;
        this.upDates = upDates;
    }
}

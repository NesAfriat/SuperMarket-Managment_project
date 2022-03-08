package BusinessLayer.Transport_BusinessLayer.Cont;

import BusinessLayer.Transport_BusinessLayer.Shops.*;
import BusinessLayer.Transport_BusinessLayer.etc.Tuple;
import DataLayer.Transport_DAL.DALController;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerShops {
 /*   private List<Product> productList;
    private List<Store> storeList;
    private List<Supplier> supplierList;

    private HashMap<ProductArea, List<Supplier>> findSupp;

    public ControllerShops(List<Product> productList, List<Store> storeList, List<Supplier> supplierList, HashMap<ProductArea, List<Supplier>> findSupp) {
        this.productList = productList;
        this.storeList = storeList;
        this.supplierList = supplierList;
        this.findSupp = findSupp;
    }

    public ControllerShops() {
        productList = new LinkedList<>();
        storeList = new LinkedList<>();
        supplierList = new LinkedList<>();
        findSupp = new HashMap<>();
    }


    public ControllerShops(List<Product> productList, List<Store> storeList, List<Supplier> supplierList) {
        this.productList = productList;
        this.storeList = storeList;
        this.supplierList = supplierList;
    }

    public void addStore(Store pa) {
        storeList.add(pa);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }



    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public void addProduct(Product pa) {
        productList.add(pa);
    }


    public void addProductToSupp(Supplier sp, Product pa) {
        sp.addProduct(pa);
        ProductArea prodAr = new ProductArea(pa, sp.getArea());
        if (!findSupp.containsKey(prodAr))
            findSupp.put(prodAr, new LinkedList<Supplier>());
        findSupp.get(prodAr).add(sp);
    }

    public void addSupplier(Supplier pa) {
        for (Product p : pa.getProductsServed()) {
            ProductArea prodAr = new ProductArea(p, pa.getArea());
            if (!findSupp.containsKey(prodAr))
                findSupp.put(prodAr, new LinkedList<Supplier>());
            findSupp.get(prodAr).add(pa);
        }

        supplierList.add(pa);
    }

    public List<Supplier> returnAvaliableSupplier(Product pd, Area ar) {
        Iterator hmIterator = findSupp.entrySet().iterator();

        ProductArea prodAr = new ProductArea(pd, ar);
        while (hmIterator.hasNext()) {
            Map.Entry mp = (Map.Entry) hmIterator.next();
            ProductArea mapElement = (ProductArea) mp.getKey();
            if (mapElement.getA() == prodAr.getA() & mapElement.getId() == prodAr.getId())
                prodAr = mapElement;
        }
        List<Supplier> sp = findSupp.get(prodAr);
        return sp;
    }

    public void removeSupplier(Supplier pa) {
        supplierList.remove(pa);
    }

    public void removeStore(Store pa) {
        storeList.remove(pa);
    }

    public void ToStringSuppliers() {
        for (Supplier sp : supplierList) {

            System.out.print(sp.getName() + " ID:" + sp.getId() + ", ");

        }
        System.out.println("");
    }

    public void ToStringProducts() {
        for (Product sp : productList) {

            System.out.print(sp.getName() + " ID:" + sp.getId() + ", ");
        }
        System.out.println("");
    }

    public void ToStringStores() {
        for (Store sp : storeList) {

            System.out.print(sp.getName() + " ID:" + sp.getId() + ", ");
        }
        System.out.println("");
    }

    public void setSupplierList(List<Supplier> supplierList) {
        this.supplierList = supplierList;
    }

    public void addSupplierToProduct(ProductArea pa, Supplier sup) {
        findSupp.get(pa).add(sup);
    }

    public void removeSupplierFromProduct(ProductArea pa, Supplier sup) {
        findSupp.get(pa).remove(sup);
    }

    public void removeProductHash(ProductArea pa) {
        findSupp.remove(pa);
    }

    public void addProductHash(ProductArea pa) {
        findSupp.put(pa, new LinkedList<Supplier>());
    }

    public List<Supplier> getListSupplier(ProductArea pa) {
        return findSupp.get(pa);
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }
    public void removeProduct(Product pa) {
        productList.remove(pa);
    }


    public void load() throws Exception {
        DALController con=DALController.getInstance();
        try {
            productList=con.pro.LoadProducts();
            storeList=con.sto.LoadStores();
            supplierList=con.sup.LoadEmptySuppliers();
            putProductsInSupp(con);

        } catch (SQLException throwables) {
            throw new Exception("Error Loading Stores/Suppliers/products");
        }
    }
    private void putProductsInSupp(DALController con) throws SQLException {
        HashMap<Integer,Product> mapedProducts=new HashMap<>();
        HashMap<ProductArea, List<Supplier>> productAreaHash= new HashMap<>();

        for (Product p:productList) {
            mapedProducts.put(p.getId(),p);
        }
        for (Supplier s:supplierList) {
            List<Integer> products= con.sup.LoadSupplierProductList(s.getId());
            List<Product> supProductes=products.stream().map(mapedProducts::get).collect(Collectors.toList());
            s.setProductsServed(supProductes);

            for (Product p: supProductes) {
                List<ProductArea> list = new ArrayList<ProductArea>(productAreaHash.keySet());
                Optional<ProductArea> tuple =list.stream().filter(x-> x.getId()==p.getId() && s.getArea().toString().equals(x.getA())).findFirst();
                if((tuple.isPresent()) && (!productAreaHash.get(tuple.get()).contains(s))){
                    productAreaHash.get(tuple.get()).add(s);
                }
                else if((tuple.isPresent()) && (productAreaHash.get(tuple.get()).contains(s))){
                    //do nothing
                }
                else if(!tuple.isPresent()){
                    List<Supplier> temp= new ArrayList<>();
                    temp.add(s);
                    productAreaHash.put(new ProductArea(p, s.getArea()), temp);
                }
            }
        }
        findSupp=productAreaHash;


    }*/
}

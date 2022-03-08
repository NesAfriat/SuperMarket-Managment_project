package BusinessLayer.FacedeModel.Objects;

import BusinessLayer.paymentMethods;

import java.util.List;

public class SupplierResponse {
    public final int id;
    public final String supplierName;
    public final String bankAcount;
    public final paymentMethods paymentMethods;
    public final List<contactResponse> contacts;

    public SupplierResponse(int id, String supplierName, String bankAcount, paymentMethods paymentMethods, List<contactResponse> contacts) {
        this.id = id;
        this.supplierName = supplierName;
        this.bankAcount = bankAcount;
        this.paymentMethods = paymentMethods;
        this.contacts = contacts;
    }

}

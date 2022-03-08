package BusinessLayer.FacedeModel.Objects;

import java.time.LocalDate;
import java.util.HashMap;

public class orderResponse {
    public final int id;
    public final int Supplierid;
    public final LocalDate date;
    public final HashMap<Integer,Integer> quantity;
    public final double TotalPayment;
    public final boolean isConstant;

    public orderResponse(int id, int supplierid, LocalDate date, HashMap<Integer, Integer> quantity, double totalPayment,boolean isConstant) {
        this.id = id;
        Supplierid = supplierid;
        this.date = date;
        this.quantity = quantity;
        TotalPayment = totalPayment;
        this.isConstant=isConstant;
    }
}

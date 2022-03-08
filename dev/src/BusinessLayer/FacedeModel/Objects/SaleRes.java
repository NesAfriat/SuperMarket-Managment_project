package BusinessLayer.FacedeModel.Objects;

import BusinessLayer.Sales.Sale;

import java.util.LinkedList;

import static BusinessLayer.FacedeModel.inventModel.getDate;

public class SaleRes {
    public int sale_id;
    public double discount_percent;
    public String sale_description;
    public String start_date;
    public String end_date;
    public LinkedList<String> affected;

    public SaleRes(Sale sale) {
        this.sale_id = sale.getSale_id();
        this.discount_percent = sale.getDiscount_percent();
        this.sale_description = sale.getSale_description();
        this.start_date = getDate(sale.getStart_date());
        this.end_date = getDate(sale.getEnd_date());
        this.affected = sale.getAffected();
    }

    @Override
    public String toString() {
        return "{" +
                "sale id=" + sale_id +
                ", discount percent=" + discount_percent+"%" +
                ", sale description= " +'"'+ sale_description+'"' +
                ", start date=" + start_date +
                ", end date=" + end_date +
                ", affected=" + affected +
                '}'+'\n';
    }
}

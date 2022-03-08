package BusinessLayer.Sales;

import java.util.Date;
import java.util.LinkedList;

public abstract class Sale {
    protected int sale_id;                  //sale id
    protected Double discount_percent;          //sale discount
    protected String sale_description;          //sale description
    protected Date start_date;                  //sale starting date
    protected Date end_date;                    //sale end date

    public Sale(int sale_id, Double discount_percent, String sale_description, Date start_date, Date end_date) {
        this.discount_percent = discount_percent;
        this.sale_description = sale_description;
        this.start_date = start_date;
        this.end_date = end_date;
        this.sale_id = sale_id;
    }

    public Integer getSale_id() {
        return sale_id;
    }

    public abstract LinkedList<String> getAffected();

    public abstract String affectedToString();

    public Double getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(Double discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getSale_description() {
        return sale_description;
    }

    public void setSale_description(String sale_description) {
        this.sale_description = sale_description;
    }

    public Date getStart_date() {
        return start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public String toString() {
        return "Sale: " + sale_id + " {" +
                "Affects=" + affectedToString() + "\n" +
                "Discount present: " + discount_percent + "\n" +
                "Description: " + sale_description + "\n" +
                "Start at: " + start_date + "\n" +
                "Ends at" + end_date + "}" + "\n";
    }
}




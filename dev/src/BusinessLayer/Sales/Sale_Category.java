package BusinessLayer.Sales;

import java.util.Date;
import java.util.LinkedList;

public class Sale_Category extends Sale {
    private LinkedList<String> affectedCategories;

    public Sale_Category(int sale_id, Double discount_percent, String sale_description, Date start_date, Date end_date, LinkedList<String> affected) {
        super(sale_id, discount_percent, sale_description, start_date, end_date);
        this.affectedCategories = affected;
    }

    public LinkedList<String> getAffected() {
        return affectedCategories;
    }

    public String affectedToString() {
        String affect = "";
        for (String s : affectedCategories)
            affect = affect + ", " + s;
        if (affect.equals(""))
            return affect;
        return affect.substring(2);
    }

}

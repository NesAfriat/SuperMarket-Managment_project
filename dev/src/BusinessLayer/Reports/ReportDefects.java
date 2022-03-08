package BusinessLayer.Reports;

import BusinessLayer.Controlls.Stock_Controller;
import BusinessLayer.GeneralProduct;
import BusinessLayer.Item;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReportDefects implements Report {
    private final String subject = "defects";
    private final Date creationDate;
    private int reportID;
    private LinkedList<String> categories;
    private String timeRange;
    private String report_data;

    public ReportDefects(Integer reportId, String time, LinkedList<String> categories) {
        this.reportID = reportId;
        this.categories = categories;
        this.timeRange = time;
        creationDate = new Date();
        this.report_data = "";
    }

    public ReportDefects(int repID, String time_range, LinkedList<String> categoriesList, Date date, String data) {
        this.reportID = repID;
        this.categories = categoriesList;
        this.timeRange = time_range;
        this.report_data = data;
        this.creationDate = date;
    }

    @Override
    public void createReport() throws Exception {
        Stock_Controller st_c = Stock_Controller.getInstance();
        List<Item> items = st_c.get_defected_items();
        List<GeneralProduct> products;
        for (Item item : items) {
            for (String cat : this.categories) {
                products = st_c.get_category_products(cat);
                for (GeneralProduct p : products)
                    if (item.getProduct_id().equals(p.getProduct_id()) && relevantToTimeRange(item.getExpiration_date()))
                        this.report_data = this.report_data + '\n' + item;
            }
        }
    }


    private boolean relevantToTimeRange(Date expiration_date) {
        switch (timeRange) {
            case "d":
                if (getDate(expiration_date).substring(8).equals(getDate(this.creationDate).substring(8)))
                    return true;
                break;
            case "w":
                if (Math.abs((this.creationDate.getTime() - expiration_date.getTime()) / (60 * 60 * 24 * 1000)) <= 7)
                    return true;
                break;
            case "m":
                if (Math.abs((this.creationDate.getTime() - expiration_date.getTime()) / (60 * 60 * 24 * 1000)) <= 30)
                    return true;
                break;
        }
        return false;

    }

    @Override
    public String getSubject() {
        return this.subject;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public int getReportID() {
        return this.reportID;
    }


    @Override
    public LinkedList<String> getCategories() {
        return this.categories;
    }

    @Override
    public String getTimeRange() {
        return timeRange;
    }

    @Override
    public String getReportData() {
        return this.report_data;
    }

    @Override
    public String toString() {
        return "Report{" +
                "\nreport_id= " + this.reportID +
                "\nreport_date= " + this.creationDate +
                "\nsubject=" + subject +
                "\ntimeRange=" + timeRange +
                "\nreport_data= " + report_data +
                '}';
    }

    private String getDate(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}

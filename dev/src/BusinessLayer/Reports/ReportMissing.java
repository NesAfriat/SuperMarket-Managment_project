package BusinessLayer.Reports;

import BusinessLayer.Controlls.Stock_Controller;
import BusinessLayer.GeneralProduct;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ReportMissing implements Report {
    private final String subject = "missing";
    private final Date creationDate;
    private Integer reportID;
    private LinkedList<String> categories;
    private String timeRange;
    private String report_data;

    public ReportMissing(Integer reportId, String time, LinkedList<String> categories) {
        this.reportID = reportId;
        this.categories = categories;
        this.timeRange = time;
        creationDate = new Date();
        this.report_data = "";
    }

    //for Dal
    public ReportMissing(int repID, String time_range, LinkedList<String> categoriesList, Date date, String data) {
        this.reportID = repID;
        this.categories = categoriesList;
        this.timeRange = time_range;
        this.report_data = data;
        this.creationDate = date;
    }

    @Override
    public void createReport() throws Exception {
        Stock_Controller st_c = Stock_Controller.getInstance();
        List<GeneralProduct> missing_products = st_c.get_missing_products();
        List<GeneralProduct> catProducts;
        for (GeneralProduct mp : missing_products) {
            for (String cat : this.categories) {
                catProducts = st_c.get_category_products(cat);
                for (GeneralProduct gp : catProducts)
                    if (gp.getProduct_id().equals(mp.getProduct_id())) {
                        this.report_data = this.report_data + "\n" + mp.toString();
                        break;
                    }
            }
        }
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
}

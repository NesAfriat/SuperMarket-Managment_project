package BusinessLayer.Reports;

import BusinessLayer.Controlls.Stock_Controller;
import BusinessLayer.GeneralProduct;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class ReportStock implements Report {
    private final String subject = "stock";
    private final Date creationDate;
    private Integer reportID;
    private LinkedList<String> categories;
    private String timeRange;
    private String report_data;

    public ReportStock(Integer reportId,String time, LinkedList<String> categories) {
        this.reportID = reportId;
        this.categories = categories;
        this.timeRange = time;
        creationDate = new Date();
        this.report_data = "";
    }
    //for Dal
    public ReportStock(int repID, String time_range, LinkedList<String> categoriesList, Date creation_date, String data) {
        this.reportID = repID;
        this.categories = categoriesList;
        this.timeRange = time_range;
        this.report_data = data;
        this.creationDate = creation_date;
    }


    @Override
    public void createReport() throws Exception {
        Stock_Controller st_c = Stock_Controller.getInstance();
        List<GeneralProduct> existProducts;
        for (String cat : this.categories) {
            existProducts = st_c.get_category_products(cat);
            for (GeneralProduct p : existProducts)
                this.report_data = this.report_data +"\n" +p.toString();
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

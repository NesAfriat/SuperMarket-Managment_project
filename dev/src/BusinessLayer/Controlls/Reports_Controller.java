package BusinessLayer.Controlls;

import BusinessLayer.IdentityMap;
import BusinessLayer.Reports.Report;
import BusinessLayer.Reports.ReportFactory;
import DataLayer.DataController;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import static BusinessLayer.FacedeModel.inventModel.getDate;

public class Reports_Controller {
    private static Reports_Controller report_C = null;
    private HashMap<Integer, Report> reports;           //holds all reports ever created
    private ReportFactory reportFactory;//hold the class which create a report
    private boolean loadedReports=false;

    private void check_valid_string(String[] arr) throws Exception {
        for (String str : arr) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }

    private void check_valid_string(LinkedList<String> lst) throws Exception {
        for (String str : lst) {
            if (str.length() == 0)
                throw new Exception("input was '' which is invalid");
            if (str.length() >= 300)
                throw new Exception("input was too long (300) which is invalid");
        }
    }


    private void check_valid_Dates(Date date) throws Exception {
        Date now = new Date();
        if (date.after(now))
            throw new Exception("date can't be after today");
    }

    private Reports_Controller() {
        this.reports = new HashMap<>();
        int maxReport= getMaxReportFromData();
        this.reportFactory = new ReportFactory(maxReport);
    }

    public static Reports_Controller getInstance() {
        if (report_C == null)
            report_C = new Reports_Controller();
        return report_C;
    }

    public Report createReport( String subject, String timeRange, LinkedList<String> categories) throws Exception {
        check_valid_string(new String[]{subject, timeRange});
        check_valid_string(categories);
        Report r = reportFactory.getReport(subject, timeRange, categories);
        addReportData(r);
        reports.put(r.getReportID(), r);
        return r;
    }

    public Report getReportById(int id) throws Exception {
        Report output=null;
        if (reports.containsKey(id))
            output=reports.get(id);
        else
        output= getReportData(id);
        if (output==null)
            throw new Exception("report id doesnt exist");
        return output;
    }

    // search reports by date and subject
    public LinkedList<Integer> getReportId(String subject, Date date) throws Exception {
        check_valid_Dates(date);
        check_valid_string(new String[]{subject});
        LinkedList<Integer> output;
        output= getReportsIDFromDal(subject,getDate(date));
        return output;
    }


    public LinkedList<Report> get_all_reports() {
        LinkedList<Report> output = new LinkedList<>();
        if(!loadedReports) {
            loadAllReports();
            loadedReports=true;
        }
        for(Report r: reports.values())
        output.add(r);
        return output;
    }

    //==============================
    //reports Data function
    private void addReportData(Report r) {
        IdentityMap im= IdentityMap.getInstance();
        DataController dc= DataController.getInstance();
        im.addReport(r);
        dc.insertReport(r);
    }
    private Report getReportData(int repID)
    {
        Report output=null;
        IdentityMap im= IdentityMap.getInstance();
        DataController dc= DataController.getInstance();
         output= im.getReport(repID);
        if(output!=null)
        { return output;}
        else {
            output = dc.getReport(repID);
            if (output != null) {
                im.addReport(output);
                reports.put(repID, output);
            }
        }
        return output;
    }
    private void loadAllReports() {
        DataController dc = DataController.getInstance();
        IdentityMap im = IdentityMap.getInstance();
        LinkedList<Report> reportsList = dc.loadAllReports();
        for (Report r : reportsList) {
            im.addReport(r);
            if (!reports.containsKey(r.getReportID())) {
                reports.put(r.getReportID(), r);
            }
        }
    }
    private LinkedList<Integer> getReportsIDFromDal(String sub, String date) {
        DataController dc= DataController.getInstance();
        return dc.getReportsIDs(sub,date);
    }
    private int getMaxReportFromData() {
        DataController dc= DataController.getInstance();
        return dc.getMaxRepID();
    }

}



package BusinessLayer.FacedeModel.Objects;

import BusinessLayer.FacedeModel.inventModel;
import BusinessLayer.Reports.Report;

import java.util.LinkedList;

public class ReportRes {
    private String subject;
    private String date;
    private int reportID;
    private LinkedList<String> categories;
    private String timeRange;
    private String report_data;

    public ReportRes(Report r) {
        this.subject = r.getSubject();
        this.date = inventModel.getDate(r.getCreationDate());
        this.reportID = r.getReportID();
        this.categories = r.getCategories();
        this.timeRange = r.getTimeRange();
        this.report_data = r.getReportData();
    }

    @Override
    public String toString() {
        return
                "subject=" + subject + '\n' +
                "date=" + date + '\n' +
                "reportID=" + reportID + '\n'+
                "categories=" + categories + '\n'+
                "timeRange=" + timeRange + '\n' +
                "report_data:" + report_data+ '\n';
    }
}

package BusinessLayer.Reports;


import java.util.LinkedList;

public class ReportFactory {
    private int report_id;

    public ReportFactory(int rep_id) {
        this.report_id = rep_id;
    }

    public Report getReport(String subject, String timeRange, LinkedList<String> categories) throws Exception {
        Report r = null;
        if (subject.equals("defects")) {
            r = new ReportDefects(report_id,timeRange, categories);
            r.createReport();
            report_id++;
        } else if (subject.equals("missing")) {
            r = new ReportMissing(report_id, timeRange, categories);
            r.createReport();
            report_id++;
        } else if (subject.equals("stock")) {
            r = new ReportStock(report_id, timeRange, categories);
            r.createReport();
            report_id++;
        }
        return r;
    }

    public void setReportID(int reports_id) {
        this.report_id = reports_id;
    }
}


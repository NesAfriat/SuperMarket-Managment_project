package BusinessLayer.Reports;

import java.util.Date;
import java.util.LinkedList;

public interface Report {
    public void createReport() throws Exception;

    public String toString();

    public String getSubject();

    public Date getCreationDate();

    public int getReportID();

    public LinkedList<String> getCategories();

    public String getTimeRange();

    public String getReportData();

}

package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Pair;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Constraint;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class WorkerDataController {
//    private final static String db_name = "database.db";

    private final IdentityMap identityMap;
    private final static String connectionPath = "jdbc:sqlite:database.db";

    public WorkerDataController() {
        this.identityMap = IdentityMap.getInstance();
    }

    public void InitDatabase() {
//        File f = new File(db_name);
        //if(!f.exists() && !f.isDirectory()) {
        String workerTable = "CREATE TABLE IF NOT EXISTS Worker (" +
                "\tID\tTEXT NOT NULL," +
                "\tName\tTEXT NOT NULL," +
                "\tBankAccount\tTEXT NOT NULL," +
                "\tSalary\tREAL NOT NULL," +
                "\tEducationFund\tTEXT NOT NULL," +
                "\tvacationDaysPerMonth\tINTEGER NOT NULL," +
                "\tsickDaysPerMonth\tINTEGER NOT NULL,\n" +
                "\tstartWorkingDate\tTEXT NOT NULL,\n" +
                "\tendWorkingDate\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\")); ";

        String shiftTable = "CREATE TABLE IF NOT EXISTS Shift (" +
                "\tDate\tTEXT NOT NULL," +
                "\tShiftType\tTEXT NOT NULL," +
                "\tApproved\tINTEGER NOT NULL,\n" +
                "\tCashier_Amount\tINTEGER NOT NULL," +
                "\tStorekeeper_Amount\tINTEGER NOT NULL,\n" +
                "\tUsher_Amount\tINTEGER NOT NULL,\n" +
                "\tGuard_Amount\tINTEGER NOT NULL,\n" +
                "\tDriverA_Amount\tINTEGER NOT NULL,\n" +
                "\tDriverB_Amount\tINTEGER NOT NULL,\n" +
                "\tDriverC_Amount\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(Date,ShiftType)); ";

        String occupationTable = "CREATE TABLE IF NOT EXISTS Occupation (\n" +
                "\tWorker_ID\tTEXT NOT NULL,\n" +
                "\tJob\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(Worker_ID,Job),\n" +
                "\tFOREIGN KEY(Worker_ID) REFERENCES Worker(ID) ON DELETE CASCADE ON UPDATE CASCADE); ";

        String defaultWorkDayAssignTable = "CREATE TABLE IF NOT EXISTS DefaultWorkDayAssign (\n" +
                "\tDay\tINTEGER NOT NULL CHECK(0<Day<=7),\n" +
                "\tShiftType\tTEXT NOT NULL,\n" +
                "\tJob\tTEXT NOT NULL,\n" +
                "\tAmount\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(Day,Day,ShiftType,Job)); ";

        String defaultWorkDayShift = "CREATE TABLE IF NOT EXISTS DefaultWorkDayShift (\n" +
                "\tDay\tINTEGER NOT NULL,\n" +
                "\thasMorning\tINTEGER NOT NULL,\n" +
                "\thasEvening\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(Day)); ";

        String constraintTable = "CREATE TABLE IF NOT EXISTS Constraints (\n" +
                "\tWorker_ID\tTEXT NOT NULL,\n" +
                "\tDate\tTEXT NOT NULL,\n" +
                "\tShiftType\tTEXT NOT NULL,\n" +
                "\tConstraintType\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(Worker_ID,Date,ShiftType),\n" +
                "\tFOREIGN KEY(Worker_ID) REFERENCES Worker(ID)); ";

        String workersInShiftsTable = "CREATE TABLE IF NOT EXISTS Workers_In_Shift (\n" +
                "\tWorker_ID\tTEXT NOT NULL,\n" +
                "\tDate\tTEXT NOT NULL,\n" +
                "\tShiftType\tTEXT NOT NULL,\n" +
                "\tJob\tTEXT NOT NULL,\n" +
                "\tCONSTRAINT fk_shift FOREIGN KEY(Date,ShiftType) REFERENCES Shift(Date,ShiftType) ON UPDATE CASCADE ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(Worker_ID,Date,ShiftType),\n" +
                "\tFOREIGN KEY(Worker_ID) REFERENCES Worker(ID) ON UPDATE CASCADE ON DELETE CASCADE\n" +
                "); ";

        String transportRequestsTable = "CREATE TABLE IF NOT EXISTS TransportRequests (\n" +
                "\tOrderID\tINTEGER NOT NULL,\n" +
                "\tDate\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(OrderID, Date)\n" +
                "); ";

        String sql = workerTable + shiftTable;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new tables
            stmt.execute(workerTable);
            stmt.execute(occupationTable);
            stmt.execute(constraintTable);
            stmt.execute(defaultWorkDayAssignTable);
            stmt.execute(defaultWorkDayShift);
            stmt.execute(shiftTable);
            stmt.execute(workersInShiftsTable);
            stmt.execute(transportRequestsTable);
            LoadPreData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // }
    }

    // load some data to the db so we'll be able to test functionality
    private void LoadPreData() {
        // add all insert queries here
//        initDefaultWorkDayShiftData();
//        initDefaultWorkDayAssignData();
//        initWorkerData();
//        initShiftsData();
//        initConstraintData();
//        initOccupationData();
//        initWorkersInShifts("20/06/2021");
//        initWorkersInShifts("21/06/2021");
//        initWorkersInShifts("27/06/2021");
//        initWorkersInShifts("28/06/2021");
//        initWorkersInShifts("04/07/2021");
//        initWorkersInShifts("05/07/2021");
//        initWorkersInShifts("11/07/2021");
//        initWorkersInShifts("12/07/2021");
//        initWorkersInShifts("18/07/2021");
//        initWorkersInShifts("19/07/2021");

        //initTransportRequests();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);
            conn = DriverManager.getConnection(connectionPath, config.toProperties());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void SaveData() {
        Collection<Worker> workers = identityMap.getAllWorkers();
        for (Worker worker : workers) {
            saveWorker(worker);
        }

        Collection<WorkDay> workDays = identityMap.getAllWorkDays();
        for (WorkDay workDay : workDays) {
            saveWorkDay(workDay);
        }
    }

    private Worker selectWorker(String id) {
        Worker worker = null;
        List<Job> occupations = selectOccupations(id);
        List<Constraint> constraints = selectConstraints(id);

        String sql = "SELECT * FROM Worker WHERE ID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String ID = rs.getString(1);
                String Name = rs.getString(2);
                String BankAccount = rs.getString(3);
                double Salary = rs.getDouble(4);
                String EducationFund = rs.getString(5);
                int vacationDaysPerMonth = rs.getInt(6);
                int sickDaysPerMonth = rs.getInt(7);
                String startWorkingDate = rs.getString(8);
                String endWorkingDate = rs.getString(9);
                worker = new Worker(Name, ID, BankAccount, Salary, EducationFund, vacationDaysPerMonth, sickDaysPerMonth, startWorkingDate);
                if (endWorkingDate != null)
                    worker.fireWorker(endWorkingDate);
                worker.setConstraints(constraints);
                for (Job occupation : occupations) {
                    worker.addOccupation(occupation);
                }
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return worker;
    }

    private List<Constraint> selectConstraints(String id) {
        List<Constraint> constraints = new LinkedList<>();
        String sql = "SELECT * FROM Constraints WHERE Worker_ID = ?;";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String Date = rs.getString(2);
                String ShiftType = rs.getString(3);
                String ConstraintType = rs.getString(4);
                Constraint constraint = new Constraint(Date, WorkersUtils.parseShiftType(ShiftType), WorkersUtils.parseConstraintType(ConstraintType));
                constraints.add(constraint);
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return constraints;
    }

    private List<Job> selectOccupations(String id) {
        List<Job> occupations = new LinkedList<>();
        String sql = "SELECT * FROM Occupation WHERE Worker_ID = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String job = rs.getString(2);
                occupations.add(WorkersUtils.parseJob(job));
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return occupations;
    }

    public void addWorker(Worker worker) {
        IdentityMap.getInstance().addWorker(worker);
    }

    public void addWorkDay(WorkDay workDay) {
        IdentityMap.getInstance().addWorkDay(workDay);
    }

    private boolean insertOrIgnoreWorker(Worker worker, Connection conn) {
        boolean inserted = false;
        String statement = "INSERT OR IGNORE INTO Worker (ID, Name, BankAccount, Salary, EducationFund, vacationDaysPerMonth, " +
                "sickDaysPerMonth, startWorkingDate, endWorkingDate) VALUES (?,?,?,?,?,?,?,?,?)";
        String ID = worker.getId();
        String Name = worker.getName();
        String BankAccount = worker.getBankAccount();
        double Salary = worker.getSalary();
        String EducationFund = worker.getEducationFund();
        int vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        int sickDaysPerMonth = worker.getSickDaysPerMonth();
        String startWorkingDate = worker.getStartWorkingDate();
        String endWorkingDate = worker.getEndWorkingDate();

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, BankAccount);
            pstmt.setDouble(4, Salary);
            pstmt.setString(5, EducationFund);
            pstmt.setInt(6, vacationDaysPerMonth);
            pstmt.setInt(7, sickDaysPerMonth);
            pstmt.setString(8, startWorkingDate);
            pstmt.setString(9, endWorkingDate);
            inserted = pstmt.executeUpdate() != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    private void insertOrIgnoreOccupation(String Worker_ID, Job occupation, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Occupation (Worker_ID, Job) VALUES (?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, occupation.toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeOccupation(String Worker_ID, Job occupation) {
        String sql = "DELETE FROM Occupation WHERE Worker_ID = ? AND Job = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, occupation.toString());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrIgnoreConstraint(String Worker_ID, Constraint constraint, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Constraints (Worker_ID, Date, ShiftType, ConstraintType) VALUES (?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, constraint.getDate());
            pstmt.setString(3, constraint.getShiftType().toString());
            pstmt.setString(4, constraint.getConstraintType().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeConstraint(String Worker_ID, String Date, ShiftType ShiftType) {
        String sql = "DELETE FROM Constraints WHERE Worker_ID = ? AND Date = ? AND ShiftType = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // set the corresponding param
            pstmt.setString(1, Worker_ID);
            pstmt.setString(2, Date);
            pstmt.setString(3, ShiftType.toString());
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateWorker(Worker worker, Connection conn) {
        String statement = "UPDATE Worker SET ID = ? , "
                + "Name = ?, "
                + "BankAccount = ?, "
                + "Salary = ?, "
                + "EducationFund = ?, "
                + "vacationDaysPerMonth = ?, "
                + "sickDaysPerMonth = ?, "
                + "startWorkingDate = ?, "
                + "endWorkingDate = ? "
                + "WHERE ID = ?";

        String ID = worker.getId();
        String Name = worker.getName();
        String BankAccount = worker.getBankAccount();
        double Salary = worker.getSalary();
        String EducationFund = worker.getEducationFund();
        int vacationDaysPerMonth = worker.getVacationDaysPerMonth();
        int sickDaysPerMonth = worker.getSickDaysPerMonth();
        String startWorkingDate = worker.getStartWorkingDate();
        String endWorkingDate = worker.getEndWorkingDate();

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, ID);
            pstmt.setString(2, Name);
            pstmt.setString(3, BankAccount);
            pstmt.setDouble(4, Salary);
            pstmt.setString(5, EducationFund);
            pstmt.setInt(6, vacationDaysPerMonth);
            pstmt.setInt(7, sickDaysPerMonth);
            pstmt.setString(8, startWorkingDate);
            pstmt.setString(9, endWorkingDate);
            pstmt.setString(10, ID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveWorker(Worker worker) {
        try (Connection conn = connect()) {
            if (!insertOrIgnoreWorker(worker, conn))
                updateWorker(worker, conn);
            saveOccupations(worker.getId(), worker.getOccupations(), conn);
            saveConstraints(worker.getId(), worker.getConstraints(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveConstraints(String Worker_ID, List<Constraint> constraints, Connection conn) {
        for (Constraint constraint : constraints) {
            insertOrIgnoreConstraint(Worker_ID, constraint, conn);
        }
    }

    private void saveOccupations(String Worker_ID, List<Job> occupations, Connection conn) {
        for (Job occupation : occupations) {
            insertOrIgnoreOccupation(Worker_ID, occupation, conn);
        }
    }

    private void saveWorkDay(WorkDay workDay) {
        Shift morning = workDay.getShift(ShiftType.Morning);
        Shift evening = workDay.getShift(ShiftType.Evening);
        if (morning != null) {
            saveShift(workDay.getDate(), morning, "Morning");
        }
        if (evening != null) {
            saveShift(workDay.getDate(), evening, "Evening");
        }
    }

    private void saveShift(String date, Shift shift, String shiftType) {
        try (Connection conn = connect()) {
            boolean inserted = insertOrIgnoreShift(date, shift, shiftType, conn);
            if (!inserted) updateShift(date, shift, shiftType, conn);
            for (Job job : WorkersUtils.getShiftWorkers()) {
                if (shift != null) {
                    for (Worker worker : shift.getCurrentWorkers(job)) {
                        insertOrWorkerInShift(worker.getId(), date, shiftType, job.toString(), conn);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean insertOrIgnoreShift(String date, Shift shift, String shiftType, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Shift (Date, ShiftType, Approved ,Cashier_Amount,  Storekeeper_Amount, Usher_Amount," +
                " Guard_Amount, DriverA_Amount, DriverB_Amount, DriverC_Amount) VALUES (?,?,?,?,?,?,?,?,?,?)";
        boolean inserted = false;
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if (shift != null) {
                boolean Approved = shift.isApproved();
                int Cashier_Amount = shift.getAmountRequired(Job.Cashier);
                int Storekeeper_Amount = shift.getAmountRequired(Job.Storekeeper);
                int Usher_Amount = shift.getAmountRequired(Job.Usher);
                int Guard_Amount = shift.getAmountRequired(Job.Guard);
                int DriverA_Amount = shift.getAmountRequired(Job.DriverA);
                int DriverB_Amount = shift.getAmountRequired(Job.DriverB);
                int DriverC_Amount = shift.getAmountRequired(Job.DriverC);

                pstmt.setString(1, date);
                pstmt.setString(2, shiftType);
                pstmt.setBoolean(3, Approved);
                pstmt.setInt(4, Cashier_Amount);
                pstmt.setInt(5, Storekeeper_Amount);
                pstmt.setInt(6, Usher_Amount);
                pstmt.setInt(7, Guard_Amount);
                pstmt.setInt(8, DriverA_Amount);
                pstmt.setInt(9, DriverB_Amount);
                pstmt.setInt(10, DriverC_Amount);
                int sqlRetVal = pstmt.executeUpdate();
                if (sqlRetVal != 0) inserted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inserted;
    }

    private void updateShift(String date, Shift shift, String shiftType, Connection conn) {
        String statement = "UPDATE Shift SET Date = ? , "
                + "ShiftType = ?, "
                + "Approved = ?, "
                + "Cashier_Amount = ?, "
                + "Storekeeper_Amount = ?, "
                + "Usher_Amount = ?, "
                + "Guard_Amount = ?, "
                + "DriverA_Amount = ?, "
                + "DriverB_Amount = ?, "
                + "DriverC_Amount = ? "
                + "WHERE Date = ? AND ShiftType = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {

            if (shift != null) {
                boolean Approved = shift.isApproved();
                int Cashier_Amount = shift.getAmountRequired(Job.Cashier);
                int Storekeeper_Amount = shift.getAmountRequired(Job.Storekeeper);
                int Usher_Amount = shift.getAmountRequired(Job.Usher);
                int Guard_Amount = shift.getAmountRequired(Job.Guard);
                int DriverA_Amount = shift.getAmountRequired(Job.DriverA);
                int DriverB_Amount = shift.getAmountRequired(Job.DriverB);
                int DriverC_Amount = shift.getAmountRequired(Job.DriverC);

                pstmt.setString(1, date);
                pstmt.setString(2, shiftType);
                pstmt.setBoolean(3, Approved);
                pstmt.setInt(4, Cashier_Amount);
                pstmt.setInt(5, Storekeeper_Amount);
                pstmt.setInt(6, Usher_Amount);
                pstmt.setInt(7, Guard_Amount);
                pstmt.setInt(8, DriverA_Amount);
                pstmt.setInt(9, DriverB_Amount);
                pstmt.setInt(10, DriverC_Amount);
                pstmt.setString(11, date);
                pstmt.setString(12, shiftType);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrWorkerInShift(String workerID, String date, String shiftType, String job, Connection conn) {
        String statement = "INSERT OR IGNORE INTO Workers_In_Shift (Worker_ID, Date, ShiftType, Job) VALUES (?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setString(1, workerID);
            pstmt.setString(2, date);
            pstmt.setString(3, shiftType);
            pstmt.setString(4, job);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeShift(String date, String shiftType) {
        String sql = "DELETE FROM Shift WHERE Date = ? AND ShiftType = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, date);
            pstmt.setString(2, shiftType);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeWorkerFromShift(String id, String date, String shiftType) {
        String sql = "DELETE FROM Workers_In_Shift WHERE Worker_ID = ? AND Date = ? AND ShiftType = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, id);
            pstmt.setString(2, date);
            pstmt.setString(3, shiftType);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Worker getWorker(String ID) {
        Worker worker = identityMap.getWorker(ID);
        if (worker == null) {
            worker = selectWorker(ID);
            if (worker != null)
                identityMap.addWorker(worker);
        }
        return worker;
    }

    public List<Worker> getAllWorkers() {
        return selectAllWorkers();
    }

    public WorkDay getWorkDay(String date) {
        WorkDay workDay = identityMap.getWorkDay(date);
        if (workDay == null) {
            workDay = buildWorkDay(date);
            if (workDay != null) {
                identityMap.addWorkDay(workDay);
            }
        }
        return workDay;
    }

    public List<WorkDay> getWorkDaysFromDate(String date) {
        List<String> dates = selectDateOfFromDate(date);
        List<WorkDay> workDays = new LinkedList<>();
        for (String d : dates) {
            workDays.add(getWorkDay(d));
        }
        return workDays;
    }

    private List<String> selectDateOfFromDate(String date) {
        List<String> dates = new LinkedList<>();
        String sql = "SELECT Date FROM Shift WHERE Date >= ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                dates.add(rs.getString("Date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    private WorkDay buildWorkDay(String date) {
        Shift DBmorning = selectShift(date, "Morning");
        Shift DBevening = selectShift(date, "Evening");

        boolean hasMorning = DBmorning != null;
        boolean hasEvening = DBevening != null;
        if (!(hasEvening || hasMorning)) return null;
        WorkDay workDay = new WorkDay(hasMorning, hasEvening, date);
        if (hasMorning) {
            Shift workDayMorning = workDay.getShift(ShiftType.Morning);
            for (Job job : WorkersUtils.getShiftWorkers()) {
                try {
                    if (job != Job.Shift_Manager) {
                        workDayMorning.setAmountRequired(job, DBmorning.getAmountRequired(job));
                    } else {
                        workDayMorning.addRequiredJob(job, 1);
                    }
                    List<Worker> workersToInsert = selectWorkersInShiftByJob(date, "Morning", job.name());
                    for (Worker worker : workersToInsert) {
                        try {
                            workDayMorning.addWorker(job, worker);
                        } catch (InnerLogicException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InnerLogicException e) {
                    e.printStackTrace();
                }
            }
            if (DBmorning.isApproved()) {
                try {
                    workDayMorning.approveShift();
                } catch (InnerLogicException e) {
                    e.printStackTrace();
                }
            }
        }
        if (hasEvening) {
            Shift workDayEvening = workDay.getShift(ShiftType.Evening);
            for (Job job : WorkersUtils.getShiftWorkers()) {
                try {
                    if (job != Job.Shift_Manager) {
                        workDayEvening.setAmountRequired(job, DBevening.getAmountRequired(job));
                    } else {
                        workDayEvening.addRequiredJob(job, 1);
                    }
                    List<Worker> workersToInsert = selectWorkersInShiftByJob(date, "Evening", job.name());
                    for (Worker worker : workersToInsert) {
                        try {
                            workDayEvening.addWorker(job, worker);
                        } catch (InnerLogicException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InnerLogicException e) {
                    e.printStackTrace();
                }
                if (DBevening.isApproved()) {
                    try {
                        workDayEvening.approveShift();
                    } catch (InnerLogicException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return workDay;

    }

    private Shift selectShift(String date, String shiftType) {
        Shift outputShift = null;
        String sql = "SELECT * FROM Shift WHERE Date = ? AND ShiftType = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            pstmt.setString(2, shiftType);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                outputShift = new Shift();
                for (Job job : WorkersUtils.getShiftWorkers()) {
                    if (job != Job.Shift_Manager) {
                        String jobAmountColumn = job.name() + "_Amount";
                        outputShift.setAmountRequired(job, rs.getInt(jobAmountColumn));
                    } else {
                        outputShift.addRequiredJob(job, 1);
                    }
                    List<Worker> workersToInsert = selectWorkersInShiftByJob(date, shiftType, job.name());
                    for (Worker worker : workersToInsert) {
                        try {
                            outputShift.addWorker(job, worker);
                        } catch (InnerLogicException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (rs.getBoolean("Approved")) outputShift.approveShift();
            }
        } catch (SQLException | InnerLogicException e) {
            e.printStackTrace();
        }
        return outputShift;
    }

    private List<Worker> selectWorkersInShiftByJob(String date, String shiftType, String job) {
        List<Worker> outputWorkers = new LinkedList<>();
        String sql = "SELECT Worker_ID "
                + "FROM Workers_In_Shift WHERE Date = ? AND ShiftType = ? AND Job = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the value
            pstmt.setString(1, date);
            pstmt.setString(2, shiftType);
            pstmt.setString(3, job);

            //
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Worker worker = getWorker(rs.getString("Worker_ID"));
                outputWorkers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputWorkers;
    }

    private List<Worker> selectAllWorkers() {
        List<Worker> outputWorkers = new LinkedList<>();
        String sql = "SELECT ID FROM Worker";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                Worker worker = getWorker(rs.getString("ID"));
                outputWorkers.add(worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return outputWorkers;
    }

    public boolean[] getDefaultWorkDayShifts(int day) {
        String sql = "SELECT * FROM DefaultWorkDayShift WHERE Day = ?";
        boolean[] hasShift = new boolean[2];
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);) {
            pstmt.setInt(1, day);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                hasShift[0] = rs.getBoolean("hasMorning");
                hasShift[1] = rs.getBoolean("hasEvening");
            }
            return hasShift;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getDefaultAmountRequired(int day, String ShiftType, String job) {
        String sql = "SELECT * FROM DefaultWorkDayAssign WHERE Day = ? AND ShiftType = ? AND Job = ?";
        int amount = 0;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, day);
            pstmt.setString(2, ShiftType);
            pstmt.setString(3, job);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("Amount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public void setDefaultAmountRequired(int day, String ShiftType, String job, int amount) {
        String statement = "UPDATE DefaultWorkDayAssign SET Amount = ? "
                + "WHERE Day = ? AND ShiftType = ? AND Job = ? ";
        if (day >= 1 && day <= 5)
            day = 1;
        if (day == 6) day = 6;
        if (day == 7) day = 7;
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.setInt(1, amount);
            pstmt.setInt(2, day);
            pstmt.setString(3, ShiftType);
            pstmt.setString(4, job);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDefaultWorkDayShifts(int day, String ShiftType, boolean changeTo) {
        String columnName = "miss_chang";
        if (ShiftType.equals("Morning"))
            columnName = "hasMorning";
        else if (ShiftType.equals("Evening"))
            columnName = "hasEvening";

        String statement = "UPDATE DefaultWorkDayShift SET " + columnName + " = ? "
                + "WHERE Day = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.setBoolean(1, changeTo);
            pstmt.setInt(2, day);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pair> getAllRequests() {
        List<Pair> output = new LinkedList<>();
        String sql = "SELECT * FROM TransportRequests";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                output.add(new Pair(rs.getInt("OrderID"), rs.getString("Date")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return output;
    }

    public void addRequest(int orderID, String date) {
        String statement = "INSERT INTO TransportRequests (OrderID, Date) VALUES (?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, orderID);
            pstmt.setString(2, date);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeRequest(int orderID, String date) {
        String statement = "DELETE FROM TransportRequests WHERE OrderID = ? AND Date = ?";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement)) {
            pstmt.setInt(1, orderID);
            pstmt.setString(2, date);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void initDefaultWorkDayShiftData() {
        String statement = "INSERT OR IGNORE INTO DefaultWorkDayShift (Day,hasMorning,hasEvening) VALUES " +
                "(1,1,1), (2,1,1), (3,0,1), (4,1,0), (5,1,1), (6,1,0), (7,0,1);";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initWorkersInShifts(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String statement = "INSERT OR IGNORE INTO Workers_In_Shift (Worker_ID,Date,ShiftType,Job) VALUES" +
                "('100000002',?,'Morning','Shift_Manager'), " +
                "('100000007',?,'Morning','Cashier'), " +
                "('100000010',?,'Morning','DriverA'), " +
                "('100000003',?,'Evening','Shift_Manager'), " +
                "('100000009',?,'Evening','Cashier')," +
                "('100000011',?,'Evening','DriverB'); ";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.setString(1, date);
            pstmt.setString(2, date);
            pstmt.setString(3, date);
            pstmt.setString(4, date);
            pstmt.setString(5, date);
            pstmt.setString(6, date);
//            pstmt.setString(7, LocalDate.now().format(formatter));
//            pstmt.setString(8, LocalDate.now().format(formatter));
//            pstmt.setString(9, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(10, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(11, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(12, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(13, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(14, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(15, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(16, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(17, LocalDate.now().format(formatter));
//            pstmt.setString(18, LocalDate.now().format(formatter));
//            pstmt.setString(19, LocalDate.now().plusDays(1).format(formatter));
//            pstmt.setString(20, LocalDate.now().plusDays(1).format(formatter));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initShiftsData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String statement = "INSERT OR IGNORE INTO Shift (Date,ShiftType,Approved,Cashier_Amount,Storekeeper_Amount,Usher_Amount,Guard_Amount,DriverA_Amount,DriverB_Amount,DriverC_Amount) VALUES " +
                "(? ,'Morning',1,2,1,3,1,1,1,1), " +
                "(? ,'Evening',0,2,1,3,1,1,1,1), " +
                "(? ,'Morning',0,2,1,1,1,1,1,1), " +
                "(? ,'Evening',0,2,1,3,1,1,1,1), " +
                "(? ,'Morning',0,1,1,0,1,1,1,1), " +
                "(? ,'Evening',0,1,1,0,1,1,1,1), " +
                "(? ,'Morning',0,2,1,3,1,1,1,1);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.setString(1, LocalDate.now().format(formatter));
            pstmt.setString(2, LocalDate.now().format(formatter));
            pstmt.setString(3, LocalDate.now().plusDays(1).format(formatter));
            pstmt.setString(4, LocalDate.now().plusDays(1).format(formatter));
            pstmt.setString(5, LocalDate.now().plusDays(2).format(formatter));
            pstmt.setString(6, LocalDate.now().plusDays(2).format(formatter));
            pstmt.setString(7, LocalDate.now().plusDays(3).format(formatter));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initDefaultWorkDayAssignData() {
        String statement = "INSERT OR IGNORE INTO DefaultWorkDayAssign (Day,ShiftType,Job,Amount) VALUES (7,'Morning','Cashier',2), " +
                " (7,'Evening','Cashier',2), " +
                "(7,'Morning','Storekeeper',2)," +
                "(7,'Evening','Storekeeper',2), " +
                "(7,'Morning','Usher',3), " +
                "(7,'Evening','Usher',3), " +
                "(7,'Morning','Guard',1), " +
                "(7,'Evening','Guard',1), " +
                "(7,'Morning','DriverA',0), " +
                "(7,'Evening','DriverA',0), " +
                "(7,'Morning','DriverB',0), " +
                "(7,'Evening','DriverB',0), " +
                "(7,'Morning','DriverC',0), " +
                "(7,'Evening','DriverC',0), " +
                "(6,'Morning','Cashier',4), " +
                "(6,'Evening','Cashier',3), " +
                "(6,'Morning','Storekeeper',2), " +
                "(6,'Evening','Storekeeper',2), " +
                "(6,'Morning','Usher',3), " +
                "(6,'Evening','Usher',3), " +
                "(6,'Morning','Guard',1), " +
                "(6,'Evening','Guard',1), " +
                "(6,'Morning','DriverA',1), " +
                "(6,'Evening','DriverA',0), " +
                "(6,'Morning','DriverB',1), " +
                "(6,'Evening','DriverB',0), " +
                "(6,'Morning','DriverC',1), " +
                "(6,'Evening','DriverC',0), " +
                "(1,'Morning','Cashier',5), " +
                "(1,'Evening','Cashier',6), " +
                "(1,'Morning','Storekeeper',3), " +
                "(1,'Evening','Storekeeper',3), " +
                "(1,'Morning','Usher',4), " +
                "(1,'Evening','Usher',4), " +
                "(1,'Morning','Guard',2), " +
                "(1,'Evening','Guard',2), " +
                "(1,'Morning','DriverA',3), " +
                "(1,'Evening','DriverA',2), " +
                "(1,'Morning','DriverB',3), " +
                "(1,'Evening','DriverB',2), " +
                "(1,'Morning','DriverC',3), " +
                "(1,'Evening','DriverC',1);";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initOccupationData() {
        String statement = "INSERT OR IGNORE INTO Occupation (Worker_ID,Job) VALUES " +
                "('100000009','Cashier'), " +
                "('100000009','Storekeeper'), " +
                "('100000009','Usher'), " +
                "('100000008','Cashier'), " +
                "('100000008','Storekeeper'), " +
                "('100000003','Transport_Manager'), " +
                "('100000003','Shift_Manager'), " +
                "('100000003','Store_Manager'), " +
                "('100000002','Transport_Manager'), " +
                "('100000002','Shift_Manager'), " +
                "('100000002','Store_Manager'), " +
                "('100000014','Guard'), " +
                "('100000002','Shift_Manager'), " +
                "('100000013','Guard'), " +
                "('100000001','HR_Manager'), " +
                "('100000012','Guard'), " +
                "('100000012','Usher'), " +
                "('100000011','Storekeeper'), " +
                "('100000011','Usher'), " +
                "('100000007','Cashier'), " +
                "('100000006','Cashier')," +
                " ('100000005','Shift_Manager'), " +
                "('100000004','Shift_Manager'), " +
                "('100000010','Cashier')," +
                " ('100000010','Storekeeper'), " +
                "('100000010','Usher'), " +
                "('100000010','DriverA'), " +
                "('100000011','DriverB'), " +
                "('100000012','DriverC');";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initConstraintData() {
        String statement = "INSERT OR IGNORE INTO Constraints (Worker_ID,Date,ShiftType,ConstraintType) VALUES" +
                " ('100000002','21/09/2021','Morning','Cant')," +
                " ('100000002','21/09/2021','Evening','Cant')," +
                " ('100000002','22/09/2021','Morning','Cant')," +
                " ('100000002','22/09/2021','Evening','Cant')," +
                " ('100000003','22/09/2021','Morning','Cant');";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initWorkerData() {
        String statement = "INSERT OR IGNORE INTO Worker (ID,Name,BankAccount,Salary,EducationFund,vacationDaysPerMonth,sickDaysPerMonth,startWorkingDate,endWorkingDate)" +
                " VALUES " +
                "('100000009','ronen','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000008','ronen','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000003','kobi','1',1.0,'1',1,1,'01/01/2021',NULL), " +
                "('100000014','meni','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000002','avi','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000013','micha','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000001','dan','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000012','eliad','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000011','dolev','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000007','shaol','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000006','moti','1',1.0,'1',1,1,'01/01/2016',NULL), " +
                "('100000005','eli','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000004','moshe','1',1.0,'1',1,1,'01/01/2018',NULL), " +
                "('100000010','moshe','1',1.0,'1',1,1,'01/01/2018',NULL);";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(statement);) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}






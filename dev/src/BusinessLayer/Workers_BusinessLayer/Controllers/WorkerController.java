package BusinessLayer.Workers_BusinessLayer.Controllers;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Workers.*;
import BusinessLayer.Workers_BusinessLayer.WorkersUtils;
import DataLayer.Workers_DAL.WorkerDataController;


public class WorkerController {
    private final static String DEFAULT_ADMINISTRATOR_ID = "000000000";
    private Worker loggedIn;
    private WorkersList workersList;



    public WorkerController() {
        this.workersList = new WorkersList();
        WorkerDataController workerDataController = new WorkerDataController();
        workerDataController.InitDatabase();
    }

    public WorkersList getWorkersList() {
        return this.workersList;
    }

    public Worker getLoggedIn() throws InnerLogicException {
        if(loggedIn == null) throw new InnerLogicException("tried to get  the logged in worker but no worker was logged in");
        return loggedIn;
    }

    public Worker login(String id) throws InnerLogicException {
        if (loggedIn != null) throw new InnerLogicException("tried to login while another user is already logged in");
         Worker worker = workersList.getWorker(id); // if the id isn't belong to any user this line will throw the right exception.
        if(worker.getEndWorkingDate() != null) throw new InnerLogicException("this worker is no longer employed");
        loggedIn = worker;
        return loggedIn;
    }

    public void logout() throws InnerLogicException {
        if(loggedIn == null) throw new InnerLogicException("tried to log out but no worker was logged in");
        WorkerDataController workerDataController = new WorkerDataController();
        workerDataController.SaveData();
        loggedIn = null;
    }

    public Worker addWorker(String name, String id, String bankAccount, double salary, String educationFund,
                          int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {

        if (loggedIn == null) throw new InnerLogicException("cant add new worker to the system because no worker is logged in");
        if (!loggedIn.getIsAdmin())
            throw new InnerLogicException("cant add new worker to the system because loggedIn is not admin");
        WorkersUtils.dateValidation(startWorkingDate);
        return workersList.addWorker(name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate);
    }

    public Worker getWorker(String id) throws InnerLogicException {
       if(loggedIn != null && !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to get details of another worker");
       return workersList.getWorker(id);
    }

    public Worker fireWorker(String id, String endWorkingDate) throws InnerLogicException {
        VerifyNotAdministrator(id);
        WorkersUtils.notPastDateValidation(endWorkingDate);
        return  workersList.fireWorker(id, endWorkingDate);
    }

    public Constraint addConstraint(String date, String shiftType, String constraintType) throws InnerLogicException {
        if(loggedIn == null) throw new InnerLogicException("tried to add constraint but no user was logged in");
        ShiftType st;
        ConstraintType ct;
        WorkersUtils.dateValidation(date);
        st = WorkersUtils.parseShiftType(shiftType);
        ct = WorkersUtils.parseConstraintType(constraintType);
        return loggedIn.addConstraint(date, st, ct);
    }

    public Constraint removeConstraint(String date, String shiftType) throws InnerLogicException {
        if(loggedIn == null) throw new InnerLogicException("tried to remove constraint but no user was logged in");
        WorkersUtils.dateValidation(date);
        ShiftType st = WorkersUtils.parseShiftType(shiftType);
        Constraint constraint = loggedIn.removeConstraint(date, st);
        WorkerDataController workerDataController = new WorkerDataController();
        workerDataController.removeConstraint(loggedIn.getId(),date,st);
        return constraint;
    }

    public Worker addOccupation(String id, String job) throws InnerLogicException {
        if(loggedIn == null || !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to add occupation to a worker");
        VerifyNotAdministrator(id);
        Job role = WorkersUtils.parseJob(job);
        Worker worker = workersList.getWorker(id);
        worker.addOccupation(role);
        return worker;
    }

    public Worker removeOccupation(String id, String job) throws InnerLogicException {
        if(loggedIn == null || !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to add occupation to a worker");
        VerifyNotAdministrator(id);
        Job role = WorkersUtils.parseJob(job);
        Worker worker = workersList.getWorker(id);
        worker.removeOccupation(role);
        WorkerDataController workerDataController = new WorkerDataController();
        workerDataController.removeOccupation(id,role);
        return worker;
    }

    private void VerifyNotAdministrator(String id) throws InnerLogicException {
        if (DEFAULT_ADMINISTRATOR_ID.equals(id)){
            throw new InnerLogicException("Cannot do this operation on administrator user");
        }
    }

    public Worker setWorkerID(String id, String newID) throws InnerLogicException {
        if(loggedIn == null || !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to add occupation to a worker");
        VerifyNotAdministrator(id);
        Worker worker = workersList.getWorker(id);
        if (workersList.contains(newID)){
            throw new InnerLogicException("new id is already existing in the system");
        }
        worker.setID(newID);
        return worker;
    }

    public Worker setWorkerName(String id, String name) throws InnerLogicException {
        if(loggedIn == null || !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to add occupation to a worker");
        VerifyNotAdministrator(id);
        Worker worker = workersList.getWorker(id);
        worker.setName(name);
        return worker;
    }

    public Worker setWorkerSalary(String id, double salary) throws InnerLogicException {
        if(loggedIn == null || !loggedIn.getIsAdmin()) throw new InnerLogicException("non admin worker tried to add occupation to a worker");
        VerifyNotAdministrator(id);
        Worker worker = workersList.getWorker(id);
        worker.setSalary(salary);
        return worker;
    }
}


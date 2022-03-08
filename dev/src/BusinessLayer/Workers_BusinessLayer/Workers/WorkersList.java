package BusinessLayer.Workers_BusinessLayer.Workers;

import BusinessLayer.Workers_BusinessLayer.Controllers.WorkerController;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import DataLayer.Workers_DAL.WorkerDataController;

import java.util.LinkedList;
import java.util.List;

public class WorkersList {
    private List<Worker> workers;

    public WorkersList(){
        this.workers = new LinkedList<>();
        try { // default administrator worker
            Worker worker = new Worker( "Administrator", "000000000", "0", 0, "0", 0, 0, "00/00/0000");
            workers.add(worker);
            worker.addOccupation(Job.HR_Manager); // administrator is admin
        } catch (InnerLogicException ignored) { }
    }

    public List<Worker> getWorkersByJob(Job job){
        LinkedList<Worker> output = new LinkedList<>();
        WorkerDataController workerDataController = new WorkerDataController();
        for (Worker worker: workerDataController.getAllWorkers()) {
            if(worker.canWorkInJob(job)) output.add(worker);
        }
        return output;
    }

    public Worker getWorker(String id) throws InnerLogicException {
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return  worker;
        }
        //search in db
        WorkerDataController workerDataController = new WorkerDataController();
        Worker worker = workerDataController.getWorker(id);
        if (worker == null)
            throw new InnerLogicException("there is no worker with that id in the system");
        else {
            workers.add(worker);
        }
        return worker;
    }

    public Worker addWorker(String name, String id, String bankAccount, double salary, String educationFund,
                            int vacationDaysPerMonth, int sickDaysPerMonth, String startWorkingDate) throws InnerLogicException {
        WorkerDataController workerDataController = new WorkerDataController();
        if (contains(id) || workerDataController.getWorker(id) != null) throw new InnerLogicException("the system already have worker with the id: " + id);

        Worker newWorker = new Worker( name, id, bankAccount, salary, educationFund, vacationDaysPerMonth,
                sickDaysPerMonth, startWorkingDate);

        workerDataController.addWorker(newWorker);
        workers.add(newWorker);
        return newWorker;
    }

    public Worker fireWorker(String id, String endWorkingDate) throws InnerLogicException {
        Worker firedWorker = getWorker(id);// throws exception if the id not good
        firedWorker.fireWorker(endWorkingDate);
        return firedWorker;
    }

    public boolean contains(String id){
        for (Worker worker: workers) {
            if(worker.getId().equals(id)) return true;
        }
        return false;
    }

}

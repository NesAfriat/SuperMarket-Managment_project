package DataLayer.Workers_DAL;

import BusinessLayer.Workers_BusinessLayer.Shifts.WorkDay;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


class IdentityMap {
    private static IdentityMap instance = null;

    public static IdentityMap getInstance() {
        if (instance == null){
            instance = new IdentityMap();
        }
        return instance;
    }


    private final Map<String, Worker> workerMap; // maps id to workers
    private final Map<String, WorkDay> workDayMap; // maps dates to workdays

    private IdentityMap(){
        this.workDayMap = new HashMap<>();
        this.workerMap = new HashMap<>();
    }

    public void addWorkDay(WorkDay workDay) {
        workDayMap.put(workDay.getDate(),workDay);
    }

    public void addWorker(Worker worker) {
        workerMap.put(worker.getId(), worker);
    }


    public WorkDay getWorkDay(String date) {
        return workDayMap.get(date);
    }

    public Worker getWorker(String id) {
        return workerMap.get(id);
    }

    Collection<Worker> getAllWorkers(){
        return workerMap.values();
    }

    public Collection<WorkDay> getAllWorkDays() {
        return workDayMap.values();
    }
}

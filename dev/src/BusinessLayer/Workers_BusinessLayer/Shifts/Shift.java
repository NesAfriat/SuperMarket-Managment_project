package BusinessLayer.Workers_BusinessLayer.Shifts;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Shift {

    private static final int DEFAULT_SHIFT_MANAGER_AMOUNT_REQUIRED = 1;
    private boolean approved;
    private final Map<Job,JobArrangement> currentWorkers;

    public Shift(){
        approved = false;
        currentWorkers = new HashMap<>();
    }

    // assign new worker to the shift
    public void addWorker(Job role, Worker worker) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement == null){
            throw new InnerLogicException("tried to add worker to a job that is not required to the shift");
        }
        if(!worker.canWorkInJob(role)) throw new InnerLogicException("tried to add worker for job he is not qualified to do");
        if (jobArrangement.amountAssigned == jobArrangement.required){
            throw new InnerLogicException("Reached maximum required, remove a worker first.");
        }
        jobArrangement.workers.add(worker);
        jobArrangement.amountAssigned++;
    }

    public void removeWorker(Job role, Worker worker) throws InnerLogicException {
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement == null){
            throw new InnerLogicException("tried to remove a worker from a job that is not required for the shift");
        }
        if (jobArrangement.workers.remove(worker)){
            jobArrangement.amountAssigned--;
            if (role == Job.Shift_Manager && jobArrangement.amountAssigned != DEFAULT_SHIFT_MANAGER_AMOUNT_REQUIRED)
                approved = false;
        }
        else {
            throw new InnerLogicException("no such worker working at this position at current shift");
        }
    }

    // verify that shift contains the role and return the job arrangement for this role
    private JobArrangement getJobArrangement(Job role){ //throws InnerLogicException {
        JobArrangement jobArrangement = currentWorkers.get(role);
        /*if (jobArrangement == null){
            throw new InnerLogicException("No such role existing the shift");
        }*/
        return jobArrangement;
    }

    public void addRequiredJob(Job role, int required) throws InnerLogicException {
        if(required < 0 )throw new InnerLogicException("Can not set required workers to negative value");
        JobArrangement jobArrangement = currentWorkers.get(role);
        if (jobArrangement == null){
            jobArrangement = new JobArrangement(required);
            currentWorkers.put(role, jobArrangement);
        }

        else {
            if (jobArrangement.required != 0) {
                throw new InnerLogicException("This role is already required for the shift");
            } else {
                jobArrangement.required = required;
            }
        }
    }

    public void setAmountRequired(Job role, int required) throws InnerLogicException {
        if(required < 0 )throw new InnerLogicException("Can not set required workers to negative value");
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement == null) {
            if(role == Job.Shift_Manager && required != 1) throw new InnerLogicException("Can not change shift manager required amount");
            jobArrangement = new JobArrangement(required);
            currentWorkers.put(role, jobArrangement);
        }
        else {
            if (jobArrangement.amountAssigned > required) {
                throw new InnerLogicException("Can not set required workers to be less than assigned worker. please remove a worker first");
            }
            if (role.equals(Job.Shift_Manager))
                throw new InnerLogicException("Can not change shift manager required amount");
            jobArrangement.required = required;
        }
    }

    // get the amount of required workers for specific role in the shift
    public int getAmountRequired(Job role){
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement == null){
            return 0;
        }
        return jobArrangement.required;
    }

    public List<Worker> getCurrentWorkers(Job role){
        JobArrangement jobArrangement = getJobArrangement(role);
        if (jobArrangement != null) {
            List<Worker> currentWorkers = jobArrangement.workers;
            if (currentWorkers != null) {
                return currentWorkers;
            }
        }
        return new LinkedList<Worker>();
    }

    public int getCurrentWorkersAmount(Job role) {
        JobArrangement jobArrangement = getJobArrangement(role);
        if(jobArrangement == null){
            return 0;
        }
        return jobArrangement.amountAssigned;
    }

    public boolean isApproved(){
        return approved;
    }

    public void approveShift() throws InnerLogicException {
        JobArrangement jobArrangement = currentWorkers.get(Job.Shift_Manager);
        if (jobArrangement == null || jobArrangement.amountAssigned != DEFAULT_SHIFT_MANAGER_AMOUNT_REQUIRED){
            throw new InnerLogicException("Can not approve a shift without a shift manager");
        }
        approved = true;
    }

    public boolean isWorking(Worker worker){
        AtomicBoolean working = new AtomicBoolean(false);
        currentWorkers.forEach((job, jobArrangement) -> {
            if (jobArrangement.workers.contains(worker)) {
            working.set(true);
            }
        });
        return working.get();
    }

    Job getWorkerRole(Worker worker){
        AtomicReference<Job> role = new AtomicReference<>();
        role.set(null);
        currentWorkers.forEach((job, jobArrangement) -> {
            if (jobArrangement.workers.contains(worker)) {
                role.set(job);
            }
        });
        return role.get();
    }

    public List<Job> getJobs() {
        List<Job> jobs = new LinkedList<>();
        currentWorkers.forEach((job, jobArrangement) -> jobs.add(job));
        return jobs;
    }

    private static class JobArrangement {
        // Constructor
        JobArrangement(int required){
            this.required = required;
            amountAssigned = 0;
            workers = new LinkedList<>();
        }

        int required;
        List<Worker> workers;
        int amountAssigned;
    }
}

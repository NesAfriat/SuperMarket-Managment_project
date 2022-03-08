package BusinessLayer.Workers_BusinessLayer.Responses;

import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShiftResponse {
    private boolean approved;
    private Map<Job, JobArrangementResponse> currentWorkers;

    public ShiftResponse(Shift shift){
        this.approved = shift.isApproved();
        currentWorkers = new HashMap<>();
        List<Job> jobs = shift.getJobs();
        for (Job job: jobs) {
            List<Worker> workers = shift.getCurrentWorkers(job);
            int required = shift.getAmountRequired(job);
            int amountAssigned = shift.getCurrentWorkersAmount(job);
            currentWorkers.put(job, new JobArrangementResponse(workers,required,amountAssigned));
        }
    }

    public boolean isApproved(){return approved;}

    public boolean isFullyOccupied(){
        AtomicBoolean output = new AtomicBoolean(true);
        currentWorkers.forEach((job, jobArrangementResponse) -> {
            if (jobArrangementResponse.amountAssigned != jobArrangementResponse.required) {
                output.set(false);
            }
        });
        return output.get();
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Shift approved: ").append(approved).append("\n");
        currentWorkers.forEach((job, jobArrangement) -> {
            if (jobArrangement.required != 0) {
                stringBuilder.append(job).append(" (").append(jobArrangement.amountAssigned).append("/").append(jobArrangement.required).append(")").append(": ");
                for (WorkerResponse workerResponse : jobArrangement.workers) {
                    stringBuilder.append(workerResponse.getNameID()).append(", ");
                }
                if (!jobArrangement.workers.isEmpty())
                    stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length() - 1, ".");
                stringBuilder.append("\n");
            }
        });
        return stringBuilder.toString();
    }

    public String Settings() {
        StringBuilder stringBuilder = new StringBuilder();
        currentWorkers.forEach((job, jobArrangement) -> {
                stringBuilder.append("Job: ").append(job).append(" ,amount required: ").append(jobArrangement.required).append("\n");
        });
        return stringBuilder.toString();
    }

    private static class JobArrangementResponse{
        int required;
        List<WorkerResponse> workers;
        int amountAssigned;

        JobArrangementResponse(List<Worker> workers, int required, int amountAssigned) {
            this.required = required;
            this.amountAssigned = amountAssigned;
            this.workers = new LinkedList<>();
            for (Worker worker: workers) {
                WorkerResponse workerResponse = new WorkerResponse(worker);
                this.workers.add(workerResponse);
            }
        }

    }
}

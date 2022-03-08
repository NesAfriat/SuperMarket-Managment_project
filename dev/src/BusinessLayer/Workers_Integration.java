package BusinessLayer;

import BusinessLayer.Workers_BusinessLayer.Responses.Response;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import java.util.List;

public interface Workers_Integration {
    // date format: "DD/MM/YYYY"
    // shiftType format: "Morning" / "Evening"
    // job format: "DriverA" / "DriverB" / "DriverC" / "Storekeeper"
    /*
     if (response.ErrorOccurred()){
            print(response.getErrorMessage());
      }
     else {
            if (response.value.isEmpty()){
                print("No available workers ");
            }
            else {
                for (WorkerResponse workerRes : response.value) {
                    print(workerRes.getNameID());
                }
            }
        }
     */
    public ResponseT<List<WorkerResponse>> getWorkersInShiftByJob(String date, String shiftType, String Job);

    public Response addRequest(int OrderID, String date);

    public ResponseT<List<Job>> getWorkerOccupations(String WorkerId);



}
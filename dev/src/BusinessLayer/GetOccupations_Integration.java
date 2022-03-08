package BusinessLayer;

import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;

import java.util.List;

public interface GetOccupations_Integration {
    // this func return list of jobs that the worker with the id "WorkerId" can do.
    // if the worker exsist but dont have any job this will be empty List.
    //if the Id is not in the system or eny other error occourd the Respones.ErrorOccord will be equal true.

    //    if (response.ErrorOccurred()){
//        print(response.getErrorMessage());
//    }
//     else {
//        do somthing with response.value...
//        }
//    }
    ResponseT<List<Job>> getWorkerOccupations(String WorkerId);
}

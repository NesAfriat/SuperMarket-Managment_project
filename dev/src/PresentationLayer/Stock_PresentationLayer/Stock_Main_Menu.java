package PresentationLayer.Stock_PresentationLayer;

import BusinessLayer.FacedeModel.Objects.Response;
import BusinessLayer.FacedeModel.facade;
import BusinessLayer.GetOccupations_Integration;
import BusinessLayer.Transport_BusinessLayer.Transport_Integration;
import BusinessLayer.Workers_BusinessLayer.Responses.ResponseT;
import BusinessLayer.Workers_BusinessLayer.Responses.WorkerResponse;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_Integration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Stock_Main_Menu {
    private facade Facade;              //this object will communicate with the business layer

    BufferedReader reader;
    Response res;
    int inputi;
    String inputs;

    public Stock_Main_Menu(Transport_Integration transport_integration,GetOccupations_Integration getOccupations_integration) {
        Facade = facade.getInstance(transport_integration,getOccupations_integration);
        reader = new BufferedReader(new InputStreamReader(System.in));

    }

    public void start(GetOccupations_Integration getOccupations_integration) throws IOException {
        boolean run = true;
        while (run){
            System.out.println("1) Enter ID number for login");
            System.out.println("2) Previous");
            System.out.print("Option: ");
            inputs = reader.readLine().trim();
            if(inputs.equals("")) {
                inputi = -1;
            }else inputi=Integer.parseInt(inputs);
            if (inputi == 1){
                System.out.println("ID: ");
                inputs = reader.readLine().trim();
                ResponseT<List<Job>> jobs = getOccupations_integration.getWorkerOccupations(inputs);
                if (jobs.ErrorOccurred()) {
                    System.out.println(jobs.getErrorMessage());
                }
                else {
                    for(Job j : jobs.value){
                        if(j==Job.Storekeeper){
                            new StorekeeperMenu(Facade, reader).run();
                        }else if(j==Job.Store_Manager){
                            new StoreManagerMenu(Facade, reader).run();
                        }
                    }
                    run=false;
                    System.out.println("Exited Stock manage system");
                }
            }
            else if (inputi == 2){
                run = false;
                System.out.println("Exited Stock manage system");
            }
            else {
                System.out.println("There's no such option");
            }

        }

    }
}

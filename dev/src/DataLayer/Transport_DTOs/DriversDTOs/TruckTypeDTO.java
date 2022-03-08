package DataLayer.Transport_DTOs.DriversDTOs;

import BusinessLayer.Transport_BusinessLayer.Drives.License;

import java.util.List;

public class TruckTypeDTO {
    private final String Name;
    private final int maxWeight;
    private final int freeWeight;
    private final List<License> licensesForTruck;

    public TruckTypeDTO(String name, int maxWeight, int freeWeight, List<License> licensesForTruck) {
        Name = name;
        this.maxWeight = maxWeight;
        this.freeWeight = freeWeight;
        this.licensesForTruck = licensesForTruck;
    }

    public String getName() {
        return Name;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getFreeWeight() {
        return freeWeight;
    }

    public List<License> getLicensesForTruck() {
        return licensesForTruck;
    }
}

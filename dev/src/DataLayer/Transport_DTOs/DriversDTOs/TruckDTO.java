package DataLayer.Transport_DTOs.DriversDTOs;

public class TruckDTO {
    private final String name;
    private final int licensePlate;
    private final TruckTypeDTO truckType;

    public TruckDTO(String name, int licensePlate, TruckTypeDTO truckType) {
        this.name = name;
        this.licensePlate = licensePlate;
        this.truckType = truckType;
    }

    public String getName() {
        return name;
    }

    public int getLicensePlate() {
        return licensePlate;
    }

    public TruckTypeDTO getTruckType() {
        return truckType;
    }
}

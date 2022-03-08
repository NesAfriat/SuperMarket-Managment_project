package BusinessLayer.FacedeModel.Objects;

public class contactResponse {
    public final int id;
    public final String name;
    public final  String PhoneNumber;
    public  final String Email;

    public contactResponse(int id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        PhoneNumber = phoneNumber;
        Email = email;
    }
}

package DataLayer.Transport_DAL;

public class DALController {

    // static variable single_instance of type Singleton
    private static DALController Con_instance = null;

    public ProductDAL pro;
    public StoreDAL sto;
    public SupplierDAL sup;
    public TransportDocDAL tra;
    public TruckDAL tru;


    // private constructor restricted to this class itself
    private DALController()
    {
         pro=new ProductDAL();
         sto=new StoreDAL();
         sup=new SupplierDAL();
         tra=new TransportDocDAL();
         tru=new TruckDAL();
    }

    // static method to create instance of Singleton class
    public static DALController getInstance()
    {
        if (Con_instance == null)
            Con_instance = new DALController();

        return Con_instance;
    }
}

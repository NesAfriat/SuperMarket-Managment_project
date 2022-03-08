package Workers_Tests;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.Shift;
import BusinessLayer.Workers_BusinessLayer.Workers.Job;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import org.junit.*;
import java.time.format.DateTimeFormatter;


public class ShiftUnitTests {
    private static Shift shift;
    private static Worker worker;
    private static DateTimeFormatter formatter;
    private static Job workerRole;
    private static Job notWorkersRole;


    @BeforeClass
    public static void initFields() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        workerRole = Job.Shift_Manager;
        notWorkersRole = Job.Guard;
        try {
            worker = new Worker("rami", "333333333", "1", 1, "1", 1, 1, "01/01/2018");
            worker.addOccupation(workerRole);
        } catch (InnerLogicException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void initTest() {
       shift = new Shift();
    }

    @Test
    public void addWorker_Allow() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 1);
        }
        catch (InnerLogicException e){exception = e;}

        //act
        try {
            shift.addWorker(workerRole,worker);
        } catch (InnerLogicException e) {
           exception = e;
        }

        //assert
        if (exception == null)
            Assert.assertTrue(shift.isWorking(worker));
        else {
            Assert.fail(exception.getMessage());
        }
    }

    @Test
    public void addWorker_JobIsFullyOccupied_Fail() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 0);
        }
        catch (InnerLogicException e){Assert.fail("Failed trying to add a required job during arrange test part. error: \"+ e.getMessage()");}

        //act
        try {
            shift.addWorker(workerRole,worker);
        } catch (InnerLogicException e) {
            exception = e;
        }

        //assert
        if (exception != null)
            Assert.assertFalse(shift.isWorking(worker));
        else {
            Assert.fail("should have not allowed to add worker to work with a role already fully occupied");
        }
    }

    @Test
    public void addWorker_WorkerIsNotQualified_Fail() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 1);
            shift.addRequiredJob(notWorkersRole, 1);
        }
        catch (InnerLogicException e){Assert.fail("Failed trying to add a required job during arrange test part. error: \"+ e.getMessage()");}

        //act
        try {
            shift.addWorker(notWorkersRole,worker);
        } catch (InnerLogicException e) {
            exception = e;
        }

        //assert
        if (exception != null)
            Assert.assertFalse(shift.isWorking(worker));
        else {
            Assert.fail("should have not allowed to add worker to work with a role he is not qualified to do");
        }
    }

    @Test
    public void removeWorker_Allow() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 1);
            shift.addWorker(workerRole, worker);
            Assert.assertTrue(shift.isWorking(worker));
        }
        catch (InnerLogicException e){exception = e;}

        //act
        try {
            shift.removeWorker(workerRole,worker);
        } catch (InnerLogicException e) {
            exception = e;
        }

        //assert
        if (exception == null)
            Assert.assertFalse(shift.isWorking(worker));
        else {
            Assert.fail(exception.getMessage());
        }
    }

    @Test
    public void removeWorker_NoSuchWorker_Fail() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 1);
            Assert.assertFalse(shift.isWorking(worker));
        }
        catch (InnerLogicException e){Assert.fail("Failed during arrange test part. error: "+ e.getMessage());}

        //act
        try {
            shift.removeWorker(workerRole,worker);
        } catch (InnerLogicException e) {
            exception = e;
        }

        //assert
        if (exception != null) {
            Assert.assertEquals(0, shift.getCurrentWorkersAmount(workerRole));
        }
        else {
            Assert.fail(exception.getMessage());
        }
    }

    @Test
    public void removeWorker_ShiftManager_approvedToFalse() {
        InnerLogicException exception = null;
        //arrange
        try {
            shift.addRequiredJob(workerRole, 1);
            shift.addWorker(workerRole, worker);
            shift.approveShift();
        }
        catch (InnerLogicException e){Assert.fail("Failed during arrange test part. error: "+ e.getMessage());}

        //act
        try {
            shift.removeWorker(workerRole,worker);
        } catch (InnerLogicException e) {
            exception = e;
        }

        //assert
        if (exception == null)
            Assert.assertTrue(!shift.isApproved() && !shift.isWorking(worker));
        else {
            Assert.fail(exception.getMessage());
        }

    }

}
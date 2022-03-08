package Workers_Tests;
import BusinessLayer.Workers_BusinessLayer.InnerLogicException;
import BusinessLayer.Workers_BusinessLayer.Shifts.ShiftType;
import BusinessLayer.Workers_BusinessLayer.Workers.ConstraintType;
import BusinessLayer.Workers_BusinessLayer.Workers.Worker;
import org.junit.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class WorkerUnitTests {
    private static Worker worker;
    private static DateTimeFormatter formatter;
    private static final int LEGAL_DAYS_RANGE = 15;


    @BeforeClass
    public static void initFields() {
        formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    }

    @Before
    public void initTest(){
        try {
            worker = new Worker("rami", "333333333", "1", 1, "1", 1, 1, "01/01/2018");
        } catch (InnerLogicException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addConstraint_FutureShift_InLegalRange_Allow(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
        }
        catch (InnerLogicException e){
            Assert.fail(e.getMessage());
        }

        //assert
        Assert.assertFalse(worker.canWorkInShift(date, ShiftType.Morning));
    }

    @Test
    public void addConstraint_FutureShift_LegalRange_SameShift_DifferentConstraint_Fail(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
        }
        catch (InnerLogicException e){
            Assert.fail("Should allow to add one constraint");
        }
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
            Assert.fail("Should have not allowed to add two constraints to the same shift.");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void addConstraint_ToFutureShift_Before2Weeks_Fail(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE-1)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
            Assert.fail("Should have not allowed to add a constraint to a shift happening in less than " + LEGAL_DAYS_RANGE +" days");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void addConstraint_ToShiftInThePast_Fail(){
        //arrange
        LocalDate now = LocalDate.now();
        String date = (now.minusDays(1)).format(formatter);

        //act
        try {
            worker.addConstraint(date, ShiftType.Morning, ConstraintType.Cant);
            Assert.fail("Should have not allowed to add a constraint to a shift that happened already");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void removeConstraint_Allow(){
        //arrange
        ShiftType shiftType = ShiftType.Morning;
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE)).format(formatter);
        try {
            worker.addConstraint(date, shiftType, ConstraintType.Cant);
        }
        catch (InnerLogicException e){
            Assert.fail("Failed trying to add constraint in the test arrange part");
        }

        InnerLogicException error = null;
        //act
        try {
            worker.removeConstraint(date, shiftType);
        }
        catch (InnerLogicException e){
            error = e;
        }

        if (error == null)
            Assert.assertTrue(worker.canWorkInShift(date, shiftType));
        else
            Assert.fail("Should have allowed to remove the constraint");

    }


    @Test
    public void removeConstraint_NoConstraint_Fail(){
        //arrange
        ShiftType shiftType = ShiftType.Morning;
        LocalDate now = LocalDate.now();
        String date = (now.plusDays(LEGAL_DAYS_RANGE)).format(formatter);

        //act
        try {
            worker.removeConstraint(date, shiftType);
            Assert.fail("Removing non-existing constraint should have failed");
        }
        catch (InnerLogicException e){
            Assert.assertTrue(true);
        }

    }


}

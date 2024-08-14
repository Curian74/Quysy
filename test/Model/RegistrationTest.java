package Model;

import java.util.Date;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HUNG
 */
public class RegistrationTest {
    
    public RegistrationTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getRegistrationId method, of class Registration.
     */
    @Test
    public void testGetRegistrationId() {
        System.out.println("getRegistrationId");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getRegistrationId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRegistrationId method, of class Registration.
     */
    @Test
    public void testSetRegistrationId() {
        System.out.println("setRegistrationId");
        int registrationId = 0;
        Registration instance = new Registration();
        instance.setRegistrationId(registrationId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserId method, of class Registration.
     */
    @Test
    public void testGetUserId() {
        System.out.println("getUserId");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getUserId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserId method, of class Registration.
     */
    @Test
    public void testSetUserId() {
        System.out.println("setUserId");
        int userId = 0;
        Registration instance = new Registration();
        instance.setUserId(userId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubjectId method, of class Registration.
     */
    @Test
    public void testGetSubjectId() {
        System.out.println("getSubjectId");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getSubjectId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSubjectId method, of class Registration.
     */
    @Test
    public void testSetSubjectId() {
        System.out.println("setSubjectId");
        int subjectId = 0;
        Registration instance = new Registration();
        instance.setSubjectId(subjectId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPackageId method, of class Registration.
     */
    @Test
    public void testGetPackageId() {
        System.out.println("getPackageId");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getPackageId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPackageId method, of class Registration.
     */
    @Test
    public void testSetPackageId() {
        System.out.println("setPackageId");
        int packageId = 0;
        Registration instance = new Registration();
        instance.setPackageId(packageId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegistrationDate method, of class Registration.
     */
    @Test
    public void testGetRegistrationDate() {
        System.out.println("getRegistrationDate");
        Registration instance = new Registration();
        Date expResult = null;
        Date result = instance.getRegistrationDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRegistrationDate method, of class Registration.
     */
    @Test
    public void testSetRegistrationDate() {
        System.out.println("setRegistrationDate");
        Date registrationDate = null;
        Registration instance = new Registration();
        instance.setRegistrationDate(registrationDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidFrom method, of class Registration.
     */
    @Test
    public void testGetValidFrom() {
        System.out.println("getValidFrom");
        Registration instance = new Registration();
        Date expResult = null;
        Date result = instance.getValidFrom();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValidFrom method, of class Registration.
     */
    @Test
    public void testSetValidFrom() {
        System.out.println("setValidFrom");
        Date validFrom = null;
        Registration instance = new Registration();
        instance.setValidFrom(validFrom);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValidTo method, of class Registration.
     */
    @Test
    public void testGetValidTo() {
        System.out.println("getValidTo");
        Registration instance = new Registration();
        Date expResult = null;
        Date result = instance.getValidTo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setValidTo method, of class Registration.
     */
    @Test
    public void testSetValidTo() {
        System.out.println("setValidTo");
        Date validTo = null;
        Registration instance = new Registration();
        instance.setValidTo(validTo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTotalCost method, of class Registration.
     */
    @Test
    public void testGetTotalCost() {
        System.out.println("getTotalCost");
        Registration instance = new Registration();
        double expResult = 0.0;
        double result = instance.getTotalCost();
        assertEquals(expResult, result, 0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTotalCost method, of class Registration.
     */
    @Test
    public void testSetTotalCost() {
        System.out.println("setTotalCost");
        double totalCost = 0.0;
        Registration instance = new Registration();
        instance.setTotalCost(totalCost);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNotes method, of class Registration.
     */
    @Test
    public void testGetNotes() {
        System.out.println("getNotes");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getNotes();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNotes method, of class Registration.
     */
    @Test
    public void testSetNotes() {
        System.out.println("setNotes");
        String notes = "";
        Registration instance = new Registration();
        instance.setNotes(notes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastUpdatedBy method, of class Registration.
     */
    @Test
    public void testGetLastUpdatedBy() {
        System.out.println("getLastUpdatedBy");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getLastUpdatedBy();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setLastUpdatedBy method, of class Registration.
     */
    @Test
    public void testSetLastUpdatedBy() {
        System.out.println("setLastUpdatedBy");
        int lastUpdatedBy = 0;
        Registration instance = new Registration();
        instance.setLastUpdatedBy(lastUpdatedBy);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFullName method, of class Registration.
     */
    @Test
    public void testGetFullName() {
        System.out.println("getFullName");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getFullName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setFullName method, of class Registration.
     */
    @Test
    public void testSetFullName() {
        System.out.println("setFullName");
        String fullName = "";
        Registration instance = new Registration();
        instance.setFullName(fullName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEmail method, of class Registration.
     */
    @Test
    public void testGetEmail() {
        System.out.println("getEmail");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getEmail();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setEmail method, of class Registration.
     */
    @Test
    public void testSetEmail() {
        System.out.println("setEmail");
        String email = "";
        Registration instance = new Registration();
        instance.setEmail(email);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMobile method, of class Registration.
     */
    @Test
    public void testGetMobile() {
        System.out.println("getMobile");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getMobile();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMobile method, of class Registration.
     */
    @Test
    public void testSetMobile() {
        System.out.println("setMobile");
        String mobile = "";
        Registration instance = new Registration();
        instance.setMobile(mobile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isGender method, of class Registration.
     */
    @Test
    public void testIsGender() {
        System.out.println("isGender");
        Registration instance = new Registration();
        boolean expResult = false;
        boolean result = instance.isGender();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setGender method, of class Registration.
     */
    @Test
    public void testSetGender() {
        System.out.println("setGender");
        boolean gender = false;
        Registration instance = new Registration();
        instance.setGender(gender);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUpdatedDate method, of class Registration.
     */
    @Test
    public void testGetUpdatedDate() {
        System.out.println("getUpdatedDate");
        Registration instance = new Registration();
        Date expResult = null;
        Date result = instance.getUpdatedDate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUpdatedDate method, of class Registration.
     */
    @Test
    public void testSetUpdatedDate() {
        System.out.println("setUpdatedDate");
        Date updatedDate = null;
        Registration instance = new Registration();
        instance.setUpdatedDate(updatedDate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRegistrationStatus method, of class Registration.
     */
    @Test
    public void testGetRegistrationStatus() {
        System.out.println("getRegistrationStatus");
        Registration instance = new Registration();
        int expResult = 0;
        int result = instance.getRegistrationStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRegistrationStatus method, of class Registration.
     */
    @Test
    public void testSetRegistrationStatus() {
        System.out.println("setRegistrationStatus");
        int registrationStatus = 0;
        Registration instance = new Registration();
        instance.setRegistrationStatus(registrationStatus);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubjectName method, of class Registration.
     */
    @Test
    public void testGetSubjectName() {
        System.out.println("getSubjectName");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getSubjectName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSubjectName method, of class Registration.
     */
    @Test
    public void testSetSubjectName() {
        System.out.println("setSubjectName");
        String subjectName = "";
        Registration instance = new Registration();
        instance.setSubjectName(subjectName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPackageName method, of class Registration.
     */
    @Test
    public void testGetPackageName() {
        System.out.println("getPackageName");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getPackageName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPackageName method, of class Registration.
     */
    @Test
    public void testSetPackageName() {
        System.out.println("setPackageName");
        String packageName = "";
        Registration instance = new Registration();
        instance.setPackageName(packageName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getStatus method, of class Registration.
     */
    @Test
    public void testGetStatus() {
        System.out.println("getStatus");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.getStatus();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setStatus method, of class Registration.
     */
    @Test
    public void testSetStatus() {
        System.out.println("setStatus");
        String status = "";
        Registration instance = new Registration();
        instance.setStatus(status);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Registration.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Registration instance = new Registration();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

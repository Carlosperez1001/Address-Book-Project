package AddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

  private Person testPerson;
  private Person testPerson2;
  private Person testPerson3;

  /**
   * setUp method executes before each @Test annotated method.
   * Creates a new Person object named testPerson
   */
  @BeforeEach
  void setUp() {
    testPerson = new Person("John","Doe","123 Main St","Fort Myers","FL","33901","239-555-1212");
  }

  /**
   * Type: Unit Test
   * Tests for invalid person object.
   * Person throws IllegalArgumentException when missing firstName, lastName, or address.
   * @throws Exception
   */
  @Test
  void invalidPerson()
  {
    // Invalid First Name
    try{
      Throwable exception = assertThrows(
              IllegalArgumentException.class, () -> {
                testPerson2 = new Person("", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
              }
      );
    }
    catch (Exception exception) {
      System.out.println("Exception caught");
      String expectedMessage = "First name cannot be empty";
      String actualMessage = exception.getMessage();
      assertEquals(expectedMessage, actualMessage);
    }

    // Invalid Last Name
//    assertThrows(IllegalArgumentException.class,() -> { testPerson3 = new Person("John","","123 Main St","Fort Myers","FL","33901","239-555-1212");} );
    // Invalid Address
    //assertThrows(IllegalArgumentException.class,() -> { testPerson3 = new Person("John","Doe","","Fort Myers","FL","33901","239-555-1212");} );

  }

  /**
   * Type: Unit Test
   * Tests getFirstName method,
   * getFirstName must return 'John' from testPerson
   */
  @Test
  void getFirstName() {
    assertEquals("John", testPerson.getFirstName());
  }

  /**
   * Type: Unit Test
   * Tests getLastName method,
   * getLastName must return 'Doe' from testPerson
   */
  @Test
  void getLastName() {
    assertEquals("Doe", testPerson.getLastName());
  }

  /**
   * Type: Unit Test
   * Tests getAddress method,
   * getAddress must return '123 Main St' from testPerson
   */
  @Test
  void getAddress() {
    assertEquals("123 Main St", testPerson.getAddress());
  }

  /**
   * Type: Unit Test
   * Tests getCity method,
   * getCity must return 'Fort Myers' from testPerson
   */
  @Test
  void getCity() {
    assertEquals("Fort Myers", testPerson.getCity());
  }

  /**
   * Type: Unit Test
   * Tests getState method,
   * getState must return 'FL' from testPerson
   * getState must return valid state
   */
  @Test
  void getState() {

      String [] ValidStates = {"AL","AK", "AZ", "AR", "CA",
              "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL",
              "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA",
              "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
              "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR",
              "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT",
              "VA", "WA", "WV", "WI", "WY"};
      Boolean isValidState = Arrays.asList(ValidStates).contains(testPerson.getState());

      assertEquals("FL", testPerson.getState());
      assertTrue(isValidState, testPerson.getState());
  }

  /**
   * Type: Unit Test
   * Tests getZip method,
   * getZip must return '33901' from testPerson
   */
  @Test
  void getZip() {
    assertEquals("33901", testPerson.getZip());
  }

  /**
   * Type: Unit Test
   * Tests getPhone method,
   * getPhone must return '239-555-1212' from testPerson
   */
  @Test
  void getPhone() {
    assertEquals("239-555-1212", testPerson.getPhone());
  }

  /**
   * Type: Unit Test
   * Tests toString method,
   * toString must return 'Doe, John' from testPerson in correct format.
   */
  @Test
  void TestToString() {
    assertEquals("Doe, John", testPerson.toString());
  }

  /**
   * Type: Unit Test
   * Tests containsString method
   * containsString must return True if contains string from testPerson.
   * containsString must return False if testPerson does not contain desired string.
   */
  @Test
  void containsString() {
    assertEquals(true, testPerson.containsString("John"));
    assertEquals(true, testPerson.containsString("Doe"));
    assertEquals(true, testPerson.containsString("123"));
    assertEquals(true, testPerson.containsString("Fort"));
    assertEquals(true, testPerson.containsString("FL"));
    assertEquals(true, testPerson.containsString("33901"));
    assertEquals(true, testPerson.containsString("239"));

    assertEquals(false, testPerson.containsString("Yo"));
  }

  /**
   * Type: Unit Test
   * Tests getField method,
   * getField must return True from testPerson if field exists.
   * getField must throw IllegalArgumentException if field does not exist.
   * @throws Exception
   */
  @Test
  void getField() {
    assertEquals("Doe", testPerson.getField(0));
    assertEquals("John", testPerson.getField(1));
    assertEquals("123 Main St", testPerson.getField(2));
    assertEquals("Fort Myers", testPerson.getField(3));
    assertEquals("FL", testPerson.getField(4));
    assertEquals("33901", testPerson.getField(5));
    assertEquals("239-555-1212", testPerson.getField(6));
    assertThrows(IllegalArgumentException.class, () -> {
      testPerson.getField(7);
    });
  }


}
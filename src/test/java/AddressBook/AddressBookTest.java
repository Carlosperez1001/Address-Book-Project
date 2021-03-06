package AddressBook;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class AddressBookTest {


    private Person testPerson;
    private Person testPerson2;
    private Person tempPerson;
    private AddressBook testAddressBook;


    /**
     * setUp method executes before each @Test annotated method.
     * Creates a new Person object named testPerson
     */
    @BeforeEach
    void setUp() {
        testAddressBook = new AddressBook();
        testPerson = new Person("John","Doe","123 Main St","Fort Myers","FL","33901","239-555-1212");
        testPerson2 = new Person("Mike","Smith","111 Fourth St","Naples","FL","33333","239-123-4567");
    }


    /**
     * Type: Unit Test
     * Tests adding persons to Addressbook,
     * AddressBook.get(index) must return testPerson and testPerson2 objects
     */
    @Test
    void addPersonTest(){

        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        assertEquals(testPerson, testAddressBook.get(0));
        assertEquals(testPerson2, testAddressBook.get(1));
    }

    /**
     * Type: Unit Test
     * Tests removing person from AddressBook,
     * AddressBook.remove(index) should remove person object from AddressBook
     */
    @Test
    void removePersonTest(){

        testPerson = new Person("John","Doe","123 Main St","Fort Myers","FL","33901","239-555-1212");
        testPerson2 = new Person("Mike","Smith","111 Fourth St","Naples","FL","33333","239-123-4567");
        // Add two persons to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        testAddressBook.remove(0);

        Assert.assertEquals(testPerson2, testAddressBook.get(0));
        Assert.assertEquals(1, testAddressBook.getRowCount());
    }
    /**
     * Type: Unit Test
     * Tests removing person from AddressBook,
     * AddressBook.remove(index) should remove person object from AddressBook
     */
    @Test
    void removePersonTestIT(){

        testAddressBook = mock(AddressBook.class);
        testPerson = new Person("John","Doe","123 Main St","Fort Myers","FL","33901","239-555-1212");
        testPerson2 = new Person("Mike","Smith","111 Fourth St","Naples","FL","33333","239-123-4567");
        // Add two persons to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        testAddressBook.remove(0);

        Mockito.when(testAddressBook.get(0)).thenReturn(testPerson2);
        Mockito.verify(testAddressBook).remove(anyInt());
        Mockito.verify(testAddressBook, times(2)).add(any(Person.class));
        Assert.assertEquals(testPerson2, testAddressBook.get(0));
    }

    /**
     * Type: Unit Test
     * Tests updating person in AddressBook,
     * AddressBook.set(index, person) should update person object in AddressBook
     */
    @Test
    void setPersonTest(){
        // Add testPerson to AddressBook
        testAddressBook.add(testPerson);
        // Check that index 0 contains testPerson
        assertEquals(testPerson, testAddressBook.get(0));

        // Replace testPerson with testPerson2
        testAddressBook.set(0, testPerson2);
        // Check that index 0 now contains testPerson2
        assertEquals(testPerson2, testAddressBook.get(0));
    }

    /**
     * Type: Unit Test
     * Tests clearing AddressBook,
     * AddressBook.clear() should remove all person objects from AddressBook
     */
    @Test
    void clearAddressBook(){

        // Clear empty addressbook
        testAddressBook.clear();

        // Add two persons to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);
        // Check that AddressBook contains two persons
        assertEquals(2, testAddressBook.getRowCount());

        // Clear AddressBook
        testAddressBook.clear();
        // Check that AddressBook is empty.
        assertEquals(0, testAddressBook.getRowCount());
    }

    /**
     * Type: Unit Test
     * AddressBook.getColumnCount() should return 7
     */
    @Test
    void getColumnCountTest(){

        testAddressBook.add(testPerson);
        assertEquals(7, testAddressBook.getColumnCount());

    }

    /**
     * Type: Unit Test
     * Tests getValueAt method in AddressBook,
     * AddressBook.getValueat(row, column) should return value from specified row,column
     */
    @Test
    void getValueAt(){

        // Add two person objects to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        // Check that getValueAt returns the correct value
        assertEquals("Fort Myers", testAddressBook.getValueAt(0, 3));
        assertEquals("Mike", testAddressBook.getValueAt(1, 1) );

        // Check that Exception is thrown when index does not exist.
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testAddressBook.getValueAt(2,2);
        });
    }

    /**
     * Type: Unit Test
     * Tests getColumnName method in AddressBook,
     * AddressBook.getColumnName(column) should return name of specified column
     */
    @Test
    void getColumnNameTest(){

        assertEquals("Last Name", testAddressBook.getColumnName(0));
        assertEquals("State", testAddressBook.getColumnName(4));

        assertThrows(IndexOutOfBoundsException.class, () -> {
            testAddressBook.getColumnName(8);
        });
    }

    /**
     * Type: Unit Test
     * Tests getPersons method in AddressBook,
     * AddressBook.getPersons() should return array of person objects in AddressBook
     */
    @Test
    void getPersonsAsArrayTest(){
        // Create new ArrayList and add two person objects
        List<Person> personsList = new ArrayList<>();
        personsList.add(testPerson);
        personsList.add(testPerson2);

        // Add same two person objects to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        // Check that returned array is the same.
        assertArrayEquals(personsList.toArray(), testAddressBook.getPersons());
    }

    /**
     * Type: Unit Test
     * Tests getList method in AddressBook,
     * AddressBook.getList() should return list of person objects in AddressBook
     */
    @Test
    void getPersonsasListTest(){
        // Create new ArrayList and add two person objects
        List<Person> personsList = new ArrayList<>();
        personsList.add(testPerson);
        personsList.add(testPerson2);

        // Add same two person objects to AddressBook
        testAddressBook.add(testPerson);
        testAddressBook.add(testPerson2);

        // Check that returned array is the same.
        assertEquals(personsList, testAddressBook.getList());
    }

}

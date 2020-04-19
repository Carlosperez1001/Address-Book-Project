package AddressBook;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Path;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class AddressBookControllerTest {

    private Person testPerson;
    private Person testPerson2;
    private AddressBook testAddressBook;
    private AddressBookController testAddressBookController;
    private File file;
    private FileSystem fileSystem;
    private Connection connection;

    /**
     * Setup FileSystem for open & save test.
     * Create an AdressBook, Controller, and two Person objects.
     */
    @BeforeEach
    void setUp() {
        fileSystem = new FileSystem();
        testAddressBook = new AddressBook();
        testAddressBookController = new AddressBookController(testAddressBook);
        testPerson = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
        testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");
    }

    /**
     * Type: Unit Test
     * Tests adding persons to AddressBook,
     * testAddressBookController.add(person) should add person object to addressbook.
     */
    @Test
    void addTest() {

        // Add 2 person objects
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // Check that both exist in addressbook
        assertEquals(testPerson, testAddressBookController.get(0));
        assertEquals(testPerson2, testAddressBookController.get(1));
    }

    /**
     * Type: Unit Test
     * Tests updating persons info in addressbook.
     * testAddressBookController.set(index, person) should update person object at
     * specified index.
     */
    @Test
    void setTest() {
        // Add testPerson to index 0 of addressbook
        testAddressBookController.add(testPerson);
        assertEquals(testPerson, testAddressBookController.get(0));

        // Update index 0 with testPerson2
        testAddressBookController.set(0, testPerson2);
        // Check testPerson2 is now at index 0
        assertEquals(testPerson2, testAddressBookController.get(0));
    }

    /**
     * Type: Unit Test
     * Tests removing person from AddressBook,
     * testAdressBookController.remove(index) should remove the person from addressbook.
     */
    @Test
    void removeTest() {

        // Add two person objects to addressbook
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // testPerson is at index 0
        assertEquals(testPerson, testAddressBookController.get(0));

        // Remove testPerson from index 0
        testAddressBookController.remove(0);
        // Check testPerson2 is now at index 0
        assertEquals(testPerson2, testAddressBookController.get(0));
    }

    /**
     * Type: Unit Test
     * Tests returning person object from addressbook.
     * testAddressBookController.get(index) should return person object.
     */
    @Test
    void getTest() {

        // Add two person objects to addressbook
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // Check that get(index) returns both person objects
        assertEquals(testPerson, testAddressBookController.get(0));
        assertEquals(testPerson2, testAddressBookController.get(1));
    }

    /**
     * Type: Unit Test
     * Tests clearing persons from AddressBook,
     * testAddressBookController.clear() should clear all persons from addressbook.
     */
    @Test
    void clearTest() {
        // Add two person objects to addressbook
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // Check that get(index) returns both person objects
        assertEquals(testPerson, testAddressBookController.get(0));
        assertEquals(testPerson2, testAddressBookController.get(1));

        // Clear all persons from addressbook
        testAddressBookController.clear();

        // Check that addressbook is empty by checking index 0, should throw exception.
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testAddressBookController.get(0);
        });
    }

    @Test
    void getModelTest() {
        assertEquals(testAddressBook, testAddressBookController.getModel());
    }

    /**
     * Type: Unit Test
     * Should open test address book file & then save it.
     * testAddressBookController.open(file) should open the testdata.
     * testAddressBookController.save(file) should save the testdata.
     */
    @Test
    void openAndSaveTest() throws IOException, SQLException {

        // Create testDB File
        try {
            file = file.createTempFile("testdata",".db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS persons");
            statement.execute("CREATE TABLE persons (firstName TEXT, lastName TEXT, address TEXT, city TEXT, state TEXT, zip TEXT, phone TEXT)");
            // Insert the data into the database
            PreparedStatement insert = connection.prepareStatement("INSERT INTO persons (lastName, firstName, address, city, state, zip, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (Person p : testAddressBook.getPersons()) {
                for (int i = 0; i < Person.fields.length; i++) {
                    insert.setString(i + 1, p.getField(i));
                }
                insert.executeUpdate();
            }
            connection.close();

        }
        catch (Exception e)
        {
            System.out.println("Exception caught. Setup failed.");
        }

        testAddressBookController.open(file);
        testAddressBookController.addressBook.fireTableDataChanged();
        testAddressBookController.save(file);
    }

}

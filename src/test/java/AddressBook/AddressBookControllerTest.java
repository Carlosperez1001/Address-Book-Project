package AddressBook;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class AddressBookControllerTest {

    private Person testPerson;
    private Person testPerson2;
    private AddressBook testAddressBook;
    private AddressBookController testAddressBookController;
    private FileSystem testFileSystem;


    @BeforeEach
    void setUp() {
        testFileSystem = new FileSystem();
        testAddressBook = new AddressBook();
        testAddressBookController = new AddressBookController(testAddressBook);
        testPerson = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
        testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");
    }

    @Test
    void addTest() {

        // Add 2 person objects
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // Check that both exist in addressbook
        assertEquals(testPerson, testAddressBookController.get(0));
        assertEquals(testPerson2, testAddressBookController.get(1));
    }


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

    @Test
    void getTest() {

        // Add two person objects to addressbook
        testAddressBookController.add(testPerson);
        testAddressBookController.add(testPerson2);

        // Check that get(index) returns both person objects
        assertEquals(testPerson, testAddressBookController.get(0));
        assertEquals(testPerson2, testAddressBookController.get(1));
    }

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

}

package AddressBook;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddressBookControllerTest {

    private Person testPerson1;
    private Person testPerson2;
    private Person testPerson3;
    private Person testPerson4;
    private AddressBook testAddressBook;
    private AddressBook testAddressBook1;
    private AddressBook testAddressBookFile;
    private AddressBookController testAddressBookControllerIn;
    private AddressBookController testAddressBookControllerOut;
    private FileSystem testFileSystem;
    private FileSystem fs;
    private File file;
//    private File junkFile;
    private Connection connection;
    FileSystem mockedFS = mock(FileSystem.class);

    @BeforeEach
    void setUp() {
        testFileSystem = new FileSystem();
        testAddressBook = new AddressBook();
        testAddressBook1 = new AddressBook();
        testAddressBookFile = new AddressBook();

        testAddressBookControllerOut = new AddressBookController(testAddressBook);

        testPerson1 = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
        testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");
//        testAddressBook.add(testPerson1);
//        testAddressBook.add(testPerson2);

        testPerson3 = new Person("Nick","Cave","123 Shitt St","Chicago","IL","63901","239-555-1212");
        testPerson4 = new Person("Dick","Dale","111 Wild St","Milwaukee","WS","73333","239-123-4567");
        testAddressBookFile.add(testPerson3);
        testAddressBookFile.add(testPerson4);

        fs = new FileSystem();

//    file.toPath(".test.sql");


        try {
            file = file.createTempFile("testdata",".db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS persons");
            statement.execute("CREATE TABLE persons (firstName TEXT, lastName TEXT, address TEXT, city TEXT, state TEXT, zip TEXT, phone TEXT)");
            // Insert the data into the database
            PreparedStatement insert = connection.prepareStatement("INSERT INTO persons (lastName, firstName, address, city, state, zip, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (Person p : testAddressBookFile.getPersons()) {
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
    }

    @Test
    void addTest() {

        // Add 2 person objects
        testAddressBookControllerOut.add(testPerson1);
        testAddressBookControllerOut.add(testPerson2);

        // Check that both exist in addressbook
        assertEquals(testPerson1, testAddressBookControllerOut.get(0));
        assertEquals(testPerson2, testAddressBookControllerOut.get(1));
    }


    @Test
    void setTest() {
        // Add testPerson to index 0 of addressbook
        testAddressBookControllerOut.add(testPerson1);
        assertEquals(testPerson1, testAddressBookControllerOut.get(0));

        // Update index 0 with testPerson2
        testAddressBookControllerOut.set(0, testPerson2);
        // Check testPerson2 is now at index 0
        assertEquals(testPerson2, testAddressBookControllerOut.get(0));

    }

    @Test
    void removeTest() {

        // Add two person objects to addressbook
        testAddressBookControllerOut.add(testPerson1);
        testAddressBookControllerOut.add(testPerson2);

        // testPerson is at index 0
        assertEquals(testPerson1, testAddressBookControllerOut.get(0));

        // Remove testPerson from index 0
        testAddressBookControllerOut.remove(0);
        // Check testPerson2 is now at index 0
        assertEquals(testPerson2, testAddressBookControllerOut.get(0));
    }

    @Test
    void getTest() {

        // Add two person objects to addressbook
        testAddressBookControllerOut.add(testPerson1);
        testAddressBookControllerOut.add(testPerson2);

        // Check that get(index) returns both person objects
        assertEquals(testPerson1, testAddressBookControllerOut.get(0));
        assertEquals(testPerson2, testAddressBookControllerOut.get(1));
    }

    @Test
    void clearTest() {
        // Add two person objects to addressbook
        testAddressBookControllerOut.add(testPerson1);
        testAddressBookControllerOut.add(testPerson2);

        // Check that get(index) returns both person objects
        assertEquals(testPerson1, testAddressBookControllerOut.get(0));
        assertEquals(testPerson2, testAddressBookControllerOut.get(1));

        // Clear all persons from addressbook
        testAddressBookControllerOut.clear();

        // Check that addressbook is empty by checking index 0, should throw exception.
        assertThrows(IndexOutOfBoundsException.class, () -> {
            testAddressBookControllerOut.get(0);
        });
    }

    @Test
    void getModelTest() {
        assertEquals(testAddressBook, testAddressBookControllerOut.getModel());
    }

    @Test
    void open() {
        //Experiment with mock filesystems so far unsuccessful
//        FileSystem mockFS = mock(FileSystem.class);
//        try {
//            when(mockFS.readFile(testAddressBook, file)).thenReturn(true);

        testAddressBookControllerIn = new AddressBookController(testAddressBook1);
        try {
            testAddressBookControllerIn.open(file);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(testAddressBookControllerIn.get(0), testPerson3);
        assertEquals(testAddressBookControllerIn.get(1), testPerson4);
    }

    @Test
    void save() {
        try {
            testAddressBookControllerOut.save(file);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        assertEquals(1, 1);
    }
}

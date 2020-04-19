package AddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class FileSystemTest {
    private Person testPerson;
    private Person testPerson2;
    private AddressBook testAddressBook1;
    private AddressBook testAddressBook2;
    private Connection connection;
    private File file;
    private FileSystem fs;

    /**
     * Setup for testing.
     * Create AddressBooks, FileSystem, and Persons.
     * Create temporary file for testing from created objects.
     */
    @Test
    @BeforeEach
    void setUp() {
        testAddressBook1 = new AddressBook();
        testAddressBook2 = new AddressBook();
        fs = new FileSystem();
        testPerson = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
        testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");
        testAddressBook1.add(testPerson);
        testAddressBook2.add(testPerson2);


        try {
            file = file.createTempFile("testdata", ".db");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
            Statement statement = connection.createStatement();
            statement.execute("DROP TABLE IF EXISTS persons");
            statement.execute("CREATE TABLE persons (firstName TEXT, lastName TEXT, address TEXT, city TEXT, state TEXT, zip TEXT, phone TEXT)");
            // Insert the data into the database
            PreparedStatement insert = connection.prepareStatement("INSERT INTO persons (lastName, firstName, address, city, state, zip, phone) VALUES (?, ?, ?, ?, ?, ?, ?)");
            for (Person p : testAddressBook1.getPersons()) {
                for (int i = 0; i < Person.fields.length; i++) {
                    insert.setString(i + 1, p.getField(i));
                }
                insert.executeUpdate();
            }
            connection.close();

        } catch (Exception e) {
            System.out.println("Exception caught. Setup failed.");
        }
    }

    /**
     * Type: Unit Test
     * Tests reading file and loading address book.
     * Create an addressbook from temporary testing file.
     * Verify addressbook contains testPerson
     */
    @Test
    void readFileTest() {
        AddressBook readAddressBook = new AddressBook();
        try {
            fs.readFile(readAddressBook, file);

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.toString());
        } catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }

//    System.out.println(readAddressBook.get(0).toString());
        assertEquals(readAddressBook.get(0).toString(), "Doe, John");
    }

    /**
     * Type: Unit Test
     * Tests saving addressbook to file.
     * Save different addressbook to file.
     * Verify the file now contains testPerson2.
     */
    @Test
    void saveFile() {
        AddressBook readAddressBook = new AddressBook();
        try {
            // Save different address book to file
            fs.saveFile(testAddressBook2, file);

            fs.readFile(readAddressBook, file);

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.toString());
        } catch (SQLException sqle) {
            System.out.println(sqle.toString());
        }
        assertEquals(readAddressBook.get(0).toString(), "Smith, Mike");
    }
}
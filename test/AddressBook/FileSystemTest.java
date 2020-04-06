package AddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

  @BeforeEach
  void setUp() {
    testAddressBook1 = new AddressBook();
    testAddressBook2 = new AddressBook();
    fs = new FileSystem();
    testPerson = new Person("John","Doe","123 Main St","Fort Myers","FL","33901","239-555-1212");
    testPerson2 = new Person("Mike","Smith","111 Fourth St","Naples","FL","33333","239-123-4567");
    testAddressBook1.add(testPerson);
    testAddressBook2.add(testPerson2);
//    file.toPath(".test.sql");


    try {
      file = file.createTempFile("testdata",".db");
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

    }
    catch (Exception e)
    {
      System.out.println("Exception caught. Setup failed.");
    }
  }

  @AfterEach
  void tearDown() {
    file.deleteOnExit();
  }

  @Test
  void readFileTest () {
    AddressBook readAddressBook = new AddressBook();
    try {
      fs.readFile(readAddressBook, file);

    }
    catch (FileNotFoundException fnfe) {
      System.out.println(fnfe.toString());
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }

//    System.out.println(readAddressBook.get(0).toString());
    assertEquals(readAddressBook.get(0).toString(), "Doe, John");
  }

  @Test
  void saveFile() {
    AddressBook readAddressBook = new AddressBook();
    try {
      // Save different address book to file
      fs.saveFile(testAddressBook2, file);

      // The only way to verify result is to read it??
      fs.readFile(readAddressBook, file);

    }
    catch (FileNotFoundException fnfe) {
      System.out.println(fnfe.toString());
    }
    catch (SQLException sqle) {
      System.out.println(sqle.toString());
    }

//    System.out.println(readAddressBook.get(0).toString());
    assertEquals(readAddressBook.get(0).toString(), "Smith, Mike");
  }
}
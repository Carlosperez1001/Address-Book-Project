package AddressBook;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;


public class AddressBookIntegrationTest {

    @Mock
    private Person testPerson;

    @Mock
    private Person testPerson2;

    @Mock
    private List<Person> testList;

    @Spy
    @InjectMocks
    private AddressBook testAddressBook;

    @Spy
    @InjectMocks
    private AddressBookController testAddressBookController;



    @BeforeEach
    void setUp() {
        testAddressBook = new AddressBook();
        testAddressBookController = new AddressBookController(testAddressBook);
        testList = new ArrayList<>();
        testPerson = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
        testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");
        testList.add(testPerson);
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Type: Integration Test
     * Tests adding person to mock addressbook.
     * Verify Addressbook.add(Person), Person.add(Person),
     * and AddressBook.fireTableRowsInserted(int, int) execute.
     */
    @Test
    void addTest() {
        testAddressBook.add(testPerson);

        List<Person> persons = testAddressBook.getList();

        assertEquals(testList, persons);
        Mockito.verify(testAddressBook).add(any(Person.class));
        Mockito.verify(persons).add(any(Person.class));
        Mockito.verify(testAddressBook).fireTableRowsInserted(anyInt(), anyInt());
    }

    /**
     * Type: Integration Test
     * Tests updating person in mock addressbook.
     * Verify Addressbook.set(int, Person), Person.set(int, Person),
     * and AddressBook.fireTableRowsUpdated(int, int) execute.
     */
    @Test
    void setTest() {
        testAddressBook.add(testPerson);
        testAddressBook.set(0, testPerson2);
        List<Person> persons = testAddressBook.getList();

        Mockito.verify(testAddressBook).set(anyInt(), any(Person.class));
        Mockito.verify(persons).set(anyInt(), any(Person.class));
        Mockito.verify(testAddressBook).fireTableRowsUpdated(anyInt(), anyInt());
    }

    /**
     * Type: Integration Test
     * Tests removing person from mock addressbook.
     * Verify Addressbook.remove(int),  Person.remove(int),
     * and AddressBook.fireTableRowsDeleted(int, int) execute.
     */
    @Test
    void removeTest() {
        testAddressBook.add(testPerson);
        testAddressBook.remove(0);
        List<Person> persons = testAddressBook.getList();

        Mockito.verify(testAddressBook).remove(anyInt());
        Mockito.verify(persons).remove(anyInt());
        Mockito.verify(testAddressBook).fireTableRowsDeleted(anyInt(), anyInt());

    }

    /**
     * Type: Integration Test
     * Tests getting person from mock addressbook.
     * Verify Addressbook.get(int) returns testPerson object & executes.
     */
    @Test
    void getTest() {
        testAddressBook.add(testPerson);
        Mockito.when(testAddressBook.get(anyInt())).thenReturn(testPerson);
        Mockito.verify(testAddressBook).get(anyInt());
    }

    /**
     * Type: Integration Test
     * Tests clearing persons from mock addressbook.
     * Verify Addressbook.clear() and Person.size() execute.
     */
    @Test
    void clearEmptyTest() {
        testAddressBook.clear();
        List<Person> persons = testAddressBook.getList();

        Mockito.verify(testAddressBook).clear();
        Mockito.verify(persons).size();
    }
}

package AddressBook;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.security.NoExitSecurityManagerInstaller;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.*;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import static org.junit.jupiter.api.Assertions.*;


public class MiscIT {

  private Person testPerson;
  private Person testPerson2;
  private AddressBook testAddressBook;
  private AddressBookController testAddressBookController;
  private FileSystem testFileSystem;
  public AddressBookGUI frame;
  private static FrameFixture window = null;

  @BeforeAll
  public static void init() {
    //For some reason, this stops the
//    NoExitSecurityManagerInstaller.installNoExitSecurityManager();

    // Required for full AssertJ GUI testing
    FailOnThreadViolationRepaintManager.install();
  }

  @BeforeEach
  public void initEach() {

    testAddressBook = new AddressBook();
    testAddressBookController = new AddressBookController(testAddressBook);
    testPerson = new Person("John", "Doe", "123 Main St", "Fort Myers", "FL", "33901", "239-555-1212");
    testPerson2 = new Person("Mike", "Smith", "111 Fourth St", "Naples", "FL", "33333", "239-123-4567");

    // Initialize window
    AddressBookGUI frame = GuiActionRunner.execute(() -> new AddressBookGUI(testAddressBookController, testAddressBook));
    window = new FrameFixture(frame);
    window.show();
  }

  @AfterEach
  public void cleanEach() {
    // Close assertJ window gui
    window.cleanUp();
  }

  @AfterAll
  public static void clean() {
    //Re-enable program to close after testing completes
//    NoExitSecurityManagerInstaller.installNoExitSecurityManager().uninstall();
  }



  @Test
  public void windowShouldOpen() {
    window.requireVisible();
    window.cleanUp();
  }

  @Test
  public void shouldOpenAddDialog() {
    window.button(withText("Add...")).click();
    DialogFixture dialog = window.dialog();
    dialog.requireVisible();
    window.button(withText("OK")).click();
//        dialog.button(JButtonMatcher.withText("OK")).click();

//        dialog.textBox("First Name").enterText("Wyatt");
////        dialog.close();
  }

  @After
  public void tearDown() {
    window.cleanUp();
  }



  @Test
  void dummyTest() {
    String testValue = "one";
    assertEquals(testValue, "one");
  }
}

package AddressBook;

import org.assertj.swing.core.*;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.*;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import org.assertj.swing.fixture.FrameFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.assertj.swing.data.TableCell.row;


public class AddressBookGUITest {

    private Robot robot;
    private FrameFixture window;
    private AddressBook testAddressBook;
    private AddressBookController testAddressBookController;

    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    @BeforeAll
    public static void init() {
//        NoExitSecurityManagerInstaller.installNoExitSecurityManager();
        FailOnThreadViolationRepaintManager.install();
    }

    @Before
    public void setUp(){

        testAddressBook = new AddressBook();

        testAddressBookController = new AddressBookController(testAddressBook);
        AddressBookGUI frame = GuiActionRunner.execute(() ->
                new AddressBookGUI(testAddressBookController, testAddressBook));
        window = new FrameFixture(frame);
        window.show();
    }

    @Test
    public void windowShouldOpen() {
        window.requireVisible();
    }

    @Test
    public void createNewAddressBook() {
        window.menuItemWithPath("File", "New").click();
    }

    @Test
    public void shouldSave() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("Mike");
        dialog.textBox("lastName_field").enterText("Michaels");
        window.button(withText("OK")).click();
        window.menuItemWithPath("File", "Save").click();
        JFileChooserFixture saveDialog = window.fileChooser("save_jfc");
        saveDialog.fileNameTextBox().enterText("test_db");
        window.button(withText("Save")).click();
    }

    @Test
    public void shouldOpenFile() {
        window.menuItemWithPath("File", "Open").click();
        JFileChooserFixture openDialog = window.fileChooser("open_jfc");
        openDialog.fileNameTextBox().enterText("test_db");
        window.button(withText("Open")).click();
    }
    @Test
    public void createNewAddressBookAfterInput() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("J");
        dialog.textBox("lastName_field").enterText("S");
        window.button(withText("OK")).click();

        window.menuItemWithPath("File", "New").click();
        window.button(withText("Yes")).click();
    }

    @Test
    public void shouldOpenFileError() {
        window.menuItemWithPath("File", "Open").click();
        JFileChooserFixture openDialog = window.fileChooser("open_jfc");
        openDialog.fileNameTextBox().enterText("fakefile");
        window.button(withText("Open")).click();
        window.button(withText("OK")).click();
    }

    @Test
    public void shouldAddPerson() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("John");
        dialog.textBox("lastName_field").enterText("Smith");
        dialog.textBox("address_field").enterText("Main St");
        dialog.textBox("city_field").enterText("Naples");
        dialog.textBox("state_field").enterText("FL");
        dialog.textBox("zip_field").enterText("33333");
        dialog.textBox("phone_field").enterText("123-456-1122");
        window.button(withText("OK")).click();
    }

    @Test
    public void shouldEditPerson() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("Mike");
        dialog.textBox("lastName_field").enterText("Smith");
        window.button(withText("OK")).click();
        JTableFixture table = window.table("name_table");
        table.click(row(0).column(0), MouseButton.LEFT_BUTTON);
        window.button(withText("Edit...")).click();
        dialog = window.dialog();
        dialog.textBox("lastName_field").enterText("s");
        dialog.textBox("address_field").enterText("123 Main St");
        window.button("pd_ok_button").click();
    }

    @Test
    public void shouldDeletePerson() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("Joe");
        dialog.textBox("lastName_field").enterText("Johnson");
        window.button(withText("OK")).click();
        JTableFixture table = window.table("name_table");
        table.click(row(0).column(0), MouseButton.LEFT_BUTTON);
        window.button(withText("Delete")).click();
    }



    @Test
    public void exitWithoutSaving() {
        window.button(withText("Add...")).click();
        DialogFixture dialog = window.dialog();
        dialog.requireVisible();
        dialog.textBox("firstName_field").enterText("John");
        dialog.textBox("lastName_field").enterText("Smith");
        window.button(withText("OK")).click();
        window.menuItemWithPath("File", "Exit").click();
        window.button(withText("No")).click();
    }

    @After
    public void tearDown() {
        window.cleanUp();
    }
    // TODO

    @AfterAll
    public void afterAll() {
//        NoExitSecurityManagerInstaller.installNoExitSecurityManager().uninstall();
    }
    // TODO
}


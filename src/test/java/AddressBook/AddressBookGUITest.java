package AddressBook;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.DialogFinder;
import org.assertj.swing.fixture.FrameFixture;

import static java.awt.event.KeyEvent.*;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import static org.assertj.swing.core.matcher.JButtonMatcher.withText;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.BeforeClass;
import org.junit.Test;
import javax.swing.JFrame;

import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.Robot;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.finder.WindowFinder;
import org.assertj.swing.fixture.DialogFixture;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.security.NoExitSecurityManagerInstaller;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import static org.junit.jupiter.api.Assertions.*;



public class AddressBookGUITest {

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
    // TODO

    @AfterAll
    public void afterAll() {
//        NoExitSecurityManagerInstaller.installNoExitSecurityManager().uninstall();
    }
    // TODO
}


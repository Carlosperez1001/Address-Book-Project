package AddressBook;

import AddressBook.Person;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;


public class PersonDialog extends JDialog {
    public enum Result {
        OK,
        CANCEL,
    }

    private Result result;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField address;
    private JTextField city;
    private JTextField state;
    private JTextField zip;
    private JTextField phone;

  
    public PersonDialog(Frame parent) {
        
        super(parent);

        
        JLabel l;
        AtomicReference<JPanel> p = new AtomicReference<>(new JPanel(new SpringLayout()));

        l = new JLabel("First name:", JLabel.TRAILING);
        p.get().add(l);
        firstName = new JTextField(20);
        firstName.setName("firstName_field");
        l.setLabelFor(firstName);
        p.get().add(firstName);

      
        l = new JLabel("Last name:", JLabel.TRAILING);
        p.get().add(l);
        lastName = new JTextField(20);
        lastName.setName("lastName_field");
        l.setLabelFor(lastName);
        p.get().add(lastName);

  
        l = new JLabel("Address:", JLabel.TRAILING);
        p.get().add(l);
        address = new JTextField(20);
        address.setName("address_field");
        l.setLabelFor(address);
        p.get().add(address);

     
        l = new JLabel("City:", JLabel.TRAILING);
        p.get().add(l);
        city = new JTextField(20);
        city.setName("city_field");
        l.setLabelFor(city);
        p.get().add(city);

       
        l = new JLabel("State:", JLabel.TRAILING);
        p.get().add(l);
        state = new JTextField(20);
        state.setName("state_field");
        l.setLabelFor(state);
        p.get().add(state);

        l = new JLabel("ZIP code:", JLabel.TRAILING);
        p.get().add(l);
        zip = new JTextField(20);
        zip.setName("zip_field");
        l.setLabelFor(zip);
        p.get().add(zip);

       
        l = new JLabel("Telephone:", JLabel.TRAILING);
        p.get().add(l);
        phone = new JTextField(20);
        phone.setName("phone_field");
        l.setLabelFor(phone);
        p.get().add(phone);

        
        SpringUtilities.makeCompactGrid(p.get(), 7, 2, 6, 6, 6, 6);

        // Set up the buttons
        JPanel buttons = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.setName("pd_ok_button");
        okButton.setMnemonic('O');
        okButton.addActionListener(e ->
        {
            // Check ZIP Input
            if(firstName.getText().isEmpty() || lastName.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "First & last name required. Can not be empty.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Check ZIP Input
            if(!zip.getText().matches(("[0-9]{5}")) && !zip.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Zip code invalid, must contain 5 digits.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Check phone input, numbers only
            if(!phone.getText().matches("[0-9]+") && !phone.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Phone number invalid, can not contain letters.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            // Check state input
            if(state.getText().matches("[A-Z_]{2}") && !state.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "State must be 2 character code.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            result = Result.OK;
            setVisible(false);
        });
        buttons.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setMnemonic('C');
        cancelButton.addActionListener(e ->
        {
            result = Result.CANCEL;
            setVisible(false);
        });
        buttons.add(cancelButton);

        // Set window properties
        getContentPane().add(p.get(), BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.PAGE_END);
        pack();
        setTitle("Person Information");
        setModalityType(ModalityType.DOCUMENT_MODAL);
        setLocation((parent.getWidth() - getWidth()) / 2, (parent.getHeight() - getHeight()) / 2);
    }

  
    public PersonDialog(Frame parent, Person person) {
        this(parent);
        if (person == null)
            return;
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        address.setText(person.getAddress());
        city.setText(person.getCity());
        state.setText(person.getState());
        zip.setText(person.getZip());
        phone.setText(person.getPhone());
    }

    
    public Result showDialog() {
        // Default to CANCEL if the user closes the dialog window
        result = Result.CANCEL;
        setVisible(true);
        return result;
    }

 
    public Person getPerson() {
        if (firstName != null && lastName != null && !firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
            return new Person(firstName.getText(),
                    lastName.getText(),
                    address.getText(),
                    city.getText(),
                    state.getText(),
                    zip.getText(),
                    phone.getText());
        } else {
            return null;
        }
    }
}
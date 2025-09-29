import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class Registration extends JFrame {
    private JTextField txtName, txtEmail, txtContact;
    private JComboBox<String> cbCourse, cbYear, cbBarangay, cbCity, cbCountry;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JButton btnRegister, btnClear, btnExit;

    public Registration() {
        setTitle("Registration Form");
        setSize(420, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Registration Form"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        txtName = new JTextField(20);
        panel.add(txtName, gbc);

        // Row 1 - Course
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        cbCourse = new JComboBox<>(new String[] { "BSCS-SE", "BSIT", "BSIS" });
        panel.add(cbCourse, gbc);

        // Row 2 - Year
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        cbYear = new JComboBox<>(new String[] { "1", "2", "3", "4" });
        panel.add(cbYear, gbc);

        // Row 2 - Gender
        gbc.gridx = 2;
        gbc.gridy = 2;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 2;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        panel.add(genderPanel, gbc);

        // Row 3 - Barangay
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Barangay:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        cbBarangay = new JComboBox<>(new String[] { "Brgy 1", "Brgy 2", "Brgy 3" });
        panel.add(cbBarangay, gbc);

        // Row 4 - City
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        cbCity = new JComboBox<>(new String[] { "Quezon City", "Manila", "Cebu" });
        panel.add(cbCity, gbc);

        // Row 5 - Country
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        cbCountry = new JComboBox<>(new String[] { "Philippines", "Singapore", "USA" });
        panel.add(cbCountry, gbc);

        // Row 6 - Email
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);

        // Row 7 - Contact
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        txtContact = new JTextField(20);
        panel.add(txtContact, gbc);

        // Row 8 - Buttons
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnRegister = new JButton("REGISTER");
        btnClear = new JButton("CLEAR");
        btnExit = new JButton("EXIT");
        btnPanel.add(btnRegister);
        btnPanel.add(btnClear);
        btnPanel.add(btnExit);
        panel.add(btnPanel, gbc);

        // Button Listeners
        btnRegister.addActionListener(e -> register());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> System.exit(0));

        add(panel);
        setVisible(true);
    }

    private void register() {
        String name = txtName.getText().trim();
        String email = txtEmail.getText().trim();
        String contact = txtContact.getText().trim();
        String gender = rbMale.isSelected() ? "Male" : rbFemale.isSelected() ? "Female" : "";

        if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || gender.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!contact.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(this, "Contact number must be 11 digits!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter("registrations.txt", true))) {
            out.println(name + "," + cbCourse.getSelectedItem() + "," + cbYear.getSelectedItem() + "," +
                    gender + "," + cbBarangay.getSelectedItem() + "," + cbCity.getSelectedItem() +
                    "," + cbCountry.getSelectedItem() + "," + email + "," + contact);
            JOptionPane.showMessageDialog(this, "Registration successful!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtContact.setText("");
        cbCourse.setSelectedIndex(0);
        cbYear.setSelectedIndex(0);
        cbBarangay.setSelectedIndex(0);
        cbCity.setSelectedIndex(0);
        cbCountry.setSelectedIndex(0);
        genderGroup.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registration::new);
    }
}

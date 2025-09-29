import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Registration extends JFrame {
    private JTextField txtName, txtEmail, txtContact;
    private JComboBox<String> cbCourse, cbYear, cbCountry, cbCity, cbBarangay;
    private JRadioButton rbMale, rbFemale;
    private ButtonGroup genderGroup;
    private JButton btnRegister, btnClear, btnExit;

    // Location data (Country → Cities → Barangays)
    private final Map<String, Map<String, String[]>> locationData = new HashMap<>();

    public Registration() {
        setTitle("FEU Registration Form");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🟢 Sample Location Data
        Map<String, String[]> phCities = new HashMap<>();
        phCities.put("Quezon City", new String[] { "Brgy 1", "Brgy 2", "Brgy 3" });
        phCities.put("Manila", new String[] { "Ermita", "Malate", "Tondo" });
        phCities.put("Cebu", new String[] { "Lahug", "Mabolo", "Talamban" });

        Map<String, String[]> sgCities = new HashMap<>();
        sgCities.put("Central", new String[] { "Raffles", "Clarke Quay" });
        sgCities.put("North", new String[] { "Yishun", "Woodlands" });

        Map<String, String[]> usCities = new HashMap<>();
        usCities.put("New York", new String[] { "Brooklyn", "Manhattan" });
        usCities.put("California", new String[] { "Los Angeles", "San Francisco" });

        locationData.put("Philippines", phCities);
        locationData.put("Singapore", sgCities);
        locationData.put("USA", usCities);

        // 🌟 Main Container
        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);

        // 🌟 Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 51));
        JLabel lblHeader = new JLabel("FEU Registration Form", JLabel.CENTER);
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);
        headerPanel.add(lblHeader);

        // 🌟 Form Panel
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0 - Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        txtName = createTextField();
        panel.add(txtName, gbc);

        // Row 1 - Course
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Course:"), gbc);
        gbc.gridx = 1;
        cbCourse = new JComboBox<>(new String[] { "BSCS-SE", "BSIT", "BSIS" });
        styleCombo(cbCourse);
        panel.add(cbCourse, gbc);

        // Row 2 - Year
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        cbYear = new JComboBox<>(new String[] { "1", "2", "3", "4" });
        styleCombo(cbYear);
        panel.add(cbYear, gbc);

        // Row 3 - Gender
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.setBackground(Color.WHITE);
        rbMale = new JRadioButton("Male");
        rbFemale = new JRadioButton("Female");
        rbMale.setBackground(Color.WHITE);
        rbFemale.setBackground(Color.WHITE);
        genderGroup = new ButtonGroup();
        genderGroup.add(rbMale);
        genderGroup.add(rbFemale);
        genderPanel.add(rbMale);
        genderPanel.add(rbFemale);
        panel.add(genderPanel, gbc);

        // Row 4 - Country
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx = 1;
        cbCountry = new JComboBox<>(locationData.keySet().toArray(new String[0]));
        styleCombo(cbCountry);
        panel.add(cbCountry, gbc);

        // Row 5 - City
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cbCity = new JComboBox<>();
        styleCombo(cbCity);
        panel.add(cbCity, gbc);

        // Row 6 - Barangay
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Barangay:"), gbc);
        gbc.gridx = 1;
        cbBarangay = new JComboBox<>();
        styleCombo(cbBarangay);
        panel.add(cbBarangay, gbc);

        // Row 7 - Email
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        txtEmail = createTextField();
        panel.add(txtEmail, gbc);

        // Row 8 - Contact
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx = 1;
        txtContact = createTextField();
        panel.add(txtContact, gbc);

        // 🌟 Buttons
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(Color.WHITE);

        btnRegister = createButton("REGISTER", new Color(0, 153, 51), Color.WHITE);
        btnClear = createButton("CLEAR", new Color(255, 204, 0), Color.BLACK);
        btnExit = createButton("EXIT", new Color(204, 0, 0), Color.WHITE);

        btnPanel.add(btnRegister);
        btnPanel.add(btnClear);
        btnPanel.add(btnExit);
        panel.add(btnPanel, gbc);

        // Add Panels
        container.add(headerPanel, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);
        add(container);

        // 🌟 Dynamic Location Logic
        cbCountry.addActionListener(e -> updateCities());
        cbCity.addActionListener(e -> updateBarangays());

        // Button Listeners
        btnRegister.addActionListener(e -> register());
        btnClear.addActionListener(e -> clearForm());
        btnExit.addActionListener(e -> System.exit(0));

        // Initialize default values
        updateCities();

        setVisible(true);
    }

    private void updateCities() {
        cbCity.removeAllItems();
        cbBarangay.removeAllItems();
        String country = (String) cbCountry.getSelectedItem();
        if (country != null && locationData.containsKey(country)) {
            for (String city : locationData.get(country).keySet()) {
                cbCity.addItem(city);
            }
        }
        updateBarangays();
    }

    private void updateBarangays() {
        cbBarangay.removeAllItems();
        String country = (String) cbCountry.getSelectedItem();
        String city = (String) cbCity.getSelectedItem();
        if (country != null && city != null && locationData.containsKey(country)) {
            String[] brgys = locationData.get(country).get(city);
            if (brgys != null) {
                for (String brgy : brgys) {
                    cbBarangay.addItem(brgy);
                }
            }
        }
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBorder(new LineBorder(new Color(0, 102, 51), 2, true));
        return field;
    }

    private void styleCombo(JComboBox<String> combo) {
        combo.setBackground(Color.WHITE);
        combo.setBorder(new LineBorder(new Color(0, 102, 51), 2, true));
    }

    private JButton createButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 25, 10, 25));
        return button;
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
            JOptionPane.showMessageDialog(this, "✅ Registration successful!");
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
        cbCountry.setSelectedIndex(0);
        genderGroup.clearSelection();
        updateCities();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Registration::new);
    }
}

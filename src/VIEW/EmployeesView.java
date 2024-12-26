package VIEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import Model.Poste;
import Model.Role;
import java.awt.*;

public class EmployeesView extends JPanel {
    private JPanel formPanel, buttonPanel;
    private JTextField firstName, lastName, phone, email, salary;
    private JComboBox<Role> roles;
    private JComboBox<Poste> postes;
    private JTable table;
    private DefaultTableModel model;
    private JScrollPane scrollPane;
    private JButton addButton, updateButton, deleteButton, displayButton;

    public EmployeesView() {
        initializeComponents();
        assembleComponents();
        setVisible(false);
    }

    private void initializeComponents() {
        this.setLayout(new BorderLayout());
        formPanel = UIUtils.createPanel(new GridLayout(7, 2, 10, 10), 10);
        buttonPanel = UIUtils.createPanel(new FlowLayout(FlowLayout.CENTER, 10, 10), 0);

        firstName = UIUtils.createTextField(10);
        lastName = UIUtils.createTextField(10);
        phone = UIUtils.createTextField(10);
        email = UIUtils.createTextField(10);
        salary = UIUtils.createTextField(10);

        roles = new JComboBox<>(Role.values());
        postes = new JComboBox<>(Poste.values());

        String[] columnNames = {"ID", "Nom", "Prenom", "Telephone", "Email", "Salaire", "Role", "Poste"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setDefaultEditor(Object.class, null);
        scrollPane = new JScrollPane(table);

        addButton = UIUtils.createButton("ADD");
        updateButton = UIUtils.createButton("UPDATE");
        deleteButton = UIUtils.createButton("REMOVE");
        displayButton = UIUtils.createButton("DISPLAY");
    }

    private void assembleComponents() {
        formPanel.add(UIUtils.createLabel("Nom:"));
        formPanel.add(firstName);
        formPanel.add(UIUtils.createLabel("Prenom:"));
        formPanel.add(lastName);
        formPanel.add(UIUtils.createLabel("Telephone:"));
        formPanel.add(phone);
        formPanel.add(UIUtils.createLabel("Email:"));
        formPanel.add(email);
        formPanel.add(UIUtils.createLabel("Salaire:"));
        formPanel.add(salary);
        formPanel.add(UIUtils.createLabel("Roles:"));
        formPanel.add(roles);
        formPanel.add(UIUtils.createLabel("Postes:"));
        formPanel.add(postes);

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayButton);

        this.add(formPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getFirstName() { return firstName; }
    public JTextField getLastName() { return lastName; }
    public JTextField getPhone() { return phone; }
    public JTextField getEmail() { return email; }
    public JTextField getSalary() { return salary; }
    public JComboBox<Role> getRoles() { return roles; }
    public JComboBox<Poste> getPostes() { return postes; }
    public JTable getTable() { return table; }
    public DefaultTableModel getModel() { return model; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getDisplayButton() { return displayButton; }
}
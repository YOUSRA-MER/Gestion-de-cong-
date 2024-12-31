package Controller;

import Model.*;
import VIEW.EmployeesView;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import DAO.*;

public class EmployeeController {
    private EmployeeModel model;
    private EmployeesView view;

    public EmployeeController(EmployeeModel model, EmployeesView view) {
        this.model = model;
        this.view = view;

        // Button listeners
        this.view.getAddButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddEmployee();
            }
        });

        this.view.getDeleteButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteEmployee();
            }
        });

        this.view.getUpdateButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateEmployee();
            }
        });

        this.view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                fillOutFields();
            }
        });

        this.view.getDisplayButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                display();
            }
        });

        this.view.getImportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleImport();
            }
        });

        this.view.getExportButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleExport();
            }
        });
    }

    private void AddEmployee() {
        if (view.getLastName().getText().isEmpty() ||
                view.getFirstName().getText().isEmpty() ||
                view.getEmail().getText().isEmpty() ||
                view.getPhone().getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = view.getLastName().getText();
        String prenom = view.getFirstName().getText();
        String email = view.getEmail().getText().toLowerCase();
        String telephone = view.getPhone().getText().trim();
        Poste poste = (Poste) view.getPostes().getSelectedItem();
        Role role = (Role) view.getRoles().getSelectedItem();

        try {
            double salaire = Double.parseDouble(view.getSalary().getText());
            String result = model.addEmployee(nom, prenom, email, telephone, salaire, role, poste);
            if (result.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Employee was added successfully", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                display();
            } else {
                JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException er) {
            JOptionPane.showMessageDialog(null, "Invalid salary format", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void DeleteEmployee() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            int id = (int) view.getTable().getValueAt(selectedRow, 0);
            if (model.deleteEmployee(id)) {
                JOptionPane.showMessageDialog(null, "Employee was deleted successfully", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                display();
            } else {
                JOptionPane.showMessageDialog(null, "Couldn't delete the employee. Please try again", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void UpdateEmployee() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            int id = (int) view.getTable().getValueAt(selectedRow, 0);
            String nom = view.getLastName().getText();
            String prenom = view.getFirstName().getText();
            String email = view.getEmail().getText().toLowerCase();
            String telephone = view.getPhone().getText().trim();
            Poste poste = (Poste) view.getPostes().getSelectedItem();
            Role role = (Role) view.getRoles().getSelectedItem();

            try {
                double salaire = Double.parseDouble(view.getSalary().getText());
                String result = model.modifyEmployee(id, nom, prenom, email, telephone, salaire, role, poste);
                if (result.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Employee was updated successfully", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    display();
                } else {
                    JOptionPane.showMessageDialog(null, result, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException er) {
                JOptionPane.showMessageDialog(null, "Invalid salary format", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an employee", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        List<Object[]> data = model.getAllElements();
        view.getModel().setRowCount(0);
        for (Object[] row : data) {
            view.getModel().addRow(row);
        }
    }

    public void fillOutFields() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            view.getLastName().setText(view.getTable().getValueAt(selectedRow, 1).toString());
            view.getFirstName().setText(view.getTable().getValueAt(selectedRow, 2).toString());
            view.getPhone().setText(view.getTable().getValueAt(selectedRow, 3).toString());
            view.getEmail().setText(view.getTable().getValueAt(selectedRow, 4).toString());
            view.getSalary().setText(view.getTable().getValueAt(selectedRow, 5).toString());
            view.getRoles().setSelectedItem((Role) view.getTable().getValueAt(selectedRow, 6));
            view.getPostes().setSelectedItem((Poste) view.getTable().getValueAt(selectedRow, 7));
        }
    }

    public void handleImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            model.importData(filePath);
            JOptionPane.showMessageDialog(null, "Data import successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            display();
        }
    }

    public void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv";
                }
                List<Employees> employe = new EmployeeDAOimplement().getAllElements();
                model.exportData(filePath, employe);
                JOptionPane.showMessageDialog(null, "Data export successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

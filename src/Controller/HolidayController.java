package Controller;

import java.time.LocalDate;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.*;
import Model.TypeConge;
import VIEW.UserInterface;
import DAO.*;

import java.io.IOException;

public class HolidayController {
    private UserInterface view;
    private HolidayModel model;

    public HolidayController(UserInterface view, HolidayModel model) {
        this.view = view;
        this.model = model;

        this.view.getAdd().addActionListener(e -> addHoliday());
        this.view.getUpdate().addActionListener(e -> updateHoliday());
        this.view.getDelete().addActionListener(e -> deleteHoliday());
        this.view.getTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    populateFields();
                }
            }
        });
        this.view.getRefresh().addActionListener(e -> refreshEmployeeIds());

        // Ajout des actions d'importation et d'exportation
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

        refreshDisplay();
    }

    private void addHoliday() {
        try {
            String selectedEmployee = (String) view.getIdComboBox().getSelectedItem();
            int idEmployee = Integer.parseInt(selectedEmployee.replace("Employee ", "").trim());
            LocalDate startDate = LocalDate.parse(view.getStartDateField().getText().trim());
            LocalDate endDate = LocalDate.parse(view.getEndDateField().getText().trim());
            TypeConge conge = (TypeConge) view.getCongeField().getSelectedItem();

            if (startDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException("La date de début ne peut pas être dans le passé.");
            }

            if (model.demandHoliday(idEmployee, startDate, endDate, conge)) {
                JOptionPane.showMessageDialog(null, "Holiday added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                refreshDisplay();
            } else {
                JOptionPane.showMessageDialog(null, "Holiday request failed. Check balance or duration.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur de date", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateHoliday() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            try {
                int id = (int) view.getTable().getValueAt(selectedRow, 0);
                String selectedEmployee = (String) view.getIdComboBox().getSelectedItem();
                int idEmployee = Integer.parseInt(selectedEmployee.replace("Employee ", "").trim());
                LocalDate startDate = LocalDate.parse(view.getStartDateField().getText().trim());
                LocalDate endDate = LocalDate.parse(view.getEndDateField().getText().trim());
                TypeConge conge = (TypeConge) view.getCongeField().getSelectedItem();

                if (startDate.isBefore(LocalDate.now())) {
                    throw new IllegalArgumentException("La date de début ne peut pas être dans le passé.");
                }

                if (model.updateHoliday(id, idEmployee, startDate, endDate, conge)) {
                    JOptionPane.showMessageDialog(null, "Holiday updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshDisplay();
                } else {
                    JOptionPane.showMessageDialog(null, "Update failed. Check balance or duration.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Erreur de date", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteHoliday() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            try {
                int id = (int) view.getTable().getValueAt(selectedRow, 0);
                if (model.deleteHoliday(id)) {
                    JOptionPane.showMessageDialog(null, "Holiday deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshDisplay();
                } else {
                    JOptionPane.showMessageDialog(null, "Deletion failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row first.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populateFields() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow > -1) {
            try {
                int idEmployee = (int) view.getTable().getValueAt(selectedRow, 1);
                String startDate = view.getTable().getValueAt(selectedRow, 2).toString();
                String endDate = view.getTable().getValueAt(selectedRow, 3).toString();
                String congeString = view.getTable().getValueAt(selectedRow, 4).toString();

                TypeConge conge = TypeConge.fromDescription(congeString);

                view.getIdComboBox().setSelectedItem("Employee " + idEmployee);
                view.getStartDateField().setText(startDate);
                view.getEndDateField().setText(endDate);
                view.getCongeField().setSelectedItem(conge);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshDisplay() {
        List<Object[]> holidays = model.displayHoliday();
        view.getTableModel().setRowCount(0);
        for (Object[] holiday : holidays) {
            view.getTableModel().addRow(holiday);
        }
    }

    private void refreshEmployeeIds() {
        List<String> employeeIds = model.getAllIds();
        view.getIdComboBox().removeAllItems();
        for (String id : employeeIds) {
            view.getIdComboBox().addItem("Employee " + id);
        }
    }

    private void handleImport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        if (fileChooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            model.importData(filePath);
            JOptionPane.showMessageDialog(null, "Data import successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
            refreshDisplay();
        }
    }

    private void handleExport() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
try{
        if (fileChooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }
            List<Holiday> holiday = new HolidayDAOimplement().getAllElements();
            model.exportData(filePath,holiday);
            JOptionPane.showMessageDialog(null, "Data export successful!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (IOException e) {
    e.getMessage();
}
}
}

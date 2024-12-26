package Controller;
import java.awt.event.*;
import javax.swing.JOptionPane;
import Model.*;
import VIEW.*;

public class UserLoginController {
    private UserView view;
    private UserModel model;
    private UserInterface application;

    public UserLoginController(UserView view, UserModel model, UserInterface application) {
        this.view = view;
        this.model = model;
        this.application = application;

        this.view.signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view.usernameField.getText().isEmpty() || view.passwordField.getPassword().length == 0) {
                    JOptionPane.showMessageDialog(null, "Username or Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //mousaab el harmali

                String username = view.usernameField.getText();
                String password = String.valueOf(view.passwordField.getPassword());

                if (model.addUser(username, password)) {
                    JOptionPane.showMessageDialog(null, "You have signed up successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Signup failed. The username might already exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.view.loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.usernameField.getText();
                String password = String.valueOf(view.passwordField.getPassword());

                if (model.checkUser(username, password)) {
                    application.setVisible(true);
                    setButtonsVisibility();
                    view.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void setButtonsVisibility() {
        String username = view.usernameField.getText();
        String password = String.valueOf(view.passwordField.getPassword());

        try {
            int id = model.getUserIds(username, password);
            if (id == 1) {//directeur
            } else if (id == 2) {
                application.getSwitchPanels().remove(application.getHolidayPanel());
            } else if (id == 3) {
                //holidays manager
                application.getEmployeePanel().getAddButton().setVisible(false);
                application.getEmployeePanel().getDeleteButton().setVisible(false);
                application.getEmployeePanel().getUpdateButton().setVisible(false);
            } else {
                //HR
                application.getSwitchPanels().remove(application.getHolidayPanel());
                application.getEmployeePanel().getAddButton().setVisible(false);
                application.getEmployeePanel().getDeleteButton().setVisible(false);
                application.getEmployeePanel().getUpdateButton().setVisible(false);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
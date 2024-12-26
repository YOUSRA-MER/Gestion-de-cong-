package VIEW;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class UserView extends JFrame {
    public JPanel mainPanel, inputPanel;
    public JLabel titleLabel, usernameLabel, passwordLabel;
    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton, signupButton;
    public UserView() {
        setTitle("Login");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(248, 249, 250));
        mainPanel.setBorder(new EmptyBorder(40, 30, 40, 30));

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setOpaque(false);


        usernameLabel = createLabel("Username");
        usernameField = createTextField();

        // Password components
        passwordLabel = createLabel("Password");
        passwordField = createPasswordField();

        loginButton = createButton("Login", new Color(52, 152, 219));
        signupButton = createButton("Sign Up", new Color(46, 204, 113));
        inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        inputPanel.add(usernameLabel);
        inputPanel.add(usernameField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        inputPanel.add(loginButton);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        inputPanel.add(signupButton);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(mainPanel);

        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(new Color(73, 80, 87));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(new CompoundBorder(
                new LineBorder(new Color(206, 212, 218), 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return textField;
    }

    private JPasswordField createPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(new CompoundBorder(
                new LineBorder(new Color(206, 212, 218), 1, true),
                new EmptyBorder(0, 10, 0, 10)
        ));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        return passwordField;
    }

    private JButton createButton(String title, Color backgroundColor) {
        JButton btn = new JButton(title);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(backgroundColor);
        btn.setForeground(Color.WHITE);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
    public JFrame getLoginInterface () {
        return this;
    }

}
package VIEW;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

class UIUtils {
    private static final Color BUTTON_COLOR = new Color(138, 43, 226);  // Violet (blue-violet)
    private static final Color BUTTON_HOVER_COLOR = new Color(186, 85, 211);  // Orchid (lighter violet)
    private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 14);

    public static JPanel createPanel(LayoutManager layout, int padding) {
        JPanel panel = new JPanel(layout);
        if (padding > 0) {
            panel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        }
        return panel;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        label.setForeground(new Color(138, 43, 226));  // Text in violet
        return label;
    }

    public static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BUTTON_COLOR));
        button.setPreferredSize(new Dimension(120, 35));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_COLOR);
            }
        });
        return button;
    }

    public static JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setBorder(new EmptyBorder(10, 3, 5, 10));
        textField.setBackground(new Color(230, 230, 250));  // Lavender pastel background
        return textField;
    }
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ChangePassword extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    private final JTextField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ChangePassword(String userID) {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        // Enter new password

        JLabel label = new JLabel("Enter new password");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setBounds(385, 80, 270, 70);
        panel.add(label);

        passwordField = new JTextField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 24));
        passwordField.setBounds(205, 150, 575, 70);
        panel.add(passwordField);

        // Submit button

        JButton submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 24));
        submit.setBounds(360, 265, 270, 70);
        submit.setForeground(new Color(247, 247, 247));
        submit.setBackground(new Color(217, 83, 79));

        submit.addActionListener(e -> {
            String password = passwordField.getText();
            String validationMessage = Methods.passwordValidationMessage(password);

            if (validationMessage.isEmpty()) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");

                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET password = ? WHERE userID = ?");
                    preparedStatement.setString(1, password);
                    preparedStatement.setString(2, userID);
                    preparedStatement.executeUpdate();

                    Methods.dialogMessage(" Password has been successfully changed ", true, "Password changed");

                    dispose();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            } else {
                Methods.dialogMessage(validationMessage, false, "Password validation message");
            }
        });

        panel.add(submit);
    }
}
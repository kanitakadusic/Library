import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.Serial;
import java.sql.*;

public class ViewUsers extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {});
    }

    public ViewUsers() {
        setBounds(270, 150, 1005, 505);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("ALL LIBRARY USERS");
        label.setFont(new Font("Arial", Font.PLAIN, 24));
        label.setBounds(385, 10, 270, 70);
        panel.add(label);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();

            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBounds(0, 90, 992, 455);

            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.addColumn("ID");
            tableModel.addColumn("Username");
            tableModel.addColumn("Password");
            tableModel.addColumn("Admin");

            JTable table = new JTable(tableModel);
            table.setFont(new Font("Arial", Font.PLAIN, 24));
            table.setRowHeight(50);
            table.setGridColor(new Color(211, 211, 211));
            table.setIntercellSpacing(new Dimension(20, 0));

            JTableHeader header = table.getTableHeader();
            header.setFont(new Font("Arial", Font.PLAIN, 24));
            header.setPreferredSize(new Dimension(scrollPane.getWidth(), 60));
            header.setBackground(new Color(91, 192, 222));

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(100);
            columnModel.getColumn(1).setPreferredWidth(420);
            columnModel.getColumn(2).setPreferredWidth(250);
            columnModel.getColumn(3).setPreferredWidth(220);

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

            while (resultSet.next()) {
                Object[] row = new Object[4];

                row[0] = resultSet.getString("users.userID");
                row[1] = resultSet.getString("users.username");
                row[2] = resultSet.getString("users.password");
                row[3] = resultSet.getString("users.admin");

                tableModel.addRow(row);
            }

            scrollPane.getViewport().add(table);
            panel.add(scrollPane);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.*;

import org.json.simple.*;

public class Gui implements ActionListener {
    private JFrame frame;
    private JPanel panel, tablePanel;
    private JLabel brand;
    private Order order;

    public Gui(String name) {
        init(name);
    }

    void init(String name) {
        frame = new JFrame(name);
        panel = new JPanel();
        tablePanel = new JPanel();
        order = new Order();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 1));
        frame.getContentPane().setBackground(Color.darkGray);
        addBrand();
        addMenu();
        // tables
        addTable(tablePanel);
        frame.add(tablePanel);
        addButton();
//        frame.setSize(800,600);
        frame.pack();
    }

    void addBrand() {
        brand = new JLabel();
        brand.setIcon(new ImageIcon("img/logo.png"));
        brand.setHorizontalAlignment(JLabel.CENTER);
        frame.getContentPane().add(brand);
    }

    void addMenu() {
        Json data = new Json();
        JSONArray obj = data.openJson("data/menu.json");
        JPanel listPanel = new JPanel();
        for (int i = 0; i < obj.size(); i++) {
            JSONObject obj1 = (JSONObject) obj.get(i);
            JButton button = new JButton("" + obj1.get("name"));
            button.addActionListener(this);
            panel.setBackground(Color.pink);
            JPanel menuPanel = new JPanel();
            menuPanel.setLayout(new GridLayout(4, 1));
            JLabel icon = new JLabel();
            icon.setIcon(new ImageIcon("img/" + obj1.get("img")));
            icon.setHorizontalAlignment(JLabel.CENTER);
            // row-1
            menuPanel.add(icon);
            // row-2
            JLabel price = new JLabel(" Price: " + obj1.get("price") + " ฿");
            price.setHorizontalAlignment(JLabel.CENTER);
            menuPanel.add(price);
            // row-3
            menuPanel.add(button);
            listPanel.add(menuPanel);
            panel.add(listPanel);
        }
        frame.getContentPane().add(panel);
    }

    void addButton() {
        JButton summit = new JButton("Order");
        JButton reset = new JButton("Reset");
        summit.addActionListener(this);
        reset.addActionListener(this);
        JPanel panel = new JPanel();
        panel.add(summit);
        panel.add(reset);
        frame.add(panel);
    }

    void addTable(JPanel container) {
        container.removeAll();
        container.revalidate();
        container.repaint();
        HashMap<String, Integer> data = order.getOrder();
        Object[][] tableData = new Object[data.keySet().size()][2];
        String column[] = {"Order", "Total"};
        int index = 0;
        for (String key : data.keySet()) {
            tableData[index][0] = key;
            tableData[index][1] = data.get(key);
            index++;
        }
        TableModel model = new DefaultTableModel(tableData, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        container.add(scrollPane);
    }

    public void actionPerformed(ActionEvent ae) {
        System.out.println(ae.getActionCommand());
        if (ae.getActionCommand().equals("Reset")) {
            order.resetOrder();
            addTable(tablePanel);
        } else if (ae.getActionCommand().equals("Order")) {
            Json orderJSON = new Json();
//            System.out.println(orderJSON.toJson(order.getOrder()));
            Network.sendSocket(orderJSON.toJson(order.getOrder()));
        } else {
            order.addOrder(ae.getActionCommand());
            addTable(tablePanel);
        }
//        System.out.println(order.getOrder());
    }
}

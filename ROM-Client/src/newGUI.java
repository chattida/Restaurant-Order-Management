import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import org.json.simple.*;

public class newGUI implements ActionListener {
    private JFrame frame;
    private JPanel header, content, footer, timestamp, menuList, row, orderList;
    private JLabel brand, date;
    private Order order;
    private JSONArray obj;

    public newGUI(String name) {
        init(name);
    }

    public void init(String name) {
        // frame
        frame = new JFrame("" + name);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // order
        order = new Order();
        // header
        header = new JPanel();
        header.setLayout(new GridLayout(1, 2));
        header.setBackground(Color.darkGray);
        // brand
        brand = new JLabel();
        brand.setIcon(new ImageIcon("img/logo.png"));
        brand.setVerticalAlignment(JLabel.CENTER);
        brand.setHorizontalAlignment(JLabel.LEFT);
        header.add(brand);
        // timestamp
        timestamp = new JPanel();
        timestamp.setLayout(new GridLayout(2, 1));
        timestamp.setBackground(Color.darkGray);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        date = new JLabel(dtf.format(now) + " ");
        Clock time = new Clock();
        Thread thread = new Thread(time);
        thread.start();
        date.setForeground(Color.white);
        time.setForeground(Color.white);
        date.setFont(new Font("Courier New", Font.BOLD, 14));
        time.setFont(new Font("Courier New", Font.BOLD, 14));
        date.setHorizontalAlignment(JLabel.RIGHT);
        time.setHorizontalAlignment(JLabel.RIGHT);
        timestamp.add(date);
        timestamp.add(time);
        header.add(timestamp);
        // content
        content = new JPanel();
        content.setLayout(new FlowLayout());
        content.setBackground(Color.darkGray);
        // menu
        Json data = new Json();
        obj = data.openJson("data/menu.json");
        menuList = new JPanel();
        menuList.setLayout(new GridLayout(3, 1));
        menuList.setBackground(Color.pink);
        row = new JPanel();
        row.setBackground(Color.BLUE);
        for (int i = 0; i < obj.size(); i++) {
            JSONObject obj1 = (JSONObject) obj.get(i);
            JPanel menu = new JPanel();

            // price, button
            JPanel small = new JPanel();
            small.setLayout(new GridLayout(3, 1));
            // menu image
            JLabel image = new JLabel();
            image.setIcon(new ImageIcon("img/" + obj1.get("img")));
            image.setHorizontalAlignment(JLabel.CENTER);
            // menu price
            JLabel price = new JLabel(" Price: " + obj1.get("price") + " ฿");
            price.setHorizontalAlignment(JLabel.CENTER);
            // add item, remove item to order
            JButton button = new JButton("+ " + obj1.get("name"));
            JButton button1 = new JButton("- " + obj1.get("name"));
            button.addActionListener(this);
            button1.addActionListener(this);
            // add to small
            small.add(price);
            small.add(button);
            small.add(button1);
            // add to menu
            menu.add(image, BorderLayout.NORTH);
            menu.add(small, BorderLayout.CENTER);
            row.add(menu);
            if (i % 5 == 4 && i != 0) {
                menuList.add(row);
                row = new JPanel();
                row.setBackground(Color.BLUE);
            }

        }
        // table
        orderList = new JPanel();
        orderList.setBackground(Color.cyan);
        addTable(orderList);
        // add menuList, orderList to content
        content.add(menuList);
        content.add(orderList);
        // footer
        footer = new JPanel();
        footer.setLayout(new FlowLayout());
        footer.setBackground(Color.darkGray);
        // order, reset button
        JButton summit = new JButton("Order");
        JButton reset = new JButton("Reset");
        summit.setFont(new Font("Courier New", Font.BOLD, 16));
        reset.setFont(new Font("Courier New", Font.BOLD, 16));
        summit.addActionListener(this);
        reset.addActionListener(this);
//        summit.setIcon(new Im);
        // add button to footer
        footer.add(summit);
        footer.add(reset);
        // add header, content to frame
        frame.getContentPane().add(header, BorderLayout.NORTH);
        frame.getContentPane().add(content, BorderLayout.CENTER);
        frame.getContentPane().add(footer, BorderLayout.SOUTH);
        // frame
        frame.pack();
        centerWindow(frame);
    }

    private void addTable(JPanel orderList) {
        orderList.removeAll();
        orderList.revalidate();
        orderList.repaint();
        HashMap<String, Integer> data = order.getOrder();
        Object[][] tableData = new Object[data.keySet().size()][3];
        String column[] = {"Order", "Total", "Price"};
        int index = 0;
        for (String key : data.keySet()) {
            tableData[index][0] = key;
            tableData[index][1] = data.get(key);
            for (int i = 0; i < obj.size(); i++) {
                JSONObject obj1 = (JSONObject) obj.get(i);
                if (obj1.get("name").equals(key)) {
                    double price = Double.parseDouble("" + obj1.get("price"));
                    int total = data.get(key);
                    tableData[index][2] = "" + (price * total);
                }
            }
            index++;
        }
        TableModel model = new DefaultTableModel(tableData, column) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        table.getTableHeader().setFont(new Font("Courier New", Font.BOLD, 16));
        table.setFont(new Font("Courier New", Font.BOLD, 18));
        table.setRowHeight(table.getRowHeight() + 10);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        orderList.add(scrollPane);
    }

    public void actionPerformed(ActionEvent ae) {
//        System.out.println(ae.getActionCommand());
        if (ae.getActionCommand().equals("Reset")) {
            order.resetOrder();
            addTable(orderList);
        } else if (ae.getActionCommand().equals("Order")) {
            Json orderJSON = new Json();
//            System.out.println(orderJSON.toJson(order.getOrder()));
            Network.sendSocket(orderJSON.toJson(order.getOrder()));
            order.resetOrder();
            addTable(orderList);
        } else {
            String name = ae.getActionCommand();
            String[] check = name.split(" ");
            if (check[0].equals("+")) {
                order.addOrder(check[1]);
            } else if (check[0].equals("-")) {
                order.removeOrder(check[1]);
            }
            addTable(orderList);
        }
//        System.out.println(order.getOrder());
    }

    public void centerWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }
}

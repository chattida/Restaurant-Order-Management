import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;

public class Splash {
    private JFrame frame;
    private JLabel loading;

    public Splash() {
        this.init();
    }

    public void init() {
        JFrame frame = new JFrame();
        JLabel loading = new JLabel();
        loading.setIcon(new ImageIcon("img/loading.png"));
        frame.add(loading);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        centerWindow(frame);
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println(e);
        }
        frame.dispose();
        new newGUI("Restaurant Order Management");
    }

    public void centerWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

}

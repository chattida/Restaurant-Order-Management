import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import com.apple.eawt.Application;

public class SplashScreen {
    private JFrame frame;
    private JLabel loading;

    public SplashScreen() {
        this.init();
    }

    public void init() {
        //icon for mac
        Application application = Application.getApplication();
        Image logo = Toolkit.getDefaultToolkit().getImage("img/dock.png");
        application.setDockIconImage(logo);
        // frame
        JFrame frame = new JFrame();
        // icon for windows (not sure)
        frame.setIconImage(new ImageIcon("img/dock.png").getImage());
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
        new GUI("Restaurant Order Management");
    }

    public void centerWindow(JFrame frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

}

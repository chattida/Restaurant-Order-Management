import javax.swing.*;
import java.time.format.DateTimeFormatter;


public class Countdown extends JLabel implements Runnable {
    public void run() {
        int sec = 0;
        while (true) {
            if (sec < 60){
                this.setText(sec+"s ");
            } else if (sec < 3600){
                this.setText((sec/60)+"m ");
            } else {
                this.setText((sec/3600)+"hr ");
            }
            try {
                Thread.sleep(1000);
                sec++;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
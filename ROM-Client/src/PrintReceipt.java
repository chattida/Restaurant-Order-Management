import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class PrintReceipt extends Clock implements Printable {

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {

            Graphics2D g2d = (Graphics2D) graphics;

            try {
                // Draw Header
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;

                g2d.setFont(new Font("Courier New", Font.BOLD, 10));
                g2d.drawString("      " + getCurrentDate() + "     " + getCurrentTime() + "  ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("      Restaurant Bill Receipt        ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += headerRectHeight;
                y += yShift;
                g2d.drawString(" Food Name                 T.Price   ", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 10, y);
                y += headerRectHeight;
//                g2d.drawString(" " + pn1a + "                  " + pp1a + "  ", 10, y);
//                y += yShift;
//                Order.getOrder().size();
//                for (int i = 0; i < k; i++)

                g2d.drawString("-------------------------------------", 10, y);
                y += yShift;
                g2d.drawString(" Total amount: " + String.format("%.02f ฿ ", Total.getTotal()) + "               ", 10, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 10, y);
                y += yShift;
                g2d.drawString("          Free Home Delivery         ", 10, y);
                y += yShift;
                g2d.drawString("             03111111111             ", 10, y);
                y += yShift;
                g2d.drawString("*************************************", 10, y);
                y += yShift;
                g2d.drawString(" THANKS FOR VISITING OUR RESTUARANT! ", 10, y);
                y += yShift;
                g2d.drawString("*************************************", 10, y);
                y += yShift;

            } catch (Exception r) {
                r.printStackTrace();
            }

            result = PAGE_EXISTS;
        }
        return result;
    }
}

import java.awt.*;
import java.awt.print.*;

public class PrintReceipt implements Printable {

    public static double cmToPPI(double cm) {
        return cm * 0.393600787 * 72d;
    }

    public static PageFormat getPageFormat(PrinterJob pj) {

        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double middleHeight = 8.0;
        double headerHeight = 2.0;
        double footerHeight = 2.0;
        double width = cmToPPI(8);      //printer know only point per inch.default value is 72ppi
        double height = cmToPPI(headerHeight + middleHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(0, 10, width, height - cmToPPI(1));
        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        Clock clock = new Clock();
        int result = NO_SUCH_PAGE;
        if (pageIndex == 0) {

            Graphics2D g2d = (Graphics2D) graphics;

            try {
                // Draw Header
                int y = 20;
                int yShift = 10;
                int headerRectHeight = 15;

                System.out.println(GUI.getMySender());

                g2d.setFont(new Font("Courier New", Font.BOLD, 10));
                g2d.drawString("      " + clock.getCurrentDate() + "     " + clock.getCurrentTime() + "  ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += yShift;
                g2d.drawString("     Restaurant Order Management     ", 12, y);
                y += yShift;
                g2d.drawString("-------------------------------------", 12, y);
                y += headerRectHeight;
                y += yShift;
                g2d.drawString(" Food Ordered          Total Price   ", 10, y);
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
                g2d.drawString(" THANKS FOR VISITING OUR RESTUARANT  ", 10, y);
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

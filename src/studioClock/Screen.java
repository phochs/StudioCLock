package studioClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * Created by Phochs on 10/04/15.
 */
public class Screen extends JPanel {
    JFrame display;
    public Screen() {
        display = new JFrame("StudioCLock");
        display.setSize(500, 500);
        display.setExtendedState(Frame.MAXIMIZED_BOTH);
        display.setMinimumSize(new Dimension(500, 250));
        display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.setLocationRelativeTo(null);
        display.add(this);
        display.setVisible(true);

        // Transparent 16 x 16 pixel cursor image.
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

        // Create a new blank cursor.
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                cursorImg, new Point(0, 0), "blank cursor");

        // Set the blank cursor to the JFrame.
        display.getContentPane().setCursor(blankCursor);

        int timerTimeInMilliSeconds = 100;
        javax.swing.Timer timer = new javax.swing.Timer(timerTimeInMilliSeconds, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D graphics2D = (Graphics2D) g;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
                RenderingHints.VALUE_ANTIALIAS_ON);

        Date currentDate = new Date();
        int seconds = currentDate.getSeconds();
        //if(seconds == 0)
        //    seconds = 60;

        String strHours = String.format("%02d", currentDate.getHours());
        String strMinutes = String.format("%02d", currentDate.getMinutes());
        String strSeconds = String.format("%02d", currentDate.getSeconds());

        int radius = Math.min((getWidth()-30) / 2, (getHeight()-30) / 2);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        drawCircle(graphics2D, centerX, centerY, radius, 60, radius / 20, seconds);
        drawCircle(graphics2D, centerX, centerY, (int) (radius * 0.9), 12, radius / 20, 20);

        Font textFont = new Font("DS-DIGITAL", Font.PLAIN, (int)(radius*0.55));
        graphics2D.setFont(textFont);
        FontMetrics fontMetrics = graphics2D.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(strHours + ":" + strMinutes);
        graphics2D.drawString(strHours + ":" + strMinutes, (getWidth()-stringWidth)/2, (int)(getHeight()*0.5));

        stringWidth = fontMetrics.stringWidth(strSeconds);
        graphics2D.drawString(strSeconds, (getWidth()-stringWidth)/2, (int)(getHeight()*0.8));
    }

    public void drawCircle(Graphics2D graphics2D, int xCenter, int yCenter, int radius, int nrBullets, int bulletSize, int darkAt) {
        int x;
        int y;
        int degreesPerStep = 360/nrBullets;

        graphics2D.setColor(Color.RED);
        //System.out.println(darkAt);

        for(int i=0;i<nrBullets;i++) {
            if(i > darkAt)
                graphics2D.setColor(new Color(100, 0, 0));

            x = (int)((radius * Math.cos(Math.toRadians((i*degreesPerStep)-90))) + xCenter);
            y = (int)((radius * Math.sin(Math.toRadians((i*degreesPerStep)-90))) + yCenter);
            //System.out.println("i: " + i + " X: " + x + "    Y: " + y);
            graphics2D.fillOval(x-(bulletSize/2), y-(bulletSize/2), bulletSize, bulletSize);
        }
    }
}

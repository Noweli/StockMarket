package logic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class DataPrinter extends JPanel implements Runnable {

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;

    private int lineX = 75;
    private double price;

    private int newX;
    private int oldX = 0;


    private DataReceiver data;
    private JFrame stockWindow;

    public DataPrinter() {
        try {
            data = new DataReceiver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        createWindow();
        run();
    }

    public void createWindow() {
        stockWindow = new JFrame();
        stockWindow.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        stockWindow.setVisible(true);
        stockWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stockWindow.add(this);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine(70, 10, 70, WINDOW_HEIGHT - 60);
        g.drawLine(70, WINDOW_HEIGHT - 60, WINDOW_WIDTH - 30, WINDOW_HEIGHT - 60);

        for (int i = WINDOW_HEIGHT - 100; i > 20; i -= 50) {
            g.drawString("$" + (WINDOW_HEIGHT - 50 - i), 0, i);
        }

        try {
            price = Double.parseDouble(data.getLine().substring(1));
            System.out.println(price);
            newX = (int)((WINDOW_HEIGHT - 100) - price/1.1627);

            if(oldX == 0) oldX = newX;

            System.out.println(newX);
            g.drawLine(lineX, oldX, lineX - 5, newX);
            oldX = newX;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lineX += 5;
            repaint();
        }
    }
}

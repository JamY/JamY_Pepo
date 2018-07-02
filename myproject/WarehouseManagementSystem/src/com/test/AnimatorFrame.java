package com.test;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author admin
 */
public class AnimatorFrame {


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setSize(300, 400);

        final JPanel panel = new JPanel() {

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                Map<RenderingHints.Key, Object> map = new HashMap<RenderingHints.Key, Object>();
                map.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
                map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                map.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHints(map);
                g2d.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
                g2d.fill(new Ellipse2D.Float((int) (Math.random() * getWidth()), (int) (Math.random() * getWidth()), 50f, 50f));
            }
        };
        Timer timer = new Timer(50, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                panel.repaint();
            }
        });
        timer.setRepeats(true);
        timer.start();
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
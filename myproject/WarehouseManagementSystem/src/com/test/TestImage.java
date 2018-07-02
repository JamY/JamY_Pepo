package com.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
 
public class TestImage extends Frame
{
    private static final long serialVersionUID = 1L;
    private static boolean PRESSED = false;
    private static int pointX = 0;
    private static int pointy = 200;
    private static int RIGHT_GO = 0;
    private static int LEFT_GO = 0;
    private static int DIR = 0;
    private static int ANGLE = 0;
    private static int W = 50;
    private static int H = 60;
    private _Canvas canvas = null;
 
    public TestImage ()
    {
        add (canvas = new _Canvas ());
        setIgnoreRepaint (true);
        requestFocus ();
    }
 
    public class _Canvas extends Canvas implements Runnable
    {
        private static final long serialVersionUID = 1L;
        private BufferedImage bi = null;
        private Image bufferedImage = null;
        private Thread thread = null;
        private long sleepTime = 10;
 
        public _Canvas ()
        {
            try
            {
                bi = ImageIO.read (new File ("image/11.jpg"));
            }
            catch (IOException e)
            {}
            setBackground (Color.BLACK);
            requestFocus ();
            addKeyListener (new KeyListener ()
            {
                @Override
                public void keyTyped ( KeyEvent e )
                {}
 
                @Override
                public void keyReleased ( KeyEvent e )
                {
                    RIGHT_GO = 0;
                    PRESSED = false;
                }
 
                @Override
                public void keyPressed ( KeyEvent e )
                {
                    // 38 40 37 39上下左右
                    DIR = e.getKeyCode ();
                    PRESSED = true;
                }
            });
        }
 
        @Override
        public void paint ( Graphics g )
        {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint (RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage (rotateImage (bi.getSubimage (RIGHT_GO, LEFT_GO, W, H), ANGLE, true), pointX, pointy, W, H,
                this);
            g2d.dispose ();
        }
 
        @Override
        public void update ( Graphics g )
        {
            if (null == bufferedImage)
            {
                bufferedImage = createImage (getWidth (), getHeight ());
            }
            Graphics bufferedG = bufferedImage.getGraphics ();
            bufferedG.clearRect (0, 0, getWidth (), getHeight ());
            paint (bufferedG);
            bufferedG.dispose ();
            g.drawImage (bufferedImage, 0, 0, this);
            g.dispose ();
        }
 
        public void start ()
        {
            thread = new Thread (this);
            thread.setName ("TestImage");
            thread.setPriority (Thread.MIN_PRIORITY);
            thread.start ();
        }
 
        public synchronized void stop ()
        {
            thread = null;
            notify ();
        }
 
        @Override
        public void run ()
        {
            Thread me = Thread.currentThread ();
            while (thread == me && !isShowing () || getSize ().width == 0)
            {
                try
                {
                    Thread.sleep (555);
                }
                catch (InterruptedException e)
                {
                    return;
                }
            }
            while (thread == me && isShowing ())
            {
                if (PRESSED)
                {
                    try
                    {
                        if (DIR == 39)
                        {
                            RIGHT_GO = RIGHT_GO + 50;
                            LEFT_GO = 0;
                            pointX = pointX + 1;
                            if (pointX > 420)
                            {
                                ANGLE = 90;
                                pointX--;
                                pointy--;
                                W = 60;
                                H = 50;
                            }
                            if (RIGHT_GO > 50)
                            {
                                RIGHT_GO = 0;
                            }
                        }
                        else if (DIR == 37)
                        {
                            pointX = pointX - 1;
                            RIGHT_GO = RIGHT_GO + 50;
                            LEFT_GO = 60;
                            if (pointX < 0)
                            {
                                ANGLE = -90;
                                pointX++;
                                pointy--;
                                W = 60;
                                H = 50;
                            }
                            if (RIGHT_GO > 50)
                            {
                                RIGHT_GO = 0;
                            }
                        }
                        else if (DIR == 38)
                        {
                            W = 50;
                            H = 60;
                            pointy = 150;
                            ANGLE = 0;
                            RIGHT_GO = 100;
                        }
                        else if (DIR == 40)
                        {
                            W = 50;
                            H = 60;
                            ANGLE = 0;
                            pointy = 200;
                            RIGHT_GO = 0;
                        }
                        Thread.sleep (sleepTime);
                        repaint ();
                    }
                    catch (InterruptedException e)
                    {
                        break;
                    }
                }
                else
                {
                    RIGHT_GO = RIGHT_GO + 50;
                    LEFT_GO = 0;
                    pointX = pointX + 1;
                    if (RIGHT_GO > 50)
                    {
                        RIGHT_GO = 0;
                    }
                    if (pointX > 500)
                    {
                        pointX = 0;
                    }
                    try
                    {
                        Thread.sleep (sleepTime);
                        repaint ();
                    }
                    catch (InterruptedException e)
                    {
                        break;
                    }
                }
            }
            thread = null;
        }
    }
 
    /**
     * 旋转图像为指定角度
     * 
     * @param degree
     * @return
     */
    public static BufferedImage rotateImage ( final BufferedImage image, final int angdeg, final boolean d )
    {
        int w = image.getWidth ();
        int h = image.getHeight ();
        int type = image.getColorModel ().getTransparency ();
        BufferedImage img;
        Graphics2D graphics2d;
        ( graphics2d = ( img = new BufferedImage (w, h, type) ).createGraphics () ).setRenderingHint (
            RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2d.rotate (d ? -Math.toRadians (angdeg) : Math.toRadians (angdeg), w / 2, h / 2);
        graphics2d.drawImage (image, 0, 0, null);
        graphics2d.dispose ();
        return img;
    }
 
    public static void main ( String[] args )
    {
        EventQueue.invokeLater (new Runnable ()
        {
            @Override
            public void run ()
            {
                final TestImage ti = new TestImage ();
                ti.setSize (new Dimension (500, 300));
                ti.setLocationRelativeTo (null);
                ti.addWindowListener (new WindowAdapter ()
                {
                    @Override
                    public void windowClosing ( WindowEvent e )
                    {
                        System.exit (0);
                    }
 
                    @Override
                    public void windowDeiconified ( WindowEvent e )
                    {
                        ti.canvas.start ();
                    }
 
                    @Override
                    public void windowIconified ( WindowEvent e )
                    {
                        ti.canvas.stop ();
                    }
                });
                ti.setResizable (false);
                ti.canvas.start ();
                ti.setVisible (true);
            }
        });
    }
}
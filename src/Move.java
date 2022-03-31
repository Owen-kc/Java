import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Move extends JFrame implements KeyListener{


    //drawing area
    private CanvasPanel drawingArea = new CanvasPanel();

    //private Container contentPane = this.getContentPane();

    public Move()
    {
        setSize(750, 750);
        setTitle("Mover");          //set size, title, keylistener
        addKeyListener(this);

    }

    public void init()
    {
        add(drawingArea);
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key Pressed");     //statement
        drawingArea.moveLeft();                //move left in drawing area
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("key Pressed");     //if any key is pressed, move left
    }

    @Override
    public void keyReleased(KeyEvent e) {       //key released, when released execute the if statements below


        if(e.getKeyCode() == 39 ){  //move right
            drawingArea.moveRight();   //move right in drawing area
        }
        else if(e.getKeyCode() == 37 ) //move left
        {
            drawingArea.moveLeft();     //move left in drawing area
        }
        else if(e.getKeyCode() == 38 ) //move up
        {
            drawingArea.moveUp();       //move up in drawing area
        }
        else if(e.getKeyCode() == 40 )  //move down
        {
            drawingArea.moveDown();     //move down in drawing area
        }
    }


    //canvas panel
    class CanvasPanel extends JPanel{

        private int xPos, yPos, xStep, yStep;       //int xpos, ypos, xstep, ystep


        public CanvasPanel()
        {
            xPos = 30;
            yPos = 30;          //set amount to move for x and y
            xStep = 50;
            yStep = -50;
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);        //super paint components
            drawShapes(g);
        }

        public void drawShapes(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;        //set graphics2d to g2d

            g2d.setPaint(Color.red);                //set color

            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     //rendering hints

            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  //put rendering hints

            g2d.setRenderingHints(rh);      //set rendering hints in g2d

            g2d.fillOval(xPos,yPos,50,50);      //fill oval for player shape, w-50, h-50

        }

        //move left
        public void moveLeft()
        {
            xPos =  xPos - xStep;

            repaint();
        }


        //move right
        public void moveRight()
        {
            xPos =  xPos + xStep;

            repaint();
        }


        //move up
        public void moveUp()
        {
            yPos = yPos + yStep;

            repaint();
        }


        //move down
        public void moveDown()
        {
            yPos = yPos - yStep;

            repaint();
        }


    }


    //move init  main method
    public static void main(String[] args) {
        new Move().init();
    }


}
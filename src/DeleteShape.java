import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DeleteShape extends JFrame implements KeyListener{

    //drawingarea decleration
    private CanvasPanel drawingArea = new CanvasPanel();
    //private Container contentPane = this.getContentPane();

    //delete shape
    public DeleteShape()
    {
        setSize(750, 750);
        setTitle("Shape Remover. Press spacebar to remove shape.");     //set title size and key listener
        addKeyListener(this);
    }

    public void init()
    {
        add(drawingArea);       //add drawing area and set visible
        setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {       //key released event, when these keys are released execute the corresponding if statement below.

        if(e.getKeyCode() == 39 ){
            drawingArea.moveRight();        //right arrow key code, move right
        }
        else if(e.getKeyCode() == 37 )
        {
            drawingArea.moveLeft();         //left arrow key code, move left
        }
        else if(e.getKeyCode() == 38 )
        {
            drawingArea.moveUp();           //up arrow key code, move up
        }
        else if(e.getKeyCode() == 40 )
        {
            drawingArea.moveDown();         //down arrow key code, move down
        }

        if(e.getKeyCode() == 32){
            drawingArea.removeShape();      //space arrow key code, delete shape
        }
    }

    class CanvasPanel extends JPanel {

        private Ellipse2D.Double player = null;
        private ArrayList<Shape> shapes = new ArrayList<>();        //arraylist, player init, these must be declared in here as when the panel is repainted, i want only the player shape to be repainted, not the shapes in the arraylist
        private int step = 40;                                      //step, xpos and ypos
        private int xPos, yPos;

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            drawShapes(g);                  //paint components, drawshapes(g)
        }

        public void drawShapes(Graphics g)
        {
            Graphics2D g2d = (Graphics2D) g;        //g2d decleration, g
            g2d.setPaint(Color.blue);               //set colour of shapes on panel to blue
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);     // renderinghints for anti ailising on the shapes
            rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);  //put rendering hints
            g2d.setRenderingHints(rh);  //set rendering hints for g2d

            if(player == null)  //if player is null
            {
                xPos = (this.getWidth()/2) - 25;            //xPos, yPos set to width/2 -25
                yPos  =(this.getHeight()/2) - 25;
                shapes.add(new Rectangle2D.Double(50,50,100,200));
                shapes.add(new Rectangle2D.Double(550, 200, 100, 90));      //add shapes to arraylist
                shapes.add(new Rectangle2D.Double(300, 400, 100, 90));
            }

            player = new Ellipse2D.Double(xPos, yPos, 50, 50);  //player shape

            for(Shape s : shapes)       //for shape in shape arraylist
            {
                g2d.fill(s);            //fill shapes
            }

            g2d.setPaint(Color.red);        //set colour of player to red
            g2d.fill(player);



        }

        public boolean detectCollision(Shape a, Shape b)        //detect collision, shape a is player, shape b is shape colliding
        {
            if(a.intersects(b.getBounds2D()))       //if a intersects b's bounds
            {
                Area areaA = new Area(a);       //set areaA
                Area areaB = new Area(b);       //set areaB
                areaA.intersect(areaB);         //areaA intersects areaB dec
                return !areaA.isEmpty();        //return area if its not empty
            }

            return false;
        }

        public void removeShape()       //remove shape method
        {
            for(int i = 0; i < shapes.size(); i++)     //for int i
            {
                if(detectCollision(player, shapes.get(i)))      //call detectCollision method, for player and shapes.i
                {
                    shapes.remove(shapes.get(i));                   //remove i from shapes arraylist
                    System.out.println("Shape has been removed");   //print out that shape has been removed
                }
            }
            if(shapes.isEmpty())        //if shapes is empty
            {
                System.out.println("No shapes left, closing application");  //print out statement
                System.exit(0);                                       //exit gui
            }
            repaint();
        }

        //move left
        public void moveLeft()
        {
            xPos =  xPos - step;    //xpos = xpos - step (-x moves left)

            repaint();              //repaint
        }

        //move right
        public void moveRight()
        {
            xPos =  xPos + step;     //xpos = xpos + step (+x moves right)

            repaint();              //repaint
        }

        //move up
        public void moveUp()
        {
            yPos = yPos - step;     //yPos = ypos - step (-y moves up)

            repaint();              //repaint
        }

        //move down
        public void moveDown()
        {
            yPos = yPos + step;     //ypos =yxpos + step (+x moves down)

            repaint();              //repaint

        }
    }

    public static void main(String[] args) {
        new DeleteShape().init();               //call init for delete shape, run main
    }

}
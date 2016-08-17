package graphics;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *
 * @author CIT027949
 */
public class CIT027949Asg2 extends JFrame
{
    private final int MAX_X = 600;
    private final int MAX_Y = 400;
    private final int SLEEP_INTERVAL = 10;
    private int sweepX = 0;
    private int sweepY = 0;
    private final int sweepWidth = 50;
    private final int sweepHeight = 50;
    private final int blockWidth = 10;
    private final int blockHeight = 10;
    private int startDragX = 0;
    private int startDragY = 0;
    private boolean gameStarted = false;
    private ArrayList<Block> blocks = new ArrayList<Block>();
    private final Font f1 = new Font("sans-serif", Font.BOLD, 24);
    
    boolean gameover = false;
   // private JButton btnRes = new JButton("Restart");

    public CIT027949Asg2() 
    {
        setTitle("Sweeper");
        setSize(MAX_X + 20, MAX_Y + 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        GameArea ga = new GameArea();
        getContentPane().add(ga);
        ga.setSize(MAX_X, MAX_Y);

       /* getContentPane().add(btnRes);
        btnRes.setBounds(0, 50, 50, 50); */
        
        addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });

        //btnRes.addActionListener(new ActionListener() {
            
       // });
        
        setVisible(true);
        
        JOptionPane.showMessageDialog(this, "Click on the sweeper to start the game");
        while (true) 
        {
            gameStarted = false;
            while (!gameStarted) //continue waiting until a mouse press starts the game
            {
                try 
                {
                    Thread.sleep(1);
                } 
                catch (Exception ex) 
                {
                }
            }

            Random rand = new Random();
            
                Block b1 = new Block(40, 120, 1, 2, 60, 60);
                blocks.add(b1);
                Block b2 = new Block(40, 120, 1, 2, 60, 60);
                blocks.add(b2);
                Block b3 = new Block(40, 120, 1, 2, 60, 60);
                blocks.add(b3);
                Block b4 = new Block(40, 120, 1, 2, 60, 60);
                blocks.add(b4);
                
                
            /* Code to generate random sized blocks, won't need as fixed size blocks will be made
            for (int i = 0; i < 40; i++) //create 40 blocks
            {    
                Block block = new Block(rand.nextInt(MAX_X - blockWidth),
                        rand.nextInt(MAX_Y - blockHeight),
                        rand.nextInt(5) - 2, // -2 to +2
                        rand.nextInt(5) - 2 // -2 to +2
                        );
                blocks.add(block);
            }*/


            //play the game
            long startTime = System.nanoTime();
            
           
            while (!gameover) 
            {
                ArrayList<Block> collision = new ArrayList<Block>();
                //move each block
                for (Block block : blocks) 
                {
                    block.setX(block.getX() + block.getIncX());
                    block.setY(block.getY() + block.getIncY());

                    if ((block.getX() <= 0) || (block.getX() >= MAX_X - blockWidth)) //check if block has reached right or left side
                    {
                        block.setIncX(-block.getIncX()); //reverse the X direction of the block
                    }
                    if ((block.getY() <= 0) || (block.getY() >= MAX_Y - blockHeight)) //check if block has reached top or bottom
                    {
                        block.setIncY(-block.getIncY()); //reverse the Y direction of the block
                    }
                    //see if block has collided with sweep
                    if ((sweepX + sweepWidth >= block.getX()) && //RHS sweep >= LHS block
                            (sweepX <= block.getX() + blockWidth) && //LHS sweep <= RHS block
                            (sweepY <= block.getY() + blockHeight) && //top sweep <= bottom block
                            (sweepY + sweepHeight >= block.getY())) //bottom sweep >= top block
                    {
                        collision.add(block); // add this block to a list that has collided with the sweep & need
                        // to be deleted. Note that we can't delete it straight away as we 
                        // are in aloop of all blocks
                    }
                }
                for (Block collidedBlock : collision) // delete all blocks that have had a collission
                {
                    blocks.remove(collidedBlock);
                }
                ga.repaint();

                try 
                {
                    Thread.sleep(SLEEP_INTERVAL); //pause some milliseconds to slow movement and allow user input action

                } catch (Exception ex) 
                {
                }
            }
            long elapsedTime = System.nanoTime() - startTime;
            JOptionPane.showMessageDialog(this, "You have survived for " + elapsedTime / 1000000000.0 + " seconds!");
        }
    }

    private void formMousePressed(java.awt.event.MouseEvent evt) 
    {
        gameStarted = true; //start the game if it is not already started
        // record the start position of a drag (which begins with the mouse press)
        startDragX = evt.getX();
        startDragY = evt.getY();
    }

    private void formMouseDragged(java.awt.event.MouseEvent evt) 
    {
        sweepX += evt.getX() - startDragX; //adjust the sweepX value by the amount the mouse has been dragged
        startDragX = evt.getX(); //store the current X position as the start for future moves
        sweepY += evt.getY() - startDragY; //adjust the sweepY value by the amount the mouse has been dragged
        startDragY = evt.getY(); //store the current Y position as the start for future moves

        //make sure the sweeper stays on the panel
        if (sweepX < 0) {
            gameover = true;
        }
        if (sweepX > MAX_X - sweepWidth) {
            gameover = true;
        }
        if (sweepY < 0) {
            gameover = true;
        }
        if (sweepY > MAX_Y - sweepHeight) {
            gameover = true;
        }
    }
    
    public static void main(String[] args) 
    {
        CIT027949Asg2 sweep = new CIT027949Asg2(); 
    }

    class Block 
    {
        private int x;
        private int y;
        private int incX;
        private int incY;
        private int width;
        private int height;
        
        //Block constructor
        public Block(int inX, int inY, int inIncX, int inIncY, int inWidth, int inHeight) 
        {
            x = inX;
            y = inY;
            incX = inIncX;
            incY = inIncY;
            width = inWidth;
            height = inHeight;
        }

        public int getX() 
        {
            return x;
        }

        public void setX(int inX) 
        {
            x = inX;
        }

        public int getY() 
        {
            return y;
        }

        public void setY(int inY) 
        {
            y = inY;
        }

        public int getIncX() 
        {
            return incX;
        }

        public void setIncX(int inIncX) 
        {
            incX = inIncX;
        }

        public int getIncY() 
        {
            return incY;
        }

        public void setIncY(int inIncY) 
        {
            incY = inIncY;
        }
    }

    class GameArea extends JPanel //this one is done as an inner class with access to outer class attributes
    {

        public void paintComponent(Graphics g) 
        {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, MAX_X, MAX_Y);

            g.setColor(Color.YELLOW);
            g.fillRect(sweepX, sweepY, sweepWidth, sweepHeight);


            g.setColor(Color.WHITE);
            for (Block block : blocks) 
            {
                g.fillRect(block.getX(), block.getY(), 10, 10);
            }
        }
    }
}
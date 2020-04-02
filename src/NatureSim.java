package pkg6c.nature.sim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class NatureSim extends JFrame implements KeyListener, MouseListener{
    
    private final int WIDTH = 50, HEIGHT = 50;
    private int mouseX = 0, mouseY = 0; 
    private boolean loop = false;
    private final int SCALE = 16;
    
    int t[][] = new int[WIDTH][HEIGHT];
    boolean w[][] = new boolean[WIDTH][HEIGHT];
    int f[][] = new int[WIDTH][HEIGHT];
    int d[][] = new int[WIDTH][HEIGHT];
    boolean c[][] = new boolean[WIDTH][HEIGHT];
    
    public NatureSim(){
        super("Predator Versus Prey");
        setSize(SCALE*WIDTH, SCALE*HEIGHT+22);
        setVisible(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
        
        
        loop = true;
        waterGen();
        tileType();
        while(true){
            this.repaint();
            updateMouse();
            update();
            try{
                Thread.sleep(50);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            
        }
        
    }
    
    private void update(){
        AI();
    }
    private void waterGen(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if (Math.random() < .0003){
                    w[x][y] = true;
                }
            }
        }
        for (int i = 0; i < 750; i++){
            for (int x = 0; x < WIDTH; x++){
                for (int y = 0; y < HEIGHT; y++){
                    if(c[x][y]){
                        w[x][y] = true;
                    }
                    c[x][y] = false;
                    if(!w[x][y]){
                        if(x >= 1){
                            if (w[x-1][y]){
                                if (Math.random() > .99){
                                    c[x][y] = true;
                                }
                            }
                        }
                        if(x < WIDTH-1){
                            if (w[x+1][y]){
                                if (Math.random() > .99){
                                    c[x][y] = true;
                                }
                            }
                        }
                        if(y >= 1){
                            if (w[x][y-1]){
                                if (Math.random() > .99){
                                    c[x][y] = true;
                                }
                            }
                        }
                        if(y < HEIGHT-1){
                            if (w[x][y+1]){
                                if (Math.random() > .99){
                                    c[x][y] = true;
                                }
                            }
                        }
                    }
                }
            }           
        }
    }
    private void tileType(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if (w[x][y]){
                    t[x][y] = 1;
                }
            }
        }
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if(t[x][y] == 0){
                    if (Math.random() > .999){
                        t[x][y] = 2;
                    }
                    if (Math.random() > .999){
                        t[x][y] = 3;
                    }
                    if (Math.random() > .997){
                        t[x][y] = 4;
                    }
                    if (Math.random() > .998){
                        t[x][y] = 5;
                    }
                    if (Math.random() > .997){
                        t[x][y] = 6;
                    }
                    if (Math.random() > .98){
                        t[x][y] = 8;
                    }
                    if (Math.random() > .97){
                        t[x][y] = 9;
                    }
                }
                if(t[x][y] == 1){
                    if (Math.random() <.1){
                        t[x][y] = 7;
                    }
                }
            }    
        }            
    }
    private void AI(){//pass in some vars for eating, etc
        //There's nothing here!
    }
    private void images(){
        //try {titleImage = ImageIO.read(this.getClass().getResource("title.png"));} catch (Exception ex) {System.out.println(“Missing Image“); ex.printStackTrace();}
    }
    public void paint(Graphics g){
        if(loop){
            BufferedImage B = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics r = B.getGraphics();
            
            for (int x = 0; x < WIDTH; x++){
                for (int y = 0; y < HEIGHT; y++){
                    if (t[x][y] == 0){
                        r.setColor(Color.GREEN);
                    }
                    else if(t[x][y] == 1){
                        r.setColor(new Color(90,160,245));
                    }
                    else if(t[x][y] == 2){
                        r.setColor(Color.ORANGE);
                    }
                    else if(t[x][y] == 3){
                        r.setColor(Color.GRAY);
                    }
                    else if(t[x][y] == 4){
                        r.setColor(Color.PINK);
                    }
                    else if(t[x][y] == 5){
                        r.setColor(Color.YELLOW);
                    }
                    else if(t[x][y] == 6){
                        r.setColor(new Color(100,100,100));
                    }
                    else if(t[x][y] == 7){
                        r.setColor(Color.CYAN);
                    }
                    else if(t[x][y] == 8){
                        r.setColor(Color.BLUE);
                    }
                    else if(t[x][y] == 9){
                        r.setColor(new Color(0,150,0));
                    }
                    else if(t[x][y] == 10){
                        r.setColor(Color.RED);
                    }
                    r.fillRect(x*SCALE, y*SCALE, SCALE, SCALE);
                }
            }
            
            g.drawImage(B, 0, 22, this);
        }
    }
    
    private void updateMouse(){
        mouseX=MouseInfo.getPointerInfo().getLocation().x-getLocationOnScreen().x;
        mouseY=MouseInfo.getPointerInfo().getLocation().y-getLocationOnScreen().y;
    }
    
    public static void main(String[] args) {
        new NatureSim();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
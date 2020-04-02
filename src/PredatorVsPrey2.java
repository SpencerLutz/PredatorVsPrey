import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class PredatorVsPrey2 extends JFrame implements KeyListener, MouseListener{
    
    private final int WIDTH = 32, HEIGHT = 32;
    private int mouseX = 0, mouseY = 0; 
    private boolean loop = false;
    private final int SCALE = 16;
    
    int t[][] = new int[WIDTH][HEIGHT];//-1s?
    //nt num1 = 0;
    int age[][] = new int[WIDTH][HEIGHT];
    
    boolean go;
    boolean hm = false;
    
    public int moveDir = -1;
    public int score = 0;
    
    public PredatorVsPrey2(){
        super("Predator Versus Prey Game");
        setSize(SCALE*WIDTH, SCALE*HEIGHT+22);
        setVisible(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
        
        
        loop = true;
        tileType();
        
        while(true){
            this.repaint();
            updateMouse();
            if(hm){
                update();
                hm=false;
                moveDir = -1;
            }
            /*try{
                Thread.sleep(500);
            }
            catch(Exception ex){
                ex.printStackTrace();*/
           // }
            
        }
        
    }
    
    private void update(){
        
            movement();
            checkLoss();
        
        go = false;
    }
    private void tileType(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                int p = 0;
                if(x == WIDTH/2 && y == HEIGHT/2){
                    p = 4;
                }else{
                    if (Math.random() > .97){
                        p = 1;
                    }
                    else if(Math.random() > .93){
                        p = 2;
                    }
                    else if(Math.random() > .9){
                        p = 3;
                    }
                }
                t[x][y] = p;
            }
        }
    }
    private void movement(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if(t[x][y] == 0){
                    if(Math.random() > .99){
                        t[x][y] = 1;
                    }
                    else if(Math.random() > .99){
                        t[x][y] = 2;
                    }
                    else if(Math.random() > .99){
                        t[x][y] = 3;
                    }   
                }
                if(t[x][y] == 1){
                    
                    //num1++;
                    if (x >= 1){
                        if (t[x-1][y] == 2 || t[x-1][y] == 4){
                            t[x-1][y] = 1;
                            age[x-1][y] = 0;
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 &&  t[x-1][y] != 3){
                                        t[x-1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 &&  t[x+1][y] != 3){
                                        t[x+1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 &&  t[x][y-1] != 3){
                                        t[x][y-1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 &&  t[x][y+1] != 3){
                                        t[x][y+1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                        }
                    }
                    /*}else{
                        t[x][y] = 0;
                        t[x+1][y] = 1;
                    }*/
                    else if (x < WIDTH-1){
                        if (t[x+1][y] == 2 || t[x+1][y] == 4){
                            t[x+1][y] = 1;
                            age[x+1][y] = 0;
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 &&  t[x-1][y] != 3){
                                        t[x-1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 &&  t[x+1][y] != 3){
                                        t[x+1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 &&  t[x][y-1] != 3){
                                        t[x][y-1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 &&  t[x][y+1] != 3){
                                        t[x][y+1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                        }
                    /*}else{
                        t[x][y] = 0;
                        t[x-1][y] = 1;
                    }*/
                    }
                    else if (y >= 1){   
                        if (t[x][y-1] == 2 || t[x][y-1] == 4){
                            t[x][y-1] = 1;
                            age[x][y-1] = 0;
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 &&  t[x-1][y] != 3){
                                        t[x-1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 &&  t[x+1][y] != 3){
                                        t[x+1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 &&  t[x][y-1] != 3){
                                        t[x][y-1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 &&  t[x][y+1] != 3){
                                        t[x][y+1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                        }}
                    /*}else{
                        t[x][y] = 0;
                        t[x][y+1] = 1;
                    }*/
                    else if (y < HEIGHT-1){
                        if (t[x][y+1] == 2 || t[x][y+1] == 4){
                            t[x][y+1] = 1;
                            age[x][y+1] = 0;
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 &&  t[x-1][y] != 3){
                                        t[x-1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 &&  t[x+1][y] != 3){
                                        t[x+1][y] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 &&  t[x][y-1] != 3){
                                        t[x][y-1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 &&  t[x][y+1] != 3){
                                        t[x][y+1] = 1;
                                    }else{
                                        t[x][y] = 1;
                                    }
                                }
                        }
                    }age[x][y]++;
                    if(age[x][y] >=2){//was 17
                        t[x][y] = 0;
                    }
                } 
                            //System.out.println(num1);
                            //num1 = 0;
                    /*}else{
                        t[x][y] = 0;
                        t[x][y-1] = 1;
                    }*/
                if(t[x][y] == 2){
                    if (x >= 1){
                        if (t[x-1][y] == 3){
                            t[x-1][y] = 2;
                            age[x-1][y] = 0;
                            if(x >= 1 && x < WIDTH-1 && y >= 1 && y < HEIGHT-1){
                                if(t[x+1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;
                                }else if(t[x-1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;//filler
                                }else if(t[x][y-1] == 0){//change t[x][y] depending
                                    t[x][y-1] = 2;
                                }else if(t[x][y+1] == 0){
                                    t[x][y+1] = 2;
                                }
                            }
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 && t[x-1][y] != 2 && t[x-1][y] != 4){
                                        t[x-1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 && t[x+1][y] != 2 && t[x+1][y] != 4){
                                        t[x+1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 && t[x][y-1] != 2 && t[x][y-1] != 4){
                                        t[x][y-1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 && t[x][y+1] != 2 && t[x][y+1] != 4){
                                        t[x][y+1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                        }
                    }
                    else if (x < WIDTH-1){
                        if (t[x+1][y] == 3){
                            t[x+1][y] = 2;
                            age[x+1][y] = 0;
                            if(x >= 1 && x < WIDTH-1 && y >= 1 && y < HEIGHT-1){
                                if(t[x+1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;
                                }else if(t[x-1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;//filler
                                }else if(t[x][y-1] == 0){//change t[x][y] depending
                                    t[x][y-1] = 2;
                                }else if(t[x][y+1] == 0){
                                    t[x][y+1] = 2;
                                }
                            }
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 && t[x-1][y] != 2 && t[x-1][y] != 4){
                                        t[x-1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 && t[x+1][y] != 2 && t[x+1][y] != 4){
                                        t[x+1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 && t[x][y-1] != 2 && t[x][y-1] != 4){
                                        t[x][y-1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 && t[x][y+1] != 2 && t[x][y+1] != 4){
                                        t[x][y+1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                        }
                    }
                    else if (y >= 1){   
                        if (t[x][y-1] == 3){
                            t[x][y-1] = 2;
                            age[x][y-1] = 0;
                            if(x >= 1 && x < WIDTH-1 && y >= 1 && y < HEIGHT-1){
                                if(t[x+1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;
                                }else if(t[x-1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;//filler
                                }else if(t[x][y-1] == 0){//change t[x][y] depending
                                    t[x][y-1] = 2;
                                }else if(t[x][y+1] == 0){
                                    t[x][y+1] = 2;
                                }
                            }
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 && t[x-1][y] != 2 && t[x-1][y] != 4){
                                        t[x-1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 && t[x+1][y] != 2 && t[x+1][y] != 4){
                                        t[x+1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 && t[x][y-1] != 2 && t[x][y-1] != 4){
                                        t[x][y-1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 && t[x][y+1] != 2 && t[x][y+1] != 4){
                                        t[x][y+1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                        }
                    }
                    else if (y < HEIGHT-1){
                        if (t[x][y+1] == 3){
                            t[x][y+1] = 2;
                            age[x][y+1] = 0;
                            if(x >= 1 && x < WIDTH-1 && y >= 1 && y < HEIGHT-1){
                                if(t[x+1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;
                                }else if(t[x-1][y] == 0){//remember to edge
                                    t[x+1][y] = 2;//filler
                                }else if(t[x][y-1] == 0){//change t[x][y] depending
                                    t[x][y-1] = 2;
                                }else if(t[x][y+1] == 0){
                                    t[x][y+1] = 2;
                                }
                            }
                        }else{
                            t[x][y] = 0;
                            int ran = ((int)(Math.random()*4)+1);
                                if(ran == 1){
                                    if (x >= 1 && t[x-1][y] != 1 && t[x-1][y] != 2 && t[x-1][y] != 4){
                                        t[x-1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 2){
                                    if (x < WIDTH-1 && t[x+1][y] != 1 && t[x+1][y] != 2 && t[x+1][y] != 4){
                                        t[x+1][y] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 3){
                                    if (y >= 1 && t[x][y-1] != 1 && t[x][y-1] != 2 && t[x][y-1] != 4){
                                        t[x][y-1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                                if(ran == 4){
                                    if (y < HEIGHT-1 && t[x][y+1] != 1 && t[x][y+1] != 2 && t[x][y+1] != 4){
                                        t[x][y+1] = 2;
                                    }else{
                                        t[x][y] = 2;
                                    }
                                }
                        }
                    }age[x][y]++;
                    if(age[x][y] >=10){
                        t[x][y] = 0;
                    }
                }
                if (t[x][y] == 4){
                    if(y >= 1){
                        if(moveDir == 0){
                            if (t[x][y-1] == 3){
                                score++;
                            }
                            t[x][y] = 0;
                            t[x][y-1] = 4;
                        }
                    }    
                    if(y < HEIGHT-1){    
                        if(moveDir == 1){
                            if (t[x][y+1] == 3){
                                score++;
                            }
                            t[x][y] = 0;
                            t[x][y+1] = 4;
                            moveDir = -1;
                        }
                    }
                    if(x >= 1){
                        if(moveDir == 2){
                            if (t[x-1][y] == 3){
                                score++;
                            }
                            t[x][y] = 0;
                            t[x-1][y] = 4;
                        }
                    }
                    if(x < WIDTH-1){
                        if(moveDir == 3){
                            if (t[x+1][y] == 3){
                                score++;
                            }
                            t[x][y] = 0;
                            t[x+1][y] = 4;
                            moveDir = -1;
                    }
                }
                    System.out.println(score);
            }
        }
    }
}            
    private void checkLoss(){
        int w = 0;
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                if(t[x][y] == 4){
                    w++;
                }
            }
        }
        if(w == 0){
            System.out.println(score);
            System.exit(0);
        }
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
                        r.setColor(Color.RED);
                    }
                    else if(t[x][y] == 2){
                        r.setColor(Color.YELLOW);
                    }
                    else if(t[x][y] == 3){
                        r.setColor(Color.BLUE);
                    }
                    else if(t[x][y] == 4){
                        r.setColor(Color.BLACK);
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
        new PredatorVsPrey2();
    }

    @Override
    public void keyTyped(KeyEvent e) {
       
    }

    @Override
    public void keyPressed(KeyEvent e) {
       if(e.getKeyCode()==KeyEvent.VK_UP){
           moveDir = 0;
           hm = true;
       }
       if(e.getKeyCode()==KeyEvent.VK_DOWN){
           moveDir = 1;
           hm = true;
       }
       if(e.getKeyCode()==KeyEvent.VK_LEFT){
           moveDir = 2;
           hm = true;
       }
       if(e.getKeyCode()==KeyEvent.VK_RIGHT){
           moveDir = 3;
           hm = true;
       }
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

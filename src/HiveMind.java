package pkg06d.hivemind;
//fix out of bounds
//for now do nearest d[x][y] finding
import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class HiveMind extends JFrame implements KeyListener, MouseListener{
    
    private final int WIDTH = 100, HEIGHT = 100;
    private int mouseX = 0, mouseY = 0; 
    private boolean loop = false;
    private final int SCALE = 8;
    
    boolean t[][] = new boolean[WIDTH][HEIGHT];
    boolean d[][] = new boolean[WIDTH][HEIGHT];
    
    public HiveMind(){
        super("HiveMind");
        setSize(SCALE*WIDTH, SCALE*HEIGHT+22);
        setVisible(true);
        requestFocusInWindow();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addKeyListener(this);
        addMouseListener(this);
        gen();
        loop = true;
        while(true){
            this.repaint();
            updateMouse();
            update();
            try{
                Thread.sleep(0);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private void gen(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(Math.random() < .1){
                    t[x][y] = true;
                }
            }
        }
        buildGen();
    }
    private void moveUp(int x, int y){
        if(y >= 1){
            t[x][y-1] = true;
            t[x][y] = false;
        }
    }
    private void moveDown(int x, int y){
        if(y < HEIGHT){
            t[x][y+1] = true;
            t[x][y] = false;
        }
    }
    private void moveLeft(int x, int y){
        if(x >= 1){
            t[x-1][y] = true;
            t[x][y] = false;
        }
    }
    private void moveRight(int x, int y){
        if(y < WIDTH){
            t[x+1][y] = true;
            t[x][y] = false;
        }
    }
    private int[] findNearest(int sx, int sy){
        double n = WIDTH*HEIGHT;
        int[] cxy = new int[2];
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(d[x][y] && !t[x][y]){
                    if(Math.sqrt(Math.pow((sx-x),2)+Math.pow((sy-y),2)) < n){
                        n = Math.sqrt(Math.pow((sx-x),2)+Math.pow((sy-y),2));
                        cxy[0] = x;
                        cxy[1] = y;
                    }
                }
            }
        }return cxy;
    }
    private void formation(int px, int py, int cx, int cy){
        while(!d[px][py]){//can change to if for one by one movement
            if(py >= 1){
                if(py > cy){
                    moveUp(px, py);
                    py--;
                }
            }
            if(py < HEIGHT){
                if(py < cy){
                    moveDown(px, py);
                    py++;
                }
            }
            if(px >= 1){
                if(px > cx){
                    moveLeft(px, py);
                    px--;
                }
            }
            if(px < WIDTH){
                if(px < cx){
                    moveRight(px, py);
                    px++;
                }
            }
        }
    }
    private void moveIn(int x, int y){
        int cx = findNearest(x, y)[0];
        int cy = findNearest(x, y)[1];
        formation(x, y, cx, cy);
    }
    private void buildGen(){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(Math.random() < .004){
                    d[x][y] = true;
                    if(y >= 2){
                        d[x][y-2] = true;
                    }
                    if(y >= 1){
                        d[x][y-1] = true;
                    }
                    if(y < HEIGHT-2){
                        d[x][y+2] = true;
                    }
                    if(y < HEIGHT-1){
                        d[x][y+1] = true;
                    }
                    if(x >= 2){
                        d[x-2][y] = true;
                    }
                    if(x >= 1){
                        d[x-1][y] = true;
                    }
                    if(x < WIDTH-2){
                        d[x+2][y] = true;
                    }
                    if(x < WIDTH-1){
                        d[x+1][y] = true;
                    }
                    if(x >= 2 && y >= 2){
                        d[x-2][y-2] = true;
                        d[x-2][y-1] = true;
                        d[x-1][y-2] = true;
                        d[x-1][y-1] = true;
                    }
                    if(x < WIDTH-2 && y < HEIGHT-2){
                        d[x+2][y+2] = true;
                        d[x+2][y+1] = true;
                        d[x+1][y+2] = true;
                        d[x+1][y+1] = true;
                    }
                    if(x >= 2 && y < HEIGHT-2){
                        d[x-2][y+2] = true;
                        d[x-2][y+1] = true;
                        d[x-1][y+2] = true;
                        d[x-1][y+1] = true;
                    }
                    if(x < WIDTH-2 && y >= 2){
                        d[x+2][y-2] = true;
                        d[x+2][y-1] = true;
                        d[x+1][y-2] = true;
                        d[x+1][y-1] = true;
                    }
                }
            }
        }
    }
    public void paint(Graphics g){
        if(loop){
            BufferedImage B = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics r = B.getGraphics();
            
            for (int x = 0; x < WIDTH; x++){
                for (int y = 0; y < HEIGHT; y++){
                    if(t[x][y]){
                        int a = (int)((Math.random()*220)+30);
                        r.setColor(new Color(a, a, a));
                    }else if(d[x][y]){
                        r.setColor(Color.PINK);
                    }else{
                        r.setColor(Color.BLACK);
                    }
                    r.fillRect(x*SCALE, y*SCALE, SCALE, SCALE);
                }
            }
            g.drawImage(B, 0, 22, this);
        }
    }
    
    private void updateMouse(){
        mouseX=(MouseInfo.getPointerInfo().getLocation().x-getLocationOnScreen().x)/SCALE;
        mouseY=((MouseInfo.getPointerInfo().getLocation().y-getLocationOnScreen().y)-22)/SCALE;
    }
    
    public static void main(String[] args) {
        new HiveMind();
    }
    int k = 0;
    private void update(){
        k++;
        System.out.println(k);
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                moveIn(x, y);
            }
        }
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
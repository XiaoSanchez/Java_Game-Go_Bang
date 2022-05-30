import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class ChessMap extends JFrame {
	private ImageIcon map;				
	private ImageIcon blackchess;		
	private ImageIcon whitechess;		
	private ChessPanel cp;				
	private JPanel east;
	private JPanel west;
	private static final int FINAL_WIDTH = 450;
	private static final int FINAL_HEIGHT = 500;
	private JMenuBar menubar;			
	private JMenu[] menu= {new JMenu("Start"),new JMenu("Setting"),new JMenu("Help")};
	private JMenuItem[] menuitem1= {new JMenuItem("Restart"),new JMenuItem("Regret"),new JMenuItem("Exit")};
	private JMenuItem[] menuitem2= {new JMenuItem("Forbidden"),new JMenuItem("PVE"),new JMenuItem("PVP")};
	private JMenuItem[] menuitem3= {new JMenuItem("Rules"),new JMenuItem("About")};
	private boolean haveai=true;
	Mouseclicked mouseclicked=new Mouseclicked();
	MouseMoved mousemoved=new MouseMoved();
	Menuitemclicked menuclicked=new Menuitemclicked();
	public ChessMap() {
		Font font = new Font("Dialog", Font.PLAIN, 12);
		Enumeration keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof javax.swing.plaf.FontUIResource) {
				UIManager.put(key, font);
			}
		}
		setTitle("GoBang");
		setSize(FINAL_WIDTH,FINAL_HEIGHT);
		setResizable(false);
		init();
		setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2
				- FINAL_WIDTH / 2, Toolkit.getDefaultToolkit()
				.getScreenSize().height
				/ 2 - FINAL_HEIGHT / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp.reset();	
		setVisible(true);
	}
 	public void init() {
		map=new ImageIcon(getClass().getResource("bg.jpg"));
		blackchess=new ImageIcon(getClass().getResource("blackchess.gif"));
		whitechess=new ImageIcon(getClass().getResource("whitechess.gif"));
		cp=new ChessPanel(map,blackchess,whitechess);
		menubar=new JMenuBar();
		menuitem1[0].setActionCommand("Restart");
		menuitem1[1].setActionCommand("Rollback");
		menuitem1[2].setActionCommand("Exit");
		menuitem2[0].setActionCommand("Forbid");
		menuitem2[1].setActionCommand("Robot");
		menuitem2[2].setActionCommand("Human");
		menuitem3[0].setActionCommand("Rule");
		menuitem3[1].setActionCommand("About");
		for(int i=0;i<3;i++)
			menu[0].add(menuitem1[i]);
		for(int i=0;i<3;i++)
			menu[1].add(menuitem2[i]);
		for(int i=0;i<2;i++)
			menu[2].add(menuitem3[i]);
		for(int i=0;i<3;i++)
			menubar.add(menu[i]);
		Container p = getContentPane();
		setJMenuBar(menubar);
		east = new JPanel();
		west = new JPanel();
		p.add(east, "East");
		p.add(west, "West");
		p.add(cp, "Center");
		cp.addMouseListener(mouseclicked);
		cp.addMouseMotionListener(mousemoved);
		menuitem1[0].addActionListener(menuclicked);
		menuitem1[1].addActionListener(menuclicked);
		menuitem1[2].addActionListener(menuclicked);
		menuitem2[0].addActionListener(menuclicked);
		menuitem2[1].addActionListener(menuclicked);
		menuitem2[2].addActionListener(menuclicked);
		menuitem3[0].addActionListener(menuclicked);
		menuitem3[1].addActionListener(menuclicked);
	}
	class Mouseclicked extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
		  if(cp.win==false) {
			    if(haveai) {           
    		              Point p1=new Point();
    		              p1=cp.getPoint(e.getX(),e.getY());
    		              int x=p1.x;
    		              int y=p1.y;
    		              System.out.println("x="+x+",y="+y);
                          if (cp.isChessOn[x][y] != 2)
                                     return;
                          if( cp.able_flag && cp.bw == 0) {
             	                 int type = cp.getType(x,y,cp.bw);
             	                 String str = null;
             	                 switch(type) {
             		             case 20: 
             			               str = "Long Even Forbidden!";
             			               break;
             		             case 21:
             			               str = "Four Four Forbidden!";
             			               break;
             		             case 22: 
             			               str = "Three Three Forbidden!";
             			               break;
             		             default : break;
             	                 }
             	                 if(str != null) {
             		                    JOptionPane.showMessageDialog(null,str);
             		                     return;
             	                 }
     		              }
                          boolean flag=cp.haveWin(x, y, cp.bw);
                          cp.update( x, y );
                         if( cp.chess_num == 1) {  
                      	 if(x-1>=0)
                     	          cp.x_min = x-1;
                         if(x-1<=15)
                     	          cp.x_max = x+1;
                         if(y-1>=0)
                     	          cp.y_min = y-1;
                         if(y-1<=15)
                     	          cp.y_max = y+1;
                  }
                 else 
                 	cp.resetMaxMin(x,y);
                 if (flag) {
                     cp.wined(1 - cp.bw);
                     return;
                 }
                 cp.putOne(cp.bw);
			} else {                                        
				Point p1=new Point();
 		        p1=cp.getPoint(e.getX(),e.getY());
 		        int x=p1.x;
 		        int y=p1.y;
 		        System.out.println("x="+x+",y="+y);
                 if (cp.isChessOn[x][y] != 2)
                             return;
                 if( cp.able_flag && cp.bw == 0) {
          	           int type = cp.getType(x,y,cp.bw);
          	           String str = null;
          	           switch(type) {
          		       case 20: 
          			       str = "Long Even Forbidden!";
          			       break;
          		       case 21:
          			       str = "Four Four Forbidden!";
          			       break;
          		       case 22: 
          			       str = "Three Three Forbidden!";
          			       break;
          		       default : break;
          	           }
          	           if(str != null) {
          		               JOptionPane.showMessageDialog(null,str);
          		               return;
          	           }
  		       }
                boolean flag=cp.haveWin(x, y, cp.bw);
                cp.update( x, y );
                cp.repaint();
              if( cp.chess_num == 1) {  
              	if(x-1>=0)
                  	cp.x_min = x-1;
                  if(x-1<=15)
                  	cp.x_max = x+1;
                  if(y-1>=0)
                  	cp.y_min = y-1;
                  if(y-1<=15)
                  	cp.y_max = y+1;
              }
              else 
              	cp.resetMaxMin(x,y);
              if (flag) {
                  cp.wined(1 - cp.bw);
                  return;
              }
			}
    	} 
		}
	}
	class MouseMoved implements MouseMotionListener	{
		public void mouseMoved(MouseEvent e) {
    		cp.showMousePos(e.getPoint());
    	}
    	public void mouseDragged(MouseEvent e) {}
	}
	class Menuitemclicked implements ActionListener	{
		public void actionPerformed(ActionEvent e) {
      		JMenuItem target = (JMenuItem)e.getSource();
      		String actionCommand = target.getActionCommand();
      		if(actionCommand.equals("Restart")) { 		
        	   cp.reset();	
        	   if(cp.sbw==cp.WHITE_ONE)
        		   cp.update(7, 7);
      		}
      		if(actionCommand.equals("Rollback")) { 		
      			if(cp.win) {
        			JOptionPane.showMessageDialog(null,"Game Ended, Please Restart!");
        			return;
                }
        		if(cp.chess_num >= 2 && cp.bw == cp.sbw) {
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.isChessOn[cp.pre[cp.chess_num-2][0]][cp.pre[cp.chess_num-2][1]] = 2;
        			cp.chess_num -= 2;
        			cp.repaint();
        		}
        		else if(cp.chess_num >= 1 && cp.bw == 1-cp.sbw) {
        			cp.isChessOn[cp.pre[cp.chess_num-1][0]][cp.pre[cp.chess_num-1][1]] = 2;
        			cp.chess_num --;
       				cp.repaint();
       			}
      		}
      		else if(actionCommand.equals("Exit")) { 		
        		System.exit(1);	
      		}
      		else if(actionCommand.equals("Forbid")) {     
        		Object[] options = { "No Forbidden", "Forbidden" };
        		int sel = JOptionPane.showOptionDialog(
          				null, "Your Pick:", "Forbidden",
          				JOptionPane.DEFAULT_OPTION,
          				JOptionPane.QUESTION_MESSAGE, null,
          				options, options[0]);
          		if(sel==1) {
                        cp.able_flag=true;
                        System.out.println("Forbidden");
          		} else {
          			    cp.able_flag=false;
                        System.out.println("No Forbidden");
          		}
          	}
      		else if(actionCommand.equals("Robot")) {            
      			haveai=true;
      			Object[] options = { "Player First", "AI First" };
        		int sel = JOptionPane.showOptionDialog(
          				null, "Your Pick:", "Side Pick",
          				JOptionPane.DEFAULT_OPTION,
          				JOptionPane.QUESTION_MESSAGE, null,
          				options, options[0]);
          		if(sel==1) {       
          			    cp.sbw=cp.WHITE_ONE;
          			    cp.update(7, 7);
          			    System.out.println("AI First");
          		} else {             
          			    cp.sbw=cp.BLACK_ONE;
          			    System.out.println("Player First");
          		}
      		}
          	else if(actionCommand.equals("Human")) { 		
        		haveai=false;	
        		cp.setHumanhuman(true);
      		} else if(actionCommand.equals("Rule")) {          
      			JOptionPane.showConfirmDialog(null,
      			" First forms five consecutive pieces of the same color on the horizontal, vertical, and diagonal directions of the board is the winner." +"\n"+
				" Forbidden:" +"\n"+
				" Black has a banned hand and loses (Lose), and white has no banned hand. Black chess banned players include:" + "\n"+
				" Double Three, (including four, three, three), " + "\n"+
				" Double Four, (including four, four, three), " + "\n"+
				" and long even. Black can only win by four or three." ,"Rule",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);
      		}
      		else if(actionCommand.equals("About")) { 		
        		JOptionPane.showConfirmDialog(null,"Creator: \n" + "Shawn   https://github.com/XiaoSanchez\n","About",JOptionPane.CLOSED_OPTION,JOptionPane.INFORMATION_MESSAGE);	
      		}
    	}
	}
  public static void main(String[] args) {
	    new ChessMap();	
  }
}
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class slotMachine implements ActionListener, MouseListener, KeyListener {

	static JFrame JF;
	static slotMachine SM;
	static slotPanel SP = new slotPanel();
	// 視窗大小設定
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	// 創建滾輪
	slotRoll r1 = new slotRoll(1);
	slotRoll r2 = new slotRoll(2);
	slotRoll r3 = new slotRoll(3);
	// 創建展示用滾輪
	public int[] viewRoll1 = new int[3];
	public int[] viewRoll2 = new int[3];
	public int[] viewRoll3 = new int[3];
	// 操作用判斷變數
	public boolean rollStart1 = false;
	public boolean rollStart2 = false;
	public boolean rollStart3 = false;
	public boolean gameStart;
	public boolean allStop = false;
	public int allStopCount;
	// 滾輪速度
	public int ticks;
	// 滾輪轉動計數用
	public int rollNext1 = 0;
	public int rollNext2 = 0;
	public int rollNext3 = 0;
	public int press = 0;
	// 計分用
	public int money = 100;
	public int score;
	public int bet =1;

	slotMachine() {

		JF = new JFrame("Slot Machine Simulator");
		JF.setSize(WIDTH, HEIGHT);
		JF.setLocation(100, 100);
		JF.setVisible(true);
		JF.setResizable(false);
		JF.setAlwaysOnTop(true);
		JF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JF.add(SP);
		JF.addMouseListener(this);
		JF.addKeyListener(this);
		// 每50m 事件監聽調用 actionPerformed 方法 一次;
		Timer timer = new Timer(20, this);
		timer.start();

	}

	public static void main(String[] args) {

		SM = new slotMachine();

	}

	public void myPaint(Graphics g) {
		// 畫背景
		g.setColor(Color.GRAY);
		g.fillRect(100, 20, 420, 500);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(120, 40, 380, 460);
		g.setColor(Color.yellow);
		g.setFont(new Font("",1,50));
		g.drawString("Lucky Slot",182, 82);
		g.setColor(Color.red);
		g.setFont(new Font("",1,50));
		g.drawString("Lucky Slot",180, 80);
		if(score > 0 && ticks %5==0) {
			g.setColor(Color.white);
			g.setFont(new Font("",1,50));
			g.drawString("Lucky Slot",180, 80);
		}
		
		g.setColor(Color.GRAY.darker());
		g.fillRect(100, 520, 420, 200);
		
		g.setColor(Color.black);
		g.fillRect(130, 540, 360, 160);
		
		g.setColor(Color.white);
		g.setFont(new Font("標楷體",1,30));
		g.drawString("右鍵 or G鍵 開始",190, 570);
		g.drawString("左　鍵 手動鎖定",200, 610);
		g.drawString("空白鍵 自動鎖定",200, 650);
		g.drawString("上下鍵 下注加減",200, 690);
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(520, 300, 50, 70);
		g.setColor(Color.black);
		g.fillRect(530, 305, 30, 40);
		g.setColor(Color.GRAY);
		g.fillRect(535, 100, 20, 230);
		g.setColor(Color.white);
		g.fillRect(545, 110, 5, 220);
		g.setColor(Color.red.darker());
		g.fillOval(522, 70, 45, 45);
		g.setColor(Color.red);
		g.fillOval(545, 75, 15, 15);
		
		// 畫標線
		g.setColor(Color.black);
		g.drawRect(144, 94, 332, 312);
		g.drawRect(145, 95, 330, 310);
		g.setColor(Color.black);
		g.drawLine(145, 249, 475, 249);
		g.drawLine(145, 250, 475, 250);
		g.drawLine(145, 251, 475, 251);

		// 畫滾輪
		g.setColor(Color.white);
		g.fillRect(150, 100, 100, 100);
		g.fillRect(260, 100, 100, 100);
		g.fillRect(370, 100, 100, 100);
		SM.image(viewRoll1[0], 150, 100, g);
		SM.image(viewRoll2[0], 260, 100, g);
		SM.image(viewRoll3[0], 370, 100, g);

		g.setColor(Color.white);
		g.fillRect(150, 200, 100, 100);
		g.fillRect(260, 200, 100, 100);
		g.fillRect(370, 200, 100, 100);
		SM.image(viewRoll1[1], 150, 200, g);
		SM.image(viewRoll2[1], 260, 200, g);
		SM.image(viewRoll3[1], 370, 200, g);

		g.setColor(Color.white);
		g.fillRect(150, 300, 100, 100);
		g.fillRect(260, 300, 100, 100);
		g.fillRect(370, 300, 100, 100);
		SM.image(viewRoll1[2], 150, 300, g);
		SM.image(viewRoll2[2], 260, 300, g);
		SM.image(viewRoll3[2], 370, 300, g);

		SM.drawAlpha(g);

		g.setColor(Color.black);
		g.fillRect(144, 94, 332, 35);
		g.fillRect(144, 370, 332, 35);

		SM.drawMonemy(money,score, g);

	}

	public void drawAlpha(Graphics g) {

		Graphics2D g4 = (Graphics2D) g;
		g4.setPaint(new GradientPaint(260, 130, new Color(0, 0, 0, 128), 260, 200, new Color(255, 255, 255, 128)));
		g4.fillRect(150, 100, 100, 100);
		g4.fillRect(260, 100, 100, 100);
		g4.fillRect(370, 100, 100, 100);

		g4.setPaint(new GradientPaint(260, 400, new Color(0, 0, 0, 128), 260, 330, new Color(255, 255, 255, 128)));
		g.fillRect(150, 300, 100, 100);
		g.fillRect(260, 300, 100, 100);
		g.fillRect(370, 300, 100, 100);

	}

	public void image(int getRoll, int x, int y, Graphics g) {

		switch (getRoll) {
		case 1:
			g.setColor(Color.green.darker());
			g.setFont(new Font("標楷體", 1, 45));
			g.drawString("人", x + 30, y + 55);
			g.setColor(Color.red);
			g.fillOval(x + 15, y + 45, 30, 30);
			g.fillOval(x + 53, y + 53, 30, 30);
			g.setColor(Color.white);
			g.fillOval(x + 30, y + 50, 5, 5);
			g.fillOval(x + 70, y + 60, 5, 5);
			break;
		case 4:
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2));
			g.setColor(Color.orange);
			g.fillOval(x + 10, y + 10, 80, 80);
			g.setColor(Color.white);
			g.drawOval(x + 13, y + 13, 74, 74);
			g2.drawLine(x + 13, y + 50, x + 87, y + 50);
			g2.drawLine(x + 50, y + 13, x + 50, y + 87);
			g2.drawLine(x + 25, y + 25, x + 75, y + 75);
			g2.drawLine(x + 75, y + 25, x + 25, y + 75);

			break;
		case 15:
			g.setColor(Color.green.darker());
			g.setFont(new Font("標楷體", 1, 30));
			g.drawString("π", x + 35, y + 30);
			g.setColor(Color.MAGENTA.darker());
			g.fillOval(x + 20, y + 30, 20, 20);
			g.fillOval(x + 40, y + 30, 20, 20);
			g.fillOval(x + 60, y + 30, 20, 20);
			g.fillOval(x + 30, y + 48, 20, 20);
			g.fillOval(x + 50, y + 48, 20, 20);
			g.fillOval(x + 40, y + 66, 20, 20);

			break;
		case 50:

			int[] xpoly = { x + 30, x + 70, x + 85, x + 50, x + 15 };
			int[] ypoly = { y + 20, y + 20, y + 35, y + 80, y + 35 };

			Polygon poly = new Polygon(xpoly, ypoly, xpoly.length);
			Graphics2D g3 = (Graphics2D) g;
			g3.setStroke(new BasicStroke(3));
			g.setColor(Color.CYAN);
			g.fillPolygon(poly);
			g.setColor(Color.black);
			g.drawPolygon(poly);
			g.drawLine(x + 85, y + 35, x + 15, y + 35);
			g.drawLine(x + 70, y + 21, x + 50, y + 80);
			g.drawLine(x + 30, y + 21, x + 50, y + 80);
			g.drawLine(x + 50, y + 21, x + 33, y + 35);
			g.drawLine(x + 50, y + 21, x + 66, y + 35);

			break;
		case 200:
			g.setColor(Color.black);
			g.fillRect(x, y + 25, 100, 50);
			g.setColor(Color.white);
			g.drawRect(x + 5, y + 30, 90, 40);
			g.setColor(Color.white);
			g.setFont(new Font("Lobster", 1, 36));
			g.drawString("BAR", x + 10, y + 65);

			break;
		case 1000:
			g.setColor(Color.black);
			g.setFont(new Font("Lobster", 1, 120));
			g.drawString("7", x + 22, y + 95);
			g.setColor(Color.red);
			g.setFont(new Font("Lobster", 1, 120));
			g.drawString("7", x + 17, y + 92);

			break;

		}

	}

	public void drawMonemy(int money,int score, Graphics g) {

		//畫餘額
		String u = String.valueOf(money % 10);
		String t = String.valueOf(money / 10 % 10);
		if(money<10) t="";
		String h = String.valueOf(money / 100 % 10);
		if(money<100) h="";
		String k = String.valueOf(money / 1000 % 10);
		if(money<1000) k="";
		String w = String.valueOf(money /10000 %10);
		if(money<10000) w="";
		
		g.setColor(Color.orange);
		g.fillRect(140, 416, 138, 58);

		g.setColor(Color.black);
		g.fillRect(144, 420, 130, 50);	
		g.setColor(Color.red);
		g.setFont(new Font("", 1, 35));
		g.drawString(u, 250, 455);
		g.drawString(t, 230, 455);
		g.drawString(h, 210, 455);
		g.drawString(k, 190, 455);
		g.drawString(w, 170, 455);
		g.drawString("$", 150, 455);
		
		//畫中獎金額
		String u1 = String.valueOf(score % 10);
		String t1 = String.valueOf(score / 10 % 10);
		if(score <10) t1="";
		String h1 = String.valueOf(score / 100 % 10);
		if(score <100) h1="";
		String k1 = String.valueOf(score / 1000 % 10);
		if(score <1000) k1="";
		

		g.setColor(Color.orange);
		g.fillRect(340, 416, 138, 58);
		
		if(score >0 && ticks%5 ==0) {
			g.setColor(Color.cyan);
			g.fillRect(340, 416, 138, 58);
		}
	
		g.setColor(Color.black);
		g.fillRect(344, 420, 130, 50);	
		g.setColor(Color.red);
		g.setFont(new Font("", 1, 35));
		g.drawString(u1, 450, 455);
		g.drawString(t1, 430, 455);
		g.drawString(h1, 410, 455);
		g.drawString(k1, 390, 455);
		g.setFont(new Font("", 1, 20));
		g.drawString("WIN", 350, 445);
		if(score >0 && ticks%8 ==0) {
			g.setColor(Color.orange);
			g.drawString("WIN", 350, 445);

		}
		
		//畫賭率 bet
		String u2 = String.valueOf(bet % 10);
		String t2 = String.valueOf(bet / 10 % 10);
		if(bet <10) t2="";
		g.setColor(Color.orange);
		g.fillRect(283, 416, 52, 58);
		g.setColor(Color.black);
		g.fillRect(286, 419, 46, 52);	
		g.setColor(Color.red);
		g.setFont(new Font("", 1, 28));
		g.drawString(u2, 310, 465);
		g.drawString(t2, 290, 465);
		g.setFont(new Font("", 1, 18));
		g.drawString("BET", 290, 435);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		ticks++;
		// 開始場設定
		if (!gameStart) {
			viewRoll1[0] = r1.getRoll()[rollNext1 % 16 + 2];
			viewRoll1[1] = r1.getRoll()[rollNext1 % 16 + 1];
			viewRoll1[2] = r1.getRoll()[rollNext1 % 16 + 0];
			System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
			viewRoll2[0] = r2.getRoll()[rollNext2 % 16 + 2];
			viewRoll2[1] = r2.getRoll()[rollNext2 % 16 + 1];
			viewRoll2[2] = r2.getRoll()[rollNext2 % 16 + 0];
			System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
			viewRoll3[0] = r3.getRoll()[rollNext3 % 16 + 2];
			viewRoll3[1] = r3.getRoll()[rollNext3 % 16 + 1];
			viewRoll3[2] = r3.getRoll()[rollNext3 % 16 + 0];
			System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
		}

		if (allStop) {

			if (ticks % 6 == 0) {
				allStopCount++;
			}

			switch (allStopCount) {
			case 0:
			case 1:
				rollStart1 = false;
				break;
			case 2:
				rollStart2 = false;
				break;
			case 3:
				rollStart3 = false;
				break;
			}
			if (allStopCount >= 3)
				allStopCount = 0;

			if (!rollStart1 && !rollStart2 && !rollStart3) {

				allStop = false;
				score = slotRoll.odds(viewRoll1[1], viewRoll2[1], viewRoll3[1]) *bet;
				
				allStopCount = 0;

			}

		}

		// 滾輪判讀區
		if (rollStart1) {
			if (ticks % 3 == 0) {
				viewRoll1[0] = r1.getRoll()[rollNext1 % 16 + 2];
				viewRoll1[1] = r1.getRoll()[rollNext1 % 16 + 1];
				viewRoll1[2] = r1.getRoll()[rollNext1 % 16 + 0];
				System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
				rollNext1++;
			}

		}
		if (rollStart2) {
			if (ticks % 3 == 0) {
				viewRoll2[0] = r2.getRoll()[rollNext2 % 16 + 2];
				viewRoll2[1] = r2.getRoll()[rollNext2 % 16 + 1];
				viewRoll2[2] = r2.getRoll()[rollNext2 % 16 + 0];
				System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
				rollNext2++;
			}

		}
		if (rollStart3) {
			if (ticks % 3 == 0) {
				viewRoll3[0] = r3.getRoll()[rollNext3 % 16 + 2];
				viewRoll3[1] = r3.getRoll()[rollNext3 % 16 + 1];
				viewRoll3[2] = r3.getRoll()[rollNext3 % 16 + 0];
				System.out.println(viewRoll1[1] + "  " + viewRoll2[1] + "  " + viewRoll3[1]);
				rollNext3++;
			}

		}

		SP.repaint();

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent e) {

		int c = e.getButton();
		if ((c == MouseEvent.BUTTON1) && rollStart3) {

			gameStart = true;

			switch (press % 3) {

			case 0:
				rollStart1 = false;
				rollStart2 = true;
				rollStart3 = true;
				break;
			case 1:
				rollStart1 = false;
				rollStart2 = false;
				rollStart3 = true;
				break;
			case 2:
				rollStart1 = false;
				rollStart2 = false;
				rollStart3 = false;

				allStop = true;
				

				break;

			}
			press++;

		}

		if ((c == MouseEvent.BUTTON3) && (rollStart3 == false) && (money >= bet) ) {
			//關自動停
			allStop = false;
			//全滾輪啟動
			rollStart1 = true;
			rollStart2 = true;
			rollStart3 = true;

			//扣餘額
			if (money > 0) {
				money = money + score;
				score = 0;
		
				money -=bet;
		
				
			}

		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if ((e.getKeyCode() == KeyEvent.VK_SPACE) && rollStart3 == true) {

			allStop = true;

		}

		if ((e.getKeyCode() == KeyEvent.VK_G) && (rollStart3 == false) && (money >= bet)) {
			//遊戲開始
			gameStart = true;
			//關自動停
			allStop = false;

			//全滾輪啟動
			rollStart1 = true;
			rollStart2 = true;
			rollStart3 = true;
			//扣餘額
			if (money > 0) {
				
				money = money + score;
				score = 0;
				money -=bet;

			}

		}
		
		if((e.getKeyCode() == KeyEvent.VK_UP)&& (rollStart3 == false)) {
			if(bet<10) {
				bet++;
			}		
		}
		if((e.getKeyCode() == KeyEvent.VK_DOWN)&& (rollStart3 == false)) {
			if(bet >1) {
				bet--;
			}		
		}
		if((e.getKeyCode() == KeyEvent.VK_M)&& (rollStart3 == false)) {
			money +=10;
		}

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

//end	
}

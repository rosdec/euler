package eulerdiagram;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.Iterator;

import javax.swing.JApplet;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUIWF extends JApplet {

	private static final long serialVersionUID = 2636032410969407421L;
	private static final double smallestH = 20.0f;
	private EDWF<Item> ed;
	private boolean isWF= true;
	private Ellipse curEllipse;
	private Ellipse tmpEllipse;
	private double startX;
	private double startY;
	private double h = smallestH;
	private int eName=0;

	private JTextArea textArea;
	//private  PrintStream tee;
	
	public GUIWF() {
		
		
		ed = new EDWF<Item>();

		Container container = getContentPane();
	    //container.setBackground(Color.pink);
	    container.setLayout(new BorderLayout());

	   
	    textArea = new JTextArea(12,40);
	    textArea.setEditable(false);
	   // tee = new myStream(textArea,System.out);
	    
       // ed.setTee(tee);
        
      
	    container.add(new JScrollPane(textArea),BorderLayout.PAGE_END);

		
		
		class ClickListener extends MouseAdapter {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && !e.isShiftDown()) {
					startX = e.getX();
					startY = e.getY();
					if (e.isAltDown()){
						Ellipse eOld = ed.getClosestEllipseAtPoint(new PointXY(startX, startY));
						if (eOld!=null && ed.isRemovable(eOld)){
							System.out.println("Ellisse da spostare"+ eOld.getName());
							ed.remove(eOld);
							tmpEllipse = new Ellipse(eOld);
							curEllipse = new Ellipse(eOld);
							double t = eOld.getT(new PointXY(e.getX(),e.getY()));
							PointXY end= eOld.getPoint(t);
							PointXY start=eOld.getPoint(t+Math.PI);
							PointXY left=eOld.getPoint(t+0.5*Math.PI);
							PointXY right=eOld.getPoint(t-0.5*Math.PI);
							h=left.distance(right)/2.0;
							curEllipse.setD(h);
							curEllipse.assignSecondEdge(start.getX(), start.getY(),  end.getX(), end.getY());
							startX = start.getX();
							startY = start.getY();
						}
					}
					else	
						curEllipse = new Ellipse(startX, startY, 2.0, 2.0, 0.0);
					repaint();
				}

				if (e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown()) {
					startX = e.getX();
					startY = e.getY();
					Ellipse eOld = ed.getClosestEllipseAtPoint(new PointXY(startX, startY));
					if (eOld!=null && ed.isRemovable(eOld)){
						startX = eOld.getA()-e.getX();
						startY = eOld.getB()-e.getY();
						System.out.println("Ellisse da spostare"+ eOld.getName());
						ed.remove(eOld);
						curEllipse=eOld;
						tmpEllipse=new Ellipse(curEllipse);
					}
					repaint();
				}	
				
				if (e.getButton() == MouseEvent.BUTTON2) {
					startX = e.getX();
					startY = e.getY();
					String s=ed.getZoneNameAtPoint(new PointXY(startX, startY));
					System.out.println(s);
					ed.selZoneAtPoint(new PointXY(startX, startY));
					repaint();
				}
				
				if (e.getButton() == MouseEvent.BUTTON3) {
					startX = e.getX();
					startY = e.getY();
					Ellipse eOld = ed.getClosestEllipseAtPoint(new PointXY(startX, startY));
					if (eOld!=null){
						System.out.println("Ellisse da rimuovere "+ eOld.getName());
						ed.remove(eOld);
						repaint();
					}
				}	

			}

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && !e.isShiftDown() && !e.isAltDown()) {
					if (curEllipse.getC()>smallestH/2){
						curEllipse.setName("" + (char) (eName++ + 65));
						ed.add(curEllipse);
					}
				}
				if (e.getButton() == MouseEvent.BUTTON1 && (e.isShiftDown() || e.isAltDown())) {
					if (curEllipse.getC()<=smallestH/2 || !ed.add(curEllipse))
						ed.add(tmpEllipse);
				}
				
				curEllipse = null;
				h = smallestH;
				repaint();
			}

			public void mouseWheelMoved(MouseWheelEvent e) {
				if(!e.isShiftDown())
					if (curEllipse != null) {
						h += e.getWheelRotation() * 10;
						if (h < smallestH)
							h = smallestH;
						curEllipse.setAll(curEllipse.getA(), curEllipse.getB(),
								curEllipse.getC(), h, curEllipse.getPhi());
						isWF=ed.isAddable(curEllipse);
						repaint();
					}
			}
		}

		class MovementListener extends MouseMotionAdapter {
			public void mouseDragged(MouseEvent e) {
				if(!e.isShiftDown()){	
					double endX = e.getX();
					double endY = e.getY();
					double tmpw, tmpCenterX, tmpCenterY;
	
					tmpw = Math.sqrt((double) (endX - startX) * (endX - startX)
							+ (endY - startY) * (endY - startY));
	
					tmpCenterX = (startX + endX) * 0.5;
					tmpCenterY = (startY + endY) * 0.5;
	
					double phi = (double) Math.atan2(endY - startY, endX - startX);
	
					if(curEllipse!= null){
						curEllipse.setAll(tmpCenterX, tmpCenterY, tmpw/2.0, h, phi);
						isWF=ed.isAddable(curEllipse);
					}
				}
				if(e.isShiftDown()){
					double endX = e.getX();
					double endY = e.getY();
					if (curEllipse!= null){
						curEllipse.setAll(startX+endX, startY+endY, curEllipse.getC(), curEllipse.getD(), curEllipse.getPhi());
						isWF=ed.isAddable(curEllipse);
					}
				}
				repaint();
					
			}
		}

		MouseAdapter EventListener = new ClickListener();
		addMouseListener(EventListener);
		addMouseWheelListener(EventListener);

		MouseMotionAdapter MovListener = new MovementListener();
		addMouseMotionListener(MovListener);

	}

	public void paint(Graphics g) {
		Font font = new Font("Trebuchet MS", Font.PLAIN, 12);

		Graphics2D g2 = (Graphics2D) g;
		g2.setFont(font);
		g2.setBackground(Color.lightGray);
		g2.clearRect(0, 0, 800, 600);

	
		if (ed.getEllipses().size() > 0) {
			Area area=null;
			if (ed.getSelZone()!=null){
				Zone z=ed.getSelZone();
				area = ed.computeArea(z);
				g2.setPaint(Color.white);
			    g2.fill (area);
			}
			
			
			Iterator<Ellipse> i = ed.getEllipses().iterator();
			while (i.hasNext()) {
				g2.setColor(Color.black);
				Ellipse ei = i.next();
				if (!ed.isRemovable(ei))
					g2.setColor(Color.MAGENTA);
				drawEllipse(ei,g2);
				g2.setColor(Color.blue);
				char[] a = ei.getName().toCharArray();
				g2.drawChars(a, 0, 1, (int) ei.getA(), (int) ei.getB());
			}

			g2.setColor(Color.blue);
			PointXY p;
			Iterator<Zone> zI = ed.getXz().keySet().iterator();
			while (zI.hasNext()) {
				p = ed.getXz().get(zI.next());
				g2.fillOval((int) p.getX() - 3, (int) p.getY() - 3, 6, 6);
			}


			g2.setColor(Color.red);
			if (ed.getAllIP().size() > 0) {
				Iterator<IntersPoint> iP = ed.getAllIP().iterator();
				while (iP.hasNext()) {
					IntersPoint iPi = iP.next();
					g2.fillOval((int) iPi.getX() - 2, (int) iPi.getY() - 2, 4,
							4);
				}
			}

			
			
			g2.setColor(Color.green);
			if(ed.getSelZone() != null){
				PointXY pp = ed.getXz().get(ed.getSelZone());
				if (pp!=null)
					g2.fillOval((int) pp.getX() - 3, (int) pp.getY() - 3, 6, 6);
			}
			
			
//			g2.setColor(Color.black);
//			if (ed.x01tmp != null)
//				g2.fillOval((int) ed.x01tmp.getX() - 2, (int) ed.x01tmp.getY() - 2,
//						4, 4);
//
//			g2.setColor(Color.BLUE);
//			if (ed.m != null)
//				g2.fillOval((int)ed.m.getX() - 3, (int) ed.m.getY() - 3,
//						6, 6);

		}
		g2.setColor(Color.black);
		if (curEllipse != null) {
			if (!isWF)
				g2.setColor(Color.red);
			drawEllipse(curEllipse,g2);
		}
		
		textArea.repaint();
	}


	private void drawEllipse(Ellipse e, Graphics2D g2){
		GeneralPath polygon = e.ellipsePolygon();
		g2.draw(polygon);
	}

}





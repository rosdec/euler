package eulerdiagram;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FunEuler.java
 *
 * Created on 5-nov-2009, 15.01.13
 */

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.util.Iterator;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;



public class FunEuler extends javax.swing.JFrame  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2453618732089045800L;


	// Graphics components
	private jUniverse JPUniverse;
	private javax.swing.JLabel jLabelUniverse;

	private javax.swing.JPanel jPanelStatus;
	private javax.swing.JLabel jLabelStatus;

	private javax.swing.JPanel jPanelOperation;
	private javax.swing.JLabel jLabelOperation;

	private javax.swing.JPanel jPanelResults;
	private javax.swing.JLabel jLabelResults;

	private javax.swing.JMenuBar jMenuBar;
	private javax.swing.JMenu jMenuDiagram;
	private javax.swing.JMenuItem jMenuNew;
	private javax.swing.JMenuItem jMenuQuery;
	private javax.swing.JMenuItem jMenuChiudi;
	private javax.swing.JSeparator jSeparator1;

	private javax.swing.JMenu jMenuItem;
	private javax.swing.JMenu jMenuHelp;


	private javax.swing.JPopupMenu jPopupMenu;
	private javax.swing.JMenuItem jMenuMove;
	private javax.swing.JMenuItem jMenuRename;
	private javax.swing.JMenuItem jMenuChangeColor;
	private javax.swing.JMenuItem jMenuDelete;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JMenuItem jMenuCancel;

	//

	private static final double smallestH = 20.0f;
	private EDSimple<Item> diagram;
	private Ellipse curEllipse;
	private Ellipse selEllipse;
	private Zone selZone;
	private double startX;
	private double startY;
	private double h = smallestH;
	private Graphics2D g2;
	private int eName=0;


	public static final int IDLE = 0;
	public static final int CREATE_SET_BUTTON_ID = 1;
	public static final int QUERY_DIAGRAM_BUTTON_ID = 2;
	public static final int MOVE_SET_BUTTON_ID = 3;
	public static final int REDRAW_SET_BUTTON_ID =4;
	//	public static final int DRAG_ITEM_BUTTON_ID = 13;
	

	int current_state = IDLE;







	/** Creates new form FunEuler */
	public FunEuler() {
		initComponents();
		diagram = new EDSimple<Item>();
	}

	private void formWindowClosing(java.awt.event.WindowEvent evt) {
		System.out.println("OnClosing");
	}

	private void initComponents() {
		JPUniverse = new jUniverse();
		jLabelUniverse = new javax.swing.JLabel();
		jPanelOperation = new javax.swing.JPanel();
		jLabelOperation = new javax.swing.JLabel();
		jPanelResults = new javax.swing.JPanel();
		jLabelResults = new javax.swing.JLabel();
		jPanelStatus = new javax.swing.JPanel();
		jLabelStatus = new javax.swing.JLabel();
		jMenuBar = new javax.swing.JMenuBar();
		jMenuDiagram = new javax.swing.JMenu();
		jMenuNew = new javax.swing.JMenuItem();
		jMenuQuery = new javax.swing.JMenuItem();
		jSeparator1 = new javax.swing.JSeparator();
		jMenuChiudi = new javax.swing.JMenuItem();
		jMenuItem = new javax.swing.JMenu();
		jMenuHelp = new javax.swing.JMenu();

		jPopupMenu = new javax.swing.JPopupMenu() ;
		jMenuMove= new javax.swing.JMenuItem() ;
		jMenuRename = new javax.swing.JMenuItem() ;
		jMenuChangeColor= new javax.swing.JMenuItem() ;
		jMenuDelete= new javax.swing.JMenuItem() ;
		jSeparator2= new javax.swing.JSeparator();
		jMenuCancel= new javax.swing.JMenuItem() ;


		jMenuMove.setText("Move");
		jMenuMove.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				Ellipse tmp=selEllipse;
				setState(MOVE_SET_BUTTON_ID);
				selEllipse=tmp;
				selEllipse.setSelected(true);
				repaint();
			}
		});
		jPopupMenu.add(jMenuMove);

		jMenuRename.setText("Rename");
		jMenuRename.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String text = JOptionPane.showInputDialog(JPUniverse,
						"Rename", selEllipse.getName());
				if (text != null){
					selEllipse.setName(text.replaceAll(" ",""));
				}	
				setState(IDLE);
				repaint();
			}

		});
		jPopupMenu.add(jMenuRename);

		jMenuChangeColor.setText("Change Color");
		jMenuChangeColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				Color tmpcolor = JColorChooser.showDialog(JPUniverse,
						"Choose color", selEllipse.getCol());
				selEllipse.setCol(new Color(tmpcolor.getRed(), tmpcolor
						.getGreen(), tmpcolor.getBlue(), 127));
				setState(IDLE);
				repaint();

			}
		});
		jPopupMenu.add(jMenuChangeColor);

		jMenuDelete.setText("Delete");
		jMenuDelete.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				diagram.remove(selEllipse);
				setState(IDLE);
				repaint();
			}
		});
		jPopupMenu.add(jMenuDelete);

		jPopupMenu.add(jSeparator2);
		jMenuCancel.setText("Cancel");
		jPopupMenu.add(jMenuCancel);

		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				formKeyPressed(evt);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				formWindowClosing(evt);
			}
		});
		setTitle("FunEuler");
		setBackground(new java.awt.Color(255, 255, 255));
		setResizable(false);

		JPUniverse.setBackground(new java.awt.Color(204, 255, 255));
		JPUniverse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		JPUniverse.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				JPUniverseMousePressed(evt);
			}
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				JPUniverseMouseReleased(evt);
			}
		});

		JPUniverse.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(java.awt.event.MouseEvent evt) {
				JPUniverseMouseDragged(evt);
			}
		});

		JPUniverse.addMouseWheelListener(new java.awt.event.MouseAdapter(){
			public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt){
				JPUniverseMouseWheelMoved(evt);
			}
		} );


		jLabelUniverse.setText("Universe");
		javax.swing.GroupLayout JPUniverseLayout = new javax.swing.GroupLayout(JPUniverse);
		JPUniverse.setLayout(JPUniverseLayout);
		JPUniverseLayout.setHorizontalGroup(
				JPUniverseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(JPUniverseLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelUniverse)
						.addContainerGap(535, Short.MAX_VALUE))
		);
		JPUniverseLayout.setVerticalGroup(
				JPUniverseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(JPUniverseLayout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelUniverse)
						.addContainerGap(489, Short.MAX_VALUE))
		);

		jPanelOperation.setBackground(new java.awt.Color(255, 204, 204));
		jPanelOperation.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

		jLabelOperation.setText("Operation");
		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanelOperation);
		jPanelOperation.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelOperation)
						.addContainerGap(100, Short.MAX_VALUE))
		);
		jPanel2Layout.setVerticalGroup(
				jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel2Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelOperation)
						.addContainerGap(213, Short.MAX_VALUE))
		);

		jPanelResults.setBackground(new java.awt.Color(255, 255, 204));
		jPanelResults.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jLabelResults.setText("Results");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanelResults);
		jPanelResults.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelResults)
						.addContainerGap(156, Short.MAX_VALUE))
		);
		jPanel3Layout.setVerticalGroup(
				jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel3Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelResults)
						.addContainerGap(245, Short.MAX_VALUE))
		);

		jPanelStatus.setBackground(new java.awt.Color(153, 153, 255));
		jPanelStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
		jLabelStatus.setText("Status Bar:");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanelStatus);
		jPanelStatus.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(jLabelStatus)
						.addGap(18, 18, 18)
						.addContainerGap(114, Short.MAX_VALUE))
		);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addComponent(jLabelStatus)
						.addContainerGap(17, Short.MAX_VALUE))
		);

		jMenuDiagram.setText("Diagram");
		jMenuNew.setText("Draw a new Set");
		jMenuNew.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(evt.getModifiers()==16)
					setState(1);
			}
		});
		jMenuDiagram.add(jMenuNew);

		jMenuQuery.setText("Query Diagram");
		jMenuQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				if(evt.getModifiers()==16)
					setState(2);
			}
		});
		jMenuDiagram.add(jMenuQuery);

		jMenuDiagram.add(jSeparator1);

		jMenuChiudi.setText("Esci");
		jMenuChiudi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				formWindowClosing(null);
				System.exit(0);
			}
		});

		jMenuDiagram.add(jMenuChiudi);

		jMenuBar.add(jMenuDiagram);
		jMenuItem.setText("Item");
		jMenuBar.add(jMenuItem);


		jMenuHelp.setText("Help");
		jMenuBar.add(jMenuHelp);

		setJMenuBar(jMenuBar);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jPanelOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(jPanelResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGap(18, 18, 18)
												.addComponent(JPUniverse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
												.addComponent(jPanelStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jPanelOperation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanelResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(JPUniverse, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jPanelStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
		);

		pack();


	}// </editor-fold>//GEN-END:initComponents






	private void JPUniverseMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JPUniverseMousePressed
		if (evt.getButton() == MouseEvent.BUTTON1){

			switch (current_state) {
			case CREATE_SET_BUTTON_ID:
				startX = (double) evt.getX();
				startY = (double) evt.getY();
				curEllipse = new Ellipse(startX, startY, 2.0, 2.0, 0.0);
				break;
			case MOVE_SET_BUTTON_ID:	
				startX = (double) evt.getX();
				startY = (double) evt.getY();
				if(selEllipse!=null){
					PointXY p = new PointXY(startX, startY);
					PointXY p1 = selEllipse.getPointC(0.0);
					PointXY p2 = selEllipse.getPointC(Math.PI * 0.5);
					PointXY p3 = selEllipse.getPointC(Math.PI);
					PointXY p4 = selEllipse.getPointC(Math.PI * 1.5);
					if (p.distance(p1) < 6 || p.distance(p2) < 6
							|| p.distance(p3) < 6 || p.distance(p4) < 6) {
						diagram.remove(selEllipse);
						curEllipse = new Ellipse(selEllipse);
						double t = selEllipse.getT(new PointXY(startX, startY));
						PointXY end = selEllipse.getPoint(t);
						PointXY start = selEllipse.getPoint(t + Math.PI);
						PointXY left = selEllipse.getPoint(t + 0.5 * Math.PI);
						PointXY right = selEllipse.getPoint(t - 0.5 * Math.PI);
						selEllipse.setD(left.distance(right) / 2.0);
						selEllipse.assignSecondEdge(start.getX(), start.getY(),
								end.getX(), end.getY());
						startX = start.getX();
						startY = start.getY();
						Ellipse tmp=selEllipse;
						setState(REDRAW_SET_BUTTON_ID);
						selEllipse=tmp;
						selEllipse.setSelected(true);
					} else {
						Ellipse eOld = diagram.getSelectedEllipseAtPoint(p);
						if (eOld != null) {
							//dragging = true;
							startX = eOld.getA() - startX;
							startY = eOld.getB() - startY;
							diagram.remove(eOld);
							curEllipse = new Ellipse(eOld);
						}
					}	
				}
			}
			repaint();
		}

	}//GEN-LAST:event_JPUniverseMousePressed


	private void JPUniverseMouseReleased(java.awt.event.MouseEvent evt){
		if (evt.getButton() == MouseEvent.BUTTON1){
			startX = (double) evt.getX();
			startY = (double) evt.getY();
			switch (current_state) {
			case CREATE_SET_BUTTON_ID:
				if (curEllipse.getC()>smallestH/2){
					curEllipse.setName("Set " + (char) (eName++ + 65));
					diagram.add(curEllipse);
					setState(IDLE);
				}
				curEllipse = null;
				h = smallestH;
				break;

			case IDLE:
				diagram.deselectAll();

				selEllipse= diagram.getClosestEllipseAtPoint(new PointXY(startX,startY));	
				if (selEllipse!=null){
					selEllipse=diagram.getEllipse(selEllipse);
					selEllipse.setSelected(true);
				}
				break;

			case QUERY_DIAGRAM_BUTTON_ID:
				PointXY p=new PointXY(startX, startY);
				selZone = diagram.getZoneAtPoint(p);		
				jLabelStatus.setText("Status Bar: Zona Selezionata "+diagram.getZoneNameAtPoint(p));
			
				break;
			case MOVE_SET_BUTTON_ID:
			case REDRAW_SET_BUTTON_ID:
				if (curEllipse !=null){
					diagram.add(curEllipse);
					setState(MOVE_SET_BUTTON_ID);
					selEllipse=curEllipse;
					selEllipse.setSelected(true);
					curEllipse = null;
					h = smallestH;
				}
				break;
			}		
			repaint();
		}

		if (evt.isPopupTrigger()) {
			if (current_state==IDLE){
				startX = (double) evt.getX();
				startY = (double) evt.getY();
				selEllipse= diagram.getClosestEllipseAtPoint(new PointXY(startX,startY));	
				if (selEllipse!=null){
					diagram.deselectAll();
					selEllipse=diagram.getEllipse(selEllipse);
					selEllipse.setSelected(true);
					jPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
			else 	
			setState(IDLE);
			repaint();
		}
	}




	private void JPUniverseMouseWheelMoved(java.awt.event.MouseWheelEvent evt){
		if (curEllipse != null) {
			if(current_state == CREATE_SET_BUTTON_ID || current_state == REDRAW_SET_BUTTON_ID){
				h += evt.getWheelRotation() * 10;
				if (h < smallestH)
					h = smallestH;
				curEllipse.setAll(curEllipse.getA(), curEllipse.getB(),
						curEllipse.getC(), h, curEllipse.getPhi());
				repaint();
			}
			
		}
	}



	private void JPUniverseMouseDragged(java.awt.event.MouseEvent evt){
		if(curEllipse!= null){
			double endX = evt.getX();
			double endY = evt.getY();
			if(current_state == CREATE_SET_BUTTON_ID || current_state == REDRAW_SET_BUTTON_ID){
				curEllipse.assignSecondEdge(startX, startY, endX, endY);
			}
			if (current_state == MOVE_SET_BUTTON_ID){
				curEllipse.setCenter(startX + endX, startY + endY);				
			}
			repaint();
		}
	}



	private void formKeyPressed(KeyEvent arg0) {
		System.out.println("key " + arg0.getKeyCode());
		int id = arg0.getKeyCode();
		if (id==KeyEvent.VK_ESCAPE){ //ESC
			setState(IDLE);
		}		
		if (id==127) { // canc
			//	System.out.println("Canc");
		}
	}




	public void setState(int _state) {

		current_state = _state;
		clear();
		switch (current_state) {
		case IDLE:
			jLabelStatus.setText("Status Bar: Idle");
			break;
		case CREATE_SET_BUTTON_ID:
			jLabelStatus.setText("Status Bar: Creating a new set");
			break;
		case QUERY_DIAGRAM_BUTTON_ID:
			jLabelStatus.setText("Status Bar: Query Diagram");
			break;
		case MOVE_SET_BUTTON_ID:
			jLabelStatus.setText("Status Bar: Move Diagram");
			break;
		case REDRAW_SET_BUTTON_ID:
			jLabelStatus.setText("Status Bar: Redraw Diagram");
			break;
		}


		repaint();
	}

	private void clear() {
		diagram.deselectAll();
		selZone=null;
		//selEllipse=null;
	}




	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FunEuler().setVisible(true);
			}
		});
	}

	public class jUniverse extends javax.swing.JPanel{
		private static final long serialVersionUID = -6365455723960229326L;
		public void paint(Graphics g){
			super.paint(g);
			g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2.0f));
			drawEllipses();
			if (current_state==2){
				drawZone();
			}
			drawEllipsesName();
		}
	}	

	private void drawZone(){
		if (selZone!=null){
			Area area = diagram.computeArea(selZone);
			g2.setPaint(Color.red);
			g2.fill (area);
		}
	}


	private void drawEllipses() {
		Ellipse selected=null;
		if (diagram.getEllipses().size() > 0) {
			Iterator<Ellipse> i = diagram.getEllipses().iterator();
			while (i.hasNext()) {		
				Ellipse ei = i.next();
				GeneralPath polygon = ei.ellipsePolygon();
				Color ellipseColor;
				ellipseColor = ei.getCol();
				g2.setColor(ellipseColor);
				g2.fill(polygon);

				if (ei.isSelected()) {
					selected = new Ellipse(ei);
				}
			}		
			if (selected!=null){
				GeneralPath polygon = selected.ellipsePolygon();
				g2.setColor(Color.black);
				g2.draw(polygon);
				if (current_state==MOVE_SET_BUTTON_ID)
					g2.setColor(Color.red);
				for (double t = 0.0; t <= 2.0*Math.PI; t += Math.PI * 0.5) {
					PointXY pp = selected.getPointC(t);
					g2.rotate(selected.getPhi(),pp.getX(),pp.getY());
					g2.drawRect((int)pp.getX()-3, (int)pp.getY()-3,6,6);
					g2.rotate(-selected.getPhi(),pp.getX(),pp.getY());
				}
			}
		}

		if (curEllipse != null) {
			g2.setColor(Color.black);
			GeneralPath polygon = curEllipse.ellipsePolygon();
			g2.draw(polygon);
		}
	}


	private void drawEllipsesName() {
		Iterator<Ellipse> ellipsesIterator = diagram.getEllipses().iterator();
		while (ellipsesIterator.hasNext()) {
			Ellipse e = ellipsesIterator.next();
			double x;
			double y;
			double dx;
			double dy;
			double theta;
			double step = 0.2;
			double t = e.getPhi() - Math.PI / 2.0;
			if (t > 0)
				t = (t - Math.PI);
			t -= step / 2.0 * e.getName().length();
			String value = e.getName();
			char[] a = e.getName().toUpperCase().toCharArray();
			for (int i = 0; i < value.length(); ++i) {
				PointXY p = e.getPoint(t);
				x = p.x;
				y = p.y;
				p = e.getPoint(t + 0.01);
				dx = p.x - x;
				dy = p.y - y;
				theta = Math.atan2(dy, dx);
				g2.setColor(Color.black);
				g2.rotate(theta, x, y);
				g2.drawChars(a, i, 1, (int)x,(int) y);	
				g2.rotate(-theta,x,y);
				t += step;
			}
		}
	}

}

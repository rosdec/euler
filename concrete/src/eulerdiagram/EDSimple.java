
package eulerdiagram;


import java.io.Serializable;
import java.awt.Point;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
import java.util.Iterator;
//import java.util.TreeSet;


@SuppressWarnings("hiding")
public class EDSimple<Item> implements Serializable {

	private static final long serialVersionUID = -7094496252892984729L;
	public static String newline = System.getProperty("line.separator");
	
	protected ArrayList<Ellipse> ellipses;
	protected ArrayList<Item> items;

	//protected TreeSet<IntersPoint> allIP;  //   Intersection points
	//protected HashMap<Ellipse,Zone> edges; //   Intersections'graph
	//protected HashSet<Zone> connComp;      //   Connected components
	
	//protected HashSet<Ellipse> tmpPO;      //    Properly Overlapping contours
	//protected HashSet<Ellipse> tmpPC;      //    Properly Containing contours 
	protected int eName=0;
			
	public Zone selZone=null;

   public  EDSimple() {
	   ellipses = new ArrayList<Ellipse>();
	   items =  new ArrayList<Item>();
	   //allIP = new TreeSet<IntersPoint>(); 
	   //edges =  new  HashMap<Ellipse,Zone>();
	   //connComp = new  HashSet<Zone>();
	  
	   //tmpPO = new HashSet<Ellipse>();
	   //tmpPC = new HashSet<Ellipse>();
   }
   
   public ArrayList<eulerdiagram.Item> getItemsAtZone(Zone zone){
	   ArrayList<eulerdiagram.Item> iz= new ArrayList<eulerdiagram.Item>(); 
	   PointXY pp; 
	   Iterator<Item> i = items.iterator();
	   while(i.hasNext()){
		   eulerdiagram.Item it = (eulerdiagram.Item) i.next();
		   pp= new PointXY(it.getPosition().x,it.getPosition().y); 
		   if (zone.equals(getZoneAtPoint(pp))){
			   iz.add(it);
		   }
	   }
	   return iz;
   }
   

	public String getZoneNameAtPoint(PointXY p) {
		String result = new String();
		Iterator<Ellipse> ellipsesIterator = ellipses.iterator();
		while (ellipsesIterator.hasNext()) {
			Ellipse ei = ellipsesIterator.next();
			if (ei.containsPoint(p))
				result +=  ei.getName() + ";";
		}
		return result;
	}

	
	public String getZoneNameAtPoint(Point p) {
		return getZoneNameAtPoint(new PointXY(p));
	}
	
	public Zone getZoneAtPoint(Point p) {
		return getZoneAtPoint(new PointXY(p.getX(),p.getY()));
	}
	
	
	
	public Zone getZoneAtPoint(PointXY p) {
		Zone z = new Zone();
		Iterator<Ellipse> ellipsesIterator = ellipses.iterator();
		while (ellipsesIterator.hasNext()) {
			Ellipse ei = ellipsesIterator.next();
			if (ei.containsPoint(p))
				z.add(ei);
		}		
		return z;
	}
	
	public eulerdiagram.Item getItemAtPoint(PointXY p) {
		Iterator<Item> itemIterator = items.iterator();
		
		while (itemIterator.hasNext()) {
			eulerdiagram.Item i = (eulerdiagram.Item) itemIterator.next();
			
			if (Math.sqrt(( i.getPosition().x-p.getX())*(i.getPosition().x-p.getX())+(i.getPosition().y-p.getY())*(i.getPosition().y-p.getY()))<10.0){
				return i;
			}
		}	
		return null;
	}
	
	
	public eulerdiagram.Item getLabelAtPoint(PointXY p) {
		Iterator<Ellipse> eIterator = ellipses.iterator();
		
		while (eIterator.hasNext()) {
			Ellipse e =  eIterator.next();
			PointXY ep=e.getEllipseLabelPoint();
			if (ep.distance(p)<10.0){
				eulerdiagram.Item i = new eulerdiagram.Item(new Point((int)ep.x,(int)ep.y),e.getName(),e.getName());				
				return i;
			}
		}	
		return null;
	}
	
	
	public Ellipse getEllipseByLabelPoint(PointXY p) {
		Iterator<Ellipse> eIterator = ellipses.iterator();
		
		while (eIterator.hasNext()) {
			Ellipse e =  eIterator.next();
			PointXY ep=e.getEllipseLabelPoint();
			if (ep.distance(p)<10.0)
				return e;
		}	
		return null;
	}
	
	
	
	
	public void selZoneAtPoint(PointXY p) {
		Zone z = new Zone();
		Iterator<Ellipse> ellipsesIterator = ellipses.iterator();
		while (ellipsesIterator.hasNext()) {
			Ellipse ei = ellipsesIterator.next();
			if (ei.containsPoint(p))
				z.add(ei);
		}
		selZone = z;
	}
	
	
	public Ellipse getSelectedEllipseAtPoint(PointXY p){
		Iterator<Ellipse> ellipsesIterator = ellipses.iterator();
		while (ellipsesIterator.hasNext()) {
			Ellipse ei = ellipsesIterator.next();
			if (ei.isSelected() && ei.containsPoint(p))
				return ei;
		}
		return null;
	}
	
	public Ellipse getEllipse(Ellipse selEllipse){
		Iterator<Ellipse> i = ellipses.iterator();
		while (i.hasNext()) {
			Ellipse ei = i.next();
			if (ei.equals(selEllipse))
				return ei;
		}
		return null;
	}
	
	public Ellipse getClosestEllipseAtPoint(PointXY p){
		Ellipse tmpmin = null;
		double min = -1;
		Iterator<Ellipse> i = ellipses.iterator();
		while (i.hasNext()) {
			Ellipse ei = i.next();
			if (ei.hasContourPoint(p) && (min == -1 || ei.distance(p) < min)){
				min = ei.distance(p);
				tmpmin = ei;
			}
		}
		return tmpmin;
	}
	
	public Area computeArea(Zone z) {
		Rectangle2D.Double outer = new Rectangle2D.Double(-1192,-1032,2980,2580);
		Area area = new Area(outer);
		Iterator<Ellipse> ie = ellipses.iterator();
		while(ie.hasNext()){
			Ellipse e = ie.next();
			Area earea = new Area(e.ellipsePolygon());
			if (z.contains(e)){
				area.intersect(earea);
			}else{
				area.subtract(earea);
			}
		}
		return area;
	}
	
		
	public boolean remove(Ellipse oldEllipse) {
			ellipses.remove(oldEllipse);
			return true;
	}
	
	public boolean removeAll(ArrayList<Ellipse> oldEllipse) {
		for(int i=0; i< oldEllipse.size();i++)
				ellipses.remove(oldEllipse.get(i));
		return true;
}

	
	
	
	
	
	public boolean add(Ellipse newEllipse) {
		ellipses.add(newEllipse);
		return true;
	}
	
	public boolean isRemovable(Ellipse ellipse) {
		return true;
	}

	public boolean isAddable(Ellipse tempSet) {
		return true;
	}
	
	
	//connected component
//	protected void addEdges(Ellipse newEllipse, HashMap<Ellipse,Zone> t){
//		Zone z = new Zone();
//		Iterator<Ellipse> i = tmpPO.iterator();
//		while (i.hasNext()) {
//			Ellipse ei = i.next();
//			Zone zi = t.get(ei);
//			Zone zn = new Zone();
//			zn.addAll(zi);
//			zn.add(newEllipse);
//			t.put(ei,zn);
//			z.add(ei);
//		}
//		z.add(newEllipse);
//		t.put(newEllipse, z);
//	}
//
//	protected void removeEdges(Ellipse oldEllipse, HashMap<Ellipse,Zone> t){
//		t.remove(oldEllipse);
//		Iterator<Ellipse> i = tmpPO.iterator();
//		while (i.hasNext()) {
//			Ellipse ei = i.next();
//			Zone zi = t.get(ei);
//			Zone zn = new Zone();
//			zn.addAll(zi);
//			zn.remove(oldEllipse);
//			t.put(ei,zn);
//		}
//		
//	}
//
//	protected void computeConnectedComponent(HashSet<Zone> c, HashMap<Ellipse,Zone> t){
//		c.clear();
//		// for each ellipse ei, create a component {ei}
//		Iterator<Ellipse> i = t.keySet().iterator();
//		while (i.hasNext()) {
//			Ellipse ei = i.next();
//			Zone zei = new Zone();
//			zei.add(ei);
//			c.add(zei);
//		}
//		/* fore each edge (ej,ek)
//		 *    if findSet(ej)!= findSet(ek)
//		 *       Union(ej,ek)  
//		 */
//		Iterator<Ellipse> j = t.keySet().iterator();
//		while (j.hasNext()){
//			Ellipse ej = j.next();
//			if (t.get(ej)!=null){
//				Iterator<Ellipse> k = t.get(ej).iterator();
//				while (k.hasNext()){
//					Ellipse ek = k.next();
//					Zone zej = findSet(c,ej);
//					Zone zek = findSet(c,ek);
//					if (!zej.equals(zek)){
//						Zone znew = new Zone();
//						znew.addAll(zej);
//						znew.addAll(zek);
//						c.add(znew);
//						c.remove(zej);
//						c.remove(zek);
//					}
//				}
//			}
//		}
//	}
//
//	protected Zone findSet(HashSet<Zone> t, Ellipse e){
//		Iterator<Zone> i = t.iterator();
//		while (i.hasNext()){
//			Zone zj = i.next();
//			if (zj.contains(e))
//				return zj;
//		}	
//		return null; 
//	}
//
//		
//	protected void computeIPZones(Ellipse e){
//		CenteredIntersPoint x0 = e.getIntPoints().pollFirst();
//
//		// x0.zone := tmpPC
//		x0.setZone(new Zone(tmpPC));
//	
//		CenteredIntersPoint x1 = e.getIntPoints().first();
//
//		//x01 is the middle point along the arc (x0, x1)
//		PointXY x01 = e.getPoint((e.getT(x0)+ e.getT(x1))*0.5);
//
//		//Computing the describing set of the zone split by (x0, x1)
//		Iterator<Ellipse> i = tmpPO.iterator();
//		while (i.hasNext()) {
//			Ellipse ei = i.next();
//			if (ei.containsPoint(x01))
//				x0.getZone().add(ei);
//		}
//
//		//Computing the zones associated to all the other intersection points (XOR rule)
//		CenteredIntersPoint tmpx = x0;
//		Iterator<CenteredIntersPoint> iCIP = e.getIntPoints().iterator();
//		while (iCIP.hasNext()) {
//			CenteredIntersPoint xi = iCIP.next();
//			xi.getZone().addAll(tmpx.getZone());
//			if (tmpx.getZone().contains(xi.getGenEll())) {
//				xi.getZone().remove(xi.getGenEll());
//			} else {
//				xi.getZone().add(xi.getGenEll());
//			}
//			tmpx = xi;
//		}
//		e.getIntPoints().add(x0);
//	}


	
	public void deselectAll(){
		Iterator<Ellipse> i = ellipses.iterator();
		while (i.hasNext()) {
			Ellipse ei = i.next();
			ei.setSelected(false);
		}
	}
	
	// Getters and Setters Methods
	public Zone getSelZone() {
		return selZone;
	}
	
	public ArrayList<Ellipse> getEllipses() {
		return ellipses;
	}

	public void setEllipses(ArrayList<Ellipse> ellipses) {
		this.ellipses = ellipses;
	}

//	public TreeSet<IntersPoint> getAllIP() {
//		return allIP;
//	}
//
//	public void setAllIP(TreeSet<IntersPoint> allIP) {
//		this.allIP = allIP;
//	}
	
	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}

	public int getEName() {
		return eName;
	}

	public void setEName(int name) {
		eName = name;
	}
	

}






	

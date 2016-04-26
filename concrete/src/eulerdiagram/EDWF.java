package eulerdiagram;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;




@SuppressWarnings("hiding")
public class EDWF<Item>  extends ED<Item> implements Serializable {
	private static final long serialVersionUID = -1277968931308688110L;

	private TreeMap<Zone, PointXY> Xz;   //   Zones descriptors and associated marked point 
	private TreeMap<Zone, PointXY> tmpXz;
	private TreeSet<IntersPoint> tmpAllIP;
	private HashMap<Ellipse,Zone> tmpEdges;
	private HashSet<Zone> tmpConnComp;

		
	public EDWF() {
		super();
		Xz = new TreeMap<Zone, PointXY>();
		tmpXz = new TreeMap<Zone, PointXY>();
		tmpAllIP = new TreeSet<IntersPoint>();
		tmpEdges =  new  HashMap<Ellipse,Zone>();
		tmpConnComp = new  HashSet<Zone>();
		
	}

	public boolean remove(Ellipse oldEllipse){
		clear();
		if (removeContourZoneComputation(oldEllipse)) {
			ellipses.remove(oldEllipse);
			copyFromTmp();
			printXz();
			return true;
		}
		return false;
	}

	public boolean add(Ellipse newEllipse) {
		clear();
		if (newContourZoneComputation(newEllipse)) {
			ellipses.add(newEllipse);
			copyFromTmp();
			printXz();
			return true;
		}
		return false;
	}


	
	public boolean isAddable(Ellipse newEllipse) {
		return newContourZoneComputation(newEllipse);
	}

	public boolean isRemovable(Ellipse oldEllipse) {
		return removeContourZoneComputation(oldEllipse);
	}

	private void copyFromTmp(){
		Xz.clear();
		Xz.putAll(tmpXz);
		allIP.clear();
		allIP.addAll(tmpAllIP);
		edges.clear();
		edges.putAll(tmpEdges);
		connComp.clear();
		connComp.addAll(tmpConnComp);
	} 

	private void clear(){
		selZone=null;
	} 

	// Adding and deleting contours
	private boolean removeContourZoneComputation(Ellipse e) {
		if (!computeContourRelationships(e,false))
			return false; //  Rollback: single or triple point
		tmpEdges.clear();
		tmpEdges.putAll(edges);
		removeEdges(e,tmpEdges);
		computeConnectedComponent(tmpConnComp,tmpEdges);

		TreeMap<Zone, PointXY> tmptmpXz = new TreeMap<Zone, PointXY>(Xz); //temporary set to be used for Rollback
		TreeMap<Zone, PointXY> Xzn = new TreeMap<Zone, PointXY>(); // Xzn is the set of new zones describing set
		TreeMap<Zone, PointXY> Xzs = new TreeMap<Zone, PointXY>(); // Xzs is the set of split zones describing set

		if (tmpPO.size() == 0) {  // newEllipse does not properly overlap any contour present in ED
			Zone Xs = new Zone();  // the split zone
			Zone Xn = new Zone();  // the old zone 

			//Compute Xs and Xn
			Iterator<Ellipse> i = tmpPC.iterator();
			while (i.hasNext()) {
				Ellipse ei = i.next();
				Xs.add(ei);
				Xn.add(ei);
			}
			Xn.add(e);

			// Add Xs to Xzs and Xn to Xzn
			Xzs.put(Xs, null);
			Xzn.put(Xn, null); // the old zone is marked by any point in newEllipse
			//tmpAllIP.remove(new IntersPoint(Xz.get(Xn)));
		} else { // the ellipse to be removed properly overlap with at least one contour in ED

			computeIPZones(e);

			//Check for disconnected zone
			int diff = connComp.size()-tmpConnComp.size();
			//System.out.println("diff= "+diff);
			if (e.getIntPoints().size() != (getNewZonesSize(e)-diff)) {
				return false; // Rollback
			}

			// UpdateMarkedPoint
			// For each intersection point
			HashSet<IntersPoint> hi = new HashSet<IntersPoint>();
			Iterator<CenteredIntersPoint> iCIP = e.getIntPoints().iterator();
			while (iCIP.hasNext()) {
				CenteredIntersPoint x = iCIP.next();
				IntersPoint hx = new IntersPoint(x.getX(),x.getY());
				hi.add(hx);			
			}

			iCIP = e.getIntPoints().iterator();
			while (iCIP.hasNext()) {
				CenteredIntersPoint x = iCIP.next();

				Zone Xs = x.getZone(); // Xs is the split zone
				Zone Xn = new Zone(); // Xn is the zone to be removed
				Xn.addAll(Xs);
				Xn.add(e);

				PointXY pXn= tmptmpXz.get(Xn);
				IntersPoint cXn = new IntersPoint(pXn.getX(), pXn.getY());
				if ((Xs.size()>0) && !hi.contains(cXn) && tmpAllIP.contains(cXn)){
					tmptmpXz.put(Xs, pXn);
				}

				Xzs.put(Xs, null); // Xzs is the set of split zones describing set
				Xzn.put(Xn, null);  // Xzn is the set of new zones describing set
			}
		}

		Iterator<Zone> k = Xzn.keySet().iterator();
		while (k.hasNext()) {
			Zone zk = k.next();
			tmptmpXz.remove(zk);  // The set of new zones Xzn is removed from Xz
		}

		PointXY p;
		tmpXz.clear();

		// for each not split zone zi
		Iterator<Zone> j = tmptmpXz.keySet().iterator();
		while (j.hasNext()) {
			Zone zi = j.next();
			p = tmptmpXz.get(zi);
			if (!Xzs.containsKey(zi) && e.containsPoint(p)) {  // if zi is not split and is contained by removedEllipse
				Zone ztmp = new Zone();
				ztmp.addAll(zi);
				ztmp.remove(e);
				if (Xz.containsKey(ztmp) || ztmp.size()==0) {
					return false; // Rollback
				}
				tmpXz.put(ztmp, p);  // the zone zi is updated, newEllipse is added to the set of contour which describes zi
			} else {
				tmpXz.put(zi, p);    // the zone zi is not updated
			}
		}

		return true;
	}

	private boolean newContourZoneComputation(Ellipse e) {
		if (!computeContourRelationships(e,true))
			return false; // Rollback: single or triple point
		tmpEdges.clear();
		tmpEdges.putAll(edges);
		addEdges(e,tmpEdges);
		computeConnectedComponent(tmpConnComp,tmpEdges);;

		TreeMap<Zone, PointXY> tmptmpXz = new TreeMap<Zone, PointXY>(Xz); //temporary set to be used for Rollback
		TreeMap<Zone, PointXY> Xzn = new TreeMap<Zone, PointXY>(); // Xzn is the set of new zones describing set
		TreeMap<Zone, PointXY> Xzs = new TreeMap<Zone, PointXY>(); // Xzs is the set of split zones describing set

		if (tmpPO.size() == 0) {  // newEllipse does not properly overlap any contour present in ED
			Zone Xs = new Zone();  // the split zone
			Zone Xn = new Zone();  // the new zone 

			//Compute Xs and Xn
			Iterator<Ellipse> i = tmpPC.iterator();
			while (i.hasNext()) {
				Ellipse ei = i.next();
				Xs.add(ei);
				Xn.add(ei);
			}
			Xn.add(e);

			// Add Xs to Xzs and Xn to Xzn
			Xzs.put(Xs, null);
			Xzn.put(Xn, e.getPointC(0.0)); // the new zone is marked by any point in newEllipse
            //tmpAllIP.add(new IntersPoint(e.getPointC(0.0)));
		} else { // newEllipse properly overlap with at least one contour in ED

			computeIPZones(e);

			//Check for disconnected zone
			int diff = connComp.size()-tmpConnComp.size();
			//System.out.println("diff= "+diff);
			if (e.getIntPoints().size() != (getNewZonesSize(e)+diff)) {
				return false; // Rollback
			}

			// UpdateMarkedPoint
			// For each intersection point
			Iterator<CenteredIntersPoint> iCIP  = e.getIntPoints().iterator();
			while (iCIP.hasNext()) {
				CenteredIntersPoint x = iCIP.next();

				Zone Xs = x.getZone(); // Xs is the split zone
				Zone Xn = new Zone(); // Xn the new zone
				Xn.addAll(Xs);
				Xn.add(e);

				PointXY pn; // marker point for the new zone
				if (tmptmpXz.get(Xs) != null && e.containsPoint(tmptmpXz.get(Xs))) {
					pn = tmptmpXz.put(Xs, new PointXY(x));  // Swapping: the point previously assigned t0 Xs is assigned to Xn
					// Xs is marked with x
				} else {							  
					pn = new PointXY(x);              // Xn is marked with x
				}
				Xzs.put(Xs, null); // Xzs is the set of split zones describing set
				Xzn.put(Xn, pn);  // Xzn is the set of new zones describing set
			}
		}

		//
		PointXY p;
		tmpXz.clear();

		// for each not split zone zi
		Iterator<Zone> j = tmptmpXz.keySet().iterator();
		while (j.hasNext()) {
			Zone zi = j.next();
			p = tmptmpXz.get(zi);
			if (!Xzs.containsKey(zi) && e.containsPoint(p)) {  // if zi is not split and is contained by newEllipse
				Zone ztmp = new Zone();
				ztmp.addAll(zi);
				ztmp.add(e);
				tmpXz.put(ztmp, p);  // the zone zi is updated, newEllipse is added to the set of contour which describes zi
			} else {
				tmpXz.put(zi, p);    // the zone zi is not updated
			}
		}

		tmpXz.putAll(Xzn);// The set of new zones Xzn is added to Xz
		return true;
	}


	private boolean computeContourRelationships(Ellipse e, boolean newEll) {
		// O := D := emptyset
		tmpPO.clear();  // is the set of all the contours which properly overlap with newEllipse
		tmpPC.clear();  // is the set of all the contours which properly contain newEllipse

		// tmpAllIP is a copy of AllIP  (for rollback functionalities)
		tmpAllIP.clear();
		tmpAllIP.addAll(allIP);

		//for each ellipses b in ED look for its relation with the new ellipse
		TreeSet<CenteredIntersPoint> newCIPs = new TreeSet<CenteredIntersPoint>();
		Iterator<Ellipse> i = ellipses.iterator();  
		while (i.hasNext()) {
			Ellipse b = i.next();
			if(newEll || !b.equals(e)){
				TreeSet<IntersPoint> eiIP = e.findIntersectionsEllipseEllipse(b);
				if (eiIP.size() == 0) {
					// no intersection point found .... check if newEllipse is properly contained in b
					if (b.containsPoint(e.getPointC(0.0)))
						tmpPC.add(b);
				} else {
					if (eiIP.size() != 2 && eiIP.size() != 4) {
						return false; // Rollback
					}
					// Overlapping Contours
					tmpPO.add(b);
					Iterator<IntersPoint> ipI = eiIP.iterator();
					while (ipI.hasNext()) {
						IntersPoint ip = ipI.next();
						CenteredIntersPoint cip = new CenteredIntersPoint(
								ip.getX(), ip.getY(), e.getA(), e.getB());
						cip.setGenEll(b); // each point keeps a reference to the contour which generates it
						newCIPs.add(cip);
						if(!newEll)
							tmpAllIP.remove(ip);
					}
					if (newEll){
						// Check for triple point
						TreeSet<IntersPoint> tmptmpAllIP = new TreeSet<IntersPoint>(tmpAllIP);
						tmptmpAllIP.addAll(eiIP);
						if (tmptmpAllIP.size() != tmpAllIP.size() + eiIP.size()) {
							return false; // Rollback
						}
						tmpAllIP = tmptmpAllIP;
					}
				}
			}
		}
		e.setIntPoints(newCIPs);
		return true;
	}


	
	private int getNewZonesSize(Ellipse e) {
		TreeSet<Zone> zones = new TreeSet<Zone>();
		Iterator<CenteredIntersPoint> iCIP = e.getIntPoints().iterator();
		while (iCIP.hasNext()) {
			CenteredIntersPoint xi = iCIP.next();
			zones.add(xi.getZone());
		}
		return zones.size();
	}

	
	// Print Methods	
	public void printXz() {
		System.out.println("Number of zones = " + (tmpXz.keySet().size() + 1));
		
		System.out.println("{}");
		
		Iterator<Zone> zI = tmpXz.keySet().iterator();
		while (zI.hasNext()) {
			Zone z = zI.next();
			Iterator<Ellipse> eI = z.iterator();
			if (eI.hasNext()) {
				Ellipse e = eI.next();
				System.out.print("{" + e.getName());
			
			}
			while (eI.hasNext()) {
				Ellipse e = eI.next();
				System.out.print("," + e.getName());
				
			}
			System.out.print("}  Marked by");
			
			if(tmpXz.get(z)!=null){
				System.out.println(tmpXz.get(z).toString());
				
			}
		}
		System.out.println();
	
	}


//	private void printEdges() {
//		System.out.println("Edges");
//		Iterator<Ellipse> i = tmpEdges.keySet().iterator();
//		while (i.hasNext()) {
//			Ellipse ei= i.next();
//			System.out.print(ei.getName()+ " --->{");
//			Zone z = tmpEdges.get(ei);
//			Iterator<Ellipse> j = z.iterator();
//			Ellipse ej;
//			if (j.hasNext()){
//				ej= j.next();
//				System.out.print(ej.getName());
//			}
//			while (j.hasNext()) {
//				ej = j.next();
//				System.out.print("," + ej.getName());
//			}
//			System.out.println("}");
//		}
//	}
//
//
//	private void printCC() {
//		System.out.println("Number of connected components = " + tmpConnComp.size());
//		
//		Iterator<Zone> i = tmpConnComp.iterator();
//		while (i.hasNext()) {
//			Zone zi= i.next();
//			System.out.print("{");
//			
//			Iterator<Ellipse> j = zi.iterator();
//			Ellipse ej;
//			if (j.hasNext()){
//				ej= j.next();
//				System.out.print(ej.getName());
//				
//			}
//			while (j.hasNext()) {
//				ej = j.next();
//				System.out.print("," + ej.getName());
//			
//			}
//			System.out.println("}");
//			
//		}
//	}
	
	
	// Getters and Setters Methods

	public TreeMap<Zone, PointXY> getXz() {
		return Xz;
	}

	public void setXz(TreeMap<Zone, PointXY> xz) {
		Xz = xz;
	}

}

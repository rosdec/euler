package eulerdiagram;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

@SuppressWarnings("hiding")
public class EDNWF<Item> extends ED<Item> implements Serializable {

	private static final long serialVersionUID = 2871524955515713315L;

	private TreeMap<Zone, TreeSet<IntersPoint>> Xz; // Zones descriptors and
													// associated marked point

	private boolean isWF = true;

	// For Debugging only
	private HashSet<Area> splits;

	public EDNWF() {
		super();
		Zone empty = new Zone();
		TreeSet<IntersPoint> emptyset = new TreeSet<IntersPoint>();
		Xz = new TreeMap<Zone, TreeSet<IntersPoint>>();
		Xz.put(empty, emptyset); // empty diagram
		splits = new HashSet<Area>();
	}

	public boolean remove(Ellipse oldEllipse) {
		clear();
		if (computeContourRelationships(oldEllipse, false)) {
			System.out.println("Overlapping ellipses " + tmpPO.size());
			System.out.println("Containing ellipses " + tmpPC.size());
			removeEdges(oldEllipse,edges);
			computeConnectedComponent(connComp,edges);
			ellipses.remove(oldEllipse);
			removeContourZoneComputation(oldEllipse);
			printXz();
			checkWF();
			return true;
		}
		return false; // Rollback: single or triple point
	}

	public boolean add(Ellipse newEllipse) {
		clear();
		if (computeContourRelationships(newEllipse, true)) {
			System.out.println("Overlapping ellipses " + tmpPO.size());
			System.out.println("Containing ellipses " + tmpPC.size());
			addEdges(newEllipse,edges);
			computeConnectedComponent(connComp,edges);
			newContourZoneComputation(newEllipse);
			ellipses.add(newEllipse);
			printXz();
			checkWF();
			return true;
		}
		return false; // Rollback: single or triple point
	}

	private void checkWF() {
		if (allIP.size() + 1 == Xz.size())
			isWF = true;
		else {
			isWF = false;
			computeSplitZones();
		}

	}


	private void computeSplitZones() {
		Iterator<Zone> izone = Xz.keySet().iterator();
		while (izone.hasNext()) {
			Zone z = izone.next();
			checkSplitZone(z);
		}
	}

	private void checkSplitZone(Zone z) {
		Area area = computeArea(z);

		PathIterator pi = area.getPathIterator(null);
		double coords[] = new double[23];
		int count = 0;
		while (!pi.isDone()) {
			switch (pi.currentSegment(coords)) {
			case PathIterator.SEG_MOVETO:
				count++;
				break;
			}
			pi.next();
		}
		if (count > 1) {
			GeneralPath[] polygon = new GeneralPath[count];
			Point2D.Double[] p = new Point2D.Double[count];
			int i = 0;
			pi = area.getPathIterator(null);
			polygon[i] = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
			pi.currentSegment(coords);
			polygon[i].moveTo(coords[0], coords[1]);
			p[i] = new Point2D.Double(coords[0], coords[1]);
			pi.next();
			while (!pi.isDone()) {
				switch (pi.currentSegment(coords)) {
				case PathIterator.SEG_MOVETO:
					i++;
					polygon[i] = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
					p[i] = new Point2D.Double(coords[0], coords[1]);
					polygon[i].moveTo(coords[0], coords[1]);
					break;
				case PathIterator.SEG_LINETO:
					polygon[i].lineTo(coords[0], coords[1]);
					break;
				case PathIterator.SEG_CLOSE:
					polygon[i].closePath();
					break;
				}
				pi.next();
			}

			Area[] bb = new Area[count];
			int empty = 0;
			for (i = 0; i < count; i++) {
				bb[i] = new Area(polygon[i]);
				if (bb[i].isEmpty())
					empty++;
			}

			if (count - empty > 1) {

				Area[] aa = new Area[count - empty];
				int j = 0;
				for (i = 0; i < count; i++) {
					if (!bb[i].isEmpty()) {
						aa[j] = bb[i];
						j++;
					}
				}
				count = count - empty;
				boolean check = true;
				for (i = 0; i < count - 2; i++) {
					if (!aa[count - 1].contains(p[i])
							|| aa[i + 1].contains(p[i]))
						check = false;
				}
				if (!aa[count - 1].contains(p[count - 2]))
					check = false;
				if (!check) {
					for (i = 0; i < count; i++)
						splits.add(area);
				}
			}
		}
	}

	private void clear() {
		selZone = null;
		splits.clear();
	}

	// Adding and deleting contours
	private void removeContourZoneComputation(Ellipse e) {
		TreeSet<Zone> splitZones = new TreeSet<Zone>();
		IntersPoint ip = e.getFakeIntersPoint();
		if (ip != null) {
			allIP.remove(ip);
		}
		if (tmpPO.size() == 0) { // oldEllipse does not properly overlap any
									// contour present in ED
			Zone Xs = new Zone(tmpPC);
			Zone Xn = new Zone(tmpPC);
			Xn.add(e); // Xs and Xn need to be merged

			TreeSet<IntersPoint> tmpIPs = Xz.get(Xs);
			tmpIPs.addAll(Xz.get(Xn));

			if (ip != null) {
				tmpIPs.remove(ip);
			}

			Xz.put(Xs, tmpIPs); // the merged zone
			Xz.remove(Xn);
			splitZones.add(Xs);
		} else { // newEllipse properly overlap with at least one contour in ED
			computeIPZones(e);
			TreeMap<Zone, TreeSet<IntersPoint>> zones = new TreeMap<Zone, TreeSet<IntersPoint>>();
			CenteredIntersPoint xLast = e.getIntPoints().last();
			CenteredIntersPoint xNext;
			Iterator<CenteredIntersPoint> iCIP = e.getIntPoints().iterator();
			while (iCIP.hasNext()) {
				Zone z = xLast.getZone();
				xNext = iCIP.next();
				TreeSet<IntersPoint> ipz = zones.get(z);
				if (ipz == null) {
					ipz = new TreeSet<IntersPoint>();
				}
				ipz.add(new IntersPoint(xLast, e));
				ipz.add(new IntersPoint(xNext, e));
				zones.put(z, ipz);
				xLast = xNext;
			}

			Iterator<Zone> izone = zones.keySet().iterator();
			while (izone.hasNext()) {
				Zone Xs = izone.next(); // the split zone
				Zone Xn = new Zone(Xs);
				Xn.add(e); // the new zone

				TreeSet<IntersPoint> tmpIPXs = Xz.get(Xs);
				TreeSet<IntersPoint> tmpIPXn = Xz.get(Xn);
				tmpIPXs.addAll(tmpIPXn);
				tmpIPXs.removeAll(zones.get(Xs));
				if (ip != null && tmpIPXs.contains(ip))
					tmpIPXs.remove(ip);
				Xz.put(Xs, tmpIPXs);
				Xz.remove(Xn);
			}
			splitZones.addAll(zones.keySet());

		}
		TreeMap<Zone, TreeSet<IntersPoint>> tmpXz = new TreeMap<Zone, TreeSet<IntersPoint>>();
		Iterator<Zone> izone = Xz.keySet().iterator();
		while (izone.hasNext()) {
			Zone Xc = izone.next();
			if (splitZones.contains(Xc) || (!Xc.contains(e))) {
				if (!tmpXz.containsKey(Xc))
					tmpXz.put(Xc, Xz.get(Xc));
			} else {
				Zone Xcs = new Zone(Xc);
				Xcs.remove(e);
				TreeSet<IntersPoint> tmpIPXc = Xz.get(Xc);
				if (Xz.containsKey(Xcs)) {
					tmpIPXc.addAll(Xz.get(Xcs));
				}
				tmpXz.put(Xcs, tmpIPXc);
			}
		}
		Xz = tmpXz;
		if (tmpPO.size() != 0)
			checkFakePoints(null);
	}

	private void checkFakePoints(Ellipse newell) {
		Iterator<Zone> iConn = connComp.iterator();
		while (iConn.hasNext()) {
			Zone conn = iConn.next();
			Iterator<Ellipse> ie = conn.iterator();
			boolean found = false;
			while (ie.hasNext()) {
				Ellipse e = ie.next();
				if (e.getFakeIntersPoint() != null) {
					if (!found)
						found = true;
					else {
						IntersPoint eip = e.getFakeIntersPoint();
						e.setFakeIntersPoint(null);
						allIP.remove(eip);
						Zone Xeip = getZoneAtPoint(new PointXY(eip));
						if (newell != null
								&& newell.containsPoint(new PointXY(eip)))
							Xeip.add(newell);
						Xz.get(Xeip).remove(eip);
						if (Xeip.contains(e))
							Xeip.remove(e);
						else
							Xeip.add(e);
						Xz.get(Xeip).remove(eip);
					}
				}
			}
			if (!found) {
				Ellipse en = conn.first();
				IntersPoint enip = new IntersPoint(en.getPointC(0.0), en, en);
				en.setFakeIntersPoint(enip);
				allIP.add(enip);
				Zone Xen = getZoneAtPoint(new PointXY(enip));
				if (newell != null && newell.containsPoint(new PointXY(enip)))
					Xen.add(newell);
				Xz.get(Xen).add(enip);
				if (Xen.contains(en))
					Xen.remove(en);
				else
					Xen.add(en);
				Xz.get(Xen).add(enip);
			}
		}
	}

	private void newContourZoneComputation(Ellipse e) {
		TreeSet<Zone> splitZones = new TreeSet<Zone>();
		if (tmpPO.size() == 0) { // newEllipse does not properly overlap any
									// contour present in ED
			Zone Xs = new Zone(tmpPC); // the split zone
			Zone Xn = new Zone(tmpPC); // the new zone
			Xn.add(e);

			IntersPoint ip = new IntersPoint(e.getPointC(0.0), e, e);
			e.setFakeIntersPoint(ip);
			allIP.add(ip);

			TreeSet<IntersPoint> ips = new TreeSet<IntersPoint>();
			TreeSet<IntersPoint> ipn = new TreeSet<IntersPoint>();
			ips.add(ip);
			ipn.add(ip);

			TreeSet<IntersPoint> tmpIPs = Xz.get(Xs);
			Iterator<IntersPoint> iIPs = tmpIPs.iterator();
			while (iIPs.hasNext()) {
				IntersPoint p = iIPs.next();
				if (e.containsPoint(new PointXY(p)))
					ipn.add(p);
				else
					ips.add(p);
			}

			Xz.put(Xs, ips);
			Xz.put(Xn, ipn);

			splitZones.add(Xs);
		} else { // newEllipse properly overlap with at least one contour in ED
			computeIPZones(e);

			TreeMap<Zone, TreeSet<IntersPoint>> zones = new TreeMap<Zone, TreeSet<IntersPoint>>();
			CenteredIntersPoint xLast = e.getIntPoints().last();
			CenteredIntersPoint xNext;
			Iterator<CenteredIntersPoint> iCIP = e.getIntPoints().iterator();
			while (iCIP.hasNext()) {
				Zone z = xLast.getZone();
				xNext = iCIP.next();
				TreeSet<IntersPoint> ipz = zones.get(z);
				if (ipz == null) {
					ipz = new TreeSet<IntersPoint>();
				}
				ipz.add(new IntersPoint(xLast, e));
				ipz.add(new IntersPoint(xNext, e));
				zones.put(z, ipz);
				xLast = xNext;
			}

			Iterator<Zone> izone = zones.keySet().iterator();
			while (izone.hasNext()) {
				Zone Xs = izone.next(); // the split zone
				Zone Xn = new Zone(Xs);
				Xn.add(e); // the new zone

				TreeSet<IntersPoint> ips = new TreeSet<IntersPoint>();
				TreeSet<IntersPoint> ipn = new TreeSet<IntersPoint>();
				ips.addAll(zones.get(Xs));
				ipn.addAll(zones.get(Xs));
				TreeSet<IntersPoint> tmpIPs = Xz.get(Xs);
				Iterator<IntersPoint> iIPs = tmpIPs.iterator();
				while (iIPs.hasNext()) {
					IntersPoint p = iIPs.next();
					if (e.containsPoint(new PointXY(p)))
						ipn.add(p);
					else
						ips.add(p);
				}
				Xz.put(Xs, ips);
				Xz.put(Xn, ipn);
			}
			splitZones.addAll(zones.keySet());
		}

		TreeMap<Zone, TreeSet<IntersPoint>> tmpXz = new TreeMap<Zone, TreeSet<IntersPoint>>();
		Iterator<Zone> izone = Xz.keySet().iterator();
		while (izone.hasNext()) {
			Zone Xc = izone.next();
			if (splitZones.contains(Xc) || Xc.contains(e)) {
				tmpXz.put(Xc, Xz.get(Xc));
			} else {
				TreeSet<IntersPoint> ipc = new TreeSet<IntersPoint>();
				TreeSet<IntersPoint> ipcn = new TreeSet<IntersPoint>();
				Iterator<IntersPoint> iIPXc = Xz.get(Xc).iterator();
				while (iIPXc.hasNext()) {
					IntersPoint iXc = iIPXc.next();
					if (e.containsPoint(iXc)) {
						ipcn.add(iXc);
					} else {
						ipc.add(iXc);
					}
				}
				if (ipc.size() > 0)
					tmpXz.put(Xc, ipc);
				if (ipcn.size() > 0) {
					Zone Xcn = new Zone(Xc);
					Xcn.add(e);
					tmpXz.put(Xcn, ipcn);
				}
			}
		}
		Xz = tmpXz;
		if (tmpPO.size() != 0)
			checkFakePoints(null);
	}

	private boolean computeContourRelationships(Ellipse e, boolean newEll) {
		// O := D := emptyset
		tmpPO.clear(); // is the set of all the contours which properly overlap
						// with newEllipse
		tmpPC.clear(); // is the set of all the contours which properly contain
						// newEllipse

		// tmpAllIP is a copy of AllIP (for rollback functionalities)
		TreeSet<IntersPoint> tmpAllIP = new TreeSet<IntersPoint>();
		tmpAllIP.addAll(allIP);

		// for each ellipses b in ED look for its relation with the new ellipse
		TreeSet<CenteredIntersPoint> newCIPs = new TreeSet<CenteredIntersPoint>();
		Iterator<Ellipse> i = ellipses.iterator();
		while (i.hasNext()) {
			Ellipse b = i.next();
			if (newEll || !b.equals(e)) {
				TreeSet<IntersPoint> eiIP = e
						.findIntersectionsEllipseEllipse(b);
				if (eiIP.size() == 0) {
					// no intersection point found .... check if newEllipse is
					// properly contained in b
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
						CenteredIntersPoint cip = new CenteredIntersPoint(ip
								.getX(), ip.getY(), e.getA(), e.getB(), b);
						// each point keeps a reference to the contour which
						// generates it
						newCIPs.add(cip);
						if (!newEll)
							tmpAllIP.remove(ip);
					}
					if (newEll) {
						// Check for triple point
						TreeSet<IntersPoint> tmptmpAllIP = new TreeSet<IntersPoint>(
								tmpAllIP);
						tmptmpAllIP.addAll(eiIP);
						if (tmptmpAllIP.size() != tmpAllIP.size() + eiIP.size()) {
							System.out.println("WARNING: TRIPLE POINT!!!!");
							return false; // Rollback
						}
						tmpAllIP = tmptmpAllIP;
					}
				}
			}
		}
		e.setIntPoints(newCIPs);
		allIP = tmpAllIP;

		// updateOvelappingEllipses
		if (newEll) {
			Iterator<CenteredIntersPoint> icip = newCIPs.iterator();
			while (icip.hasNext()) {
				CenteredIntersPoint cip = icip.next();
				Ellipse oe = cip.getGenEll();
				CenteredIntersPoint oecip = new CenteredIntersPoint(cip.getX(),
						cip.getY(), oe.getA(), oe.getB(), e);
				oe.getIntPoints().add(oecip);
			}
		} else {
			Iterator<Ellipse> iPO = tmpPO.iterator();
			while (iPO.hasNext()) {
				Ellipse oe = iPO.next();
				TreeSet<CenteredIntersPoint> tmpCIP = new TreeSet<CenteredIntersPoint>(
						oe.getIntPoints());
				Iterator<CenteredIntersPoint> icip = tmpCIP.iterator();
				while (icip.hasNext()) {
					CenteredIntersPoint cip = icip.next();
					if (e.equals(cip.getGenEll())) {
						oe.getIntPoints().remove(cip);
					}
				}
			}
		}
		return true;
	}

	private void myprint(String s) {
		System.out.print(s);
	}

	// Print Methods
	private void printXz() {
		myprint("Number of zones = " + (Xz.keySet().size()) + newline);

		Iterator<Zone> zI = Xz.keySet().iterator();
		while (zI.hasNext()) {
			Zone z = zI.next();
			if (z.size() > 0) {
				Iterator<Ellipse> eI = z.iterator();
				if (eI.hasNext()) {
					Ellipse e = eI.next();
					myprint("{" + e.getName());
				}
				while (eI.hasNext()) {
					Ellipse e = eI.next();
					myprint("," + e.getName());
				}
			} else {
				myprint("{outer");
			}
			myprint("}  Marked by ");
			if (Xz.get(z) != null) {
				myprint(Xz.get(z).size() + " points: ");
				myprint(Xz.get(z).toString() + newline);
			}
		}
		myprint(newline);
	}

	public TreeMap<Zone, TreeSet<IntersPoint>> getXz() {
		return Xz;
	}

	public void setXz(TreeMap<Zone, TreeSet<IntersPoint>> xz) {
		Xz = xz;
	}

	public boolean isWF() {
		return isWF;
	}

	public HashSet<Area> getSplits() {
		return splits;
	}


}

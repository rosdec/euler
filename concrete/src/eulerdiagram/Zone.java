package eulerdiagram;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Zone extends TreeSet<Ellipse> implements Comparable<Zone>, Serializable {

	private static final long serialVersionUID = -5247404056310754557L;

	public Zone(){
		super();
	}

	public Zone(Zone z){
		super(z);
	}
	
	public Zone(HashSet<Ellipse> t){
		super(t);
	}
	
	@Override
	public boolean equals(Object o) {
		Zone b = (Zone) o;
		if (this.size()!= b.size()) return false; 
		Iterator<Ellipse> ie = this.descendingIterator();
		Iterator<Ellipse> jf = b.descendingIterator();
		while(ie.hasNext()){
			Ellipse e= ie.next();
			Ellipse f= jf.next();
			if (!e.equals(f)) return false;
		}	
		return true;
	}

	public int compareTo(Zone b) {
		if (this.size()>b.size()) return 1;
		if (this.size()<b.size()) return -1;
		Iterator<Ellipse> ie = this.descendingIterator();
		Iterator<Ellipse> jf = b.descendingIterator();
		while(ie.hasNext()){
			Ellipse e= ie.next();
			Ellipse f= jf.next();
			if (!e.equals(f)){
				return (e.compareTo(f));
			}
		}	
		return 0;
	}

	public int hashCode(){
		int hash=0;
		Iterator<Ellipse> ie = this.descendingIterator();
		while(ie.hasNext()){
			Ellipse e= ie.next();
			hash^=e.hashCode();
		}	
		return hash;
	}

	public boolean isUniverse() {
		return (this.size()==0);
	}
}

package eulerdiagram;

import java.awt.Point;
import java.io.Serializable;

import javax.swing.Icon;

public class Item implements Serializable{
	private static final long serialVersionUID = 104747660311740228L;
	
	private Point position;
	private String name;
	private String path;
	private Icon icon;
	private boolean isGenerated;

	public Item() {
	}
		
	public Item(Point position, String name, String path, Icon icon) {
		super();
		this.position  = new Point(position);
		this.name = name;
		this.path = path;
		this.icon = icon;
		this.isGenerated=false;
	}
	
	
	public Item(Point position, String name, String path) {
		super();
		this.position  = new Point(position);
		this.name = name;
		this.path = path;
		this.icon = null;
		this.isGenerated=false;
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void setPosition(Point position) {
		this.position = new Point(position);
	}
	
	public Icon getIcon() {
		return icon;
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
	}
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	
	public boolean equals(Object _a) {
		Item i = (Item) _a;
		return (i.path.equals(this.path));
	}

	public int hashCode() {
		return (path.hashCode());
	}

	public boolean isGenerated() {
		return isGenerated;
	}

	public void setGenerated(boolean isGenerated) {
		this.isGenerated = isGenerated;
	}
}

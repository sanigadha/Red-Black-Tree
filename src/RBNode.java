//Class to define and declare red black tree node, its objects can act as nodes. YO
//@uthor_Naman_Rajpal 

public class RBNode {

	

	long ID;

	int count;

	int color;

	RBNode left;

	RBNode right;

	
	private final static int LEFT = -1;
	private final static int RIGHT = 1;
	//..Starting Constructors - 3types of constructors 
	//..declared below  
	public RBNode(long id, int count,int color,RBNode left,RBNode right) {
		this.setID(id);
		this.setCount(count);
		this.setColor(color);
		//1 for black
		//0 for red
		this.setLeft(left);
		this.setRight(right);
	}

	public RBNode(long id,int count) {
		this.setID(id);
		this.setCount(count);
		this.setColor(0);//red
		this.setLeft(null);
		this.setRight(null);
	}
	
	public RBNode() {
		this.setID(0);
		this.setCount(0);
		this.setColor(0);//red
		this.setLeft(null);
		this.setRight(null);
	}
	
	public RBNode(int color) {
		this.setID(0);
		this.setCount(0);
		this.setColor(color);//red or black
		this.setLeft(null);
		this.setRight(null);
	}
	
    // any non-zero argument returns right
    RBNode link(int direction) {
        return (direction == LEFT) ? this.left : this.right;
    }
    // any non-zero argument sets right
    RBNode setLink(int direction, RBNode n) {
        if (direction == LEFT) this.left = n;
        else this.right = n;
        return n;
    }

	//getters and setters for the parameters

	public long getID() {
		return ID;
	}


	 
	public void setID(long iD) {
		ID = iD;
	}
	

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public void changeCount(int change) {
		this.count += change;
	}


	public int getColor() {
		return color;
		//returns 1 for black
		//returns 0 for red
	}


	public void setColor(int color) {
		this.color = color;
	}


	public RBNode getLeft() {
		return left;
	}

	public void setLeft(RBNode left) {
		this.left = left;
	}


	public RBNode getRight() {
		return right;
	}

	public void setRight(RBNode right) {
		this.right = right;
	}
	//getters setters end here
	
	
	
	
}

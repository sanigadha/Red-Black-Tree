import java.util.*;
import java.io.*;

class RBtree {
	// Red Black Tree class to define Red Black tree.
	// Its object will be used by main class to insert new nodes from root

	static final int BLACK = 1; // Black is 1
	static final int RED = 0;

	private final static int LEFT = -1;
	private final static int RIGHT = 1;

	/**
	 * @uml.property  name="head"
	 * @uml.associationEnd  
	 */
	private RBNode head; // head is used as root here

	/**
	 * @uml.property  name="sum"
	 */
	private int sum;

	public RBtree() {
		this.head = null;
	}

	// rotation functions starting here

	// Function to implement single rotation
	// lr -1 for left
	// lr 1 for right
	public RBNode Rsingle(RBNode root, int lr) {

		RBNode save;
		if (lr == -1) {
			save = root.right;
			root.right = save.left;
			save.left = root;
		} else {
			save = root.left;
			root.left = save.right;
			save.right = root;
		}

		root.color = RED;
		save.color = BLACK;

		return save;
	}

	// Uses two single rotations
	// Double rotation lr identifies the direction
	// lr is -1 for left
	// lr is 1 for right
	public RBNode Rdouble(RBNode root, int lr) {
		if (lr == -1) {
			root.right = Rsingle(root.right, 1);

			return Rsingle(root, -1);
		} else {
			root.left = Rsingle(root.left, -1);

			return Rsingle(root, 1);
		}

	}

	// TreeIncrease Search
	public void TreeIncreaseSearch(long id, int count) {

		RBNode result;
		result = search(head, id); // trying to find the node first
		if (result == null) {
			Treeinsert(id, count); // insert if its not found
			System.out.println(count);
		} else {
			result.count += count;
			System.out.println(result.count);
		}

	}

	// returns null if nothing if found
	private RBNode search(RBNode root, long id) {
		if (root == null) // base case
			return null;
		if (root.ID == id) {
			return root;
		} else {
			if (id > root.ID)
				return search(root.right, id); // recursing to right
			else
				return search(root.left, id); // recursing to left
		}
	}

	private RBNode parentsearch(RBNode root, long id) {
		if (root.left == null && root.right == null)
			return null;
		if ((root.left != null && root.left.ID == id)
				|| (root.right != null && root.right.ID == id)) {
			return root;
		} else {
			if (id > root.ID)
				return parentsearch(root.right, id);
			else
				return parentsearch(root.left, id);
		}
	}

	// Insertion functions start here
	// Insertion Code
	// Two functions here

	// This is a caller function for insert
	public void Treeinsert(long id, int count) {

		// System.out.println("<treeinsert>Before " + tree.ID +" "+tree.color );
		// System.out.println("<treeinsert>Inserting " + id +" "+count );
		this.head = insert(head, id, count);
		this.head.color = BLACK;
		// System.out.println("<treeinsert>After " + root.ID +" "+root.color );

	}

	private RBNode insert(RBNode root, long id, int count) {

		if (root == null) {
			root = new RBNode(id, count);
			// System.out.println("THis run" + id + " " + count);
			// root.left = new RBNode(BLACK);
			// root.right = new RBNode(BLACK);
		} else if (id != root.ID) {

			if (id < root.ID) {
				// this case wont ever run in insert test cases because ids are
				// sorted
				// When insertion has to take place on left side
				root.left = insert(root.left, id, count);

				// rebalancing code starts here
				if (root.left != null && root.left.color == RED) {
					if (root.right != null && root.right.color == RED) {
						/*
						 * Case 1 for left (RRb) when sibling and parent are
						 * both RED
						 */
						root.setColor(RED);
						root.left.setColor(BLACK);
						root.right.setColor(BLACK);
					} else {
						/* Cases 2 & 3 for left */
						if (root.left.left != null
								&& root.left.left.color == RED) {
							root = Rsingle(root, 1);
						} else if (root.left.right != null
								&& root.left.right.color == RED) {
							root = Rdouble(root, 1);
						}
					}
				}

			} else {

				// when insertion has to take place on right side
				root.right = insert(root.right, id, count);

				if (root.right != null && root.right.color == RED) {
					if (root.left != null && root.left.color == RED) {
						/* Case 1 for left */
						root.setColor(RED);
						root.right.setColor(BLACK);
						root.left.setColor(BLACK);
					} else {
						/* Cases 2 & 3 for left */
						if (root.right.right != null
								&& root.right.right.color == RED) {
							root = Rsingle(root, -1);
						} else if (root.right.left != null
								&& root.right.left.color == RED) {
							root = Rdouble(root, -1);
						}
					}
				}

			}

		}

		return root;
	}

	// Removal functions start here
	// I have used top-down balancing approach where
	// balancing is done by pushing a red node down

	boolean remove(long data) {
		if (this.head != null) {
			final RBNode header = new RBNode(-1, -1); /* False tree root */
			RBNode cur, parent, grandpa; /* Helpers */
			RBNode found = null; /* Found item */
			int dir = RIGHT;

			/* Set up helpers */
			cur = header;
			grandpa = parent = null;
			cur.setLink(RIGHT, this.head);

			/* Search and push a red down */
			while (cur.link(dir) != null) {
				int last = dir;

				/* Update helpers */
				grandpa = parent;
				parent = cur;
				cur = cur.link(dir);
				dir = cur.ID < data ? RIGHT : LEFT;

				/* Save found node */
				if (cur.ID == data)
					found = cur;

				/* Push the red node down */
				if (cur.color == BLACK
						&& (cur.link(dir) != null && cur.link(dir).color == BLACK)) {
					if ((cur.link(-1 * dir).color == RED))
						parent = parent.setLink(last, Rsingle(cur, dir));
					else if (cur.link(-1 * dir).color == BLACK) {
						RBNode s = parent.link(-1 * last);

						if (s != null) {
							if (s.link(-1 * last).color == BLACK
									&& s.link(last).color == BLACK) {
								/* Color flip */
								parent.color = BLACK;
								s.color = RED;
								cur.color = RED;

							} else {
								int dir2 = grandpa.link(RIGHT) == parent ? RIGHT
										: LEFT;

								if ((s.link(last).color == RED))
									grandpa.setLink(dir2, Rdouble(parent, last));
								else if ((s.link(-1 * last).color == RED))
									grandpa.setLink(dir2, Rsingle(parent, last));

								/* Ensure correct coloring */
								cur.color = grandpa.link(dir2).color = RED;
								grandpa.link(dir2).link(LEFT).color = BLACK;
								grandpa.link(dir2).link(RIGHT).color = BLACK;

							}
						}
					}
				}

			}

			/* Replace and remove if found */
			if (found != null) {
				found.ID = cur.ID;
				found.count = cur.count;
				parent.setLink(parent.link(RIGHT) == cur ? RIGHT : LEFT,
						cur.link(cur.link(LEFT) == null ? RIGHT : LEFT));
			}

			/* Update root and make it black */
			this.head = header.link(RIGHT);
			if (this.head != null)
				this.head.color = BLACK;
		}

		return true;
	}

	void range(RBNode node, long id1, long id2, int count) {

		/* base case */
		if (node == null) {
			return;
		}

		// recurse for left subtree first If
		// root->data is greater than k1, then only we can get o/p keys in left
		// subtree

		if (id1 < node.ID) {
			range(node.left, id1, id2, count);
		}

		/* if mode's id lies in range, then sum it up */
		if (id1 <= node.ID && id2 >= node.ID) {
			// count = count+node.count;
			this.sum += node.count;

		}

		// If node.id is smaller than id2, then only we can get ids in
		// right subtree

		if (id2 > node.ID) {
			range(node.right, id1, id2, count);
		}
	}

	// Below funtion finds out the inorder succesor
	// of the given node
	private RBNode inOrderSuccessor(RBNode node) {

		// if right is not then
		// the left most node in right subtree
		// will be the inorder succser
		if (node.right != null) {
			return min(node.right);
		}

		// if the node has no children
		// then we will search upwards
		RBNode p = parentsearch(head, node.ID);
		while (p != null && node == p.right) {
			node = p;
			p = parentsearch(head, p.ID);
		}
		return p;
	}

	private RBNode inOrderPredeccessor(RBNode node) {

		// if left is not null then
		// the right most node in right subtree
		// will be the inorder succser
		if (node.left != null) {
			return max(node.left);
		}

		// if the node has no children
		// then we will search upwards
		RBNode p = parentsearch(head, node.ID);
		while (p != null && node == p.left) {
			node = p;
			p = parentsearch(head, p.ID);
		}
		return p;
	}

	// Given a non-empty tree, return the minimum data value found
	// in that tree. Note that the entire tree does not need to be searched.

	private RBNode min(RBNode node) {
		RBNode current = node;

		/* loop down to find the leftmost leaf */
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	private RBNode max(RBNode node) {
		RBNode current = node;

		/* loop down to find the leftmost leaf */
		while (current.right != null) {
			current = current.right;
		}
		return current;
	}

	// Below are the functions that are to implemented for
	// the project requiremnets
	public void Increase(long id, int count) {
		TreeIncreaseSearch(id, count);
	}

	public void Reduce(long id, int count) {
		RBNode result;
		result = search(head, id);
		if (result != null) {
			result.count -= count;
			if (result.count <= 0) {
				remove(result.ID);
				System.out.println(0);
			} else {
				System.out.println(result.count);
			}
		} else {
			System.out.println(0);
		}
	}

	public void Count(long id) {
		RBNode result;
		result = search(head, id);
		if (result != null)
			System.out.println(result.count);
		else
			System.out.println(0);
	}

	public void InRange(long id1, long id2) {
		int count = 0;
		range(this.head, id1, id2, count);
		System.out.println(this.sum);
		this.sum = 0;
	}

	// this function is used if null node is input in next
	public RBNode nextnullsearch(long id) {
		RBNode cur = this.head;
		RBNode result = null;
		int flag = 0;

		while (flag == 0) {
			if (cur.ID < id) {
				if (id < inOrderSuccessor(cur).ID) {
					flag = 1;
					return inOrderSuccessor(cur);
				} else {
					cur = cur.right; //moving right if not in range
				}
			} else {
				if (id > inOrderPredeccessor(cur).ID) {
					flag = 1;
					return cur;
				} else {
					cur = cur.left; //moving left if not in range
				}
			}
		}

		return result;
	}

	// this function is used if null node is input in previous
	public RBNode previousnullsearch(long id) {
		RBNode cur = this.head;
		RBNode result = null;
		int flag = 0;

		while (flag == 0) {
			if (cur.ID < id) {
				RBNode s = inOrderSuccessor(cur);
				if (s == null || id < s.ID) {
					flag = 1;
					return cur;
				} else {
					cur = cur.right; // move to right if id is not in range
				}
			} else {
				if (id > inOrderPredeccessor(cur).ID) {
					flag = 1;
					return inOrderPredeccessor(cur);
				} else {
					cur = cur.left; // move to left if id is not in range
				}
			}
		}

		return result;
	}

	public void Next(long id) {

		if (search(head, id) == null) { // if id is not present in the tree

			RBNode r = null;
			if (id <= max(head).ID) { // if ID is less than maximum in
										// the tree it is worth searching
										// otherwise it will stay null and print
										// 0 0.

				r = nextnullsearch(id);

			}

			if (r != null)
				System.out.println(r.ID + " " + r.count);
			else
				System.out.println(0 + " " + 0);

		} else // if id is present in the tree
		{
			RBNode r = inOrderSuccessor(search(head, id));
			if (r != null)
				System.out.println(r.ID + " " + r.count);
			else
				System.out.println(0 + " " + 0);
		}
	}

	// Previous function
	public void Previous(long id) {

		if (search(head, id) == null) {

			RBNode r = null;
			if (id >= min(head).ID) {

				r = previousnullsearch(id);

			}

			if (r != null)
				System.out.println(r.ID + " " + r.count);
			else
				System.out.println(0 + " " + 0);

		} else {
			RBNode r = inOrderPredeccessor(search(head, id));
			if (r != null)
				System.out.println(r.ID + " " + r.count);
			else
				System.out.println(0 + " " + 0);
		}
	}

}

public class bbst {

	private static Scanner sc; // scanner to take file input
	private static long n;
	private static Scanner input; // scanner to take input from input stream

	public static void main(String args[]) {

		RBtree tree = new RBtree();

		// try catch to grab IO exceptions related to file input
		try {
			if (args.length > 0) {
				sc = new Scanner(new File(args[0]));
				n = sc.nextLong();
				// System.out.println(n);
				// debug_printing_code just printing to check its take the right

				// input loop!
				while (n > 0) {
					long id = sc.nextLong();
					int count = sc.nextInt();
					// Treeinsert function to build tree from sorted input
					tree.Treeinsert(id, count);
					n--;
				}

				// scanner to take input from standard input stream
				input = new Scanner(System.in);
				String command;
				int flag = 0;

				while (flag == 0) {
					command = input.next();
					switch (command) {
					case "increase":
						tree.Increase(input.nextLong(), input.nextInt());
						break;
					case "reduce":
						tree.Reduce(input.nextLong(), input.nextInt());
						break;
					case "inrange":
						tree.InRange(input.nextLong(), input.nextLong());
						break;
					case "count":
						tree.Count(input.nextLong());
						break;
					case "next":
						tree.Next(input.nextLong());
						break;
					case "previous":
						tree.Previous(input.nextLong());
						break;

					case "quit":
						flag = 1;
						break;
					default:
						break;

					}
				}

			} else {

				System.out.println("No file given");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}

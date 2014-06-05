package game;

import java.awt.List;
import java.util.ArrayList;

public class TreeNode {
	String data;
	TreeNode parent;
	String id;
	public char[][] board  = new char[3][3];	
	ArrayList<TreeNode> childrens	= new ArrayList<TreeNode>();

	public TreeNode(String id, TreeNode myparent, String pData, char[][] newBoard){
		this.data	= pData;
		this.parent	= myparent;
		this.board	= newBoard;
		this.id		= id;
	}
	public String toString(){
		return String.valueOf(data);
	}
	
}

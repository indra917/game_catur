package game;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

public class ForestTree {
	TreeNode root;
	ArrayList<TreeNode> forest 		= new ArrayList<TreeNode>();
	ArrayList<TreeNode> dfsList		= new ArrayList<TreeNode>();
	ArrayList<Integer> bestScore	= new ArrayList<Integer>();
	char[] arrow		= new char[]{'w','e','d','c','x','z','a','q'};
	
	public ForestTree(){
		root=null;
	}
	public boolean addChild(String id, String parent, String data, char[][] newBoard){
		if(root==null){
			TreeNode t	= new TreeNode(id, root, data, newBoard);
			root		= t;
			forest.add(t);
			return true;
		}else{
			TreeNode p = findParent(parent);
			TreeNode t	= new TreeNode(id, p, data, newBoard);
			p.childrens.add(t);
			forest.add(t);
			return true;
		}
	}
	public TreeNode findParent(Object key){
		for(TreeNode t:forest){
			if(t.id.equals(key))
				return t;
		}
		return null;
	}
	public void preOrder(TreeNode root){
		dfsList.add(root);
		for(TreeNode child : root.childrens){
			preOrder(child);
		}
	}
	public void postOrder(TreeNode root){
		for(TreeNode child:root.childrens){
			postOrder(child);
		}
		dfsList.add(root);
	}
	public boolean contain_piece(char board, char[]value){
		for(char pc: value){
			if(board == pc) return true;
		}
		return false;
	}
	
	public int winPossibility(char[][] board, int[][] winPosition, char[]value, int state){
		int nWin=0;
		//for(int[] winPos:winPosition){
		for(int x=1-state; x<=6+(1-state); x++){
			int jum=0;
			//for(int winP: winPos){
			for(int y=0;y<3;y++){
				int m	= (winPosition[x][y]-1)/3; //Baris
				int n	= (winPosition[x][y]-1)%3; //Kolum				
				if(board[m][n]=='0'||contain_piece(board[m][n], value)) jum++;
			}
			System.out.print(jum+"\n");
			nWin	= nWin+(jum/3);//(jum==3?1:0);
		}
		return nWin;
	}
	
	public int winPos(int state, char[][] board){
			player p		= new player();
			int n			= 0;
			//for(char turn: p.piece[state]){
				n			= winPossibility(board, p.WIN, p.piece[state], state);
			//}
			//if(n == 3) return 1;
			return n;
	}
	public int nodeValue(char[][] board){
		int alpbet;
		alpbet	= winPos(0,board)-winPos(1,board);
		return alpbet;
	}
	public boolean pos_step(Object a){
		if(a.equals(-1)){
			//System.out.print("contain -1 "+"\n");
			return false;
		}
		return true;
	}
	
	public int max_value(int alpha, int beta, int i, int bestValue){//Beta adalah nilai terbaik
		if(alpha>beta) return i;
		return bestValue;
	}
	public int min_value(int alpha, int beta, int i, int bestValue){//Beta adalah nilai terbaik
		if(alpha<beta) return i;
		return bestValue;
	}
	public int alphaBeta(TreeNode forest,int state){
		int bestValue	= Integer.parseInt(forest.childrens.get(0).id);//index
		for(int i=0;i<forest.childrens.size();i++){
			if(state == 0){
				bestValue	= max_value(Integer.parseInt(forest.childrens.get(i).data), Integer.parseInt(forest.childrens.get(bestValue).data), i, bestValue);
				System.out.print("Nilai alpha "+Integer.parseInt(forest.childrens.get(i).data)+" ");
				System.out.print("best value"+bestValue+"\n");
			}else{
				bestValue	= min_value(Integer.valueOf(forest.childrens.get(i).data), Integer.valueOf(forest.childrens.get(bestValue).data), i, bestValue);
			}
		}
		return bestValue;
	}
	public int prediction(char[][] inputBoard, int state, int startId, String parent){
		player pl		=  new player(); 
		for(char ar:this.arrow){
			for(char p: pl.piece[state]){
				//input		= inputBoard.clone();
				char[][] input = new char[3][3];
				for(int i=0; i<3; i++){
					System.arraycopy(inputBoard[i], 0, input[i], 0, 3);
				}
				Object newBoard		= pl.next(ar, p, state, input);
				if(pos_step(newBoard)){
					int data	= nodeValue((char[][])newBoard);
					if(data!=-1){
						startId++;
						addChild(Integer.toString(startId), parent, Integer.toString(data), (char[][])newBoard);
					}
				}
			}
		}
		return 1;
	}
	public static void main(String []args){
		ForestTree f		= new ForestTree();
		player p			= new player();

		Queue<Integer>noteId	= new LinkedList<Integer>();
		f.addChild("0", null, "0", p.array);
		
		f.prediction(f.forest.get(0).board, 0, 0, "0");
			int i					= f.alphaBeta(f.forest.get(0),0);
			System.out.print(i+"\n");
			TreeNode fTree			= f.forest.get(i);
			char[][] board			= fTree.board;
			String pr				=f.forest.get(i).id;
			System.out.print("parentnya"+i+"\n");
			char[][] input = new char[3][3];
			for(int j=0; j<3; j++){
				System.arraycopy(board[j], 0, input[j], 0, 3);
			}
			f.prediction(input, 1, i, pr);
			

		for(TreeNode child:f.forest){
			//System.out.print(child.data + " ");
			
			System.out.println("board "+child.id+" parent: "+child.parent+" AlphaBeta"+child.data);
			for(char[] row:child.board){
				for(char data:row){	
					System.out.print(data);
				}
				System.out.print("\n");
			}
			
		}
		
		
		
		
	}
}


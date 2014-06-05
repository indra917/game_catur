package game;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.List;
import java.awt.Point;
import java.io.*;

public class player{
	//private static Point final Object = 1;
	public static char[][] array = new char[][]{{'1','2','3'},{'0','0','0'},{'7','8','9'}};
	public static int[][] cur_pos = new int[][]{{1,2,3},{7,8,9}};
	public static final int[][] WIN = new int[][]{{1,2,3},{4,5,6},{1,4,7},{2,5,8},{3,6,9},{3,5,7},{1,5,9},{7,8,9}};
	public static char[][] piece  = new char[][]{{'1','2','3'},{'7','8','9'}};	
	public String full_name;
	public player(){
		this.full_name		= "No Name";

	}
	
	public void cetak(){
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
            	System.out.print(player.array[i][j]);
            }
            System.out.print("\n");
        }
	}
	public void set_name(String name){
		if(name != "-1"){
			this.full_name		= name;
			//return 1;
		}
		//return -1;
	}	

	public static int[] check_pos(char a, char[][] array){
		int[] pos;
		pos = new int[2];
        for (int y=0;y<3;y++) {
            for (int x=0;x<3;x++) {
            	if(array[y][x]==a){
            		pos[0] = y;
            		pos[1] = x;
            		System.out.print("\n");
            	}
            }
            //System.out.print("\n");
        }
        return(pos);
        //return pos[];
	}
	
	
	public static int position(int[] state){		
		return (3*state[0])+(state[1]+1);
	}
	public Object repo(int newp, char a, char[][] array){
		int m	= (newp-1)/3; //Baris
		int n	= (newp-1)%3; //Kolum
	
		if(array[m][n]=='0'){
	        for (int y=0;y<3;y++) {
	            for (int x=0;x<3;x++) {
	            	if(array[y][x]==a){
	            		array[y][x] = '0';	            		
	            	}
	            	
	            }
	        }
			array[m][n]	=  a;
			return array;	        
		}else{
			return -1;
		}
	}
	public Object next(char q, char a, int i, char[][] array){
		int[] state = check_pos(a, array);
		int p	= position(state);
		int newp = -1;
		if (q == 'w' && state[0]%3!=0){			
			newp	= (p - 3) % (3 * 3);
		}
		else if(q == 'a' && state[1]%3!=0){
			newp	= (p - 1);
		}
		else if(q == 'd' && state[1]/2!=1){
			newp	= (p + 1) ;
		}
		else if(q == 'x' && state[0]/2!=1){
			newp	= (p + 3) % 10;
		}
		else if(q == 'c' && ((state[0]==0 && state[1]==0)||(state[0]==1 && state[1]==1))){
			newp	= (p + 4) % 10;
		}
		else if(q == 'z' && ((state[0]==0 && state[1]==2)||(state[0]==1 && state[1]==1))){
			newp	= (p + 2) % 10;
		}
		else if (q == 'q' && ((state[0]==2 && state[1]==2)||(state[0]==1 && state[1]==1))){			
			newp	= (p - 4) % (3 * 3);
		}
		else if (q == 'e' && ((state[0]==2 && state[1]==0)||(state[0]==1 && state[1]==1))){			
			newp	= (p - 2) % (3 * 3);
		}
		Object newPost;
		if(newp == -1) return -1;
		newPost = repo(newp, a, array);
		if(newPost.equals(-1))return -1;
		player.cur_pos[i][((Character.getNumericValue(a))%6)-1] = newp;
		/*
		System.out.print("Posisi \n");
		for(int[] row: player.cur_pos){
			for(int data:row){
				System.out.print(data);
			}
			System.out.print("\n");
		}
		*/
		//System.out.println(player.cur_pos[i]);
		return newPost;
	}
	public static int contain(int value, int i){
		for(int valid:WIN[i]){
			if(valid == value)return 1;
		}
		return 0;
	}
	public static int check_win(int i){
		for(int x=1-i; x<=6+(1-i); x++){
			int flag =0;
			for(int j=0; j<3 ; j++){
				flag = flag+contain(cur_pos[i][j], x);
			}
			if(flag==3)return 1;
		}
		return -1;
	}
	public static boolean check_permission(char in, int i){
		for(int valid:piece[i]){
			if(valid == in)return true;
		}
		return false;		
	}
	public int step(int i){
    	Scanner input = new Scanner(System.in);
    	
    	char q= input.next().charAt(0);
    	if (q==-1)return -1;
    	
    	char a= input.next().charAt(0);
    	if(!check_permission(a,i))return -1;
    	
    	Object out	= this.next(q, a, i, this.array);
    	if(out.equals(Integer.valueOf(-1)))return -1;
    	this.array = (char[][])out;
    	if(player.check_win(i)==1)return 1;
		return 0;
		
	}
	public void inisial_nama(){
    	Scanner input = new Scanner(System.in);
    	String name		= input.next();
    	set_name(name);
    	//return set_name(name);
	}	
	public static void main(String []args) throws IOException{
		player[] p	= new player[2];
		
		p[0] 		= new player();
		p[1] 		= new player();		
		//p[0].cetak();
		for(int i=0; i<2; i++){
			p[i].inisial_nama();
		}		
		int a = 0;
		for(int i=0; i<2; i++){
			System.out.print(p[i].full_name);
			System.out.print("\n");
		}
		int i = 1;
		while(a == 0){
			System.out.print("Sekarang giliran "+p[i].full_name+"\n");			
			a	= p[i].step(i);
			p[i].cetak();
			if(a==1){
				System.out.println(p[i].full_name+" Menang!");
			}			
			i	= 1-i;
		}
		
		
	}
	

}

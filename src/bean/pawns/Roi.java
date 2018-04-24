package bean.pawns;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import bean.Ecouteur;
import ui.Window;

public class Roi extends Piece{
	private boolean chess = false;
	private ArrayList<Piece> assailants = new ArrayList<Piece>();
	
	public Roi(Color c,int i, int j){
		String path ="../images/Roi";
		col = c;
		rep="Roi ";
		if(c == Color.WHITE){
			path +="B.png";
			rep +="blanc";
		}else{
			path +="N.png";
			rep +="noir";
		}
		img = new ImageIcon(path);
		x=i;
		y=j;
		dep = new ArrayList<Dimension>();		
	}
	
	
	// Dimension :Le premier champ de constructeur est la largeur et le second est la hauteur
	@Override
	public void motionScheme(int a){
		ArrayList<Dimension> list = new ArrayList<Dimension>();
		Case t;
		if((x-1)>-1){
			t = Window.getTabCase(x-1,y);
			if(t.getOccupant()==null){
				list.add(new Dimension(y,x-1));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){	
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y,x-1));
					}
				}else{
					list.add(new Dimension(y,x-1));
				}
			}
		}
		
		if(((y+1)<8)&&((x-1)>-1)){
			t = Window.getTabCase(x-1,y+1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+1,x-1));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){	
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y+1,x-1));
					}
				}else{
					list.add(new Dimension(y+1,x-1));
				}
			}
		}
		
		if((y+1)<8){
			t = Window.getTabCase(x,y+1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+1,x));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y+1,x));
					}
				}else{
					list.add(new Dimension(y+1,x));
				}
			}
		}
		
		if(((y+1)<8)&&((x+1)<8)){
			t = Window.getTabCase(x+1,y+1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+1,x+1));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y+1,x+1));
					}
				}else{
					list.add(new Dimension(y+1,x+1));
				}
			}
		}
		
		if((x+1)<8){
			t = Window.getTabCase(x+1,y);
			if(t.getOccupant()==null){
				list.add(new Dimension(y,x+1));
			}else if((t.getOccupant().getColor()!=this.col)){
				if(a!=1){	
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y,x+1));	
					}
				}else{
					list.add(new Dimension(y,x+1));
				}
			}
		}
		
		if(((y-1)>-1)&&((x+1)<8)){
			t = Window.getTabCase(x+1,y-1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-1,x+1));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y-1,x+1));
					}
				}else{
					list.add(new Dimension(y-1,x+1));
				}
			}
		}
		
		if((y-1)>-1){
			t = Window.getTabCase(x,y-1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-1,x));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){	
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y-1,x));
					}
				}else{
					list.add(new Dimension(y-1,x));
				}
			}
		}
		
		if(((y-1)>-1)&&((x-1)>-1)){
			t = Window.getTabCase(x-1,y-1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-1,x-1));
			}else if(((t.getOccupant().getColor()!=this.col))){
				if(a!=1){
					if((t.getOccupant().getRep().substring(0,3).equals("Roi"))){
						t.getOccupant().inChess(this);
					}else{
						list.add(new Dimension(y-1,x-1));
					}
				}else{
					list.add(new Dimension(y-1,x-1));
				}
			}
		}
		
		this.dep = list;
	}
	
	@Override
	public void unsetChess(){
		Case c = Window.getTabCase(this.x,this.y);
		c.setBorder(null);
		this.chess = false;
		this.assailants.clear();
	}
	
	@Override
	public void inChess(Piece p){
		if(!this.assailants.contains(p)){
			//System.out.println("Entree dans 'inChess' de Roi");
			Case c = Window.getTabCase(this.x,this.y);
			c.setBorder(BorderFactory.createLineBorder(Color.RED,3));
			this.chess = true;
			this.assailants.add(p);
			Ecouteur.inChess(this.getColor());
		}
	}
	
	public ArrayList<Piece> getAssailants(){
		return this.assailants;
	}	
}

package bean.pawns;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ui.Window;

public class Tour extends Piece{
	
	public Tour(Color c,int i, int j){
		String path ="../images/Tour";
		col = c;
		rep="Tour ";
		if(c == Color.WHITE){
			path +="B.png";
			rep +="blanche";
		}else{
			path +="N.png";
			rep +="noire";
		}
		img = new ImageIcon(path);
		x=i;
		y=j;
		dep = new ArrayList<Dimension>();
	}
	
	
	// Dimension :Le premier champ de constructeur est la largeur et le second est la hauteur
	@Override
	public void motionScheme(int a){
		int i;
		ArrayList<Dimension> list = new ArrayList<Dimension>();
		Case t;
		boolean ok=true;
		
		i = this.x-1;
		while((i>-1)&&ok){
			t = Window.getTabCase(i,y);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(y,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(y,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(y,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			--i;
		}
		
		ok=true;
		i = this.x+1;
		while((i<8)&&ok){
			t = Window.getTabCase(i,y);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(y,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(y,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(y,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			++i;
		}
		
		ok=true;
		i = this.y-1;
		while((i>-1)&&ok){
			t = Window.getTabCase(x,i);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(i,x));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(i,x));
						ok=false;
					}
				}else{
					list.add(new Dimension(i,x));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			--i;
		}
		
		
		ok=true;
		i = this.y+1;
		while((i<8)&&ok){
			t = Window.getTabCase(x,i);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(i,x));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){	
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){//piece dans la case == Roi
						t.getOccupant().inChess(this);
						ok=false;
					}else{//occupant de la case different d'un Roi
						list.add(new Dimension(i,x));
						ok=false;
					}
				}else{
					list.add(new Dimension(i,x));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){//case occupee par une piece de meme couleur
				ok=false;
			}
			++i;
		}
		
		this.dep = list;
	}
}

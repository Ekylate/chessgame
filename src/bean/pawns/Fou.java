package bean.pawns;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ui.Window;

public class Fou extends Piece{
	
	public Fou(Color c,int i, int j){
		String path ="../images/Fou";
		col = c;
		rep = "Fou ";
		if(c == Color.WHITE){
			path +="B.png";
			rep += "blanc\n";
		}else{
			path +="N.png";
			rep += "noir\n";
		}
		img = new ImageIcon(path);
		x=i;
		y=j;
		dep = new ArrayList<Dimension>();
	}
	
	
	// Dimension :Le premier champ de constructeur est la largeur et le second est la hauteur
	@Override
	public void motionScheme(int a){
		int i,j;
		Case t;
		boolean ok=true;
		ArrayList<Dimension> list = new ArrayList<Dimension>();
		
		
		/*en bas a droite*/
		i = this.x+1;
		j = this.y+1;
		while((i<8)&&(j<8)&&ok){
			t = Window.getTabCase(i,j);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(j,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){	
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(j,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(j,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			++i;
			++j;
		}
		
		/*en haut a droite*/
		ok=true;
		i = this.x-1;
		j = this.y+1;
		while((i>-1)&&(j<8)&&ok){
			t = Window.getTabCase(i,j);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(j,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){	
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(j,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(j,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			--i;
			++j;
		}
		
		/*en bas a gauche*/
		ok=true;
		i = this.x+1;
		j = this.y-1;
		while((i<8)&&(j>-1)&&ok){
			t = Window.getTabCase(i,j);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(j,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){
						t.getOccupant().inChess(this);
						ok=false;
					}else{
						list.add(new Dimension(j,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(j,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){
				ok=false;
			}
			++i;
			--j;
		}
		
		/*en haut a gauche*/      
		ok=true;
		i = this.x-1;
		j = this.y-1;
		while((i>-1)&&(j>-1)&&ok){
			t = Window.getTabCase(i,j);
			
			if((t.getOccupant()==null)){
				list.add(new Dimension(j,i));
			}else if(((t.getOccupant().getColor()!=this.col))){//Si la case est occupee par une piece de couleur differente
				if(a!=1){
					if(t.getOccupant().getRep().substring(0,3).equals("Roi")){//piece dans la case == Roi
						t.getOccupant().inChess(this);
						ok=false;
					}else{//occupant de la case different d'un Roi
						list.add(new Dimension(j,i));
						ok=false;
					}
				}else{
					list.add(new Dimension(j,i));
					ok=false;
				}
			}else if(((t.getOccupant().getColor()==this.col))){//case occupee par une piece de meme couleur
				ok=false;
			}
			--i;
			--j;
		}
		
		this.dep = list;		
	}
}

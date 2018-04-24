package bean.pawns;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import ui.Window;

public class Cavalier extends Piece{
	
	
	
	public Cavalier(Color c,int i, int j){
		String path ="../images/Cavalier";
		col = c;
		rep = "Cavalier ";
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
		Case t;
		
		ArrayList<Dimension> list = new ArrayList<Dimension>();
		
		if(((y+2)<8) && ((x+1)<8)){
			t = Window.getTabCase(x+1,y+2);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+2,x+1));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y+2,x+1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y+2,x+1));
					}
				}
			}
		}
		
		if(((y+1)<8)&&((x+2)<8)){
			t = Window.getTabCase(x+2,y+1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+1,x+2));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y+1,x+2));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y+1,x+2));
					}
				}
			}
		}
		
		if(((y-1)>-1)&&((x+2)<8)){
			t = Window.getTabCase(x+2,y-1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-1,x+2));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y-1,x+2));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y-1,x+2));
					}
				}
			}
		}
		
		if(((y-2)>-1)&&((x-1)>-1)){
			t = Window.getTabCase(x-1,y-2);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-2,x-1));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y-2,x-1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y-2,x-1));
					}
				}
			}
		}
			
		if(((y-2)>-1)&&((x+1)<8)){
			t = Window.getTabCase(x+1,y-2);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-2,x+1));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y-2,x+1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y-2,x+1));
					}
				}
			}
		}
		
		if(((y-1)>-1)&&((x-2)>-1)){
			t = Window.getTabCase(x-2,y-1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y-1,x-2));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y-1,x-2));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y-1,x-2));
					}
				}
			}
		}
		
		if(((y+1)<8)&&((x-2)>-1)){
			t = Window.getTabCase(x-2,y+1);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+1,x-2));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y+1,x-2));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y+1,x-2));
					}
				}
			}
		}
		
		if(((y+2)<8)&&((x-1)>-1)){
			t = Window.getTabCase(x-1,y+2);
			if(t.getOccupant()==null){
				list.add(new Dimension(y+2,x-1));
			}else{
				if((t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							list.add(new Dimension(y+2,x-1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						list.add(new Dimension(y+2,x-1));
					}
				}
			}
		}
		
		this.dep = list;
	}
}

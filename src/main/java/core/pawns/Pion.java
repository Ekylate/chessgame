package core.pawns;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import ui.graphics.Window;
import ui.listeners.Ecouteur;

public class Pion extends Piece{
	
	private boolean firstMove;//true si pion n'a pas encore bouge, false sinon
	
	public Pion(Color c,int i, int j){
		String path = "../images/Pion";
		firstMove=true;
		col = c;
		rep = "Pion ";
		if(c == Color.WHITE){
			rep += "blanc\n";
			path += "B.png";
		}else{
			rep += "noir\n";
			path += "N.png";
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
		if(col==Color.BLACK){
			if((x+1)<8){
				if(Window.getTabCase(x+1,y).getOccupant()==null){
					list.add(new Dimension(y,x+1));
					
					if((this.firstMove==true)&&(Window.getTabCase(x+2,y).getOccupant()==null)){
						list.add(new Dimension(y,x+2));
					}
				}
			}
		}else{//col==Color.WHITE
			if((x-1)>-1){
				if(Window.getTabCase(x-1,y).getOccupant()==null){
					list.add(new Dimension(y,x-1));
					
					if((this.firstMove==true)&&(Window.getTabCase(x-2,y).getOccupant()==null)){
						list.add(new Dimension(y,x-2));
					}
				}
			}
			
			
		}
		
		
		this.dep = list;
		this.eatable(a);
	}
	
	public void eatable(int a){
		Case t;
		int i = this.x;
		int j = this.y;
		
		//System.out.println("Entree dans le eatable");
		if(this.col == Color.BLACK){
			//System.out.println("Piece noire");
			if(((i+1)<8) && ((i+1)>-1) && ((j-1)<8) && ((j-1)>-1)){
				t = Window.getTabCase((i+1),(j-1));
				if((t.getOccupant()!=null)&&(t.getOccupant().getColor()!= this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							this.dep.add(new Dimension(j-1,i+1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						this.dep.add(new Dimension(j-1,i+1));
					}
				}
			}
			
			if(((i+1)<8) && ((i+1)>-1) && ((j+1)<8) && ((j+1)>-1)){
				t = Window.getTabCase((i+1),(j+1));
				if((t.getOccupant()!=null)&&(t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							this.dep.add(new Dimension(j+1,i+1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						this.dep.add(new Dimension(j+1,i+1));
					}
				}
			}
		}else if(this.col == Color.WHITE){
			if(((i-1)<8) && ((i-1)>-1) && ((j-1)<8) && ((j-1)>-1)){
				t = Window.getTabCase((i-1),(j-1));
				if((t.getOccupant()!=null)&&(t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							this.dep.add(new Dimension(j-1,i-1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						this.dep.add(new Dimension(j-1,i-1));
					}
				}
			}
				
			if(((i-1)<8) && ((i-1)>-1) && ((j+1)<8) && ((j+1)>-1)){
				t = Window.getTabCase((i-1),(j+1));
				if((t.getOccupant()!=null)&&(t.getOccupant().getColor()!=this.col)){
					if(a!=1){
						if(!(t.getOccupant().getRep().equals("Roi"))){
							this.dep.add(new Dimension(j+1,i-1));
						}else{
							t.getOccupant().inChess(this);
						}
					}else{
						this.dep.add(new Dimension(j+1,i-1));
					}
				}
			}
		}
	}
	
	public void refresh(int i,int j){
		this.x = i;
		this.y = j;
		
		if((i==0)||(i==7)){	
			JOptionPane ask = new JOptionPane();
			String[] pieces = {"Reine","Fou","Tour","Cavalier"};
			int choix;
			choix = ask.showOptionDialog(null,"Veuillez choisir la piece qui remplacera votre Pion :","Promotion:",JOptionPane.OK_OPTION,JOptionPane.QUESTION_MESSAGE, null, pieces,pieces[0]);
			Ecouteur.promotion(this,choix);
		}
		
		if(this.firstMove==true){
			this.firstMove=false;
		}
	}
}

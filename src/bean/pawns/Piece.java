package bean.pawns;

//import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import bean.Ecouteur;
import ui.Window;

public abstract class Piece{
	protected ImageIcon img;
	protected String rep;
	protected Color col;
	protected int x,y;
	protected ArrayList<Dimension> dep;
	// Dimension :Le premier champ de constructeur est la largeur et le second est la hauteur
	public Piece(){
	}
	
	
	
	public abstract void motionScheme(int a);
	
	/***************************************************/
	/****		   ACCESSEURS			****/
	/***************************************************/
	
	public ImageIcon getImage(){
		return this.img;
	}
	
	public String getRep(){
		return rep;
	}
	
	public Color getColor(){
		return this.col;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public ArrayList<Dimension> getDep(){
		return this.dep;
	}
		
	
	
	
	
	/***************************************************/
	/****		    MUTATEURS			****/
	/***************************************************/
	
	public void setImg(ImageIcon image){
		this.img = image;
	}
	
	public void setRep(String s){
		this.rep = s;
	}
	
	public void setColor(Color c){
		this.col = c;
	}
	
	public void setX(int i){
		this.x=i;
	}
	
	public void setY(int i){
		this.y=i;
	}
	
	public void setDep(ArrayList<Dimension> d){
		this.dep = d;
	}
	
	public void removeOneDep(Dimension d){
		this.dep.remove(d);
	}

	
	
	/***************************************************/
	/****		    METHODES			****/
	/***************************************************/
	
	
	public void isSelected(boolean real){
		int i;
		Case[][] t = Window.getTab();
		this.motionScheme(0);
		
		if(real){
			if(Ecouteur.getColorPlayerChessState(this.col)){
				this.isSafe();
			}
		}
		
		for(i=0;i<this.dep.size();++i){
			t[(int)(this.dep.get(i).getHeight())][(int)(this.dep.get(i).getWidth())].setBorder(BorderFactory.createLineBorder(Color.magenta,3));
		}
	}
	
	/* Sert a voir si les possibilites de chaque piece peuvent liberer le roi de l'echec */
	public boolean isSafe(){
		boolean contain=false,result = false;
		Piece savedPiece = null;
		Case[][] tmp = Window.getTab();
		Roi king = Ecouteur.getJoueur(this.col).getKing();
		Dimension dKing = new Dimension(king.getY(),king.getX());
		Dimension saveD = null;
		
		ArrayList<Dimension> toDelete = new ArrayList<Dimension>();
		ArrayList<Dimension> opponentsDep = new ArrayList<Dimension>();
		int i,j;//coordonnees de la prochaine case de deplacement a tester
		Color c;//Couleur des pieces adverses
		
		if(this.col == Color.BLACK){
			c = Color.WHITE;
		}else{
			c = Color.BLACK;
		}

		
		tmp[this.getX()][this.getY()].freeCase();
		for(Dimension d : this.dep ){
			i = (int)(d.getHeight());
			j = (int)(d.getWidth());
			if(tmp[i][j].getOccupant() != null){
				savedPiece = tmp[i][j].getOccupant();
			}
				
			tmp[i][j].setOccupant(this);
			//Pour chaque piece adverse, recuperer les deplacements possibles
			for(Piece pi : Window.getColorPieces(c,tmp)){
				pi.motionScheme(1);
				opponentsDep.addAll(pi.getDep());
			}
				
			//Si la piece sur laquelle on appelle la fonction n'est pas un roi
			if(!this.getRep().substring(0,3).equals("Roi")){
				for(Dimension dim : opponentsDep){
					if(dim.equals(dKing)){
						contain = true;
					}
				}
			//Si la piece sur laquelle on appelle la fonctoin est un roi
			}else{
				for(Dimension dim : opponentsDep){
					if(dim.equals(new Dimension(j,i))){
						contain = true;
					}
				}
			}
				
			opponentsDep.clear();	
				
			if(contain){
				toDelete.add(d);
			}else{
				result = true;
			}
			tmp[i][j].freeCase();
			contain = false;
				
			if(savedPiece != null){
				tmp[i][j].setOccupant(savedPiece);
				savedPiece = null;
			}
		}
		
		tmp[this.getX()][this.getY()].setOccupant(this);
		
		for(Dimension d : toDelete){
			this.dep.remove(d);
		}
		
		return result;
	}
	
	public boolean isIn(Dimension d){
		return this.dep.contains(d);
	}
	
	public void inChess(Piece p){
	}
	
	public void unsetChess(){
	}
	
	public void refresh(int i,int j){
		this.x = i;
		this.y = j;
	}
	
	public String toString(){
		return("La piece est un/une : "+this.rep+"\r\n\tcouleur : "+this.col+"\r\n\tx = "+this.x+"\r\n\ty = "+this.y );
	}
}

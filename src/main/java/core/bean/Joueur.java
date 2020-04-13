package core.bean;
import java.awt.Color;
import java.util.ArrayList;

import core.pawns.Case;
import core.pawns.Cavalier;
import core.pawns.Fou;
import core.pawns.Piece;
import core.pawns.Reine;
import core.pawns.Roi;
import core.pawns.Tour;
import ui.graphics.Window;
import ui.listeners.Ecouteur;

public class Joueur{
	private String name;
	private Color c;
	private ArrayList<Piece> own;
	private ArrayList<Piece> lost_ones;
	private boolean echec=false;//true si joueur en echec
	
	public Joueur(){
	}
	
	public Joueur(Color prop,String s){
		c = prop;
		name = s;
		own = new ArrayList<Piece>();
		lost_ones = new ArrayList<Piece>();
	}
	
	/* Ecrite pour recuperer les pieces si sauvegarde implementee */
	public void initPieces(ArrayList<Piece> p){
		own = p;
	}
	
	public ArrayList<Piece> getLostPieces(){
		return this.lost_ones;
	}
	
	public ArrayList<Piece> getPieces(){
		return own;
	}
	
	public void addPiece(Piece p){
		this.own.add(p);
	}
	
	public void loosePiece(Piece p){
		this.own.remove(p);
		this.lost_ones.add(p);
	}
	
	public Color getColor(){
		return this.c;
	}
	
	public String getName(){
		return this.name;
	}
	
	public boolean getStateChess(){
		return this.echec;
	}
	
	public Roi getKing(){
		int i=0;
		
		while(!(this.own.get(i).getRep().substring(0,3).equals("Roi"))){
			++i;
		}
		return (Roi)(this.own.get(i));
	}	
	
	public void promotion(Piece p, int ch){
		int i,j;
		Color tmp_c;
		String str;
		Case tmp_case;
		Piece tmp_p=null;
		
		i = p.getX();
		j = p.getY();
		tmp_case = Window.getTabCase(i,j);
		tmp_c = p.getColor();
		tmp_case.freeCase();
		own.remove(p);
		
		switch(ch){
		case 0 : //Reine
			tmp_p = new Reine(tmp_c,i,j); 
			break;
		case 1 : //Fou
			tmp_p = new Fou(tmp_c,i,j);
			break;
		case 2 : //Tour
			tmp_p = new Tour(tmp_c,i,j);
			break;
		case 3 : //Cavalier
			tmp_p = new Cavalier(tmp_c,i,j);
			break;
		default:
			break;
		}
		
		own.add(tmp_p);
		tmp_case.setOccupant(tmp_p);
	}
	
	public void inChess(){
		String str;
		int i;
		boolean not_echec_mate=false;
		this.echec = true;
		i=0;
		for(Piece p : this.own){
			p.motionScheme(0);
			not_echec_mate = ( p.isSafe() || not_echec_mate); 
		}
		
		if(!not_echec_mate){
			Ecouteur.stop();
			str = "\r\nLe joueur "+ this.getName()+" a perdu par echec et mate";
			Window.updateCom(str,str.length());
		}else{
			str = "\r\nLes deux joueurs peuvent continuer a jouer\r\nPas d'echec&mate";
			Window.updateCom(str,str.length());
		}
	}
	
	public void unsetChess(){
		Window.unpaint(true);
		this.echec = false;
		Roi king = this.getKing();
		king.unsetChess();
	}
}

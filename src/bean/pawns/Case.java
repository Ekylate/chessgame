package bean.pawns;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Case extends JButton{
	private Color couleur;
	private Dimension pos;
	private Piece occupant;
	
	
	public Case(Color c,Dimension d){
	//public Case(Color c){
		super();
		couleur = c;
		pos = d;
		this.setBackground(c);
		occupant = null;
	}
	
	public Case(Color c, Piece p,Dimension d){
	//public Case(Color c, Piece p){
		super();
		couleur = c;
		pos = d;
		this.setBackground(c);
		occupant = p;
		if(couleur == Color.BLACK)
			this.setForeground(Color.WHITE);
		else
			this.setForeground(Color.BLACK);
	}
	
	public void freeCase(){
		this.occupant = null;
		this.setIcon(new ImageIcon(""));
	}
	
	public void setColor(Color c){
		this.couleur = c;
		this.setBackground(c);
	}
	
	public Color getColor(){
		return this.couleur;
	}
	
	public void setOccupant(Piece p){
		this.occupant = p;
		this.setIcon(p.getImage());
	}
	
	public Piece getOccupant(){
		return this.occupant;
	}
	
	public Dimension getPos(){
		return this.pos;
	}
}

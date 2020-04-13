package ui.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import core.bean.Joueur;
import core.pawns.Case;
import core.pawns.Piece;
import ui.graphics.Window;

public class Ecouteur implements ActionListener{
		private static Joueur[] t;
		private Piece s=null;
		private Case tmp,anc;
		private static int state;
		private boolean playerDisplayed=false;
		
		public Ecouteur(Joueur[] play){
			state= 0;
			t = play;
		}
		
	  	public void actionPerformed(ActionEvent ae){
	  		String str;
	  		Joueur no_current = new Joueur(),current = new Joueur();
	  		tmp = (Case)ae.getSource();

	  		if(state>=0){
				if(state%2 == 0)
				{/* Tour de jeu des pieces blanches*/
					
					if(t[0].getColor()==Color.WHITE){
						if(!playerDisplayed){
							str = "\n\nTour des pieces blanches c'est au tour de "+t[0].getName()+"\n\n";
							Window.updateCom(str,str.length());
						}
						current = t[0];
						no_current = t[1];
					}else if((t[1].getColor()==Color.WHITE)){
						if(!playerDisplayed){
							str = "\n\nTour des pieces blanches c'est au tour de "+t[1].getName()+"\n\n";
							Window.updateCom(str,str.length());
						}
						current = t[1];
						no_current = t[0];
					}
					playerDisplayed=true;
					if((tmp.getOccupant()!=null)){/* Si la case est occupee */
						if(s!=null){ /* Si on a une piece en memoire */
							if(tmp.getOccupant().getColor() == Color.WHITE)/* Si la couleur est blanche */
							{
								Window.unpaint(false);
								anc = tmp;
								s = tmp.getOccupant();
								tmp.getOccupant().isSelected(true);
							}else{
								if(s.isIn(tmp.getPos()))
								{
									no_current.loosePiece(tmp.getOccupant());
									tmp.freeCase();
									tmp.setOccupant(s);
									anc.freeCase();
									anc = null;
									s.refresh((int)(tmp.getPos().getHeight()),(int)(tmp.getPos().getWidth()));
									s.motionScheme(0);
									s = null;
									Window.updatePP(no_current.getLostPieces().get(no_current.getLostPieces().size()-1).getRep(),no_current.getLostPieces().get(no_current.getLostPieces().size()-1).getRep().length());
									state++;
									playerDisplayed=false;
									if(current.getStateChess()){
										Window.unpaint(true);
										current.unsetChess();
									}else{
										Window.unpaint(false);
									}
								}
							}
						}else{/* s vide */
							if(tmp.getOccupant().getColor() == Color.WHITE)
							{/* Si la couleur est blanche */
								
								Window.unpaint(false);
								anc = tmp;
								s = tmp.getOccupant();
								tmp.getOccupant().isSelected(true);
							}
						}
					}else{/* Si la case n'est pas occupee */
						if((s!=null)&&(s.isIn(tmp.getPos()))){
							tmp.setOccupant(s);
							anc.freeCase();
							anc = null;
							s.refresh((int)(tmp.getPos().getHeight()),(int)(tmp.getPos().getWidth()));
							s.motionScheme(0);
							s = null;
							state++;
							playerDisplayed=false;
							if(current.getStateChess()){
								Window.unpaint(true);
								current.unsetChess();
							}else{
								Window.unpaint(false);
							}
						}
					}
				}else{
				/* Tour de jeu des pieces noires*/				
					
					if(t[0].getColor()==Color.BLACK){
						if(!playerDisplayed){
							str = "Tour des pieces noires c'est au tour de "+t[0].getName()+"";
							Window.updateCom(str,str.length());
						}
						current = t[0];
						no_current = t[1];
					}else if(t[1].getColor()==Color.BLACK){
						if(!playerDisplayed){
							str = "Tour des pieces noires c'est au tour de "+t[1].getName()+"";
							Window.updateCom(str,str.length());
						}
						current = t[1];
						no_current = t[0];
					}
					playerDisplayed=true;
					if((tmp.getOccupant()!=null)){/* Si la case est occupee */
						if(s!=null){ /* Si on a une piece en memoire */
							if(tmp.getOccupant().getColor() == Color.BLACK){
							/* Si la couleur est noire */
								Window.unpaint(false);
								anc = tmp;
								s = tmp.getOccupant();
								tmp.getOccupant().isSelected(true);
							}else{
							/* Si la couleur est blanche */
								if(s.isIn(tmp.getPos()))
								{
									no_current.loosePiece(tmp.getOccupant());
									tmp.freeCase();
									tmp.setOccupant(s);
									anc.freeCase();
									anc = null;
									s.refresh((int)(tmp.getPos().getHeight()),(int)(tmp.getPos().getWidth()));
									s.motionScheme(0);
									s = null;
									Window.updatePP(no_current.getLostPieces().get(no_current.getLostPieces().size()-1).getRep(),no_current.getLostPieces().get(no_current.getLostPieces().size()-1).getRep().length());
									state++;
									playerDisplayed=false;
									if(current.getStateChess()){
										Window.unpaint(true);
										current.unsetChess();
									}else{
										Window.unpaint(false);
									}
								}
							}
						}else{
						/*Si on a pas de piece en memoire */
							if(tmp.getOccupant().getColor() == Color.BLACK)
							{/* Si la couleur est noire */
								
								Window.unpaint(false);
								anc = tmp;
								s = tmp.getOccupant();
								tmp.getOccupant().isSelected(true);
							}
						}
					}else{/* Si la case est vide */
						if((s!=null)&&(s.isIn(tmp.getPos()))){
						/* Si pas de piece en memoire et que destination possible*/
							tmp.setOccupant(s);
							anc.freeCase();
							anc = null;
							s.refresh((int)(tmp.getPos().getHeight()),(int)(tmp.getPos().getWidth()));
							s.motionScheme(0);
							
							s = null;
							
							state++;
							playerDisplayed=false;
							if(current.getStateChess()){
								Window.unpaint(true);
								current.unsetChess();
							}else{
								Window.unpaint(false);
							}
						}
					}
				}
			}
	  	}
	  	public static void stop(){
	  		state=-2;
	  	}
	  	public static void inChess(Color c){
	  		if(t[0].getColor() == c){
	  			t[0].inChess();
	  		}else if(t[1].getColor() == c){
	  			t[1].inChess();
	  		}
	  	}
	  	
	  	public static void promotion(Piece p,int ch){
	  		Joueur j = getJoueur(p.getColor());
	  		j.promotion(p,ch);
	  	}
	  	
	  	public static boolean getColorPlayerChessState(Color c){
	  		if(t[0].getColor()==c){
	  			return (t[0].getStateChess());
	  		}else if(t[1].getColor()==c){
	  			return (t[1].getStateChess());
	  		}
	  		return false;
	  	}
	  	
	  	public static Joueur getJoueur(Color c){
	  		if(t[0].getColor()==c){
	  			return t[0];
	  		}else{
	  			return t[1];
	  		}
	  	}
	}

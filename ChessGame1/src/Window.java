import javax.swing.*;
import java.awt.*;
import java.awt.event.*; 
import java.util.*;

public class Window extends JFrame{
	private JLayeredPane Layer;
	
	private static Case[][] tab = new Case[8][8];
	private Ecouteur ear;
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private Joueur[] players = new Joueur[2];
	private boolean firstGame=true;
	
	private JPanel echiq;
	private static int pp_length=0;
	private static int com_length=0;
	
	private static JTextArea pieces_perdues = new JTextArea();
	private static JTextArea com = new JTextArea();
	private JMenuBar menu = new JMenuBar();
	private JMenu new_part = new JMenu("Nouvelle Partie");
	private JMenuItem hvh = new JMenuItem("Humain vs Humain");
	private JMenuItem hvm = new JMenuItem("Humain vs Machine");
	private JButton tuto = new JButton("Didacticiel");
	private JButton rules = new JButton("Regle");
	private JButton exit = new JButton("Quitter");
	
	public void initJoueurs(String[] p, int rang){
		/* players[0] = joueur humain */
		Piece tmp;
		Color[] colors = new Color[2];
		if(rang==0){
			colors[0] = Color.BLACK;
			colors[1] = Color.WHITE;
		}else{// Si choix n'est pas egal a 0, il est forcement a 1 a cause des conditions de boucle
			colors[0] = Color.WHITE;
			colors[1] = Color.BLACK;
		}
		
		players[0] = new Joueur(colors[0],p[0]);
		players[1] = new Joueur(colors[1],p[1]);
		
		ear = new Ecouteur(players);
		
		for(Case [] t : Window.tab){
			for(Case c : t){
				c.addActionListener(ear);
				if(c.getOccupant() != null){
					tmp = c.getOccupant();
					if(players[0].getColor()==tmp.getColor()){
						players[0].addPiece(tmp);
					}else{
						//Si la couleur ne correspond pas a celle du joueur humain alors elle correspond forcement a celle de la piece
						players[1].addPiece(tmp);						
					}
				}
				
			}
		}
	}
	
	public void initTab(boolean reuse){
		//if (indiceCase%10) = 0 case d'apres de meme couleur
		Color previousButtonColor = null;
		Color currentButtonColor = null;
		Piece occ = null;
		int cpt = 0;
		int i,j;//i = lignes, j = colonnes
		
		
		
		for(i=0;i<8;++i){
			for(j=0;j<8;++j){
				occ=null;
				//System.out.println("\r\nCreation de la " + cpt +"e case j="+j+" i="+i);
				if((i==0)&&(j==0)){
					currentButtonColor = Color.WHITE;
				}else if((i!=0)&&(j==0)){//1ere ligne et different de la premiere colonne
					currentButtonColor = previousButtonColor;
				}else{
					if(previousButtonColor == Color.WHITE){
						currentButtonColor = Color.BLACK;
					}else if(previousButtonColor == Color.BLACK){
						currentButtonColor = Color.WHITE;
					}
				}
				if(i == 0){
					if(j==0 || j==7){
						occ = new Tour(Color.BLACK,i, j);
					}
					if(j==1 || j==6){
						occ = new Cavalier(Color.BLACK,i, j);
					}
					if(j==2 || j==5){
						occ = new Fou(Color.BLACK,i, j);
					}
					if(j==3){
						occ = new Reine(Color.BLACK,i, j);
					}
					if(j==4){
						occ = new Roi(Color.BLACK,i, j);
					}
				}
				if(i == 7){
					if(j==0 || j==7){
						occ = new Tour(Color.WHITE,i, j);
					}
					if(j==1 || j==6){
						occ = new Cavalier(Color.WHITE,i, j);
					}
					if(j==2 || j==5){
						occ = new Fou(Color.WHITE,i, j);
					}
					if(j==3){
						occ = new Reine(Color.WHITE,i, j);
					}
					if(j==4){
						occ = new Roi(Color.WHITE,i, j);
					}
				}
				if(i == 1 ){
					occ = new Pion(Color.BLACK,i, j);
				}else{
					if(i==6)
						occ = new Pion(Color.WHITE,i, j);
				}
				
				if(!reuse){
					Window.tab[i][j] = new Case(currentButtonColor,occ, new Dimension(j,i));
				}
				
				if(occ!=null){
					
					if(reuse){
						Window.tab[i][j].setOccupant(occ);
					}
					
					Window.tab[i][j].setIcon(occ.getImage());
					pieces.add(occ);
				}
				
				if(!reuse){
					echiq.add(Window.tab[i][j]);
				}
				
				++cpt;
				previousButtonColor = currentButtonColor;
			}
		}
		
		for(i=0;i<this.pieces.size();++i){
			pieces.get(i).motionScheme(0);
		}
		Window.unpaint(false);

	}
	
	public static void updateCom(String s, int a){
		com.append(s);
		com_length += a;
	}
	
	public static void updatePP(String s, int a){
		pieces_perdues.append(s);
		pp_length += a;
	}
	
	public static void eraseCom(){
		com.replaceRange("",0,com_length);
		com_length = 0;
	}
	
	public static void erasePP(){
		pieces_perdues.replaceRange("",0,pp_length);
		pp_length = 0;
	}
	
	public static Case[][] getTab(){
		return tab;
	}
	
	public static ArrayList<Piece> getColorPieces(Color c,Case[][] t){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		int i,j;
		
		for(i=0;i<8;++i){
			for(j=0;j<8;++j){
				if(t[i][j].getOccupant()!=null){
					if(t[i][j].getOccupant().getColor()==c){
						pieces.add(t[i][j].getOccupant());
					}
				}
			}
		}
		return pieces;
	}
	
	public static Case getTabCase(int x,int y){
		Case t = tab[x][y];
		return t;
	}
	
	public static void unpaint(boolean a){
		int i,j;
		
		for(i=0;i<8;++i){
			for(j=0;j<8;++j){
				if(!a){//N'efface pas les bordures de cases du aux echecs
					if(tab[i][j].getOccupant() == null){
						tab[i][j].setBorder(null);
					}else if((!tab[i][j].getOccupant().getRep().equals("Roi"))){
						tab[i][j].setBorder(null);
					}
				}else{
					tab[i][j].setBorder(null);
				}
				
			}
		}
	}
	
	public void Question(int nbJoueursHumains,boolean reuse){
		/***********initialisation du joueur**************/
		JOptionPane jop = new JOptionPane();//, jop2 = new JOptionPane();
    		String[] pseudos = new String[2];
    		//pseudos[0] = new String();
    		do{
    			pseudos[0]=jop.showInputDialog(null, "Veuillez saisir votre pseudonyme :", "Identite du Joueur 1 : humain", JOptionPane.QUESTION_MESSAGE);
    		}while(pseudos[0]==null||pseudos[0].replaceAll("\\s","").equals(""));
    		
    		
		
		String[] coul = {"Noir", "Blanc"};
	        int rang = -1;
	        do{
	        	//rang = jop2.showOptionDialog(null,"Choisissez la couleur de vos pieces :","Choix de couleur", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,coul,coul[1]);
	        	rang = jop.showOptionDialog(null,"Choisissez la couleur de vos pieces :","Choix de couleur", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,coul,coul[1]);
	        }while(rang<0);
	        
	        if(nbJoueursHumains>1){
    			do{
				pseudos[1]=jop.showInputDialog(null, "Veuillez saisir votre pseudonyme :", "Identite du Joueur 2 : humain", JOptionPane.QUESTION_MESSAGE);
			}while(pseudos[1]==null||pseudos[1].replaceAll("\\s","").equals(""));	
    		}else{
    			pseudos[1] = "IA";
    		}
	        /***********fin d'inialisation du joueur**********/
	        initJoueurs(pseudos,rang);
	        initTab(reuse);
	        com.setText(null);
	}
	
	
	class Menu implements ActionListener{
		int size=0,compt=0;
		public Menu(){
		}
	
		public void actionPerformed(ActionEvent e) {
 
			String s="",action = e.getActionCommand();
		 
			if(action.equals("Quitter")){
			    System.exit(0);
			}else if(action.equals("Regle")){
				JTextArea textArea = new JTextArea("But du jeu et principes généraux \n\n- Le but du jeu est la capture du roi adverse. C'est l'échec et mat.  \n- Les blancs commencent toujours la partie. \n- Chaque joueur joue un coup à tour de rôle. (jouer est obligatoire, on ne peut pas passer son tour). \n- Une partie d'échecs peut se terminer par un match nul \n\n Les règles officielles\n\n Les règles du jeu d'échecs sont très simples et identiques dans le monde entier ! Les règles officielles \n sont établies par La FIDE (Fédération Internationale Des Echecs). \nVous trouverez le texte de référence (en anglais) sur le site de la FIDE. \n Le site de la fédération française propose une traduction de ce texte dans la rubrique arbitrage \n Pour savoir jouer il faudra maîtriser les points suivants \nla position initiale de l'échiquier et des pièces \nle déplacement des pièces (roi, dame, fou, cavalier, tour, pion) \nle roque (cas particulier du déplacement du roi) \nla prise en passant (cas particulier du déplacement du pion) \nla promotion \n\nla mise en échec et ses conséquences \n\nles cas de parties nulles \n\nUne partie d'échecs se jouera souvent avec une pendule afin de limiter la durée de la partie. \nIl faudra donc également connaître le règlement du jeu à la pendule. \nIl existe également un règlement pour le jeu par correspondance. \n\nLa mise en échec et ses conséquences \n\nL' échec \nLorsque le roi est en prise, il est en échec. Le joueur devra obligatoirement parer cet échec. S'il n'existe \n aucun coup pour soustraire le roi à l'échec, le roi est alors échec et mat et la partie est perdue. \nRemarque : l'annonce de l'échec est facultative. \nIl existe trois façons de parer un échec: \nDéplacer le roi. \nPrendre la pièce qui met le roi en échec. \nCouvrir l'échec en intercalant une autre pièce. \n Il est interdit de mettre son roi dans une position d'échec. Si c'est le seul coup jouable le roi est dans \n une position de pat et la partie est nulle. \n\nL'échec et mat \nL'expression 'Echec et mat' signifie que le roi adverse reçoit un échec imparable. Cela constitue le \n dernier coup d'une partie d'échecs.  \n\nLes cas de parties nulles \n\nUne partie est déclarée nulle dans les cas suivants \nsur un commun accord des joueurs. \nsi une position se répète 3 fois à l'identique (consécutive ou non). \nsur un échec perpétuel. \nsi 50 coups sont joués sans prise et sans mouvement de pion.  \nsi le matériel est insuffisant pour mater. \n\n");
				JScrollPane scrollPane = new JScrollPane(textArea);  
				textArea.setLineWrap(true);  
				textArea.setWrapStyleWord(true); 
				scrollPane.setPreferredSize( new Dimension( 500, 500 ) );
				
				JOptionPane d = new JOptionPane();
				d.showMessageDialog( null, scrollPane,"Regle", JOptionPane.INFORMATION_MESSAGE);
				
			}else if(action.equals("Didacticiel")){
				for(Case[] t : tab){
					for(Case c : t){
						c.freeCase();
						c.removeActionListener(ear);
					}
				}
				JButton suivant = new JButton("Suivant");
				suivant.setBounds(775,550,100,50);
				suivant.addActionListener(this);
				Layer.add(suivant);				
				initTab(true);
				erasePP();
				eraseCom();				
			}else if(action.equals("Suivant")){
				switch(compt){
				case 0:
					Window.unpaint(true);
					tab[6][4].getOccupant().isSelected(false);
					s="Bienvenue dans le didacticiel destine\nau jeu d'echec\n\nVous ne pouvez interagir avec aucune piece\nmais vous pouvez aller a l'explication suivante\npar le bouton 'suivant' ci-dessous\n\nLes deplacements suivants constituent ce que\nl'on appelle le 'coup du berger'\nce coup est le plus rapide a realiser\nil faut arriver a bloquer le roi adverse\navec un fou ou une reine\ntout en protegeant celle-ci\n\nDonc lorsque l'on clique sur une piece\n les deplacements qui lui sont possibles\n sont representes par la bordure rose\nsur les cases concernees\n\n";
					size += s.length();
					com.append(s);
					++compt;
					break;
				case 1:
					Window.unpaint(true);
					tab[4][4].setOccupant(tab[6][4].getOccupant());
					tab[6][4].freeCase();
					s = "Dans une partie d'echec c'est au joueur\nqui possede les blancs qui commence\ndans le 'coup du berger' il faut tout\nd'abord libere la Reine et le fou blanc\n\n";
					size += s.length();
					com.append(s);
					++compt;
					break;
				case 2:
					Window.unpaint(true);
					tab[1][4].getOccupant().isSelected(false);
					s = "Ensuite c'est au tour des noirs\n\n";
					size += s.length();
					com.append(s);
					++compt;
					break;
				case 3:
					Window.unpaint(true);
					tab[3][4].setOccupant(tab[1][4].getOccupant());
					tab[1][4].getOccupant().refresh(3,4);
					tab[1][4].freeCase();
					++compt;
					break;
				case 4:
					Window.unpaint(true);
					tab[7][5].getOccupant().isSelected(false);
					s="ensuite nous positionnons le fou \nqui lui ne peut ce deplacer qu'en diagonal\n\n";
					size += s.length();
					com.append(s);
					++compt;
					break;
				case 5:
					Window.unpaint(true);
					tab[4][2].setOccupant(tab[7][5].getOccupant());
					tab[7][5].getOccupant().refresh(4,2);
					tab[7][5].freeCase();
					++compt;
					break;
				case 6:
					Window.unpaint(true);
					tab[0][5].getOccupant().isSelected(false);
					++compt;
					break;
				case 7:
					Window.unpaint(true);
					tab[3][2].setOccupant(tab[0][5].getOccupant());
					tab[0][5].getOccupant().refresh(3,2);
					tab[0][5].freeCase();
					++compt;
					break;
				case 8:
					Window.unpaint(true);
					tab[7][3].getOccupant().isSelected(false);
					++compt;
					break;
				case 9:
					Window.unpaint(true);
					tab[3][7].setOccupant(tab[7][3].getOccupant());
					tab[7][3].getOccupant().refresh(3,7);
					tab[7][3].freeCase();
					++compt;
					break;
				case 10:
					Window.unpaint(true);
					tab[0][6].getOccupant().isSelected(false);
					++compt;
					break;
				case 11:
					Window.unpaint(true);
					tab[2][5].setOccupant(tab[0][6].getOccupant());
					tab[0][6].getOccupant().refresh(2,5);
					tab[0][6].freeCase();					
					++compt;
					break;
				case 12:
					Window.unpaint(true);
					tab[3][7].getOccupant().isSelected(false);
					++compt;
					break;
				case 13:
					Window.unpaint(true);
					tab[1][5].freeCase();
					tab[1][5].setOccupant(tab[3][7].getOccupant());
					tab[3][7].getOccupant().refresh(1,5);
					tab[3][7].freeCase();
					tab[0][4].setBorder(BorderFactory.createLineBorder(Color.RED,3));
					++compt;
					break;
				case 14:
					Window.unpaint(true);
					s = "Voici la fin du didacticiel et\nle joueur noir a perdu face\n au joueur blanc\npar echec & mate ^^\nSi vous voulez faire un nouveau\n didacticiel, cliquez sur 'Suivant'\nsinon cliquez sur l'un des autres boutons.";
					size += s.length();
					com.append(s);
					++compt;
					break;
				case 15:
					Window.unpaint(true);
					for(Case[] t : tab){
						for(Case c : t){
							c.freeCase();
							c.removeActionListener(ear);
						}
					}
					
					com.replaceRange("",0,size);
					initTab(true);
					
					compt=0;
					size=0;
					break;
				default:
					break;
				}
			}else{//"Nouvelle Partie"
				for(Case[] t : tab){
					for(Case c : t){
						c.freeCase();
						c.setBorder(null);
						if(c.getActionListeners().length == 0){
							c.addActionListener(ear);
						}
					}
				}
				pieces = new ArrayList<Piece>();
				players = new Joueur[2];
				erasePP();
				eraseCom();
				
				if(action.equals("Humain vs Humain")){
					Question(2,true);
				}else if(action.equals("Humain vs Machine")){
					Question(1,true);
				}
			}
        	}
	}
	
	
	/////CONSTRUCTEUR
	public Window(){
		Menu read;
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,700);
		this.setLocationRelativeTo(null);
		
		Layer = new JLayeredPane();
	
		this.setVisible(true);
		
		
		read = new Menu();
		
	/*********** Menu **********/

		hvh.addActionListener(read);
		hvm.addActionListener(read);
		tuto.addActionListener(read);
		rules.addActionListener(read);
		exit.addActionListener(read);
		
		new_part.add(hvh);
		new_part.add(hvm);
		menu.add(new_part);
		menu.add(tuto);
		menu.add(rules);
		menu.add(exit);
		
		setJMenuBar(menu);
		
	/*********** fin Menu **********/
		
		
		
		echiq = new JPanel(new GridLayout(8,8));
		echiq.setBounds(50,30,600,600);
		initTab(false);
		JLabel a = new JLabel("8");
		JLabel b = new JLabel("7");
		JLabel c = new JLabel("6");
		JLabel d = new JLabel("5");
		JLabel e = new JLabel("4");
		JLabel f = new JLabel("3");
		JLabel g = new JLabel("2");
		JLabel i = new JLabel("1");
		JLabel j = new JLabel("a");
		JLabel k = new JLabel("b");
		JLabel l = new JLabel("c");
		JLabel m = new JLabel("d");
		JLabel n = new JLabel("e");
		JLabel o = new JLabel("f");
		JLabel p = new JLabel("g");
		JLabel q = new JLabel("h");
		a.setBounds(30,25,100,100);
		b.setBounds(30,100,200,100);
		c.setBounds(30,170,300,100);
		d.setBounds(30,250,400,100);
		e.setBounds(30,320,500,100);
		f.setBounds(30,400,600,100);
		g.setBounds(30,480,700,100);
		i.setBounds(30,550,800,100);
		j.setBounds(80,-30,100,100);
		k.setBounds(155,-30,100,100);
		l.setBounds(230,-30,100,100);
		m.setBounds(300,-30,100,100);
		n.setBounds(380,-30,100,100);
		o.setBounds(450,-30,100,100);
		p.setBounds(520,-30,100,100);
		q.setBounds(600,-30,100,100);
		
		int v=ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
		int h=ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER; 
		
		/*piece_perdu.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
		com.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));*/
		
		JScrollPane scroll_1 = new JScrollPane(pieces_perdues,v,h);
		scroll_1.setBounds(675, 50, 300,200);
		
		JScrollPane scroll_2 = new JScrollPane(com,v,h);
		scroll_2.setBounds(675,300,300,200);
		Layer.add(a);
		Layer.add(b);
		Layer.add(c);
		Layer.add(d);
		Layer.add(e);
		Layer.add(f);
		Layer.add(g);
		Layer.add(i);
		Layer.add(j);
		Layer.add(k);
		Layer.add(l);
		Layer.add(m);
		Layer.add(n);
		Layer.add(o);
		Layer.add(p);
		Layer.add(q);
		Layer.add(echiq);
		Layer.add(scroll_1);
		Layer.add(scroll_2);
		
		this.getContentPane().add(Layer);
		this.setVisible(true);
	}
	public static void main(String[] args){
		new Window();
	}
}

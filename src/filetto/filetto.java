package filetto;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class filetto extends JPanel {
	
	char giocatore = 'x';
	JButton[] celle = new JButton[9];
	
	public filetto() {
		setLayout(new GridLayout(3,3));
		mostra_messaggio();
		creazione_celle();
		
	}
	
	//Creo un metodo per mostrare il messaggio di benvenuto iniziale
	public void mostra_messaggio()
	{
		JOptionPane.showMessageDialog(null, "Benvenuto su Filetto, un gioco gratuito ed open source\nrealizzato in Java da Francesco Bucciantini per dispositivi touch.\n\nREGOLAMENTO:\nIl gioco prevede due giocatori che utilizzano lo stesso dispostivo touch.\nCi sono 9 possibili posizioni che un giocatore può scegliere facendo tap.\nIl primo giocatore che effettua tap sarà x, mentre il secondo sarà o.\nOgni giocatore può occupare una sola posizione durante il suo turno\ne per vincere bisogna mettere in fila per tre volte le x oppure le o\nda cui appunto il nome filetto e la messa in fila può essere\norizzontale, verticale oppure diagonale.\n Buon divertimento!", "Benvenuto", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	//Creo un metodo per creare le 9 possibili celle vuote
	//e popolarle con x oppure o
	public void creazione_celle()
    {
		
        for(int i = 0; i <= 8; i++)
        {
        	//Creo le celle vuote bianche
        	//e mi assicuro che quando verranno popolate
        	//le x e le o siano di grandezza adeguata
        	celle[i] = new JButton();
        	celle[i].setText(" ");
        	celle[i].setBackground(Color.white);
        	celle[i].setFont(new Font("Arial", Font.BOLD, 80));
			
            
            //Creo il mio Action Listener che rimane in ascolto del click dell'utente
            //ed inserisce la x o la o a seconda dell'alternarsi dei click
        	celle[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//Vado a vedere cosa viene cliccato
					JButton pulsanteCliccato = (JButton) e.getSource();
					pulsanteCliccato.setText(String.valueOf(giocatore));
			        
			        //Se il giocatore che va per primo è la x
			        //coloro il quadrato di rosa ed il prossimo click sarà o
			        if(giocatore == 'x') {
			        	giocatore = 'o';
			        	pulsanteCliccato.setBackground(Color.PINK);
			        }
			        //Quando clicca il giocatore o
			        //coloro il quadrato di grigio ed il prossimo click sarà x
			        else {
			        	giocatore ='x';
			        	pulsanteCliccato.setBackground(Color.GRAY);
			        }
			        vincitore();
			        
					
				}
			});
            
            //Aggiungo il pulsante al JPanel
            add(celle[i]);
        }
        
    }
	
	
	//Faccio vedere chi ha vinto
	
	public void vincitore() {
		
		if(vittoria() == true) {
			
			//Per controllare chi ha vinto devo rovesciare x ed o
			//perché quando l'utente clicca e mette l'ultima x e vince
			//il turno passerà ad o, ma lui non potrà più cliccare
			//perché ha vinto x e l'inverso vale se ha vinto o
			if(giocatore == 'x') giocatore = 'o';
	        else giocatore ='x';
			
			//Mostro chi ha vinto
			//e chiedo se i giocatori vogliono la rivincita
			//e quindi iniziare una nuova partita
			JFrame pane = new JFrame();
			String[] options = new String[2];
			options[0] = "Sì";
			options[1] = "No";
			int dialogResult = JOptionPane.showOptionDialog(pane, "Ha vinto " + giocatore + ". Rivincita?", "Partita Terminata", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
			
			//Se vogliono la rivincita, riparto
			//altrimenti chiudo tutto
			if(dialogResult == JOptionPane.YES_OPTION) resetTheButtons();
			else System.exit(0);
		}
		
		//Una partita a filetto può terminare senza vincitori
		//quindi devo mostrare un messaggio opportuno anche in caso di pareggio
		//e chiedere se vogliono fare una nuova partita o meno
		else if(pareggio()) {
			JFrame pane = new JFrame();
			String[] options = new String[2];
			options[0] = "Sì";
			options[1] = "No";
			int dialogResult = JOptionPane.showOptionDialog(pane, "Pareggio. Vuoi iniziare una nuova partita?","Partita Terminata.", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
			
			//Se vogliono fare una nuova partita, riparto
			//altrimenti chiudo tutto
			if(dialogResult == JOptionPane.YES_OPTION) resetTheButtons();
			else System.exit(0);
		}
	}
	
	
	
	//Con questo metodo azzero tutti i quadrati
	//e comincio una nuova partita
	private void resetTheButtons() {
		giocatore = 'x';
		for(int i=0; i<9; i++) {
			  
			celle[i].setText(" ");
			celle[i].setBackground(Color.white);
			  
		  }	
	}

	//Controllo se la partita è finita in pareggio
	public boolean pareggio() {
		boolean full = true;
		for(int i=0; i<9; i++) {
			if(celle[i].getText().charAt(0) == ' ') {
				full = false;
			}
		}
		return full;
	}
	
	//Guardo se ha vinto qualcuno
		public boolean vittoria() {
			if(controllo_righe() == true || controllo_colonne() == true || controllo_diagonali() == true) return true;
			else return false;
		}
		
		//Controllo se qualcuno ha vinto perché ha messo 3 righe insieme
		public boolean controllo_righe() {
			int i = 0;
			for(int j = 0;j<3;j++) {
			if( celle[i].getText().equals(celle[i+1].getText()) && celle[i].getText().equals(celle[i+2].getText()) 
					&& celle[i].getText().charAt(0) != ' ') {
				return true;
			}
			i = i+3;
			
		}
			return false;
	}
		
		
		//Controllo se qualcuno ha vinto perché ha messo 3 colonne insieme
		public boolean controllo_colonne() {
			
			int i = 0;
			for(int j = 0;j<3;j++) {
			if( celle[i].getText().equals(celle[i+3].getText()) && celle[i].getText().equals(celle[i+6].getText())
					&& celle[i].getText().charAt(0) != ' ') 
			{
				return true;
			}
			i++;
			}
			return false;	
		}
		
		//Controllo se qualcuno ha vinto perché ha fatto la diagonale
		public boolean controllo_diagonali() {
			if(celle[0].getText().equals(celle[4].getText()) && celle[0].getText().equals(celle[8].getText())
					&& celle[0].getText().charAt(0) !=' ')
				return true;
			else if(celle[2].getText().equals(celle[4].getText()) && celle[2].getText().equals(celle[6].getText())
					&& celle[2].getText().charAt(0) !=' ') return true;
				
			else return false;
		}
	
	
	
	public static void main(String[] args) {
		JFrame window = new JFrame("Filetto by Francesco Bucciantini");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().add(new filetto()); //Dati
        window.setBounds(500,500,500,500); //Area di gioco
        window.setVisible(true); //Mi assicuro che tutto sia visibile
        window.setLocationRelativeTo(null); //Mi assicuro che sia centrato
        
      
	}

}

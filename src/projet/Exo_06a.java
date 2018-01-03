package projet;

import java.net.* ;
import java.io.* ;
import java.util.ArrayList;

/**
 * Classe principale qui joue le rôle de connecteur. Le thread attend
 * les demandes de connexions, pour chaque demande un thread Client_06a est créé
 *
 *  @author N. Menard
 *	@version 20161102-d
 *
 */
class Exo_06a implements Runnable {

	/**
	 * le nom du serveur 
	 */
	static String nomServeur="ServeurTCP";

	public static ArrayList<Client_06a> clients = new ArrayList<Client_06a>();
	/**
	 * Initialisation du thread connecteur
	 * principal
	 */
	public static void main(String[] args) throws IOException {
		Thread c = new Thread (new Exo_06a());
		c.start();
	}

	/**
	 * méthode run du Thread connecteur qui initialise un socket de type serveur en attente des connexions clientes
	 * à chaque demande de connexion acceptée on instancie un thread de type CdC qui gèrera les communication
	 * avec le client
	 */
	@Override
	public void run() {

		ServerSocket srvs = null;

		try {
			srvs = new ServerSocket(2042);

			while(true){
				System.out.println("En attente de connection");
				new Thread();

				clients.add(new Client_06a(srvs.accept()));

				Thread t = new Thread(clients.get(clients.size()-1));
				//la methode accepte la connexion et renvoie
				// le socket apres acceptation de la connexion
				System.out.println("Connection accepted");
				t.start();

			}
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		try {
			srvs.close();
		} catch (IOException e){}
	}//fin du run
}

package projet;

import java.net.* ;
import java.io.* ;
import java.util.ArrayList;

/**
 * Classe principale qui joue le role de serveur. Le thread attend
 * les demandes de connexions, pour chaque demande un thread Client est cree
 *
 *  @author Valentin Hebert, Jason Liebault
 *	@version 2017-2018
 *
 */

public class Serveur implements Runnable {

	/**
	 * le nom du serveur 
	 */
	static String nomServeur="Valentin & Jason Channel";


	/**
	 * Liste des clients connectes
	 */
	public static ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	/**
	 * Initialisation du thread principal
	 * qui va gerer par la suite les connexions des clients
	 */
	public static void main(String[] args) {
		Thread c = new Thread (new Serveur());
		c.start();
	}

	/**
	 * Methode run du Thread principal
	 * Il va initialiser la socket du serveur  et va attendre des connexions clientes
	 * A chaque demande de connexion acceptee on instancie un thread Client qui gerera les communications
	 * avec le client
	 */
	@Override
	public void run() {

		ServerSocket srvs = null;

		try {
			srvs = new ServerSocket(2042);

			while(true){

				System.out.println("En attente de connection");

				clients.add(new ClientThread(srvs.accept()));
				Thread t = new Thread(clients.get(clients.size()-1));

				System.out.println("Connection accepted");
				t.start();

			}
		} catch (IOException e) {
			System.err.println("Accept failed.");
			System.exit(1);
		}

		try {
			srvs.close();
		} catch (IOException e){ e.printStackTrace();}
	}
}

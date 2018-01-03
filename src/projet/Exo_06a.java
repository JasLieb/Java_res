package projet;
import java.net.* ;
import java.io.* ;
/**
 * 
 * <b>TD M3102 Java : Exercice 07</b><br/>
 * 
 * Écrire un programme Java dans lequel :
 * Un socket serveur écoute sur le port « 2015 » et attend les demandes de connexions en provenance de clients (telnet).
 * 
 * Pour chaque connexion acceptée un thread client est démarré ; le socket client généré par le socket serveur (accept()) est associé à ce thread.
 * Des flux d'entrées et sorties doivent être associés au socket afin d'échanger des messages avec l'utilisateur :
 * - Le thread donne le nom du serveur et demande celui du client
 * - Il attend  la réponse et salue le client par son nom
 * - Le thread répète tout ce qui est dit par le client jusqu'à ce que le mot clé « bye » soit envoyé ; la communication est alors coupée.
  *	@author N. Menard
 *	@version 20151030-c
 */

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
				Thread chargeClient=new Thread(new Client_06a(srvs.accept()));
															//la methode accepte la connexion et renvoie 
														   // le socket apres acceptation de la connexion
				System.out.println("Connection accepted");
				chargeClient.start();
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

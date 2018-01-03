package projet;

import java.io.*;
import java.net.Socket;

/**
 *	Classe des Threads clients
 *	C'est cette classe qui va recevoir et envoyer les messages
 *	a son client qui lui est propre
 *
 *	@author Valentin Hebert, Jason Liebault
 *	@version 2017-2018
 */

public class ClientThread implements Runnable {


    /**
     * Socket client donnee par le serveur
      */
	private Socket s;

    /**
     * Nom du client
     */
	private String nom;

    /**
     * Objet Message
     */
	private Message message;

	// buffers pour l'arrivée et l'envoi des messages
    /**
     * Buffer d'emission
     */
	private BufferedWriter fluxOut = null;

    /**
     * Buffer de reception
     */
	private BufferedReader fluxIn = null;

    /**
     * Contructeur du client
     * @param soc port de la socket cliente
     */

	public ClientThread(Socket soc) {
		super();
		s=soc;
		setBuff();
	}

	/**
	 * Pour obtenir le nom du client connecte avec ce Thread
	 * @return nom du client sur ce Thread
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Initialisation des Buffers d'emission et de reception des messages
	 */

	public void setBuff(){
		try {
			fluxOut=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			fluxIn=new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    /**
     *
     * Methode run du Thread courant
     * C'est cette methode qui va permettre l'interaction du client connecte
     * (envoi et reception des message) avec le reste des clients connectes au serveur
     *
     */

	@Override
	public void run() {

		try {

			fluxOut.write("Bienvenue sur "+Serveur.nomServeur+"\nQuel est votre nom ?");
			fluxOut.newLine();fluxOut.flush();
			
			nom=fluxIn.readLine();

			fluxOut.write("Bienvenue "+nom);
			fluxOut.newLine();fluxOut.flush();

			message = new Message(nom,nom+" est connecté.");
			System.out.println(nom+" est connecté.");
			message.sendToAll();

		} catch (IOException e){}


		while(!message.getContenu().equals("bye")){
			try {
				fluxOut.write("vous : ");fluxOut.flush();
				message.setContenu(fluxIn.readLine());
				System.out.println(message.getNomFrom()+" : "+message.getContenu());
				message.sendToAll();

			} catch(IOException e){}
		}

		System.out.println(nom+" s'est déconnecté.");
		message.setContenu(nom+" s'est déconnecté.");
		message.sendToAll();

		deconnection();

	}

    /**
     *
     * Methode qui se gere de la deconnection du client
     * retrait du client deconnecte de la liste des clients connectes au serveur
     * fermeture des flux d'entre et sortie des messages
     * fermeture de la socket utilisee pour ce client
     *
     */

	public void deconnection(){
		//fermeture des flux et du socket
		try {
			Thread.sleep(1000);
			Serveur.clients.remove(this);
			fluxOut.close();
			fluxIn.close();
			s.close();
		} catch (Exception e){ e.printStackTrace();}
	}

    /**
     * Envoi d'un message (de connexion, de deconnexion, ou autre) a tous les clients
     * @param nomFrom nom du client d'ou le message provient
     * @param msg message a envoyer aux autres clients
     */

	public void send(String nomFrom, String msg){
		try {
			fluxOut.write("\n"+nomFrom+" : "+msg);
			fluxOut.newLine();
			fluxOut.flush();
			fluxOut.write("vous : ");fluxOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
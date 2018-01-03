package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *	Classe des Threads clients
 *	C'est cette classe qui va recevoir et envoyer les messages
 *	à son client qui lui est propre
 *
 *	@author Valentin Hebert, Jason Liebault
 *	@version 2017
 */
class Client_06a implements Runnable {

	// le socket client récupéré du socket serveur	
	private Socket s;
	// le nom du client
	private String nom;
	// les messages saisis au clavier
	private Message message;

	// buffers pour l'arrivée et l'envoi des messages
	private BufferedWriter fluxOut = null;
	private BufferedReader fluxIn = null;

	//constructeur
	Client_06a(Socket soc) {
		super();
		s=soc;
		setBuff();
	}

	/**
	 * Pour obtenir le nim du client connecté avec ce Thread
	 * @return nom du client sur ce Thread
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Initialisation des Buffers d'émission et de réception des messages
	 */

	public void setBuff(){
		try {
			fluxOut=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			fluxIn=new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {

		try {

			fluxOut.write("Bienvenue sur "+Exo_06a.nomServeur+"\nQuel est votre nom ?");
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

	public void deconnection(){
		//fermeture des flux et du socket
		try {
			Thread.sleep(1000);
			Exo_06a.clients.remove(this);
			fluxOut.close();
			fluxIn.close();
			s.close();
		} catch (Exception e){ e.printStackTrace();}
	}

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
package projet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *	<b>TD M3102 Java : Exercice 06</b><br/>
 *
 *	@author N. Menard
 *	@version 20161102-d
 */
class Client_06a implements Runnable {

	// le socket client récupéré du socket serveur	
	private Socket s;
	// le nom du client
	private String nom;
	// les messages saisis au clavier
	private String message;
	
	//constructeur
	Client_06a(Socket soc) {
		super();
		s=soc;
	}
	
	@Override
	public void run() {
		
		//quelques informations affichées pour chaque connexion
		System.out.println("run du cdc du socket"+s);
		System.out.println(s.getLocalAddress());
		
		//flux de sortie pour le socket
		BufferedWriter fluxOut=null;
		
		//flux d'entrée pour le socket
		BufferedReader fluxIn=null;

		try {
			fluxOut=new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			fluxIn=new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			fluxOut.write("Bienvenue sur "+Exo_06a.nomServeur+"\nQuel est votre nom ?");
			fluxOut.newLine();fluxOut.flush();
			
			nom=fluxIn.readLine();
			
			fluxOut.write("Bienvenue "+nom);
			fluxOut.newLine();fluxOut.flush();
			
			System.out.println(nom+" est connecté.");
			
		} catch (IOException e){}

		message="";
		while(!message.equals("bye")){
			try {
				fluxOut.write("vous: ");fluxOut.flush();
				message=fluxIn.readLine();
				fluxOut.write("echo: "+message);
				fluxOut.newLine();fluxOut.flush();
			} catch(IOException e){}
		}
		System.out.println(nom+" est déconnecté.");

		//fermeture des flux et du socket
		try {
			fluxOut.close();
			fluxIn.close();
			s.close();
		} catch (IOException e){}
	}
}
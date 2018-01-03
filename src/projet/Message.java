package projet;

/**
 * Classe Message qui va contenir les messages à envoyer aux clients
 * Chaque Thread Client va avoir son propre objet Message
 * qu'il instanciera durant son execution
 * @author Valentin Hebert, Jason Liebault
 * @version 2017-2018
 */

public class Message {

    /**
     * Contenu du message
     */
    private String contenu;
    /**
     * Nom du client
     */
    private String nomFrom;

    /**
     * Constructeur de la classe Message
     * @param nom Nom du client qui a ecrit ce message
     * @param cont Contenu du message lors de l'instanciation de la classe
     */
    public Message(String nom, String cont){
        contenu = cont; nomFrom = nom;
    }

    /**
     * Envoi du message a tous les clients
     * excepte le client emetteur
     */

    public void sendToAll(){
        int size = Serveur.clients.size();
        for(int i= 0; i< size; i++){
            if(!Serveur.clients.get(i).getNom().equals(nomFrom)){
                Serveur.clients.get(i).send(nomFrom, contenu);
            }
        }
    }

    /**
     * Setter du contenu de message
     * @param contenu Message
     */

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * Getter du message
     * @return message
     */

    public String getContenu(){
        return contenu;
    }

    /**
     * Getter du nom du client emetteur
     * @return nom du client emetteur
     */
    public String getNomFrom() {
        return nomFrom;
    }
}

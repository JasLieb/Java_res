package projet;

public class Message {

    private String contenu;
    private String nomFrom;


    public Message(String nom, String cont){
        contenu = cont; nomFrom = nom;
    }

    public void sendToAll(){
        int size = Exo_06a.clients.size();
        for(int i= 0; i< size; i++){
            if(!Exo_06a.clients.get(i).getNom().equals(nomFrom)){
                Exo_06a.clients.get(i).send(nomFrom, contenu);
            }
        }
    }



    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getContenu(){
        return contenu;
    }

    public String getNomFrom() {
        return nomFrom;
    }
}

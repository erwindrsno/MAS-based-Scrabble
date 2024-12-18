package dictionary;

import jade.core.AID;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DictionaryService extends Agent{
    private String api = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final String REQUEST_METHOD = "GET";

    public DictionaryService(){
        //empty constructor
    }

    public boolean checkValid(String word){
        try{
            String strURL = api + word;
            URL url = new URL(strURL);
    
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod(REQUEST_METHOD);

            int responseCode = conn.getResponseCode();

            if(responseCode == 200){
                // BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                // String inputLine;
                // StringBuilder response = new StringBuilder();
                // while ((inputLine = in.readLine()) != null) {
                //     response.append(inputLine);
                // }
                // in.close();
                return true;
            } else if (responseCode == 404){
                // System.out.println("detected not valid");
                return false;
            } else{
                throw new Exception("Error: could not fetch API data");
            }
        } catch(Exception e){
            System.out.println("Error at : " + e.getMessage());
        }
        return false;
    }

    public String getUrl(String word){
        return api + word;
    }
    
    protected void setup(){
        System.out.println("Dictionary service is up");
        
        addBehaviour(new CyclicBehaviour() {
            public void action(){
                ACLMessage msg = receive();
                if(msg != null){
                    if(msg.getPerformative() == ACLMessage.REQUEST && msg.getOntology().equals("VALIDATE")){
                        System.out.println("Validating : " + msg.getContent());
                        boolean isValid = checkValid(msg.getContent());
                        
                        ACLMessage reply = msg.createReply();
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setOntology("VALIDATION");
                        reply.setContent(isValid+"");
                        send(reply);
                    }
                }
            }
        });
    }
}
package dictionary;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DictionaryService{
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
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getUrl(String word){
        return api + word;
    }
}
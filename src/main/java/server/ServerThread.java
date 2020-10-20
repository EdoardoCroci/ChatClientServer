package server;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread{
    ServerSocket server = null;
    Socket client = null;
    String receivedString = null;
    String modifiedString = null;
    BufferedReader clientInput; //prende input dal client
    DataOutputStream clientOutput; //invia al client
    BufferedReader clientInput2; //prende input dal client
    DataOutputStream clientOutput2; //invia al client
    
    public ServerThread(Socket socket) {
        client = socket;
    }
    
    public void run() {
        try{
            comunica();
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public void comunica() throws Exception{
        clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientOutput = new DataOutputStream(client.getOutputStream());
        clientInput2 = new BufferedReader(new InputStreamReader(client.getInputStream()));
        clientOutput2 = new DataOutputStream(client.getOutputStream());
        for(int i = 0; i < 3; i++) { //minore di 3 per permettere ai 2 client di connettersi e di chiudere la connessione
            receivedString = clientInput.readLine();
            if(receivedString == null || receivedString.equals("FINE")) {
                clientOutput.writeBytes(receivedString + " (server in chiusura...)" + '\n');
                break;
            }
            else{
                clientOutput.writeBytes(receivedString + " (ricevuta e trasmessa)" + '\n');
                System.out.println("6 Echo sul server :" + receivedString);
            }
        }
        clientOutput.close();
        clientInput.close();
        System.out.println("9 Chiusura socket" + client);
        client.close();
    }
}
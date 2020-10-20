package client;

import java.io.*;
import java.net.*;

public class Client {
   int portaServer = 7777;
   Socket mySocket;
   BufferedReader tastiera;
   String userString;
   String serverString;
   DataOutputStream outputServer; //output verso server
   BufferedReader inputServer; //input dal server
   
   public Socket connetti() {
       System.out.println("2, Client in esecuzione...");
       try
       {
           tastiera = new BufferedReader(new InputStreamReader(System.in));         
           
           byte[] ipAddr = new byte[]{(byte)172, (byte)16, (byte)12, (byte)47}; //ip DeLuca
           InetAddress address = InetAddress.getByAddress(ipAddr);
           mySocket = new Socket(address, portaServer);
           
           outputServer = new DataOutputStream(mySocket.getOutputStream());
           inputServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
       }
       catch(Exception ex) 
       {
           System.out.println(ex.getMessage());
           System.out.println("Errore durante la connessione");
           System.exit(1);
       }
       
       return mySocket;
   }
   
   public void comunica() {
       for(;;) {
           try
            {
                System.out.println("4, inserisci la stringa da trasmettere al server");
                userString = tastiera.readLine();

                System.out.println("5, invio la stringa al server e attendo...");
                outputServer.writeBytes(userString + '\n');

                serverString = inputServer.readLine();
                System.out.println("7, risposta dal server" + '\n' + serverString);

                if(userString.equals("FINE")) {
                    System.out.println("8 Client: termina elaborazione e chiude la connessione");
                    mySocket.close();
                    break;
                }
            }
            catch(Exception ex) 
            {
                System.out.println(ex.getMessage());
                System.out.println("Errore durante la comunicazione col server!");
                System.exit(1);
            }
       }  
   }
   
   public static void main(String args[]) {
       Client cliente = new Client();
       cliente.connetti();
       cliente.comunica();
   }
}
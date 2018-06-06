package client;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import javax.swing.filechooser.FileSystemView;
import common.*;


public class NewClientConnection {	
    private NetworkManager networkManager = new NetworkManager();
	private Socket clientSocket;
	private InetAddress serverAddress;
	private String serverIP;
	private String pseudo;
	private String password;
    private DataInput dataInput;
	private PrintWriter pout;
	private DataOutput dataOutput;
	private String localIP;
	private int port;
	private String folderPath;
	
	
	public NewClientConnection(String pseudo, String password, String serverIP, String networkInterface, int port) {
		this.pseudo = pseudo;
		this.password = password;
        this.serverIP = serverIP;
		this.localIP = networkManager.getOwnIp(networkInterface);
		this.port = port;
		this.folderPath = getHomePath();
	}
	
	
	private String getHomePath() {
		File home = FileSystemView.getFileSystemView().getHomeDirectory();
		String path = home.getAbsolutePath() + "/JavaSocket";

		return path;
	}
	
	
	public void connectToServer() {
		try {
			// Ask the server to create a new socket
			serverAddress = InetAddress.getByName(serverIP);
			clientSocket = new Socket(serverAddress, port);			
			System.out.println("I got the connection to " + serverAddress);
			
			// Create a repository if the client is new
			if (createRepository()) {
				System.out.println("Please add the files you want to share in the 'JavaSocket' repository on your desktop...");
				System.out.println("When you're ready, press the ENTER key.");				
				System.in.read();
			}
			
			// Retrieve the list of files from the client
			FileHandler fileHandler = new FileHandler();
			fileHandler.retrieveListFiles(folderPath);
			String[] filesList = fileHandler.getListFiles();
			
			// Give login information to the server
			dataOutput = new DataOutput(clientSocket);
			dataOutput.giveInformationToServer(pseudo, password, localIP, Integer.toString(port), filesList);

			
			
			
			// TODO: check this part of the code
			// CODE RAPH A FAIRE PROPRE
            dataInput = new DataInput(clientSocket);
            Clients clients = dataInput.receiveClients();

            ArrayList<String> choicesList = new ArrayList<String>();
            ArrayList<String> ipList = new ArrayList<String>();
            ArrayList<String> portList = new ArrayList<String>();

            // Get all list and save it into the choicesList | ipList and portList
            for(Client c: clients.getClients()) {
                for(String s: c.getFiles()) {
                    choicesList.add(s);
                    ipList.add(c.getClientIP());
                    portList.add(c.getClientPort());
                }
            }

            // Print the choicesList
            for(int i = 0; i < choicesList.size(); i++) {
                System.out.println(i + ": " + choicesList.get(i));
            }

            System.out.println("Wich list did you want to download?");
            /* TODO: Faire le scanner (aller avec l'index et envoyer tout les objet en même temps
                Ex: si il choisis 3: methodToGetFile(choiceList.get(3), ipList.get(3) portList.get(3)) */


            
            
            
            // Disconnecting the server
			System.out.println("Now dying...");			
			clientSocket.close();
		} catch(UnknownHostException e) {
			e.printStackTrace();			 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean createRepository() {			
		File folder = new File(folderPath);

		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("The folder has been successfully created!");
			return true;
		}
		
		return false;
	}
}

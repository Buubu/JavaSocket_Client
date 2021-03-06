package common;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class DataInput {
	private Socket socket;

	
	public DataInput(Socket socket) {
		this.socket = socket;
	}

	
	// Retrieve the client
    public Client receiveClient() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            
            try {
                Client client = (Client) objectInput.readObject();
                return client;
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has a problem.");
            e.printStackTrace();
        }
        
        return null;
    }

    
    // Retrieve the "Clients" object
    public Clients receiveClients() {
        try {
            ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
            
            try {
                Clients clients = (Clients) objectInput.readObject();
                return clients;
            } catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("The socket for reading the object has problem.");
            e.printStackTrace();
        }
        
        return null;
    }
    
    
    // Retrieve the a list of string
    public ArrayList<String> receiveList() {
    	try {
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		
    		try {
    			ArrayList<String> list = (ArrayList<String>) objectInput.readObject();        		
        		return list;
    		} catch (ClassNotFoundException e) {
                System.out.println("The title list has not come from the server.");
                e.printStackTrace();
            }	
    	} catch (IOException e) {
                System.out.println("The socket for reading the object has problem.");
                e.printStackTrace();
    	}
    	
    	return null;
    }


    // Retrieve all the sent data => 0 : names of the files, 1 : files converted into bytes
    public void receiveData(String path) {
    	try {
    		ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
    		
    		try {
    			DownloadFiles dl = (DownloadFiles) objectInput.readObject();
    			ArrayList<String> names = dl.getNames();
    			ArrayList<byte[]> bytes = dl.getBytes();

    			
    			for (int i = 0; i < bytes.size(); i++) {
    				String name = names.get(i);
    				byte[] item = bytes.get(i);
    				
    				name = retrieveFileName(name);
    				
    				String completedPath = path + name;
    				readBytes(completedPath, item);
    			}
    		} catch (ClassNotFoundException e) {
				e.printStackTrace();
    		}
    	} catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Retrieve the file name from the entire path
	private String retrieveFileName(String name) {
		int startIndex = name.lastIndexOf('\\') + 1;
		int endIndex = name.length();
		String refactor = name.substring(startIndex, endIndex);
				
		return refactor;
	}


	// Method to write bytes in the downloads file
	private void readBytes(String path, byte[] item) throws IOException {		
		FileOutputStream fos = new FileOutputStream(path); 
	    fos.write(item);
	}
}

package common;

import java.io.Serializable;
import java.util.ArrayList;


public class Client implements Serializable {
	private static final long serialVersionUID = 5950169519310163575L;
	
    private String pseudo;
    private String password;
    private String ip;
    private String port;
    private ArrayList<String> files = new ArrayList<String>();

    
    public Client(String pseudo, String password, String clientIP, String clientPort){
        this.pseudo = pseudo;
        this.password = password;
        this.ip = clientIP;
        this.port = clientPort;
    }
    
    
    public void setFiles(ArrayList<String> files) {
        this.files = files;
    }
    

    public void addFile(String fileName) {
        files.add(fileName);
    }

    
    public ArrayList<String> getFiles() {
        return files;
    }
    
    
    public String getPseudo() {
        return pseudo;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    
    public String getClientIP() {
        return ip;
    }

    
    public void setClientIP(String clientIP) {
        this.ip = clientIP;
    }
    
    
    public String getClientPort() {
        return port;
    }

    
    public void setClientPort(String clientPort) {
        this.port = clientPort;
    }
    
    
    
    // TODO: check if methods are used
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return pseudo + ";" + password + ";" + ip + ";" + port;
    }
}
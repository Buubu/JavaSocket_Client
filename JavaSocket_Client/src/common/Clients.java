package common;

import java.io.Serializable;
import java.util.ArrayList;


public class Clients implements Serializable {
    private static final long serialVersionUID = 5832063776451490808L;
    private ArrayList<Client> clients = new ArrayList<Client>();
    private Client clientFocused = new Client("undefined", "undefined", "undefined", "undefined");
    
    public Clients(){}

    
    public Client getClientName() {
       return clientFocused;
    }
    
    
    // Add a new client to the list of register clients
    public boolean addNewClient(Client client) {
        if (!this.isClient(client.getPseudo())) {
            this.clients.add(client);
            System.out.println("New client created!");
            return true;
        }
        
        return false;
    }

    
    // Update a client in the list
    public boolean updateClient(Client client) {
        for (int i = 0; i < clients.size(); i++)
            if (clients.get(i).getPseudo().equalsIgnoreCase(client.getPseudo())) {
                if (clients.get(i).getPassword().equals(client.getPassword())) {
                    clients.get(i).setClientIP(client.getClientIP());
                    clients.get(i).setClientPort(client.getClientPort());
                    clients.get(i).setFiles(client.getFiles());
                    return true;
                }
            }
        
        return false;
    }

    
    public boolean isClient(String pseudo) {
        if (!clients.isEmpty()) {
            for (Client c: clients) {
                if (c.getPseudo().equalsIgnoreCase(pseudo)) {
                    System.out.println("This client already exists.");
                    return true;
                }
            }
        }

        return false;
    }

    
    public ArrayList<Client> getClients() {
        return this.clients;
    }
    
    
    // Check the password of the client (=> check in the list)
    public boolean isPwdCorrect(String pseudo, String password) {
        try {
            for (Client c : clients) {
                if (c.getPseudo().equalsIgnoreCase(pseudo)) {
                    this.clientFocused = c;
                    if (c.getPassword().equals(password)) {
                        System.out.println("I confirm the password.");
                        return true;
                    }
                }
            }
            
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    
    public int size() {
        return clients.size();
    }

    
    // Debugging purpose
    public void printArray() {
        System.out.println("There's what Clients contains: ");
        for (Client c: clients) {
            System.out.println(c.getPseudo() + " | " + c.getPassword() + " | " +
                    c.getClientIP() + " | " + c.getFiles());
        }
    }
}

package com.kursovaya;

import java.io.*;
import java.net.*;
import java.util.*;

public class ThreadServer 
{
    //static ExecutorService executeIt = Executors.newFixedThreadPool(5);
    private static List<Server> clientList=new ArrayList<>();
    private static ServerSocket serverSocket;

    private static void start(int port) throws IOException{
        if(serverSocket!=null){
            return;
        }
        serverSocket=new ServerSocket(port);
    }

    public static void main( String[] args ) throws IOException
    {
        start(2202);
        acceptingLoop();
        // Socket client=null;
        // try(ServerSocket serverSocket = new ServerSocket(1603);) {
            
        //     boolean stop = false;
        //    // DataInputStream br = new DataInputStream(new DataInputStream(System.in));
        //     while(!serverSocket.isClosed()){
        //         client = serverSocket.accept();
        //         executeIt.execute(new Server(client));
        //     }
        // executeIt.shutdown();
        // } catch (IOException e) {
        //     throw new RuntimeException(e);}
        //     finally{
        //         try{
        //         if(client!=null){
        //             client.close();
        //         }
        //     }catch(IOException e){
        //         throw new RuntimeException();
        //     }
        //     }
        // catch (ClassNotFoundException e){
        //     throw new RuntimeException(e);
        // }
    }

    private static void acceptingLoop() throws IOException
    {
        while(true){
            Socket client=serverSocket.accept();
            Server server=new Server(client);
            clientList.add(server);
            server.start();
        }
    }
}

package com.kursovaya;

import java.io.IOException;
import java.net.Socket;

public class Server extends Thread {
    private Socket client;

    public Server(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        Carsalon carsalon=new Carsalon(client);

        try {
            carsalon.GetRequest();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);}
        // }catch (ClassNotFoundException e){
        //     throw new RuntimeException(e);
        // }

                // int i=0;
        // ObjectOutputStream ostream;
        // try{
        // ostream=new ObjectOutputStream(client.getOutputStream());
        // }catch(IOException e){
        //     throw new RuntimeException(e);
        // }
        // while(true){
            
        //     try{
        //         ostream.writeInt(i++);
        //         System.out.println(i);
        //     sleep(1000);
        //     }catch (InterruptedException e){
        //         throw new RuntimeException(e);
        //     }catch(IOException e){
        //         throw new RuntimeException(e);
        //     }
        // }
    }

}

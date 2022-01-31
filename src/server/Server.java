package server;

/**
 *
 * @author Michacreu
 */

import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import server.Util.Message;
import server.Util.Status;

public class Server {

    private ServerSocket serverSocket;

    private void createServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    private Socket waitConnection() throws IOException {
        Socket server = serverSocket.accept();
        return server;
    }

    private void closeSocket(Socket socket) throws IOException {
        socket.close();
    }

    private void handdleConnection(Socket socket) throws IOException, ClassNotFoundException {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Message msg = (Message) input.readObject();
            String operation = msg.getOperation();
            
            Message reply = null;
            if(operation.equals("HELLO")) {
                String name = (String) msg.getParam("name");
                String surname = (String) msg.getParam("surname");
                
                reply = new Message("HELLO-REPLY");
                
                if(name == null || surname == null) {
                    reply.setStatus(Status.PARAMERROR);
                } else {
                    reply.setStatus( Status.OK );
                    reply.setParam( "message", "Hello Client, " + name + " " + surname);
                }
            }
            
            output.writeObject(reply);
            output.flush();

            input.close();
            output.close();

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("Problema na conexão com o cliente" + socket.getInetAddress());
            System.out.println("Erro: " + e.getMessage());
        } finally {
            closeSocket(socket);
        }

    }

    public static void main(String[] args) throws Exception {
        try {
            Server server = new Server();
            server.createServer(8080);

            while (true) {
                System.out.println("Aguardando conexão... O.O");
                Socket socket = server.waitConnection();
                System.out.println("Cliente conectado ☆⌒(*＾-゜)v");
                server.handdleConnection(socket);
            }
        } catch (IOException e) {
            //TODO: handle exception
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in cast: " + e.getMessage());
        }
    }
}

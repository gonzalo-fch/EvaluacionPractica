import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int puerto = 12;
        try {
        ServerSocket serverSocket = new ServerSocket(puerto);
        Socket cliente = serverSocket.accept();
        System.out.println("Se ha conectado un cliente");
        DataInputStream dataInputStream = new DataInputStream(cliente.getInputStream());
        DataOutputStream dataOutputStream = new DataOutputStream(cliente.getOutputStream());
        String mensajeCliente = "";
        String contenido="";
        while (!mensajeCliente.equals("EXIT")){
            mensajeCliente = dataInputStream.readUTF();
            System.out.println(mensajeCliente);
            if  (mensajeCliente.equals("1")){
                contenido=obtenerContenido("C:/Users/Gonzalo/Desktop/ArchivosServer/minusculas.txt");
               dataOutputStream.writeUTF(contenido);

            } else if (mensajeCliente.equals("2")) {
                contenido = obtenerContenido("C:/Users/Gonzalo/Desktop/ArchivosServer/mayusculas.txt");
                dataOutputStream.writeUTF(contenido);

            }else if (mensajeCliente.equals("3")){
                    contenido=obtenerContenido("C:/Users/Gonzalo/Desktop/ArchivosServer/original.txt");
                    dataOutputStream.writeUTF(contenido);

            }else if (mensajeCliente.equals("EXIT")) {
                dataOutputStream.writeUTF("");
            }
        }
        dataInputStream.close();
        dataOutputStream.close();
        cliente.close();
        }  catch(IOException e){
        e.printStackTrace();
    }
}

    public static String obtenerContenido(String ruta) {

        Path archivo = Paths.get(ruta);
        String texto = "";
        try {
            texto = new String(Files.readAllBytes(archivo));


        } catch (IOException e) {
            System.out.println("El archivo no pudo ser leido");

        }
        return texto;

    }

}




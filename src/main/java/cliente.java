import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class cliente {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        String rutaCliente;
        int numero=0;




        String ipDestino = "localhost";
        int puertoDestino = 12;




        try {


            Socket socketCliente = new Socket(ipDestino,puertoDestino);
            System.out.println("Conexion establecida");
            System.out.println("Para terminar la conexion escriba EXIT");
            System.out.println("");
            System.out.println("ENTER para continuar");
            DataOutputStream dataOutputStream = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream dataInputStream = new DataInputStream(socketCliente.getInputStream());
            String mensaje= "";
            while (!mensaje.equals("EXIT")){
                if (numero ==0){
                    rutaCliente= "C:/Users/Gonzalo/Desktop/ArchivosCliente/archivo.txt";

                }else{
                    rutaCliente= "C:/Users/Gonzalo/Desktop/ArchivosCliente/archivo("+numero+").txt";
                }
                teclado.nextLine();
                System.out.println("Seleccione el formato en el que desea ver la informacion");
                System.out.println("1) Minusculas");
                System.out.println("2) Mayusculas");
                System.out.println("3) Original");
                mensaje = teclado.nextLine();
                System.out.println("");
                System.out.println("Enviando Solicitud");
                dataOutputStream.writeUTF(mensaje);
                socketCliente.setKeepAlive(true);
                System.out.println("Recibiendo Respuesta");
                System.out.println("");
                System.out.println("-----------------------------------------");
                System.out.println("");
                String contenido= dataInputStream.readUTF();
                System.out.println("ARCHIVO DESCARGADO");
                crearArchivo(rutaCliente,contenido);
                contenido="";
                System.out.println();
                System.out.println("");
                System.out.println("-----------------------------------------");
                System.out.println("");
                System.out.println("ENTER para continuar");
                numero+=1;
            }

        } catch (ConnectException e){
            System.err.println("Error: Conexion rechazada");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void crearArchivo(String ruta, String contenido) {

        Path archivo = Paths.get(ruta);

        try {
            Files.write(archivo, obtenerTexto(contenido).getBytes());

        } catch (IOException e) {
            System.out.println("no se pudo crear el archivo");
        }
    }
    public static String obtenerTexto(String contenido){
        String texto = contenido;
        return texto;

    }
}

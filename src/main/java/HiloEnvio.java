import java.net.*;
import java.io.*;

public class HiloEnvio extends Thread {

    private File archivo;
    private Socket socketCliente = null;
    private FileInputStream fileChannel = null;
    private BufferedInputStream lectorArchivo = null;
    private OutputStream sendChannel = null;

    public HiloEnvio(Socket socketCliente, File archivo){ this.socketCliente = socketCliente; this.archivo = archivo; }

    public void run() {
        // envio de file
        try{
            byte [] mybytearray  = new byte [(int)archivo.length()];
            fileChannel = new FileInputStream(archivo);
            lectorArchivo = new BufferedInputStream(fileChannel);
            lectorArchivo.read(mybytearray,0,mybytearray.length);
            sendChannel = socketCliente.getOutputStream();
            System.out.println("Sending " + archivo + "(" + mybytearray.length + " bytes)");
            sendChannel.write(mybytearray,0,mybytearray.length);
            sendChannel.flush();
            System.out.println("Done.");
        }catch (IOException | NullPointerException e) { System.out.println("Interrumpido. ");
            interrupt();}
    }
}

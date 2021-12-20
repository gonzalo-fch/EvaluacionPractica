import java.io.*;
import java.net.*;

public class FicheroCliente {
    public static void main(String[] args) throws IOException {

        int bytesRead;
        OutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket socketCliente = null;

        PrintWriter salida = null;

        String hostName = InetAddress.getLocalHost().getHostName();
        /* Creamos un socket en el lado cliente, enlazado con un servidor que está en la misma máquina
        que el cliente y que escucha en el puerto 4444 */

        try{ socketCliente = new Socket(hostName, 4444);
            System.out.println("servidor conectado:" + hostName);

            //Obtenemos el canal de salida
            salida = new PrintWriter( new BufferedWriter( new OutputStreamWriter(socketCliente.getOutputStream())),true);
        }catch(IOException e){
            System.err.println("No puede establecer conexion");
            System.exit(-1); }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String linea = "";

        /*El programa cliente no analiza los mensajes enviados por el usuario, simplemente los
         * reenvia al servidor hasta que este se despide con Adios*/
        while(!linea.equalsIgnoreCase("bye")){

            do{
                System.out.println("Intrude comando válido:");
                //Leo la entrada del usuario
                linea = stdIn.readLine();
            }while (!linea.matches("[a-z][a-z][a-z] \".*\"") && !linea.equalsIgnoreCase("bye"));
            //La envia al servidor
            salida.println(linea);

            try {
                //reciibr archivo
                //Creamos array de bytes
                byte [] mybytearray  = new byte [66666];
                //Creamos objeto InputStream que abre la cadena de entrada para lectura del fichero que mande servidor
                InputStream cadenaReceptor = socketCliente.getInputStream();
                fos = new FileOutputStream("C:/Users/Gonzalo/Desktop/Gonzalo/llegada.txt");
                bos = new BufferedOutputStream(fos);  //lee donde va a escribir

                bytesRead = cadenaReceptor.read(mybytearray,0,mybytearray.length);


                if(bytesRead == 1){   //1 valor que toma como mínimo un archivo Outputstream, significa archivo no encontrado o no buscado
                    System.out.println("Archivo no encontrado o comando erroneo");
                } else if(bytesRead == -1){ System.out.println("Sesion finalizada"); } //-1 valor que toma al no recibir ningun archivo erroneo ni correcto, solo con BYE

                else { bos.write(mybytearray, 0 , bytesRead);  //buffer escribe en el archivo asignado
                    bos.flush();
                    System.out.println("File " + "C:/Users/Gonzalo/Desktop/Gonzalo/llegada.txt"
                            + " downloaded (" + bytesRead + " bytes read)");  }

            }catch(IOException e){ System.out.println("Error en la transmisión.");
                if (fos != null) fos.close();
                if (bos != null) bos.close();
                if (socketCliente != null) socketCliente.close(); }
        }

        if (fos != null) fos.close();
        if (bos != null) bos.close();
        if (socketCliente != null) socketCliente.close();

    }
}

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** DESCUBRIMIENTO DE SERVICIOS **/
public class Cliente {
    static int SERVICE_NAME_ATTRID = 0x0100;

    public static void main(String[] args) {
        final Object inquiryCompletedEvent = new Object();
        String deviceID = "XXXXXXXXXXX"; 
        String serviceID = "chat";
        List<String> services = new ArrayList<>();
        List<String> URLServices = new ArrayList<>();
        String URL;

        try {
            LocalDevice ld = LocalDevice.getLocalDevice();
            DiscoveryAgent da = ld.getDiscoveryAgent();
            // da.searchServices() // param: int[] attrSet, UUID[] uuidSet, RemoteDevice btDev, DiscoveryListener listener
            MyDiscoveryListener listener = new MyDiscoveryListener(inquiryCompletedEvent, deviceID, serviceID);

            long initTime = System.nanoTime();
            // Empieza la consulta con el access code especificado (GIAC -> la mayoria de dispositivos bluetooth,
            // usa una direccion unica y permanente) (LIAC -> direcccion temporal para dispositivos que operan en modo bajo consumo e.g. BLE)
            /** Los dispositivos con la direccion GIAC que respondan, son devueltos al metodo deviceDiscovered del listener  **/
            boolean started = da.startInquiry(DiscoveryAgent.GIAC, listener);

            synchronized (inquiryCompletedEvent) {
                if (started) {
                    System.out.println("********* Buscando dispositivos *********");
                    inquiryCompletedEvent.wait();
                    long endTime = System.nanoTime();
                    System.out.println("********* End inquiry *********");
                    System.out.println("* Tiempo de busqueda: " + (endTime - initTime) / 1e9 + " segundos");
                    List<RemoteDevice> devices = listener.getDevices();

                    System.out.println("* Se han encontrado "+devices.size()+" dispositivos");
                    if(!devices.isEmpty()){
                        UUID uuids[] = new UUID[1];
                        uuids[0] = new UUID(0x1101); // UUID de PublicBrowseGroup
                        //uuids[0] = new UUID(0x1101); // SerialPort service
                        int attrIDSet[] = new int[1];
                        attrIDSet[0] = SERVICE_NAME_ATTRID; // 0x0100 (L2CAP)
                        System.out.println("********* Buscando servicios *********");
                        for(RemoteDevice rd : devices){
                            System.out.println("* Servicios activos de "+
                                    rd.getFriendlyName(false)+" "+ rd.getBluetoothAddress()+ ":");
                            da.searchServices(attrIDSet, uuids, rd, listener);
                            synchronized (inquiryCompletedEvent){ inquiryCompletedEvent.wait(); }
                        } // end foreach rd
                        URLServices = listener.getURLServices();
                        if(!URLServices.isEmpty()){
                            // 1. Construir una URL del servicio al que se quiere conectar
                            URL = URLServices.get(0);
                            System.out.println("La URL es: "+URL);
                            // 2. Utilizar la URL para establecer una conexión a través del método (estático) open de la clase Connector.
                            StreamConnection con = (StreamConnection) Connector.open(URL);
                            RemoteDevice connDev = RemoteDevice.getRemoteDevice(con);
                            System.out.println("Bluetooth address del Servidor: "+connDev.getBluetoothAddress());
                            System.out.println("Nombre del Servidor: "+connDev.getFriendlyName(false));

                            // OutputStream e InputStream son para enviar y recibir datos
                            // a traves de la conexion respectivamente (Igual que el Servidor, copypaste)
                            InputStream is = con.openInputStream();
                            BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            OutputStream os = con.openOutputStream();
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

                            Scanner sc = new Scanner(System.in);
                            String message = "";
                            String response = "";
                            while(!response.equals("FIN.")){
                                System.out.println("(Cliente) "+ld.getFriendlyName()+": ");
                                response = sc.nextLine(); // Escribo la respuesta al servidor
                                bw.write(response); // La envio
                                bw.newLine();
                                bw.flush();
                                System.out.println("Esperando respuesta del servidor");
                                message = br.readLine(); // Leo el mensaje del servidor
                                // Mismo formato que el servidor
                                System.out.println("(Servidor) "+connDev.getBluetoothAddress()+" - "+
                                        connDev.getFriendlyName(false)+": "+message);
                            }
                            System.out.println("* Desconectando ...");
                            br.close();
                            bw.close();
                            sc.close();
                            message = ""; con.close();
                        }
                    }
                }
            }
        }
        catch (BluetoothStateException e) { e.printStackTrace(); }
        catch (InterruptedException e) { throw new RuntimeException(e); }
        catch (IOException e) { throw new RuntimeException(e); } // rd.getFriendlyName
    }
}

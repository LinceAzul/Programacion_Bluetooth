import javax.bluetooth.*;
import java.io.IOException;
import java.util.List;

/** DESCUBRIMIENTO DE SERVICIOS **/
public class BluetoothServiceDiscovery {
    static int SERVICE_NAME_ATTRID = 0x0100;

    public static void main(String[] args) {
        final Object inquiryCompletedEvent = new Object();
        String dispositivo = "F0396552445A"; // Name: I25 Addr: 891BA0BCFAE1 | telefono: F0396552445A
        try {
            LocalDevice ld = LocalDevice.getLocalDevice();
            DiscoveryAgent da = ld.getDiscoveryAgent();
            // da.searchServices() // param: int[] attrSet, UUID[] uuidSet, RemoteDevice btDev, DiscoveryListener listener
            MyDiscoveryListener listener = new MyDiscoveryListener(inquiryCompletedEvent, dispositivo);

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
                        uuids[0] = new UUID(0x1002); // UUID de PublicBrowseGroup (te lo pide el ejercicio)
                        int attrIDSet[] = new int[1];
                        attrIDSet[0] = SERVICE_NAME_ATTRID; // 0x0100 (L2CAP)
                        System.out.println("********* Buscando servicios *********");
                        for(RemoteDevice rd : devices){
                            System.out.println("* Servicios activos: "+
                                    rd.getFriendlyName(false)+" "+ rd.getBluetoothAddress());
                            da.searchServices(attrIDSet, uuids, rd, listener);
                            synchronized (inquiryCompletedEvent){ inquiryCompletedEvent.wait(); }
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

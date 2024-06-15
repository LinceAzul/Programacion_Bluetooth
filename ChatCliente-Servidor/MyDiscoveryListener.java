import javax.bluetooth.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyDiscoveryListener implements DiscoveryListener {
    static int SERVICE_NAME_ATTRID = 0x0100;
    private Object inquiryCompletedEvent;
    private List<RemoteDevice> devices;
    List<String> services;
    List<String> URLServices;
    String deviceID;
    String serviceID;

    public MyDiscoveryListener(Object inquiryCompletedEvent, String deviceID, String servicio){
        this.inquiryCompletedEvent = inquiryCompletedEvent;
        devices = new ArrayList<>();
        services = new ArrayList<>();
        this.URLServices = new ArrayList<>();
        this.deviceID = deviceID;
        this.serviceID = servicio;
    }
    public List<String> getURLServices() { return URLServices; }
    public List<RemoteDevice> getDevices(){
        return devices;
    }
    public List<String> getServices() { return services; }

    // Si un dispositivo responde al discovery, se le redirige a este método
    /** Se devuelve el nombre y la direccion bluetooth del dispositivo que responda a este metodo ***/
    @Override
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        try{
            if(deviceID.equals(remoteDevice.getFriendlyName(false)) ||
                    deviceID.equals(remoteDevice.getBluetoothAddress())){
                // Formato -> NombreDispositivo BluetoothAddress
                System.out.println("\t * DISPOSITIVO ENCONTRADO: "+remoteDevice.getFriendlyName(true)+
                        " "+remoteDevice.getBluetoothAddress());
                devices.add(remoteDevice);
            }
        }catch(IOException e) { e.printStackTrace(); }
    }
    /** Una vez acabada la consulta, se redirige a este metodo **/
    @Override
    public void inquiryCompleted(int discType) {
        if(devices.isEmpty()) System.out.println("\t[!] NO SE ENCONTRÓ NADA");
        System.out.println("\t[-] Busqueda de dispositivos terminada");
        try{
            synchronized (inquiryCompletedEvent){ inquiryCompletedEvent.notifyAll(); }
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void servicesDiscovered(int transID, ServiceRecord[] serviceRecords) {
        for(int i = 0; i < serviceRecords.length; ++i){
            DataElement d = serviceRecords[i].getAttributeValue(SERVICE_NAME_ATTRID);// 0x0100
            //System.out.println("************************************** DEBUG");
            if(d != null){
                //
                if(serviceID.equals((String) d.getValue())){
                    System.out.println("Servicio: "+ (String)d.getValue());
                    services.add((String) d.getValue());
                    System.out.println("URL: "+serviceRecords[i].getConnectionURL(
                                    ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
                    URLServices.add(serviceRecords[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
                }
            }
            else{
                System.out.println("Unnamed service");
                services.add("Unnamed service "+i);
                System.out.println("URL: "+serviceRecords[i].getConnectionURL(
                                ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
                URLServices.add(serviceRecords[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
            }
            //System.out.println("URL: "+serviceRecords[i].getConnectionURL(
            //        ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
            //URLServices.add(serviceRecords[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
        }
    }

    @Override
    public void serviceSearchCompleted(int i, int i1) {
        if(devices.isEmpty()) System.out.println("\t[!] NO SE ENCONTRÓ NADA");
        System.out.println("\t[-] Busqueda de servicios terminada");
        try{
            synchronized (inquiryCompletedEvent){ inquiryCompletedEvent.notifyAll(); }
        } catch (Exception e) { e.printStackTrace(); }
    }

}


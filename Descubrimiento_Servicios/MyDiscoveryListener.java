package Ejercicio5;

import javax.bluetooth.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MyDiscoveryListener implements DiscoveryListener {
    static int SERVICE_NAME_ATTRID = 0x0100;
    private Object inquiryCompletedEvent;
    private List<RemoteDevice> devices;
    List<String> URLServices;
    String deviceID;

    public MyDiscoveryListener(Object inquiryCompletedEvent, String deviceID){
        this.inquiryCompletedEvent = inquiryCompletedEvent;
        devices = new ArrayList<>();
        this.URLServices = new ArrayList<>();
        this.deviceID = deviceID;
    }
    public List<RemoteDevice> getDevices(){
        return devices;
    }

    // Si un dispositivo responde al discovery, se le redirige a este método
    /** Se devuelve el nombre y la direccion bluetooth del dispositivo que responda a este metodo ***/
    @Override
    public void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        try{
            //System.out.println("EL DEVICEID --------------------------------------->"+deviceID);
            //System.out.println(deviceID+" =? "+remoteDevice.getBluetoothAddress());
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
            if(d != null) System.out.println("Servicio: "+ (String)d.getValue());
            else System.out.println("Unnamed service");
            System.out.println("URL: "+serviceRecords[i].getConnectionURL(
                    ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
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

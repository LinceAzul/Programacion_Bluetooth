# Programacion_Bluetooth
En este repositorio se pretende enseñar algunos ejemplos de programación en Java de aplicaciones sobre Bluetooth utilizando la API BlueCove.

Cada carpeta tendrá el readme con su explicación correspondiente.

Pretendo mostrar dos ejemplos:
1. Descubrimiento de dispositivos y servicios
2. Comunicación cliente/servidor mediante Bluetooth

Los outputs esperados para estos ejemplos son los siguientes.

### Descubrimiento de servicios
Para el descubrimiento, en mi caso particular tengo lo siguiente:
![Logo](Descubrimiento_Servicios/Descubrimiento.png)

En el que el programa encuentra dos dispositivos, mi teléfono y mis auriculares inalámbricos.

Para los auriculares no se encuentran servicios concretos en la foto, mientras que en el teléfono se detectan 
servicios de audio y control remoto, junto a sus URLs de conexión.


# Comunicación cliente/servidor mediante Bluetooth
### Punto de vista del cliente:
![Logo](ChatCliente-Servidor/Cliente.png)

### Punto de vista del Servidor:
![Logo](ChatCliente-Servidor/Servidor.png)

En donde se ve que se realiza una comunicación efectiva entre el cliente y el servidor.

El cliente busca mediante el DiscoveryAgent el servicio de "chat" y configura flujos de entrada y salida
para permitir dicha comunicación.

El servidor espera conexiones entrantes. Luego acepta y abre las conexiones y gestiona la comunicación mediante
más flujos de entrada y salida.

En ambos lados se intercambian mensajes hasta que se envía "FIN" para acabar la comunicación

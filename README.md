# Programacion_Bluetooth
En este repositorio se pretende enseñar algunos ejemplos de programación en Java de aplicaciones sobre Bluetooth utilizando la API BlueCove (proporcionada en el proyecto en un archivo .jar).

Cada carpeta tendrá el readme con su explicación correspondiente.

Pretendo mostrar dos ejemplos:
1. Descubrimiento de dispositivos y servicios
2. Comunicación cliente/servidor mediante Bluetooth

# Introducción a la pila Bluetooth

Antes de presentar los resultados de estos dos ejemplos, voy a introducir algunos aspectos importantes de la pila de protocolos Bluetooth para este proyecto.

La pila de protocolos Bluetooth es esencial para la comunicación y el descubrimiento de dispositivos y servicios.


La pila estructura de la pila de protocolos es la siguiente:
![Logo](PilaBluetooth.png)

### ¿Qué es la Pila de Protocolos Bluetooth?

La pila de protocolos Bluetooth es una colección de protocolos diseñados para facilitar la comunicación entre dispositivos Bluetooth. Esta pila define cómo se establecen y mantienen las conexiones, cómo se descubren y usan los servicios, y cómo se transfieren los datos entre los dispositivos.

### ¿Para qué sirve?

La pila de protocolos Bluetooth permite la interoperabilidad entre diferentes dispositivos, como teléfonos móviles, ordenadores, auriculares inalámbricos y otros dispositivos habilitados para Bluetooth. Proporciona una estructura estándar que asegura que los dispositivos puedan comunicarse de manera eficiente y efectiva.

### Componentes Principales de la Pila

1. **Bluetooth Radio**: La capa física que maneja la transmisión y recepción de señales de radio.
2. **Baseband**: Maneja las conexiones y desconexiones de dispositivos, así como la sincronización de datos.
3. **LMP (Link Manager Protocol)**: Responsable del establecimiento de enlaces, la autenticación y la configuración de parámetros de enlace.
4. **L2CAP (Logical Link Control and Adaptation Protocol)**: Proporciona multiplexación de datos, segmentación y reensamblaje.
5. **RFCOMM (Radio Frequency Communication)**: Emula puertos serie para la transmisión de datos.
6. **SDP (Service Discovery Protocol)**: Facilita la búsqueda de servicios disponibles en dispositivos Bluetooth.
7. **Protocolos de Aplicación**: Incluyen OBEX para transferencia de archivos y comandos AT para la configuración de dispositivos.

### Componentes clave

Los componentes clave relevantes para este proyecto son los siguientes:

1. **SDP (Service Discovery Protocol)**
    - **Función**: Permite descubrir los servicios disponibles en otros dispositivos Bluetooth.
    - **Proceso**: Utiliza PDU (Protocol Data Units) para consultas y respuestas, proporcionando información sobre servicios disponibles y parámetros de conexión.
    - **Resultado**: Devuelve la URL del servicio para establecer conexiones.

2. **L2CAP (Logical Link Control and Adaptation Protocol)**
    - **Función**: Multiplexación de datos, segmentación y reensamblaje, y manejo de calidad de servicio (QoS).
    - **Ubicación**: Actúa como capa intermedia entre niveles superiores (como SDP) y niveles inferiores (como el controlador Bluetooth).

3. **RFCOMM (Radio Frequency Communication)**
    - **Función**: Emulación de puertos serie para transmisión de datos entre dispositivos Bluetooth.
    - **Uso**: Crucial para la comunicación en aplicaciones cliente-servidor.

# Outputs esperados
Ya que este proyecto depende de la pila de protocolos bluetooth, ya introducidos estos aspectos,
voy a enseñar el resultado esperado de los códigos proporcionados

## Descubrimiento de servicios
Para el descubrimiento, en mi caso particular tengo lo siguiente:
![Logo](Descubrimiento_Servicios/Descubrimiento.png)

En el que el programa encuentra dos dispositivos, mi teléfono y mis auriculares inalámbricos.

Para los auriculares no se encuentran servicios concretos en la foto, mientras que en el teléfono se detectan 
servicios de audio y control remoto, junto a sus URLs de conexión.

## Comunicación cliente/servidor mediante Bluetooth
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

# Descubrimiento de servicios
## Clase principal

### Inicialización
La clase Descubrimiento comienza obteniendo el dispositivo Bluetooth local y su agente de descubrimiento. Estos componentes son esenciales para realizar la búsqueda de dispositivos y servicios.

### Configuración del Listener
Se crea una instancia de MyDiscoveryListener, que es responsable de manejar los eventos de descubrimiento de dispositivos y servicios. Este listener se inicializa con un objeto para sincronización y el ID del dispositivo objetivo.

### Búsqueda de Dispositivos
El método startInquiry del DiscoveryAgent inicia la búsqueda de dispositivos Bluetooth. Utiliza el código de acceso general (GIAC) para detectar la mayoría de los dispositivos Bluetooth. La búsqueda se realiza de manera asíncrona, y el programa espera hasta que se complete la búsqueda utilizando la sincronización del objeto inquiryCompletedEvent.

### Sincronización y Manejo de Resultados
El programa utiliza sincronización para esperar la finalización de la búsqueda de dispositivos. Una vez completada, se calcula y muestra el tiempo de búsqueda. Los dispositivos encontrados se almacenan en una lista obtenida del listener.

### Búsqueda de Servicios
Para cada dispositivo encontrado, se realiza una búsqueda de servicios específicos utilizando searchServices. Se buscan servicios con un UUID particular "0x1002" (Public Browse Group), para la búsqueda de servicios generales en dispositivos bluetooth y se especifica un conjunto de atributos con el UID "0x0100". La búsqueda de servicios también se realiza de manera asíncrona, y el programa espera los resultados antes de continuar.

## Clase Listener: MyDiscoveryListener
### Constructor
El constructor de MyDiscoveryListener inicializa las listas de dispositivos y URLs de servicios, y recibe el objeto de sincronización y el ID del dispositivo objetivo.

### deviceDiscovered
Este método se llama cuando se descubre un dispositivo. Si el dispositivo encontrado coincide con el ID especificado (ya sea por nombre o dirección Bluetooth), se añade a la lista de dispositivos y se imprime su información.

### inquiryCompleted
Este método se llama al finalizar la búsqueda de dispositivos. Si no se encontraron dispositivos, se imprime un mensaje indicándolo. Luego, se notifica al objeto de sincronización para continuar con el flujo del programa.

### servicesDiscovered
Este método se llama cuando se descubren servicios en un dispositivo. Para cada servicio encontrado, se obtiene y muestra su nombre y URL de conexión. Esto permite identificar los servicios disponibles y cómo conectarse a ellos.

### serviceSearchCompleted
Este método se llama al finalizar la búsqueda de servicios. Similar a inquiryCompleted, notifica al objeto de sincronización para permitir que el programa continúe con el procesamiento de resultados.

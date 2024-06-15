# Chat cliente-servidor
El objetivo es establecer una conexión y permitir el intercambio de mensajes entre dispositivos a través de Bluetooth.

## Cliente

El código del cliente inicia una búsqueda de dispositivos Bluetooth cercanos utilizando la clase DiscoveryAgent. Utiliza el método startInquiry con DiscoveryAgent.GIAC como parámetro para iniciar la búsqueda de dispositivos que utilizan una dirección única y permanente. Durante esta búsqueda, un objeto MyDiscoveryListener actúa como listener para capturar los dispositivos encontrados. Este listener almacena los dispositivos en una lista y luego realiza una búsqueda de servicios específicos en cada dispositivo encontrado.

Después de identificar el dispositivo deseado, el cliente busca servicios con un UUID específico (0x1101 en este caso, que identifica el servicio de chat). El cliente utiliza el método searchServices del DiscoveryAgent para este propósito, proporcionando el conjunto de atributos y UUIDs adecuados para la búsqueda. El resultado de esta búsqueda se maneja nuevamente a través del listener MyDiscoveryListener, que captura la URL del servicio encontrado.

Con la URL del servicio obtenida, el cliente establece una conexión utilizando Connector.open(URL). Esto abre una conexión Bluetooth con el dispositivo remoto que proporciona el servicio de chat. Se configuran flujos de entrada y salida (InputStream y OutputStream) para manejar la comunicación bidireccional entre el cliente y el servidor. El cliente espera la entrada del usuario desde la consola, envía mensajes al servidor a través del flujo de salida, y espera la respuesta del servidor a través del flujo de entrada. Esta comunicación continúa hasta que el usuario envía "FIN." para terminar la sesión, momento en el cual se cierran los flujos y la conexión Bluetooth.

## Servidor

El servidor inicia su funcionalidad configurando un StreamConnectionNotifier en un URL específico que incluye un UUID (0x1101) para identificar el servicio de chat. Este objeto permite al servidor aceptar conexiones entrantes de clientes a través de Bluetooth. Al iniciarse, el servidor imprime información relevante como la dirección Bluetooth del dispositivo local y su nombre amigable.

Cuando un cliente se conecta utilizando acceptAndOpen() en el StreamConnectionNotifier, el servidor obtiene una conexión de tipo StreamConnection y recupera información sobre el dispositivo remoto utilizando RemoteDevice.getRemoteDevice(). Se establecen flujos de entrada (InputStream) y salida (OutputStream) para permitir la comunicación bidireccional con el cliente conectado.

El servidor espera recibir mensajes del cliente a través del InputStream, los muestra en la consola junto con la identificación del dispositivo remoto. Luego, espera la entrada del usuario local desde la consola, envía la respuesta al cliente a través del OutputStream, y espera nuevos mensajes del cliente. Este ciclo de recibir y enviar mensajes continúa hasta que el cliente envía "FIN.", momento en el cual el servidor cierra los flujos de conexión y finaliza la sesión.

# Prueba técnica Android con Kotlin y Mapbox

Esta aplicación permite a los usuarios visualizar un mapa interactivo utilizando el SDK de Mapbox, agregar puntos personalizados sobre el mapa (tanto normales como de alerta), guardar estos puntos como favoritos y consultarlos posteriormente desde una sección dedicada. También se cargan datos geoespaciales desde un servicio remoto usando un archivo GeoJSON.

🔧 Tecnologías y herramientas principales
Tecnología | Descripción 
--- | --- 
Room | Librería de persistencia local sobre SQLite para almacenar puntos de forma permanente.
Hilt | Framework de inyección de dependencias para desacoplar componentes y facilitar testing.
Retrofit | Cliente HTTP para consumir servicios REST (GeoJSON). Utiliza Gson para deserialización.
Gson | Biblioteca de serialización/deserialización de JSON en Kotlin/Java.
Mapbox | Maps SDK SDK para visualización y manipulación avanzada de mapas.
Jetpack Compose | Toolkit moderno para construir interfaces de usuario declarativas y reactivas.
Kotlin | Coroutines Permite operaciones asíncronas limpias para red, base de datos y animaciones.

## ¿Como usar la aplicación?
1. La primera vez que ingresas a la aplicación es importante que tengas acceso a internet para descargar los puntos
2. Le puedes dar click a ver el mapa para ver el mapa con el sdk de mapbox y con los puntos descargados
3. Si dejas presionado un punto del mapa te sale un popup para añadir un nuevo punto, ya sea normal o tipo alerta
4. Puedes acceder a tus puntos en el botón abajo a la derecha con ícono de estrella y si le das click a ver, el mapa te mostrará el lugar donde está el punto
5. Si le das click al botón abajo a la derecha con el ícono de la ubicación, te llevará a tu ubicación actual
6. Y si le das click al botón abajo a la derecha con el ícono de configuración, puedes elegir que estilo de mapa ver

# Sistema de gestion de mantenimiento

En este documento estara presentado los pasos necesarios y requisitos para instalar y ejecutar los servicios que haran funcionar el aplicativo pedido. 

No se tendran en cuenta aspectos de infraestructura ni aquellos que tengan que ver con seguridad, esta guia solo proporciona informacion para poder ejecutar los aplicativos por parte del lector en su entorno de trabajo


## Requisitos
### Hardware
- Almenos 16GB RAM
- 4GB Espacio en disco 
### Software
- Windows mayor o igual a 7
- Gestor de paquetes NPM
- Node
- Docker Desktop
- Git
- Navegador web
- Intellij
- Android Studio
- Visual Studio Code

(La instalación de las herramientas quedan por fuera de esta guia, dado que existe mucha informacion en linea sobre como instalarlas)

## Variables de entorno

To run this project, you will need to add the following environment variables to your .env file

`API_KEY`

`ANOTHER_API_KEY`


## Instalación

- Abrir una terminal de comandos en el sitio donde desea guardar el proyecto
- Con el comando git clone clonar el repositorio
```cmd
  git clone --recurse-submodules https://git.utec.edu.uy/CodigoCreativo/pft/proyecto-final-tecnicatura.git
  cd proyecto-final-tecnicatura
```
- Recuerda usar el comando cd para acceder a la raiz
- Con un editor como VSCode abra el archivo datasource.sh y cargarbasededatos.sh cuenten con tipo de retorno LT
- verifique que esos archivos se encuentran en el formato correcto como LT y no CRLF 
- En consola anterior colocar el comando docker
```cmd
docker-compose up -d
``` 
- Finalizado todo puede cerrar la ventana de comandos
- Abrir Docker Desktop y ver que efectivamente el container esta iniciado
- Felicidades ya tienes los servicios instalados
## Despliegue

### Desplegar backend
Para desplegar el proyecto debe 
- Abrir la ubicacion http://localhost:9990 con un navegador
- Ingresar con las credenciales
```txt
Usuario: Admin
Contraseña: Admin123
```
- Ir a deployments
- Subir el archivo que se encuentra en deployments/nombredelarchivo.war
- Esperar a finalizar
- Ya tienes tus endpoints (backend) funcionando

### Desplegar App Web
- Abrir una ventana de comandos en /frontend/
- Introducir los comandos en orden a medida que finalice cada uno
```cmd
npm install
npm run dev
```
- Sera avisado del link al cual debe ingresar y tendra su AppWeb desplegada.

### Desplegar app mobile
- Abrir su dispositivo Android (Debe poder aceptar instalación desde origenes desconocidos)
- Conectarse mediante cable y descargar al movil la aplicacion .apk en deployments
- Instalar y ejecutar
- Requiere internet

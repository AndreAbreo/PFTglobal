

# Paso 2: Copiar el archivo WAR al contenedor de WildFly
$warFilePath = "./target/ServidorApp-1.0-SNAPSHOT.war"
$containerPath = "/opt/jboss/wildfly/standalone/deployments/"
$wildflyContainer = "wildfly"

if (Test-Path $warFilePath) {
    Write-Host "Copiando archivo WAR al contenedor de WildFly..."
    docker cp $warFilePath "${wildflyContainer}:$containerPath"
} else {
    Write-Host "Archivo WAR no encontrado. Asegúrate de que la compilación haya tenido éxito y que el archivo exista en el directorio target."
    Exit 1
}

# Paso 3: Desplegar el archivo WAR usando el CLI de WildFly
Write-Host "Desplegando el archivo WAR usando el CLI de WildFly..."
docker exec -it $wildflyContainer /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deploy $containerPath/ServidorApp-1.0-SNAPSHOT.war --force"

Write-Host "Despliegue completado."

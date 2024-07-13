# Usamos la imagen de WildFly con Java 17
FROM quay.io/wildfly/wildfly:latest-jdk17

# Copiamos el driver de Oracle
COPY drivers/ojdbc11.jar /opt/jboss/wildfly/modules/system/layers/base/com/oracle/ojdbc/main/

# Exponemos el puerto de la consola de administración
EXPOSE 9990

# Creamos el usuario admin
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#007 --silent

# Copiamos el script de configuración de datasource
COPY datasource.sh /opt/jboss/wildfly/bin/datasource.sh

# Cambiamos el usuario a root para poder cambiar los permisos del script
USER root

# cambiamos los permisos del script
RUN chown jboss:jboss /opt/jboss/wildfly/bin/datasource.sh && chmod +x /opt/jboss/wildfly/bin/datasource.sh

# Cambiamos el usuario a jboss
USER jboss

# Ejectuamos el script de configuración de datasource
RUN /opt/jboss/wildfly/bin/datasource.sh

#Definimos el comando de arranque
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
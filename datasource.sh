#!/bin/sh

JBOSS_CLI=/opt/jboss/wildfly/bin/jboss-cli.sh
JBOSS_HOME=/opt/jboss/wildfly

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}
# ..
# Iniciar WildFly
$JBOSS_HOME/bin/standalone.sh &

# Esperamos a que el servidor esté listo
wait_for_server

# Añadir el módulo de Oracle
$JBOSS_CLI -c "module add --name=com.oracle.ojdbc --resources=$JBOSS_HOME/modules/system/layers/base/com/oracle/ojdbc/main/ojdbc11.jar --dependencies=javax.api,javax.transaction.api"

# Registrar el driver de Oracle
$JBOSS_CLI -c "/subsystem=datasources/jdbc-driver=oracle:add(driver-name=oracle,driver-module-name=com.oracle.ojdbc,driver-xa-datasource-class-name=oracle.jdbc.xa.client.OracleXADataSource)"

# Comprobar si el datasource ya existe ante
if $JBOSS_CLI -c "/subsystem=datasources/data-source=OracleDS:read-resource" | grep -q 'outcome => success'; then
  echo "Datasource OracleDS already exists, skipping addition."
else
  # Añadir el datasource de Oracle
  $JBOSS_CLI -c "/subsystem=datasources/data-source=OracleDS: add(jndi-name=java:/OracleDS,driver-name=oracle,connection-url=jdbc:oracle:thin:@//oracle-db:1521/DESARROLLO,user-name=pinfra,password=pinfra)"
fi
# desacrivar el escaneo de despliegues
$JBOSS_CLI -c "/subsystem=deployment-scanner/scanner=default:write-attribute(name=\"scan-enabled\",value=false)"
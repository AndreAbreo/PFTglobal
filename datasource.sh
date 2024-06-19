#!/bin/sh

JBOSS_CLI=/opt/jboss/wildfly/bin/jboss-cli.sh
JBOSS_HOME=/opt/jboss/wildfly

function wait_for_server() {
  until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
    sleep 1
  done
}
# ..
# Start WildFly in the background
$JBOSS_HOME/bin/standalone.sh &

# Wait for WildFly to start
wait_for_server

# Add the Oracle JDBC driver as a module
$JBOSS_CLI -c "module add --name=com.oracle.ojdbc --resources=$JBOSS_HOME/modules/system/layers/base/com/oracle/ojdbc/main/ojdbc11.jar --dependencies=javax.api,javax.transaction.api"

# Register the JDBC driver
$JBOSS_CLI -c "/subsystem=datasources/jdbc-driver=oracle:add(driver-name=oracle,driver-module-name=com.oracle.ojdbc,driver-xa-datasource-class-name=oracle.jdbc.xa.client.OracleXADataSource)"

# Check if the datasource exists
if $JBOSS_CLI -c "/subsystem=datasources/data-source=OracleDS:read-resource" | grep -q 'outcome => success'; then
  echo "Datasource OracleDS already exists, skipping addition."
else
  # Run the datasource configuration commands
  $JBOSS_CLI -c "/subsystem=datasources/data-source=OracleDS: add(jndi-name=java:/OracleDS,driver-name=oracle,connection-url=jdbc:oracle:thin:@//oracle-db:1521/DESARROLLO,user-name=pinfra,password=pinfra)"
fi
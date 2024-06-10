Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/bionic64"

  config.vm.provider "virtualbox" do |v|
    v.memory = 4096
    v.cpus = 2
  end

  config.vm.network "forwarded_port", guest: 8080, host: 8080
  config.vm.network "forwarded_port", guest: 9990, host: 9990
  config.vm.network "forwarded_port", guest: 9993, host: 9993
  config.vm.network "forwarded_port", guest: 8443, host: 8443
  config.vm.network "forwarded_port", guest: 1521, host: 1521
  config.vm.network "forwarded_port", guest: 3000, host: 3000

  config.vm.synced_folder "./frontend", "/usr/src/app"
  config.vm.synced_folder "./backend", "/opt/jboss/wildfly/standalone/deployments"

  config.vm.provision "shell", inline: <<-SHELL
    apt-get update
    apt-get install -y apt-transport-https ca-certificates curl software-properties-common
    curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
    add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
    apt-get update
    apt-get install -y docker-ce
    curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
    chmod +x /usr/local/bin/docker-compose

    # Movemos node_modules a una carpeta compartida para evitar problemas de carpetas compartidas
    mkdir -p /vagrant_node_modules
    mount --bind /vagrant_node_modules /vagrant/frontend/node_modules
  SHELL

  config.trigger.after :up, :reload do |trigger|
    trigger.name = "Launching Docker Compose"
    trigger.info = "Running docker-compose up -d"
    trigger.run_remote = {inline: "cd /vagrant/ && docker-compose up -d"}
  end
end
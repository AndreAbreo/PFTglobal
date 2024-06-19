    Vagrant.configure("2") do |config|
      config.vm.box = "ubuntu/jammy64"

      config.vm.provider "virtualbox" do |v|
        v.customize ["setextradata", :id, "VBoxInternal2/SharedFoldersEnableSymlinksCreate/v-root", "1"]
      end

      config.vm.provider "virtualbox" do |v|
        v.memory = 4096
        v.cpus = 2
      end

      config.vm.network "forwarded_port", guest: 8080, host: 8080
      config.vm.network "forwarded_port", guest: 9990, host: 9990
      config.vm.network "forwarded_port", guest: 9993, host: 9993
      config.vm.network "forwarded_port", guest: 8443, host: 8443
      config.vm.network "forwarded_port", guest: 1521, host: 1521

      config.vm.network "forwarded_port", guest: 2375, host: 2375


      config.vm.synced_folder "./frontend", "/usr/src/app"
      config.vm.synced_folder "./backend/deployments", "/opt/jboss/wildfly/standalone/deployments"

      config.vm.provision "shell", inline: <<-SHELL
        apt-get update
        apt-get install -y apt-transport-https ca-certificates curl software-properties-common
        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
        add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
        apt-get update
        apt-get install -y docker-ce
        curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
        chmod +x /usr/local/bin/docker-compose
        echo "TESTTESTESTESTESTESTESTESTESTESTEST########################################################"

        # Movemos node_modules a una carpeta compartida para evitar problemas de carpetas compartidas
        mkdir -p /vagrant_node_modules
        mkdir -p /vagrant/frontend/node_modules
        mount --bind /vagrant_node_modules /vagrant/frontend/node_modules
        chmod -R 777 /vagrant/frontend/node_modules

        # Install nvm (Node Version Manager)
        curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
        export NVM_DIR="$HOME/.nvm"
        [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
        [ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion

        # Install Node.js using nvm
        nvm install 20

        # Confirugamos Docker para que escuche en el puerto 2375 (para poder usarlo desde el host windows con IntelliJ)
        # En caso de que no funcionen los comandos, ejecutarlos manualmente en la máquina virtual
        sudo mkdir -p /etc/systemd/system/docker.service.d
        echo '[Service]' | sudo tee /etc/systemd/system/docker.service.d/override.conf
        echo 'ExecStart=' | sudo tee -a /etc/systemd/system/docker.service.d/override.conf
        echo 'ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock' | sudo tee -a /etc/systemd/system/docker.service.d/override.conf
        sudo systemctl daemon-reload
        sudo systemctl restart docker
      SHELL

      config.vm.provision "shell", run: "always", inline: <<-SHELL
        cd /vagrant/
        sudo docker compose up -d --build
      SHELL


    end
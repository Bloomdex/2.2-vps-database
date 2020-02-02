# 2.2-vps-database

## Requirements

#### Required

* Docker 18.09.7 or higher
* Git

#### Recommended

* A working [automated nginx-proxy](https://github.com/jwilder/nginx-proxy)

## Getting Started

There are multiple methods of retrieving the source files:

#### Method 1: Cloning

```
git clone https://github.com/Bloomdex/2.2-vps-database.git
cd 2.2-vps-database/
```

#### Method 2: Saving

```
wget https://github.com/Bloomdex/2.2-vps-database/archive/master.zip
unzip 2.2-vps-database-master.zip
cd 2.2-vps-database/
```

## Building

Building the final product is done using a multi-stage building `Dockerfile`.

```
cd 2.2-vps-database-master/
docker build -t LutzenH/datamcbaseface .
```

To confirm the image has been built perform the command:

```
docker images
```

## Configuration

In order to get the database running a folder has to be created on the host-machine with configuration settings for the database:

* Create a folder at a given directory
* Add your own TLS `.jks` keystore file to the created folder.
* Create a new file inside the folder called: `application.properties`

`application.properties` should contain the following settings (only change the settings provided with a comment #):

```
server.port=8080
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.name=bloomdex
spring.datasource.url=jdbc:h2:file:/var/datamcbaseface/database/bloomdex;AUTO_SERVER=TRUE
spring.datasource.username=bloomdex
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update

# The name of your .jks keystore file
bloomdex.server.keyStoreFileName=keystore.jks

# The password of your .jks keystore file
bloomdex.server.keyStorePassword=passphrase

bloomdex.server.port=445
```

## Running

To run the container certain parameters have to be given to the docker-container in order to function properly:

#### Method 1: Behind an automated nginx-proxy (recommended)

If you want to run the container behind an [automated nginx-proxy](https://github.com/jwilder/nginx-proxy) (which can also be used to use the database using https instead of http) use the following run command:

* First change `$(pwd)/datamcbaseface` to the directory you want to store the database and configuration files (see chapter Configuration).

```
docker run -d -p 445:445 -v $(pwd)/datamcbaseface:/var/datamcbaseface \
--name datamcbaseface_desperate_torvalds \
-e VIRTUAL_HOST=api.vegaflor.bloomdex.org \
-e VIRTUAL_PORT=8080 \
datamcbaseface:latest
```

This will automatically map the containers-port: 8080 to the hostname provided in VIRTUAL_HOST. It will also expose the port 445 to the host-machine, this port is used by the data-collection-clients to connect to the server. The above command will run the container with the following name: `datamcbaseface_desperate_torvalds`.

If everything has been set-up properly the database should be accessible with the hostname provided using `VIRTUAL_HOST`.

#### Method 2: Without an automated nginx-proxy

The following method should only be used in a testing environment since the connection between the client and the web-server is not protected with https.

* First change `$(pwd)/datamcbaseface` to the directory you want to store the database and configuration files (see chapter Configuration).

Make sure port `445` and `8080` are not being used by your host-machine.

```
docker run -d -p 445:445 -p 8080:8080 -v $(pwd)/datamcbaseface:/var/datamcbaseface \
--name datamcbaseface_desperate_torvalds \
datamcbaseface:latest
```

This will run the docker container with the ports 445 and 8080 exposed to your host-machine.

If everyting has been set-up properly the database should be accessible using [http://localhost:8080/](http://localhost:8080/) (with the right credentials. see next chapter).

## Accessing the database

The only way to access the database is with the right user account which should be in the database. If during the Running chapter no database has been provided in the given directory, A testing database will be created with the following testing credentials: 

* Username: `bloomdex-test-dummy`
* Password: `bloomdex-donotuse-temporary-password`

These credentials should be given using [http-basic-authentication](https://en.wikipedia.org/wiki/Basic_access_authentication). This can easily be done using an application like [Postman](https://www.getpostman.com/) or [Insomnia](https://insomnia.rest/). A recommended first query to perform is checking the provided users account status using: `GET /api/v1/me` If you are using the testing environment, This query can be accessed by performing a GET request on the following URL with the proper authentication credentials: [http://localhost:8080/api/v1/me](http://localhost:8080/api/v1/me),

Something similar to this should be returned by the server:

```json
{
    "username": "bloomdex-test-dummy",
    "enabled": true,
    "authorities": [
        "ROLE_USER"
    ]
}
```

Take a look at the [query specifications](https://github.com/Bloomdex/2.2-vps-database/blob/master/documentation/database_queries_specification.pdf) to see what other queries are available to be used.

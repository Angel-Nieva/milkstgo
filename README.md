# Técnicas de Ingeniería de Software PEP 1 2023-1: Aplicación Monolítica

- Autor: Angel Nieva
- Sección:A-1
- Profesor: Alcides Quispe

## Descripción

En este repositorio se encuentra la aplicación monolítica desarrollada para la PEP 1 de Técnicas de Ingeniería de Software en 2023. La aplicación esta desarrollada principalmente en Java, utilizando [SpringBoot](https://start.spring.io) y HTML.

## Herramientas utilizadas

Se utilizan las siguientes herramientas principales para desarrollar el proyecto:

- [Java 18](https://www.oracle.com/java/technologies/downloads/): La aplicación utiliza la Programación Orientada a Objetos y se desarrolla utilizando capas, compuestas por Servicios, Entidades, Controladores y Repositorios
- [IntelliJ IDEA Ultimate 2022.2.2](https://www.jetbrains.com/idea/download/#section=windows): IDE perfecto para trabajar con Java y SpringBoot. Tiene bastante buena compatibilidad con varios plugins y es perfecto para desarollar un proyecto monolítico.
- [Visual Studio Code](https://code.visualstudio.com): IDE con multiples compatibilidades que sirve como alternativa a IntelliJ y para editar archivos no provenientes de Java.
- [Docker / Docker-Compose / Docker Desktop](https://www.docker.com): Se utiliza Docker junto con Docker-Compose para crear contenedores de Imágenes y asi poder levantar la aplicación en distintos PCs localmente. Las imágenes de Docker se descargan desde [Docker Hub](https://hub.docker.com).
- [Jenkins](https://www.jenkins.io): Se utiliza para automatizar todo el proceso de el ensamblado de la aplicación junto con la creación de imágenes de Docker y la subida de estas a Docker Hub.
- [SonarQube](https://www.sonarqube.org): Se utiliza para testear código y obtener los Code Smells del proyecto (Buenas prácticas)
- [Terraform](https://learn.hashicorp.com/tutorials/terraform/install-cli?in=terraform/aws-get-started): Se utiliza para poder levantar la aplicación en un servidor web, como lo es [AWS](https://aws.amazon.com/es/)

## Comandos importantes

Los siguientes son comandos importantes para la presentacion.

### Terraform

- Levantar Máquina Virtual de AWS:

```sh
ssh -i key.pem ec2-user@(IP4 PUBLICA)

En mi caso seria:
ssh -i key.pem ec2-user@34.218.253.87
finalmente:
docker-compose --compatibility up

luego de presentar:
docker-compose down
exit
```

### Jenkins

En la carpeta donde se encuentra el archivo "jenkins.war":

- Levantar Jenkins (localhost:8080/):

```sh
java -jar jenkins.war
```

### SonarQube

Para analizar manualmente con SonarQube, en la carpeta del proyecto:
sonarqube-9.9.0.65466/bin/windows-x86-64:


```sh
StartSonar.bat
```



#!/bin/sh -e
echo
echo "Compilando con javac ..."
javac *.java
sleep 2

echo
echo "Lanzando el servidor"
java -cp . -Djava.rmi.server.codebase=file:./ -Djava.rmi.server.hostname=localhost -Djava.security.policy=server.policy servidor $1
sleep 2

javac *.java
echo
echo "Lanzando el cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente $1
echo
echo "Lanzando el primer cliente"
echo
# java -cp . -Djava.security.policy=server.policy Cliente_Ejemplo localhost 10
# sleep 2

echo
echo "Lanzando el segundo cliente"
echo
java -cp . -Djava.security.policy=server.policy cliente localhost 3
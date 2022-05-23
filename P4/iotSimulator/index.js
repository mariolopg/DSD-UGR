const HttpServer = require("./src/servidores/httpServer.js")
const DbServer = require("./src/servidores/dbServer.js");

var dbServer = new DbServer();
var httpServer = new HttpServer(dbServer);

dbServer.iniciar();
httpServer.iniciar();
var http = require("http");
var url = require("url");
var fs = require("fs");
var path = require("path");
const SocketIOServer = require("./socketio.js");
var mimeTypes = { "html": "text/html", "jpeg": "image/jpeg", "jpg": "image/jpeg", "png": "image/png", "js": "text/javascript", "css": "text/css", "swf": "application/x-shockwave-flash"};

class HttpServer{
    constructor (dataBase, port = 8080){
        this.port = port;
        this.dataBase = dataBase;
        this.server = http.createServer(
            ((request, response) => {
                var ruta = this.ruta(request.url);
                this.readPage(response, ruta)
            })
        );
        this.socketio = new SocketIOServer(this.server)
    }

    //Mediante fs lee el html correspondiente a la peticion del cliente.
    readPage(response, fname){
        fs.readFile(fname, function (err, data) {
            if (!err) {
                var extension = fname.split(".")[1];
                var type = mimeTypes[extension];
                var code = 200;
                response.writeHead(code, type);
                response.write(data);
            }
            else {
                response.writeHead(301, { "Location": "/404" });
                console.log("Peticion invalida: " + uri);
                            response.writeHead(302, {"Location": "404"});
                            response.end();
            }

            response.end();
        });
    }

    ruta(uri){
        var ruta = "";
        var directorio = "public/pages/";
        switch (uri) {
            case "/": case "/home":
                ruta = directorio + "home.html";
                break;

            case "/controlador":
                ruta = directorio + "controlador.html";
                break;

            case "/historico":
                ruta = directorio + "historico.html";
                // var switch_aire = this.dataBase.getData("switch-aire");
                // this.socketio.emit("historico-log", switch_aire)
                // this.socketio.emit("historico-log", {time:"hola", valor: "valor"})
                break;

            case "/404":
                ruta = directorio + "404.html";
                break;
        
            default:
                var peticion = uri.slice(1).split("/");
                if(peticion[0] == 'controlador'){
                    ruta = directorio + "controlador.html";
                    console.log(peticion[1] + " " + peticion[2])
                    var evento = {time:getTimeStamp(), valor:peticion[2]};
                    this.dataBase.insertar(peticion[1], evento)
                    this.socketio.emit(peticion[1], evento)

                    // Emision de alerta
                    if(peticion.length == 4){
                        this.socketio.emit("alerta", peticion[3])
                    }
                }
                else{
                    ruta = path.join(process.cwd(), uri);
                }
                break;
        }

        return ruta;
    }



    /// Lanza el servidor Http
    iniciar() {
        this.socketio.iniciar();
        this.server.listen(this.port);

        //Compruebo si el servidor se inicia o no.
        if (this.server.listening) {
            console.log("Servicio HTTP iniciado");
        } else {
            throw new Error("Error")
        }

    }
}

function getTimeStamp(){
    var date = new Date();

    var dia = parseDate(date.getUTCDate())
    var mes = parseDate(date.getUTCMonth() + 1)
    var ano = parseDate(date.getFullYear())
    var hora = parseDate(date.getHours())
    var minutos = parseDate(date.getMinutes())
    var segundos = parseDate(date.getSeconds())

    return dia + "/" + mes + "/" + ano + " - " + hora + ":" + minutos + ":" + segundos;
}

function parseDate(date){
    if(date < 10)
        return "0" + date;
    return date;
}

module.exports = HttpServer;
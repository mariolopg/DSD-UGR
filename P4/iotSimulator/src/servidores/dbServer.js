var MongoClient = require('mongodb').MongoClient;

class DbServer {
    constructor(url = "mongodb://localhost:27017/", port = 8080, nombre = "DSDP4"){
        this.url = url;
        this.port = port;
        this.nombre = nombre; 
        this.dataBase = null;      
    }

    getDB(){
        return this.dataBase;
    }

    iniciar(){
        MongoClient.connect(this.url, { useUnifiedTopology: true })
        .then( (connection) => {
            this.dataBase = connection.db(this.nombre);
            console.log("Servidor Mongo iniciado");
        }
        ).catch((err) => {
            console.log("Error al iniciar servidor Mongo");
        })
    }

   async getData(nombre){
        var data = await this.dataBase.collection(nombre).find().toArray();
        return data;
    }

    async insertar(nombre, valores){
        return await this.dataBase.collection(nombre).insertOne(valores);
    }
}

module.exports = DbServer

var MongoClient = require('mongodb').MongoClient;

class DbServer {
    constructor(url = "mongodb://localhost:27017/", port = 8080, nombre = "DSDP4"){
        this.port = port;
        this.nombre = nombre; 
        this.dataBase = null;      
    }

    static getDB(){
        return this.dataBase;
    }

    iniciar(){
        MongoClient.connect("mongodb://localhost:27017/", { useUnifiedTopology: true })
        .then( (connection) => {
            this.dataBase = connection.db(this.nombre);
            console.log("Servidor Mongo iniciado");
        }
        ).catch((err) => {
            console.log("Error al iniciar servidor Mongo");
        })
    }

    async existeTabla(nombre){
        var existe = false;
        var tablas = await this.db.listCollections().toArray();
        var nombreTablas = tablas.map(t => t.nombre);
        if(nombreTablas.includes(nombre))
            existe = true;

        return existe;
    }

    crearTabla(nombre){
        if(!this.existeTabla(nombre))
            this.dataBase.createCollection(nombre, function (err, res) {
                if(err) throw err;
                console.log("Tabla " + nombre + " creada");
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

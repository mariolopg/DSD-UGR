var socketio = require("socket.io");

class SockerIOServer {
    constructor(httpServer){
        this.socket = socketio(httpServer);
    }

    emit (event, data) {
        this.socket.sockets.emit(event, data);
    }

    iniciar(){
        console.log("Socket IO iniciado");

        this.socket.sockets.on( 'connection', (socket) => {
            socket.on('start-session', (data) =>{
                var sessionID = data.sessionID;
                socket.emit('set-session-acknowledgement', {
                    sessionID,
                    name: data.name
                })
            })
        })
    }
}

module.exports = SockerIOServer;
window.history.forward(1);
var historicoLeidoAire = false;
var historicoLeidoPersiana = false;
var historicoLeidoTemperatura = false;
var historicoLeidoLuminosidad = false;

window.onload = function (){
    var httpRequest = new XMLHttpRequest()
    httpRequest.open("GET", document.URL + "/lectura", false)
    httpRequest.send(null)
};

var socket = io();

function setEstado(id, estado){
    document.getElementById(id).innerText = estado;
}

function setColor(id, color) {
    document.getElementById(id).style.color = color;
}

// Socket
// Prelectura
socket.on("historico-aire", function (data) {
    if(!historicoLeidoAire){
        data.forEach(evento => {
            var estado = evento.valor;
            setColor("estado-aire", "rgb(208, 0, 0)");

            if(estado == "encendido"){
                estado = "encendido";
                setColor("estado-aire", "green")
            }
            setEstado("estado-aire", estado)
        })
        historicoLeidoAire = true;
    }
})

socket.on("historico-persiana", function (data) {
    if(!historicoLeidoPersiana){
        data.forEach(evento => {
            var estado = evento.valor;
            setColor("estado-persiana", "rgb(208, 0, 0)");

            if(estado == "encendido"){
                estado = "encendido";
                setColor("estado-persiana", "green")
            }
            setEstado("estado-persiana", estado)
        })
        historicoLeidoPersiana = true;
    }
})

socket.on("historico-temperatura", function (data) {
    if(!historicoLeidoTemperatura){
        data.forEach(evento => {
            setEstado("estado-temperatura", evento.valor);
        });
        historicoLeidoTemperatura = true;
    }
})

socket.on("historico-luminosidad", function (data) {
    if(!historicoLeidoLuminosidad){
        data.forEach(evento => {
            setEstado("estado-luminosidad", evento.valor);
        });
        historicoLeidoLuminosidad = true;
    }
})

socket.on("switch-aire", function (data) {
    var estado = data.valor;
    setColor("estado-aire", "rgb(208, 0, 0)")

    if(estado == "encendido"){
        setColor("estado-aire", "green")
    }
    setEstado("estado-aire", estado)
})

socket.on("switch-persiana", function (data) {
    var estado = data.valor;
    setColor("estado-persiana", "rgb(208, 0, 0)");

    if(estado == "encendido"){
        estado = "encendido";
        setColor("estado-persiana", "green")
    }
    setEstado("estado-persiana", estado)
})

socket.on("slider-temperatura", function (data) {
    setEstado("estado-temperatura", data.valor);
})

socket.on("slider-luminosidad", function (data) {
    setEstado("estado-luminosidad", data.valor);
})

socket.on("alerta", function (data) {
    parseMsg(data);
})

function parseMsg(data) {
    var pieces = data.split('%20');
    var msg = "";
    pieces.forEach(element => {
        msg += element + " ";
    });
    alert(msg)
}
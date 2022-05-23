var socket = io();

function setEstado(id, estado){
    document.getElementById(id).innerText = estado;
}

function setColor(id, color) {
    document.getElementById(id).style.color = color;
}

// Socket
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
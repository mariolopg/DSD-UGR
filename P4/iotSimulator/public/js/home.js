var socket = io();

function setEstado(id, estado){
    document.getElementById(id).innerText = estado;
}

function setColor(id, color) {
    document.getElementById(id).style.color = color;
}

// Socket
socket.on("switch-aire", function (data) {
    var estado = "apagado";
    setColor("estado-aire", "rgb(208, 0, 0)")

    if(data == "on"){
        estado = "encendido";
        setColor("estado-aire", "green")
    }
    setEstado("estado-aire", estado)
})

socket.on("switch-persiana", function (data) {
    var estado = "apagado";
    setColor("estado-persiana", "rgb(208, 0, 0)");

    if(data == "on"){
        estado = "encendido";
        setColor("estado-persiana", "green")
    }
    setEstado("estado-persiana", estado)
})

socket.on("slider-temperatura", function (data) {
    setEstado("estado-temperatura", data);
})

socket.on("slider-luminosidad", function (data) {
    setEstado("estado-luminosidad", data);
})
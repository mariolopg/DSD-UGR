window.history.forward(1);
var historicoLeidoAire = false;
var historicoLeidoPersiana = false;
var historicoLeidoTemperatura = false;
var historicoLeidoAire = false;

window.onload = function (){
    // Sensores
    document.getElementById("slider-temperatura").addEventListener("change", slider("slider-temperatura"));
    document.getElementById("slider-luminosidad").addEventListener("change", slider("slider-luminosidad"));
    document.getElementById("switch-aire").addEventListener("click", toggleAC);
    document.getElementById("switch-persiana").addEventListener("click", togglePersiana);

    var httpRequest = new XMLHttpRequest()
    httpRequest.open("GET", document.URL + "/lectura", false)
    httpRequest.send(null)
};

var socket = io();

function sendActualizacion(id, value){
    var serviceURL = document.URL;
    var url = serviceURL + "/" + id + "/" + value;
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", url, true);
    httpRequest.send(null);
}

function sendAlerta(id, status, mensaje){
    setToggleStatus(id, status);
    var modo = "apagado"
    if(status)
        modo = "encendido"
    sendActualizacion(id, (modo + mensaje));
}

// Slider
function slider(id){
    var slider = document.getElementById(id);
    var sliderOutPut = document.getElementById(id + "-value");
    slider.oninput = function() {
        sliderOutPut.innerHTML = this.value;
        sendActualizacion(id, this.value)
        switch (id) {
            case "slider-temperatura":
                    var status = getToggleStatus("switch-aire");
                    if(this.value > 30 && status != "encendido"){
                        sendAlerta("switch-aire", true, "/Se ha encendido el aire porque hace mucho calor");
                    }
                    else if(this.value < 20 && status != "apagado"){
                        sendAlerta("switch-aire", false, "/Se ha apagado el aire porque hace mucho frio");
                    }
                break;
        
            case "slider-luminosidad":
                    var status = getToggleStatus("switch-persiana");
                    if(this.value > 80 && status != "apagado"){
                        sendAlerta("switch-persiana", false, "/Se ha bajado la persiana porque entra demasiada luz");
                    }
                    else if(this.value < 20 && status != "encendido"){
                        sendAlerta("switch-persiana", true, "/Se ha subido la persiana porque entra muy poca luz");
                    }
                break;
        }
    }
}

function umbralToggleSwitcher(id, status) {
    setToggleStatus(id, status);
    var modo = "apagado"
    if(status)
        modo = "encendido"
    sendActualizacion(id, modo);
}

function setSlider(id, value) {
    document.getElementById(id).value = value;
    document.getElementById(id + "-value").innerHTML = value;
}

// ToggleSwitch
function toggleSwitch(id) {
    var status = getToggleStatus(id);
    sendActualizacion(id, status)
}

function toggleAC() {
    toggleSwitch("switch-aire")
}

function togglePersiana() {
    toggleSwitch("switch-persiana")
}

function getToggleStatus(id) {
    var checked = document.getElementById(id).checked;
    var status = "apagado";
    if(checked)
        status = "encendido";

    return status;
}

function setToggleStatus(id, status) {
    document.getElementById(id).checked = status;
}


// Socket
// Prelectura
socket.on("historico-aire", function (data) {
    if(!historicoLeidoAire){
        data.forEach(evento => {
            var status = false;
            if(evento.valor == "encendido")
                status = true;
            setToggleStatus("switch-aire", status)
        })
        historicoLeidoAire = true;
    }
})

socket.on("historico-persiana", function (data) {
    if(!historicoLeidoPersiana){
        data.forEach(evento => {
            var status = false;
            if(evento.valor == "encendido")
                status = true;
            setToggleStatus("switch-persiana", status)
        })
        historicoLeidoPersiana = true;
    }
})

socket.on("historico-temperatura", function (data) {
    if(!historicoLeidoTemperatura){
        data.forEach(evento => {
            setSlider("slider-temperatura", evento.valor);
        });
        historicoLeidoTemperatura = true;
    }
})

socket.on("historico-luminosidad", function (data) {
    if(!historicoLeidoLuminosidad){
        data.forEach(evento => {
            setSlider("slider-luminosidad", evento.valor);
        });
        historicoLeidoLuminosidad = true;
    }
})

socket.on("switch-aire", function (data) {
    var status = false;
    if(data.valor == "encendido")
        status = true;
    setToggleStatus("switch-aire", status)
})

socket.on("switch-persiana", function (data) {
    var status = false;
    if(data.valor == "encendido")
        status = true;
    setToggleStatus("switch-persiana", status)
})

socket.on("slider-temperatura", function (data) {
    setSlider("slider-temperatura", data.valor);
})

socket.on("slider-luminosidad", function (data) {
    setSlider("slider-luminosidad", data.valor);
})

socket.on("alerta", function (data) {
    var alerta = parseMsg(data);
    alert(alerta)
})

function parseMsg(data) {
    var pieces = data.split('%20');
    var msg = "";
    pieces.forEach(element => {
        msg += element + " ";
    });
    return msg;
}
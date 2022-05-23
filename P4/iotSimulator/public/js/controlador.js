window.onload = function (){
    // document.getElementById("actualizar-button").addEventListener("click", actualizarDispositivos);
    // Sensores
    document.getElementById("slider-temperatura").addEventListener("change", slider("slider-temperatura"));
    document.getElementById("slider-luminosidad").addEventListener("change", slider("slider-luminosidad"));
    document.getElementById("switch-aire").addEventListener("click", toggleAC);
    document.getElementById("switch-persiana").addEventListener("click", togglePersiana);
};

var socket = io();

function sendActualizacion(id, value){
    var serviceURL = document.URL;
    var url = serviceURL + "/" + id + "/" + value;
    var httpRequest = new XMLHttpRequest();
    httpRequest.open("GET", url, true);
    httpRequest.send(null);
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
                        umbralToggleSwitcher("switch-aire", true)
                    }
                    else if(this.value < 20 && status != "apagado"){
                        umbralToggleSwitcher("switch-aire", false)
                    }
                break;
        
            case "slider-luminosidad":
                    var status = getToggleStatus("switch-persiana");
                    if(this.value > 80 && status != "apagado"){
                        umbralToggleSwitcher("switch-persiana", false)
                    }
                    else if(this.value < 20 && status != "encendido"){
                        umbralToggleSwitcher("switch-persiana", true)
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
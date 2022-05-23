window.onload = function (){
    document.getElementById("div-ac").addEventListener("click", toggleVisibilityAC);
    document.getElementById("div-persiana").addEventListener("click", toggleVisibilityPersiana);
    document.getElementById("div-temperatura").addEventListener("click", toggleVisibilityTemperatura);
    document.getElementById("div-luminosidad").addEventListener("click", toggleVisibilityLuminosidad);
};

function toggleVisibilityAC() {
    toggleVisibility("historico-ac", "div-ac-arrow")
}
function toggleVisibilityPersiana() {
    toggleVisibility("historico-persiana", "div-persiana-arrow")
}
function toggleVisibilityTemperatura() {
    toggleVisibility("historico-temperatura", "div-temperatura-arrow")
}
function toggleVisibilityLuminosidad() {
    toggleVisibility("historico-luminosidad", "div-luminosidad-arrow")
}

function toggleVisibility(id, arrow_id) {

    var component = document.getElementById(id)
    if(component.style.display === ""){
        component.style.display = "block";
        rotateArrow(arrow_id, "rotate(90deg)");
    }
    else{
        component.style.display = "";
        rotateArrow(arrow_id, "rotate(0deg)");
    }
}

function rotateArrow(id, rotation) {
    document.getElementById(id).style.transform = rotation;
}

function createLi(text) {
    var li = document.createElement("li");
    li.innerText = text;
    return li;
}

function addLi(id, li) {
    document.getElementById(id).prepend(li)
}

// Socket
var socket = io();

socket.on("switch-aire", function (data) {
    addLi("historico-ac", createLi(data.time + " -> se ha " + data.valor))
})

socket.on("switch-persiana", function (data) {
    addLi("historico-persiana", createLi(data.time + " -> se ha " + data.valor))
})

socket.on("slider-temperatura", function (data) {
    addLi("historico-temperatura", createLi(data.time + " -> Tª cambia a " + data.valor))
})

socket.on("slider-luminosidad", function (data) {
    addLi("historico-luminosidad", createLi(data.time + " -> Lum cambia a " + data.valor))
})

socket.on("historico-log", function (data) {
    alerta("entra en socket")
    addLi("historico-ac", createLi(data.time + " -> se ha " + data.valor))
})

function alerta(alerta) {
    alerta(alerta)
}

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


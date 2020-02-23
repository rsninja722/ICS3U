function parseNotes(object) {
    document.getElementById("notes").appendChild(recursiveChild(object));
    document.getElementById("notes").childNodes[1].style.display = "block";
}

var objectColor = "#ea00ff";
var arrayColor = "rgb(77, 132, 251)";
var stringColor = "#4de1f5";

function recursiveChild(obj) {
    let returnChild = document.createElement("div");
    returnChild.style.display = "none";

    // object
    if (Object.prototype.toString.call(obj) === "[object Object]") {
        let keys = Object.keys(obj);
        for (let i = 0; i < keys.length; i++) {
            let section = makeSection(keys[i]);
            section.appendChild(recursiveChild(obj[keys[i]]));
            section.className = "indent";
            let childIsObject = Object.prototype.toString.call(obj[keys[i]]) === "[object Object]";
            section.childNodes[1].style.color = childIsObject ? objectColor : arrayColor;
            if(!childIsObject) {
                section.childNodes[0].innerText = "-";
                let childrenToShow = section.childNodes[2].childNodes;
                for(let j=0;j<childrenToShow.length;j++) {
                    childrenToShow[j].style.display = "block";
                }
                section.childNodes[2].style.display = "block";
            }
            returnChild.appendChild(section);
        }
    }

    // array
    if (Object.prototype.toString.call(obj) === "[object Array]") {

        for (let i = 0; i < obj.length; i++) {
            returnChild.appendChild(recursiveChild(obj[i]));
        }
    }

    // string
    if (Object.prototype.toString.call(obj) === "[object String]") {
        var span = document.createElement("span");
        span.innerText = obj;
        span.className = "indent";
        span.style.color = stringColor;
        returnChild.appendChild(span);
        // returnChild.style.display = "block";
    }
    
    return returnChild;
}

function makeSection(name) {
    var div = document.createElement("div");

    var button = document.createElement("button");
    button.innerText = "+";
    button.onclick = sectionClick;
    div.appendChild(button);

    var span = document.createElement("span");
    span.innerText = name;
    div.appendChild(span);

    return div;
}

function sectionClick() {
    var children = this.parentElement.childNodes;
    if (this.innerText === "+") {
        children[2].style.display = "block";
        this.innerText = "-";
    } else {
        children[2].style.display = "none";
        this.innerText = "+";
    }
}


fetch("./notes.json").then((response) => response.json().then(data => { parseNotes(data); }));
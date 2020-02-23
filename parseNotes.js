function parseNotes(object) {

    console.log(object)

    // document.getElementById("notes").appendChild(recursiveChild());
}


fetch("./notes.json").then((response) => response.text().then((data) => { parseNotes(data); }));
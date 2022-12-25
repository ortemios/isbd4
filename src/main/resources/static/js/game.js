
async function sayHi() {
    if (await (await fetch('/havePerson')).text() == "0") {
        document.location.href = '/logout'
    }


}

setInterval(sayHi, 700);

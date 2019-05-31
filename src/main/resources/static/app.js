const issueTable = document.getElementById("issueTable");
const searchButton = document.getElementById("searchButton");
const searchField = document.getElementById("searchField");

async function getIssues() {
    try {
        const reply = await fetch('https://spring-boot-rest-itpark.herokuapp.com/api/issues');
        const data = await reply.json();
        console.log(data);
        createTableBody(data);
    }
    catch (e) {
        console.error(e);
    }
}

async function searchForIssues(query) {
    try {
        const reply = await fetch(`https://spring-boot-rest-itpark.herokuapp.com/api/search/issues?q=${query}`);
        const data = await reply.json();
        console.log(data);
        if(issueTable.childElementCount === 2) {
            issueTable.removeChild(issueTable.lastChild);
        }
        createTableBody(data);
    }
    catch (e) {
        console.error(e);
    }
}

function createTableBody(array) {
    const tbody = document.createElement('tbody');
    for(const i of array) {
        let tr = document.createElement('tr');

        let td = document.createElement('td');
        let textNode = document.createTextNode(i.id);
        td.appendChild(textNode);
        tr.appendChild(td);

        td = document.createElement('td');
        textNode = document.createTextNode(i.title);
        td.appendChild(textNode);
        tr.appendChild(td);

        td = document.createElement('td');
        textNode = document.createTextNode(i.description);
        td.appendChild(textNode);
        tr.appendChild(td);

        td = document.createElement('td');
        textNode = document.createTextNode(new Date(i.date).toISOString().slice(0, 19).replace('T', ' '));
        td.appendChild(textNode);
        tr.appendChild(td);

        td = document.createElement('td');
        textNode = document.createTextNode(i.votes);
        td.appendChild(textNode);
        tr.appendChild(td);

        td = document.createElement('td');
        let labelTitles = i.labels.map(function(label) {
            return label.title;
        });
        textNode = document.createTextNode(labelTitles.join(', '));
        td.appendChild(textNode);
        tr.appendChild(td);

        tbody.appendChild(tr);
    }
    issueTable.appendChild(tbody);
}

if(issueTable.childElementCount === 1) {
    getIssues();
}

searchButton.addEventListener('click', function () {
    searchForIssues(searchField.value);
});

searchField.addEventListener('keyup', function (e) {
    if(e.code === 'Enter') {
        searchForIssues(this.value);
    }
});

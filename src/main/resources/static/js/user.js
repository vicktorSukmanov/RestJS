const url = 'http://localhost:8080/api/user';


function getPage() {
    fetch(url).then(response => response.json()).then(user =>
        getInformation(user))
}

function getInformation(user) {

    document.getElementById('userTable').innerHTML = `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(r=>r.name)}</td>
        </tr>`;

    document.getElementById('userData').innerHTML = ` 
        <p style="color: aliceblue " className="navbar-brand text-white" >${user.username} with Role: ${user.roles.map(r=>r.name)}</p>

    <form className="form-inline">
        <a className="text-light" href="/logout">Logout</a>
    </form>
      `

}

getPage();
const url = 'http://localhost:8080/api/';


function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            loadTable(data)
        })
}




function loadTable(listAllUsers) {
    let res = ``;
    for (let user of listAllUsers) {
        res +=
            `<tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>
                <td>${user.email}</td>
            <td>${user.roles.map(r=>r.name)}</td>
                <td>
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#editModal"
                    onclick="editModal(${user.id})">Edit</button></td>
                <td>
                     <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteModal"
                    onclick="deleteModal(${user.id})">Delete</button></td>
            </tr>`
    }
    document.getElementById('tableBodyAdmin').innerHTML = res;

}

function getAuthUser(){
    let url2 = url + 'user'
    fetch(url2).then(response => response.json()).then((userA)=>{
        document.getElementById('adminData').innerHTML = ` 
        <p style="color: aliceblue " className="navbar-brand text-white" >${userA.username} with Role: ${userA.roles.map(r=>r.name)}</p>

    <form className="form-inline">
        <a className="text-light" href="/logout">Logout</a>
    </form>
      `
    })
}



// function newUserTab() {
   document.getElementById('newUserForm').addEventListener('submit', (e) => {
        e.preventDefault()
        let role = document.getElementById('rolesNew')
        let rolesAddUser = []
        for (let i = 0; i < role.options.length; i++) {
            if (role.options[i].selected) {
                rolesAddUser.push({id: role.options[i].value, name})
            }
        }
       fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                username: document.getElementById('usernameAdd').value,
                lastName: document.getElementById('lastNameAdd').value,
                age: document.getElementById('ageNew').value,
                email: document.getElementById('emailNew').value,
                password: document.getElementById('passwordNew').value,
                roles: rolesAddUser
            })
        })
            .then((response) => {
                if (response.ok) {
                    document.getElementById('usernameAdd').value = '';
                    document.getElementById('lastNameAdd').value = '';
                    document.getElementById('ageNew').value = '';
                    document.getElementById('emailNew').value = '';
                    document.getElementById('passwordNew').value = '';
                    document.getElementById('nav-home-tab').click()
                    getAllUsers();
                }
            })
    })
// }

function closeModal() {
    document.querySelectorAll(".btn-close").forEach((btn) => btn.click())
}


function editModal(id) {
    let editId = url + id;
    fetch(editId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('exampleInputID').value = user.id;
            document.getElementById('username').value = user.username;
            document.getElementById('lastName').value = user.lastName;
            document.getElementById('age').value = user.age;
            document.getElementById('email').value = user.email;
            document.getElementById('password').value = '';
        })
    });

}


async function editUser() {
    let idValue = document.getElementById('exampleInputID').value;
    let usernameValue = document.getElementById('username').value;
    let lastNameValue = document.getElementById('lastName').value;
    let ageValue = document.getElementById('age').value;
    let emailValue = document.getElementById('email').value;
    let passwordValue = document.getElementById('password').value;
    let role = document.getElementById('rolesEdit')
    console.log(role)
    let listOfRole = []
    for (let i = 0; i < role.options.length; i++) {
        if (role.options[i].selected) {
            listOfRole.push({id: role.options[i].value, name})
        }
    }
    let user = {
        id: idValue,
        username: usernameValue,
        lastName: lastNameValue,
        age: ageValue,
        email: emailValue,
        password: passwordValue,
        roles: listOfRole
    }
    await fetch(url, {
        method: 'PATCH',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify(user)
    });
     closeModal()
     getAllUsers()

}


function deleteModal(id) {
    let delId = url + id;
    fetch(delId, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(res => {
        res.json().then(user => {
            document.getElementById('idDelete').value = user.id;
            document.getElementById('usernameDelete').value = user.username;
            document.getElementById('lastNameDelete').value = user.lastName;
            document.getElementById('ageDelete').value = user.age;
            document.getElementById('emailDelete').value = user.email;
        })
    });
}

async function deleteUser() {
    const id = document.getElementById('idDelete').value
    let urlDel = url + id;

    let method = {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        }
    }
    fetch(urlDel, method).then(() => {
        closeModal()
        getAllUsers()
    })

}
getAllUsers()
getAuthUser()
function getUserPage() {
    let url2 = url + 'user'
    fetch(url2).then(response => response.json()).then(user =>
    informationAboutUser(user))

}



function informationAboutUser(user) {
    // user.roles.map(r => {
    //     if (r.name.replace('ROLE_', '') === 'ADMIN') {
    //         getAllUsers()
    //         // newUserTab()
    //     }
    // })
    document.getElementById('userTableBody').innerHTML = `<tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.lastName}</td>
            <td>${user.age}</td>
            <td>${user.email}</td>
            <td>${user.roles.map(r => r.name)}</td>
        </tr>`;

}


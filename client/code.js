// let editingId = 0;
// let soldiers = [];

// function handleLinkClick(event, section) {
//     event.preventDefault();
//     showSection(section);
// }

// function showSection(section) {
//     const allSections = document.querySelectorAll("section");
//     for (const section of allSections) {
//         section.hidden = true;
//     }
//     document.querySelector("#" + section).hidden = false;
// }

// showSection("list-section");

// async function getAll() {
//     fetch("http://localhost:8080/api/soldier", { method: "GET", headers: { "Accept": "application/json" } })
//     .then((response) => {
//         if (response.status >= 200 && response.status < 300) {
//             return response.json();
//         } else {
//             return;
//         }
//     })
//     .then((json) => {
//         soldiers = json;
//         let html = "";
//         for (soldier of json) {
//             html += `<tr>
//                         <td>${soldier.firstName}</td>
//                         <td>${soldier.middleName}</td>
//                         <td>${soldier.lastName}</td>
//                         <td>${soldier.dob}</td>
//                         <td>${soldier.heightInInches}</td>
//                         <td><button class="btn btn-primary" onclick="update(${soldier.soldierId})">Edit</button></td>
//                         <td><button class="btn btn-danger" onclick="doDelete(${soldier.soldierId})">Delete</button></td>
//                     </tr>`;
//         }
//         document.getElementById("list-contents").innerHTML = html;
//     });
// }

// function handleCreateSoldierClick(event) {
//     event.preventDefault();
//     editingId = 0; // Reset the editingId to indicate creating a new soldier
//     document.querySelector("#form").reset(); // Clear the form
//     showSection("form-section");
// }

// function handleFormSubmit(event) {
//     event.preventDefault();
//     const data = {
//         firstName: document.querySelector("#first-input").value,
//         middleName: document.querySelector("#middle-input").value,
//         lastName: document.querySelector("#last-input").value,
//         dob: document.querySelector("#dob-input").value,
//         heightInInches: document.querySelector("#height-input").value,
//     };

//     const method = editingId === 0 ? "POST" : "PUT";
//     const url = editingId === 0 ? "http://localhost:8080/api/soldier" : `http://localhost:8080/api/soldier/${editingId}`;
    
//     fetch(url, {
//         method: method,
//         headers: {
//             "Content-Type": "application/json",
//             "Accept": "application/json",
//         },
//         body: JSON.stringify(data),
//     })
//     .then((response) => {
//         if (response.status >= 200 && response.status < 300) {
//             document.querySelector("#errors").innerHTML = "";
//             editingId = 0; // Reset editingId after submission
//             document.querySelector("#form").reset(); // Clear the form after submission
//             showSection("list-section");
//             getAll(); // Refresh the list of soldiers
//         } else {
//             return response.json();
//         }
//     })
//     .then((json) => {
//         if (json) {
//             let html = "";
//             for (const error of json) {
//                 html += `<div>${error}</div>`;
//             }
//             document.querySelector("#errors").innerHTML = html;
//         }
//     });
// }

// async function update(id) {
//     const soldier = soldiers.find(s => s.soldierId === id);
//     document.querySelector("#first-input").value = soldier.firstName;
//     document.querySelector("#middle-input").value = soldier.middleName;
//     document.querySelector("#last-input").value = soldier.lastName;
//     document.querySelector("#dob-input").value = soldier.dob;
//     document.querySelector("#height-input").value = soldier.heightInInches;
//     editingId = soldier.soldierId;
//     showSection("form-section");
// }

// async function doDelete(id) {
//     if (confirm(`Are you sure you want to delete?`)) {
//         fetch(`http://localhost:8080/api/soldier/${id}`, { method: "DELETE" })
//         .then(response => {
//             if (response.status === 204) {
//                 console.log("Delete success.");
//                 getAll(); // Refresh the list after deletion
//             } else if (response.status === 404) {
//                 console.log("Soldier not found.");
//             } else {
//                 console.log(`Delete failed with status: ${response.status}`);
//             }
//         });
//     }
// }

// getAll(); // Initial fetch to populate the soldier list











let editingId = 0;
let soldiers = [];

function handleLinkClick(event) {
    event.preventDefault();
    const section = event.target.pathname.replace("/", "")
    showSection(section)
}

function showSection(section) {
    const allSections = document.querySelectorAll("section")
    for (const section of allSections) {
        section.hidden = true;
    }
    document.querySelector("#" + section).hidden = false;
}

showSection("list-section")


async function getAll() {
  fetch("http://localhost:8080/api/soldier", {method: "GET", headers: {"Accept": "application/json"}})
  .then((response) => {
    if (response.status >= 200 && response.status < 300) {
        return response.json();
    } else {
        return;
    }
  })
  .then((json) => {
    soldiers = json;
    let html = "";
    for (soldier of json) {
      html += `<tr>
                  <td>${soldier.firstName}</td>
                  <td>${soldier.middleName}</td>
                  <td>${soldier.lastName}</td>
                  <td>${soldier.dob}</td>
                  <td>${soldier.heightInInches}</td>
                  <td><button class="btn btn-primary" onclick="update(${soldier.soldierId})">Edit</button></td>
                  <td><button class="btn btn-danger" onclick="doDelete(${soldier.soldierId})">Delete</button></td>
                </tr>`
    }
    document.getElementById("list-contents").innerHTML = html;
  })
}


async function getById() {
  fetch("http://localhost:8080/api/soldier", {
      method: "GET",
      headers: {
          "Accept": "application/json"
      }
  })
  .then((response) => {
      if (response.status >= 200 && response.status < 300) {
          return response.json();
      } else {
          console.log("error");
          return;
      } 
  })
  .then((json) => {
      let html = "";
      for (soldier of json) {
          html += `<li>
              firstName: ${soldier.firstName},
              middleName: ${soldier.middleName},
              lastName: ${soldier.lastName},
              dob: ${soldier.dob},
              heightInInches: ${soldier.heightInInches}
              <button onclick=update(${soldier.soldierId})>Edit</button>
              <button onclick=delete(${soldier.soldierId})>Delete</button>
              </li>`
      }
      document.querySelector("#list-contents").innerHTML = html
  })
}

function handleCreateSoldierClick(event) {
    event.preventDefault();
    editingId = 0; // Reset the editingId to indicate creating a new soldier
    document.querySelector("#form").reset(); // Clear the form
    showSection("form-section"); // Show the form section
}


function handleFormSubmit(event) {
  event.preventDefault();
  data = {
      firstName: document.querySelector("#first-input").value,
      middleName: document.querySelector("#middle-input").value,
      lastName: document.querySelector("#last-input").value,
      dob: document.querySelector("#dob-input").value,
      heightInInches: document.querySelector("#height-input").value,
  }

  if (editingId !== 0) {
      data.soldierId = editingId
  }

  const method = editingId === 0 ? "POST" : "PUT"
  const url = editingId === 0 ? "http://localhost:8080/api/soldier" : `http://localhost:8080/api/soldier/${editingId}`
  fetch(url, {
      method: method,
      headers: {
          "Content-Type": "application/json",
          "Accept": "application/json"
      },
      body: JSON.stringify(data)
  })
  .then((response) => {
      if (response.status >= 200 && response.status < 300) {
          document.querySelector("#errors").innerHTML = "";
          editingId = 0;
          document.querySelector("#form").reset()
          showSection("list-section")
          getAll();
      } else {
          return response.json();
      }
  })
  .then((json) => {
      if (json) {
          let html = ""
          for (error of json) {
              html += `<div>${error}</div>`
          }
          document.querySelector("#errors").innerHTML = html;
      }
  })
}


async function update(id) {
  let soldier;
  for (a of soldiers) {
      if (a.soldierId == id) {
        soldier = a;
      }
  }
  document.querySelector("#first-input").value = soldier.firstName;
  document.querySelector("#middle-input").value = soldier.middleName;
  document.querySelector("#last-input").value = soldier.lastName;
  document.querySelector("#dob-input").value = soldier.dob;
  document.querySelector("#height-input").value = soldier.heightInInches;
  editingId = soldier.soldierId;
  showSection("form-section");
}


async function doDelete(id) {
    if (confirm(`Are you sure you want to delete?`)) {
        fetch(`http://localhost:8080/api/soldier/${id}`, { method: "DELETE" })
        .then(response => {
            if (response.status === 204) {
                console.log("Delete success.");
                getAll(); // Refresh the list after deletion
            } else if (response.status === 404) {
                console.log("Soldier not found.");
            } else {
                console.log(`Delete failed with status: ${response.status}`);
            }
        });
    }
}


getAll()
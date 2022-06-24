let today = new Date();
let tomorrow = new Date(today.getTime() + MILLIS_PER_DAY);
let btnCase;
let mentor;

window.onload = function () {
    getCurrentMentor();
    newReviewRequests();
};

function findReview(reviewFilterDTO) {
    $.ajax({
        method: 'POST',
        url: '/reviews',
        contentType: 'application/json',
        data: JSON.stringify(reviewFilterDTO),
        success: function (response) {
            console.log(response);
            drawColumns(response);
        },
        error: function (error) {
            console.log(error);
        }
    })
}

function drawColumns(data) {
    while (document.getElementById("review-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("review-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        data.sort(function (a, b) {
            var dateA = new Date(a.bookedDate), dateB = new Date(b.bookedDate)
            return dateA - dateB //sort by date ascending
        });
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("review-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.id, tr);
    insertTd(data.topic, tr);
    insertTd(data.studentLogin, tr);
    insertTd(data.mentorLogin, tr);
    insertTd(data.bookedDate, tr);
    if (data.bookedTime != null) {
        insertTd(data.bookedTime.substring(0, 5), tr);
    } else insertTd(data.bookedTime, tr);


    let review = {}
    review.id = data.id;
    review.topic = data.topic;
    review.studentLogin = data.studentLogin;
    review.mentorLogin = data.mentorLogin;
    review.bookedDate = data.bookedDate;
    review.bookedTime = data.bookedTime;
    review.timeSlots = data.timeSlots;

    let times = data.timeSlots.toString().split(",");
    for (i = 0; i < times.length; i++) {
        times[i] = times[i].substring(0, 5);
    }
    times.sort();

    if (btnCase === 1) {
        for (let i = 0; i < times.length; i++) {
            let sumId = "#lastColumn" + i;
            $(sumId).append("<th scope=\"col\" id=\"lastColumn" + i + "\">  </th>");
            let acceptBtn = document.createElement("button");
            acceptBtn.className = "btn btn-success";
            acceptBtn.innerHTML = times[i];
            acceptBtn.type = "submit";
            acceptBtn.addEventListener("click", () => {
                review.bookedTime = times[i];
                editReview(review);
            });
            td = tr.insertCell(6 + i);
            td.insertAdjacentElement("beforeend", acceptBtn);
        }
    }

    if (btnCase === 2) {
        let deleteBtn = document.createElement("button");
        deleteBtn.className = "btn btn-danger";
        deleteBtn.innerHTML = "Cancel";
        deleteBtn.type = "submit";
        deleteBtn.addEventListener("click", () => {
            deleteReview(data.id);
        });
        td = tr.insertCell(6);
        td.insertAdjacentElement("beforeend", deleteBtn);
    }
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function getCurrentMentor() {
    $.ajax({
        url: '/platformUser/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (currentUser) {
            mentor = currentUser.login;
        }
    })
}

function newReviewRequests() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.bookedDate = null;
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    findReview(reviewFilterDTO);
}

function myReview() {
    btnCase = 2;
    let reviewFilterDTO = {};
    reviewFilterDTO.bookedDate = null;
    reviewFilterDTO.mentorLogin = mentor;
    reviewFilterDTO.studentLogin = null;
    findReview(reviewFilterDTO);
}

function todayReview() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    reviewFilterDTO.bookedDate = today;
    findReview(reviewFilterDTO);
}

function tomorrowReview() {
    btnCase = 1;
    let reviewFilterDTO = {};
    reviewFilterDTO.mentorLogin = null;
    reviewFilterDTO.studentLogin = null;
    reviewFilterDTO.bookedDate = tomorrow;
    findReview(reviewFilterDTO);
}

function editReview(reviewDTO) {
    let confirmation = confirm("Вы уверены, что хотите взять ревью " + reviewDTO.id + " и назначить его время на " + reviewDTO.bookedTime + "?");
    if (confirmation === true) {
        $.ajax({
            url: '/reviews',
            method: 'PATCH',
            contentType: 'application/json',
            data: JSON.stringify(reviewDTO),
            success: function () {
                console.log('accepted')
            },
            error: function (error) {
                console.log(error);
            }
        })
        location.reload();
    }
}

function deleteReview(id) {
    let confirmation = confirm("Вы уверены, что хотите отменить ревью " + id + "?");
    if (confirmation === true) {
        $.ajax({
            url: '/reviews?id=' + id,
            method: 'DELETE',
            success: function () {
                console.log('deleted')
            },
            error: function (error) {
                console.log(error);
            }
        })
        location.reload();
    }
}
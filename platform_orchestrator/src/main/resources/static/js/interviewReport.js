window.onload = function () {
    findInterviewReport();
};

let currentUser;

function getCurrentUser() {
    $.ajax({
        url: '/questions/current',
        type: 'GET',
        contentType: 'application/json',
        success: function (user) {
            currentUser = user;
        }
    })
}

function findInterviewReport() {
    getCurrentUser();
    $.ajax({
        method: 'GET',
        url: '/interviewReport',
        contentType: 'application/json',
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
    while (document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0].rows[0])
        document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0].deleteRow(0);
    for (let i = 0; i < data.length; i++) {
        addColumn(data[i]);
    }
}

function addColumn(data) {
    let table = document.getElementById("interviewReport-table").getElementsByTagName("tbody")[0];
    let tr = table.insertRow(table.rows.length);
    let td;

    insertTd(data.date, tr);
    insertTd(data.email, tr);
    insertTd(data.company, tr);
    insertTd(data.project, tr);
    insertTd(data.questions, tr);
    insertTd(data.impression, tr);
    insertTd(data.minSalary, tr);
    insertTd(data.maxSalary, tr);
    insertTd(data.level, tr);

    let interviewReport = {}
    interviewReport.id = data.id;
    interviewReport.date = data.date;
    interviewReport.email = data.email;
    interviewReport.company = data.company;
    interviewReport.project = data.project;
    interviewReport.questions = data.questions;
    interviewReport.impression = data.impression;
    interviewReport.minSalary = data.minSalary;
    interviewReport.maxSalary = data.maxSalary;
    interviewReport.status = data.status;
    interviewReport.level = data.level;
    interviewReport.userId = data.userId;

    if (data.userId === currentUser.id && data.status === "Passed") {
        let offerBtn = document.createElement("button");
        offerBtn.className = "btn btn-warning";
        offerBtn.innerHTML = "Got An Offer";
        offerBtn.type = "submit";
        offerBtn.addEventListener("click", () => {
            updateInterviewReport(interviewReport);
        });
        td = tr.insertCell(9);
        td.insertAdjacentElement("beforeend", offerBtn);
    }

    if (data.userId === currentUser.id && data.status === "Offer") {
        let acceptedBtn = document.createElement("button");
        acceptedBtn.className = "btn btn-success";
        acceptedBtn.innerHTML = "Accepted The Offer";
        acceptedBtn.type = "submit";
        acceptedBtn.addEventListener("click", () => {
            updateInterviewReport(interviewReport);
        });
        td = tr.insertCell(9);
        td.insertAdjacentElement("beforeend", acceptedBtn);
    }
}

function insertTd(value, parent) {
    let element = document.createElement("td");
    element.scope = "row";
    element.innerText = value;
    parent.insertAdjacentElement("beforeend", element)
}

function saveInterviewReport(interviewReportDTO) {
    $.ajax({
        url: '/interviewReport',
        method: 'PATCH',
        contentType: 'application/json',
        data: JSON.stringify(interviewReportDTO),
        success: function () {
            console.log('saved')
        },
        error: function (error) {
            console.log(error);
        }
    })
    location.reload();
}

function updateInterviewReport(interviewReportDTO) {
    let confirmation =
        confirm("Вы уверены, что хотите изменить статус?");

    if (confirmation === true) {
        interviewReportDTO.maxSalary = prompt("Уточните зарплату на руки", interviewReportDTO.maxSalary);
        interviewReportDTO.minSalary = interviewReportDTO.maxSalary;

        if (interviewReportDTO.status === "Offer") {
            interviewReportDTO.status = "Accepted";
        }
        if (interviewReportDTO.status === "Passed") {
            interviewReportDTO.status = "Offer";
        }
        saveInterviewReport(interviewReportDTO);
    }
}

function sendInterviewReport() {
    let interviewReport = {}
    interviewReport.date = $("#interviewReport-date").val();
    interviewReport.email = $("#interviewReport-email").val();
    interviewReport.company = $("#interviewReport-company").val();
    interviewReport.project = $("#interviewReport-project").val();
    interviewReport.questions = $("#interviewReport-questions").val();
    interviewReport.impression = $("#interviewReport-impression").val();
    interviewReport.minSalary = $("#interviewReport-min").val();
    interviewReport.maxSalary = $("#interviewReport-max").val();
    interviewReport.status = $("#interviewReport-status").val();
    interviewReport.level = $("#interviewReport-level").val();

    let confirmation =
        confirm("Отправить отчёт о собеседовании?");

    if (confirmation === true) {
        saveInterviewReport(interviewReport);
    }
}

function openInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "block";
}

function closeInterviewReportForm() {
    document.getElementById("interviewReportForm").style.display = "none";
}
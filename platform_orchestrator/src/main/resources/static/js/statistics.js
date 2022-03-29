const statisticsData = getStatistics(20)
const dataOfHardTasks = {
    datasets: [{
        label: 'Самые сложные задачи',
        backgroundColor: 'rgb(100, 99, 132)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.hardTasks,
    }]
};

const dataOfSteps = {
    datasets: [{
        label: 'Самые сложные главы',
        backgroundColor: 'rgb(100,255,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.hardStepRatio,
    }]
};

const dataOfUsers = {
    datasets: [{
        label: 'Ученики',
        backgroundColor: 'rgb(255,0,255)',
        borderColor: 'rgb(100, 99, 132)',
        data: statisticsData.studentsCodeTry,
    }]
};

const dataOfStatus = {
    datasets: [{
        label: 'Статус решений',
        backgroundColor: [
            'rgb(255, 99, 132)',
            'rgb(54, 162, 235)',
            'rgb(255, 205, 86)',
            'rgb(255,0,255)'
        ],
        data: statisticsData.codeTryStatus,
    }]
};

const configOfHardTasks = {
    type: 'bar',
    data: dataOfHardTasks,
    options: {}
};

const configOfSteps = {
    type: 'bar',
    data: dataOfSteps,
    options: {}
};

const configOfUsers = {
    type: 'bar',
    data: dataOfUsers,
    options: {}
};

const configOfStatus = {
    type: 'bar',
    data: dataOfStatus,
    options: {}
};


var hardTaskChart = new Chart(
    document.getElementById('statsHardTaskChart'),
    configOfHardTasks
);

const stepChart = new Chart(
    document.getElementById('statsStepsChart'),
    configOfSteps
);

const usersChart = new Chart(
    document.getElementById('statsUsersChart'),
    configOfUsers
);

const statusChart = new Chart(
    document.getElementById('statsStatusChart'),
    configOfStatus
);


function getStatForHardTask(size) {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/hardTasks?size=" + size,
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function getStatistics(size) {
    let data;
    $.ajax({
        method: 'GET',
        url: "/statistics/data?size=" + size,
        async: false,
        success: function (response) {
            console.log(response)
            data = response;
        },
        error: function (error) {
            console.log(error);
        }
    });
    return data;
}

function updateHardTaskChart(size){
    hardTaskChart.data = {
        datasets: [{
            label: 'Самые сложные задачи',
            backgroundColor: 'rgb(100, 99, 132)',
            borderColor: 'rgb(100, 99, 132)',
            data: getStatForHardTask(size),
        }]
    };
    hardTaskChart.update();
}
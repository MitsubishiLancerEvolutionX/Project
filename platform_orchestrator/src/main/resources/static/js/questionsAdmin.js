window.onload = function () {
    listAllUsers()
};

function listAllUsers() {
    var navLinkId;
    var vPillId;
    $.ajax({
        url: '/getAllStudents',
        type: 'GET',
        contentType: 'application/json',
        success: function (users) {
            $("#allUserList").empty()
            $("#tabContent").empty()

            $.each(users, function (i, user) {
                navLinkId = i + 'navLink'
                vPillId = 'v-pills-' + i
                $("#allUserList").append('<a class="nav-link" id="' + navLinkId + '" data-toggle="pill" role="tab" ' +
                    'aria-controls="v-pills-' + i + '"  aria-selected="false">' + user.login + '</a>')
                $("#tabContent").append('<div class="tab-pane" id="' + vPillId + '" role="tabpanel" ' +
                    '></div>')

                $("#" + navLinkId).attr("href", "#" + vPillId)
                checkLessonStructure(user.login, vPillId)

            })
            $("#v-pills-0").addClass('active')
            $("#0navLink").attr("aria-selected", true)
            $("#0navLink").addClass('active')
        }
    })
}

function checkLessonStructure(studentLogin, tabPane) {
    let tab = $('#' + tabPane)
    var tableId;
    var chapterName
    $.ajax({
        url: 'questions/chapters',
        type: 'GET',
        contentType: 'application/json',
        success: function (chapters) {
            tab.empty()
            $.each(chapters, function (i, chapter) {
                chapterName = chapter.replaceAll(' ', '')
                tableId = studentLogin + chapterName + 'table'
                tab.append('<br>' +
                    '<div class="card" style="background-color: #f8f9fa">' +
                    '<div class="card-body" >' +
                    '<div class="table-responsive">' +
                    '<table class="table table-hover col">' +
                    '<thead class="table-light" >' +
                    '<tr role="row">' +
                    '<td><h5>' + chapter + '</h5></td>' +
                    '</tr>' +
                    '</thead>' +
                    '<tbody class="table-light" id="' + tableId + '">' +
                    '<tr><td><div id="accordion' + studentLogin + i + '">' +
                    '<div class="card">' +
                    '<div class="card-header" id="heading' + studentLogin + i + '">' +
                    '<h5 class="mb-0">' +
                    '<button class="btn btn-link" data-toggle="collapse" ' +
                    'data-target="#collapse' + studentLogin + i + '" ' +
                    'aria-expanded="true" aria-controls="collapse' + studentLogin + i + '"> ???????????????????? ??????????????' +
                    '</button>' +
                    '</h5>' +
                    '</div>' +
                    '<div id="collapse' + studentLogin + i + '" class="collapse" ' +
                    'aria-labelledby="heading' + studentLogin + i + '" ' +
                    'data-parent="#accordion' + studentLogin + i + '">' +
                    '<div class="card-body">' +
                    '<div class="table-responsive">' +
                    '<table class="table table-hover col">' +
                    '<tbody class="table" id="' + tableId + 'Answered">' +
                    '</tbody></table>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</td></tr></tbody>' +
                    '</table>' +
                    '<button type="button" class="btn btn-primary btn-lg btn-block" data-toggle="collapse" ' +
                    'data-target="#collapseButton' + studentLogin + i + '" aria-expanded="false"' +
                    ' aria-controls="collapseButton' + studentLogin + i + '" ' +
                    'id="button' + studentLogin + i + '">???????????????? ????????????</button>' +
                    '<div class="collapse" id="collapseButton' + studentLogin + i + '">' +
                    '<div class="card card-body">' +
                    '<form id="form' + studentLogin + i + '" >' +
                    '<div class="form-group">' +
                    '<textarea class="form-control" id="newQuestion' + studentLogin + i + '" ' +
                    'placeholder="????????????" rows="1" maxlength="255" required></textarea><br>' +
                    '<button type="submit" id="addButton' + studentLogin + i + '" ' +
                    'class="btn btn-success">????????????????</button>' +
                    '</div>' +
                    '</form>' +
                    '</div>' +
                    '</div>' +
                    '</div></div></div><br>')

                getQuestionsByChapter(studentLogin, chapter, tableId, tabPane)

                $('#form' + studentLogin + i).submit(function (e) {
                    e.preventDefault();
                    saveQuestion(null, $('#newQuestion' + studentLogin + i).val(), chapter, false,
                        studentLogin)
                    checkLessonStructure(studentLogin, tabPane)
                })
            })
        }
    })
}

function getQuestionsByChapter(studentLogin, chapter, table, tabPane) {
    let tableId = $('#' + table)
    let tableAnswered = $('#' + table + 'Answered')
    $.ajax({
        url: 'questions/' + studentLogin + '/' + chapter,
        type: 'GET',
        contentType: 'application/json',
        success: function (questions) {
            $.each(questions, function (i, question) {
                if (question.answered === true) {
                    tableAnswered.append('<tr><td><div class="container-fluid" id="container'
                        + studentLogin + i + table + '"><div class="row">' +
                        '<div class="form-check form-check-inline col-1" style="max-width: 0">' +
                        '<input class="form-check-input" type="checkbox" ' +
                        'id="checkbox' + studentLogin + i + chapter.id + '" checked/>' +
                        '</div>' +
                        '<div class="col-9" style="word-wrap: break-word; max-width: 1400px"><s>' + question.question +
                        '</s></div>' +
                        '<div class="col-1"><button type="button" id="edit' + studentLogin + i + table + '" ' +
                        'class="btn btn-primary btn-sm" data-toggle="collapse" ' +
                        'data-target="#collapse' + studentLogin + i + table + '" aria-expanded="false" ' +
                        'aria-controls="collapse' + studentLogin + i + table + '">????????????????</button>' +
                        '</div>' +
                        '<div class="col-1"><button type="button" id="del' + studentLogin + i + table + '"' +
                        ' class="btn btn-danger btn-sm">??????????????</button>' +
                        '</div>' +
                        '</div>' +
                        '<div class="collapse" id="collapse' + studentLogin + i + table + '">' +
                        '<div class="card card-body">' +
                        '<form id="form' + studentLogin + i + table + '" >' +
                        '<div class="form-group">' +
                        '<textarea class="form-control" id="editQuestion' + studentLogin + i + table + '" ' +
                        'placeholder="????????????" maxlength="255" required>' + question.question + '</textarea><br>' +
                        '<button type="submit" id="editButton' + studentLogin + i + table + '" ' +
                        'class="btn btn-success">??????????????????????</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked !== true) {
                            saveQuestion(question.id, question.question, question.chapter, false,
                                studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                } else {
                    tableId.append('<tr><td><div class="container-fluid"><div class="row">' +
                        '<div class="form-check form-check-inline col-1" style="max-width: 0">' +
                        '<input class="form-check-input" type="checkbox" ' +
                        'id="checkbox' + studentLogin + i + chapter.id + '">' +
                        '</div>' +
                        '<div class="col-9" style="word-wrap: break-word; max-width: 1400px"><p>' + question.question +
                        '</p></div>'+
                        '<div class="col-1"><button type="button" id="edit' + studentLogin + i + table + '" ' +
                        'class="btn btn-primary btn-sm" data-toggle="collapse" ' +
                        'data-target="#collapse' + studentLogin + i + table + '" aria-expanded="false" ' +
                        'aria-controls="collapse' + studentLogin + i + table + '">????????????????</button>' +
                        '</div>' +
                        '<div class="col-1"><button type="button" id="del' + studentLogin + i + table + '" ' +
                        'class="btn btn-danger btn-sm">??????????????</button>' +
                        '</div>' +
                        '</div>' +
                        '<div class="collapse" id="collapse' + studentLogin + i + table + '">' +
                        '<div class="card card-body">' +
                        '<form id="form' + studentLogin + i + table + '" >' +
                        '<div class="form-group">' +
                        '<textarea class="form-control" id="editQuestion' + studentLogin + i + table + '"' +
                        ' placeholder="????????????" maxlength="255" required>' + question.question + '</textarea><br>' +
                        '<button type="submit" id="editButton' + studentLogin + i + table + '" ' +
                        'class="btn btn-success">??????????????????????</button>' +
                        '</div>' +
                        '</form>' +
                        '</div>' +
                        '</div>' +
                        '</div></td></tr>')

                    $('#checkbox' + studentLogin + i + chapter.id).change(function () {
                        if (this.checked) {
                            saveQuestion(question.id, question.question, question.chapter,
                                true, studentLogin, tabPane)
                            checkLessonStructure(studentLogin, tabPane)
                        }
                    })
                }
                $('#del' + studentLogin + i + table).click(function () {
                    deleteQuestion(question.id)
                    checkLessonStructure(studentLogin, tabPane)
                })

                $('#form' + studentLogin + i + table).submit(function (e) {
                    e.preventDefault();
                    deleteQuestion(question.id)
                    saveQuestion(null, $('#editQuestion' + studentLogin + i + table).val(), chapter,
                        question.answered, studentLogin)
                    checkLessonStructure(studentLogin, tabPane)
                })
            })
        }
    })
}

function deleteQuestion(id) {
    $.ajax({
        url: 'questionsAdmin?id=' + id,
        method: 'DELETE',
        success: function () {
            console.log('deleted')
        }
    })
}

function saveQuestion(id, question, chapter, answered, login, tabPane) {
    $.ajax({
        url: 'questionsAdmin',
        dataType: 'json',
        method: 'PATCH',
        cache: false,
        contentType: 'application/json',
        data: JSON.stringify({
            id: id,
            question: question,
            chapter: chapter,
            answered: answered,
            login: login,
        }),
        success: function () {
            console.log('saved')
            checkLessonStructure(login, tabPane)
        }
    })
}
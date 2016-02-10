var express 	= require('express')
var bodyParser 	= require("body-parser");
var multer 		= require('multer'); 

var app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(multer());



app.get('/', function (req, res) {
        res.send('Hello World!')
});


app.post('/login', function (req, res) {
	var loginFunction = require('./loginFunction');
    console.log('/login');
    loginFunction.postLogin(req, res);
});


app.post('/createProject', function (req, res) {
	var createProjectFunction = require('./createProjectFunction');
    console.log('/createProject');
    createProjectFunction.postCreateProject(req, res);
});

app.post('/addMember', function (req, res) {
	var addMemberFunction = require('./addMemberFunction');
    console.log('/addMember');
    addMemberFunction.postAddMember(req, res);
});
app.post('/deleteMember', function (req, res) {
	var deleteMemberFunction= require('./deleteMemberFunction');
    console.log('/deleteMember');
    deleteMemberFunction.postDeleteMember(req, res);
});


app.post('/resetMemberPassword', function (req, res) {
	var addMemberFunction = require('./passwordsFunction');
    console.log('/resetMemberPassword');
    addMemberFunction.postResetMemberPassword(req, res);
});

app.post('/changeMemberPassword', function (req, res) {
	var changeMemberFunction= require('./passwordsFunction');
    console.log('/changeMemberPassword');
    changeMemberFunction.postChangeMemberPassword(req, res);
});

app.post('/changeMemberHasRightAdmin', function (req, res) {
	var memberEditFunction= require('./memberEditFunction');
    console.log('/changeMemberHasRightAdmin');
    memberEditFunction.postMemberEditRightAdmin(req, res);
});

app.post('/changeMemberHasRightEditUsers', function (req, res) {
	var memberEditFunction= require('./memberEditFunction');
    console.log('/changeMemberHasRightEditUsers');
    memberEditFunction.postMemberEditRightEditUsers(req, res);
});

app.post('/addMemberSchedule', function (req, res) {
	var addScheduleFunction= require('./addScheduleFunction');
    console.log('/addMemberSchedule');
    addScheduleFunction.postAddMemberSchedule(req, res);
});

app.post('/deleteMemberSchedule', function (req, res) {
	var deleteScheduleFunction= require('./deleteScheduleFunction');
    console.log('/deleteMemberSchedule');
    deleteScheduleFunction.postDeleteScheduleFunction(req, res);
});
app.post('/changeScheduleStartDate', function (req, res) {
	var changeScheduleFunction= require('./changeScheduleFunction');
    console.log('/changeScheduleStartDate');
    changeScheduleFunction.postChangeScheduleStartDate(req, res);
});
app.post('/changeScheduleNbHours', function (req, res) {
	var changeScheduleFunction= require('./changeScheduleFunction');
    console.log('/changeScheduleNbHours');
    changeScheduleFunction.postChangeScheduleNbHours(req, res);
});


var server = app.listen(3000, function () {

        var host = server.address().address
        var port = server.address().port

        console.log('Example app listening at http://%s:%s', host, port)

        })




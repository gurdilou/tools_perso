// loginFunction.js
// ========


// === login
exports.postLogin = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	fLogin(req, res, responseJSON, nextLogin);
};



//Function login, returns a db connection and an user if ok
var fLogin = function (req, res, responseJSON, next) {
	console.log("Sign in...")
    var project = req.body.project;
    var login   = req.body.login;
    var pwd     = req.body.pwd;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Hashing pwd
		var crypto = require('crypto');
		var shasum = crypto.createHash('sha1');
		shasum.update(pwd);
		var hashedPwd = shasum.digest('hex');

		var sql = 'SELECT COUNT(*) as nbUsers FROM Users WHERE login=? AND password=?';
		var nb = 0;
		db.all(sql, [login, hashedPwd], function(err, rows) {
			if( !err ){
				nb = rows[0].nbUsers;
				if ( nb != 1 ) {
					responseJSON.succeed = false;
					responseJSON.msg = "Wrong user/password.";
					responseJSON.errCode = "ERR_WRONG_LOGIN_PWD";
					sendResponse(req, res, responseJSON);
				}else{
					console.log("User found.");
					fLoadUser(req, res, responseJSON, db, login, hashedPwd, next);
				}
			}else{
				responseJSON.succeed=false;
				responseJSON.msg = err;
				sendResponse(req, res, responseJSON);
				throw err;
			}
		});
		db.close();
	});
};
exports.fLogin = fLogin;

//function loadUser, load an user from db
var fLoadUser = function (req, res, responseJSON, db, login, hashedPwd, next) {
    console.log("Loading user...");

	var sql = 'SELECT rightAdmin, rightEditUsers FROM Users WHERE login=? AND password=?';
	db.all(sql, [login, hashedPwd], function(err, rows) {
		if( !err ) {
			var user ={login:login, rightAdmin : (rows[0].rightAdmin == 1), rightEditUsers : (rows[0].rightEditUsers == 1)};
			responseJSON.user=user;
			next(req, res, responseJSON);
		}else{
			responseJSON.succeed=false;
			responseJSON.msg=err;
			sendResponse(req, res, responseJSON);
			throw err;
		}
	});
};
exports.fLoadUser = fLoadUser;

//Callback after login
var nextLogin = function(req, res, responseJSON){
	var loadProject = require("./createProjectFunction");
    console.log("nextLogin");
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		loadProject.fLoadProject(req, res, responseJSON, db, nextLogin2);
	});
};
//Callback after login
var nextLogin2 = function(req, res, responseJSON){
	var loadMembers = require("./loadMembersFunction");
    console.log("nextLogin2");
    loadMembers.fLoadMembers(req, res, responseJSON, sendResponse);
};

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};




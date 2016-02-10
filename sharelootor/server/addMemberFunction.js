// addMemberFunction.js
// ========


// === postAddMember
exports.postAddMember = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("Add member login...");
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers ){
			fAddMember(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to add a member, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_ADD_MEMBER";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fAddMember = function (req, res, responseJSON, next) {
    console.log("Add member...");
	
	//params
	var name = req.body.name;
	var startDay = req.body.startDay;
	var email = req.body.email;
	var rightAdmin = req.body.rightAdmin;
	var rightEditUsers = req.body.rightEditUsers;
	
	var pwdGenerator = require('./passwordsFunction');
	var tmpPwd = pwdGenerator.fGeneratePwd();	
	var crypto = require('crypto');
	var shasum = crypto.createHash('sha1');
	shasum.update(tmpPwd);
	var hashedPwd = shasum.digest('hex');


	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		var sql = 'SELECT COUNT(login) AS nb FROM Users WHERE login=?';
		db.all(sql, [name], function(err, rows) {
			if(rows[0].nb == 0){
				db.serialize(function() {
					//Check if user has right to change this value
					var rightAdminValue = 0;
					if( (rightAdmin == 'true') && responseJSON.user.rightAdmin){
						rightAdminValue = 1;
					}
					
					//Check if user has right to change this value
					var rightEditUsersValue = 0;
					if( (rightEditUsers == 'true') && responseJSON.user.rightAdmin){
						rightEditUsersValue = 1;
					}
					
					//Insert new member and add first schedule
					db.run("INSERT INTO Users VALUES(?, ?, ?, ?, ?)", [name, hashedPwd, rightAdminValue, rightEditUsersValue, email]);
					db.run("INSERT INTO WorkSchedules(startDay, userLinked, monday, tuesday, wednesday, thursday, friday, saturday, sunday) "
							+"SELECT ?, ?, monday, tuesday, wednesday, thursday, friday, saturday, sunday "
							+"FROM Project LIMIT 1", [startDay, name]);
					nextAddMember(req, res, db, name, tmpPwd, responseJSON, next);
				});
			}else{
				responseJSON.succeed = false;
				responseJSON.msg = "Member '"+name+"' already exists.";
				responseJSON.errCode = "ERR_MEMBER_ALREADY_EXISTS";
				responseJSON.errArgs=[name];
				sendResponse(req, res, responseJSON);
			}
			db.close();
		});
	});
};
exports.fAddMember = fAddMember;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};



//Load member in response and send email
var nextAddMember=function(req, res, db, name, tmpPwd, responseJSON, next){
	var loadMembersFunction = require('./loadMembersFunction');
	console.log("nextAddMember");
	//will fill response
	loadMembersFunction.fLoadAMember(req, res, db, name, responseJSON, function(req, res, responseJSON){
		//variables
		var project = req.body.project;
		var email = req.body.email;
		var userParent = req.body.login;
		
		//send
		var mailFunction = require('./mailNewMember');
		mailFunction.fSendMailNewMember(req, res, email, project, userParent, name, tmpPwd, responseJSON, next);
	});
};






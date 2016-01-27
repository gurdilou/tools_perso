// deleteMemberFunction.js
// ========


// === postDeleteMember
exports.postDeleteMember = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("User login...");
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers ){
			fDeleteMember(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to delete a member, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_DELETE_MEMBER";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fDeleteMember = function (req, res, responseJSON, next) {
    console.log("Delete member...");
	
	//params
	var name = req.body.name;
	var currentUser = req.body.login;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		var loadMemberFunc = require('./loadMembersFunction');
		loadMemberFunc.fLoadMemberEmail(req, res, db, name, responseJSON, function(req, res, responseJSON){
			var email = responseJSON.memberEmail;
			delete responseJSON.memberEmail;
			db.serialize(function() {
				//delete members schedule and row
				db.run("DELETE FROM WorkSchedules WHERE userLinked = ?", [name]);
				db.run("DELETE FROM Users WHERE login=?", [name], function(err){
					if(!err){
						var mailFunction = require('./mailMemberDeleted');
						mailFunction.fSendMailMemberDeleted(req, res, email, name, currentUser, responseJSON, next);
					}else{
						responseJSON.succeed = false;
						responseJSON.msg = err;
						sendResponse(req, res, responseJSON);
						throw err;
					}
				});
			});
			db.close();
		});
	});
};
exports.fDeleteMember = fDeleteMember;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};






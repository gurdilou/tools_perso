// memberEditFunction.js
// ========


// === postMemberEditRightAdmin
exports.postMemberEditRightAdmin = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("Editing member right admin...");
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightAdmin ){
			fMemberEditRightAdmin(req, res, responseJSON, sendResponse);		
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to change member right, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_EDIT_RIGHTS";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

var fMemberEditRightAdmin = function (req, res, responseJSON, next) {
    console.log("fMemberEditRightAdmin");
	
	//params
	var name = req.body.name;
	var rightAdmin = req.body.rightAdmin;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		db.all("SELECT COUNT(*) AS nb FROM Users WHERE rightAdmin=1", function(err, rows) {
			if(!err){
				//Check if user has right to change this value
				var rightAdminValue = 0;
				if( (rightAdmin == 'true') && responseJSON.user.rightAdmin){
					rightAdminValue = 1;
				}
				if( (rightAdminValue == 1) || (rows[0].nb > 1) ){
					//Change db		
					db.run("UPDATE Users SET rightAdmin=? WHERE login=?", [rightAdminValue, name], function(err){
						if(!err){
							responseJSON.rightAdmin = (rightAdminValue == 1);
							next(req, res, responseJSON);							
						}else{
							responseJSON.succeed = false;
							responseJSON.msg = err;
							sendResponse(req, res, responseJSON);
							throw err;
						}
					});	
				}else{
					responseJSON.succeed = false;
					responseJSON.msg = "There must be at least one admin.";
					responseJSON.errCode = "ERR_MUST_HAVE_AN_ADMIN";
					sendResponse(req, res, responseJSON);
				}
			}else{
				responseJSON.succeed = false;
				responseJSON.msg = err;
				sendResponse(req, res, responseJSON);
				throw err;
			}
			db.close();
		});
	});
};
exports.fMemberEditRightAdmin = fMemberEditRightAdmin;

// === postMemberEditRightEditUsers
exports.postMemberEditRightEditUsers = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("Editing member right admin...");
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightAdmin ){
			fMemberEditRightEditUsers(req, res, responseJSON, sendResponse);		
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to change member right, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_EDIT_RIGHTS";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

var fMemberEditRightEditUsers = function (req, res, responseJSON, next) {
    console.log("fResetMemberPassword");
	
	//params
	var name = req.body.name;
	var rightEditUsers = req.body.rightEditUsers;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Change db
		//Check if user has right to change this value
		var rightEditUsersValue = 0;
		if( (rightEditUsers == 'true') && responseJSON.user.rightAdmin){
			rightEditUsersValue = 1;
		}
					
		db.run("UPDATE Users SET rightEditUsers=? WHERE login=?", [rightEditUsersValue, name], function(err){
			if(!err){
				responseJSON.rightEditUsers = (rightEditUsersValue == 1);
				next(req, res, responseJSON);							
			}else{
				responseJSON.succeed = false;
				responseJSON.msg = err;
				sendResponse(req, res, responseJSON);
				throw err;
			}
		});
		db.close();
	});
};
exports.fMemberEditRightEditUsers = fMemberEditRightEditUsers;


//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response edit member");
    res.json(responseJSON);
};


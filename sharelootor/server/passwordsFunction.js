// addMemberFunction.js
// ========


// === postResetMemberPassword
exports.postResetMemberPassword = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("Reseting member password...");
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers ){
			fResetMemberPassword(req, res, responseJSON, sendResponse);		
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to reset a member password, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_RESET_PASSWORD";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

var fResetMemberPassword = function (req, res, responseJSON, next) {
    console.log("fResetMemberPassword");
	
	//params
	var currentUser = req.body.login;
	var name = req.body.name;
	
	var tmpPwd = fGeneratePwd();	
	var crypto = require('crypto');
	var shasum = crypto.createHash('sha1');
	shasum.update(tmpPwd);
	var hashedPwd = shasum.digest('hex');
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		var loadMemberFunc = require('./loadMembersFunction');
		loadMemberFunc.fLoadMemberEmail(req, res, db, name, responseJSON, function(req, res, responseJSON){
			var email = responseJSON.memberEmail;
			delete responseJSON.memberEmail;
			//Change db
			db.run("UPDATE Users SET password=? WHERE login=?", [hashedPwd, name], function(err){
				if(!err){
					var mail = require('./mailResetPassword');
					mail.fSendResetPasswordMail(req, res, email, currentUser, name, tmpPwd, responseJSON, next);								
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
};
exports.fResetMemberPassword = fResetMemberPassword;

//Create tmp password
var fGeneratePwd=function(){
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 8; i++ ){
        text += possible.charAt(Math.floor(Math.random() * possible.length));
	}
    return text;
};
exports.fGeneratePwd = fGeneratePwd;

// === postChangeMemberPassword
exports.postChangeMemberPassword = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	console.log("Changing member password...");
	
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		fChangeMemberPassword(req, res, responseJSON, sendResponse);		
	});
}

var fChangeMemberPassword = function (req, res, responseJSON, next) {
    console.log("fChangeMemberPassword");
	
	//params
	var currentUser = req.body.login;
	
	var newPwd = req.body.newPwd;
	var crypto = require('crypto');
	var shasum = crypto.createHash('sha1');
	shasum.update(newPwd);
	var hashedPwd = shasum.digest('hex');
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		var loadMemberFunc = require('./loadMembersFunction');
		loadMemberFunc.fLoadMemberEmail(req, res, db, currentUser, responseJSON, function(req, res, responseJSON){
			var email = responseJSON.memberEmail;
			delete responseJSON.memberEmail;
			//Change db
			db.run("UPDATE Users SET password=? WHERE login=?", [hashedPwd, currentUser], function(err){
				if(!err){
					var mail = require('./mailChangePassword');
					mail.fSendChangePasswordMail(req, res, email, currentUser, responseJSON, next);								
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
};
exports.fChangeMemberPassword = fChangeMemberPassword;


//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response pwd function");
    res.json(responseJSON);
};



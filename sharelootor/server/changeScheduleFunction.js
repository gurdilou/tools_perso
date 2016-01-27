// changeScheduleFunction.js
// ========


// === postChangeScheduleStartDate
exports.postChangeScheduleStartDate = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	var name = req.body.name;
	
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers || (name == responseJSON.user.login) ){
			fChangeScheduleDateStart(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to do that, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fChangeScheduleDateStart = function (req, res, responseJSON, next) {
    console.log("Change member schedule...");
	
	//params
	var name = req.body.name;
	var oldStartDay = req.body.oldStartDay;
	var newStartDay = req.body.newStartDay;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Delete given schedule
		db.run("UPDATE WorkSchedules SET startDay=? WHERE startDay=? and userLinked=?", [newStartDay, oldStartDay, name],
			function(err){
				if(!err){
					next(req, res, responseJSON);	
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
exports.fChangeScheduleDateStart = fChangeScheduleDateStart;

// === postChangeScheduleNbHours
exports.postChangeScheduleNbHours = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	var name = req.body.name;
	
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers || (name == responseJSON.user.login) ){
			fChangeScheduleNbHours(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to do that, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fChangeScheduleNbHours = function (req, res, responseJSON, next) {
    console.log("Change member schedule...");
	
	//params
	var name = req.body.name;
	var startDay = req.body.startDay;
	var dayEdited = req.body.dayEdited;
	var nbHours = req.body.nbHours;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Delete given schedule
		db.run("UPDATE WorkSchedules SET "+dayEdited+"=? WHERE startDay=? and userLinked=?", [nbHours, startDay, name],
			function(err){
				if(!err){
					next(req, res, responseJSON);	
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
exports.fChangeScheduleNbHours = fChangeScheduleNbHours;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};

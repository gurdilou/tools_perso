// deleteScheduleFunction.js
// ========


// === postDeleteScheduleFunction
exports.postDeleteScheduleFunction = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	var name = req.body.name;
	
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers || (name == responseJSON.user.login) ){
			fDeleteMemberSchedule(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to delete a schedule to this member, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_DELETE_MEMBER_SCHEDULE";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fDeleteMemberSchedule = function (req, res, responseJSON, next) {
    console.log("Delete member schedule...");
	
	//params
	var name = req.body.name;
	var startDay = req.body.startDay;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Delete given schedule
		db.run("DELETE FROM WorkSchedules WHERE startDay=? and userLinked=?", [startDay, name],
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
exports.fDeleteMemberSchedule = fDeleteMemberSchedule;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};

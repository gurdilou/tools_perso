// addScheduleFunction.js
// ========


// === postAddMemberSchedule
exports.postAddMemberSchedule = function (req, res) { 
	var responseJSON={succeed:true, msg:""};
	
	var name = req.body.name;
	
	var loginFunction = require('./loginFunction');
	loginFunction.fLogin(req, res, responseJSON, function(req, res, responseJSON){
		if( responseJSON.user.rightEditUsers || (name == responseJSON.user.login) ){
			fAddMemberSchedule(req, res, responseJSON, sendResponse);
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = "You haven't the right to add a schedule to this member, lil' pirate";
			responseJSON.errCode = "ERR_NO_RIGHT_ADD_MEMBER_SCHEDULE";
			responseJSON.errArgs=[];
			sendResponse(req, res, responseJSON);
		}
	});
}

//function loadUser, load an user from db
var fAddMemberSchedule = function (req, res, responseJSON, next) {
    console.log("Add member schedule...");
	
	//params
	var name = req.body.name;
	var startDay = req.body.startDay;
	
	//Connect to db
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		//Insert new member and add first schedule
		db.run("INSERT INTO WorkSchedules(startDay, userLinked, monday, tuesday, wednesday, thursday, friday, saturday, sunday) "
				+"SELECT ?, ?, monday, tuesday, wednesday, thursday, friday, saturday, sunday "
				+"FROM Project LIMIT 1", [startDay, name],
			function(err){
				if(!err){
					var loadMemberFunc = require('./loadMembersFunction');
					loadMemberFunc.fLoadMemberSchedule(req, res, db, name, startDay, responseJSON, next);	
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
exports.fAddMemberSchedule = fAddMemberSchedule;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response");
    res.json(responseJSON);
};

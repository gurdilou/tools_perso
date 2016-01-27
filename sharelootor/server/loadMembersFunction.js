// loadMembersFunction.js
// ========

var fLoadMembers = function(req, res, responseJSON, next){
	console.log('fLoadMembers');
	var dbUtils = require("./dbUtils");
	dbUtils.fConnectdB(req, res, responseJSON, function(db){
		var sql =  	 'SELECT startDay, login, monday, tuesday, wednesday, thursday, friday, saturday, sunday, rightAdmin, rightEditUsers '
					+'FROM Users u '					
					+'LEFT JOIN WorkSchedules s '
					+'ON u.login = s.userLinked '
					+'ORDER BY login, startDay';
		var currentUser = '';
		var listMembers = new Array();
		responseJSON.listMembers = listMembers;
		db.each(sql, function(err, row) {
			if(!err){
				if(currentUser != row.login){
					console.log("add member in list");
					var member={};
					var userLinked={login:row.login, rightAdmin : (row.rightAdmin == 1), rightEditUsers : (row.rightEditUsers == 1)};
					member.userLinked=userLinked;
					listMembers[listMembers.length] = member;
					var workSchedules = new Array();
					member.workSchedules = workSchedules;
					currentUser = member.userLinked.login;
				}else{
					var member=listMembers[listMembers.length - 1];
					var workSchedules = member.workSchedules;
				}
				//If join ok
				if(row.startDay){
					var newSchedule = {
						startDay : row.startDay,
						monday : row.monday,
						tuesday : row.tuesday,
						wednesday : row.wednesday, 
						thursday : row.thursday,
						friday : row.friday, 
						saturday : row.saturday,
						sunday : row.sunday
					};
					workSchedules[workSchedules.length] = newSchedule;
				}
				
			}else{
				responseJSON.succeed = false; 
				responseJSON.msg = err;
				sendResponse(req, res, responseJSON);
				throw err;
			}
		}, function(err, nbRows){
			if(!err){
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
exports.fLoadMembers = fLoadMembers;

var fLoadAMember = function(req, res, db, name, responseJSON, next){
	console.log('fLoadAMember');
	
	var sql =  	 'SELECT startDay, userLinked, monday, tuesday, wednesday, thursday, friday, saturday, sunday, rightAdmin, rightEditUsers '
				+'FROM WorkSchedules s '
				+'LEFT JOIN Users u '
				+'ON s.userLinked = u.login '
				+'WHERE userLinked=? '
				+'ORDER BY userLinked, startDay';
	var currentUser = '';
	console.log('inner');
	db.each(sql, [name], function(err, row) {
		if(!err){
			if( currentUser == '' ){
				var member={};
				var userLinked={login:row.userLinked, rightAdmin : (row.rightAdmin == 1), rightEditUsers : (row.rightEditUsers == 1)};
				member.userLinked=userLinked;
				var workSchedules = new Array();
				member.workSchedules = workSchedules;
				currentUser = member.userLinked.login;
				responseJSON.member = member;
			}
			var newSchedule = {
				startDay : row.startDay,
				monday : row.monday,
				tuesday : row.tuesday,
				wednesday : row.wednesday, 
				thursday : row.thursday,
				friday : row.friday, 
				saturday : row.saturday,
				sunday : row.sunday
			};
			workSchedules[workSchedules.length] = newSchedule;
		}else{
			responseJSON.succeed = false; 
			responseJSON.msg = err;
			sendResponse(req, res, responseJSON);
			throw err;
		}
	}, function(err, nbRows){
		if(!err){
			next(req, res, responseJSON);
		}else{
			responseJSON.succeed = false; 
			responseJSON.msg = err;
			sendResponse(req, res, responseJSON);
			throw err;
		}
	});
};
exports.fLoadAMember = fLoadAMember;

var fLoadMemberEmail = function(req, res, db, name, responseJSON, next){
	console.log('fLoadMemberEmail');
	//Check if email exists
	var sql = 'SELECT COUNT(login) AS nb FROM Users WHERE login=?';
	db.all(sql, [name], function(err, rows) {
		if(rows[0].nb == 1){
			//get member concerned email
			sql = 'SELECT email FROM Users WHERE login=?';
			db.all(sql, [name], function(err, rows) {
				if(!err){
					var email = rows[0].email;
					responseJSON.memberEmail = email;
					
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
			responseJSON.msg = "Member '"+name+"' doesn't exist.";
			responseJSON.errCode = "ERR_MEMBER_NOT_EXISTS";
			responseJSON.errArgs=[name];
			sendResponse(req, res, responseJSON);
		}
	});
}
exports.fLoadMemberEmail = fLoadMemberEmail;

var fLoadMemberSchedule = function(req, res, db, name, startDay, responseJSON, next){
	console.log('fLoadMemberSchedule : '+name);
	//Check if user exists
	var sql = 'SELECT COUNT(login) AS nb FROM Users WHERE login=?';
	db.all(sql, [name], function(err, rows) {
		if(!err){
			if(rows[0].nb == 1){
				//get member required schedule
				console.log('get member required schedule');
				sql = 'SELECT startDay, monday, tuesday, wednesday, thursday, friday, saturday, sunday FROM WorkSchedules WHERE startDay=? and userLinked=?';
				db.all(sql, [startDay, name], function(err, rows) {
					if(!err){
						console.log('newWorkSchedule');
						var workSchedule = {
							startDay : rows[0].startDay,
							monday : rows[0].monday,
							tuesday : rows[0].tuesday,
							wednesday : rows[0].wednesday, 
							thursday : rows[0].thursday,
							friday : rows[0].friday, 
							saturday : rows[0].saturday,
							sunday : rows[0].sunday
						};
						responseJSON.workSchedule = workSchedule;
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
				responseJSON.msg = "Member '"+name+"' doesn't exist.";
				responseJSON.errCode = "ERR_MEMBER_NOT_EXISTS";
				responseJSON.errArgs=[name];
				sendResponse(req, res, responseJSON);
			}
		}else{
			responseJSON.succeed = false;
			responseJSON.msg = err;
			sendResponse(req, res, responseJSON);
			throw err;
		}
	});
}
exports.fLoadMemberSchedule = fLoadMemberSchedule;

//Callback response end
var sendResponse = function(req, res, responseJSON){
    console.log("Sending response loadMembers project");
    res.json(responseJSON);
};
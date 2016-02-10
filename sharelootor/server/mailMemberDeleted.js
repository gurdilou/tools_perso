// mailMemberDeleted.js
// ========


// === fSendMailNewMember
var fSendMailMemberDeleted = function(req, res, email, name, currentUser, responseJSON, next) {
	console.log("fSendMailMemberDeleted");

	var utils = require('./mailUtils');
	var transporter = utils.fCreateTransporter();
	
	var project = req.body.project;

	var mailOptions = {
		from: 'Share lootor <puddi@luce.eu.com>', 
		to: email, 
		subject: 'Removed', 
		text:
			"Hello "+name+",\n"
			+"'"+currentUser+"' removed you from project '"+project+"'.\n"
			+"If you have any complains, please contact an admin.",
		html: 
		  "<h2>Hello "+name+",</h2>"
		  +"<div>"
			+"<div>"
			  +"'"+currentUser+"' removed you from project '"+project+"'."
			+"</div>"
			+"<div>"
			  +"If you have any complains, please contact an admin."
			+"</div>"
		  +"</div>"
	};

	// send mail with defined transport object
	transporter.sendMail(mailOptions, function(error, info){
		if(error){
			console.log(error);
		}else{
			console.log('Message sent: ' + info.response);
		}
	});
	next(req, res, responseJSON);
};
exports.fSendMailMemberDeleted = fSendMailMemberDeleted;


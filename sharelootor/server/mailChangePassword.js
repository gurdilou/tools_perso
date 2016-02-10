// mailChangePassword.js
// ========


// === fSendChangePasswordMail
var fSendChangePasswordMail = function (req, res, email, currentUser, responseJSON, next) {
	console.log("fSendChangePasswordMail");

	var utils = require('./mailUtils');
	var transporter = utils.fCreateTransporter();

	var project = req.body.project;

	// setup e-mail data with unicode symbols
	var mailOptions = {
		from: 'Share lootor <puddi@luce.eu.com>',
		to: email,
		subject: 'Password changed.', 
		text:
			"Hello "+currentUser+", \n"
			+"Your password for project '"+project+"' has been changed. If you did not initiate this request, please contact an admin of the project.",
		html: 
		  "<h2>Hello "+currentUser+",</h2>"
		  +"<div>"
			+"<div>"
			  +"Your password for project '"+project+"' has been changed. If you did not initiate this request, please contact an admin of the project."
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
exports.fSendChangePasswordMail = fSendChangePasswordMail;



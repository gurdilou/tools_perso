// mailResetPassword.js
// ========


// === fSendResetPasswordMail
var fSendResetPasswordMail = function (req, res, email, currentUser, name, tmpPwd, responseJSON, next) {
	console.log("fSendResetPasswordMail");

	var utils = require('./mailUtils');
	var transporter = utils.fCreateTransporter();

	var project = req.body.project;

	// setup e-mail data with unicode symbols
	var mailOptions = {
		from: 'Share lootor <puddi@luce.eu.com>',
		to: email,
		subject: 'Password reset', 
		text:
			"Hello "+name+", \n"
			+"'"+currentUser+"' has reset your password for project '"+project+"'. \n\n"
			+"New password below :\n"
			+"\t - Password :"+tmpPwd+"\n\n"
			+"Please note that this password has been randomly generated, please change it on your next visit at www.sharelootor.luce.eu.com.",
		html: 
		  "<h2>Hello "+name+",</h2>"
		  +"<div>"
			+"<div>"
			  +"'"+currentUser+"' has reset your password for project '"+project+"'.<br/>"
			+"</div>"
			+"<div>"
			  +"New password below :"
			+"</div>"
			+"<ul>"
			  +"<li>Password :"+tmpPwd+"</li>"
			+"</ul>"
			+"<div>"
			  +"Please note that this password has been randomly generated, please change it on your next visit at <a href='www.sharelootor.luce.eu.com'>www.sharelootor.luce.eu.com</a>."
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
exports.fSendResetPasswordMail = fSendResetPasswordMail;



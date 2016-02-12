var fs = require('fs');
var path = require('path');
var process = require("process");
var nodemailer = require('nodemailer');
var ini = require('ini');
var request = require('sync-request');

// =================================================================== VARIABLES
var config = ini.parse(fs.readFileSync('./config.ini', 'utf-8'));

var blog_posts_location = config.variables.blog_posts_location;
var max_days_off = config.variables.max_days_off;
var mail_blogger = config.variables.mail_blogger;

//mail
var config_mail = {
    service: config.config_mail.service,
    auth: {
      user: config.config_mail.user,
      pass: config.config_mail.pwd,
      secure: config.config_mail.secure
    }
  };


// ======================================================================== MAIN
var date_last_post;


// Loop through all the files in the temp directory
fs.readdir(blog_posts_location, function(err, files) {
  if (err) {
    console.error("Could not list the directory.", err);
    process.exit(1);
  }

  for(var i = 0; i < files.length; i++){
    var fromPath = path.join(blog_posts_location, files[i]);
    var stat = fs.statSync(fromPath, function(error) {
      if (error) {
        console.error("Error stating file.", error);
        return;
      }
    });
    if (stat.isFile()) {
      processFile(fromPath);
    }
  }


  var nbDays = getDaysOff(date_last_post);

  if(nbDays > max_days_off){
    sendThreatMail(nbDays);
  }
});


// =================================================================== FUNCTIONS
// processFile : Lit un fichier et extrait
function processFile(filepath) {
  // console.log("Processing '%s'", filepath);

  var data = fs.readFileSync(filepath, 'utf8', function(err) {
    if (err) {
      return console.error("Could not read the file.", err);
    }
  });

  var currentLine = '';
  var dateFound = false;

  for(var i = 0; i < data.length; i++){
    if(!dateFound){
      if(data[i] === '\n'){
        date = findDateInLine(currentLine);
        currentLine = '';
        if(date !== undefined){
          // console.log("date found : "+date);
          dateFound = true;
          addPostDate(date);
        }
      }else{
        currentLine = currentLine+data[i];
      }
    }
  }

  return ;
}

// findDateInLine : Regarde si la ligne contient la date du poste
function findDateInLine(line) {
  if (line.substring(0, 6) == "date: ") {
    var dateStr = line.substring(6, line.length);
    return strToDate(dateStr);
  }
  return undefined;
}

//strToDate parse une date
function strToDate(dateStr) {
  var dayTimeSplit = dateStr.split(" ");  
  var day = dayTimeSplit[0];
  var time = dayTimeSplit[1];

  if (day == "Today") {
      day = new Date();
  } else if (day == "Yesterday") {
      day = new Date();
      day.setDate(day.getDate() - 1);
  } else {
      day = new Date(day);
  }

  var hourMinutes = time.substring(0, time.length -2);
  var amPM = time.substring(time.length -2, time.length);

  return new Date((day.getMonth() + 1) + "/" + day.getDate() + "/" + day.getFullYear() 
      + " " + hourMinutes  + " " + amPM);
}

// addPostDate : Met à jour la date de maj
function addPostDate(date) {
  // console.log("addPostDate");
  if( (date_last_post === undefined) || (date_last_post < date) ){
    date_last_post=date;
  }
}

// getDaysOff : Retourne le nombre de jours sans posts
function getDaysOff() {
  var now = new Date();
  var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds

  var diffDays = Math.round(Math.abs((now.getTime() - date_last_post.getTime())/(oneDay)));
  console.log("Aucun poste depuis : "+diffDays+" jour(s).");

  return diffDays;
}
// sendThreatMail : Envoie un mail de menace
function sendThreatMail(nbDays) {
  var transporter = nodemailer.createTransport(config_mail);

  var res = request('GET', 'http://quotes.rest/qod.json');
  var quoteJson = JSON.parse(res.getBody('utf8'));
  var quote = quoteJson.contents.quotes[0].quote;
  var quoteAutor = quoteJson.contents.quotes[0].author;
  // console.log("quote : "+quote);
  // console.log("By: "+quoteAutor);

  var mailOptions = {
    from: 'Motivator <'+config_mail.auth.user+'>', 
    to: mail_blogger, 
    subject: 'Still alive ?!', 
    text:
      "Salut le blogger, \n"
      +"Tu n'as pas été très actif dérnièrement ("+nbDays+" jour(s))... Bouges-toi ! "
      +"Bisous,\n"
      +"Motivator",
    html: 
      "<table style='width : 96%; margin-left : 2%; margin-right : 2%; background : #F3ECED; border-radius : 9px; font-family : Verdana, Geneva, sans-serif;' cellspacing='0' cellpadding='0'> \
         <tbody>\
           <tr>\
              <td style='background-color: #E16565; border-radius : 9px; height : 100px;'>\
                <div style='color : white; font-size : 32px; font-weight : bold; margin-left : 20px'>\
                  Hey ! \
                </div>\
              </td>\
           </tr>\
           <tr>\
             <td><h2 style='color : #2C4D5E; margin-left : 32px;'>Salut le blogger,</h2></td>\
           </tr>\
           <tr>\
              <td style='color : #516D7B;'>\
                <div style='margin-left : 40px; margin-bottom : 32px;'>\
                  Tu n'as pas été très actif dernièrement ("+nbDays+" jour(s))... Bouges-toi ! \
                </div>\
              </td>\
           </tr>   \
           <tr>\
              <td style='color : #516D7B;'>\
                <div style='margin-left : 40px; margin-bottom : 22px;'>\
                  Comme tu manques d'inspiration voici une citation d'un homme qui était motivé : \
                </div>\
              </td>\
           </tr> \
           <tr>\
             <td>\
               <div style='color : #516D7B; align : left; margin-left : 72px; margin-right : 72px; height : 100%; border-radius : 20px 20px 0 0;border : 1px solid #2C4D5E; border-bottom : none; background: #FFFCFC; padding-bottom : 20px;'>\
                  <div style='padding : 12px; font-size : 14px; font-family : \"Lucida Console\", Monaco, monospace'>\
                    "+quote+"\
                  </div>\
               </div>\
             </td>\
           </tr>    \
           <tr>\
              <td>\
                 <div style='color : #516D7B; margin-left : 72px; margin-right : 72px; border : 1px solid #2C4D5E; border-top : none; border-bottom : none; background: #FFFCFC' align='right'>\
                  <span style='margin-right : 20px; font-size : 14px;'>"+quoteAutor+"</span>\
                 </div>\
              </td>\
           </tr>\
           <tr>\
              <td>\
               <div style='color : #516D7B; margin-left : 72px; margin-right : 72px; border-radius : 0 0 20px 20px;border : 1px solid #2C4D5E; border-top : none; background: #FFFCFC; margin-bottom : 42px; padding-bottom : 12px;' align='right'>\
                <span style='z-index:50;font-size:12px; margin-right : 20px; '><img src='https://theysaidso.com/branding/theysaidso.png' height='12' width='12' alt='theysaidso.com'/><a href='https://theysaidso.com' title='Powered by quotes from theysaidso.com' style='color: #516D7B; margin-left: 4px; vertical-align: middle;'>theysaidso.com</a></span>\
               </div>\
              </td>\
           </tr>\
           <tr>\
              <td style='color : #516D7B;'>\
                <div style='margin-left : 40px;'>\
                  Bisous,\
                </div>\
              </td>\
           </tr>   \
           <tr>\
              <td style='color : #2C4D5E;'>\
                <div style='margin-left: 40px; margin-bottom : 32px; font-weight : bold;'>\
                  Motivator\
                </div>\
              </td>\
           </tr>   \
           <tr>\
              <td style='background-color: #E16565; border-radius : 6px; height : 20px;' align='right'>\
                <div style='color : white; font-size : 10px; margin-right : 12px;'>\
                  Je te surveille...\
                </div>\
              </td>\
           </tr>\
         </tbody>\
       </table> ",
  };

  // send mail with defined transport object
  transporter.sendMail(mailOptions, function(error, info){
    if(error){
      console.log(error);
    }else{
      console.log('Message sent: ' + info.response);
    }
  });

}
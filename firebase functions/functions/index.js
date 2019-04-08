const functions = require('firebase-functions');

exports.getTime= functions.https.onRequest((req,res) =>
{
	  res.setHeader('Content-Type', 'application/json');
  res.send( JSON.stringify({ data: Date.now()}));
  
})
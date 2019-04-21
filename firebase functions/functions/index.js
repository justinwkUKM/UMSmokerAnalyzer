const functions = require('firebase-functions');

let admin = require('firebase-admin');

admin.initializeApp();

exports.getTime= functions.https.onRequest((req,res) =>
{
	  res.setHeader('Content-Type', 'application/json');
  res.send( JSON.stringify({ data: Date.now()}));
  
})

exports.sendNotification = functions.database.ref('/users/{pushId}/SmokeDiarys/{diaryId}').onCreate
((snapshot,context)  => {
	console.log("pushID",context.params.pushId)
	console.log("DATA4: ", snapshot.child('SmokeDiary').child('smoked').val());

//query the users node and get the name of the user who sent the message
return admin.database().ref("/users/" + context.params.pushId).once('value').then(snap => {
		
	const token = snap.child("FcmToken").val();
	const payload = {
		"notification" : {
			"body" : "great start!",
			"title" : "Smoke Diary"
		  }
	};
		console.log("token: ", token);
		console.log("payload: ", payload);
	
	return admin.messaging().sendToDevice(token, payload)
				.then(function(response) {
					console.log("Successfully sent message:", response);
				  })
				  .catch(function(error) {
					console.log("Error sending message:", error);
				  });
});
	
})	

	
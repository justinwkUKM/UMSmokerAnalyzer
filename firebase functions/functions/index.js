// firebase deploy --only functions 

const functions = require('firebase-functions');

let admin = require('firebase-admin');
admin.initializeApp();
const database = admin.database();



const rootRef = database.ref("/users/");
exports.getTime= functions.https.onRequest((req,res) =>
{
	  res.setHeader('Content-Type', 'application/json');
  res.send( JSON.stringify({ data: Date.now()}));
  
})

exports.sendNotification = functions.database.ref('/users/{pushId}/SmokeDiarys/{diaryId}').onCreate
((snapshot,context)  => {
	const smokeFlag=snapshot.child('SmokeDiary').child('smoked').val();
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
//0 */1 * * * to be called every 1 hour
exports.scheduledFunction = functions.pubsub.topic('SmokeFree').onPublish(() => {

	let ref=database.ref("/users/");
	
	ref.once('value', (snapshot) => {
		let arrayFcmToken=[];
		let counter=0;
		snapshot.forEach((childSnapshot) => {
				let fcmToken=childSnapshot.child('FcmToken').val();
				let SmokeFreeTime=childSnapshot.child('SmokeFreeTime').child('startDate').child("time").val();
				let currentDate=new Date();
				let smokeFreeCompleted=childSnapshot.child('SmokeFreeTime').child('smokeFreeCompleted').val();
				let hours = Math.abs((currentDate.getTime() - SmokeFreeTime) / 3600000);
		

			if(parseInt(hours)>24 && String(smokeFreeCompleted) === "false")
			{
					ref.database.ref("/users/"+childSnapshot.key+"/SmokeFreeTime/").
					update({"smokeFreeCompleted":true}).then(function() {
						arrayFcmToken[counter]=fcmToken;

						const payload = {
							"notification" : {
								"body" : "YAAAAY",
								"title" : "You have completed 24 hours smoke free"
							  }
						};
						
					    admin.messaging().sendToDevice(fcmToken, payload)
						.then(function(response) {
							console.log("Successfully sent message:", response);
						})
						.catch(function(error) {
							console.log("Error sending message:", error);
						});
			
						
					}).catch(function(error) {
						console.error("Write failed: "+error);
					});
					
			}

		});

		return new Promise((resolve, reject) => {
			//do nothing
			resolve('')
		  });
	
	});
})
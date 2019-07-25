// firebase deploy --only functions 
//firebase deploy --only functions:calculate_EACNO
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

exports.calculate_EACNO=functions.https.onRequest((req,res) =>
{
	let ref=database.ref("/users/"+req.body.data.user_id);

	ref.once('value', (snapshot) => {
			
		
		let questionsArray=[];
				
				let videoQuestions1=snapshot.child('videoQuestions1').val();
				let videoQuestions2=snapshot.child('videoQuestions2').val();
				let videoQuestions3=snapshot.child('videoQuestions3').val();
				let videoQuestions4=snapshot.child('videoQuestions4').val();
				let videoQuestions5=snapshot.child('videoQuestions5').val();

				let questions_flag=false;

				if(videoQuestions1)
					questionsArray.push(videoQuestions1);
				else
					questions_flag=true;
				
				if(videoQuestions2)
					questionsArray.push(videoQuestions2);
				else
					questions_flag=true;
					
				if(videoQuestions3)
					questionsArray.push(videoQuestions3);
				else
					questions_flag=true;
				
				if(videoQuestions4)
					questionsArray.push(videoQuestions4);
				else
					questions_flag=true;
					
				if(videoQuestions5)
					questionsArray.push(videoQuestions5);
				else
						questions_flag=true;
					
	

				let Extroversion=20;
				let Agreeableness=14;
				let Conscientiousness=14;
				let Neuroticism=38;
				let Openness=8;
				
				const Extroversion_indexies=[1,6,11,16,21,26,31,41,46];
				const Agreeableness_indexies=[2,7,12,17,22,27,32,37,42,47];
				const Conscientiousness_indexies=[3,8,13,18,23,28,33,38,43,48];
				const Neuroticism_indexies=[4,9,14,19,24,29,34,44,49];
				const Openness_indexies=[5,10,15,20,25,30,35,40,45,50];
				
if (questions_flag)
{

	res.setHeader('Content-Type', 'application/json');
	res.send(JSON.stringify({
				data:{
					scores:null,
					error_message:"You must answer all questions to get personality results, go to Mindfulness videos or Info page"
				}
				}));
	// res.send( JSON.stringify({"data":{
	// 	"name":"basel"
	// }}));
}
else
{
	questionsArray.forEach((videoQuestion)=>
{
	try {
		const questions = JSON.parse(JSON.stringify(videoQuestion));
		// questions.forEach(function(value){
		// 	console.log(value);
		// });
		
		//console.log(JSON.stringify(videoQuestion));
		questions.forEach((question)=>
		{
				if(Extroversion_indexies.includes(question.index))
				{
					Extroversion=Extroversion+question.selectedOptions.score;
					
				}

				if(Agreeableness_indexies.includes(question.index))
				{
					Agreeableness=Agreeableness+question.selectedOptions.score;
				}

				if(Conscientiousness_indexies.includes(question.index))
				{
					Conscientiousness=Conscientiousness+question.selectedOptions.score;
				}

				if(Neuroticism_indexies.includes(question.index))
				{
					Neuroticism=Neuroticism+question.selectedOptions.score;
				}

				if(Openness_indexies.includes(question.index))
				{
					Openness=Openness+question.selectedOptions.score;
				}
		});

	} catch(err) {
		res.setHeader('Content-Type', 'application/json');
		res.send(JSON.stringify({
			"data":null,
			"error_message":"You must answer all questions to get personality results, go to Mindfulness videos or Info page"}));
		
	}
	res.setHeader('Content-Type', 'application/json');
	res.send( JSON.stringify(
		{
			"data":{
				"scores":[
					{
						"title":"Extroversion",
						"score":Extroversion,
						"description":"is the personality trait of seeking fulfillment from sources outside the self or in community. High scorers tend to be very social while low scorers prefer to work on their projects alone"},
						{
						"title":"Agreeableness",
						"score":Agreeableness,
						"description":"reflects much individuals adjust their behavior to suit others. High scorers are typically polite and like people. Low scorers tend to 'tell it like it is'."},
						{
						"title":"Conscientiousness",
						"score":Conscientiousness,
						"description":"is the personality trait of being honest and hardworking. High scorers tend to follow rules and prefer clean homes. Low scorers may be messy and cheat others"},
						{
						"title":"Neuroticism",
						"score":Neuroticism,
						"description":"is the personality trait of being emotional"},
						{
						"title":"Openness",
						"score":Openness,
						"description":"is the personality trait of seeking new experience and intellectua pursuits. High scores may day dream a lot. Low scorers may be very down to earth."}
				],
				error_message:null
			}
		
		
		}
	));

});
}
});

	
})

exports.sendNotification = functions.database.ref('/users/{pushId}/SmokeDiarys/{diaryId}').onCreate
((snapshot,context)  => {
	const smokeFlag=snapshot.child('SmokeDiary').child('smoked').val();
//query the users node and get the name of the user who sent the message

if(String(smokeFlag) ==="I Smoked")
{
	rootRef.ref("/users/" + context.params.pushId+"/MotivationMessages").remove()
	.then(function() {
		console.log("Remove succeeded.")
	})
	.catch(function(error) {
		console.log("Remove failed: " + error.message)
	});
}
else
{
	return rootRef.ref("/users/" + context.params.pushId).once('value').then(snap => {
		

		const token = snap.child("FcmToken").val();
		const payload = {
			"notification" : {
				"body" : "great start!",
				"title" : "Smoke Diary"
			  }
		};
		
		return admin.messaging().sendToDevice(token, payload)
					.then(function(response) {
						console.log("Successfully sent message:", response);
					  })
					  .catch(function(error) {
						console.log("Error sending message:", error);
					  });
	});
		
	
}
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
					counter=counter+1;
			}

		});

		return new Promise((resolve, reject) => {
			//do nothing
			resolve('')
		  });
	
	});
})



exports.getLeaderBoard= functions.https.onRequest((req,res) =>
{
// 	  res.setHeader('Content-Type', 'application/json');
//   res.send( JSON.stringify({ data: Date.now()}));


var result = [];
rootRef.once('value', (snapshot) => {
	let arrayTopSnapshot=[];
	let arrayTopSmokeFree=[];
	let counter=0;
	snapshot.forEach((childSnapshot) => {
			// let fcmToken=childSnapshot.child('FcmToken').val();
			let SmokeFreeTime=childSnapshot.child('SmokeFreeTime').child('startDate').child("time").val();

			if(SmokeFreeTime!= null && typeof SmokeFreeTime != "object" && SmokeFreeTime!=0)
			{
				let currentDate=new Date();
				let hours = Math.abs((currentDate.getTime() - SmokeFreeTime) / 3600000);
				console.log('name'+childSnapshot.child('username').val());
				console.log('time'+SmokeFreeTime);
				arrayTopSmokeFree[counter]=hours;
				arrayTopSnapshot[hours]=childSnapshot;	
				counter=counter+1;
	
			}
	});

	let arrayTop10=arrayTopSmokeFree.sort((a, b) => b - a).slice(0,10);

	let index=0;
	arrayTop10.forEach(function(entry) {
	
		let snapshot=arrayTopSnapshot[entry];
		result.push({name: snapshot.child('username').val(), SmokeFreeTime: parseInt(entry, 10)});
		index=index+1;
	});
	var responsearray = {
		data: {
		  LeaderBoard: result}
	  };
	res.setHeader('Content-Type', 'application/json');

	res.send( JSON.stringify(responsearray));

});



})


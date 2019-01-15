var playerName = null;
var gameId = null;

var notice = $("#notice ul");

$(document).ready(function() {
	connectSockJS();
	playerName = $("#playerName span").html();
	gameId = $("#gameId span").html();
});

function connectSockJS() {
	console.log("start Sock.js");

	// handShake
	var sock = new SockJS("/ws/game");
    sock.onopen = function () {
        console.log('Info: connection opened.');
        console.log('gameId : ', gameId);
        console.log('playerName : ', playerName);

        // message Send
        sock.send(JSON.stringify({'gameId': gameId, 'playerName': playerName, type: 'NOTICE'}));

        // message Receive
        sock.onmessage = function (event) {
            var contents = JSON.parse(event.data);
            console.log("ReceivedMessage:", contents);
            console.log("Type:", contents.type);

            receiveMessage(contents);
        };

        // connection Closed
//        sock.onclose = function (event) {
//            console.log('Info: connection closed.');
//        };
    };
}

function receiveMessage(contents) {
    var type = contents.type;
    if (type === 'NOTICE') {
        notice.append('<li>' + contents.message + '</li>')
    }

    if (type === 'TURN_INFO') {
        console.log(contents);
    }



}
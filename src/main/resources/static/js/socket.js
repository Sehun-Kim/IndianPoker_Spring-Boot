// 전역변수
var playerName = null; // 해당 페이지에서 게임하는 player의 이름
var gameId = null; // 해당 페이지의 게임 id
var sock = null; // socket

// 전체 공지 영역
var notice = $("#notice ul");
var turnNum = $('#turnNum span');

// betting Table
var bettingTable = $('#bettingTable');
// betting buttons
var bettingBtns = $('#bettingBtns');


// game.html page가 로드되면 실행
$(function() {
	connectSockJS();
	playerName = $("#playerName").html();
	gameId = $("#gameId span").html();
});

function connectSockJS() {
	console.log("start Sock.js");

	// handShake
	sock = new SockJS("/ws/game");
    sock.onopen = function () {
        console.log('Info: connection opened.');
        console.log('gameId : ', gameId);
        console.log('playerName : ', playerName);

        // message Send
        sock.send(JSON.stringify({'gameId': gameId, 'playerName': playerName, type: 'NOTICE'}));

        // message Receive
        sock.onmessage = function (event) {
            var contents = JSON.parse(event.data);
            console.log("Type:", contents.type);
            console.log("ReceivedMessage:", contents);

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
        printNotice(contents);
    }

    if (type === 'TURN_INFO') {
        printTurnInfo(contents);
    }

    if (type === 'BETTER_INFO') {
        printBettingInfo(contents);
    }

}

function printNotice(contents) {
    notice.append('<li>' + contents.message + '</li>');
}

function printTurnInfo(contents) {
    // turnNum 수정
    turnNum.html('[' + contents.turnCount + ']');
    notice.append('<li>===<strong>Turn' + contents.turnCount +'</strong>이 시작되었습니다.===</li>');

    // 현재 플레이어들의 보유 칩을 알려줌
    printPlayerInfo(contents.ownPlayerInfoDto);
    printPlayerInfo(contents.otherPlayerInfoDto);

    // 누가 베팅할 차례인지 알려줌
    printPlayerOrder(contents.ownPlayerInfoDto.name);

    function printPlayerInfo(player) {
        var name = player.name;
        var chipsNum = player.remainChips.numberOfChips;
        notice.append('<li> <strong>' + name + '</strong>의 현재 보유칩 : [' + chipsNum +']</li>')
        if (playerName === player.name) {
            $('#numberOfChips strong').html(chipsNum);
        }
    }

    function printPlayerOrder(name) {
        notice.append('<li> --> <strong>' + name + '</strong>의 배팅 차례입니다.-</li>');

        if (playerName === name) {
            bettingBtns.show();
        } else {
            bettingBtns.hide();
        }
    }
}

function printBettingInfo(contents) {
    // 현재 betting 된 칩들을 보여줌
    printBettingTable(contents.currentTableDto);

    // 상대의 카드를 보여줌
    printOtherPlayerCard(contents.otherPlayerCard);

    // 턴의 첫 배팅이라면 call을 하지 못하도록 btn을 없앰
    isTurnFirstBetting(contents.firstBetting);


    function printBettingTable(currentTable) {
        var ownChips = currentTable.ownChips.numberOfChips;
        var otherChips = currentTable.otherChips.numberOfChips;
        $('#ownChips span').html(ownChips);
        $('#otherChips span').html(otherChips);
    }

    function printOtherPlayerCard(otherCard) {
        var cardNum = otherCard.card;
        $('#cardNum').html("[" + cardNum + "]");
    }

    function isTurnFirstBetting(firstBetting) {
        var callBtn = $("#callBtn");
        if (firstBetting) {
            callBtn.hide();
        } else {
            callBtn.show();
        }
    }
}
// --- Global Variable --
var playerName = null; // 해당 페이지에서 게임하는 player의 이름
var gameId = null; // 해당 페이지의 게임 id
var sock = null; // socket

var notice = $("#notice ul"); // 전체 공지 영역
var turnNum = $('#turnNum span'); // Turn 번호

var bettingTable = $('#bettingTable'); // betting Table
var bettingBtns = $('#bettingBtns'); // betting buttons

// Buttons
var callBtn = $("#callBtn"); // CALL
var raiseBtn = $("#raiseBtn"); // RAISE
var dieBtn = $("#dieBtn"); // DIE

// raise 배팅용 칩 바운더리
var diffChipsNum = 0;
var betterChipsNum = 0;
var otherChipsNum = 0;


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
        $("#callBtn").on('click', callBetting);
        $("#raiseBtn").on('click', raiseBetting);
        $("#dieBtn").on('click', dieBetting);

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

    if (type === 'TURN_START') {
        printTurnStart(contents);
    }

    if (type === 'TURN_INFO') {
        printTurnInfo(contents);
    }

    if (type === 'BETTER_INFO') {
        printBetterInfo(contents);
    }

    if (type === 'ERROR') {
        printError(contents);
    }

    if (type === 'BETTING_RESULT') {
        printBettingResult(contents);
    }

    if (type === 'TURN_RESULT') {
        printTurnResult(contents);
    }
}

function printNotice(contents) {
    notice.append('<li>' + contents.message + '</li>');
    gameStart(contents.numberOfPeople, contents.playerInfoDto.name);
}

function printTurnStart(contents) {
    // turnNum 수정
    turnNum.html('[' + contents.turnCount + ']');
    notice.append('<li>===== <strong>Turn' + contents.turnCount +'</strong> =====</li>');
}

function printTurnInfo(contents) {
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
        // detach/attach까지 할 필욘 없다고 생각해서..
        if (playerName === name) {
            bettingBtns.show();
        } else {
            bettingBtns.hide();
        }
    }
}

function printBetterInfo(contents) {
    // 현재 betting 된 칩들을 보여줌
    printBettingTable(contents.currentTableDto);

    // 상대의 카드를 보여줌
    printOtherPlayerCard(contents.otherPlayerCard);

    // 턴의 첫 배팅이라면 call을 하지 못하도록 btn을 없앰
    isTurnFirstBetting(contents.firstBetting);

    //Raise할 경우 배팅할 수 있는 chip 바운더리 설정
    setChipsBoundary(contents.bettingChipBoundaryDto);


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
        if (firstBetting) {
            callBtn.hide();
        } else {
            callBtn.show();
        }
    }

    function setChipsBoundary(chipBoundary) {
        diffChipsNum = parseInt(chipBoundary.diffChips.numberOfChips);
        otherChipsNum = parseInt(chipBoundary.otherChips.numberOfChips);
        betterChipsNum = parseInt(chipBoundary.betterChips.numberOfChips);

        console.log(diffChipsNum + ", " + betterChipsNum + ", " + otherChipsNum);
    }
}

function printError(contents) {
    // error message 출력
    alert(contents.message);
}

function printBettingResult(contents) {
    notice.append('<li> <strong>' + contents.playerName + '</strong>이(가) [' + contents.bettingCase +'] 하였습니다.</li>')
}

function printTurnResult(contents) {
    // 두 플레이어 모두 notice 영역에 승자가 누구인지, 얼마의 칩을 얻었는지 표시한다.


    function printWinner() {

    }
}

// --- Send METHOD ---

// GAME START
function gameStart(numberOfPeople, name) {
    if (numberOfPeople === 2) {
        notice.append('<li> ++++++++ <strong> [INDIAN-POKER] GAME START </strong> ++++++++ </li>'); // 게임시작을 알려줌

        if (name === playerName) { // 두 번째 들어온 player가 게임시작 요청을 한다.
            var gameStartMessage = {'gameId': gameId, 'type': 'TURN_START', 'value': 0, 'player': playerName};
            sock.send(JSON.stringify(gameStartMessage));
        }
    }
}


// BETTING
function callBetting() {
    if (sock.readyState !== 1) return;
    alert("call");
    var betting = {'gameId': gameId, 'type': callBtn.html(), 'value': 0, 'playerName': playerName};
    sock.send(JSON.stringify(betting));
}

function raiseBetting() {
    if (sock.readyState !== 1) return;

    alert("raise");

    var message = '칩 수를 입력하세요';
    var value = inputChipsNum(message);

    var betting = {'gameId': gameId, 'type': raiseBtn.html(), 'value': value, 'playerName': playerName};
    sock.send(JSON.stringify(betting));


    function inputChipsNum(inputMessage) {
        var inputNum = parseInt(prompt("INPUT RAISE CHIPS SIZE : ", inputMessage));

        if (!inputNum) {
            inputMessage = '값을 제대로 입력하세요.';
            return inputChipsNum(inputMessage);
        }

        if (inputNum <= diffChipsNum) {
            inputMessage = '최소 ' + diffChipsNum + '초과로 배팅해야 합니다.';
            return inputChipsNum(inputMessage);
        }

        if (inputNum > (diffChipsNum + otherChipsNum)) {
            inputMessage = '상대의 칩 이상 배팅할 수 없습니다. 최대 배팅 가능한 칩 수 :' + (diffChipsNum + otherChipsNum);
            return inputChipsNum(inputMessage);
        }

        if (inputNum > betterChipsNum) {
            inputMessage = '칩이 부족합니다. 현재 보유 칩 :' + betterChipsNum;
            return inputChipsNum(inputMessage);
        }

        return inputNum;
    }
}

function dieBetting() {
    if (sock.readyState !== 1) return;

    alert("die");
    var betting = {'gameId': gameId, 'type': dieBtn.html(), 'value': 0, 'playerName': playerName};
    sock.send(JSON.stringify(betting));
}

// --- Global Variable --
var playerName = null; // 해당 페이지에서 게임하는 player의 이름
var gameId = null; // 해당 페이지의 게임 id
var sock = null; // socket

var notice = $("#notice ul"); // 전체 공지 영역
var turnNum = $('#turnNum span'); // Turn 번호

var cardNum = $('#cardNum');

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
        sock.onclose = function (event) {
            console.log('Info: connection closed.');
        };
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

    if (type === 'GAME_RESULT') {
        printGameResult(contents);
    }
}

// notice 영역
function addNotice(message) {
    notice.append('<li>' + message + '</li>');
    notice.animate({
        scrollTop: 100000
    }, 10);
}

function printNotice(contents) {
    addNotice(contents.message);
    gameStart(contents.numberOfPeople, contents.playerInfoDto.name);
}

function printTurnStart(contents) {
    // turnNum 수정
    turnNum.html(contents.turnCount);
    var message = '===== <strong>Turn' + contents.turnCount +'</strong> START =====';
    addNotice(message);
}

function hideAndShowTable(ownPlayerName) {
    if (playerName === ownPlayerName) {
        cardNum.show();
        bettingTable.show();
    } else {
        cardNum.hide();
        bettingTable.hide();
    }
}

function printTurnInfo(contents) {
    // 현재 플레이어들의 보유 칩을 알려줌
    printPlayerInfo(contents.ownPlayerInfoDto);
    printPlayerInfo(contents.otherPlayerInfoDto);

    var ownPlayerName = contents.ownPlayerInfoDto.name;

    // 누가 배팅할 차례인지 알려줌
    printPlayerOrder(ownPlayerName);
    hideAndShowTable(ownPlayerName);

    function printPlayerInfo(player) {
        var name = player.name;
        var chipsNum = player.remainChips.numberOfChips;
        var message = '<strong>' + name + '</strong>의 현재 보유칩 : [' + chipsNum +']';
        addNotice(message);
        if (playerName === player.name) {
            $('#numberOfChips strong').html(chipsNum);
        }
    }

    function printPlayerOrder(name) {
        var message = '--> <strong>' + name + '</strong>의 배팅 차례입니다.';
        addNotice(message);

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

    // 상대가 올인한 경우 Raise할 수 없게함.
    isAllIn(contents.allIn);

    //Raise할 경우 배팅할 수 있는 chip 바운더리 설정
    setChipsBoundary(contents.bettingChipBoundaryDto);


    function printBettingTable(currentTable) {
        var ownChips = currentTable.ownChips.numberOfChips;
        var otherChips = currentTable.otherChips.numberOfChips;

        $('#ownChips span').html(ownChips);
        $('#otherChips span').html(otherChips);
    }

    function printOtherPlayerCard(otherCard) {
        var card = otherCard.card;
        cardNum.html("[" + card + "]");
    }

    function isTurnFirstBetting(firstBetting) {
        if (firstBetting) {
            callBtn.hide();
        } else {
            callBtn.show();
        }
    }

    function isAllIn(allIn) {
        if (allIn) {
            raiseBtn.hide();
        } else {
            raiseBtn.show();
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
    alert(contents.playerName + contents.message);
    if (contents.point === 'PLAYER_OUT') {
        pageOut(playerName);
    }
}

function printBettingResult(contents) {
    var message = '<strong>' + contents.playerName + '</strong>이(가) [' + contents.bettingCase +'] 하였습니다.';
    addNotice(message);
}

function printTurnResult(contents) {
    // 두 플레이어 모두 notice 영역에 승자가 누구인지, 얼마의 칩을 얻었는지 표시한다.
    var message = '===== <strong>Turn' + contents.turnCount +'</strong> END =====';
    addNotice(message);

    var resultCards = contents.resultCards;

    // player들의 카드 결과
    for (const key of Object.keys(resultCards)) {
        message = '-> ' + key + '의 카드 [' + resultCards[key] + ']';
        addNotice(message);
    }

    var winners = contents.winnerNames;
    var winningChipsNum = contents.winningChips.numberOfChips;

    // 다음 턴의 번호
    var num = parseInt(turnNum.html()) + 1;

    // 비긴 경우와 승리한 경우 메시지는 다르다.
    if (contents.draw) {
        for (var i = 0; i < winners.length; i++) {
            message = '-> <strong>' + winners[i] +'</strong>이(가) 비겨서 [' + winningChipsNum + ']개의 칩을 얻습니다.';
            addNotice(message);

            if (winners[i] === playerName) {
                printWinner(contents.draw);

                // 새로운 턴을 시작하라고 서버에 메시지를 보내야 한다.
                if (i === 1) {
                    turnStart(num);
                }
            }
        }
    } else {
        message = '-> <strong>' + winners[0] +'</strong> 이(가) 승리하여 [' + winningChipsNum + ']개의 칩을 얻습니다.';
        addNotice(message);

        if (winners[0] === playerName) {
            printWinner(contents.draw);

            // 새로운 턴을 시작하라고 서버에 메시지를 보내야 한다.
            turnStart(num);
        }
    }

    function printWinner(draw) {
        if (draw) {
            alert('TURN DRAW');
        } else {
            alert('TURN WIN')
        }
    }
}

function printGameResult(contents) {
    bettingBtns.hide(); // 더 이상 배팅할 수 없게 버튼 지우기
    var draw = contents.draw;
    var winnerNames = contents.winnerNames;

    var message = '******** <strong> [INDIAN-POKER] GAME OVER </strong> *******';
    addNotice(message);

    if (draw) {
        message = '-> 비겼습니다!';
        addNotice(message);
    } else {
        message = '-> <strong>' + winnerNames[0] + '</strong>이(가) 승리하였습니다!';
        addNotice(message);
    }

    // game 종료 후에 Game 메인 페이지로 이동
    pageOut(playerName);
}

function pageOut(playerName) {
    setTimeout(function() {
        alert(playerName + '님 게임이 종료되었으므로 페이지를 이동합니다.');

        $(location).attr('href', '/indianpokers');
        }, 1500);
}

// --- Send METHOD ---

// GAME START
function gameStart(numberOfPeople, name) {
    if (numberOfPeople === 2) {
        var message = '******** <strong> [INDIAN-POKER] GAME START </strong> *******';
        addNotice(message); // 게임시작을 알려줌

        if (name === playerName) { // 두 번째 들어온 player가 게임 시작 요청을 한다.
            turnStart(1);
        }
    }
}

// TURN START
function turnStart(num) {
    var gameStartMessage = {'gameId': gameId, 'type': 'TURN_START', 'value': num, 'player': playerName};
    sock.send(JSON.stringify(gameStartMessage));
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

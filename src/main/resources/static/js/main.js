let searchUserInput;
let friendButton;
let resultsSearchButton;
let messageUl;

let socket;
let stompClient;
let subscribeOnChat;

let haveMessages = true;
let subscribe = 0;
let chatname;
let username;

function getImage() {
    fetch('/getImage', {
        method: 'GET',
    })
        .then(response => {
            return response.blob();
        })
        .then(blob => {
            const imageUrl = URL.createObjectURL(blob);
            const button = document.getElementById('profile-button');
            button.src = imageUrl;
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function connect() {
    socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
    fetchUsername()
    getChatList()
    getImage()
}

async function fetchUsername() {
    const response = await fetch('/getUsername', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    username = await response.text();
}

document.addEventListener("DOMContentLoaded", function () {

    searchUserInput = document.getElementById('searchUserInput');
    friendButton = document.getElementById('friendButton');
    resultsSearchButton = document.getElementById('resultsSearchButton');
    messageUl = document.getElementById('messageUl');

    resultsSearchButton.addEventListener("click", addChat);
    document.getElementById('searchUserForm').addEventListener("submit", findUser);
    document.getElementById('writeMessageForm').addEventListener("submit", sendMessage);

    const friendButtons = [...document.getElementsByClassName('friendButton')];
    friendButtons.forEach(function (friendButton) {
        friendButton.addEventListener('click', function () {
            openChat(this.innerText);
        });
    });
    connect();
});
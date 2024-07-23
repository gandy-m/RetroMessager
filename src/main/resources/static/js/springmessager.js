let searchUserForm;
let searchUserInput;
let searchUserButton;
let friendUl;
let friendButton;
let resultsSearchButton;
let resultsSearchContainer;
let leftContainer;
let messageUl;
let writeMessageInput;
let writeMessageForm;


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
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
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
        method: 'POST',
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


    resultsSearchContainer = document.getElementById('resultsSearchContainer');
    searchUserForm = document.getElementById('searchUserForm');
    searchUserInput = document.getElementById('searchUserInput');
    searchUserButton = document.getElementById('searchUserButton');
    friendUl = document.getElementById('friendUl');
    friendButton = document.getElementById('friendButton');
    resultsSearchButton = document.getElementById('resultsSearchButton');
    leftContainer = document.getElementById('leftContainer');
    messageUl = document.getElementById('messageUl');
    writeMessageInput = document.getElementById('writeMessageInput');
    writeMessageForm = document.getElementById('writeMessageForm');


    resultsSearchButton.addEventListener("click", addChat);
    searchUserForm.addEventListener("submit", sendFindUserRequest);
    searchUserButton.addEventListener("click", sendFindUserRequest);
    writeMessageForm.addEventListener("submit", sendMessage);


    const friendButtons = [...document.getElementsByClassName('friendButton')];
    friendButtons.forEach(function (friendButton) {
        friendButton.addEventListener('click', function () {
            chatButtonOnClick(this.innerText);
        });
    });


    connect();


});
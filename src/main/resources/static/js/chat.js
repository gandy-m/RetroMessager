function chatButtonOnClick(buttonText) {
    haveMessages = true;
    if (subscribe !== 0) {
        subscribeOnChat.unsubscribe();
    }
    subscribe = 1;
    fetch("/getChat/" + buttonText)
        .then(response => {
            if (!response.ok) {
                throw new Error("HTTP error " + response.status);
            }
            return response.json();
        })
        .then(data => {
            openChat(data);
        })
        .catch(error => {
            console.error("Ошибка при выполнении запроса:", error);
        });
    messageUl.innerHTML = '';
    writeMessageInput.value = '';
}

function openChat(chatDTO) {
    subscribeOnChat = stompClient.subscribe("/topic/chat/" + chatDTO.id, function (messageDTO) {
        showMessage(JSON.parse(messageDTO.body));
    });
    if (chatDTO.user1DTO.username === username) {
        chatname = chatDTO.user2DTO.username;
    } else {
        chatname = chatDTO.user1DTO.username;
    }
    if (chatDTO.messages.length !== 0) {
        chatDTO.messages.forEach(function (messageDTO) {
            showMessage(messageDTO);
        });
    } else {
        let messageLi = document.createElement('li');
        let messageButton = document.createElement('button');
        messageLi.appendChild(messageButton);
        messageUl.appendChild(messageLi);
        messageLi.classList.add('messageLiCenter');
        messageButton.classList.add('noMessage');
        messageButton.setAttribute('id', 'noMessage')
        messageButton.innerText = 'Send first message';
        haveMessages = false;
    }
    document.getElementById('writeMessageForm').style.display = 'block';
}


function showMessage(messageDTO) {
    if (haveMessages === false) {
        document.getElementById('noMessage').remove();
        haveMessages = true;
    }
    let messageLi = document.createElement('li');
    let messageButton = document.createElement('button');
    messageLi.appendChild(messageButton);
    messageUl.appendChild(messageLi);
    messageButton.innerText = messageDTO.text;
    messageButton.classList.add('messageButton');
    if (messageDTO.sender.username !== username) {
        messageLi.classList.add('messageLiLeft`');
    } else {
        messageLi.classList.add('messageLiRight');
    }
}


function sendMessage(event) {
    event.preventDefault();
    if (writeMessageInput.value) {
        if (writeMessageInput.value.trim().length > 0) {
            stompClient.send("/app/addMessage/" + chatname ,{}, writeMessageInput.value.trim());
            writeMessageInput.value = '';
        }
    }
}


function getChatList() {
    fetch("/getChatList")
        .then(response => response.json())
        .then(data => {
            data.forEach(function (chatDTO) {
                let friendLi = document.createElement('li');
                let friendBut = document.createElement('button')
                friendUl.appendChild(friendLi);
                friendLi.appendChild(friendBut)
                friendBut.classList.add('friendButton');
                friendLi.classList.add('friendLi');
                friendBut.setAttribute('id', 'friendButton');
                if (chatDTO.user2DTO.username === username) {
                    friendBut.innerText = chatDTO.user1DTO.username;
                } else if (chatDTO.user1DTO.username === username){
                    friendBut.innerText = chatDTO.user2DTO.username;
                }
                else {
                    console.log('ERROR')
                }
                friendBut.addEventListener('click', function () {
                    chatButtonOnClick(this.innerText);
                })
            })
        })
        .catch(error => {
            console.error("Ошибка при выполнении запроса:", error);
        });
}
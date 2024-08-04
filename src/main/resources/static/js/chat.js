function openChat(buttonText) {
    messageUl.innerHTML = '';
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
        .then(chatDTO => {
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
        })
        .catch(error => {
            console.error("Ошибка при выполнении запроса:", error);
});
}

function getChatList() {
    fetch("/getChatList")
        .then(response => response.json())
        .then(data => {
            data.forEach(function (chatDTO) {
                showChat(chatDTO);
            })
        })
        .catch(error => {
            console.error("Ошибка при выполнении запроса:", error);
        });
}

function addChat(event) {
    event.preventDefault()
    let content = resultsSearchButton.textContent;
    let chatname =  content.split('| ADD TO')[0].trim();
    console.log(chatname)
    fetch("/addchat/" + chatname, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(chatDTO => showChat(chatDTO))
        .catch(error => console.error('Ошибка:', error));
    hideButton();
}


function showChat(chatDTO) {
    if (chatDTO) {
        let friendUl = document.getElementById('friendUl');
        let friendLi = document.createElement('li');
        let chatButton = document.createElement('button');
        friendLi.appendChild(chatButton);
        friendUl.appendChild(friendLi);
        chatButton.classList.add('friendButton');
        friendLi.classList.add('friendLi');
        chatButton.setAttribute('id', 'friendButton');
        let content;
        if (chatDTO.user1DTO.username === username) {
            content = chatDTO.user2DTO.username;
        } else {
            content = chatDTO.user1DTO.username;
        }
        chatButton.innerText = content;
        chatButton.addEventListener('click', function () {
            openChat(this.innerText);
        });
    }
}
function addChat() {
    event.preventDefault()
    let content = resultsSearchButton.textContent;
    let chatname =  content.split('| ADD TO')[0].trim();
    console.log(chatname)
    fetch("/addchat/" + chatname, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(result => showNewChat(result))
        .catch(error => console.error('Ошибка:', error));
    buttonOnHidden();
}


function showNewChat(chatDTO) {
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
        if (Number(chatDTO.user1DTO.username) === username) {
            content = chatDTO.user2DTO.username;
        } else {
            content = chatDTO.user1DTO.username;
        }
        console.log(content)
        chatButton.innerText = content;
        chatButton.forEach(function (friendButton) {
            friendButton.addEventListener('click', function () {
                chatButtonOnClick(this.innerText);
            });
        })
    } else {
    }
}
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
    let writeMessageInput = document.getElementById('writeMessageInput');
    if (writeMessageInput.value) {
        if (writeMessageInput.value.trim().length > 0) {
            stompClient.send("/app/addMessage/" + chatname ,{}, writeMessageInput.value.trim());
            writeMessageInput.value = '';
        }
    }
}
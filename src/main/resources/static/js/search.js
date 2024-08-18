function findUser(event) {
    event.preventDefault();
    var request = searchUserInput.value.trim();
    if (request) {
        fetch("/find/" + request, {
            method: 'POST'
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => { throw new Error(text) });
                }
                return response.json();
            })
            .then(userDTO => {
                if (userDTO) {
                    showPopUp(userDTO.username);
                }
            })
            .catch(error => {
                alert("Error: " + error.message);
            });
    }
}


function showPopUp(username) {
    resultsSearchButton.style.display = 'block';
    resultsSearchButton.style.pointerEvents = 'auto';
    resultsSearchContainer.style.display = 'block';
    resultsSearchContainer.style.pointerEvents = 'auto';
    document.addEventListener('click', deletePopUp);
    resultsSearchButton.textContent = username + " | ADD TO FRIENDS";
    fetch("/getChatImage/" + username)
        .then(response => response.blob())
        .then(blob => {
            document.getElementById('resultSearchImage').src = URL.createObjectURL(blob)
            document.getElementById('resultSearchImageA').href = "/profile/" + username;
        });
}


function deletePopUp(event) {
    let isClickOnContainer = document.getElementById('container').contains(event.target)
    let isClickInsideInput = searchUserInput.contains(event.target);
    let isClickInsideButton = resultsSearchButton.contains(event.target);
    if (!isClickInsideInput || !isClickInsideButton || isClickOnContainer) {
        hideButton();
    }
}

function hideButton() {
    resultsSearchButton.style.display = 'none';
    resultsSearchButton.style.pointerEvents = 'none';
    resultsSearchContainer.style.display = 'none';
    resultsSearchContainer.style.pointerEvents = 'none';
    searchUserInput.value = '';

    document.removeEventListener('click', deletePopUp);
}
const eventSource = new EventSource("http://localhost:8080/chat?sender=adidas&receiver=nike");
eventSource.onmessage =( event) =>{
    console.log(1, event);
    const data = JSON.parse(event.data);
    console.log(2, data);
    initSendMessage(data);
}

function getSendMessageBox(msg, time){
    return `<div id="chat-sent-msg" class="sent_msg">
    <p>${msg}</p>
    <span class="time_date"> ${time}</span>
    </div>`;
}

async function addSendMessage(){
    let chatBox = document.querySelector("#chat-box");
    let msgInputBox = document.querySelector("#chat-outgoing-msg");
    let chatOutgoingBox = document.createElement("div");
    let date = new Date();
    let now = date.getHours() + ":" + date.getMinutes() + " | Today";

    let chat = {
        sender : "adidas",
        receiver : "nike",
        msg : msgInputBox.value
    }

    let response = await fetch("http://localhost:8080/chat", {
        method: "post",
        body: JSON.stringify(chat),
        headers:{
            "Content-Type":"application/json; charset=utf-8"
        }
    });
    //console.log(response);
    let parseResponse = await response.json();

    chatOutgoingBox.className = "outgoing_msg"
    chatOutgoingBox.innerHTML = getSendMessageBox(msgInputBox.value, now);
    chatBox.append(chatOutgoingBox);
    msgInputBox.value = "";
}

function initSendMessage(data){
    let chatBox = document.querySelector("#chat-box");
    let msgInputBox = document.querySelector("#chat-outgoing-msg");
    let chatOutgoingBox = document.createElement("div");
    chatOutgoingBox.className = "outgoing_msg";
    let md = data.createdAt.substring(5,10);
    let tm = data.createdAt.substring(11,16);
    convertTime = tm + " | " + md;
    chatOutgoingBox.innerHTML = getSendMessageBox(data.msg, convertTime);
    chatBox.append(chatOutgoingBox);
    msgInputBox.value = "";
}


document.querySelector("#chat-send").addEventListener("click", ()=>{
    addSendMessage();
});

document.querySelector("#chat-outgoing-msg").addEventListener("keydown", (e)=>{
    if(e.keyCode === 13){
        addSendMessage();
    }
});
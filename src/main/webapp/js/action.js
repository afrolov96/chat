$(document).ready(function () {

    var updateInProcces = false;
    var chatFrame = $('#chatFrame');
    var newMessageArea = $('#newMessageArea');
    var emojiArea = $('#emojiArea');
    var lastMessageTimeStamp = 0;


    /*    $("#chatFrame").load(function () {
     this.contentWindow.scrollBy(0, 100000)
     });*/

    $('#sendMessage').click(addNewMessage);

    function loadEmoji() {
        $.get('emoji').done(function (data) {
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    emojiArea.append('<img class="emoji" src="' + data[i].path + '">');
                    if ((i + 1) % 20 == 0) {
                        emojiArea.append('<br>');
                    }
                }
            }
        })
    }

    function addNewMessage() {
        var params = {};
        params['messageTime'] = new Date().getTime();
        params['messageBody'] = newMessageArea.html();
        $.post('messages', {
            params: JSON.stringify(params)
        }).done(function () {
            newMessageArea.html("");
        });
    }

    function loadNewMessages() {
        var params = {};
        if (!updateInProcces) {
            updateInProcces = true;
            $.get('messages', {
                p_now: JSON.stringify(lastMessageTimeStamp)
            }).done(function (data) {
                if (data.length > 0) {
                    for (var i = 0; i < data.length; i++) {
                        chatFrame.contents().find("body").append(renderMessage(data[i].messageBody));
                    }
                    lastMessageTimeStamp = data[data.length - 1].messageTime;
                    chatFrame.contents().scrollTop(chatFrame.contents().height());
                }
                updateInProcces = false;
            })
        }
    }

    function renderMessage(messageBody) {
        messageBody = messageBody.replace(new RegExp("\n", "g"), "<br>");
        messageBody = "<div class='msg'>" + messageBody + "<img class='emoji' src='/pics/emoji/png_64/1f60c.png'></div>";
        return messageBody
    }

    setInterval(loadNewMessages, 500);
    loadEmoji();

});

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
                $('img').click(clickEmoji);
            }
        })
    }

    function clickEmoji() {

        var img_smiley =$(this).clone().css("width","18px").wrap("<div />").parent().html();

        console.log('>>> emoji clicked ' + $(this).attr('src'));
        newMessageArea.focus();
        pasteHtmlAtCaret(img_smiley + "&nbsp"); //adding html at cursorlocation BY - Tim Down
        return false;
    }

    function pasteHtmlAtCaret( html ) {
        var sel, range;
        if ( window.getSelection ) {
            // IE9 and non-IE
            sel = window.getSelection();
            if ( sel.getRangeAt && sel.rangeCount ) {
                range = sel.getRangeAt( 0 );
                range.deleteContents();


                // Range.createContextualFragment() would be useful here but is
                // only relatively recently standardized and is not supported in
                // some browsers (IE9, for one)
                var el = document.createElement( "div" );
                el.innerHTML = html;
                var frag = document.createDocumentFragment(),
                    node, lastNode;
                while ( ( node = el.firstChild ) ) {
                    lastNode = frag.appendChild( node );
                }
                range.insertNode( frag );

                // Preserve the selection
                if ( lastNode ) {
                    range = range.cloneRange();
                    range.setStartAfter( lastNode );
                    range.collapse( true );
                    sel.removeAllRanges();
                    sel.addRange( range );
                }
            }
        } else if ( document.selection && document.selection.type != "Control" ) {
            // IE < 9
            document.selection.createRange().pasteHTML( html );
        }
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
        messageBody = "<div class='msg'>" + messageBody + "</div>";
        return messageBody
    }

    setInterval(loadNewMessages, 500);
    loadEmoji();

});

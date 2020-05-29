    var amountPhones = 0;

    function createInputTelephones(){
            amountPhones++;
			const boxTelephones = document.getElementById('box_telephones');
			boxTelephones.innerHTML = boxTelephones.innerHTML + "<div class=\"row\"><label for=\"input-phones" + amountPhones
			 + "\">Phone:</label><input id=\"input-phones" + amountPhones
			 + "\" type=\"text\" name = \"phones\" value=\"\" placeholder=\"Enter phone " + (amountPhones + 1)
			 + "\"/></div>";
	}

    function connect(){
        stompClient = Stomp.over(new SockJS('/gs-guide-websocket'));
        stompClient.connect({}, (frame) => {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/mesAddUser', (result) => {if(result.body){stompClient.send("/app/getUserList")};});
            stompClient.subscribe('/topic/mesUserList', (userList) => {usersShowTable(userList.body);
                                                                        $("#button-save").prop("disabled", false);
                                                                        clearInputForm ();
                                                                        });
            stompClient.send("/app/getUserList");
        });


    }

    function makePhone(number){
        return{
            number: number
        }
    }

    function sendFormAddUser(){
        $("#button-save").prop("disabled", true);
        var name = $("#input-name").val();
        var address = $("#input-street").val();
        var phonesArray = new Array();
        for (var i = 0; i <= amountPhones; i++){
            var inputObject = "#input-phones" + i;
            phonesArray.push( makePhone( $(inputObject).val() ) );
        }
        stompClient.send("/app/addUser", {}, JSON.stringify({"name": name , "address":{"street": address}, "phones": phonesArray}));

    }

    function usersShowTable(usersJson){
        $("#tableUsers").html("");
        var userArray = JSON.parse(usersJson);
        userArray.forEach(user => userObj(user));
    }

    function userObj(user){
        var phonesArray = user.phones;
        var phones = new String();
        phonesArray.forEach(function(phone){
            phones = phones + phone.number + "<br/>";
        })
        $("#tableUsers").append("<tr>"
                                + "<td>" + user.id + "</td>"
                                + "<td>" + user.name + "</td>"
                                + "<td>" + user.address.street + "</td>"
                                + "<td>" + phones + "</td>"
                                + "</tr>")
    }

    function clearInputForm (){
        $("#input-name").val("");
        $("#input-street").val("");
        $("#input-phones0").val("");
        $("#box_telephones").html("");
        amountPhones = 0;
    }
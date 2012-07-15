var server = require("lib/server");
var client = require("lib/client");

var socket = server.createServer(function(name) {
	echo("Connected: " + name);
});

var client1 = client.createClient("Test");

socket.connect(client1);
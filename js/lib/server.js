var Server = function(callback) {
	this.callback = callback;
};

Server.prototype = {
	connect : function(client) {
		this.callback(client.name);
	}
};

exports = {
	createServer : function(callback) {
		return new Server(callback);
	}
};
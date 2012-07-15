var Client = function(name) {
	this.name = name;
}

exports = {
	createClient : function(name) {
		return new Client(name);
	}
};
var express = require('express');
var app = express();

app.get('/', function(req, res) {
  res.json({ data: 'Sup!'});
});

app.listen(3000, function() {
  console.log('App running on port ' + this.address().port);
});

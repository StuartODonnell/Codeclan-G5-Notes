var Bank = function(){
  this.accounts = [250, 250, 500, 500, 500];
  this.total = 0;
}

Bank.prototype = {
  calculateTotal: function(){
    this.total = 0;
    this.accounts.forEach(function(account){
      this.total += account;
      console.log(this);
    }.bind(this));
  }
}

var bank = new Bank();
console.log('total before: ', bank.total);

bank.calculateTotal();
console.log('total after: ', bank.total);

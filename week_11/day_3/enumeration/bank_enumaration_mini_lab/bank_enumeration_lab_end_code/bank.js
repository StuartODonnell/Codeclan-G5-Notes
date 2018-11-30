var Account = require('./account.js');

var Bank = function() {
  this.accounts = []
}

Bank.prototype.addAccount = function (account) {
  this.accounts.push(account);
}

Bank.prototype.accountByName = function (name) {
  var foundAccount = this.accounts.find(function(account) {
    return account.name === name;
  });
  return foundAccount;
}

Bank.prototype.largestAccount = function () {
    var largest = this.accounts[0];

    this.accounts.forEach( function( account ) {
      if (account.value > largest.value ) {
        largest = account;
      }
    })
    return largest;
}

// Alternative using reduce()

// Bank.prototype.largestAccount = function() {
//   return this.accounts.reduce(function(largest, account){
//     return largest.value > account.value ? largest : account;
//   })
// }

Bank.prototype.payInterest = function () {
  this.accounts = this.accounts.map(function (account) {
    var newAccountValue = account.value * 1.1
    return new Account(account.name, newAccountValue, account.type)
  })
}

Bank.prototype.businessAccounts = function () {
  return this.accounts.filter(function (account) {
    return account.type === 'business';
  });
}

Bank.prototype.totalValue = function () {
  var total = this.accounts.reduce(function(acc, currentAccount) {
    return acc + currentAccount.value;
  }, 0)
  return total;
}

Bank.prototype.averageValue = function () {
  return this.totalValue() / this.accounts.length;
}

module.exports = Bank;

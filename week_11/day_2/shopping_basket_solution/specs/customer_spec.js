const Customer = require("../Customer");
const assert = require("assert");

describe("Customer", function(){
    let customer;

    beforeEach(function(){
        customer = new Customer("John", true);
    });

    it("should have a name", function(){
        assert.strictEqual(customer.name, "John");
    });

    it("should be loyal", function(){
        assert.strictEqual(customer.isLoyal, true);
    });
});
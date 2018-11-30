const Item = require("../Item");
const assert = require("assert");

describe("Item", function(){
    let item;

    beforeEach(function(){
        item = new Item("Rwandan Peaberry", 8.50, false);
    });

    it("should have a name", function(){
        assert.strictEqual(item.name, "Rwandan Peaberry");
    });

    it("should have a price", function(){
        assert.strictEqual(item.price, 8.50);
    });

    it("should have discount eligibility set", function(){
        assert.strictEqual(item.discount, false);
    });
});
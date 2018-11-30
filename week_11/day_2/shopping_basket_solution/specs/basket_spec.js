const Item = require("../Item");
const Basket = require("../Basket");
const Customer = require("../Customer");
const assert = require("assert");

describe("Shopping Basket", function(){
    let item1, item2, item3, item4, basket1, basket2, customer1, customer2;

    beforeEach(function(){
        item1 = new Item("Rwandan Peaberry", 8.50, true);
        item2 = new Item("Guji", 9.00, true);
        item3 = new Item("Huehuetenango", 9.50, false);
        item4 = new Item("Burundi", 6.00, false);

        customer1 = new Customer("John", true);
        customer2 = new Customer("Colin", false);

        basket1 = new Basket(customer1);
        basket2 = new Basket(customer2);
    });

    it("should have no items to start", function(){
        assert.strictEqual(basket1.items.length, 0);
    });

    it("should be able to add items", function(){
        basket1.addItem(item1);
        assert.strictEqual(basket1.items.length, 1);
    });

    it("should be able to remove items", function(){
        basket1.addItem(item1);
        basket1.addItem(item2);
        basket1.removeItem(item2);

        assert.strictEqual(basket1.items.length, 1);
        assert.strictEqual(basket1.items[0].item, item1);
    });

    it("should be able to provide a total of all items", function(){
        // Non-loyal customer!
        basket2.addItem(item3);
        basket2.addItem(item4);

        assert.strictEqual(basket2.getTotal(), 15.50);
    });

    it("should be able to provide a BOGOF discount", function(){
        basket2.addItem(item1);
        basket2.addItem(item1);

        assert.strictEqual(basket2.getTotal(), 8.50);
    });

    it("should be able to provide a 10% discount for > £20", function(){
        basket2.addItem(item1);
        basket2.addItem(item3);
        basket2.addItem(item4);

        assert.strictEqual(basket2.getTotal(), 21.60);
    });

    it("should be able to provide a 5% discount for loyal customers", function(){
        basket1.addItem(item3);
        basket1.addItem(item4);

        assert.strictEqual(basket1.getTotal(), 14.73);
    });
});
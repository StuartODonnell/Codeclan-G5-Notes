const Basket = function(customer){
    this.items = [];
    this.customer = customer;
};

Basket.prototype.addItem = function(itemToAdd){
    let added;

    for(let cartItem of this.items){
        if(cartItem.item.name == itemToAdd.name){
            cartItem.quantity += 1;
            added = true;
        }
    }

    if(!added){
        this.items.push({item: itemToAdd, quantity: 1});
    }
};

Basket.prototype.removeItem = function(itemToRemove){
    for(let i = this.items.length - 1; i >= 0; i--){
        if(this.items[i].item === itemToRemove){
            this.items[i].quantity -= 1;
            if(!this.items[i].quantity){
                this.items.splice(i, 1);
            }
        }
    }
}

Basket.prototype.getTotal = function(){
    let total = 0;

    // Get base total
    for(let cartItem of this.items){
        total += cartItem.item.price * cartItem.quantity;
    }

    // calculate BOGOF discount
    let bogofDiscountAmount = 0;
    for(let cartItem of this.items){
        if(cartItem.item.discount == true && cartItem.quantity > 1){
            let numberDiscountedItems = Math.floor(cartItem.quantity / 2);
            
            let discountAmount = numberDiscountedItems * cartItem.item.price;
            bogofDiscountAmount += discountAmount;
        }
    }
    total -= bogofDiscountAmount;

    // 10% off over £20
    if(total > 20){
        total = total * 0.9;
    }

    // Loyalty Discount
    if(this.customer.isLoyal){
        total = parseFloat(Math.round((total * 0.95) * 100) / 100);
    }
    
    return total;
};

module.exports = Basket;
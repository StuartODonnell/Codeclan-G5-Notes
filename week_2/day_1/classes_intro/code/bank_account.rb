class BankAccount


  attr_accessor :name, :balance, :type
  def initialize(name, balance, type)
    @name = name
    @type = type
    @balance = balance
  end

  def pay_in(balance)
    @balance += balance
  end

  def pay_monthly_fee
   @balance -= 10 if @type == 'personal'
   @balance -= 50 if @type == 'business'
  end

end

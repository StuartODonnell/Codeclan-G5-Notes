class AccountsController < ApplicationController
  def index
    authenticate_user!

    @accounts = current_user.accounts
    render( { json: @accounts } )
  end
end
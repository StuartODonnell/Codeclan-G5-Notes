import React from 'react'
import { Link } from 'react-router-dom'

class SignOut extends React.Component{

  constructor(){
    super()
    this.signOut = this.signOut.bind(this)
  }
  
  signOut(event){
    // sign out request here
  }

  render() {
    return (
       <div>
        <button onClick={this.signOut}>Sign Out</button>
        <Link className='shows-link' to='/shows'>View Shows</Link>
      </div>
    )
  }
}

export default SignOut
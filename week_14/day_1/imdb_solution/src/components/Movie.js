import React from 'react';
import './Movie.css';

class Movie extends React.Component {
  constructor(props){
    super();
    this.state = {
      showtimes: false
    }
  }

  showtimes(){
    this.setState({showtimes: !this.state.showtimes})
  }

  render(){

    return (
      <div className="movie-card">
        <img src="/images/icon.png" alt="" className="icon"/>
        <div className="movie-card__details">
          <div>
            <h3>{this.props.movie.title}</h3>
            <p className={`showtimes-${this.state.showtimes}`}>{this.props.movie.showtimes}</p>
          </div>
          <button className="showtimes" onClick={() => this.showtimes()}>Showtimes</button>
        </div>
      </div>
    )
  }
}

export default Movie;

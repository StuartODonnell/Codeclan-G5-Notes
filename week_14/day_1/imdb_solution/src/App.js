import React, { Component } from 'react';
import OpeningsBox from './containers/OpeningsBox';
import './App.css';

class App extends Component {

  render() {

    const movies = [
      {title: 'Mama Mia! Here we go again', showtimes: '19.00, 19.30, 20.00'},
      {title: 'Ant-Man and the Wasp', showtimes: '17.30, 19.15, 21.00'},
      {title: 'Mission: Impossible - Fallout', showtimes: '16.00, 19.30, 21.30'},
      {title: 'Incredibles 2', showtimes: '12.00, 14.30, 18.30'},
      {title: 'The First Purge', showtimes: '20.45'}
    ]
    return (
      <OpeningsBox movies={movies}/>
    );
  }
}

export default App;

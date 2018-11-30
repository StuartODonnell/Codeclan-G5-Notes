import React from 'react';
import Movie from './Movie';
import './MovieList.css'

const MovieList = (props) => {

  const movies = props.movies.map((movie, index) => {
    return (
      <li key={index}>
        <Movie movie={movie} />
      </li>
    )
  })

  return (
    <div className="movie-list">
      <ul>
        {movies}
      </ul>
    </div>
  )
}

export default MovieList

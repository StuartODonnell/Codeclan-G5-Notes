import React from 'react';
import Header from '../components/Header';
import MovieList from '../components/MovieList';
import Link from '../components/Link';
import ShowTimesButton from '../components/ShowTimesButton';
import './OpeningsBox.css'

const OpeningsBox = (props) => {
    return (
      <div className="openings-box">
        <Header title="UK Showtimes This Week" />
        <MovieList movies={props.movies} />
        <Link />
        <ShowTimesButton />
      </div>
    )
}

export default OpeningsBox;

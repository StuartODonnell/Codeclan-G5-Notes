import React, { Component } from 'react';
import TodoContainer from './containers/TodoContainer'
import Header from './components/Header'
import './App.css';

class App extends Component {
  render() {
    return (
    <React.Fragment>
      <Header title="Our Todos App"/>
      <TodoContainer/>
    </React.Fragment>
    );
  }
}

export default App;

import React from 'react';
import Todo from './Todo';

const TodosList = (props) => {

  const todoNodes = props.todos.map((todo, index) => {
    return <Todo key={index} task={todo}/>
  })

  return(
    <ul>
      {todoNodes}
    </ul>
  )

}

export default TodosList

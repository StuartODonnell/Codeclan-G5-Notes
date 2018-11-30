import React from 'react';

class TodoForm extends React.Component {
  constructor(props){
    super(props);
    this.state = {
      todo: ""
    }
    this.handleInputChange = this.handleInputChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleInputChange(event){
    this.setState({todo: event.target.value})
  }

  handleSubmit(event){
    event.preventDefault();
    const todo = this.state.todo.trim();
    if(!todo) return;

    this.props.onTodoSubmit(todo);
    this.setState({todo: ""});
  }

  render(){
    return(
      <form className="todoForm" onSubmit={this.handleSubmit}>
        <input
          type="text"
          placeholder="What's needing done, like?"
          onChange={this.handleInputChange}
          value={this.state.todo}
        />
        <input type="submit" value="Get it done"/>
    </form>
    )
  }
}

export default TodoForm

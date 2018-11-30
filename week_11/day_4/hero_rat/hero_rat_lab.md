# Heroes & Rats Lab

The task is to test drive the beginnings of an adventure game!

We won't be using pair-programming this time - we'll be practicing using branches and merges to work on different parts of the project simultaneously.

You can subdivide the task as you see fit, but it is usually easiest for one person to do the initial setup of the project. Then, they should push up to Github and [add the other person as a collaborator on the project](https://help.github.com/articles/inviting-collaborators-to-a-personal-repository/).

Once this is done, the second person can clone the project and start to work on it.

## Tasks:

A.
Create a constructor to create a Hero character

- A Hero has a name
- A Hero has health
- A Hero has a favourite food
- A Hero can talk saying their name
- A Hero has a collection of tasks to complete

B. Create a constructor to create Task objects

- A task has a difficulty level
- A task has an urgency level
- A task has a reward
- A task should be able to be marked as completed

C. Create a constructor to create Food objects.

- Food should have a name
- Food should have a replenishment value

D. Extend your hero.

- A hero should be able to eat food, and health should go up by the replenishment value
- If the food is their favourite food, their health should go up by 1.5 * value.
- A hero should be able to sort their tasks by difficulty, urgency or reward.
- A hero should be able to view tasks that are marked as completed or incomplete.

## Extension
- Create a constructor to create Rat objects.
- Rats should be able to touch food,  if they do the food becomes poisonous.
- Heroes that eat poisonous food should lose health.

## Further

Be creative. Extend the game! Give the hero super powers.

Create a super-villain who wants to take over the world!

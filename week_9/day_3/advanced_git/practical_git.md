# Practical Git

We've been using git for a while and we're pretty comfortable with the basics.

We can:

- stage changes via `git add`
- commit staged changes with `git commit`
- remove un-staged work with `git checkout -- .`
- un-stage work with `git reset`
- push commits (`git push`)
- pull new commits down (`git pull`)
- clone fresh repositories (`git clone`)

This lets us work on files, make some save points in case we mess up, and push those save points to the server in case our laptop explodes.

What we haven't touched on yet is the power git gives us when we are working with other people. This is going to be super important when you are doing your group projects so we're going to have a look at this today.

## Branching

One of the key features of git is that it allows is to create "branches" of code.

You can think of this as streams of work, which start together then diverge and come back together again. Much like a river, it comes down from the mountain, splits if it hits a small island or piece of land, but then comes back together again and heads to the sea.

A branch is **not** an independent piece of code that exists in isolation. The intention should always be to bring it back to the main codebase at some point.

### Master

So far we have always been using a branch called master. This is created for us as soon as we `init` a new git repo.

```
mkdir git_fun
cd git_fun

git init
```

Now that we have started our repo, we have to create our fun files.

```
touch fantastic_web_app
git add --all
git commit -m "First commit"
```

This is where we normally keep our stable, deployable code that we'd be happy to put "live" at any time. "Live" could be deployed to a server or even just run locally to demo your project.

Now a problem arises when you want to add new features. If we do this on master, and we add anything that breaks the code, we can no longer present the code in a working state. This problem becomes exponentially worse with the more developers that are working on a project.

Imagine Alice, Bob and Charlie are working on a project together to build Tic Tac Toe.

- Alice has decided she is going to do the board code
- Bob is going to add user logins
- Charlie has decided that quantum tic tac toe is the key feature that needs done right now.

Given we like to commit early and often, there will be lots of commits on master from everyone. What happens if Charlie commits something that breaks the site? And what if Bob and Alice do the same thing? How can we easily identify where the bug has come from? All we have is a jumble of commits.

Even if Bob fixes his bug, the code is still broken until both Alice and Charlie fix their bugs. It's not ideal.  This is where the power of branching comes in.

### Develop

Since our master branch is meant to always contain working code, it's good practice to have a secondary branch for in-progress code. We call this branch develop.

We don't need to do this - we can have as many branches as we want called anything but it's good to start getting into good habits now rather than trying to iron out bad habits later.

Let's now create our develop branch. We want to be on the branch that we want to create a stream from: in this case it's master. There's a special command we can do to achieve this - `git checkout`. Checkout allows us to change the branch or stream of code we are working on, or create a new branch to move to.

```
git checkout -b develop
```

We need to provide the -b argument just this once because we are creating a new branch. You'll notice that the terminal has now changed and tells us that we are in the "develop" branch. Develop at this exact point in time has the same history as master, since it was copied from it. It is **not** linked to master in any other way - adding files to master will not alter develop and adding files to develop will not alter master. Let's see this in action.

```
touch more_fun
git add --all
git commit -m "add a cool new feature"
```

If we go back to master, will more_fun be there?

```
git checkout master
```

Note that we don't need the -b argument - we are simply swapping between branches, not creating a new one.

It's gone! Is it lost forever? If we check our log, we can see no trace of the commit we just did.

```
git log
```

But it's fine, it's safely stored on our develop branch. Let's go back to develop and check our file is still there.

```
git checkout develop
```

There it is, safe and sound. Git has effectively copied master, with all of it's history and commits. This is what we call a branch. We can do work on develop now.

- Alice could add her authentication
- Charlie can change the world with his quantum tic tac toe
- and Bob can add the board.

Even if develop is broken, master is still there safe and sound.

> Get the students to add a new file on develop, then switch to master and check it's not there, then swap back again.

Note that if you have any un-staged files, they will teleport with you as you change branch so be careful of that.

> They don't need to do this.

```
touch wow_many_fun
git checkout master
git checkout develop
rm wow_many_fun
```

Our commits on develop exist in a completely separate universe from our commits on master, until we decide we are ready to bring them together. We call this "merging".

## Merging

"Merging" is the act of bringing together the history of one branch with another and resolving them. Luckily, git does this for us pretty seamlessly most of the time.

Let's assume we are happy with what we have on develop. Bob and Charlie and Alice have all added new code and tested each other's work and it's all looking good. We want to merge this to master so it's ready to show in our presentation.

We checkout the branch that we want to merge **into**. In this case we are merging develop **into** master.

```
git checkout master
git merge develop
```

You can see that our develop files now appear on master, and our commits have now magically appeared on master. Our merge was successful, hurrah!

> Get the students to checkout their develop branch, add a file and merge it to master.

Awesome, so we've got 2 branches and we are happily merging them together. You'll notice that git is telling us that it is "fast forwarding". This means that nothing has happened on the branch we are merging IN to, since we branched away from it. So what happens if the branch changes?

```
git checkout develop
touch such_new_feature_wow
git add --all
git commit -m "much commit"

git checkout master
touch bug_fixes
git add --all
git commit -m "fixes those bugs"

```

Both our master and develop branches have changed. What is going to happen when we merge?

```
git merge develop
```

You'll see that a text editor will pop up - we'll just close it for now - and git will say the merge was successful using the "recursive" strategy.

This means Git will check the directories in each branch and find out which files have differences compared to the base revision (the point at which we branched), and then use the one with changes. A new commit will be made to represent this process. We had the option of adding a message for this when the text editor popped up a minute ago.

This is all awesome - but what if someone changes the same file on each branch? This is very common when you do a pull and other people are working on your branch - they have no idea what files you have been changing.

```
git checkout develop
touch conflict
echo "develop text" >> conflict

git add --all
git commit -m "adds conflict file on develop"
```

Let's now go do a similar thing on master.

```
git checkout master
touch conflict
echo "master text" >> conflict

git add --all
git commit -m "adds conflict file on master"
```

What do we think is going to happen when we merge in develop now?

In this situation where both have changes, the new file's contents are merged textually, and if there's a problem with that, a conflict ensues. Let's merge and see what happens.

```
git merge develop
```

Sure enough git is saying "I need some help" because it doesn't know what to do with the files. It needs a human touch. Git has told us which file has the problem, so we open it up we'll see something has happened to it.

Git has marked the version on our current branch (HEAD) and the version on the merging branch and we can manually choose which version we want.

> Get the students to try this themselves. Create the same file on develop and master, with different contents. Then merge develop into master.

### Clean up

Note that after we have merged develop to master, we will also want to merge master to develop - in case there have been any changes done on master.

While we're on the subject: you should never commit directly to master. Now that you know about branches, and about develop, you have no excuse to ever do this. Projects have broken on presentation day because people committed to master five minutes before their talk.

Anyhow, like we said we want to merge master into develop first, just to make sure nobody changed master since we last merged develop in.

```
git checkout develop
git merge master
```

Now develop and master are effectively the same as each other again.

We can view our branches by typing

```
git branch
```

## Feature branches

We now have 2 branches - one for our stable code and one for our developmental code. But we still have a problem.

Remember when we talked about merge conflicts? We said these could happen if we changed the same file on master and on develop. But what if someone changed a file on develop and then pushed it up? Now, let's say we need to pull it down, and we've changed the same file locally. Boom. Merge conflict.

Now we're forced to fix this conflict. We only wanted to make sure we had the latest code, but suddenly we have to start thinking about whether our change or the remote change is most important. Not good! How can we avoid this situation?

It's helpful as developers to chunk up our tasks into packages of self-contained work. One pair of engineers might work on the database layer, making sure the server can talk to the database properly, while another might work on some animations client-side. These two pieces of work will never overlap.

This can help solve the problem, but it still might not fix it entirely. What if the client and server layers share some models?

The answer to this is feature branches. Every time we have a self contained piece of work to do, something which might interfere with what other people are doing, we should work on a feature branch.

So what does that look like? Let's make one.

We should always branch a feature off from develop since they are contributing to work that is in development. The convention is to call our branch `feature/name`. It's not a hard and fast rule, but it's a fairly standard thing to do.

```
git checkout develop
git checkout -b feature/cheeseburger
```

Cool so we have successfully made a feature branch. Let's add some files.

```
touch bread burger cheese
git add --all
git commit -m "Add cheeseburger"
```

So we have our work all saved in our little branch. Another developer could come along and work on it with it and push it to Github, without us ever polluting the code on develop.

We can have as many feature branches as we like, which can all be rolled into develop when we are ready. Let's merge this one back in.

```
git checkout develop
git merge feature/cheeseburger
```

The key thing about feature branches is that we delete them when we are done with them. They now live in develop and any changes should just be small bug fixes.

```
git branch -D feature/cheeseburger
```
This will delete the branch both remotely and locally.

> Get the class to make a feature branch, and merge it into develop the delete it.

Cool, so there's a couple of other tricks we have up our sleeves.

You can also create feature branches simply to play around with ideas you don't plan on saving. These tend to be called experiment branches, and you'd call the branch `experiment/name`.

## Reverting

Let's say something awful happens and you end up with broken code on master two minutes before your presentation. You know it was working fine one commit ago, but somebody decided they just had to add some last minute tweaks (a tomato background?) and now everything's screwed. How do we fix that?

Let's create another wee feature branch. You are all experts at that now.

```
git checkout develop
git checkout -b feature/test

touch bad-indentation broken-functions missing-semicolons
git add --all
git commit -m "Add bad code"

touch syntax-errors bracket-hell single-letter-variable-names
git add --all
git commit -m "Add even more rubbish code"
```

Cool so we have 2 commits now. Let's go merge this to develop.

```
git checkout develop
git merge feature/test
```

If you look at the log, you'll see both commit messages from the feature branch now appear on develop as we'd expect.

But this is bad code. We want to undo the merge. How?

```
git log
# find hash of merge commit, 
# e.g. 0c7359ba490b43f177a59251371d7ae46dda4710
git revert { commit_hash }
```

All of our changes from the feature branch are gone. We have a new commit and our history is totally preserved. This is a very safe way to undo changes.

## Stashing

One last thing. What do we do if we have started work, and need to swap to do a quick fix on master.

```
touch just-a-wee-thing-im-trying
echo "TEXT" >> just-a-wee-thing-im-trying
git add .
```

Oh no, we've accidentally made changes on master that we intended for develop. We can use the handy "stash" command for this.

```
git stash
git checkout develop
git stash pop
```

Stash allows us to magically transport our changes to another branch.

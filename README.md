## <img alt="Title Image" src="data/title.png" height="50" />

CPSC 210 Personal Project

## Phase 0-2 : Project Idea and User Stories

### What?

PokéJar is an application designed to analyze the stats of Pokémon teams.

### Why?

This project is of interest to me because
I've recently gotten my first Pokémon game &mdash; Pokémon Violet.
As a new player, an analytics toolset would make
it so much easier for me to master my Pokémon game.

### Who?

The targeted user group of this app are Pokémon trainers.

### User Stories

- [x] As a user, I want to be able to add my Pokémon to my box
- [x] As a user, I want to be able to remove Pokémon from my box
- [x] As a user, I want to be able to edit the attributes of each Pokémon
- [x] As a user, I want to be able to view and analyze each Pokémon
- [x] As a user, I want to be able to form multiple teams with my Pokémon
- [x] As a user, I want to be able to remove teams from a list of teams
- [ ] As a user, I want to be able to switch Pokémon in and out from my teams
- [x] As a user, I want to be able to view and analyze my teams
- [x] As a user, I want all current app data to be autosaved when I close the app
- [x] As a user, I want my autosave to be automatically loaded when I open the app
- [x] As a user, I want to be able to save all current app data to a json file
- [x] As a user, I want to be able to load a saved app state from a json file

## Phase 3: Instructions for Grader

### UI Summary

- The PokéJar UI is composed of a left tabbed pane and a right tabbed pane.
- The left pane is used to manage your box, i.e. list of Pokémon, and teams (to be implemented in the future).
- The right pane is used to see the basic info of your Pokémon, to see a generated insight about your Pokémon, as well as to edit your Pokémon.

### Step-by-step Instructions

##### Opening (Visual Component)

- Every time you open the app, a cool custom-made splash screen appears for three seconds before the app is launched.
- Then, "./data/autosave.json" will be automatically loaded to the app to retrieve the save state when you last closed the app.

##### Adding and Editing Pokémon (Action 1)

- Click the "Add Pokémon" button to add a new Pokémon to your box. The new Pokémon will have no name, no type, and no moves.
- In order to edit your brand new Pokémon. Click the "Edit Pokémon" button in the "Basic Info" panel.
- The "Basic Info" panel will be replaced by the "Edit Pokémon" panel to allow you to edit your Pokémon.
- As you type into the name field or choose the Pokémon's type, you can see live changes to your box on the left pane.
- You can choose to give this Pokémon sick moves by clicking the "Add Move" button. Then you will be prompted with a series of popup windows to set up your move.
  - Number of types is limited to two and number of moves is limited to six, buttons and checkboxes will be disabled to prevent exceeding those limits.
- After you are done with customizing your Pokémon, click "Done" to return to viewing "Basic Info". All info will be updated to reflect changes.

##### Analyze Pokémon (Action 1.5)

- Click the "Insight" tab to view an insight on your selected Pokémon's offensive and defensive multipliers generated based on their types and moves. 
  - There are some footnote explaining this analysis below the multiplier table. 
- Feel free to add new Pokémon or edit your existing Pokémon to see how their insights change.

##### Removing Pokémon (Action 2)

- You can remove a Pokémon by selecting it and then press the "Remove Pokémon" Button.

##### Saving

- Once you are satisfied with your box of Pokémon, click "File" > "Save As..." on the menu bar to bring up a prompt to save the current state of the app to a JSON file anywhere you choose.

##### Loading

- By clicking "File" > "Load File...", a prompt will show up to let you choose any valid save file on your computer to be loaded directly into the app's interface.

##### Closing

- When you close the app, the state of the app will automatically be saved to "./data/autosave.json" to be restored the next time you open the app.
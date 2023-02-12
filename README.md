# PokéJar
CPSC 210 Personal Project

## Phase 0: Project Idea and User Stories

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

- [x] As a user, I want to be able to add my Pokémon to my roster.
- [x] As a user, I want to be able to remove Pokémon from my roster.
- [ ] As a user, I want to be able to edit the attributes of each Pokémon.
- [x] As a user, I want to be able to view the stats of each Pokémon.
- [x] As a user, I want to be able to form teams with my Pokémon.
- [x] As a user, I want to be able to remove teams from a list of teams.
- [ ] As a user, I want to be able to switch Pokémon in and out from my teams.
- [ ] As a user, I want to be able to view the stats of my team.

## Java 11 &rarr; Java 8 for Autograder

- IllegalCallerException &rarr; IllegalStateException (model.Type)
- List.of &rarr; Arrays.asList
- " ".repeat(n) &rarr; String.join("", Collections.nCopies(n, " "))
- new ArrayList&lt;&gt;() {} &rarr; new ArrayList&lt;Team&gt;() {} (ui.PokeJar)

# Spawn (fake) Border
A Server utility mod for visualising spawn chunks of the world by sending a fake world border to a client within it.

## Goal
To provide Server Operators with a approach to give a visual guide to any players that are within the Spawn Chunks of a world without needing any client modifications, this is often important to know as part of a lag optimisation tactic to ensure that server performance is only being consumed by player-made structures if they are actively within them.

## Approach
When the server is started, the mod will watch for when the game requests for a "START" chunk ticket which is typically only used by spawn chunks then use this to calculate the world border based on the radius of the request, the server will then;
- Check if a player sends movement data within this area
  - If they are within this area, send that player a World Border data packet with a modified world border set to the calculated spawn chunks (plus a bit, to allow the player to walk through)
- Check if a player sends a movement data outside of this area
  - If they were inside of this area and no longer are, sends a World Border data pack with the original world border data.

## Installation
//TO DO

## Usage
This mod takes no specific adjustments and provides no cofiguration file and will just automatically operate.
You can observe this working by simply moving around in the world within a few 1XX blocks of world spawn (If your not sure where that is, unaliving with no spawn set should plant you at world spawn)

## Common Issues
- There is a possible chance that a laggier player moving quickly might collide with the fake world border before the server has time to send an updated packet to them (as the client believes the world border is actually there)
- There is not a way to customise how this world border appears so it could be visually confusing to players that do not understand this mod is installed

Stats of battle items: 
- Health of player: 100
    - 75 in hard mode
- Damage increase of sword: *1.5
- Damage increase of bow: *2
- Damage decrease of armour: halves the damage
- Damage reducer of shield: *0.4
- Durability of shield: 5
- Durability of bow: 5
- Attack of enemies:
    - Hard: 20
    - Standard: 10
    - Peaceful: 0
- Durability of armour: 5
- Durability of sword: 5


Assume that only one effect of invincibility/invisibility can hold at one time(can’t stack)

Moving Entities:
- Spider health = 25
- Mercenary health = 60
- ZombieToast health = 40

- Setting max number of spiders to 4
- Mercenaries spawn at the entry location ‘periodically’
- Setting the ‘periodically’ time to once every 10 ticks
    - Spiders spawn once every 10 ticks
- Once mercenaries become allies, they also go through doors and portals just like the player
- Spiders will leave their ‘circling path’ when player drinks invincible potion and will directly run away from the player
    - Spiders are not constrained by the wall boundaries when running away from player while they are invincible
    - If spider is still alive when the invincible potion expires, they will teleport back to their circling path and continue from where they left off
- Hydra also spawns at the player's entry location

Buildable Entities:
- For bow, if player holds 2 wood + 1 treasure + 1 key in there inventory, the player uses the treasure first instead of the key for crafting
- Can have more than one instance in player’s inventory 


Static Entities: 
- Portals are linked by their colour 
- Portal teleport entity to exact location of other portal, rather than next to it like the spec.
- If there are more than 2 portals of the same colour, entering one will teleport the player to the nearest portal of the same colour
- If a portal doesn’t have a corresponding portal, it will act as a non-standable static entity
- Portal cannot be destroyed by a bomb
- Boulders can be pushed onto any entity the player can walk over
- Bombs do not explode if placed next to an already activated switch	


Collectable Entities: 
- Bomb destroys only adjacent positions( destroys all 8 surrounding blocks)
    - Has to be put down by player for it to be able to detonate


Battles: 
- If player is on the same cell with more than one enemy, the player will do the normal damage to all enemies
- If the player has more than 1 weapon (e.g. sword and bow) the player will get a damage increase from all the weapons and use durability of all the weapons in a battle
- If the player has more than 1 defensive item (e.g. armour and shield) the player will get a damage reduction from all the defensive items and use durability of all them in a battle

Potions:
- Invisibility and invincibility potions last for 10 ticks

Sun Stone:
- Sun stone is used up when crafting a sceptre or midnight armour
- Sunstone can’t replace treasure in crafting situations
- When opening a door, the player will prefer to use sunstone over key
- Sunstone will be used over treasure when bribing a mercenary

Sceptre:
- Still have to interact to bribe: benefit is that it can bribe assassins too.
- When bribing a mercenary, order of use is sunstone, sceptre then treasure


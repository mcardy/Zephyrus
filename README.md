Zephyrus-Addon
========

This is an example addon for the Bukkit servermod Zephyrus.

### Making an add-on for Zephyrus

#### Setting up your workspace
First off, you must know how to make a plugin. If you don't, then don't continue with this tutorial. If you do, then add Zephyrus as an external jar much the same way as you would have added Bukkit. Instead of using Bukkit's javadocs use Zephyrus' (link: http://minnymin3.github.io/Zephyrus/). Then in your plugin.yml file, put this in ** depend: [Zephyrus] ** . This makes your plugin dependant on Zephyrus so that your plugin cannot load without it. This will stop errors from spaming your consol. Now you are ready to start making your custom spells!

#### Your first spell
The first thing you are going to want to do is extend Spell (minny.zephyrus.spells.Spell) and add the unimplemented methods. When you hover over the methods you can see what each one is. Fill in each method appropriately. **Remember that the mana cost is multiplied by the mana multiplier which is 5 by default!** The run(Player player) is the most important method. This is called when the spell is cast. It is the method where you would heal the player in, feed the player in, or anything else! If your spell requires you to be looking at a specific block or something like that, then add **public boolean canRun(Player player)** to the class. This will decide if the spell can or can't run. If you then want to add a custom message that is sent to the player when canRun returns false, add **public String failMessage()**. Now you are done your first spell!

#### Registering your spell
Inside your main class, you will want to define Zephyrus as a variable. Add this into your onEnable: ** Zephyrus zephyrus = new Zephyrus;**. Now you will want to tell Zephyrus that you have a new spell. To do this, lets pretend that you named your spell class TestSpell. Add **new TestSpell(zephyrus);** into your onEnable and that registers your spell! Now you are all done!
  
Licenced under GNU General Public Licence version 3

#### JavaDocs
http://minnymin3.github.io/Zephyrus/

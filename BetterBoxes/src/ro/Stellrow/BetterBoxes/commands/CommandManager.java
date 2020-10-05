package ro.Stellrow.BetterBoxes.commands;

import ro.Stellrow.BetterBoxes.BetterBoxes;

public class CommandManager {
    private final BetterBoxes pl;
    public CommandManager(BetterBoxes pl) {
        this.pl = pl;
    }

    public void registerCommands(){
        pl.getCommand("betterboxes").setExecutor(new BetterBoxCommand(pl));
    }
}

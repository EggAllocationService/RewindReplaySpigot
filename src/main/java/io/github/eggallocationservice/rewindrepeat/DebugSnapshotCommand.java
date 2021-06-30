package io.github.eggallocationservice.rewindrepeat;


import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandExecutor;
import io.github.eggallocationservice.rewindrepeat.database.Database;
import io.github.eggallocationservice.rewindrepeat.database.DatabaseReplay;
import io.github.eggallocationservice.rewindrepeat.replays.ReplayAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DebugSnapshotCommand implements CommandExecutor {
    @Override
    public void run(CommandSender sender, Object[] args) throws WrapperCommandSyntaxException {
        Player p = (Player) sender;
        long start = System.currentTimeMillis();
        int duration = (int) args[0];
        if (duration > Config.BUFFER_SIZE_SECONDS) {
            sender.sendMessage("Cannot clip more than 30 seconds");
            return;
        }
        byte[] data = ReplayAPI.captureReplay(p.getWorld(), duration);
        sender.sendMessage("Created " + data.length / 1000 + "KB replay in " + (System.currentTimeMillis() - start) + "ms");
        try {
            Files.write(Path.of("debug.replay"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void register() {
        new CommandAPICommand("debugclip")
                .executes(this)
                .withArguments(new IntegerArgument("duration"))
                .withPermission(CommandPermission.OP)
                .register();

    }
}

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
import io.github.eggallocationservice.rewindrepeat.snapshots.Snapshot;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotReel;
import io.github.eggallocationservice.rewindrepeat.snapshots.SnapshotThread;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SnapshotCommand implements CommandExecutor {
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
        String name = (String) args[1];
        DatabaseReplay r = new DatabaseReplay();
        r.id = name;
        r.data = data;
        Database.getInstance().replays.insertOne(r);
    }
    public void register() {

        new CommandAPICommand("clip")
                .executes(this)
                .withArguments(new IntegerArgument("duration"), new StringArgument("name"))
                .withPermission(CommandPermission.OP)
                .register();

    }
}

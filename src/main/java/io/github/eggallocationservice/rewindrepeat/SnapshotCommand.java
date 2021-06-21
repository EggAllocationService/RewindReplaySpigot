package io.github.eggallocationservice.rewindrepeat;


import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;
import dev.jorel.commandapi.executors.CommandExecutor;
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
        if (duration > 30) {
            sender.sendMessage("Cannot clip more than 30 seconds");
            return;
        }
        byte[] data = ReplayAPI.captureReplay(p.getWorld(), 15);
        sender.sendMessage("Created " + data.length / 1000 + "KB replay in " + (System.currentTimeMillis() - start) + "ms");
        try {
            Files.write(Path.of("test.replay"), data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void register() {

        new CommandAPICommand("clip")
                .executes(this)
                .withArguments(new IntegerArgument("duration"))
                .register();

    }
}

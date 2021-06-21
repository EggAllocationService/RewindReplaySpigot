package io.github.eggallocationservice.rewindrepeat.snapshots;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.util.concurrent.Futures;
import io.github.eggallocationservice.rewindrepeat.snapshots.util.EncodeResult;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_16_R3.PacketPlayOutMapChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Entity;
import org.bukkit.material.MaterialData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

public class SnapshotThread extends Thread {
    World world;
    Field f; // f is the field which containes the serialized chunk
    static ExecutorService executor = Executors.newFixedThreadPool(12);
    public SnapshotThread(World target) {
        super();
        world = target;
        try {
            f = PacketPlayOutMapChunk.class.getDeclaredField("f");
            f.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        ArrayList<Future<EncodeResult>> futures = new ArrayList<>();
        Chunk[] loaded = world.getLoadedChunks().clone();
        Snapshot s = new Snapshot(loaded.length, world.getEntities().size());
        for (Chunk c: loaded) {
            futures.add(executor.submit(() -> {
                return encodeChunk(c);
            }));
        }
        for (Future<EncodeResult> v : futures) {
            try {
                EncodeResult chunk = v.get();
                s.chunks.put(chunk.position, chunk.data);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        for (Entity y: world.getEntities()) {
            s.entities.add(new SEntityInfo(y));
        }



        SnapshotReelManager.reels.get(world.getName()).newSnapshot(s);

    }





    public EncodeResult encodeChunk(Chunk c) {

       /* short[] blocks = new short[16 * 16 * 256];
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 256; y++) {
                    Material b = c.getBlock(x, y, z).getBlockData().getMaterial();
                    blocks[index(x, y, z)] = (short) Block.getCombinedId(((CraftMagicNumbers)Bukkit.getUnsafe()).getBlock(b).getBlockData());
                }
            }
        }
        ByteBuffer bb =  ByteBuffer.allocate(blocks.length * 2);
        bb.asShortBuffer().put(blocks);*/

        PacketPlayOutMapChunk p = new PacketPlayOutMapChunk(((CraftChunk) c).getHandle(), 65535);
        try {
            EncodeResult r = new EncodeResult();
            r.data = (byte[]) f.get(p);
            r.position = c.getX() + ":" + c.getZ();
            return r;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }





        return new EncodeResult();
    }

    /**
     * Calculate the index into internal arrays for the given coordinates.
     *
     * @param x The x coordinate, for east and west.
     * @param y The y coordinate, for up and down.
     * @param z The z coordinate, for north and south.
     * @return The index.
     */
    public int index(int x, int y, int z) {
        if (x < 0 || z < 0 || x >= 16 || z >= 16) {
            throw new IndexOutOfBoundsException(
                    "Coords (x=" + x + ",z=" + z + ") out of section bounds");
        }
        //return (y & 0xf) << 8 | z << 4 | x;
        //System.out.println("x=" + x +",y=" +y +",z=" +z);
        return (y * 256) + (z * 16) + x;
    }


    public static byte[] compress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            DeflaterOutputStream defl = new DeflaterOutputStream(out);
            defl.write(in);
            defl.flush();
            defl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(150);
            return null;
        }
    }

    public static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(150);
            return null;
        }
    }
}

//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import joptsimple.internal.*;
import java.io.*;
import net.minecraft.client.*;
import java.util.*;
import java.util.stream.*;
import javax.annotation.*;
import net.minecraft.client.network.*;
import java.util.concurrent.*;
import com.google.common.util.concurrent.*;
import com.google.common.collect.*;

public class PlayerInfoHelper
{
    private static final int THREAD_COUNT = 1;
    public static final int MAX_NAME_LENGTH = 16;
    private static final ListeningExecutorService EXECUTOR_SERVICE;
    private static final Map<String, PlayerInfo> NAME_TO_INFO;
    private static final Map<UUID, PlayerInfo> UUID_TO_INFO;
    
    private static PlayerInfo register(final String name) throws IOException {
        if (Strings.isNullOrEmpty(name) || name.length() > 16) {
            return null;
        }
        final PlayerInfo info = new PlayerInfo(name);
        PlayerInfoHelper.NAME_TO_INFO.put(info.getName().toLowerCase(), info);
        PlayerInfoHelper.UUID_TO_INFO.put(info.getId(), info);
        return info;
    }
    
    private static PlayerInfo register(final UUID uuid) throws IOException {
        final PlayerInfo info = new PlayerInfo(uuid);
        PlayerInfoHelper.NAME_TO_INFO.put(info.getName().toLowerCase(), info);
        PlayerInfoHelper.UUID_TO_INFO.put(info.getId(), info);
        return info;
    }
    
    private static PlayerInfo offlineUser(final String name) {
        if (name.length() > 16) {
            return null;
        }
        return new PlayerInfo(name, true);
    }
    
    public static PlayerInfo get(final String name) {
        return Strings.isNullOrEmpty(name) ? null : PlayerInfoHelper.NAME_TO_INFO.get(name.toLowerCase());
    }
    
    public static PlayerInfo get(final UUID uuid) {
        return (uuid == null) ? null : PlayerInfoHelper.UUID_TO_INFO.get(uuid);
    }
    
    public static List<PlayerInfo> getPlayers() {
        return (List<PlayerInfo>)Immutables.copyToList((Collection)PlayerInfoHelper.UUID_TO_INFO.values());
    }
    
    public static List<PlayerInfo> getOnlinePlayers() {
        PlayerInfo pl;
        return (List<PlayerInfo>)((Minecraft.getMinecraft().getConnection() == null) ? Collections.emptyList() : Minecraft.getMinecraft().getConnection().getPlayerInfoMap().stream().map(info -> {
            pl = get(info.getGameProfile().getName());
            return (pl == null) ? offlineUser(info.getGameProfile().getName()) : pl;
        }).collect((Collector<? super Object, ?, List<Object>>)Collectors.toList()));
    }
    
    public static PlayerInfo lookup(final String name) throws IOException {
        final PlayerInfo info = get(name);
        if (info == null) {
            return register(name);
        }
        return info;
    }
    
    public static PlayerInfo lookup(final UUID uuid) throws IOException {
        final PlayerInfo info = get(uuid);
        if (info == null) {
            return register(uuid);
        }
        return info;
    }
    
    public static boolean registerWithCallback(final String name, final FutureCallback<PlayerInfo> callback) {
        final PlayerInfo info = get(name);
        if (info == null) {
            Futures.addCallback(PlayerInfoHelper.EXECUTOR_SERVICE.submit(() -> register(name)), (FutureCallback)callback);
            return true;
        }
        Futures.addCallback(Futures.immediateFuture((Object)info), (FutureCallback)callback);
        return false;
    }
    
    public static boolean registerWithCallback(final UUID uuid, final FutureCallback<PlayerInfo> callback) {
        final PlayerInfo info = get(uuid);
        if (info == null) {
            Futures.addCallback(PlayerInfoHelper.EXECUTOR_SERVICE.submit(() -> register(uuid)), (FutureCallback)callback);
            return true;
        }
        Futures.addCallback(Futures.immediateFuture((Object)info), (FutureCallback)callback);
        return false;
    }
    
    public static boolean registerWithCallback(final UUID uuid, final String name, final FutureCallback<PlayerInfo> callback) {
        return registerWithCallback(uuid, (FutureCallback<PlayerInfo>)new FutureCallback<PlayerInfo>() {
            public void onSuccess(@Nullable final PlayerInfo result) {
                callback.onSuccess((Object)result);
            }
            
            public void onFailure(final Throwable t) {
                PlayerInfoHelper.registerWithCallback(name, callback);
            }
        });
    }
    
    public static boolean generateOfflineWithCallback(final String name, final FutureCallback<PlayerInfo> callback) {
        final ListenableFuture<PlayerInfo> future = (ListenableFuture<PlayerInfo>)Futures.immediateFuture((Object)offlineUser(name));
        Futures.addCallback((ListenableFuture)future, (FutureCallback)callback);
        return false;
    }
    
    public static UUID getIdFromString(final String uuid) {
        if (uuid.contains("-")) {
            return UUID.fromString(uuid);
        }
        return UUID.fromString(uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
    }
    
    public static String getIdNoHyphens(final UUID uuid) {
        return uuid.toString().replaceAll("-", "");
    }
    
    static {
        EXECUTOR_SERVICE = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(Math.max(1, 1)));
        NAME_TO_INFO = Maps.newConcurrentMap();
        UUID_TO_INFO = Maps.newConcurrentMap();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            PlayerInfoHelper.EXECUTOR_SERVICE.shutdown();
            while (!PlayerInfoHelper.EXECUTOR_SERVICE.isShutdown()) {
                try {
                    PlayerInfoHelper.EXECUTOR_SERVICE.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                }
                catch (InterruptedException ex) {}
            }
        }));
    }
}

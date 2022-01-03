//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\u1\Documents\Java Decompiler\1.12 stable mappings"!

//Decompiled by Procyon!

package me.zeroeightsix.kami.util;

import net.minecraft.client.entity.*;
import java.net.*;
import com.google.gson.*;
import com.google.common.collect.*;
import net.minecraft.client.*;
import javax.net.ssl.*;
import java.io.*;
import java.util.*;

public class PlayerInfo implements GsonConstant
{
    private final UUID id;
    private final UUID offlineId;
    private final boolean isOfflinePlayer;
    private final List<Name> names;
    
    public PlayerInfo(final UUID id) throws IOException {
        Objects.requireNonNull(id);
        this.id = id;
        this.names = (List<Name>)ImmutableList.copyOf((Collection)lookupNames(id));
        this.offlineId = EntityPlayerSP.getOfflineUUID(this.getName());
        this.isOfflinePlayer = false;
    }
    
    public PlayerInfo(final String name) throws IOException, NullPointerException {
        Objects.requireNonNull(name);
        final JsonArray ar = new JsonArray();
        ar.add(name);
        final JsonArray array = getResources(new URL("https://api.mojang.com/profiles/minecraft"), "POST", (JsonElement)ar).getAsJsonArray();
        final JsonObject node = array.get(0).getAsJsonObject();
        final UUID uuid = PlayerInfoHelper.getIdFromString(node.get("id").getAsString());
        Objects.requireNonNull(uuid);
        this.id = uuid;
        this.names = (List<Name>)ImmutableList.copyOf((Collection)lookupNames(uuid));
        this.offlineId = EntityPlayerSP.getOfflineUUID(name);
        this.isOfflinePlayer = false;
    }
    
    public PlayerInfo(final String name, final boolean dummy) {
        this.id = EntityPlayerSP.getOfflineUUID(name);
        this.names = Collections.singletonList(new Name(name));
        this.offlineId = this.id;
        this.isOfflinePlayer = true;
    }
    
    private static List<Name> lookupNames(final UUID id) throws IOException {
        final JsonArray array = getResources(new URL("https://api.mojang.com/user/profiles/" + PlayerInfoHelper.getIdNoHyphens(id) + "/names"), "GET").getAsJsonArray();
        final List<Name> temp = (List<Name>)Lists.newArrayList();
        for (final JsonElement e : array) {
            final JsonObject node = e.getAsJsonObject();
            final String name = node.get("name").getAsString();
            final long changedAt = node.has("changedToAt") ? node.get("changedToAt").getAsLong() : 0L;
            temp.add(new Name(name, changedAt));
        }
        Collections.sort(temp);
        return temp;
    }
    
    public UUID getId() {
        return this.id;
    }
    
    public UUID getOfflineId() {
        return this.offlineId;
    }
    
    public boolean isOfflinePlayer() {
        return this.isOfflinePlayer;
    }
    
    public String getName() {
        if (!this.names.isEmpty()) {
            return this.names.get(0).getName();
        }
        return null;
    }
    
    public List<Name> getNameHistory() {
        return this.names;
    }
    
    public String getNameHistoryAsString() {
        final StringBuilder builder = new StringBuilder();
        if (!this.names.isEmpty()) {
            final Iterator<Name> it = this.names.iterator();
            it.next();
            while (it.hasNext()) {
                final Name next = it.next();
                builder.append(next.getName());
                if (it.hasNext()) {
                    builder.append(", ");
                }
            }
        }
        return builder.toString();
    }
    
    public boolean isLocalPlayer() {
        return String.CASE_INSENSITIVE_ORDER.compare(this.getName(), Minecraft.getMinecraft().player.getName()) == 0;
    }
    
    public boolean matches(final UUID otherId) {
        return otherId != null && (otherId.equals(this.getOfflineId()) || otherId.equals(this.getId()));
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof PlayerInfo && this.id.equals(((PlayerInfo)obj).id);
    }
    
    @Override
    public int hashCode() {
        return this.id.hashCode();
    }
    
    @Override
    public String toString() {
        return this.id.toString();
    }
    
    private static JsonElement getResources(final URL url, final String request, final JsonElement element) throws IOException {
        HttpsURLConnection connection = null;
        JsonElement data;
        try {
            connection = (HttpsURLConnection)url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(request);
            connection.setRequestProperty("Content-Type", "application/json");
            if (element != null) {
                final DataOutputStream output = new DataOutputStream(connection.getOutputStream());
                output.writeBytes(PlayerInfo.GSON.toJson(element));
                output.close();
            }
            final Scanner scanner = new Scanner(connection.getInputStream());
            final StringBuilder builder = new StringBuilder();
            while (scanner.hasNextLine()) {
                builder.append(scanner.nextLine());
                builder.append('\n');
            }
            scanner.close();
            final String json = builder.toString();
            data = PlayerInfo.PARSER.parse(json);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return data;
    }
    
    private static JsonElement getResources(final URL url, final String request) throws IOException {
        return getResources(url, request, null);
    }
    
    public static class Name implements Comparable<Name>
    {
        private final String name;
        private final long changedAt;
        
        public Name(final String name, final long changedAt) {
            this.name = name;
            this.changedAt = changedAt;
        }
        
        public Name(final String name) {
            this(name, 0L);
        }
        
        public String getName() {
            return this.name;
        }
        
        public long getTimeChanged() {
            return this.changedAt;
        }
        
        @Override
        public int compareTo(final Name o) {
            return Long.compare(o.changedAt, this.changedAt);
        }
        
        @Override
        public boolean equals(final Object obj) {
            return obj instanceof Name && this.name.equalsIgnoreCase(((Name)obj).getName()) && this.changedAt == ((Name)obj).changedAt;
        }
        
        @Override
        public int hashCode() {
            return Objects.hash(this.name, this.changedAt);
        }
    }
}

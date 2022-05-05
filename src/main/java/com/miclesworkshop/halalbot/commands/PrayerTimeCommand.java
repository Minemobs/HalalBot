package com.miclesworkshop.halalbot.commands;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.miclesworkshop.halalbot.HalalBot;
import com.miclesworkshop.halalbot.utils.LevenshteinDistance;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class PrayerTimeCommand extends AbstractCommands {

    private static final String BASE_URL = "https://dailyprayer.abdulrcs.repl.co/api/%s";

    public PrayerTimeCommand(HalalBot bot) {
        super(bot);
    }

    @Override
    protected void executeCommand(Server server, User user, ServerTextChannel channel, Message message, String channelName, String cmd, String[] args) {
        if(!cmd.equalsIgnoreCase("*prayertime")) return;
        if (args.length < 1) {
            channel.sendMessage("__**Usage:**__ " + cmd + " `Paris` OR `Paris Fajr` OR " + " `Paris 1`");
            return;
        }

        String city = args[0];
        JsonObject object = new Gson().fromJson(getJson(city), JsonObject.class);
        JsonObject today = object.getAsJsonObject("today");
        if(args.length >= 2) {
            try {
                int index = Integer.parseInt(args[1]);
                if(index < 1 || index > 5) {
                    channel.sendMessage("__**Usage:**__ " + cmd + " `Paris` OR `Paris Fajr` OR " + " `Paris 1`");
                    return;
                }
                Map.Entry<String, JsonElement> entry = new ArrayList<>(today.entrySet()).get(index - 1);
                channel.sendMessage(
                        new EmbedBuilder()
                                .setTitle("Prayer Time for " + object.get("city").getAsString())
                                .addField(entry.getKey(), entry.getValue().getAsString(), false));
            } catch (NumberFormatException ignored) {
                String selected = args[1];
                Optional<String> first = today.keySet().stream().filter(key -> LevenshteinDistance.getDistance(key, selected) > .5).max(Comparator.naturalOrder());
                if(first.isPresent()) {
                    String prayer = today.get(first.get()).getAsString();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Prayer Time for " + object.get("city").getAsString());
                    builder.addField(first.get(), prayer);
                    channel.sendMessage(builder);
                } else {
                    channel.sendMessage("__**Usage:**__ " + cmd + " `Paris Isha'a` OR `Paris Fajr`");
                }
            }
        } else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Prayer Time for " + object.get("city").getAsString());
            for(Map.Entry<String, JsonElement> entry : today.entrySet()) {
                builder.addField(entry.getKey(), entry.getValue().getAsString(), false);
            }
            channel.sendMessage(builder);
        }
    }

    private String getJson(String city) {
        try(InputStream is = new URL(String.format(BASE_URL, city)).openStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            return reader.lines().reduce("", (acc, line) -> acc + line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.miclesworkshop.halalbot.commands;

import com.miclesworkshop.halalbot.HalalBot;
import com.miclesworkshop.halalbot.object.Ayahs;
import com.miclesworkshop.halalbot.object.Surahs;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.Arrays;

public class FrenchQuranCommand extends AbstractCommands {

    private final Surahs[] surahs;

    public FrenchQuranCommand(HalalBot bot) {
        super(bot);
        this.surahs = bot.getSurahs();
    }

    @Override
    protected void executeCommand(Server server, User user, ServerTextChannel channel, Message message, String channelName, String cmd, String[] args) {
        if(!cmd.equals("*fquran")) return;

        if (args.length < 2) {
            sendUsage(channel, cmd);
            return;
        }

        int surahNum;
        int from;
        int to;

        try {
            surahNum = Integer.parseInt(args[0]);
            from = Integer.parseInt(args[1]);
            to = args.length > 2 ? Integer.parseInt(args[2]) : from;
        } catch (NumberFormatException e) {
            sendUsage(channel, cmd);
            return;
        }

        if (surahNum < 1 || surahNum > surahs.length) {
            channel.sendMessage("Surah #" + surahNum + " not found");
            return;
        }

        Surahs surah = surahs[surahNum - 1];

        if (to < from) {
            channel.sendMessage("Second ayah can't be less than first ayah.");
            return;
        }

        if (from < 1) {
            channel.sendMessage("Ayah can't be less than 1");
            return;
        }

        int ayahCount = surah.getAyahs().length;
        if (to > ayahCount) {
            channel.sendMessage("Surah only has " + ayahCount + " ayahs");
            return;
        }

        Ayahs[] ayahs = Arrays.copyOfRange(surah.getAyahs(), from - 1, to);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Surah " + surahNum + ": " + surah.getName());
        System.out.println(surah.getName());
        for (int i = 0; i < ayahs.length; i++) {
            Ayahs ayah = ayahs[i];
            embed.addField("Ayah " + (i + 1), ayah.getTextFR() + "\n" + ayah.getTextAR() + "\n\n", false);
            //System.out.println(ayah.getText());
        }
        channel.sendMessage(embed);
    }

    private void sendUsage(ServerTextChannel channel, String cmd) {
        channel.sendMessage("__**Usage:**__ " + cmd + " surah ayah OR " + " surah ayah1 ayah2");
    }
}

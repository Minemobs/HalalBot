package com.miclesworkshop.halalbot.commands;

import com.miclesworkshop.halalbot.HalalBot;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class HelpCommand extends AbstractCommands {
    public HelpCommand(HalalBot bot) {
        super(bot);
    }

    @Override
    protected void executeCommand(Server server, User user, ServerTextChannel channel, Message message,
                                  String channelName, String cmd, String[] args) {
        if (!cmd.equalsIgnoreCase("*help")) {
            return;
        }

        message.delete();

        user.sendMessage("Commands:\n" +
                "\n" +
                "__***Approval***__\n" +
                "`*approve [role]` **:::** Accepts this channel's applicant as the given role\n" +
                "`*ban [reason]` **:::** Bans the channel's applicant. Reason must be provided.\n" +
                "`*apply` **:::** Used by users to apply.\n" +
                "`*close [reason]` **:::** Close the channel's application. Reason must be provided.\n" +
                "`*addmod [users]` **:::** Add the given users as approval moderators.\n" +
                "`*removemod [users]` **:::** Remove the given users as approval moderators.\n" +
                "`*listroles` **:::** List all valid approval roles for `*approve`.\n" +
                "`*addrole` **:::** Add a role for `*approve`.\n" +
                "`*removerole` **:::** Remove a role for `*approve`.\n" +
                "\n" +
                "__***Jail***__\n" +
                "`*jail [user(s)]` **:::** Send the given user(s) to jail.\n" +
                "`*unjail [user(s)]` **:::** Remove the given user(s) from jail.");
    }
}

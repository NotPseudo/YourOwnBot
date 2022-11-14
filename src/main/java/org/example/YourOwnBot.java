package org.example;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.interaction.callback.InteractionOriginalResponseUpdater;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionException;

/**
 * A very basic Discord Bot written with JavaCord
 * <p>
 * This bot is meant to be used to give you the Discord Active Developer Badge after running a global slash command
 * Set your bot token below in the main() method and run it to activate your bot.
 */
public class YourOwnBot {

    /**
     * Set the token properly and run this method to activate the bot
     */
    public static void main(String[] args) {
        String token = "YOUR_TOKEN_HERE";
        try {
            DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();
            SlashCommand.with("badge", "If you own the bot, run this command for the badge").createGlobal(api).join();
            api.addSlashCommandCreateListener(event -> {
                        SlashCommandInteraction interaction = event.getSlashCommandInteraction();
                        if (interaction.getCommandName().equals("badge")) {
                            InteractionOriginalResponseUpdater responseUpdater = interaction.respondLater(true).join();
                            responseUpdater.setContent("Nice! Now wait about 24 hours for Discord to recognize your global slash command. Then visit https://discord.com/developers/active-developer to claim your badge.").update();
                            new Timer().schedule(
                                    new TimerTask() {
                                        @Override
                                        public void run() {
                                            api.disconnect();
                                        }
                                    }, 5000
                            );
                        }
                    }
            );
        } catch (CompletionException ce) {
            System.out.println("\u001B[31mThere was an issue trying to log in to your bot");
        }
    }

}
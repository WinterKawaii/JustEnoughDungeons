package com.portalthree.jed;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRPC {

    private static boolean running;

    public static void start(){
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser user) {
                System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
                update("Booting up...", "");
            }
        }).build();
        String applicationId = "784602948969824257";
        net.arikia.dev.drpc.DiscordRPC.discordInitialize(applicationId, handlers, true);

        new Thread("Discord-RPC-Callback"){
            @Override
            public void run() {
                while (running){
                    net.arikia.dev.drpc.DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public static void shutdown(){
        running = false;
        net.arikia.dev.drpc.DiscordRPC.discordShutdown();
    }

    public static void update(String firstline, String secondline){
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondline);
        builder.setDetails(firstline);
        net.arikia.dev.drpc.DiscordRPC.discordUpdatePresence(builder.build());
    }
}

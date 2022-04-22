package com;

import com.handler.MessageHandler;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class DiscordBot extends ListenerAdapter {
  public static void main(String[] args) throws LoginException {
    JDA jdaObject = JDABuilder.createLight(
            "OTY3MDk0NjI4NjIyOTI1OTI5.YmLTDw.2pgZnNCcw5e0IFPZnAJzQBw4Zh0")
        .enableIntents(GatewayIntent.GUILD_MEMBERS)
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .setActivity(Activity.playing("Type !help"))
        .build();

    jdaObject.addEventListener(new MessageHandler(jdaObject));
  }
}

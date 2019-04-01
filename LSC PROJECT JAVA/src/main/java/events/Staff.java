package events;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sun.misc.MessageUtils;

import java.util.List;




public class Staff extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member test;

        if (event.getMember().hasPermission(Permission.KICK_MEMBERS))
        {



            if (msg[0].equalsIgnoreCase("!clear")) {



            }

            if (msg[0].equalsIgnoreCase("!lock")) {
                if (msg.length > 1)
                {


                }


            }


            if (msg[0].equalsIgnoreCase("!mute")) {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();

                    Member user = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                    long userId = user.getUser().getIdLong();
                    Member mem = event.getGuild().getMemberById(userId);
                    Role role = event.getMessage().getMentionedRoles().get(0);



                    guild.getController().addSingleRoleToMember(mem, role).queue();
                    //event.getChannel().sendMessage(role + "").queue();

                }

            }


            if (msg[0].equalsIgnoreCase("!temp")) {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();
                    for (User user : mentionedUsers) {
                        event.getGuild().getController().ban(user.getId(), 1, "Temp").queue();
                        String logMessage = event.getAuthor().getName() + " has banned the player: " + user.getAsMention();
                        event.getChannel().sendMessage(logMessage).queue();
                    }
                }

            }


            if (msg[0].equalsIgnoreCase("!kick")) {

                if (msg.length > 1) {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();
                    for (User user : mentionedUsers) {
                        event.getGuild().getController().kick(user.getId()).queue();
                        String logMessage = event.getAuthor().getName() + " has kicked the player: " + user.getAsMention();
                        event.getChannel().sendMessage(logMessage).queue();
                    }

                } else {
                    event.getChannel().sendMessage("PLEASE PROVIDE A NAME").queue();
                }

            }

        }




    }
}

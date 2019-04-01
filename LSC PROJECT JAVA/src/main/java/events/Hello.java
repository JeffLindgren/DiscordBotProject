package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Hello extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase("Hello"))
        {
            event.getChannel().sendMessage("Hey " + event.getMember().getAsMention()).queue();
            //event.getChannel().sendMessage(event.getAuthor().getAvatarUrl()).queue();
            //event.getChannel().getMembers().
        }


    }
}

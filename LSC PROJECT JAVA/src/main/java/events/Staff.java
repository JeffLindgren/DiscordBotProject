package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMuteEvent;
import net.dv8tion.jda.api.events.message.MessageDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.update.GenericRoleUpdateEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sun.misc.MessageUtils;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;




public class Staff extends ListenerAdapter
{

    public void onGuildUnban(GuildUnbanEvent event) {
        String unbanMessage = "The ban hammer has been lifted on: " + event.getUser().getAsMention();
        event.getJDA().getTextChannelById("562312040287305885").sendMessage(unbanMessage).queue();
    }
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member test;

        if (event.getMember().hasPermission(Permission.KICK_MEMBERS))
        {
            if (msg[0].equalsIgnoreCase("!clear")) {
                MessageHistory history = event.getChannel().getHistory();
                List<Message> msgs;

                if (msg.length == 1)
                {
                    msgs = history.retrievePast(2).complete();
                    for(int x = 0; x < msgs.size(); ++x)
                    {
                        msgs.get(x).delete().queue();
                    }
                    event.getChannel().sendMessage("MESSAGE DELETED").queue();
                }

                if(msg.length == 2)
                {
                    if (Integer.parseInt(msg[1])  >= 1 && Integer.parseInt(msg[1]) <= 100)
                    {
                        msgs = history.retrievePast(Integer.parseInt(msg[1])).complete();
                        for(int x = 0; x < msgs.size(); ++x)
                        {
                            msgs.get(x).delete().queue();
                        }

                        event.getChannel().sendMessage("ALL MESSAGES DELETED").queue();

                    }
                }
            }

            if (msg[0].equalsIgnoreCase("!staff")) {
                if (msg.length > 1)
                {
                    Role role = event.getMessage().getMentionedRoles().get(0);
                    String listStaff = "";
                    int x;


                    List<Member> test1 = event.getGuild().getMembersWithRoles(role);


                    event.getChannel().sendMessage(Arrays.toString(test1.toArray())).queue();
                }
            }
            if (msg[0].equalsIgnoreCase("!mute")) {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();

                    Member user = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                    long userId = user.getUser().getIdLong();
                    Member mem = event.getGuild().getMemberById(userId);
                    Role role = event.getGuild().getRolesByName("MUTE", true).get(0);
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

            if (msg[0].equalsIgnoreCase("!info+")) {

                if (msg.length > 1) {
                    test = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                } else {
                    test = ((Guild) guild).getMember(event.getMember().getUser());
                }
                String NAME = test.getEffectiveName();
                String PERMS = test.getPermissions().toString();
                String TAG = test.getUser().getName() + "#" + test.getUser().getDiscriminator();
                String ID = test.getUser().getId();
                String STATUS = test.getOnlineStatus().getKey();
                String ROLES = "";
                String GAME;
                String ACT = test.getActivities().toString();
                String AVATAR = test.getUser().getAvatarUrl();
                String GUILDDATE = test.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME);
                String JOINDATE = test.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME);


                if (AVATAR == null) {
                    AVATAR = "No avatar";
                }
                for (Role role : member.getRoles()) {
                    ROLES += role.getName() + ", ";
                }
                if (ROLES.length() > 0) {
                    ROLES = ROLES.substring(0, ROLES.length() - 2);
                } else {
                    ROLES = "NO ROLES";


                }

                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Color.CYAN)
                        .setDescription(":spy: UserInfo for " + NAME + " (" + TAG + ")")
                        .addField("Name/Nick", NAME, false)
                        .addField("User tag", TAG, false)
                        .addField("User id", ID, false)
                        .addField("Current status", STATUS, false)
                        .addField("Permissions", PERMS, false)
                        .addField("Activities", ACT, false)
                        .addField("Guild joined", GUILDDATE, false)
                        .addField("Roles", ROLES, false)
                        .addField("Dicord joined", JOINDATE, false)
                        .addField("Avatar url", AVATAR, false);

                if (AVATAR != "No avatar") {
                    embed.setThumbnail(AVATAR);


                }

                event.getChannel().sendMessage(embed.build()).queue();
            }
        }
    }

    public void onGuildVoiceMute(GuildVoiceMuteEvent event) {
        if (event.isMuted()) {
            String logMessage = "User ``" + event.getMember().getEffectiveName() + "`` has been server muted.";
            event.getJDA().getTextChannelById("562312040287305885").sendMessage(logMessage).queue();
        } else {
            String logMessage = "User ``" + event.getMember().getEffectiveName() + "`` has been un-muted";
            event.getJDA().getTextChannelById("562312040287305885").sendMessage(logMessage).queue();
        }

    }


    public void onGenericRoleUpdate(GenericRoleUpdateEvent event) {
        event.getEntity();
        String message = event.getEntity() + "\n\n" +event.getPropertyIdentifier() + "\n\n" + event.getOldValue() + "\n\n" +event.getNewValue();

        event.getJDA().getTextChannelById("561728191845236742").sendMessage(message).queue();

    }



    public void onUserUpdateName(UserUpdateNameEvent event) {
        String changeNameMessage = "User ``" + event.getUser().getName() + "`` is now known as ``" + (event.getNewName() == null ? event.getUser().getAsMention(): event.getNewName());
        event.getJDA().getTextChannelById("561728191845236742").sendMessage(changeNameMessage).queue();
    }

    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {
        String changeNameMessage = "User ``" + event.getUser().getName() + "`` is now known as " + (event.getNewNick() == null ? event.getUser().getAsMention() : event.getNewNick());
        event.getJDA().getTextChannelById("561728191845236742").sendMessage(changeNameMessage).queue();
    }
}

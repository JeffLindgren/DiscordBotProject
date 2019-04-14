package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Admin extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member test;


        if (event.getMember().hasPermission(Permission.BAN_MEMBERS))
        {
            if (msg[0].equalsIgnoreCase("!role"))
            {

            }


            if (msg[0].equalsIgnoreCase("!addrole"))
            {
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


            if (msg[0].equalsIgnoreCase("!remrole"))
            {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();

                    Member user = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                    long userId = user.getUser().getIdLong();
                    Member mem = event.getGuild().getMemberById(userId);
                    Role role = event.getMessage().getMentionedRoles().get(0);



                    //NEED TO FIX THIS



                    guild.getController().removeSingleRoleFromMember(mem, role);
                    //event.getChannel().sendMessage(role + "").queue();

                }

            }


            if (msg[0].equalsIgnoreCase("!setnick"))
            {
                if (msg.length > 2) {
                    test = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                } else {
                    test = ((Guild) guild).getMember(event.getMember().getUser());
                }



                String newName = msg[msg.length - 1];
                event.getGuild().getController().setNickname(test, msg[2]).queue();
                event.getChannel().sendMessage("CHANGED").queue();


            }


            if (msg[0].equalsIgnoreCase("!delnick"))
            {
                if (msg.length > 1) {
                    test = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                } else {
                    test = ((Guild) guild).getMember(event.getMember().getUser());
                }

                String newName = test.getUser().getName();
               event.getGuild().getController().setNickname(test, newName).queue();

            }

            if (msg[0].equalsIgnoreCase("!info++"))
            {
                if (msg.length > 1) {
                    test = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                } else {
                    test = ((Guild) guild).getMember(event.getMember().getUser());
                }
                String VOICE = test.getVoiceState().toString();
                String NAME = test.getEffectiveName();
                String PERMS = test.getPermissions().toString();
                String SERVERS = test.getUser().getMutualGuilds().toString();
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
                        .addField("Name/Nick", NAME, true)
                        .addField("User tag", TAG, true)
                        .addField("User id", ID, true)
                        .addField("Current status", STATUS, true)
                        .addField("Permissions", PERMS, true)
                        .addField("Voice", VOICE, true)
                        .addField("Activities", ACT, true)
                        .addField("Guild joined", GUILDDATE, true)
                        .addField("Roles", ROLES, true)
                        .addField("Dicord joined", JOINDATE, true)
                        .addField("Servers", SERVERS, true)
                        .addField("Avatar url", AVATAR, false);

                if (AVATAR != "No avatar") {
                    embed.setThumbnail(AVATAR);


                }

                event.getChannel().sendMessage(embed.build()).queue();


            }


            if (msg[0].equalsIgnoreCase("!rank"))
            {
                if (msg.length > 1)
                {
                    Role role = event.getMessage().getMentionedRoles().get(0);
                    String listStaff = "";
                    int x;


                    List<Member> test1 = event.getGuild().getMembersWithRoles(role);


                    event.getChannel().sendMessage(Arrays.toString(test1.toArray())).queue();
                }

            }

            if (msg[0].equalsIgnoreCase("!lock"))
            {



                //String role = event.getMessage().getMentionedRoles().get(0).toString();

                if (event.getJDA().getRoleById("564604461503217723").hasPermission(Permission.MESSAGE_WRITE))
                {
                    guild.getRolesByName("MEMBER", true).get(0).getPermissions().remove(Permission.MESSAGE_WRITE);
                }

                else
                {
                    guild.getRolesByName("MEMBER", true).get(0).getPermissions().add(Permission.MESSAGE_WRITE);
                    event.getChannel().sendMessage("UNLOCKED").queue();
                }

            }



            if (msg[0].equalsIgnoreCase("!admins"))
            {
                if (msg.length > 1)
                {
                    Role role = event.getMessage().getMentionedRoles().get(0);
                    String listStaff = "";
                    //String te = "R:Admin(560519529592127488)";


                    System.out.println(role);
                    int x;


                    List<Member> test1 = event.getGuild().getMembersWithRoles();


                    event.getChannel().sendMessage(Arrays.toString(test1.toArray())).queue();
                }

            }

            if (msg[0].equalsIgnoreCase("!createrole"))
            {
                //Role role;

                event.getGuild().getController().createRole().setColor(Color.GREEN).setName(msg[1]).setMentionable(true).queue();


            }


            if (msg[0].equalsIgnoreCase("!delrole"))
            {
                Role role = event.getMessage().getMentionedRoles().get(0);
                role.delete().queue();
                event.getGuild().getTextChannelById("561728191845236742").sendMessage(role.getName() + " has beened removed from the server").queue();


            }

            if (msg[0].equalsIgnoreCase("!ban"))
            {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();
                    for (User user : mentionedUsers) {
                        event.getGuild().getController().ban(user.getId(), 5, "PERM").queue();
                        String logMessage = event.getAuthor().getName() + " has banned the player: " + user.getAsMention();
                        event.getChannel().sendMessage(logMessage).queue();
                    }
                }

            }




            if (msg[0].equalsIgnoreCase("!default"))
            {
                if (msg.length > 1)
                {
                    List<User> mentionedUsers = event.getMessage().getMentionedUsers();

                    Member user = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
                    long userId = user.getUser().getIdLong();
                    Member mem = event.getGuild().getMemberById(userId);
                    List<Role> role = mem.getRoles();


                    for (int x = 0; x < role.size(); ++x)
                    {
                        guild.getController().removeSingleRoleFromMember(mem, role.get(x)).queue();
                    }

                    guild.getController().addSingleRoleToMember(mem, guild.getRolesByName("MEMBER", true).get(0)).queue();
                    //event.getChannel().sendMessage(role + "").queue();

                }


            }




        }

    }
}

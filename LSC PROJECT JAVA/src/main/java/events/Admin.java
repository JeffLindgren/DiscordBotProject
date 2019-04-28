package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
            if (msg[0].equalsIgnoreCase("!loadrole") && member.hasPermission(Permission.ADMINISTRATOR))
            {

                try {
                    String url = "https://api.myjson.com/bins/1dtqik";
                    URL obj = new URL(url);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");

                    con.setRequestProperty("User-Agent", "Mozilla/5.0");

                    int responseCode = con.getResponseCode();
                    System.out.println("\nSending get request to: " + url);
                    System.out.println("Code: " + responseCode);

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null){
                        response.append(inputLine);
                    }
                    in.close();
                    System.out.println(response.toString());
                    JSONObject roles = new JSONObject(response.toString());

                    //JSON roles = new JSONArray((response.toString()));

                    String color = roles.getJSONArray("roles").getJSONObject(0).getJSONObject(msg[1]).getString("color");
                    Boolean men = roles.getJSONArray("roles").getJSONObject(0).getJSONObject(msg[1]).getBoolean("men");



                    event.getGuild().getController().createRole().setColor(Color.cyan).setName(msg[2]).setMentionable(men).queue();
                    event.getChannel().sendMessage("Loaded").queue();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //event.getGuild().getController().createRole().setColor(Color.GREEN).setName(msg[2]).setMentionable(true).setPermissions().queue();



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
                    event.getChannel().sendMessage(role.getName() + " Has been added to " + mem.getUser().getAsMention()).queue();

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
                    guild.getController().removeSingleRoleFromMember(mem, role).queue();
                    event.getChannel().sendMessage(role.getName() + " Has been removed from " + mem.getUser().getAsMention()).queue();

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
                for (Role role : test.getRoles()) {
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
                if (true)
                {
                    Role role = guild.getRolesByName("ADMIN", true).get(0);
                    String listStaff = "";

                    List<Member> test1 = event.getGuild().getMembersWithRoles(role);


                    event.getChannel().sendMessage(Arrays.toString(test1.toArray())).queue();
                }

            }

            if (msg[0].equalsIgnoreCase("!createrole"))
            {
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

package events;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.JDA;


import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.format.DateTimeFormatter;


import org.json.JSONArray;
import org.json.JSONObject;


public class Basic extends ListenerAdapter
{
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] msg = event.getMessage().getContentRaw().split(" ");
        Member member = event.getMember();
        Guild guild = event.getGuild();
        Member test;
        Member user;

        boolean status = true;

        //ping
        if (msg[0].equalsIgnoreCase("!ping")) {
            String re = "Ping: `" + event.getJDA().getGatewayPing() + "ms` " + ((Member) member).getAsMention();
            event.getChannel().sendMessage(re).queue();


        }

        //Live notifications

        if (msg[0].equalsIgnoreCase("!live") && event.getMember().hasPermission(Permission.ADMINISTRATOR))
        {
            while (true)
            {


                try {
                    String url = "https://api.twitch.tv/kraken/streams/vader?client_id=077cnffgasxn3hk2mlaiqk2gx0d9j1";
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

                    JSONObject myResponse = new JSONObject(response.toString());

                    if (myResponse.get("stream") != null && status == true)
                    {
                        //event.getChannel().sendMessage("TEST").queue();
                        EmbedBuilder embed = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setDescription(myResponse.getJSONObject("stream").getJSONObject("channel").getString("display_name"))
                                .addField("Title:", myResponse.getJSONObject("stream").getJSONObject("channel").getString("status"), true)
                                .addField("Game:", myResponse.getJSONObject("stream").getJSONObject("channel").getString("game"),true)
                                .addField("Language:", myResponse.getJSONObject("stream").getJSONObject("channel").getString("language"), true)
                                .addField("Viewers:", myResponse.getJSONObject("stream").get("viewers").toString(), true)
                                .addField("URL:", "https://www.twitch.tv/", true)
                                .setThumbnail(myResponse.getJSONObject("stream").getJSONObject("channel").getString("logo"))
                                .setImage(myResponse.getJSONObject("stream").getJSONObject("preview").getString("large"));


                        event.getChannel().sendMessage(embed.build()).queue();
                        event.getChannel().sendMessage("@everyone").queue();
                        status = false;
                        System.out.println("Streamer is live");

                    } else if(myResponse.getJSONArray("stream") != null && status == false){
                        System.out.println("Streamer is live");
                    }

                    else
                    {
                        System.out.println("NOT LIVE");
                        status = true;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(500000);
                } catch (Exception e) {}


            }
        }

        //event.getChannel().sendMessage(event.getMessage().toString()).queue();

        if (msg[0].equalsIgnoreCase("!twitch"))
        {
            if (msg.length == 1)
            {
                event.getChannel().sendMessage("Please provide a Twitch name!").queue();
            }

            if (msg.length == 2)
            {

                try {
                    String url = "https://api.twitch.tv/kraken/channels/"+ msg[1]+"?client_id=077cnffgasxn3hk2mlaiqk2gx0d9j1";
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

                    JSONObject myResponse = new JSONObject(response.toString());

                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setDescription(myResponse.getString("display_name"))
                            .addField("Title:", myResponse.getString("status"), true)
                            .addField("Game:", myResponse.getString("game"),true)
                            .addField("Language:", myResponse.getString("language"), true)
                            .addField("Total view:", myResponse.get("views").toString(), true)
                            .addField("Followers:", myResponse.get("followers").toString(), true)
                            .addField("created_at", myResponse.getString("created_at"), true)
                            .addField("URL:", "https://www.twitch.tv/" + msg[1], true)
                            .setThumbnail(myResponse.getString("logo"))
                            .setImage(myResponse.getString("video_banner"));


                    event.getChannel().sendMessage(embed.build()).queue();




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        if (msg[0].equalsIgnoreCase("-ytStat"))
        {


            if (msg.length == 1)
            {
                event.getChannel().sendMessage("Please provide a link!").queue();
            }

            if (msg.length == 3)
            {
                //Nothing
            }

            if (msg.length == 2)
            {
                String[] code = msg[1].split("v=");
                try {
                    String url = "https://www.googleapis.com/youtube/v3/videos?id="+ code[1]  +"&key=AIzaSyC_nF4W7XKnCRYufB89ZGT5K2CU-k-FHfo&part=statistics,snippet";
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

                    JSONObject myResponse = new JSONObject(response.toString());

                    //JSONArray itemsArray = new JSONArray(myResponse.optJSONArray("items"));

                    JSONArray items = new JSONArray(myResponse.getJSONArray("items").toString());

                    System.out.println("HI: "+ items);
                    items.toString();
                    JSONObject stat = new JSONObject(items.getJSONObject(0).get("statistics").toString());
                    JSONObject snip = new JSONObject(items.getJSONObject(0).get("snippet").toString());
                    System.out.println("TEST: " + stat);

                    //JSONObject videoInfo = new JSONObject(myResponse.getString("items"));
                    //System.out.println("Location :" + myResponse.getString("name"));
                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.green)
                            .addField("Views:", stat.getString("viewCount"),false)
                            .addField("Likes:", stat.getString("likeCount"), true)
                            .addField("Dislikes", stat.getString("dislikeCount"), true)
                            .addField("Number of comments:", stat.getString("commentCount"), true)
                            .addField("Published:", snip.getString("publishedAt"), true)
                            .addField("Description:", snip.getString("description").substring(0, 100) + " ...", false)
                            .setImage(snip.getJSONObject("thumbnails").getJSONObject("standard").getString("url"))
                            .setDescription("YouTube stats for " + snip.getString("title"));

                    event.getChannel().sendMessage(embed.build()).queue();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=91SyPGgELiU&format=json
        //Working url thing
        //https://www.googleapis.com/youtube/v3/videos?id=C0DPdy98e4c&key=AIzaSyDsbFsdH2GR4Ur05IpfsP3565WGZWvIqpU&part=statistics
        //https://www.googleapis.com/youtube/v3/videos?id=C0DPdy98e4c&key=AIzaSyDsbFsdH2GR4Ur05IpfsP3565WGZWvIqpU&part=statistics
        //api.openweathermap.org/data/2.5/weather?zip=94040,us
        //http://api.openweathermap.org/data/2.5/weather?zip=94040,us&appid=32ff9631e6a7a4d1e354d92af3ec01a3

        if (msg[0].equalsIgnoreCase("!weather"))
        {
            if (msg.length == 1)
            {
                event.getChannel().sendMessage("Please provide a zip code!").queue();
            }
            if (msg.length == 2)
            {
                try {
                    String url = "http://api.openweathermap.org/data/2.5/weather?zip="+ msg[1]+ ",us&appid=32ff9631e6a7a4d1e354d92af3ec01a3";
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

                    JSONObject myResponse = new JSONObject(response.toString());
                    //System.out.println("Location :" + myResponse.getString("name"));
                    EmbedBuilder embed = new EmbedBuilder()
                            .setColor(Color.green)
                            .setDescription("Weather for " + myResponse.getString("name"))
                            .addField("Location", myResponse.getString("name"), false)
                            .addField("Wind", myResponse.getJSONObject("wind").toString(), true)
                            .addField("Pressure", myResponse.getJSONObject("main").toString(), true)
                            .addField("Visibility", myResponse.get("visibility").toString(), true);
                    event.getChannel().sendMessage(embed.build()).queue();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (msg[0].equalsIgnoreCase("!website")) {
            event.getChannel().sendMessage("http://www.jefflindgren.me").queue();
        }

        if (msg[0].equalsIgnoreCase("!av")) {
            String userURL = "";
            if (msg.length == 1)
            {
                userURL = event.getMember().getUser().getAvatarUrl();

            } if (msg.length == 2){
                if (event.getMessage().getMentionedUsers().size() == 1)
                {
                    userURL = event.getMessage().getMentionedUsers().get(0).getAvatarUrl();
                }

            }

            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.cyan)
                    .setImage(userURL);
            event.getChannel().sendMessage(embed.build()).queue();
        }

        if (msg[0].equalsIgnoreCase("!members")) {
            int size = event.getGuild().getMembers().size();
            event.getChannel().sendMessage("`" +size + "` Total members").queue();
        }

        if (msg[0].equalsIgnoreCase("!enlarge") || msg[0].equalsIgnoreCase("!en")) {

            if (event.getMessage().getEmotes().size() == 0)
            {

            } else {
                Emote emote = event.getMessage().getEmotes().get(0);
                //emote.getAsMention();
                EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Color.cyan)
                        .setDescription(emote.getAsMention())
                        .setImage(emote.getImageUrl());
                event.getChannel().sendMessage(embed.build()).queue();
            }

        }


        if (msg[0].equalsIgnoreCase("!test")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR))
            {
                event.getChannel().sendMessage("YOU CAN DO IT").queue();

            }
        }


        if (msg[0].equalsIgnoreCase("!games")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.green)
                    .setDescription("!game commands")
                    .addField("!8ball", "Ask me a question", false);
                    //.addField("!info @<name>", "Info about the user", false)

            embed.setThumbnail("https://cdn.discordapp.com/avatars/539305238423928854/4d372140d87bb94d3f22d2e41bfe5c8c.png");

            event.getChannel().sendMessage(embed.build()).queue();
        }

        if (msg[0].equalsIgnoreCase("!rules")) {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.green)
                    .setDescription("The Rules")
                    .addField("01.", "No Text/Voice/Reaction Spam", false)
                    .addField("02.", "No Discord Links", false)
                    .addField("03.", "No Raiding", false)
                    .addField("04.", "No Racism, Harassment, Hateful Conduct, Homophobia/Transphobia (including slurs).", false)
                    .addField("05.", "No Selfbots/Doxxing/Personal Information", false)
                    .addField("06.", "Do not @ moderators or admins unless you have a valid reason.", false)
                    .addField("07.", "No self-promotion or promotion of anyone else, unless given permission by an Admin.", false)
                    .addField("08.", "No graphic or pornographic content. NSFW content is not allowed anywhere on the server, as it violates Discord Partnership TOS.", false)
                    .addField("09.", "If a Mod or above tells you to stop, stop. If you do not, you will be put into the #deleted-channel .", false);
            //.addField("!info @<name>", "Info about the user", false)

            embed.setThumbnail("https://cdn.discordapp.com/avatars/539305238423928854/4d372140d87bb94d3f22d2e41bfe5c8c.png");

            event.getChannel().sendMessage(embed.build()).queue();

        }
        if (msg[0].equalsIgnoreCase("!8ball")) {
            if (msg.length < 2)
            {
                event.getChannel().sendMessage("Please provide a question.").queue();
            } else {
                int magic = (int) Math.ceil(Math.random() * 10);
                String x;
                switch (magic)
                {
                    case 1: x = "Yes.";
                        break;
                    case 2: x = "No.";
                        break;
                    case 3: x = "The odds are in favor.";
                        break;
                    case 4: x = "The odds are against you.";
                        break;
                    case 5: x = "Never.";
                        break;
                    case 6: x = "Definitely!";
                        break;
                    case 7: x = "Maybe.";
                        break;
                    case 8: x = "I don't think so.";
                        break;
                    case 9: x = "I'd say no.";
                        break;
                    case 10: x = "Probably.";
                        break;

                    default: x = "Try Again.";
                        break;
                }

                event.getChannel().sendMessage(event.getMember().getAsMention() + " " + x).queue();
            }
        }

        if (msg[0].equalsIgnoreCase("!roast")) {
            if (msg.length < 2)
            {
                event.getChannel().sendMessage("Please provide a user to roast").queue();
            } else {
                int magic = (int) Math.ceil(Math.random() * 10);
                String x;
                switch (magic) {
                    case 1:
                        x = "You look like something that I would draw with my left hand.";
                        break;
                    case 2:
                        x = "I refuse to have a battle of wits with somebody who is unarmed!";
                        break;
                    case 3:
                        x = "If I ever said anything to offend you, it was purely intentional.";
                        break;
                    case 4:
                        x = "I'm not saying that I hate you, but I would unplug your life support to charge my phone.";
                        break;
                    case 5:
                        x = "In spite of what it did to you, don't you love nature?";
                        break;
                    case 6:
                        x = "I've seen someone like you before, but I had to pay admission.";
                        break;
                    case 7:
                        x = "You have the perfect face for radio!";
                        break;
                    case 8:
                        x = "You're not as bad as people say. You're a whole lot worse.";
                        break;
                    case 9:
                        x = "I'm not sure what your problem is, but I'd be wiling to bet that it's hard to pronounce.";
                        break;
                    case 10:
                        x = "It's pretty easy to figure out when you're lying. Your lips are moving.";
                        break;

                    default:
                        x = "Try Again.";
                        break;
                }


                event.getChannel().sendMessage(x + " " + msg[1]).queue();
            }
        }
        if (msg[0].equalsIgnoreCase("!gif")) {

            int magic = (int) Math.ceil(Math.random() * 10);
            String x;
            switch (magic) {
                case 1:
                    x = "https://media.giphy.com/media/tXL4FHPSnVJ0A/giphy.gif";
                    break;
                case 2:
                    x = "https://media.giphy.com/media/26n6xBpxNXExDfuKc/giphy.gif";
                    break;
                case 3:
                    x = "https://media.giphy.com/media/tn1cGqW0xWyfm/giphy.gif";
                    break;
                case 4:
                    x = "https://media.giphy.com/media/DeIdA4WRBi1Ta/giphy.gif";
                    break;
                case 5:
                    x = "https://media.giphy.com/media/q3XzdvHpr0GPK/giphy-downsized-large.gif";
                    break;
                case 6:
                    x = "https://media.giphy.com/media/aeOE9ziteyk6c/giphy.gif";
                    break;
                case 7:
                    x = "https://media.giphy.com/media/aAjB8sanqY3eM/giphy-downsized-large.gif";
                    break;
                case 8:
                    x = "https://media.giphy.com/media/6CKwe5zXeRwyI/giphy-downsized-large.gif";
                    break;
                case 9:
                    x = "https://media.giphy.com/media/6FTee2VeZ8sak/giphy.gif";
                    break;
                case 10:
                    x = "https://media.giphy.com/media/aC28QCnj1HClW/giphy.gif";
                    break;

                default:
                    x = "https://media.giphy.com/media/4lLJIF1el6KWs/giphy.gif";
                    break;

                    //I will add more next week.
            }


            event.getChannel().sendMessage(x).queue();
        }

        if (msg[0].equalsIgnoreCase("!help"))
        {
            EmbedBuilder embed = new EmbedBuilder()
                    .setColor(Color.green)
                    .setDescription("!help commands")
                    .addField("!help", "Help", false)
                    .addField("!info @<name>", "Info about the user", false)
                    .addField("!games", "Games you can play" , false)
                    .addField("!website", "My website", false)
                    .addField("!members", "Total number of people on the server", false)
                    .addField("!gif", "Random gif", false);

            embed.setThumbnail("https://cdn.discordapp.com/avatars/539305238423928854/4d372140d87bb94d3f22d2e41bfe5c8c.png");

            event.getChannel().sendMessage(embed.build()).queue();

        }

        //info
        if (msg[0].equalsIgnoreCase("!info")) {

            if (msg.length > 1) {
                test = ((Guild) guild).getMember(event.getMessage().getMentionedUsers().get(0));
            } else {
                test = ((Guild) guild).getMember(event.getMember().getUser());
            }
            String NAME = test.getEffectiveName();

            String TAG = test.getUser().getName() + "#" + test.getUser().getDiscriminator();
            String ID = test.getUser().getId();
            String STATUS = test.getOnlineStatus().getKey();
            String ROLES = "";
            String GAME;
            String AVATAR = test.getUser().getAvatarUrl();
            String GUILDDATE = test.getTimeJoined().format(DateTimeFormatter.RFC_1123_DATE_TIME);
            //String JOINDATE = test.getUser().getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME);


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
                    //.addField("Current game", GAME, false)
                    //.addField("Guild joined", GUILDDATE, false)
                    .addField("Roles", ROLES, false);
                    //.addField("Dicord joined", JOINDATE, false)
                    //.addField("Avatar url", AVATAR, false);

            if (AVATAR != "No avatar") {
                embed.setThumbnail(AVATAR);


            }

            event.getChannel().sendMessage(embed.build()).queue();
        }
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        JDA jda = event.getJDA();
        long responseNumber = event.getResponseNumber();

        Member member1 = event.getMember();
        Guild guild = event.getGuild();
        int serverSize = event.getGuild().getMembers().size();

        event.getJDA().getTextChannelById("560528741626019840").sendMessage("Member " + event.getMember().getAsMention() + " has left " + guild.getName() + ".").queue();
        event.getJDA().getTextChannelById("560528741626019840").sendMessage("We now have **" + serverSize + "** members").queue();
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        JDA jda = event.getJDA();
        long responseNumber = event.getResponseNumber();

        Member member = event.getMember();
        Guild guild = event.getGuild();
        int serverSize = event.getGuild().getMembers().size();
        long userID = member.getUser().getIdLong();
        Member mem = event.getGuild().getMemberById(userID);
        Role role = event.getGuild().getRolesByName("MEMBER", true).get(0);

        event.getGuild().getController().addSingleRoleToMember(mem, role).queue();

        event.getJDA().getTextChannelById("560528741626019840").sendMessage("Member " + event.getMember().getAsMention() + " has joined " + guild.getName() + ".\n" + "We now have **" + serverSize + "** members").queue();
        //guild.getPublicChannel().sendMessage("Member `" + member.getEffectiveName() + "` has joined " + guild.getName() + ".").queue();
        //event.getJDA().getTextChannelById("560528741626019840").sendMessage("We now have **" + serverSize + "** members").queue();
    }
}

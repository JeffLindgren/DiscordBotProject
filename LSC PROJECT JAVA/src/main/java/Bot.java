import events.Admin;
import events.Basic;
import events.Hello;
import events.Staff;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;;


public class Bot {

    public static void main(String args[]) throws Exception
    {
        //JDA jda = new JDABuilder("NTM5MzA1MjM4NDIzOTI4ODU0.DzAbHA._LiX1qqryGh-KgeQ4lt2eZTD1do").build();
        //set game of the bot



        JDA jda = new JDABuilder(AccountType.BOT).setToken("NTM5MzA1MjM4NDIzOTI4ODU0.DzAbHA._LiX1qqryGh-KgeQ4lt2eZTD1do").build();
        //jda.getPresence().setGame(Game.of("Lolxd V4", "https://www.twitch.tv/killersgamin"));
        jda.addEventListener(new Hello());
        jda.addEventListener(new Basic());
        jda.addEventListener(new Staff());
        jda.addEventListener(new Admin());

    }
}


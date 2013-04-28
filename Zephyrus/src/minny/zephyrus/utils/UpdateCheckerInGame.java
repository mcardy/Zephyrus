package minny.zephyrus.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import minny.zephyrus.Zephyrus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class UpdateCheckerInGame implements Runnable {
	
	String url = "https://raw.github.com/minnymin3/Simple-Build/master/version";
	
	public boolean isUpdate;
	
	Zephyrus plugin;
	CommandSender sender;
	
	Thread t;
	
	public UpdateCheckerInGame(Zephyrus plugin, CommandSender sender){
		this.plugin = plugin;
		this.sender = sender;
		t = new Thread(this, "UpdateThread");
	    t.start();
	}
	

	public void run() {
		if (plugin.getConfig().getBoolean("UpdateChecker")) {
			String current = plugin.getDescription().getVersion();
			String readurl = url;
			Logger log = plugin.getLogger();
			try {
				log.info(sender.getName() + " is checking for a new version.");
				sender.sendMessage(ChatColor.GREEN + "Checking for updates...");
				URL url = new URL(readurl);
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = br.readLine()) != null) {
                    String line = str;
                    
                    if (isUpdate(current, line) == -1){
                    	sender.sendMessage(ChatColor.GREEN + "Update Message");
                        sender.sendMessage(ChatColor.GREEN + "New Version");
                        break;
                    } else if (isUpdate(current, line) == 1){
                    	sender.sendMessage(ChatColor.LIGHT_PURPLE + "Dev Build Message");
                        break;
                    } else if (isUpdate(current, line) == 0){
                    	sender.sendMessage(ChatColor.GREEN + "Up to date");
                    	break;
                    }
                }
                br.close();
                


			} catch (IOException e) {
				log.info("Was unable to check for update. URL may be invalid");
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Checking for updates is disabled. This can be fixed in the SimpleBuild config.");
		}
	}
	
	public int isUpdate(String str1, String str2){
		String[] vals1 = str1.split("\\.");
		String[] vals2 = str2.split("\\.");
		int i=0;
		while(i<vals1.length&&i<vals2.length&&vals1[i].equals(vals2[i])) {
		  i++;
		}

		if (i<vals1.length && i<vals2.length) {
		    int diff = Integer.valueOf(vals1[i]).compareTo(Integer.valueOf(vals2[i]));
		    return diff<0?-1:diff==0?0:1;
		}

		return vals1.length<vals2.length?-1:vals1.length==vals2.length?0:1;
	}
	
}

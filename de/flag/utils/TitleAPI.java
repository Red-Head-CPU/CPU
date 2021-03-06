package de.flag.utils;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;



public class TitleAPI {
	
	public static void sendTablist(Player p, String oben, String unten) {
		if(oben==null) oben = " ";
		if(unten==null) unten = " ";
		
		IChatBaseComponent tabHeader = ChatSerializer.a("{\"text\":\"" + oben + "\"}");
		IChatBaseComponent tabFooter = ChatSerializer.a("{\"text\":\"" + unten + "\"}");
		
		PacketPlayOutPlayerListHeaderFooter tabPacket = new PacketPlayOutPlayerListHeaderFooter();
		
		try {
			Field headerField = tabPacket.getClass().getDeclaredField("a");
			headerField.setAccessible(true);
			headerField.set(tabPacket, tabHeader);
			
			Field footerField = tabPacket.getClass().getDeclaredField("b");
			footerField.setAccessible(true);
			footerField.set(tabPacket, tabFooter);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			((CraftPlayer)p).getHandle().playerConnection.sendPacket(tabPacket);
		}
		
	}
	
	public static void sendTitel(Player p, String title, String subtitle, int fadein, int stay, int fadeout) {
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
		IChatBaseComponent Ititle = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + title + "\"}");
		IChatBaseComponent Isub = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + subtitle + "\"}");
		
		PacketPlayOutTitle titletime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,Ititle, fadein, stay, fadeout);
		PacketPlayOutTitle subtitletime = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES,Isub);
		PacketPlayOutTitle titlepacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,Ititle);
		PacketPlayOutTitle subpacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,Isub);
		
		connection.sendPacket(titletime);
		connection.sendPacket(subtitletime);
		connection.sendPacket(titlepacket);
		connection.sendPacket(subpacket);
		
	}
	
//	public static void sendAction(Player p, String message) {
//		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
//	}
	

}

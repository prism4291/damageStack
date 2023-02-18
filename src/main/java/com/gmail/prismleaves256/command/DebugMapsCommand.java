package com.gmail.prismleaves256.command;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.gmail.prismleaves256.Main;

import com.gmail.prismleaves256.util.MapUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.storage.MapData;

public class DebugMapsCommand extends CommandBase {
	private Main main;

	public DebugMapsCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/test' in chat
		return "debugmaps";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "mcforgesample debugmaps command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// define what happens when the command is executed
		// in this case a simple message is displayed
		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("DebugMapsCommand"));
		long timestamp = System.currentTimeMillis();
		File mapsFolder = new File(main.getSettings().getLogFolder(), "maps");
		mapsFolder.mkdirs();
		List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
		for (Entity e : entities) {
			if (!(e instanceof EntityItemFrame))
				continue;
			EntityItemFrame itemFrame = (EntityItemFrame) e;
			ItemStack item = itemFrame.getDisplayedItem();
			if (item == null || !item.getUnlocalizedName().equalsIgnoreCase("item.map"))
				continue;
			MapData map = Items.filled_map.getMapData(item, Minecraft.getMinecraft().theWorld);
			if (map == null || map.colors == null)
				continue;
			File image = new File(mapsFolder, timestamp + "-" + e.getPosition().getX() + "-" + e.getPosition().getY()
					+ "-" + e.getPosition().getZ() + ".png");
			try {
				ImageIO.write(new MapUtil(map).getImage(), "png", image);
			} catch (IOException ignored) {
			}
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}
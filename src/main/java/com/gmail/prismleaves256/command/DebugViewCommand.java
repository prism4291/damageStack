package com.gmail.prismleaves256.command;

import com.gmail.prismleaves256.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class DebugViewCommand extends CommandBase {
	private Main main;

	public DebugViewCommand(Main main) {
		this.main = main;
	}

	@Override
	public String getCommandName() {
		// the command can be used by typing '/debugview' in chat
		return "debugview";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "mcforgesample debugview command";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		if (args != null && args.length > 0 && args[0].equalsIgnoreCase("off")) {
			main.getSettings().putSetting("debugview", "false");
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug view disabled"));
		} else {
			main.getSettings().putSetting("debugview", "true");
			String debugFilter = "";
			if (args != null)
				for (int i = 0; i < args.length; i++)
					debugFilter += (debugFilter.isEmpty() ? "" : "|") + ".*" + args[i] + ".*";
			debugFilter = debugFilter.isEmpty() ? ".*" : debugFilter;
			main.getSettings().putSetting("debugViewFilter", debugFilter);
			Minecraft.getMinecraft().thePlayer
					.addChatMessage(new ChatComponentText("McForgeSample > debug view enabled"));
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		// needed so that the user can access the command
		return 0;
	}

}
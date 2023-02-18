package com.gmail.prismleaves256.command;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.gmail.prismleaves256.Main;
import com.gmail.prismleaves256.util.HypixelEntityExtractor;
import com.gmail.prismleaves256.util.MapUtil;
import com.gmail.prismleaves256.wrapper.StackedEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;

import javax.imageio.ImageIO;

public class SaveEntitiesCommand extends CommandBase {
    private Main main;
    public SaveEntitiesCommand(Main main) {
        this.main = main;
    }

    @Override
    public String getCommandName() {
        // the command can be used by typing '/debugsound' in chat
        return "saveentities";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "saveentities";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        String text="";
        List<Entity> entities = Minecraft.getMinecraft().theWorld.getLoadedEntityList();
        for (Entity e : entities){
            text+=e.toString()+"\n";
            text+="  Name          : "+e.getName()+"\n";
            text+="  DisplayName   : "+e.getDisplayName()+"\n";
            text+="  CustomNameTag : "+e.getCustomNameTag()+"\n";
            text+="  EntityData    : "+e.getEntityData()+"\n";
            if(e instanceof EntityLiving){
                EntityLiving ee=(EntityLiving)e;
                text+="  EntityHealth  : "+ee.getHealth()+"\n";
            }
            text+= "\n";

        }
        File entitiesFolder = new File(main.getSettings().getLogFolder(), "entities");
        entitiesFolder.mkdirs();
        long timestamp = System.currentTimeMillis();
        File textFile = new File(entitiesFolder, "entities_"+timestamp + ".txt");
        try {
            OutputStreamWriter writer =new OutputStreamWriter(new FileOutputStream(textFile), StandardCharsets.UTF_8);
            //FileWriter fileWriter = new FileWriter(textFile);
            //fileWriter.write(text);
            //fileWriter.close();
            writer.write(text);
            writer.close();
        } catch (IOException ignored) {
        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        // needed so that the user can access the command
        return 0;
    }
}

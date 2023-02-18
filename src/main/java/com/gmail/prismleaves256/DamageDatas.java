package com.gmail.prismleaves256;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DamageDatas {
    private static List<DamageData> damageDataArrayList=new ArrayList<>();

    public static void add(EntityArmorStand e) {
        String text=e.getCustomNameTag();
        if(matchDamage(text)){
            Vec3 pos=e.getPositionVector();
            long damage=Long.parseLong(damageDeleteCode(text));
            for(DamageData data : damageDataArrayList){
                if(pos.distanceTo(data.pos)<3){
                    data.damage+=damage;
                    data.pos=new Vec3((data.pos.xCoord+pos.xCoord)/2.0,(data.pos.yCoord+pos.yCoord)/2.0,(data.pos.zCoord+pos.zCoord)/2.0);
                    return;
                }
            }
            damageDataArrayList.add(new DamageData(pos,damage));
        }
    }
    public static boolean matchDamage(String dmg){
        if(!damageDeleteCode(dmg).matches("\\d+")){
            return false;
        }
        if(damageDeleteCode(dmg).length()>=13){
            return false;
        }
        String cc="777aaaeeeccc";
        if('-'==cc.charAt(damageDeleteCode(dmg).length()-1)){
            return false;
        }
        return true;
    }
    public static String damageDeleteCode(String dmg){
        //✧|☄|❤|♞|
        dmg=dmg.replaceAll("\u00a7.","");
        return dmg.replaceAll("[,\u2727\u2604\u2764\u265e]","");
    }
    public static String damageCompactor(String dmg){
        dmg=damageDeleteCode(dmg);
        int l=dmg.length();
        String cc="777aaaeeeccc";
        String res="\u00a7"+ cc.charAt(l-1);
        if(l<4){
            return res+dmg;
        }else if(l<13){
            res+=dmg.substring(0,(l-1)%3+1)+(l%3==0?"":"."+dmg.substring((l-1)%3+1,3));
            res+= new String[]{"k", "m", "b"}[(l-4)/3];
            return res;
        }else{
            return "error";
        }
    }
    public static void drawIndicator(float ticks,double x,double y,double z,String text){
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        double wx = (x - player.lastTickPosX) + ((x - player.posX) - (x - player.lastTickPosX)) * ticks;
        double wy = (y - player.lastTickPosY) + ((y- player.posY) - (y- player.lastTickPosY)) * ticks;
        double wz = (z - player.lastTickPosZ) + ((z - player.posZ) - (z - player.lastTickPosZ)) * ticks;
        RenderManager renderManager = mc.getRenderManager();
        int width = mc.fontRendererObj.getStringWidth(text) / 2;
        GlStateManager.pushMatrix();
        GlStateManager.translate(wx, wy, wz);
        GL11.glNormal3f(0f, 1f, 0f);
        GlStateManager.rotate(-renderManager.playerViewY, 0f, 1f, 0f);
        GlStateManager.rotate(renderManager.playerViewX, 1f, 0f, 0f);
        GlStateManager.scale(-0.03f, -0.03f, -0.03f);
        //GlStateManager.enableBlend();
        //GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        mc.fontRendererObj.drawString(text, -width, 0, 0xffffff);
        //GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void draw(float partialTicks) {
        for(DamageData data : damageDataArrayList){
            drawIndicator(partialTicks,data.pos.xCoord,data.pos.yCoord+1, data.pos.zCoord, damageCompactor(String.valueOf(data.damage)));
        }
    }
}

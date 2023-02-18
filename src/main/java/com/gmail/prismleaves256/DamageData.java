package com.gmail.prismleaves256;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.Vec3;

public class DamageData {
    Vec3 pos;
    long damage;
    public DamageData(Vec3 _pos, long _damage){
        pos=_pos;
        damage=_damage;
    }

}

package com.deadlyartist.jpa.client.model;

import com.deadlyartist.jpa.item.JetpackItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

/*
 * This is a slightly modified version of the model from Simply Jetpacks
 * https://github.com/Tomson124/SimplyJetpacks-2/blob/1.12/src/main/java/tonius/simplyjetpacks/client/model/ModelJetpack.java
 */
@Environment(EnvType.CLIENT)
public class JetpackModel extends HumanoidModel<LivingEntity> {
    private final JetpackItem jetpack;
    
    public JetpackModel(JetpackItem jetpack) {
        super(newParts());
        this.jetpack = jetpack;
        
        this.body.visible = true;
        this.rightArm.visible = false;
        this.leftArm.visible = false;
        this.head.visible = false;
        this.hat.visible = false;
        this.rightLeg.visible = false;
        this.leftLeg.visible = false;
    }
    
    private static ModelPart newParts() {
        MeshDefinition data = createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition child = data.getRoot().getChild("body");
        child.addOrReplaceChild("middle", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 54)
                        .addBox(-2F, 5F, 3.6F, 4, 3, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("leftCanister", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 32)
                        .addBox(0.5F, 2F, 2.6F, 4, 7, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("rightCanister", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 32)
                        .addBox(-4.5F, 2F, 2.6F, 4, 7, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("leftTip1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 45)
                        .addBox(1F, 0F, 3.1F, 3, 2, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("leftTip2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(0, 50)
                        .addBox(1.5F, -1F, 3.6F, 2, 1, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("rightTip1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 45)
                        .addBox(-4F, 0F, 3.1F, 3, 2, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("rightTip2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(17, 50)
                        .addBox(-3.5F, -1F, 3.6F, 2, 1, 2),
                PartPose.ZERO);
        child.addOrReplaceChild("leftExhaust1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 32)
                        .addBox(1F, 9F, 3.1F, 3, 1, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("leftExhaust2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 37)
                        .addBox(0.5F, 10F, 2.6F, 4, 3, 4),
                PartPose.ZERO);
        child.addOrReplaceChild("rightExhaust1", CubeListBuilder.create()
                        .mirror()
                        .texOffs(48, 32)
                        .addBox(-4F, 9F, 3.1F, 3, 1, 3),
                PartPose.ZERO);
        child.addOrReplaceChild("rightExhaust2", CubeListBuilder.create()
                        .mirror()
                        .texOffs(35, 45)
                        .addBox(-4.5F, 10F, 2.6F, 4, 3, 4),
                PartPose.ZERO);
        
        return data.getRoot().bake(64, 64);
    }
    
    private void setRotation(ModelPart model, float x, float y, float z) {
        model.xRot = x;
        model.yRot = y;
        model.zRot = z;
    }
}

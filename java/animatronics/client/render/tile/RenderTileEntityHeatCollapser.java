package animatronics.client.render.tile;

import java.nio.FloatBuffer;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import animatronics.client.render.RenderPatterns;
import animatronics.common.tile.TileEntityHeatCollapser;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class RenderTileEntityHeatCollapser extends TileEntitySpecialRenderer {

	private static final ResourceLocation endSky = new ResourceLocation("animatronics:textures/misc/end_sky.png");
    private static final ResourceLocation endPortal = new ResourceLocation("animatronics:textures/misc/end_portal.png");
    private static final Random random = new Random(31100L);
    FloatBuffer fBuffer = GLAllocation.createDirectFloatBuffer(16);

    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {   	
    	float f2 = (float)TileEntityRendererDispatcher.staticPlayerX;
        float f3 = (float)TileEntityRendererDispatcher.staticPlayerY;
        float f4 = (float)TileEntityRendererDispatcher.staticPlayerZ;
        GL11.glDisable(2896);
        Random random = new Random(31100L);
        float offset = 0.99f;
        for (int i = 0; i < 16; ++i) {
            GL11.glPushMatrix();
            float f5 = 16 - i;
            float f6 = 0.0625F;
            float f7 = 1.0f / (f5 + 1.0f);
            if (i == 0) {
            	bindTexture(endSky);
            	f7 = 0.1f;
            	f5 = 65.0f;
            	f6 = 0.125f;
            	GL11.glEnable(3042);
            	GL11.glBlendFunc(770, 771);
            }
            if (i == 1) {
            	bindTexture(endPortal);
            	GL11.glEnable(3042);
            	GL11.glBlendFunc(1, 1);
            	f6 = 0.5f;
            }
            float f8 = (float)(-(y + offset));
            float f9 = f8 + ActiveRenderInfo.objectY;
            float f10 = f8 + f5 + ActiveRenderInfo.objectY;
            float f11 = f9 / f10;
            f11 += (float)(y + offset);
            GL11.glTranslatef(f2, f11, f4);
            GL11.glTexGeni(8192, 9472, 9217);
            GL11.glTexGeni(8193, 9472, 9217);
            GL11.glTexGeni(8194, 9472, 9217);
            GL11.glTexGeni(8195, 9472, 9216);
            GL11.glTexGen(8192, 9473, this.calcFloatBuffer(1.0f, 0.0f, 0.0f, 0.0f));
            GL11.glTexGen(8193, 9473, this.calcFloatBuffer(0.0f, 0.0f, 1.0f, 0.0f));
            GL11.glTexGen(8194, 9473, this.calcFloatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
            GL11.glTexGen(8195, 9474, this.calcFloatBuffer(0.0f, 1.0f, 0.0f, 0.0f));
            GL11.glEnable(3168);
            GL11.glEnable(3169);
            GL11.glEnable(3170);
            GL11.glEnable(3171);
            GL11.glPopMatrix();
            GL11.glMatrixMode(5890);
            GL11.glPushMatrix();
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, System.currentTimeMillis() % 700000L / 150000.0f, 0.0f);
            GL11.glScalef(f6, f6, f6);
            GL11.glTranslatef(0.5f, 0.5f, 0.0f);
            GL11.glRotatef((i * i * 4321 + i * 9) * 2.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-0.5f, -0.5f, 0.0f);
            GL11.glTranslatef(-f2, -f4, -f3);
            GL11.glTranslatef(ActiveRenderInfo.objectX * f5 / f9, ActiveRenderInfo.objectZ * f5 / f9, -f3);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            f11 = random.nextFloat() * 0.5f + 0.1f;
            float f12 = random.nextFloat() * 0.5f + 0.4f;
            float f13 = random.nextFloat() * 0.5f + 0.5f;
            if (i == 0) {
            	f12 = (f11 = (f13 = 1.0f));
            }
            tessellator.setBrightness(180);
            tessellator.setColorRGBA_F(f11 * f7, f12 * f7, f13 * f7, 1.0f);
            tessellator.addVertex(x, y + offset, z);
            tessellator.addVertex(x, y + offset, z + 1.0);
            tessellator.addVertex(x + 1.0, y + offset, z + 1.0);
            tessellator.addVertex(x + 1.0, y + offset, z);
            tessellator.draw();
            GL11.glPopMatrix();
            GL11.glMatrixMode(5888);
        }
        GL11.glDisable(3042);
        GL11.glDisable(3168);
        GL11.glDisable(3169);
        GL11.glDisable(3170);
        GL11.glDisable(3171);
        GL11.glEnable(2896);
    }

    private FloatBuffer calcFloatBuffer(float f, float f1, float f2, float f3) {
        this.fBuffer.clear();
        this.fBuffer.put(f).put(f1).put(f2).put(f3);
        this.fBuffer.flip();
        return this.fBuffer;
    }

}
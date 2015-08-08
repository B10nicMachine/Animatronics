package animatronics.utils.misc;

import animatronics.Animatronics;
import animatronics.api.energy.IItemAllowsSeeingEntropy;
import animatronics.api.energy.ITEHasEntropy;
import animatronics.common.tile.TileEntityPrimary;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class EnergyUtils {
	
	public static final float GENERATOR_MAXIMUN_ENTROPY_GLOBAL = 1000000F;
	public static final float CONSUMER_MAXIMUM_ENTROPY_GLOBAL = 100000F;
	
	public static void saveEntropyState(ITEHasEntropy tile, NBTTagCompound saveTag)
	{
		
		saveTag.setFloat("entropy", tile.getEntropy());
		saveTag.setFloat("maxEntropy", tile.getMaxEntropy());
		saveTag.setString("uuid", tile.getUUID().toString());
	}
	
	public static void loadEntropyState(ITEHasEntropy tile, NBTTagCompound loadTag)
	{
		tile.setEntropy((int) loadTag.getFloat("entropy"));
		tile.setMaxEntropy(loadTag.getFloat("maxEntopry"));
	}
	
	public static void entropyIn(TileEntity tile)
	{
		TileEntityPrimary entropyt = (TileEntityPrimary) tile;
		if(entropyt.storageCoord != null)
		{
			float[] coord = {(float) entropyt.storageCoord.x, (float) entropyt.storageCoord.y, (float) entropyt.storageCoord.z};
			if(tile.getWorldObj().getTileEntity((int)coord[0], (int)coord[1], (int)coord[2]) != null && tile.getWorldObj().getTileEntity((int)coord[0], (int)coord[1], (int)coord[2]) instanceof TileEntityPrimary)
			{
				ITEHasEntropy tGen = (ITEHasEntropy) tile.getWorldObj().getTileEntity((int)coord[0], (int)coord[1], (int)coord[2]);
				if(tGen != tile && tGen != null && !tile.getWorldObj().isRemote)
				{
					if(entropyt.getEntropy() < entropyt.getMaxEntropy())
					{
						int entropy = tGen.getEntropy();
						if(entropy > entropyt.getMaxEntropy() - entropyt.getEntropy())
						{
							tGen.setEntropy(entropy-(entropyt.getMaxEntropy() - entropyt.getEntropy()));
							entropyt.setEntropy(entropyt.getMaxEntropy());
						}else
						{
							tGen.setEntropy(0);
							entropyt.setEntropy(entropyt.getEntropy()+entropy);
	    				}
					}
				}
			}
			if(entropyt.getEntropy() < 0)
				entropyt.setEntropy(0);
		}	
	}
	
	public static void spawnParticles(TileEntity tile, float xOffset, float yOffset, float zOffset)
	{
		if(tile.getWorldObj().isRemote)
		{
			EntityClientPlayerMP player = Minecraft.getMinecraft().thePlayer;
			ItemStack currentItem = player.getCurrentEquippedItem();
			if(currentItem != null && currentItem.getItem() instanceof IItemAllowsSeeingEntropy)
			{	
				TileEntityPrimary entropyt = (TileEntityPrimary) tile;
				if(entropyt.storageCoord != null)
				{	
					float[] coord = {(float) entropyt.storageCoord.x, (float) entropyt.storageCoord.y, (float) entropyt.storageCoord.z};
					Vector3 vec = new Vector3(entropyt.xCoord-coord[0], entropyt.yCoord-coord[1], entropyt.zCoord-coord[2]);
					Vector3 vecCopy = vec.copy();
					Vector3 vecNC = vecCopy.normalize().multiply(0.25);
					double dist = vec.toVec3D().lengthVector()/0.25;
					double x = coord[0];
					double y = coord[1];
					double z = coord[2];
					for(int steps=0; steps<(int)dist; steps++) {
						x += vecNC.x;
						y += vecNC.y;
						z += vecNC.z;
						if(Math.random() < 0.25) {
							Animatronics.proxy.sparkleFX((float)(x+xOffset), (float)(y+yOffset), (float)(z+zOffset), 0.75F, 6, 0);	
						}
					}	
				}
			}
		}
	}
	
	public static void manage(TileEntity tile, float xOffset, float yOffset, float zOffset)
	{
		entropyIn(tile);
		spawnParticles(tile, xOffset, yOffset, zOffset);
	}
	
	//TODO: when I made energy for items, I need made this public static void entropyIn(TileEntity tile, int slotNum){}@@ - checking for entropy energy in the items in tile inventory
}

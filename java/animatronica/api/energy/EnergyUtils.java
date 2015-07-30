package animatronica.api.energy;

import animatronica.Animatronica;
import animatronica.common.tile.TileEntityPrimary;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

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
	
	public static void spawnParticles(TileEntity tile)
	{
		if(tile.getWorldObj().isRemote)
		{
			TileEntityPrimary entropyt = (TileEntityPrimary) tile;
			if(entropyt.storageCoord != null)
			{	
				float[] coord = {(float) entropyt.storageCoord.x, (float) entropyt.storageCoord.y, (float) entropyt.storageCoord.z};
				Animatronica.proxy.wispFX(tile.getWorldObj(), coord[0]+0.5F, coord[1]+0.5F, coord[2]+0.5F, (float)255, (float)255, (float)255, (float)0.1F, (float)(entropyt.xCoord+0.5), (float)(entropyt.yCoord+0.5), (float)(entropyt.zCoord+0.5), 1000);
				tile.getWorldObj().spawnParticle("magicCrit", coord[0]+0.5, coord[1]+0.5, coord[2]+0.5, entropyt.storageCoord.x+0.5, entropyt.storageCoord.y+0.5, entropyt.storageCoord.z+0.5);
			}
		}
	}
	
	public static void manage(TileEntity tile)
	{
		entropyIn(tile);
		spawnParticles(tile);
	}
	
	//TODO: when I made energy for items, I need made this public static void entropyIn(TileEntity tile, int slotNum){}@@ - checking for entropy energy in the items in tile inventory
}

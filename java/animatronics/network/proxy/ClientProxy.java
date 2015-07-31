package animatronics.network.proxy;

import animatronics.client.fx.FXRenderingHandler;
import animatronics.client.fx.FXSparkle;
import animatronics.client.fx.FXWisp;
import animatronics.client.render.LibRenderIDs;
import animatronics.common.item.AnimatronicaItems;
import animatronics.debug.RenderBlockDebug;
import animatronics.debug.RenderItemDebug;
import animatronics.debug.RenderTileEntityDebug;
import animatronics.debug.TileEntityDebug;
import animatronics.utils.config.AnimatronicaConfiguration;
import animatronics.utils.event.EventHookContainer;
import animatronics.utils.handler.ClientTickHandler;
import animatronics.utils.handler.DebugInfoHandler;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {
	
	public void registerAll(){
	    super.registerAll();
		render();
		
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new DebugInfoHandler());
		MinecraftForge.EVENT_BUS.register(new FXRenderingHandler());
		MinecraftForge.EVENT_BUS.register(new EventHookContainer());
	}
	
	public void render(){
		LibRenderIDs.idBlockDebug = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(new RenderBlockDebug());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDebug.class, new RenderTileEntityDebug());
		
		MinecraftForgeClient.registerItemRenderer(AnimatronicaItems.itemDebug, new RenderItemDebug());
	}
	
	public World getClientWorld() {
	    return FMLClientHandler.instance().getClient().theWorld;
	}

	@Override
	public long getWorldElapsedTicks() {
		return ClientTickHandler.ticksInGame;
	}

	private static boolean noclipEnabled = false;
	private static boolean corruptSparkle = false;

	@Override
	public void setSparkleFXNoClip(boolean noclip) {
		noclipEnabled = noclip;
	}

	@Override
	public void setSparkleFXCorrupt(boolean corrupt) {
		corruptSparkle = corrupt;
	}

	@Override
	public void sparkleFX(World world, double x, double y, double z, float r, float g, float b, float size, int m) {
		if(!doParticle(world))
			return;

		FXSparkle sparkle = new FXSparkle(world, x, y, z, size, r, g, b, m);
		if(noclipEnabled)
			sparkle.noClip = true;
		if(corruptSparkle)
			sparkle.corrupt = true;
		Minecraft.getMinecraft().effectRenderer.addEffect(sparkle);
	}

	private static boolean distanceLimit = true;
	private static boolean depthTest = true;

	@Override
	public void setWispFXDistanceLimit(boolean limit) {
		distanceLimit = limit;
	}

	@Override
	public void setWispFXDepthTest(boolean test) {
		depthTest = test;
	}

	@Override
	public void wispFX(World world, double x, double y, double z, float r, float g, float b, float size, float motionx, float motiony, float motionz, float maxAgeMul) {
		if(!doParticle(world))
			return;

		FXWisp wisp = new FXWisp(world, x, y, z, size, r, g, b, distanceLimit, depthTest, maxAgeMul);
		wisp.motionX = motionx;
		wisp.motionY = motiony;
		wisp.motionZ = motionz;

		Minecraft.getMinecraft().effectRenderer.addEffect(wisp);
	}

	private boolean doParticle(World world) {
		if(!world.isRemote)
			return false;

		if(!AnimatronicaConfiguration.vanillaParticleLimitter)
			return true;

		float chance = 1F;
		if(Minecraft.getMinecraft().gameSettings.particleSetting == 1)
			chance = 0.6F;
		else if(Minecraft.getMinecraft().gameSettings.particleSetting == 2)
			chance = 0.2F;

		return chance == 1F || Math.random() < chance;
	}
}
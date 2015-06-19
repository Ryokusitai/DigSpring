package digspring;

import java.util.logging.Logger;

import buildcraft.BuildCraftCore;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=DigSpring.MODID, name=DigSpring.MODID, version="@VERSION", dependencies="required-after:BuildCraft|Core;")
public class DigSpring
{
	public static final String MODID = "DigSpring";
	public static Logger logger = Logger.getLogger(DigSpring.MODID);

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		DigSpringConfig.init(event.getSuggestedConfigurationFile());
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		// ダイヤピッケルなら採掘できるようにする
		BuildCraftCore.springBlock.setHardness(30.0F).setHarvestLevel("pickaxe", 3);
	}

}

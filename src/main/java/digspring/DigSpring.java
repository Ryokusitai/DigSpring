package digspring;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import buildcraft.BuildCraftCore;

@Mod(modid="DigSpring", name="DigSpring", version="@VERSION", dependencies="required-after:BuildCraft|Core;")
public class DigSpring
{
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		// ダイヤピッケルなら採掘できるようにする
		BuildCraftCore.springBlock.setHardness(30.0F).setHarvestLevel("pickaxe", 3);
	}

}

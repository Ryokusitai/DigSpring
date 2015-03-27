package digspring.asm;

import java.util.Map;
import java.util.logging.Logger;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class DigSpringCorePlugin implements IFMLLoadingPlugin
{
	public static Logger logger = Logger.getLogger("DigSpringCore");

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"digspring.asm.QPBlockFrameTransformer", "digspring.asm.BlockFrameDigSpringTransformer"};
	}

	@Override
	public String getModContainerClass() {
		return "digspring.asm.DigSpringContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}

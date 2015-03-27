package digspring.asm;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class DigSpringContainer extends DummyModContainer
{
	public DigSpringContainer() {
		super(new ModMetadata());

		ModMetadata meta = getMetadata();
		meta.modId = "DigSpringCore";
		meta.name = "DigSpringCore";
		meta.version = "@Verison@";
		meta.authorList = Arrays.asList("ryokusitai");
		meta.description = "";
		meta.url = "";
        meta.credits = "";

		this.setEnabledState(true);
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}

}

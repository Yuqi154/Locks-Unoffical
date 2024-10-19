package melonslise.locks.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class EmptyCapabilityStorage<A> implements IStorage<A>
{
	@Override
	public Tag writeNBT(Capability<A> cap, A inst, Direction side)
	{
		return null;
	}

	@Override
	public void readNBT(Capability<A> cap, A inst, Direction side, Tag nbt)
	{
	}
}
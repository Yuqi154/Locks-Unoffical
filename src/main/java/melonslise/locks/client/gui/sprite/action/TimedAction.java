package melonslise.locks.client.gui.sprite.action;

import melonslise.locks.client.gui.sprite.Sprite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public abstract class TimedAction<S extends Sprite> extends SingleCallbackAction<S>
{
	public int ticks;

	public TimedAction<S> time(int ticks)
	{
		this.ticks = ticks;
		return this;
	}

	@Override
	public boolean isFinished(S sprite)
	{
		return this.ticks == 0;
	}

	@Override
	public void update(S sprite)
	{
		--this.ticks;
	}
}
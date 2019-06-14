package melonslise.locks.utility;

import java.util.Objects;
import java.util.Observable;

public class Lock extends Observable
{
	// TODO Visibility on all these
	public final int id;
	protected byte[] combination;
	protected boolean locked;

	protected Lock(int id, byte[] combination, boolean locked)
	{
		this.id = id;
		this.combination = combination;
		this.locked = locked;
	}

	public Lock(int id, int length, boolean locked)
	{
		this.id = id;
		this.combination = new byte[length];
		for(byte a = 0; a < length; ++a) combination[a] = a;
		this.shuffle();
		this.locked = locked;
	}

	public int getLength()
	{
		return this.combination.length;
	}

	public boolean isLocked()
	{
		return this.locked;
	}

	public void setLocked(boolean locked)
	{
		if(this.locked != locked)
		{
			this.setChanged();
			this.notifyObservers();
		}
		this.locked = locked;
	}

	public boolean checkPin(int index, int pin)
	{
		return this.combination[index] == pin;
	}

	public void shuffle()
	{
		LocksUtilities.shuffle(this.combination);
	}

	@Override
	public boolean equals(Object object)
	{
		if(this == object) return true;
		if(!(object instanceof Lock)) return false;
		Lock lock = (Lock) object;
		return this.id == lock.id && this.locked == lock.locked && ((this.combination == null && lock.combination == null) || this.combination.equals(lock.combination));
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(this.id, this.combination, this.locked);
	}
}